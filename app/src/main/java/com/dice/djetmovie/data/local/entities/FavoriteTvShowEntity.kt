package com.dice.djetmovie.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dice.djetmovie.data.Constants

@Entity(tableName = Constants.TABLE_FAV_TV_SHOW)
data class FavoriteTvShowEntity(
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
        val overview: String?

)