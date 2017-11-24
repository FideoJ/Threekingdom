package com.pear.threekingdom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.pear.threekingdom.db.DbManager;
import com.pear.threekingdom.entity.HeroHelper;

import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private List<Map<String, Object>> dataToShow;
    private DbManager dbManager;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateListView();
    }
    private void updateListView() {
        if (dbManager == null) {
            dbManager = new DbManager(MainActivity.this);
        }
        if (listView == null) {
            listView = (ListView)findViewById(R.id.heros_list);
        }
        dataToShow = HeroHelper.herosListToMapList(dbManager.queryAllHeroes());
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, dataToShow, R.layout.listview_hero,
                new String[]{
                        HeroHelper.avatar,
                        HeroHelper.name,
                        HeroHelper.hero_name_pinyin,
                        HeroHelper.gender
                }, new int[]{
                R.id.avatar,
                R.id.hero_name,
                R.id.hero_pinyin,
                R.id.gender
                });
        listView.setAdapter(simpleAdapter);
    }
}
