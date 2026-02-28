package com.nabil.ahmed.thamanyatask.search.model.res


import com.google.gson.annotations.SerializedName

data class Content(
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("duration")
    val duration: String,
    @SerializedName("episode_count")
    val episodeCount: String,
    @SerializedName("language")
    val language: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("podcast_id")
    val podcastId: String,
    @SerializedName("popularityScore")
    val popularityScore: String,
    @SerializedName("priority")
    val priority: String,
    @SerializedName("score")
    val score: String
)