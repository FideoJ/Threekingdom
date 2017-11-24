package com.pear.threekingdom.entity;

import android.os.Bundle;

import com.pear.threekingdom.db.DbBitmapUtility;
import com.pear.threekingdom.pinyin.ChineseToPinyinHelper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bowenwu on 2017/11/24.
 */

public class HeroHelper {
    public static String name = "name";
    public static String alias = "alias";
    public static String gender = "gender";
    public static String birthyear = "birthyear";
    public static String deadyear = "deadyear";
    public static String native_place = "native_place";
    public static String work_for = "work_for";
    public static String avatar = "img";
    public static String hero_id = "hero_id";
    public static String achievement = "achievement";
    public static String hero_name_pinyin = "hero_name_pinyin";

    public static Bundle toBundle(Hero h) {
        Bundle bundle = new Bundle();
        bundle.putString(HeroHelper.name, h.name);
        bundle.putString(HeroHelper.alias, h.alias);
        bundle.putString(HeroHelper.gender, h.gender);
        bundle.putString(HeroHelper.birthyear, h.birth_year);
        bundle.putString(HeroHelper.deadyear, h.death_year);
        bundle.putString(HeroHelper.native_place, h.native_place);
        bundle.putString(HeroHelper.work_for, h.work_for);
        bundle.putByteArray(HeroHelper.avatar, DbBitmapUtility.img2Bytes(h.avatar));
        bundle.putInt(HeroHelper.hero_id, h.hero_id);
        bundle.putString(HeroHelper.achievement, h.achievement);
        return bundle;
    }

    /**
     * 此函数为了SimpleAdapter而写
     * 注意：会添加拼音字段，这在hero中原先是没有的 可能会破坏结构 不过先这样吧
     */
    public static List<Map<String, Object>> herosListToMapList(List<Hero> l) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < l.size(); ++i) {
            Map<String, Object> temp = new LinkedHashMap<>();
            temp.put(HeroHelper.name, l.get(i).name);
            temp.put(HeroHelper.alias, l.get(i).alias);
            temp.put(HeroHelper.gender, l.get(i).gender);
            temp.put(HeroHelper.birthyear, l.get(i).birth_year);
            temp.put(HeroHelper.deadyear, l.get(i).death_year);
            temp.put(HeroHelper.native_place, l.get(i).native_place);
            temp.put(HeroHelper.work_for, l.get(i).work_for);
            temp.put(HeroHelper.avatar, l.get(i).avatar);
            temp.put(HeroHelper.hero_id, l.get(i).hero_id);
            temp.put(HeroHelper.achievement, l.get(i).achievement);
            temp.put(HeroHelper.hero_name_pinyin, ChineseToPinyinHelper.getPinYin(l.get(i).name));
            list.add(temp);
        }
        return list;
    }
}
