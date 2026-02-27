package com.nabil.ahmed.thamanyatask.home.model.res

import com.google.gson.annotations.SerializedName

/**
 * Union DTO for section content. API returns different shapes by [Section.contentType]:
 * - podcast: podcast_id, name, description, avatar_url, episode_count, duration, language?, priority, popularityScore, score
 * - episode: episode_id, podcast_id, name, podcast_name, description, avatar_url, audio_url, duration, release_date, chapters, paid_*, etc.
 * - audio_book: audiobook_id, name, author_name, description, avatar_url, duration, language, release_date, score
 * - audio_article: article_id, name, author_name, description, avatar_url, duration, release_date, score
 * Fields not present for a given type are null.
 */
data class Content(
    @SerializedName("article_id")
    val articleId: String? = null,
    @SerializedName("audio_url")
    val audioUrl: String? = null,
    @SerializedName("audiobook_id")
    val audiobookId: String? = null,
    @SerializedName("author_name")
    val authorName: String? = null,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("chapters")
    val chapters: List<Any>? = null,
    @SerializedName("description")
    val description: String,
    @SerializedName("duration")
    val duration: Int,
    @SerializedName("episode_count")
    val episodeCount: Int? = null,
    @SerializedName("episode_id")
    val episodeId: String? = null,
    @SerializedName("episode_type")
    val episodeType: String? = null,
    @SerializedName("free_transcript_url")
    val freeTranscriptUrl: String? = null,
    @SerializedName("language")
    val language: String? = null,
    @SerializedName("name")
    val name: String,
    @SerializedName("number")
    val number: Int? = null,
    @SerializedName("paid_early_access_audio_url")
    val paidEarlyAccessAudioUrl: String? = null,
    @SerializedName("paid_early_access_date")
    val paidEarlyAccessDate: String? = null,
    @SerializedName("paid_exclusive_start_time")
    val paidExclusiveStartTime: Int? = null,
    @SerializedName("paid_exclusivity_type")
    val paidExclusivityType: String? = null,
    @SerializedName("paid_is_early_access")
    val paidIsEarlyAccess: Boolean? = null,
    @SerializedName("paid_is_now_early_access")
    val paidIsNowEarlyAccess: Boolean? = null,
    @SerializedName("paid_is_exclusive")
    val paidIsExclusive: Boolean? = null,
    @SerializedName("paid_is_exclusive_partially")
    val paidIsExclusivePartially: Boolean? = null,
    @SerializedName("paid_transcript_url")
    val paidTranscriptUrl: String? = null,
    @SerializedName("podcast_id")
    val podcastId: String? = null,
    @SerializedName("podcast_name")
    val podcastName: String? = null,
    @SerializedName("podcastPopularityScore")
    val podcastPopularityScore: Int? = null,
    @SerializedName("podcastPriority")
    val podcastPriority: Int? = null,
    @SerializedName("popularityScore")
    val popularityScore: Int? = null,
    @SerializedName("priority")
    val priority: Int? = null,
    @SerializedName("release_date")
    val releaseDate: String? = null,
    @SerializedName("score")
    val score: Double,
    @SerializedName("season_number")
    val seasonNumber: Int? = null,
    @SerializedName("separated_audio_url")
    val separatedAudioUrl: String? = null
)
