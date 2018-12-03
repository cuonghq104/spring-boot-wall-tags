package ptit.cuonghq.walltag.services;

import org.springframework.http.ResponseEntity;
import ptit.cuonghq.walltag.models.commons.ResponseFactory;
import ptit.cuonghq.walltag.models.commons.ResponseObjectResult;
import ptit.cuonghq.walltag.models.beans.*;
import ptit.cuonghq.walltag.models.repositories.*;
import ptit.cuonghq.walltag.models.requestmodels.CreateNewPlaceRequestBody;
import ptit.cuonghq.walltag.models.requestmodels.SearchRequestModel;
import ptit.cuonghq.walltag.models.requestmodels.UpdatePlaceRequestBody;
import ptit.cuonghq.walltag.models.responsemodels.PlaceSearch;
import ptit.cuonghq.walltag.models.responsemodels.PlaceSummary;
import ptit.cuonghq.walltag.models.responsemodels.TypeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ptit.cuonghq.walltag.services.serviceinterface.PlaceServiceInterface;

import java.sql.Timestamp;
import java.util.*;

@Service
public class PlaceService implements PlaceServiceInterface {

    @Autowired
    PlaceRepository repository;

    @Autowired
    ProviderRepository providerRepository;

    @Autowired
    WallTypeRepository wallTypeRepository;

    @Autowired
    PosterTypeRepository posterTypeRepository;

    @Autowired
    ImageRepository imageRepository;

    public User checkUser(int idUser) {
        Optional<User> optionalUser = providerRepository.findById(idUser);
        return optionalUser.orElse(null);
    }

    public Place getPlace(int idPlace) {
        Optional<Place> optionalPlace = repository.findById(idPlace);
        return optionalPlace.orElse(null);
    }

    @Override
    public ResponseEntity<ResponseObjectResult> getTypeList(int idProvider) {
        Optional<User> optionalProvider = providerRepository.findById(idProvider);
        if (optionalProvider.isPresent()) {
            List<WallType> wallTypes = wallTypeRepository.findAll();
            List<PosterType> posterTypes = posterTypeRepository.findAll();

            TypeResponse response = new TypeResponse(wallTypes, posterTypes);
            return ResponseFactory.ok("Success", response);

        } else {
            return ResponseFactory.authorizationError();
        }
    }

    @Override
    public ResponseEntity<ResponseObjectResult> getList(int idProvider) {
        Optional<User> optionalProvider = providerRepository.findById(idProvider);
        if (optionalProvider.isPresent()) {
            User user = optionalProvider.get();
            List<PlaceSummary> places = repository.findPlaceByProviderId(user.getId());
            return ResponseFactory.ok("OK", places);
        } else {
            return ResponseFactory.authorizationError();
        }
    }

    @Override
    public ResponseEntity<ResponseObjectResult> updatePlaceInformation(int idProvider, int idPlace, UpdatePlaceRequestBody requestBody) {
        Optional<User> optionalProvider = providerRepository.findById(idProvider);
        if (optionalProvider.isPresent()) {

            User user = optionalProvider.get();
            Optional<Place> optionalPlace = repository.findById(idPlace);
            if (optionalPlace.isPresent()) {

                Place place = optionalPlace.get();

                if (user.getId() != place.getUser().getId()) {
                    return ResponseFactory.authorizationError();
                }

                Optional<WallType> optionalWallType = wallTypeRepository.findById(requestBody.getIdWallType());
                Optional<PosterType> optionalPosterType = posterTypeRepository.findById(requestBody.getIdPosterType());

                if (!optionalWallType.isPresent()) {
                    return ResponseFactory.badRequest("Id wall type not true");
                }

                if (!optionalPosterType.isPresent()) {
                    return ResponseFactory.badRequest("Id poster type not true");
                }

                place.setPrice(requestBody.getPrice());
                place.setDescription(requestBody.getDescription());
                place.setAddress(requestBody.getAddress());
                place.setImageUrl(requestBody.getImageUrl());

                Place savedPlace = repository.save(place);
                return ResponseFactory.ok("Place information update", savedPlace);

            } else {
                return ResponseFactory.badRequest("Place doesn't exist");
            }
        } else {
            return ResponseFactory.authorizationError();
        }
    }

