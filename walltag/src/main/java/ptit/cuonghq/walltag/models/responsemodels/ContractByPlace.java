package ptit.cuonghq.walltag.models.responsemodels;

import ptit.cuonghq.walltag.models.commons.CurrencyModel;

public class ContractByPlace {

    private int id;

    private String dateStart;

    private String dateEnd;

    private String posterUrl;

    private String note;

    private String status;

    private int constructionPrice;

    private int rentingPrice;

    private UserSummary user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public CurrencyModel getConstructionPrice() {
        return CurrencyModel.VND(constructionPrice);
    }

    public void setConstructionPrice(int constructionPrice) {
        this.constructionPrice = constructionPrice;
    }

    public CurrencyModel getRentingPrice() {
        return CurrencyModel.VND(rentingPrice);
    }

    public void setRentingPrice(int rentingPrice) {
        this.rentingPrice = rentingPrice;
    }

    public UserSummary getUser() {
        return user;
    }

    public void setUser(UserSummary user) {
        this.user = user;
    }

    public static class UserSummary{

        private int id;

        private String name;

        private String email;

        private String phone;

        private String imageUrl;

        public UserSummary(int id, String name, String email, String phone, String imageUrl) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.phone = phone;
            this.imageUrl = imageUrl;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }
}
