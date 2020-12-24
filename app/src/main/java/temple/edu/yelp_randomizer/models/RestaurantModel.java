package temple.edu.yelp_randomizer.models;

import android.os.Parcel;
import android.os.Parcelable;

public class RestaurantModel implements Parcelable {
    private int rating;
    private String id; // not accessible to user
    private String name; // name
    private String phone; // phone
    private String image; // image_url
    private String location; // location has a JSON array
    private String url;

    public RestaurantModel(int rating, String id, String name, String phone, String image, String location, String url) {
        this.rating = rating;
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.image = image;
        this.location = location;
        this.url = url;
    }

    protected RestaurantModel(Parcel in) {
        id = in.readString();
        name = in.readString();
        phone = in.readString();
        image = in.readString();
        location = in.readString();
        url = in.readString();
    }

    public static final Creator<RestaurantModel> CREATOR = new Creator<RestaurantModel>() {
        @Override
        public RestaurantModel createFromParcel(Parcel in) {
            return new RestaurantModel(in);
        }

        @Override
        public RestaurantModel[] newArray(int size) {
            return new RestaurantModel[size];
        }
    };

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
        parcel.writeString(url);
    }
}
