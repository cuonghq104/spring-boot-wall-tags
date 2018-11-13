package ptit.cuonghq.walltag.models.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ptit.cuonghq.walltag.models.CurrencyModel;
import ptit.cuonghq.walltag.models.responsemodels.ContractByPlaceResponseModel;
import ptit.cuonghq.walltag.models.responsemodels.ContractSummary;
import org.joda.time.LocalDate;
import org.joda.time.Months;

import javax.persistence.*;
import java.sql.Date;
import java.text.SimpleDateFormat;


//public ContractByPlaceResponseModel(int id, int idCustomer, Date dateStart, Date dateEnd, String posterUrl, String note, String status, int constructionPrice, int rentingPrice, String customerFirstName, String customerLastName, String customerEmail, String customerPhone, String customerImageUrl)
@SqlResultSetMappings({
        @SqlResultSetMapping(
                name = "ContractSummaryResult",
                classes = {
                        @ConstructorResult(
                                targetClass = ContractSummary.class,
                                columns = {
                                        @ColumnResult(name = "id", type = int.class),
                                        @ColumnResult(name = "id_customer", type = int.class),
                                        @ColumnResult(name = "id_place", type = int.class),
                                        @ColumnResult(name = "customer_name", type = String.class),
                                        @ColumnResult(name = "address", type = String.class),
                                        @ColumnResult(name = "price", type = long.class),
                                        @ColumnResult(name = "size", type = int.class),
                                        @ColumnResult(name = "image_url", type = String.class),
                                }
                        )
                }
        ),
//        public ContractByPlaceResponseModel(int id, int idCustomer, Date dateStart, Date dateEnd, String posterUrl, String note, String status, int constructionPrice, int rentingPrice, String customerFirstName, String customerLastName, String customerEmail, String customerPhone, String customerImageUrl) {
@SqlResultSetMapping(
                name = "ContractByPlaceResult",
                classes = {
                        @ConstructorResult(
                                targetClass = ContractByPlaceResponseModel.class,
                                columns = {
                                        @ColumnResult(name = "id", type = int.class),
                                        @ColumnResult(name = "id_customer", type = int.class),
                                        @ColumnResult(name = "date_start", type = String.class),
                                        @ColumnResult(name = "date_end", type = String.class),
                                        @ColumnResult(name = "poster_url", type = String.class),
                                        @ColumnResult(name = "note", type = String.class),
                                        @ColumnResult(name = "status", type = String.class),
                                        @ColumnResult(name = "construction_price", type = int.class),
                                        @ColumnResult(name = "renting_price", type = int.class),
                                        @ColumnResult(name = "first_name", type = String.class),
                                        @ColumnResult(name = "last_name", type = String.class),
                                        @ColumnResult(name = "email", type = String.class),
                                        @ColumnResult(name = "phone", type = String.class),
                                        @ColumnResult(name = "image_url", type = String.class)
                                }
                        )
                }
        )
})
@NamedNativeQueries({
        @NamedNativeQuery(name = "Contract.getWaitingContracts",
                query = "CALL get_waiting_contract(:id_provider)",
                resultSetMapping = "ContractSummaryResult"),

        @NamedNativeQuery(name = "Contract.getContractByPlaceId",
                query = "CALL get_contract_by_place(:id_place)",
                resultSetMapping = "ContractByPlaceResult")
})
@Entity
@Table(name = "tbl_contract")
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @JoinColumn(name = "id_customer", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User customer;

    @JoinColumn(name = "id_place", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Place place;

    @Column(name = "date_start")
    private Date dateStart;

    @Column(name = "date_end")
    private Date dateEnd;

    @Column(name = "poster_url")
    private String posterUrl;

    @Column(name = "note")
    private String note;

    @Column(name = "status")
    private String status;

    @Column(name = "construction_price")
    private long constructionPrice;

    @Column(name = "renting_price")
    private long rentingPrice;

    @Transient
    private int monthRented;

    @Transient
    private long totalPrice;

    @Transient
    private final int percentage = 40;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public String getDateStart() {
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
        return sdf1.format(dateStart);
    }

    @JsonIgnore
    public Date getDateStartValue() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
        return sdf1.format(dateEnd);
    }

    @JsonIgnore
    public Date getDateEndValue() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
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

    public CurrencyModel getConstructionPrice() {
        return CurrencyModel.VND(constructionPrice);
    }

    public void setConstructionPrice(long constructionPrice) {
        this.constructionPrice = constructionPrice;
    }

    public CurrencyModel getRentingPrice() {
        return CurrencyModel.VND(rentingPrice);
    }

    public void setRentingPrice(long rentingPrice) {
        this.rentingPrice = rentingPrice;
    }

    @JsonIgnore
    public long getRentingPriceValue() {
        return rentingPrice;
    }

    @JsonIgnore
    public long getConstructionPriceValue() {
        return constructionPrice;
    }

    public int getMonthRented() {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        LocalDate dateBefore = LocalDate.parse(sdf1.format(getDateStartValue()));
        LocalDate dateAfter = LocalDate.parse(sdf1.format(getDateEndValue()));

        return Months.monthsBetween(dateBefore, dateAfter).getMonths();
    }

    public void setMonthRented(int monthRented) {
        this.monthRented = monthRented;
    }

    public CurrencyModel getTotalPrice() {
        return CurrencyModel.VND((getMonthRented() * getRentingPriceValue()) + getConstructionPriceValue());
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public CurrencyModel getDepositAmount() {
        return CurrencyModel.VND(((getMonthRented() * getRentingPriceValue()) + getConstructionPriceValue()) * percentage / 100);
    }

    public String getPercentage() {
        return percentage + "%";
    }
}

