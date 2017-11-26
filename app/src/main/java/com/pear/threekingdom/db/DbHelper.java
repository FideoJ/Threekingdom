package com.pear.threekingdom.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

  private static final String DATABASE_NAME = "hero.db";
  private static final int DATABASE_VERSION = 1;

  public DbHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL("CREATE TABLE `hero` (\n"
               + "`hero_id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n"
               + "`name`	TEXT NOT NULL,\n"
               + "`alias`	TEXT NOT NULL DEFAULT '无',\n"
               + "`avatar`	BLOB NOT NULL,\n"
               + "`gender`	TEXT NOT NULL,\n"
               + "`birth_year`	TEXT NOT NULL DEFAULT '不详',\n"
               + "`death_year`	TEXT NOT NULL DEFAULT '不详',\n"
               + "`native_place`	TEXT NOT NULL DEFAULT '不详',\n"
               + "`work_for`	TEXT NOT NULL DEFAULT '不详',\n"
               + "`achievement`	TEXT NOT NULL DEFAULT '不详'\n"
               + ");");
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}