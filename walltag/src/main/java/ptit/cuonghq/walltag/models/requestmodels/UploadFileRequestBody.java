package ptit.cuonghq.walltag.models.requestmodels;

public class UploadFileRequestBody {

    private String data;

    private String type;

    private String ext;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }
}
