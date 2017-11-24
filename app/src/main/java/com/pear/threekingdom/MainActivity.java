package com.pear.threekingdom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.pear.threekingdom.db.DbManager;
import com.pear.threekingdom.pinyin.ChineseToPinyinHelper;

public class MainActivity extends AppCompatActivity {
    private DbManager dbManager;
    private HeroListAdapter adapter;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.heros_list);
        dbManager = new DbManager(MainActivity.this);
        adapter = new HeroListAdapter(dbManager, MainActivity.this, null);
        listView.setAdapter(adapter);
        System.out.println(ChineseToPinyinHelper.getInstance().getSelling("吴博文"));
    }
}
