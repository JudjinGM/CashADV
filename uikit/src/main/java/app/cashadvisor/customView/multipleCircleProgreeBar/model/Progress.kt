package app.cashadvisor.customView.multipleCircleProgreeBar.model

import android.os.Parcel
import android.os.Parcelable

data class Progress(val id: Int,
                    var value: Int,
                    val mainColor: Int,
                    val backgroundColor: Int):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(value)
        parcel.writeInt(mainColor)
        parcel.writeInt(backgroundColor)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Progress> {
        override fun createFromParcel(parcel: Parcel): Progress {
            return Progress(parcel)
        }

        override fun newArray(size: Int): Array<Progress?> {
            return arrayOfNulls(size)
        }
    }
}
