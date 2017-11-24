package com.pear.threekingdom.entity;

import android.graphics.Bitmap;

import com.pear.threekingdom.pinyin.ChineseToPinyinHelper;

public class Hero {
  public int hero_id;
  public String name;
  public String alias;
  public Bitmap avatar;
  public String gender;
  public String birth_year;
  public String death_year;
  public String native_place;
  public String work_for;
  public String achievement;
  public String pinyin;

  public Hero() {
  }

  public Hero(String name, String alias, Bitmap avatar, String gender, String birth_year, String death_year, String native_place, String work_for, String achievement) {
    this.name = name;
    this.alias = alias;
    this.avatar = avatar;
    this.gender = gender;
    this.birth_year = birth_year;
    this.death_year = death_year;
    this.native_place = native_place;
    this.work_for = work_for;
    this.achievement = achievement;
      this.pinyin = ChineseToPinyinHelper.getInstance().getSelling(name);
  }
}