    @Override
    public ResponseEntity<ResponseObjectResult> createNewManagedPlace(int idProvider, CreateNewPlaceRequestBody requestBody) {
        Optional<User> optionalProvider = providerRepository.findById(idProvider);
        if (optionalProvider.isPresent()) {
            User user = optionalProvider.get();

            Set<WallType> wallTypeSet = new HashSet<>();
            String[] wallTypes = requestBody.getWallTypes().split("&");
            for (String wallType : wallTypes) {
                try {
                    Optional<WallType> optionalWallType = wallTypeRepository.findById(Integer.parseInt(wallType));
                    WallType wt = optionalWallType.orElse(null);
                    if (wt != null) {
                        wallTypeSet.add(wt);
                    }
                } catch (NumberFormatException exception) {
                    return ResponseFactory.badRequest("Id wall type not valid");
                }
            }

            Set<PosterType> posterTypeSet = new HashSet<>();
            String[] posterTypes = requestBody.getPosterTypes().split("&");
            for (String posterType : posterTypes) {
                try {
                    Optional<PosterType> optionalPosterType = posterTypeRepository.findById(Integer.parseInt(posterType));
                    PosterType pt = optionalPosterType.orElse(null);
                    if (pt != null) {
                        posterTypeSet.add(pt);
                    }
                } catch (NumberFormatException exception) {
                    return ResponseFactory.badRequest("Id poster type not valid");
                }

            }

            Place place = new Place();
            place.setDateCreated(new Timestamp(new Date().getTime()));
            place.setWallType(wallTypeSet);
            place.setPosterType(posterTypeSet);
            place.setAddress(requestBody.getAddress());
            place.setDescription(requestBody.getDescription());
            place.setImageUrl(requestBody.getImageUrl());
            place.setLatitude(requestBody.getLatitude());
            place.setLongitude(requestBody.getLongitude());
            place.setPrice(requestBody.getPrice());
            place.setUser(user);
            place.setWidth(requestBody.getWidth());
            place.setHeight(requestBody.getHeight());

            Place savedPlace = repository.save(place);
            return ResponseFactory.created("New place created", savedPlace);

        } else {
            return ResponseFactory.authorizationError();
        }

    }

    @Override
    public ResponseEntity<ResponseObjectResult> getPlaceDetailInformation(int idProvider, int idPlace) {
        Optional<User> optionalProvider = providerRepository.findById(idProvider);
        Optional<Place> optionalPlace = repository.findById(idPlace);
        if (optionalProvider.isPresent()) {
            User user = optionalProvider.get();
            if (optionalPlace.isPresent()) {
                Place place = optionalPlace.get();

                if (place.getUser().getId() == user.getId()) {

                    List<Image> images = imageRepository.getImages("place", place.getId());
                    place.setAdditionalImages(images);
                    return ResponseFactory.ok("Get place information success", place);
                } else {
                    return ResponseFactory.authorizationError();
                }
            } else {
                return ResponseFactory.badRequest("Place doesn't exist");
            }
        } else {
            return ResponseFactory.authorizationError();
        }

    }

    @Override
    public ResponseEntity<ResponseObjectResult> searchPlace(int idUser, SearchRequestModel requestModel) {
        User user = checkUser(idUser);
        if (user == null) {
            return ResponseFactory.authorizationError();
        } else {

            List<PlaceSearch> list = repository.searchPlace(requestModel.getLat(),
                    requestModel.getLng(),
                    requestModel.getDistance(),
                    requestModel.getMinWidth(),
                    requestModel.getMaxWidth(),
                    requestModel.getMinHeight(),
                    requestModel.getMaxHeight(),
                    requestModel.getMinPrice(),
                    requestModel.getMaxPrice(),
                    requestModel.getIdWall(),
                    requestModel.getIdPoster());
            return ResponseFactory.ok(((list == null) ? 0 : list.size()) + " result", list);
        }
    }

    @Override
    public ResponseEntity<ResponseObjectResult> getFavoritePlace(int idCustomer) {
        List<Place> places = repository.getFavoritePlace(idCustomer);
        return ResponseFactory.ok(((places == null) ? 0 : places.size()) + "results", places);
    }

    @Override
    public ResponseEntity<ResponseObjectResult> addPlaceToFavoriteList(User user, int idPlace) {
        Set<Place> places = user.getFavoritePlaces();
        Place place = repository.findById(idPlace).orElse(null);
        if (place == null) {
            return ResponseFactory.badRequest("ID place not exist");
        } else {

            if (places.contains(place)) {
                return ResponseFactory.badRequest("This place with id " + idPlace + " is already exist in favorite list");

            } else {
                places.add(place);
                user.setFavoritePlaces(places);
                providerRepository.save(user);
                return ResponseFactory.created("Success", null);
            }

        }
    }

    @Override
    public ResponseEntity<ResponseObjectResult> removePlaceFromFavoriteList(User user, int idPlace) {
        Set<Place> places = user.getFavoritePlaces();
        Place place = repository.findById(idPlace).orElse(null);
        if (place == null) {
            return ResponseFactory.badRequest("ID place not exist");
        } else {

            if (!places.contains(place)) {
                return ResponseFactory.badRequest("This place with id " + idPlace + " have not been exist in favorite list");

            } else {
                places.remove(place);
                user.setFavoritePlaces(places);
                providerRepository.save(user);
                return ResponseFactory.created("Success", null);
            }

        }
    }

    @Override
    public ResponseEntity<ResponseObjectResult> getLatestPlace() {
        List<Place> places = repository.getNewCreatePlace();
        return ResponseFactory.ok(((places == null) ? 0 : places.size()) + " results", places);
    }
}
