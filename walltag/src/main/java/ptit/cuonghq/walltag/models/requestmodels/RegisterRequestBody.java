package ptit.cuonghq.walltag.models.requestmodels;

public class RegisterRequestBody {

    private String password;

    private String emailOrPhone;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailOrPhone() {
        return emailOrPhone;
    }

    public void setEmailOrPhone(String emailOrPhone) {
        this.emailOrPhone = emailOrPhone;
    }

}
