package ptit.cuonghq.walltag.controller;

import ptit.cuonghq.walltag.models.commons.ResponseFactory;
import ptit.cuonghq.walltag.models.commons.ResponseObjectResult;
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
import ptit.cuonghq.walltag.utils.Const;

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
    @GetMapping(Const.Path.Place.MANAGED)
    @ApiOperation(value = "Lấy danh sách địa điểm đăng ký của provider")
    public ResponseEntity<ResponseObjectResult> getList(@RequestHeader(value = "Authorization") int id) {
        return service.getList(id);
    }

    /***GET PLACE DETAIL INFORMATION*********************************************************************************************************/
    @GetMapping(Const.Path.Place.DETAIL)
    @ApiOperation(value = "Lấy thông tin chi tiết về địa điểm ")
    public ResponseEntity<ResponseObjectResult> getPlaceDetailInformation(@RequestHeader(value = "Authorization") int idProvider, @PathVariable("id") int idPlace) {
        return service.getPlaceDetailInformation(idProvider, idPlace);
    }

    /***CREATE NEW PLACE ON MANAGED********************************************************************************************************/
    @PostMapping(Const.Path.Place.MANAGED)
    @ApiOperation(value = "Đăng ký một địa điểm mới")
    public ResponseEntity<ResponseObjectResult> createNewManagedPlace(@RequestHeader(value = "Authorization") int idProvider, @RequestBody CreateNewPlaceRequestBody requestBody) {
        return service.createNewManagedPlace(idProvider, requestBody);
    }

    /***UPDATE PLACE INFORMATION*************************************************************************************************************/
    @PutMapping(Const.Path.Place.DETAIL)
    @ApiOperation(value = "Cập nhật thông tin về địa điểm")
    public ResponseEntity<ResponseObjectResult> updatePlaceInformation(@RequestHeader(value = "Authorization") int idProvider, @PathVariable("id") int idPlace, @RequestBody UpdatePlaceRequestBody requestBody) {
        return service.updatePlaceInformation(idProvider, idPlace, requestBody);
    }

    /***GET PLACE WALL TYPE AND POSTER TYPE LIST*********************************************************************************************/
    @GetMapping(Const.Path.Place.TYPE)
    @ApiOperation(value = "Lấy danh sách thể loại poster và thể loại tường")
    public ResponseEntity<ResponseObjectResult> getPlaceTypeList(@RequestHeader(value = "Authorization") int idProvider) {
       return service.getTypeList(idProvider);
    }

    /***SEARCH FOR PLACES*******************************************************************************************************************/
    @GetMapping(Const.Path.Place.SEARCH)
    @ApiOperation(value = "Tìm kiếm địa điểm")
    public ResponseEntity<ResponseObjectResult> searchPlace(@RequestHeader("Authorization")int idUser,
                                                            @RequestParam("lat") Optional<Double> lat,
                                                            @RequestParam("lng") Optional<Double> lng,
                                                            @RequestParam("distance") Optional<Double> distance,
                                                            @RequestParam("min_width") Optional<Integer> minWidth,
                                                            @RequestParam("max_width") Optional<Integer> maxWidth,
                                                            @RequestParam("min_height") Optional<Integer> minHeight,
                                                            @RequestParam("max_height") Optional<Integer> maxHeight,
                                                            @RequestParam("min_price") Optional<Integer> minPrice,
                                                            @RequestParam("max_price") Optional<Integer> maxPrice,
                                                            @RequestParam("id_poster") Optional<String> idPoster,
                                                            @RequestParam("id_wall") Optional<String> idWall) {
        SearchRequestModel requestModel = new SearchRequestModel();
        requestModel.setLat(lat.orElse(0.0));
        requestModel.setLng(lng.orElse(0.0));
        requestModel.setDistance(distance.orElse(2.0));
        requestModel.setMinWidth(minWidth.orElse(0));
        requestModel.setMaxWidth(maxWidth.orElse(1000));
        requestModel.setMinHeight(minHeight.orElse(0));
        requestModel.setMaxHeight(maxHeight.orElse(1000));
        requestModel.setMinPrice(minPrice.orElse(0));
        requestModel.setMaxPrice(maxPrice.orElse(10000000));
        requestModel.setIdPoster(idPoster.orElse("10000,100001,10002"));
        requestModel.setIdWall(idWall.orElse("10000,100001,10002"));

        return service.searchPlace(idUser, requestModel);
    }

    /***GET FAVORITE PLACE OF USER***********************************************************************************************************/
    @GetMapping("/favorite")
    @ApiOperation(value = "Lấy danh sách các địa điểm yêu thích của customer")
    private ResponseEntity<ResponseObjectResult> getFavoritePlaces(@RequestHeader("Authorization") int idCustomer) {
        User user = authService.checkCustomer(idCustomer);
        if (user != null) {
            return service.getFavoritePlace(idCustomer);
        } else {
            return ResponseFactory.authorizationError();
        }

    }

    /***ADD NEW PLACE TO FAVORITE LIST**************************************************************************************************/
    @PutMapping("/favorite")
    @ApiOperation(value = "Thêm địa điểm vào danh sách yêu thích")
    private ResponseEntity<ResponseObjectResult> addNewFavoritePlace(@RequestHeader("Authorization") int idCustomer, @RequestParam("id_place") int idPlace) {
        User user = authService.checkCustomer(idCustomer);
        if (user != null) {
            return service.addPlaceToFavoriteList(user, idPlace);
        } else {
            return ResponseFactory.authorizationError();
        }
    }

    /***REMOVE PLACE FROM FAVORITE LIST******************************************************************************************************/
    @DeleteMapping("/favorite")
    @ApiOperation(value = "Xóa địa điểm khỏi danh sách yêu thích")
    private ResponseEntity<ResponseObjectResult> removePlaceFromFavoriteList(@RequestHeader("Authorization") int idCustomer, @RequestParam("id_place") int idPlace) {
        User user = authService.checkCustomer(idCustomer);
        if (user != null) {
            return service.removePlaceFromFavoriteList(user, idPlace);
        } else {
            return ResponseFactory.authorizationError();
        }
    }

    /****************************************************************************************************************************************/
    @GetMapping
    @ApiOperation(value = "Lấy danh sách địa điểm theo các tiêu chí")
    private ResponseEntity<ResponseObjectResult> getPlaceByCategory(@RequestHeader("Authorization") int idCustomer, @RequestParam("category") String category) {
        User user = authService.checkCustomer(idCustomer);
        if (user != null) {
            switch (category) {
                case "latest": {
                    return service.getLatestPlace();
                }
                default:
                    return ResponseFactory.badRequest("Category is not valid");
            }
        } else {
            return ResponseFactory.authorizationError();
        }
    }
}
