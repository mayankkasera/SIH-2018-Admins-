package dynamicdrillers.sih2018admins;

/**
 * Created by Mayank on 19-03-2018.
 */

public class AuthorityModal {
    String authority;
    String image;
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AuthorityModal(String authority, String image, String name) {
        this.authority = authority;
        this.image = image;
        this.name = name;
    }

    public AuthorityModal() {
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
