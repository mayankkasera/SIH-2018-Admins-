package dynamicdrillers.sih2018admins;

/**
 * Created by Mayank on 25-03-2018.
 */

public class ComplaintDesModal {

    String image;
    String type;

    public ComplaintDesModal() {
    }

    public ComplaintDesModal(String image, String type) {
        this.image = image;
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
