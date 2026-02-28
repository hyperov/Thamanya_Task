package com.nabil.ahmed.thamanyatask.search.model.res


import com.google.gson.annotations.SerializedName

data class SearchRes(
    @SerializedName("sections")
    val sections: List<SearchSection>
)