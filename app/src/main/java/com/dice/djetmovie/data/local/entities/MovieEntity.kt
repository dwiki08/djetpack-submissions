package com.dice.djetmovie.data.local.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dice.djetmovie.data.Constants
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = Constants.TABLE_MOVIE)
data class MovieEntity(
        @PrimaryKey
        @ColumnInfo
        val id: Int,

        @ColumnInfo
        val title: String,

        @ColumnInfo
        val posterPath: String?,

        @ColumnInfo
        val backdropPath: String?,

        @ColumnInfo
        val releaseDate: String?,

        @ColumnInfo
        val overview: String?,

        @ColumnInfo
        val popularity: Float

) : Parcelable