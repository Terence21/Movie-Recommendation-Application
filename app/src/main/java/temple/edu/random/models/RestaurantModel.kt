package temple.edu.random.models

import android.os.Parcelable
import android.os.Parcel
import android.os.Parcelable.Creator

class RestaurantModel : Parcelable {
    var rating = 0
    var id // not accessible to user
            : String?
    var name // name
            : String?
    var phone // phone
            : String?
    var image // image_url
            : String?
    var location // location has a JSON array
            : String?
    var url: String?

    constructor(rating: Int, id: String?, name: String?, phone: String?, image: String?, location: String?, url: String?) {
        this.rating = rating
        this.id = id
        this.name = name
        this.phone = phone
        this.image = image
        this.location = location
        this.url = url
    }

    protected constructor(`in`: Parcel) {
        id = `in`.readString()
        name = `in`.readString()
        phone = `in`.readString()
        image = `in`.readString()
        location = `in`.readString()
        url = `in`.readString()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, i: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(phone)
        parcel.writeString(image)
        parcel.writeString(location)
        parcel.writeString(url)
    }

    companion object {
        val CREATOR: Creator<RestaurantModel?> = object : Creator<RestaurantModel?> {
            override fun createFromParcel(`in`: Parcel): RestaurantModel? {
                return RestaurantModel(`in`)
            }

            override fun newArray(size: Int): Array<RestaurantModel?> {
                return arrayOfNulls(size)
            }
        }
    }
}