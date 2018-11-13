package ptit.cuonghq.walltag.models.responsemodels;


import java.sql.Date;

public class ContractByPlaceResponseModel {

    private int id;

    private int idCustomer;

    private String dateStart;

    private String dateEnd;

    private String posterUrl;

    private String note;

    private String status;

    private int constructionPrice;

    private int rentingPrice;

    private String customerFirstName;

    private String customerLastName;

    private String customerEmail;

    private String customerPhone;

    private String customerImageUrl;

    public ContractByPlaceResponseModel(int id, int idCustomer, String dateStart, String dateEnd, String posterUrl, String note, String status, int constructionPrice, int rentingPrice, String customerFirstName, String customerLastName, String customerEmail, String customerPhone, String customerImageUrl) {
        this.id = id;
        this.idCustomer = idCustomer;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.posterUrl = posterUrl;
        this.note = note;
        this.status = status;
        this.constructionPrice = constructionPrice;
        this.rentingPrice = rentingPrice;
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.customerEmail = customerEmail;
        this.customerPhone = customerPhone;
        this.customerImageUrl = customerImageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getConstructionPrice() {
        return constructionPrice;
    }

    public void setConstructionPrice(int constructionPrice) {
        this.constructionPrice = constructionPrice;
    }

    public int getRentingPrice() {
        return rentingPrice;
    }

    public void setRentingPrice(int rentingPrice) {
        this.rentingPrice = rentingPrice;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerImageUrl() {
        return customerImageUrl;
    }

    public void setCustomerImageUrl(String customerImageUrl) {
        this.customerImageUrl = customerImageUrl;
    }
}
