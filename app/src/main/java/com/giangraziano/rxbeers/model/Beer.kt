package com.giangraziano.rxbeers.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by giannig on 11/02/18.
 */


data class Beer(
        @SerializedName("status")
        @Expose
        var status: String,

        @SerializedName("numberOfPages")
        @Expose
        var numberOfPages: Int,

        @SerializedName("data")
        @Expose
        var data: MutableList<Data>
)

data class Data(
        @SerializedName("name")
        @Expose
        var name: String,

        @SerializedName("description")
        @Expose
        var description: String,

        @SerializedName("labels")
        @Expose
        var labels: Label?
)

data class Label(
        @SerializedName("medium")
        @Expose
        var medium: String,

        @SerializedName("large")
        @Expose
        var large: String,

        @SerializedName("icon")
        @Expose
        var icon: String
)