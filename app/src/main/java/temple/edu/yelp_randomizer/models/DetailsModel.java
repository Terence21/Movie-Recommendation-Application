package temple.edu.yelp_randomizer.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.HashMap;

public class DetailsModel implements Parcelable {
    private String _1_imageURL;
    private String _2_imageURL;
    private String _3_imageURL;
    private String sundayText;
    private String mondayText;
    private String tuesdayText;
    private String wednesdayText;
    private String thursdayText;
    private String fridayText;
    private String saturdayText;

    public DetailsModel() {
    }

    protected DetailsModel(Parcel in) {
        _1_imageURL = in.readString();
        _2_imageURL = in.readString();
        _3_imageURL = in.readString();
        sundayText = in.readString();
        mondayText = in.readString();
        tuesdayText = in.readString();
        wednesdayText = in.readString();
        thursdayText = in.readString();
        fridayText = in.readString();
        saturdayText = in.readString();
    }

    public static final Creator<DetailsModel> CREATOR = new Creator<DetailsModel>() {
        @Override
        public DetailsModel createFromParcel(Parcel in) {
            return new DetailsModel(in);
        }

        @Override
        public DetailsModel[] newArray(int size) {
            return new DetailsModel[size];
        }
    };

    public String get_1_imageURL() {
        return _1_imageURL;
    }

    public void set_1_imageURL(String imageURL) {
        if (imageURL != null){
            this._1_imageURL = imageURL;
        }

    }

    public String get_2_imageURL() {
        return _2_imageURL;
    }

    public void set_2_imageURL(String imageURL) {
        if (imageURL != null){
            this._2_imageURL = imageURL;
        }
    }

    public String get_3_imageURL() {
        return _3_imageURL;
    }

    public void set_3_imageURL(String imageURL) {
        if (imageURL != null){
            this._3_imageURL = imageURL;
        }
    }

    public void setSundayText(HashMap<String, String> hours) {
        String text = "SUNDAY\n\n";
        String start = hours.get("start");
        String end = hours.get("end");
        if (start != null && end != null){
            text += "open: " + formatTime(start) + "\n";
            text += "close: " + formatTime(end);
        }
        else{
            text += "open: N/A\nclose: N/A";
        }
        sundayText = text;
    }

    public String getSundayText(){
        return this.sundayText;
    }

    public String getMondayText() {
        return mondayText;
    }

    public void setMondayText(HashMap<String, String> hours) {
        String text = "MONDAY\n\n";
        String start = hours.get("start");
        String end = hours.get("end");
        if (start != null && end != null){
            text += "open: " + formatTime(start) + "\n";
            text += "close: " + formatTime(end);
        }
        else{
            text += "open: N/A\nclose: N/A";
        }
        mondayText = text;
    }

    public String getTuesdayText() {
        return tuesdayText;
    }

    public void setTuesdayText(HashMap<String, String> hours) {
        String text = "TUESDAY\n\n";
        String start = hours.get("start");
        String end = hours.get("end");
        if (start != null && end != null){
            text += "open: " + formatTime(start) + "\n";
            text += "close: " + formatTime(end);
        }
        else{
            text += "open: N/A\nclose: N/A";
        }
        tuesdayText = text;
    }

    public String getWednesdayText() {
        return wednesdayText;
    }

    public void setWednesdayText(HashMap<String, String> hours) {
        String text = "WEDNESDAY\n\n";
        String start = hours.get("start");
        String end = hours.get("end");
        if (start != null && end != null){
            text += "open: " + formatTime(start) + "\n";
            text += "close: " + formatTime(end);
        }
        else{
            text += "open: N/A\nclose: N/A";
        }
        wednesdayText = text;
    }

    public String getThursdayText() {
        return thursdayText;
    }

    public void setThursdayText(HashMap<String, String> hours) {
        String text = "THURSDAY\n\n";
        String start = hours.get("start");
        String end = hours.get("end");
        if (start != null && end != null){
            text += "open: " + formatTime(start) + "\n";
            text += "close: " + formatTime(end);
        }
        else{
            text += "open: N/A\nclose: N/A";
        }
        thursdayText = text;
    }

    public String getFridayText() {
        return fridayText;
    }

    public void setFridayText(HashMap<String, String> hours) {
        String text = "FRIDAY\n\n";
        String start = hours.get("start");
        String end = hours.get("end");
        if (start != null && end != null){
            text += "open: " + formatTime(start) + "\n";
            text += "close: " + formatTime(end);
        }
        else{
            text += "open: N/A\nclose: N/A";
        }
        fridayText = text;
    }

    public String getSaturdayText() {
        return saturdayText;
    }

    public void setSaturdayText(HashMap<String, String> hours) {
        String text = "SATURDAY\n\n";
        String start = hours.get("start");
        String end = hours.get("end");
        if (start != null && end != null){
            text += "open: " + formatTime(start) + "\n";
            text += "close: " + formatTime(end);
        }
        else{
            text += "open: N/A\nclose: N/A";
        }
        saturdayText = text;
    }

    private String formatTime(String time) {
        String fixedTime = "";
        int minutes_int = 60 - Integer.parseInt(time.substring(2, 3));
        String minutes = "";
        if (minutes_int == 60){
            minutes = "00";
        }else{
            minutes = String.valueOf(minutes_int);
        }

        if (time.length() == 4) {


            if (Integer.parseInt(time.substring(0, 2)) >= 12) {
                int hours = Integer.parseInt(time.substring(0, 2)) - 12;
                if (hours == 0){
                    fixedTime = "12:";
                } else {
                    fixedTime = hours + ":";
                }
                fixedTime += minutes + " pm";

            } else {
                int hours = Integer.parseInt(time.substring(0,2));
                fixedTime = hours + ":" + minutes + "am";

            }
            Log.i("time", "formatTime: " + fixedTime);
            return fixedTime;
        } else if (time.length() == 3) {
            fixedTime = Integer.parseInt(time.substring(0, 1)) + minutes + "am";
            return fixedTime;
        }

        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(_1_imageURL);
        parcel.writeString(_2_imageURL);
        parcel.writeString(_3_imageURL);
        parcel.writeString(sundayText);
        parcel.writeString(mondayText);
        parcel.writeString(tuesdayText);
        parcel.writeString(wednesdayText);
        parcel.writeString(thursdayText);
        parcel.writeString(fridayText);
        parcel.writeString(saturdayText);
    }
}
