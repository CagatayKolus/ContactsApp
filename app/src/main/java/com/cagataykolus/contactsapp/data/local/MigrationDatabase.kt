package com.cagataykolus.contactsapp.data.local

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.cagataykolus.contactsapp.model.Contact

/**
 * Created by Çağatay Kölüş on 24.08.2021.
 * cagataykolus@gmail.com
 */
/**
 * Each Migration has a start and end versions and Room runs these migrations to bring the
 * database to the latest version. The migration object that can modify the database and
 * to the necessary changes.
 */
object MigrationDatabase {
    const val DB_VERSION = 2

    val MIGRATION_CONTACT: Array<Migration>
        get() = arrayOf(
            migrationContact()
        )

    private fun migrationContact(): Migration = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE ${Contact.TABLE_CONTACTS} ADD COLUMN body TEXT")
        }
    }
}

