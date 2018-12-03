package ptit.cuonghq.walltag.services.serviceinterface;

import org.springframework.http.ResponseEntity;
import ptit.cuonghq.walltag.models.commons.ResponseObjectResult;
import ptit.cuonghq.walltag.models.beans.User;
import ptit.cuonghq.walltag.models.requestmodels.CreateNewPlaceRequestBody;
import ptit.cuonghq.walltag.models.requestmodels.SearchRequestModel;
import ptit.cuonghq.walltag.models.requestmodels.UpdatePlaceRequestBody;

public interface PlaceServiceInterface {

    ResponseEntity<ResponseObjectResult> getTypeList(int idProvider);

    ResponseEntity<ResponseObjectResult> getList(int idProvider);

    ResponseEntity<ResponseObjectResult> updatePlaceInformation(int idProvider, int idPlace, UpdatePlaceRequestBody requestBody);

    ResponseEntity<ResponseObjectResult> createNewManagedPlace(int idProvider, CreateNewPlaceRequestBody requestBody);

    ResponseEntity<ResponseObjectResult> getPlaceDetailInformation(int idProvider, int idPlace);

    ResponseEntity<ResponseObjectResult> searchPlace(int idUser, SearchRequestModel requestModel);

    ResponseEntity<ResponseObjectResult> getFavoritePlace(int idCustomer);

    ResponseEntity<ResponseObjectResult> addPlaceToFavoriteList(User user, int idPlace);

    ResponseEntity<ResponseObjectResult> removePlaceFromFavoriteList(User user, int idPlace);

    ResponseEntity<ResponseObjectResult> getLatestPlace();
}

