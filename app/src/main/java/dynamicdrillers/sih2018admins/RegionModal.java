package dynamicdrillers.sih2018admins;

/**
 * Created by Mayank on 24-03-2018.
 */

public class RegionModal {

    String region;
    String image;

    public RegionModal() {
    }

    public RegionModal(String region, String image) {
        this.region = region;
        this.image = image;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
