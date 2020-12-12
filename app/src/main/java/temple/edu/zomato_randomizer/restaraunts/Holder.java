package temple.edu.zomato_randomizer.restaraunts;

import java.util.ArrayList;

public class Holder {
    private String id; // not accessible to user
    private String name; // name
    private String phone; // phone
    private String image; // image_url
    private String location; // location has a JSON array

    public Holder(String id, String name, String phone, String image, String location) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.image = image;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
