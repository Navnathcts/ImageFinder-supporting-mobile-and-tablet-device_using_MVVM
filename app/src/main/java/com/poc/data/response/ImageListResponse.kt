package com.poc.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ImageListResponse:Serializable {
    @SerializedName("data")
    @Expose
    var data: List<ImageList>? = null

    @SerializedName("success")
    @Expose
    var success: Boolean? = null

    @SerializedName("status")
    @Expose
    var status: Int? = null
}