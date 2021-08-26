package com.cagataykolus.contactsapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cagataykolus.contactsapp.data.local.dao.ContactDao
import com.cagataykolus.contactsapp.model.Contact

/**
 * Created by Çağatay Kölüş on 24.08.2021.
 * cagataykolus@gmail.com
 */
/**
 * ContactDatabase provides DAO [ContactDao] by using method [getContactDao].
 */
@Database(
    entities = [Contact::class],
    version = MigrationDatabase.DB_VERSION
)
abstract class ContactDatabase : RoomDatabase() {

    abstract fun getContactDao(): ContactDao

    companion object {
        private const val DB_NAME = "database_contacts"

        @Volatile
        private var INSTANCE: ContactDatabase? = null

        fun getInstance(context: Context): ContactDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ContactDatabase::class.java,
                    DB_NAME
                ).addMigrations(*MigrationDatabase.MIGRATION_CONTACT)
                    .allowMainThreadQueries()
                    .build()

                INSTANCE = instance
                return instance
            }
        }
    }
}
