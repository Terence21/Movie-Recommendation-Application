package temple.edu.random.models

import android.os.Parcelable
import android.os.Parcel
import android.os.Parcelable.Creator
import android.util.Log
import java.util.HashMap

class DetailsModel : Parcelable {
    var _1_imageURL: String? = null
    var _2_imageURL: String? = null
    var _3_imageURL: String? = null
    var sundayText: String? = null
        private set
    var mondayText: String? = null
        private set
    var tuesdayText: String? = null
        private set
    var wednesdayText: String? = null
        private set
    var thursdayText: String? = null
        private set
    var fridayText: String? = null
        private set
    var saturdayText: String? = null
        private set

    constructor() {}
    protected constructor(`in`: Parcel) {
        _1_imageURL = `in`.readString()
        _2_imageURL = `in`.readString()
        _3_imageURL = `in`.readString()
        sundayText = `in`.readString()
        mondayText = `in`.readString()
        tuesdayText = `in`.readString()
        wednesdayText = `in`.readString()
        thursdayText = `in`.readString()
        fridayText = `in`.readString()
        saturdayText = `in`.readString()
    }


    fun setSundayText(hours: HashMap<String, String>) {
        var text = "SUNDAY\n\n"
        val start = hours["start"]
        val end = hours["end"]
        if (start != null && end != null) {
            text += """
                open: ${formatTime(start)}
                
                """.trimIndent()
            text += "close: " + formatTime(end)
        } else {
            text += "open: N/A\nclose: N/A"
        }
        sundayText = text
    }

    fun setMondayText(hours: HashMap<String, String>) {
        var text = "MONDAY\n\n"
        val start = hours["start"]
        val end = hours["end"]
        if (start != null && end != null) {
            text += """
                open: ${formatTime(start)}
                
                """.trimIndent()
            text += "close: " + formatTime(end)
        } else {
            text += "open: N/A\nclose: N/A"
        }
        mondayText = text
    }

    fun setTuesdayText(hours: HashMap<String, String>) {
        var text = "TUESDAY\n\n"
        val start = hours["start"]
        val end = hours["end"]
        if (start != null && end != null) {
            text += """
                open: ${formatTime(start)}
                
                """.trimIndent()
            text += "close: " + formatTime(end)
        } else {
            text += "open: N/A\nclose: N/A"
        }
        tuesdayText = text
    }

    fun setWednesdayText(hours: HashMap<String, String>) {
        var text = "WEDNESDAY\n\n"
        val start = hours["start"]
        val end = hours["end"]
        if (start != null && end != null) {
            text += """
                open: ${formatTime(start)}
                
                """.trimIndent()
            text += "close: " + formatTime(end)
        } else {
            text += "open: N/A\nclose: N/A"
        }
        wednesdayText = text
    }

    fun setThursdayText(hours: HashMap<String, String>) {
        var text = "THURSDAY\n\n"
        val start = hours["start"]
        val end = hours["end"]
        if (start != null && end != null) {
            text += """
                open: ${formatTime(start)}
                
                """.trimIndent()
            text += "close: " + formatTime(end)
        } else {
            text += "open: N/A\nclose: N/A"
        }
        thursdayText = text
    }

    fun setFridayText(hours: HashMap<String, String>) {
        var text = "FRIDAY\n\n"
        val start = hours["start"]
        val end = hours["end"]
        if (start != null && end != null) {
            text += """
                open: ${formatTime(start)}
                
                """.trimIndent()
            text += "close: " + formatTime(end)
        } else {
            text += "open: N/A\nclose: N/A"
        }
        fridayText = text
    }

    fun setSaturdayText(hours: HashMap<String, String>) {
        var text = "SATURDAY\n\n"
        val start = hours["start"]
        val end = hours["end"]
        if (start != null && end != null) {
            text += """
                open: ${formatTime(start)}
                
                """.trimIndent()
            text += "close: " + formatTime(end)
        } else {
            text += "open: N/A\nclose: N/A"
        }
        saturdayText = text
    }

    private fun formatTime(time: String): String? {
        var fixedTime = ""
        val minutes_int = 60 - time.substring(2, 3).toInt()
        var minutes = ""
        minutes = if (minutes_int == 60) {
            "00"
        } else {
            minutes_int.toString()
        }
        if (time.length == 4) {
            if (time.substring(0, 2).toInt() >= 12) {
                val hours = time.substring(0, 2).toInt() - 12
                fixedTime = if (hours == 0) {
                    "12:"
                } else {
                    "$hours:"
                }
                fixedTime += "$minutes pm"
            } else {
                val hours = time.substring(0, 2).toInt()
                fixedTime = hours.toString() + ":" + minutes + "am"
            }
            Log.i("time", "formatTime: $fixedTime")
            return fixedTime
        } else if (time.length == 3) {
            fixedTime = time.substring(0, 1).toInt().toString() + minutes + "am"
            return fixedTime
        }
        return null
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, i: Int) {
        parcel.writeString(_1_imageURL)
        parcel.writeString(_2_imageURL)
        parcel.writeString(_3_imageURL)
        parcel.writeString(sundayText)
        parcel.writeString(mondayText)
        parcel.writeString(tuesdayText)
        parcel.writeString(wednesdayText)
        parcel.writeString(thursdayText)
        parcel.writeString(fridayText)
        parcel.writeString(saturdayText)
    }

    companion object {
        val CREATOR: Creator<DetailsModel?> = object : Creator<DetailsModel?> {
            override fun createFromParcel(`in`: Parcel): DetailsModel? {
                return DetailsModel(`in`)
            }

            override fun newArray(size: Int): Array<DetailsModel?> {
                return arrayOfNulls(size)
            }
        }
    }
}