package com.dice.djetmovie.data.local.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dice.djetmovie.data.Constants
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = Constants.TABLE_TV_SHOW)
data class TvShowEntity(
        @PrimaryKey
        @ColumnInfo
        var id: Int,

        @ColumnInfo
        var title: String,

        @ColumnInfo
        var posterPath: String?,

        @ColumnInfo
        var backdropPath: String?,

        @ColumnInfo
        var releaseDate: String?,

        @ColumnInfo
        var overview: String?,

        @ColumnInfo
        val popularity: Float

) : Parcelable