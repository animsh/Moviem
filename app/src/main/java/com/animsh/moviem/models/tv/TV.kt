package com.animsh.moviem.models.tv


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class TV(
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("created_by")
    val createdBy: @RawValue List<Any>,
    @SerializedName("episode_run_time")
    val episodeRunTime: @RawValue List<Int>,
    @SerializedName("first_air_date")
    val firstAirDate: String?,
    @SerializedName("genres")
    val genres: @RawValue List<Genre>,
    @SerializedName("homepage")
    val homepage: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("in_production")
    val inProduction: Boolean?,
    @SerializedName("languages")
    val languages: @RawValue List<String>,
    @SerializedName("last_air_date")
    val lastAirDate: String?,
    @SerializedName("last_episode_to_air")
    val lastEpisodeToAir: LastEpisodeToAir?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("networks")
    val networks: @RawValue List<Network>,
    @SerializedName("next_episode_to_air")
    val nextEpisodeToAir: LastEpisodeToAir?,
    @SerializedName("number_of_episodes")
    val numberOfEpisodes: Int?,
    @SerializedName("number_of_seasons")
    val numberOfSeasons: Int?,
    @SerializedName("origin_country")
    val originCountry: @RawValue List<String>,
    @SerializedName("original_language")
    val originalLanguage: String?,
    @SerializedName("original_name")
    val originalName: String?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("popularity")
    val popularity: Double?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("production_companies")
    val productionCompanies: @RawValue List<ProductionCompany>,
    @SerializedName("production_countries")
    val productionCountries: @RawValue List<ProductionCountry>,
    @SerializedName("seasons")
    val seasons: @RawValue List<Season>,
    @SerializedName("spoken_languages")
    val spokenLanguages: @RawValue List<SpokenLanguage>,
    @SerializedName("status")
    val status: String?,
    @SerializedName("tagline")
    val tagline: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    @SerializedName("vote_count")
    val voteCount: Int?
) : Parcelable