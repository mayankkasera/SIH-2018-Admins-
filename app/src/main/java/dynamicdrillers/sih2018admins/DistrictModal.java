package dynamicdrillers.sih2018admins;

/**
 * Created by Mayank on 18-03-2018.
 */

public class DistrictModal {


    String district;
    String image;
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DistrictModal(String district, String image, String name) {

        this.district = district;
        this.image = image;
        this.name = name;
    }

    public DistrictModal() {
    }


    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
