package com.muftialies.kotlin.submissionfootball.favorite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class MyDatabaseTeamOpenHelper (ctx: Context) : ManagedSQLiteOpenHelper(ctx,"FavoriteTeam.db", null, 1) {
    companion object{
        private var instance: MyDatabaseTeamOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseTeamOpenHelper{
            if (instance == null){
                instance = MyDatabaseTeamOpenHelper(ctx.applicationContext)
            }
            return instance as MyDatabaseTeamOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(FavoriteTeam.TABLE_FAVORITE_TEAM, true,
            FavoriteTeam.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            FavoriteTeam.TEAM_ID to TEXT + UNIQUE,
            FavoriteTeam.TEAM_NAME to TEXT,
            FavoriteTeam.TEAM_LOGO to TEXT,
            FavoriteTeam.TEAM_YEAR to TEXT,
            FavoriteTeam.TEAM_STADIUM to TEXT,
            FavoriteTeam.TEAM_DESC to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(FavoriteTeam.TABLE_FAVORITE_TEAM, true)
    }
}

val Context.databaseTeam: MyDatabaseTeamOpenHelper
    get() = MyDatabaseTeamOpenHelper.getInstance(applicationContext)