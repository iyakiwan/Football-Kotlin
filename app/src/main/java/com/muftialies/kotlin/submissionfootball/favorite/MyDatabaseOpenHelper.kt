package com.muftialies.kotlin.submissionfootball.favorite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class MyDatabaseOpenHelper (ctx: Context) : ManagedSQLiteOpenHelper(ctx,"FavoriteMatch.db", null, 1) {
    companion object{
        private var instance: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseOpenHelper{
            if (instance == null){
                instance = MyDatabaseOpenHelper(ctx.applicationContext)
            }
            return instance as MyDatabaseOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(Favorite.TABLE_FAVORITE, true,
            Favorite.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            Favorite.MATCH_ID to TEXT + UNIQUE,
            Favorite.MATCH_NAME to TEXT,
            Favorite.MATCH_DATE to TEXT,
            Favorite.MATCH_TIME to TEXT,
            Favorite.MATCH_ROUND to TEXT,
            Favorite.MATCH_SESSION to TEXT,

            Favorite.MATCH_HOME_ID to TEXT,
            Favorite.MATCH_HOME_TEAM to TEXT,
            Favorite.MATCH_HOME_SCORE to TEXT,
            Favorite.MATCH_HOME_GOAL to TEXT,
            Favorite.MATCH_HOME_RED to TEXT,
            Favorite.MATCH_HOME_YELLOW to TEXT,
            Favorite.MATCH_HOME_LOGO to TEXT,

            Favorite.MATCH_AWAY_ID to TEXT,
            Favorite.MATCH_AWAY_TEAM to TEXT,
            Favorite.MATCH_AWAY_SCORE to TEXT,
            Favorite.MATCH_AWAY_GOAL to TEXT,
            Favorite.MATCH_AWAY_RED to TEXT,
            Favorite.MATCH_AWAY_YELLOW to TEXT,
            Favorite.MATCH_AWAY_LOGO to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(Favorite.TABLE_FAVORITE, true)
    }
}

val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)