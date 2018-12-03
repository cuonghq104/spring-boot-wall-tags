package ptit.cuonghq.walltag.models.responsemodels;

import ptit.cuonghq.walltag.models.commons.CurrencyModel;

public class PlaceSummary {

    private int id;

    private String address;

    private String description;

    private String imageUrl;

    private long price;

    private String status;

    public PlaceSummary(int id, String address, String imageUrl, String description, long price) {
        this.id = id;
        this.address = address;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
        status = "Available";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CurrencyModel getPrice() {
        return CurrencyModel.VND(price);
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
