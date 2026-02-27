package com.nabil.ahmed.thamanyatask.home.model.res


import com.google.gson.annotations.SerializedName

data class Section(
    @SerializedName("content")
    val content: List<Content>,
    @SerializedName("content_type")
    val contentType: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("order")
    val order: Int,
    @SerializedName("type")
    val type: String
)