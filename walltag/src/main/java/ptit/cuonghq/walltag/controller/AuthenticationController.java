package ptit.cuonghq.walltag.controller;

import ptit.cuonghq.walltag.models.ResponseObjectResult;
import ptit.cuonghq.walltag.models.requestmodels.FacebookLoginRequestBody;
import ptit.cuonghq.walltag.models.requestmodels.LoginRequestBody;
import ptit.cuonghq.walltag.models.requestmodels.RegisterRequestBody;
import ptit.cuonghq.walltag.models.requestmodels.UpdateInfoRequestBody;
import ptit.cuonghq.walltag.services.ProviderService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@Api(value = "auth", description = "User authentications APIs", produces = "application/json", tags = {"Authentication"})
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private ProviderService providerService;

    /****************************************************************************************************************************************/
    @GetMapping("/demo")
    public ResponseEntity<String> demo() {
        return new ResponseEntity<String>("You did it", HttpStatus.OK);
    }


    /*********User Login*****************************************************************************************************************/
    @PostMapping("/login")
    public ResponseEntity<ResponseObjectResult> login(@RequestBody LoginRequestBody requestBody) {
        return new ResponseEntity<ResponseObjectResult>(providerService.login(requestBody.getPhoneOrEmail(), requestBody.getPassword()), HttpStatus.OK);
    }

    /*********User Register**************************************************************************************************************/
    @PostMapping("/register")
    public ResponseEntity<ResponseObjectResult> register(@PathVariable("role") Optional<String> rolePath, @RequestBody RegisterRequestBody requestBody) {
        String role = "";
        role = rolePath.orElse("customer");

        ResponseObjectResult result = null;
        if (role.equalsIgnoreCase("customer") || role.equalsIgnoreCase("provider")) {
            result = providerService.register(requestBody, role);
        } else {
            result = new ResponseObjectResult(false, 400, "Role not valid", null);
        }
        if (result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }

    /*********User update****************************************************************************************************************/
    @PutMapping("/user")
    public ResponseEntity<ResponseObjectResult> updateInformation(@RequestHeader(value = "Authorization") int id, @RequestBody UpdateInfoRequestBody requestBody) {
        ResponseObjectResult result = providerService.update(id, requestBody);
        HttpStatus status = null;
        if (result.isSuccess()) {
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(result, status);
    }

    /*********User login by facebook account*********************************************************************************************/
    @PostMapping("/login_facebook")
    public ResponseEntity<ResponseObjectResult> loginByFacebook(@RequestBody FacebookLoginRequestBody requestBody) {
        ResponseObjectResult result = providerService.facebookLogin(requestBody.getAccessToken());
        HttpStatus status = null;
        if (result.isSuccess()) {
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(result, status);
    }

}
