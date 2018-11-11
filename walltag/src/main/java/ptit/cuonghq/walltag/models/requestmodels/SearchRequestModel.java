package ptit.cuonghq.walltag.models.requestmodels;

public class SearchRequestModel {

    private double lat;

    private double lng;

    private double distance;

    private int minWidth;

    private int maxWidth;

    private int minHeight;

    private int maxHeight;

    private long minPrice;

    private long maxPrice;

    private String idPoster;

    private String idWall;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getMinWidth() {
        return minWidth;
    }

    public void setMinWidth(int minWidth) {
        this.minWidth = minWidth;
    }

    public int getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
    }

    public int getMinHeight() {
        return minHeight;
    }

    public void setMinHeight(int minHeight) {
        this.minHeight = minHeight;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    public long getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(long minPrice) {
        this.minPrice = minPrice;
    }

    public long getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(long maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getIdPoster() {
        return idPoster;
    }

    public void setIdPoster(String idPoster) {
        this.idPoster = idPoster;
    }

    public String getIdWall() {
        return idWall;
    }

    public void setIdWall(String idWall) {
        this.idWall = idWall;
    }
}
