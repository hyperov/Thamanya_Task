package com.nabil.ahmed.thamanyatask.home.model.res


import com.google.gson.annotations.SerializedName

data class SectionsRes(
    @SerializedName("pagination")
    val pagination: Pagination,
    @SerializedName("sections")
    val sections: List<Section>
)