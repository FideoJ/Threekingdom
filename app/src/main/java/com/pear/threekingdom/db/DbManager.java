package com.pear.threekingdom.db;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.pear.threekingdom.entity.Hero;

public class DbManager {
  private SQLiteDatabase db;
  private final String DB_TABLE = "hero";

  public DbManager(Context context) {
    DbHelper helper = new DbHelper(context);
    db = helper.getWritableDatabase();
  }

  public void closeDb() { db.close(); }

  public void addOneHero(Hero hero) {
    byte[] avatar = DbBitmapUtility.img2Bytes(hero.avatar);
    ContentValues cv = new ContentValues();

    cv.put("name", hero.name);
    cv.put("alias", hero.alias);
    cv.put("avatar", avatar);
    cv.put("gender", hero.gender);
    cv.put("birth_year", hero.birth_year);
    cv.put("death_year", hero.death_year);
    cv.put("native_place", hero.native_place);
    cv.put("work_for", hero.work_for);
    cv.put("achievement", hero.achievement);

    db.beginTransaction();
    try {
      db.insert(DB_TABLE, null, cv);
      db.setTransactionSuccessful();
    } finally {
      db.endTransaction();
    }
  }

  public ArrayList<Hero> queryAllHeroes() {
    ArrayList<Hero> heroes = new ArrayList<>();
    Cursor c = db.rawQuery("SELECT * FROM hero", null);

    c.moveToFirst();
    while (c.moveToNext()) {
      Hero hero = new Hero();
      byte[] avatar = c.getBlob(c.getColumnIndex("avatar"));

      hero.hero_id = c.getInt(c.getColumnIndex("hero_id"));
      hero.name = c.getString(c.getColumnIndex("name"));
      hero.alias = c.getString(c.getColumnIndex("alias"));
      hero.avatar = DbBitmapUtility.bytes2Img(avatar);
      hero.gender = c.getString(c.getColumnIndex("gender"));
      hero.birth_year = c.getString(c.getColumnIndex("birth_year"));
      hero.death_year = c.getString(c.getColumnIndex("death_year"));
      hero.native_place = c.getString(c.getColumnIndex("native_place"));
      hero.work_for = c.getString(c.getColumnIndex("work_for"));
      hero.achievement = c.getString(c.getColumnIndex("achievement"));

      heroes.add(hero);
    }
    c.close();

    return heroes;
  }

}