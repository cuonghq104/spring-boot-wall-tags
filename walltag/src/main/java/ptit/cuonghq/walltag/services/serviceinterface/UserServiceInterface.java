package ptit.cuonghq.walltag.services.serviceinterface;

import org.springframework.http.ResponseEntity;
import ptit.cuonghq.walltag.models.commons.ResponseObjectResult;
import ptit.cuonghq.walltag.models.beans.User;
import ptit.cuonghq.walltag.models.requestmodels.RegisterRequestBody;
import ptit.cuonghq.walltag.models.requestmodels.UpdateInfoRequestBody;

public interface UserServiceInterface {

    ResponseEntity register(RegisterRequestBody requestBody, String role);

    ResponseEntity login(String phoneOrEmail, String password);

    ResponseEntity facebookLogin(String facebookAccessToken);

    ResponseEntity update(int id, UpdateInfoRequestBody requestBody);

    User checkProvider(int idProvider);

    User checkCustomer(int idCustomer);
}
