package com.example.imagesapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ImagesEntity::class],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase: RoomDatabase() {

    abstract fun ImagesDao(): ImagesDao

}
