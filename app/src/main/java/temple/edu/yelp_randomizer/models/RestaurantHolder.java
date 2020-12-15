package temple.edu.yelp_randomizer.models;

import android.os.Parcel;
import android.os.Parcelable;

public class RestaurantHolder implements Parcelable {
    private String id; // not accessible to user
    private String name; // name
    private String phone; // phone
    private String image; // image_url
    private String location; // location has a JSON array

    public RestaurantHolder(String id, String name, String phone, String image, String location) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.image = image;
        this.location = location;
    }

    protected RestaurantHolder(Parcel in) {
        id = in.readString();
        name = in.readString();
        phone = in.readString();
        image = in.readString();
        location = in.readString();
    }

    public static final Creator<RestaurantHolder> CREATOR = new Creator<RestaurantHolder>() {
        @Override
        public RestaurantHolder createFromParcel(Parcel in) {
            return new RestaurantHolder(in);
        }

        @Override
        public RestaurantHolder[] newArray(int size) {
            return new RestaurantHolder[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(phone);
        parcel.writeString(image);
        parcel.writeString(location);
    }
}
