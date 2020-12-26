package temple.edu.yelp_randomizer.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ReviewsModel implements Parcelable {

    int rating;
    String text;
    String ProfileImageURL;
    String userName;

    public ReviewsModel(int rating, String text, String profileImageURL, String userName) {
        this.rating = rating;
        this.text = text;
        ProfileImageURL = profileImageURL;
        this.userName = userName;
    }

    protected ReviewsModel(Parcel in) {
        rating = in.readInt();
        text = in.readString();
        ProfileImageURL = in.readString();
        userName = in.readString();
    }

    public static final Creator<ReviewsModel> CREATOR = new Creator<ReviewsModel>() {
        @Override
        public ReviewsModel createFromParcel(Parcel in) {
            return new ReviewsModel(in);
        }

        @Override
        public ReviewsModel[] newArray(int size) {
            return new ReviewsModel[size];
        }
    };

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getProfileImageURL() {
        return ProfileImageURL;
    }

    public void setProfileImageURL(String profileImageURL) {
        ProfileImageURL = profileImageURL;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(rating);
        parcel.writeString(text);
        parcel.writeString(ProfileImageURL);
        parcel.writeString(userName);
    }
}
