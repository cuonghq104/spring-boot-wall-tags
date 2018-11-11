package ptit.cuonghq.walltag.controller;

import ptit.cuonghq.walltag.models.ResponseObjectResult;
import ptit.cuonghq.walltag.models.beans.User;
import ptit.cuonghq.walltag.models.requestmodels.CreateNewPlaceRequestBody;
import ptit.cuonghq.walltag.models.requestmodels.SearchRequestModel;
import ptit.cuonghq.walltag.models.requestmodels.UpdatePlaceRequestBody;
import ptit.cuonghq.walltag.services.PlaceService;
import ptit.cuonghq.walltag.services.ProviderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@Api(value = "place", description = "Place Apis", produces = "application/json", tags = {"Place"})
@RequestMapping("place")
public class PlaceController {

    /****************************************************************************************************************************************/
    @Autowired
    PlaceService service;

    @Autowired
    private ProviderService authService;

    /***GET PLACE MANAGED BY PROVIDER LIST***************************************************************************************************/
    @GetMapping("/managed")
    @ApiOperation(value = "Get managed place list")
    public ResponseEntity<ResponseObjectResult> getList(@RequestHeader(value = "Authorization") int id) {
        ResponseObjectResult result = service.getList(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /***GET PLACE DETAIL INFORMATION*********************************************************************************************************/
    @GetMapping("/managed/{id}")
    @ApiOperation(value = "Get place detail information ")
    public ResponseEntity<ResponseObjectResult> getPlaceDetailInformation(@RequestHeader(value = "Authorization") int idProvider, @PathVariable("id") int idPlace) {
        ResponseObjectResult result = service.getPlaceDetailInformation(idProvider, idPlace);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /***CREATE NEW PLACE ON MANAGED********************************************************************************************************/
    @PostMapping("/managed")
    @ApiOperation(value = "Create new managed place")
    public ResponseEntity<ResponseObjectResult> createNewManagedPlace(@RequestHeader(value = "Authorization") int idProvider, @RequestBody CreateNewPlaceRequestBody requestBody) {
        ResponseObjectResult result = service.createNewManagedPlace(idProvider, requestBody);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }

    /***UPDATE PLACE INFORMATION*************************************************************************************************************/
    @PutMapping("/managed/{id}")
    @ApiOperation(value = "Update place information")
    public ResponseEntity<ResponseObjectResult> updatePlaceInformation(@RequestHeader(value = "Authorization") int idProvider, @PathVariable("id") int idPlace, @RequestBody UpdatePlaceRequestBody requestBody) {
        ResponseObjectResult result = service.updatePlaceInformation(idProvider, idPlace, requestBody);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

    }

    /***GET PLACE WALL TYPE AND POSTER TYPE LIST*********************************************************************************************/
    @GetMapping("/type")
    @ApiOperation(value = "Get place poster type and wall type list")
    public ResponseEntity<ResponseObjectResult> getPlaceTypeList(@RequestHeader(value = "Authorization") int idProvider) {
        ResponseObjectResult result = service.getTypeList(idProvider);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        }
    }

    /***SEARCH FOR PLACES*******************************************************************************************************************/
    @GetMapping("/search")
    @ApiOperation(value = "Search for places")
    public ResponseEntity<ResponseObjectResult> searchPlace(@RequestHeader("Authorization")int idUser,
                                                            @RequestParam("lat") double lat,
                                                            @RequestParam("lng") double lng,
                                                            @RequestParam("distance") Optional<Double> distance,
                                                            @RequestParam("min_width") Optional<Integer> minWidth,
                                                            @RequestParam("max_width") Optional<Integer> maxWidth,
                                                            @RequestParam("min_height") Optional<Integer> minHeight,
                                                            @RequestParam("max_height") Optional<Integer> maxHeight,
                                                            @RequestParam("min_price") Optional<Integer> minPrice,
                                                            @RequestParam("max_price") Optional<Integer> maxPrice,
                                                            @RequestParam("id_poster") String idPoster,
                                                            @RequestParam("id_wall") String idWall) {
        SearchRequestModel requestModel = new SearchRequestModel();
        requestModel.setLat(lat);
        requestModel.setLng(lng);
        requestModel.setDistance(distance.orElse(2.0));
        requestModel.setMinWidth(minWidth.orElse(0));
        requestModel.setMaxWidth(maxWidth.orElse(1000));
        requestModel.setMinHeight(minHeight.orElse(0));
        requestModel.setMaxHeight(maxHeight.orElse(1000));
        requestModel.setMinPrice(minPrice.orElse(0));
        requestModel.setMaxPrice(maxPrice.orElse(2000000));
        requestModel.setIdPoster(idPoster);
        requestModel.setIdWall(idWall);

        ResponseObjectResult result = service.searchPlace(idUser, requestModel);
        if (result.getCode() == 401) {
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        } else if (result.getCode() == 200){
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }

    /***GET FAVORITE PLACE OF USER***********************************************************************************************************/
    @GetMapping("/favorite")
    @ApiOperation(value = "Get list favorite place of customer")
    private ResponseEntity<ResponseObjectResult> getFavoritePlaces(@RequestHeader("Authorization") int idCustomer) {
        User user = authService.checkUser(idCustomer);
        if (user != null && user.getRole().contains("customer")) {
            ResponseObjectResult result = service.getFavoritePlace(idCustomer);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseObjectResult(false, 401, "Authorization Error", null), HttpStatus.UNAUTHORIZED);
        }

    }

    /****************************************************************************************************************************************/
    @GetMapping
    @ApiOperation(value = "Get place by its categories")
    private ResponseEntity<ResponseObjectResult> getPlaceByCategory(@RequestHeader("Authorization") int idCustomer, @RequestParam("category") String category) {
        User user = authService.checkUser(idCustomer);
        if (user != null && user.getRole().contains("customer")) {
            switch (category) {
                case "latest": {
                    ResponseObjectResult result = service.getLastestPlace();
                    return new ResponseEntity<>(result, HttpStatus.OK);
                }
                default:
                    return new ResponseEntity<>(new ResponseObjectResult(false, 400, "Category is not valid", null), HttpStatus.BAD_REQUEST);

            }
        } else {
            return new ResponseEntity<>(new ResponseObjectResult(false, 401, "Authorization Error", null), HttpStatus.UNAUTHORIZED);
        }
    }
}
