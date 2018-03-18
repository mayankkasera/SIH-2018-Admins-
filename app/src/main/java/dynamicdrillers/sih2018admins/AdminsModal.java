package dynamicdrillers.sih2018admins;

/**
 * Created by Mayank on 18-03-2018.
 */

public class AdminsModal {

    String image;
    String state;

    public AdminsModal(String image, String state) {
        this.image = image;
        this.state = state;
    }

    public AdminsModal() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
