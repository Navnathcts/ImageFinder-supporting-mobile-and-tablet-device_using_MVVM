package com.poc.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ImageList : Serializable {
    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("link")
    @Expose
    var link: String? = null
    @SerializedName("images")
    @Expose
    var images: List<ImageItem>? = null
}