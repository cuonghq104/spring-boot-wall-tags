package ptit.cuonghq.walltag.models.requestmodels;

public class CreateNewPlaceRequestBody {

    private String address;

    private double latitude;

    private double longitude;

    private String description;

    private String imageUrl;

    private long price;

    private String wallTypes;

    private String posterTypes;

    private float width;

    private float height;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getWallTypes() {
        return wallTypes;
    }

    public void setWallTypes(String wallTypes) {
        this.wallTypes = wallTypes;
    }

    public String getPosterTypes() {
        return posterTypes;
    }

    public void setPosterType(String posterTypes) {
        this.posterTypes = posterTypes;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
