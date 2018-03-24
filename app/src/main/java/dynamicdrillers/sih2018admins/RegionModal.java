package dynamicdrillers.sih2018admins;

/**
 * Created by Mayank on 24-03-2018.
 */

public class RegionModal {

    String authority;
    String image;

    public RegionModal() {
    }

    public RegionModal(String authority, String image) {
        this.authority = authority;
        this.image = image;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
