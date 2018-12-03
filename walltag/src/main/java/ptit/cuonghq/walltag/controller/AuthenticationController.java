package ptit.cuonghq.walltag.controller;

import ptit.cuonghq.walltag.models.commons.ResponseFactory;
import ptit.cuonghq.walltag.models.commons.ResponseObjectResult;
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
        return providerService.login(requestBody.getPhoneOrEmail(), requestBody.getPassword());
    }

    /*********User Register**************************************************************************************************************/
    @PostMapping("/register")
    public ResponseEntity<ResponseObjectResult> register(@PathVariable("role") Optional<String> rolePath, @RequestBody RegisterRequestBody requestBody) {
        String role;
        role = rolePath.orElse("customer");

        ResponseObjectResult result = null;
        if (role.equalsIgnoreCase("customer") || role.equalsIgnoreCase("provider")) {
            return providerService.register(requestBody, role);
        } else {
            return ResponseFactory.badRequest("Role not valid");
        }
    }

    /*********User update****************************************************************************************************************/
    @PutMapping("/user")
    public ResponseEntity<ResponseObjectResult> updateInformation(@RequestHeader(value = "Authorization") int id, @RequestBody UpdateInfoRequestBody requestBody) {
        return providerService.update(id, requestBody);
    }

    /*********User login by facebook account*********************************************************************************************/
    @PostMapping("/login_facebook")
    public ResponseEntity<ResponseObjectResult> loginByFacebook(@RequestBody FacebookLoginRequestBody requestBody) {
        return providerService.facebookLogin(requestBody.getAccessToken());
    }

}
