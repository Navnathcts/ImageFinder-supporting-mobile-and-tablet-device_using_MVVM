package com.poc.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ImageItem(
    @SerializedName("id")
    @Expose
    var id: String? = null,

    @SerializedName("title")
    @Expose
    var title: String? = null,

    @SerializedName("comment")
    @Expose
    var comment: String? = null,

    @SerializedName("link")
    @Expose
    var link: String? = null
) : Serializable