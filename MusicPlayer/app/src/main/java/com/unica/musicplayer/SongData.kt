package com.unica.musicplayer

import android.os.Parcel
import android.os.Parcelable

data class SongData (
        val path:String?,
        val title:String?,
        val artist:String?,
        val album:String?,
        val duration:String?
):Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(path)
        parcel.writeString(title)
        parcel.writeString(artist)
        parcel.writeString(album)
        parcel.writeString(duration)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SongData> {
        override fun createFromParcel(parcel: Parcel): SongData {
            return SongData(parcel)
        }

        override fun newArray(size: Int): Array<SongData?> {
            return arrayOfNulls(size)
        }
    }
}