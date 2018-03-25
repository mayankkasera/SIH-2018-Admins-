package dynamicdrillers.sih2018admins;

/**
 * Created by Mayank on 18-03-2018.
 */

public class AdminsModal {

    String image;
    String state;
    String name;

    public AdminsModal(String image, String state, String name) {
        this.image = image;
        this.state = state;
        this.name = name;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
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
