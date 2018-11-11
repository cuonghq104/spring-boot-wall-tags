package ptit.cuonghq.walltag.models.requestmodels;

public class UpdatePlaceRequestBody {

    private String address;

    private String description;

    private long price;

    private String imageUrl;

    private int idWallType;

    private int idPosterType;

    public int getIdWallType() {
        return idWallType;
    }

    public void setIdWallType(int idWallType) {
        this.idWallType = idWallType;
    }

    public int getIdPosterType() {
        return idPosterType;
    }

    public void setIdPosterType(int idPosterType) {
        this.idPosterType = idPosterType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
