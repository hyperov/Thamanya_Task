package com.nabil.ahmed.thamanyatask.search.model.res


import com.google.gson.annotations.SerializedName

data class SearchSection(
    @SerializedName("content")
    val content: List<Content>,
    @SerializedName("content_type")
    val contentType: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("order")
    val order: String,
    @SerializedName("type")
    val type: String
)