package ptit.cuonghq.walltag.models.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import ptit.cuonghq.walltag.models.commons.CurrencyModel;
import ptit.cuonghq.walltag.models.responsemodels.PlaceSearch;
import ptit.cuonghq.walltag.models.responsemodels.PlaceSummary;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SqlResultSetMappings({
        @SqlResultSetMapping(
                name = "PlaceResult",
                classes = {
                        @ConstructorResult(
                                targetClass = PlaceSummary.class,
                                columns = {
                                        @ColumnResult(name = "id", type = int.class),
                                        @ColumnResult(name = "address", type = String.class),
                                        @ColumnResult(name = "imageUrl", type = String.class),
                                        @ColumnResult(name = "description", type = String.class),
                                        @ColumnResult(name = "price", type = long.class),
                                }
                        )
                }
        ),
        @SqlResultSetMapping(
                name = "SearchResult",
                classes = {
                        @ConstructorResult(
                                targetClass = PlaceSearch.class,
                                columns = {
                                        @ColumnResult(name = "id", type = int.class),
                                        @ColumnResult(name = "address", type = String.class),
                                        @ColumnResult(name = "distance", type = double.class),
                                        @ColumnResult(name = "image_url", type = String.class),
                                        @ColumnResult(name = "latitude", type = double.class),
                                        @ColumnResult(name = "longitude", type = double.class),
                                        @ColumnResult(name = "width", type = int.class),
                                        @ColumnResult(name = "height", type = int.class),
                                        @ColumnResult(name = "price", type = long.class),
                                        @ColumnResult(name = "poster_type", type = String.class),
                                        @ColumnResult(name = "wall_type", type = String.class),
                                        @ColumnResult(name = "status", type = String.class),
                                        @ColumnResult(name = "date_til_available", type = int.class),
                                }
                        )
                }
        )

})

@NamedNativeQueries({
        @NamedNativeQuery(name = "Place.findPlaceByProviderId",
                query = "SELECT id, address, image_url as imageUrl, description, price FROM tbl_place WHERE id_provider = :id_provider",
                resultSetMapping = "PlaceResult"
        ),
        @NamedNativeQuery(name = "Place.searchPlace",
                query = "CALL search_place(:lat, :lng, :distance, :min_width, :max_width, :min_height, :max_height, :min_price, :max_price, :wall_type, :poster_type)",
                resultSetMapping = "SearchResult"
        )
})

@Entity
@Table(name = "tbl_place")
public class Place implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    @Column(name = "address")
    @NotNull
    private String address;

    @Column(name = "image_url")
    @NotNull
    private String imageUrl;

    @Column(name = "latitude")
    @NotNull
    private double latitude;

    @Column(name = "longitude")
    @NotNull
    private double longitude;

    @Column(name = "description")
    private String description;

    @Column(name = "width")
    private float width;

    @Column(name = "height")
    private float height;

    @Column(name = "price")
    private long price;

    @Column(name = "construction_price")
    private long constructionPrice;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "tbl_place_wall", joinColumns = {@JoinColumn(name = "id_place")}, inverseJoinColumns = {@JoinColumn(name = "id_wall_type")})
    private Set<WallType> wallType = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "tbl_place_poster", joinColumns = {@JoinColumn(name = "id_place")}, inverseJoinColumns = {@JoinColumn(name = "id_poster")})
    private Set<PosterType> posterType = new HashSet<>();

    @JoinColumn(name = "id_provider", referencedColumnName = "id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ManyToOne(optional = false)
    private User user;

    @Column(name = "date_created")
    private Timestamp dateCreated;

    @Transient
    private List<Image> additionalImages;

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

    public CurrencyModel getPrice() {
        return CurrencyModel.VND(price);
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public Set<WallType> getWallType() {
        return wallType;
    }

    public void setWallType(Set<WallType> wallType) {
        this.wallType = wallType;
    }

    public Set<PosterType> getPosterType() {
        return posterType;
    }

    public void setPosterType(Set<PosterType> posterType) {
        this.posterType = posterType;
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

    public String getDateCreated() {
//        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        return sdf1.format(dateCreated);

        return dateCreated.toString();
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    @JsonIgnore
    @JsonProperty(value = "id_provider")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getConstructionPrice() {
        return constructionPrice;
    }

    public void setConstructionPrice(long constructionPrice) {
        this.constructionPrice = constructionPrice;
    }

    public List<Image> getAdditionalImages() {
        return additionalImages;
    }

    public void setAdditionalImages(List<Image> additionalImages) {
        this.additionalImages = additionalImages;
    }
}
