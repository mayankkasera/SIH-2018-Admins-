package dynamicdrillers.sih2018admins;

/**
 * Created by Mayank on 18-03-2018.
 */

public class DistrictModal {


    String district;
    String image;



    public DistrictModal() {
    }

    public DistrictModal(String district, String image) {
        this.district = district;
        this.image = image;
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
