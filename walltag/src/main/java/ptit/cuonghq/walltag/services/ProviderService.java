package ptit.cuonghq.walltag.services;

import org.springframework.http.ResponseEntity;
import ptit.cuonghq.walltag.models.commons.ResponseFactory;
import ptit.cuonghq.walltag.models.commons.ResponseObjectResult;
import ptit.cuonghq.walltag.models.beans.User;
import ptit.cuonghq.walltag.models.repositories.ProviderRepository;
import ptit.cuonghq.walltag.models.requestmodels.RegisterRequestBody;
import ptit.cuonghq.walltag.models.requestmodels.UpdateInfoRequestBody;
import ptit.cuonghq.walltag.services.serviceinterface.UserServiceInterface;
import ptit.cuonghq.walltag.utils.CommonServices;
import ptit.cuonghq.walltag.utils.Validate;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;

@Service
public class ProviderService implements UserServiceInterface {

    @Autowired
    ProviderRepository repository;

    @Override
    public ResponseEntity<ResponseObjectResult> register(RegisterRequestBody requestBody, String role) {
        String phoneOrEmail = requestBody.getPhoneOrEmail();
        boolean isEmailAddress =  Validate.isValidatedEmail(phoneOrEmail);

        if (isEmailAddress) {
            User user = repository.findUserByEmail(phoneOrEmail);
            if (user != null) {
                return ResponseFactory.badRequest("This email is already registered with another account");
            }
        } else {
            User user = repository.findUserByPhone(phoneOrEmail);
            if (user != null) {
                return ResponseFactory.badRequest("This phone is already registered with another account");
            }
        }

        User user = null;
        try {
            User tmp = new User();
            tmp.setRole(role);
            tmp.setPassword(requestBody.getPassword());
            if (isEmailAddress) {
                tmp.setEmail(phoneOrEmail);
            } else {
                tmp.setPhone(phoneOrEmail);
            }
            user = repository.save(tmp);
        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> set = ex.getConstraintViolations();
            for (ConstraintViolation<?> violation : set) {
                String msg = violation.getPropertyPath().toString() + " " + violation.getMessage();
                return ResponseFactory.badRequest(msg);
            }
        }

        return ResponseFactory.created("Register success", user);
    }

    @Override
    public ResponseEntity<ResponseObjectResult> login(String phoneOrEmail, String password) {
        User user = repository.login(phoneOrEmail, password);
        if (user != null) {
            return ResponseFactory.ok("Login approved", user);
        } else {
            return ResponseFactory.badRequest("Username or password are not correct");
        }
    }

    @Override
    public ResponseEntity<ResponseObjectResult> facebookLogin(String facebookAccessToken) {
        JSONObject jsonObject = null;
        String url = "https://graph.facebook.com/v3.1/me?fields=gender%2Caddress%2Cbirthday%2Cpicture.type(large)%7Burl%7D%2Cfirst_name%2Clast_name%2Cemail&access_token=" + facebookAccessToken + "";
        try {
            jsonObject = CommonServices.requestGET(url, null);
            System.out.println(jsonObject);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (jsonObject == null) {
            return ResponseFactory.authorizationError();
        } else {
            String facebookId = jsonObject.getString("id");
            User prov = repository.findProviderByFacebookId(facebookId);
            if (prov == null) {
                prov = new User();
                prov.setEmail(jsonObject.getString("email"));
                prov.setFirstName(jsonObject.getString("first_name"));
                prov.setLastName(jsonObject.getString("last_name"));

                JSONObject imageContainer = jsonObject.getJSONObject("picture");
                JSONObject imageHolder = imageContainer.getJSONObject("data");
                prov.setImageUrl(imageHolder.getString("url"));

                prov.setIdFacebook(facebookId);

                System.out.println("ABCD: " + prov);
                repository.save(prov);
            }
            return ResponseFactory.ok("Login approved", prov);
        }
    }

    @Override
    public ResponseEntity<ResponseObjectResult> update(int id, UpdateInfoRequestBody requestBody) {
        User prov = null;
        prov = repository.findById(id).get();

        if (requestBody.getFirstName() != null && !requestBody.getFirstName().isEmpty()) {
            prov.setFirstName(requestBody.getFirstName());
        }

        if (requestBody.getLastName() != null && !requestBody.getLastName().isEmpty()) {
            prov.setLastName(requestBody.getLastName());
        }

        if (requestBody.getEmail() != null && !requestBody.getEmail().isEmpty()) {
            prov.setEmail(requestBody.getEmail());
        }

        if (requestBody.getPhone() != null && !requestBody.getPhone().isEmpty()) {
            prov.setPhone(requestBody.getPhone());
        }

        if (requestBody.getImageUrl() != null && !requestBody.getImageUrl().isEmpty()) {
            prov.setImageUrl(requestBody.getImageUrl());
        }

        if (requestBody.getBirthDate() != null && !requestBody.getBirthDate().isEmpty()) {
            prov.setBirthDate(requestBody.getBirthDate());
        }

        if (requestBody.getGender() != null && !requestBody.getGender().isEmpty()) {
            prov.setGender(requestBody.getGender());
        }

        prov = repository.save(prov);
        return ResponseFactory.ok("Update information successfully", prov);
    }

    @Override
    public User checkProvider(int idProvider) {
        Optional<User> optionalUser = repository.findById(idProvider);
        User user = optionalUser.orElse(null);
        if (user == null || !user.getRole().contains("provider"))
            return null;
        return user;
    }

    @Override
    public User checkCustomer(int idCustomer) {
        Optional<User> optionalUser = repository.findById(idCustomer);
        User user = optionalUser.orElse(null);
        if (user == null || !user.getRole().contains("customer"))
            return null;
        return user;
    }
}
