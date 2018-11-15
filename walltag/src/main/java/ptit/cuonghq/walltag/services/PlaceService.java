package ptit.cuonghq.walltag.services;

import ptit.cuonghq.walltag.models.ResponseObjectResult;
import ptit.cuonghq.walltag.models.beans.*;
import ptit.cuonghq.walltag.models.repositories.PlaceRepository;
import ptit.cuonghq.walltag.models.repositories.PosterTypeRepository;
import ptit.cuonghq.walltag.models.repositories.ProviderRepository;
import ptit.cuonghq.walltag.models.repositories.WallTypeRepository;
import ptit.cuonghq.walltag.models.requestmodels.CreateNewPlaceRequestBody;
import ptit.cuonghq.walltag.models.requestmodels.SearchRequestModel;
import ptit.cuonghq.walltag.models.requestmodels.UpdatePlaceRequestBody;
import ptit.cuonghq.walltag.models.responsemodels.PlaceSearch;
import ptit.cuonghq.walltag.models.responsemodels.PlaceSummary;
import ptit.cuonghq.walltag.models.responsemodels.TypeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class PlaceService {

    @Autowired
    PlaceRepository repository;

    @Autowired
    ProviderRepository providerRepository;

    @Autowired
    WallTypeRepository wallTypeRepository;

    @Autowired
    PosterTypeRepository posterTypeRepository;

    public User checkUser(int idUser) {
        Optional<User> optionalUser = providerRepository.findById(idUser);
        return optionalUser.orElse(null);
    }

    public Place getPlace(int idPlace) {
        Optional<Place> optionalPlace = repository.findById(idPlace);
        return optionalPlace.orElse(null);
    }
    public ResponseObjectResult getTypeList(int idProvider) {
        Optional<User> optionalProvider = providerRepository.findById(idProvider);
        if (optionalProvider.isPresent()) {
            List<WallType> wallTypes = wallTypeRepository.findAll();
            List<PosterType> posterTypes = posterTypeRepository.findAll();

            TypeResponse response = new TypeResponse(wallTypes, posterTypes);
            return new ResponseObjectResult(true, 200, "Success", response);

        } else {
            return new ResponseObjectResult(false, 401, "User doesn't exist", null);
        }
    }

    public ResponseObjectResult getList(int idProvider) {
        Optional<User> optionalProvider = providerRepository.findById(idProvider);
        if (optionalProvider.isPresent()) {
            User user = optionalProvider.get();
            List<PlaceSummary> places = repository.findPlaceByProviderId(user.getId());
            return new ResponseObjectResult(true, 200, "OK", places);
        } else {
            return new ResponseObjectResult(false, 401, "User doesn't exist", null);
        }
    }

    public ResponseObjectResult updatePlaceInformation(int idProvider, int idPlace, UpdatePlaceRequestBody requestBody) {
        Optional<User> optionalProvider = providerRepository.findById(idProvider);
        if (optionalProvider.isPresent()) {

            User user = optionalProvider.get();
            Optional<Place> optionalPlace = repository.findById(idPlace);
            if (optionalPlace.isPresent()) {

                Place place = optionalPlace.get();

                if (user.getId() != place.getUser().getId()) {
                    return new ResponseObjectResult(false, 401, "Unauthorised", null);
                }

                Optional<WallType> optionalWallType = wallTypeRepository.findById(requestBody.getIdWallType());
                Optional<PosterType> optionalPosterType = posterTypeRepository.findById(requestBody.getIdPosterType());

                if (!optionalWallType.isPresent()) {
                    return new ResponseObjectResult(false, 400, "Id wall type not true", null);
                }

                if (!optionalPosterType.isPresent()) {
                    return new ResponseObjectResult(false, 400, "Id poster type not true", null);
                }

//                place.setPosterType(optionalPosterType.get());
//                place.setWallType(optionalWallType.get());
                place.setPrice(requestBody.getPrice());
                place.setDescription(requestBody.getDescription());
                place.setAddress(requestBody.getAddress());
                place.setImageUrl(requestBody.getImageUrl());

                Place savedPlace = repository.save(place);
                return new ResponseObjectResult(true, 200, "Place information update", savedPlace);

            } else {
                return new ResponseObjectResult(false, 400, "Place doesn't exist", null);
            }
        } else {
            return new ResponseObjectResult(false, 401, "User doesn't exist", null);
        }
    }

    public ResponseObjectResult createNewManagedPlace(int idProvider, CreateNewPlaceRequestBody requestBody) {
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
                    return new ResponseObjectResult(false, 400, "Id wall type not valid", null);
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
                    return new ResponseObjectResult(false, 400, "Id poster type not valid", null);
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
            return new ResponseObjectResult(true, 201, "New place created", savedPlace);

        } else {
            return new ResponseObjectResult(false, 401, "User doesn't exist", null);
        }

    }

    public ResponseObjectResult getPlaceDetailInformation(int idProvider, int idPlace) {
        Optional<User> optionalProvider = providerRepository.findById(idProvider);
        Optional<Place> optionalPlace = repository.findById(idPlace);
        if (optionalProvider.isPresent()) {
            User user = optionalProvider.get();
            if (optionalPlace.isPresent()) {
                Place place = optionalPlace.get();

                if (place.getUser().getId() == user.getId()) {
                    return new ResponseObjectResult(true, 200, "Get place information success", place);
                } else {
                    return new ResponseObjectResult(false, 401, "Unauthorised", null);
                }
            } else {
                return new ResponseObjectResult(false, 401, "Place doesn't exist", null);
            }
        } else {
            return new ResponseObjectResult(false, 401, "User doesn't exist", null);
        }

    }

    public ResponseObjectResult searchPlace(int idUser, SearchRequestModel requestModel) {
        User user = checkUser(idUser);
        if (user == null) {
            return new ResponseObjectResult(false, 401, "User doesn't exist", null);
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
            return new ResponseObjectResult(true, 200, ((list == null) ? 0 : list.size()) + " result", list);
        }
    }

    public ResponseObjectResult getFavoritePlace(int idCustomer) {
        List<Place> places = repository.getFavoritePlace(idCustomer);
        return new ResponseObjectResult(true, 200, ((places == null) ? 0 : places.size()) + "results", places);
    }

    public ResponseObjectResult addPlaceToFavoriteList(User user, int idPlace) {
        Set<Place> places = user.getFavoritePlaces();
        Place place = repository.findById(idPlace).orElse(null);
        if (place == null) {
            return new ResponseObjectResult(false, 400, "ID place not exist", null);
        } else {

            if (places.contains(place)) {
                return new ResponseObjectResult(false, 400, "This place with id " + idPlace + " is already exist in favorite list", null);

            } else {
                places.add(place);
                user.setFavoritePlaces(places);
                providerRepository.save(user);
                return new ResponseObjectResult(true, 201, "Success", null);
            }

        }
    }


    public ResponseObjectResult removePlaceFromFavoriteList(User user, int idPlace) {
        Set<Place> places = user.getFavoritePlaces();
        Place place = repository.findById(idPlace).orElse(null);
        if (place == null) {
            return new ResponseObjectResult(false, 400, "ID place not exist", null);
        } else {

            if (!places.contains(place)) {
                return new ResponseObjectResult(false, 400, "This place with id " + idPlace + " have not been exist in favorite list", null);

            } else {
                places.remove(place);
                user.setFavoritePlaces(places);
                providerRepository.save(user);
                return new ResponseObjectResult(true, 201, "Success", null);
            }

        }
    }

    public ResponseObjectResult getLatestPlace() {
        List<Place> places = repository.getNewCreatePlace();
        return new ResponseObjectResult(true, 200, ((places == null) ? 0 : places.size()) + " results", places);
    }
}
