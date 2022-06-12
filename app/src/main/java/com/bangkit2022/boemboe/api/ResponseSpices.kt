package com.bangkit2022.boemboe.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ResponseSpices(

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("data")
    val data: ArrayList<ItemSpices>,

)

data class ResponseItemSpices(

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("data")
    val data: ItemSpices,

    )


@Parcelize
data class ItemSpices(

    @field:SerializedName("id")
    val id: Int ,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("benefit")
    val benefit: ArrayList<String>,

    @field:SerializedName("cooking")
    val cooking: ArrayList<String>,

    @field:SerializedName("photo_url")
    val photoUrl: String,

    ):Parcelable