package ptit.cuonghq.walltag.services;

import ptit.cuonghq.walltag.models.ResponseObjectResult;
import ptit.cuonghq.walltag.models.beans.User;
import ptit.cuonghq.walltag.models.repositories.ProviderRepository;
import ptit.cuonghq.walltag.models.requestmodels.RegisterRequestBody;
import ptit.cuonghq.walltag.models.requestmodels.UpdateInfoRequestBody;
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
public class ProviderService {

    @Autowired
    ProviderRepository repository;

    public ResponseObjectResult register(RegisterRequestBody requestBody, String role) {
        String emailOrPhone = requestBody.getEmailOrPhone();
        boolean isEmailAddress =  Validate.isValidatedEmail(emailOrPhone);

        if (isEmailAddress) {
            User user = repository.findUserByEmail(emailOrPhone);
            if (user != null) {
                return new ResponseObjectResult(false, 400, "This email is already registered with another account", null);
            }
        } else {
            User user = repository.findUserByPhone(emailOrPhone);
            if (user != null) {
                return new ResponseObjectResult(false, 400, "This phone is already registered with another account", null);
            }
        }

        User user = null;
        try {
            User tmp = new User();
            tmp.setRole(role);
            tmp.setPassword(requestBody.getPassword());
            if (isEmailAddress) {
                tmp.setEmail(emailOrPhone);
            } else {
                tmp.setPhone(emailOrPhone);
            }
            user = repository.save(tmp);
        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> set = ex.getConstraintViolations();
            for (ConstraintViolation<?> violation : set) {
                String msg = violation.getPropertyPath().toString() + " " + violation.getMessage();
                return new ResponseObjectResult(false, 400, msg, null);
            }
        }

        return new ResponseObjectResult(true, 201, "Register success", user);
    }

    public ResponseObjectResult login(String phoneOrEmail, String password) {
        ResponseObjectResult result = null;
        User user = repository.login(phoneOrEmail, password);
        if (user != null) {
            result = new ResponseObjectResult(true, 200, "Login approved", user);
        } else {
            result = new ResponseObjectResult(false, 401, "Username or password are not correct", null);
        }
        return result;
    }

    public ResponseObjectResult facebookLogin(String facebookAccessToken) {
        JSONObject jsonObject = null;
        String url = "https://graph.facebook.com/v3.1/me?fields=gender%2Caddress%2Cbirthday%2Cpicture.type(large)%7Burl%7D%2Cfirst_name%2Clast_name%2Cemail&access_token=" + facebookAccessToken + "";
        try {
            jsonObject = CommonServices.requestGET(url, null);
            System.out.println(jsonObject);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (jsonObject == null) {
            return new ResponseObjectResult(false, 401, "Facebook token doesn't correct", null);
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
            return new ResponseObjectResult(true, 200, "Login approved", prov);
        }
    }

    public ResponseObjectResult update(int id, UpdateInfoRequestBody requestBody) {
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
        return new ResponseObjectResult(true, 200, "Update information successfully", prov);
    }

    public User checkUser(int idUser) {
        Optional<User> optionalUser = repository.findById(idUser);
        return optionalUser.orElse(null);
    }
}
