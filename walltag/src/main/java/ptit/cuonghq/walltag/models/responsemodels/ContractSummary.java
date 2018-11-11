package ptit.cuonghq.walltag.models.responsemodels;

import ptit.cuonghq.walltag.models.CurrencyModel;

public class ContractSummary {

    private int id;

    private int idCustomer;

    private int idPlace;

    private String customerName;

    private String address;

    private long price;

    private int size;

    private String imageUrl;

    public ContractSummary(int id, int idCustomer, int idPlace, String customerName, String address, long price, int size, String imageUrl) {
        this.id = id;
        this.idCustomer = idCustomer;
        this.idPlace = idPlace;
        this.customerName = customerName;
        this.address = address;
        this.price = price;
        this.size = size;
        this.imageUrl = imageUrl;
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

    public int getIdPlace() {
        return idPlace;
    }

    public void setIdPlace(int idPlace) {
        this.idPlace = idPlace;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public CurrencyModel getPrice() {
        return CurrencyModel.VND(price);
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
