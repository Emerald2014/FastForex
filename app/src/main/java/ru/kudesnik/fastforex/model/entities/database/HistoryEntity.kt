package ru.kudesnik.fastforex.model.entities.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HistoryEntity(
//    (autoGenerate = true) val idEntity: Int = 0,
    @PrimaryKey val currency: String,
    val currencyValue: Double = 0.0,
    val isFavourite: Boolean = false
)