package ptit.cuonghq.walltag.models.requestmodels;

public class UpdateContractStatusRequestBody {

    private String rejectReason;

    private String readyImageUrl;


    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getReadyImageUrl() {
        return readyImageUrl;
    }

    public void setReadyImageUrl(String readyImageUrl) {
        this.readyImageUrl = readyImageUrl;
    }
}
