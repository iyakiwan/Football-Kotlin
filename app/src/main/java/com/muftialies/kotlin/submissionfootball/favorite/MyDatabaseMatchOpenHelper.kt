package com.muftialies.kotlin.submissionfootball.favorite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class MyDatabaseMatchOpenHelper (ctx: Context) : ManagedSQLiteOpenHelper(ctx,"FavoriteMatch.db", null, 1) {
    companion object{
        private var instance: MyDatabaseMatchOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseMatchOpenHelper{
            if (instance == null){
                instance = MyDatabaseMatchOpenHelper(ctx.applicationContext)
            }
            return instance as MyDatabaseMatchOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(FavoriteMatch.TABLE_FAVORITE_MATCH, true,
            FavoriteMatch.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            FavoriteMatch.MATCH_ID to TEXT + UNIQUE,
            FavoriteMatch.MATCH_NAME to TEXT,
            FavoriteMatch.MATCH_DATE to TEXT,
            FavoriteMatch.MATCH_TIME to TEXT,
            FavoriteMatch.MATCH_ROUND to TEXT,
            FavoriteMatch.MATCH_SESSION to TEXT,

            FavoriteMatch.MATCH_HOME_ID to TEXT,
            FavoriteMatch.MATCH_HOME_TEAM to TEXT,
            FavoriteMatch.MATCH_HOME_SCORE to TEXT,
            FavoriteMatch.MATCH_HOME_GOAL to TEXT,
            FavoriteMatch.MATCH_HOME_RED to TEXT,
            FavoriteMatch.MATCH_HOME_YELLOW to TEXT,
            FavoriteMatch.MATCH_HOME_LOGO to TEXT,

            FavoriteMatch.MATCH_AWAY_ID to TEXT,
            FavoriteMatch.MATCH_AWAY_TEAM to TEXT,
            FavoriteMatch.MATCH_AWAY_SCORE to TEXT,
            FavoriteMatch.MATCH_AWAY_GOAL to TEXT,
            FavoriteMatch.MATCH_AWAY_RED to TEXT,
            FavoriteMatch.MATCH_AWAY_YELLOW to TEXT,
            FavoriteMatch.MATCH_AWAY_LOGO to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(FavoriteMatch.TABLE_FAVORITE_MATCH, true)
    }
}

val Context.databaseMatch: MyDatabaseMatchOpenHelper
    get() = MyDatabaseMatchOpenHelper.getInstance(applicationContext)