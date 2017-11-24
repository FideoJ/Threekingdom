package com.pear.threekingdom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.pear.threekingdom.db.DbManager;
import com.pear.threekingdom.entity.Hero;
import com.pear.threekingdom.entity.HeroHelper;

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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, Detail.class);
                intent.putExtras(HeroHelper.HeroToBundle((Hero)adapter.getItem(i)));
                startActivity(intent);
            }
        });
    }
}
