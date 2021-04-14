package com.dicoding.githubuser.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

internal class DatabaseHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "githubUser"

        private const val DATABASE_VERSION = 1

        private const val SQL_CREATE_TABLE = "CREATE TABLE ${DatabaseContract.FavoriteUserColumns.TABLE_NAME}" +
                "(${DatabaseContract.FavoriteUserColumns.LOGIN} TEXT NOT NULL PRIMARY KEY," +
                "${DatabaseContract.FavoriteUserColumns.NAME} TEXT," +
                "${DatabaseContract.FavoriteUserColumns.AVATAR_URL} TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${DatabaseContract.FavoriteUserColumns.TABLE_NAME}")
        onCreate(db)
    }
}