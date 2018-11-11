package ptit.cuonghq.walltag.models.responsemodels;

import ptit.cuonghq.walltag.models.CurrencyModel;

public class PlaceSearch {

    private int id;

    private String address;

    private double distance;

    private String imageUrl;

    private double latitude;

    private double longitude;

    private int width;

    private int height;

    private long price;

    private String wallType;

    private String posterType;

    private String status;

    private int daysTilAvailable;

    public PlaceSearch(int id, String address, double distance, String imageUrl, double latitude, double longitude, int width, int height, long price, String wallType, String posterType, String status, int daysTilAvailable) {
        this.id = id;
        this.address = address;
        this.distance = distance;
        this.imageUrl = imageUrl;
        this.latitude = latitude;
        this.longitude = longitude;
        this.width = width;
        this.height = height;
        this.price = price;
        this.wallType = wallType;
        this.posterType = posterType;
        this.status = status;
        this.daysTilAvailable = daysTilAvailable;
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

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public double getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public CurrencyModel getPrice() {
        return CurrencyModel.VND(price);
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getWallType() {
        return wallType;
    }

    public void setWallType(String wallType) {
        this.wallType = wallType;
    }

    public String getPosterType() {
        return posterType;
    }

    public void setPosterType(String posterType) {
        this.posterType = posterType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getDaysTilAvailable() {
        return daysTilAvailable;
    }

    public void setDaysTilAvailable(int daysTilAvailable) {
        this.daysTilAvailable = daysTilAvailable;
    }
}
