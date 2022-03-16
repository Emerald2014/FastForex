package ru.kudesnik.fastforex.model.entities.database

import androidx.room.Room
import androidx.room.RoomDatabase
import ru.kudesnik.fastforex.App

@androidx.room.Database(
    entities = [
        HistoryEntity::class
    ],
    version = 1,
    exportSchema = false
)

abstract class HistoryDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao

    companion object {
        private const val DB_NAME = "history_database.db"

        val db: HistoryDatabase by lazy {
            Room.databaseBuilder(
                App.appInstance,
                HistoryDatabase::class.java,
                DB_NAME
            ).build()
        }
    }
}