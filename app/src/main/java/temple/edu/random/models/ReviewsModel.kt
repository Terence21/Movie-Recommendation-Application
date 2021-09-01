package temple.edu.random.models

import android.os.Parcelable
import android.os.Parcel
import android.os.Parcelable.Creator

class ReviewsModel : Parcelable {
    var rating: Int
    var text: String?
    var profileImageURL: String?
    var userName: String?

    constructor(rating: Int, text: String?, profileImageURL: String?, userName: String?) {
        this.rating = rating
        this.text = text
        this.profileImageURL = profileImageURL
        this.userName = userName
    }

    protected constructor(`in`: Parcel) {
        rating = `in`.readInt()
        text = `in`.readString()
        profileImageURL = `in`.readString()
        userName = `in`.readString()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, i: Int) {
        parcel.writeInt(rating)
        parcel.writeString(text)
        parcel.writeString(profileImageURL)
        parcel.writeString(userName)
    }

    companion object {
        val CREATOR: Creator<ReviewsModel?> = object : Creator<ReviewsModel?> {
            override fun createFromParcel(`in`: Parcel): ReviewsModel? {
                return ReviewsModel(`in`)
            }

            override fun newArray(size: Int): Array<ReviewsModel?> {
                return arrayOfNulls(size)
            }
        }
    }
}