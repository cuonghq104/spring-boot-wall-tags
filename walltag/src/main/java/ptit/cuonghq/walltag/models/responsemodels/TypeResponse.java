package ptit.cuonghq.walltag.models.responsemodels;

import ptit.cuonghq.walltag.models.beans.PosterType;
import ptit.cuonghq.walltag.models.beans.WallType;

import java.util.List;

public class TypeResponse {

    List<WallType> wallTypes;

    List<PosterType> posterTypes;

    public TypeResponse(List<WallType> wallTypes, List<PosterType> posterTypes) {
        this.wallTypes = wallTypes;
        this.posterTypes = posterTypes;
    }

    public List<WallType> getWallTypes() {
        return wallTypes;
    }

    public void setWallTypes(List<WallType> wallTypes) {
        this.wallTypes = wallTypes;
    }

    public List<PosterType> getPosterTypes() {
        return posterTypes;
    }

    public void setPosterTypes(List<PosterType> posterTypes) {
        this.posterTypes = posterTypes;
    }
}
