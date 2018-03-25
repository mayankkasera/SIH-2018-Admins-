package dynamicdrillers.sih2018admins;

/**
 * Created by Mayank on 24-03-2018.
 */

public class RegionModal {

    String region;
    String image;
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RegionModal(String region, String image, String name) {

        this.region = region;
        this.image = image;
        this.name = name;
    }

    public RegionModal() {
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
