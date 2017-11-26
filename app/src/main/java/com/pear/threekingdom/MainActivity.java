package com.pear.threekingdom;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.pear.threekingdom.db.DbManager;
import com.pear.threekingdom.entity.Hero;
import com.pear.threekingdom.entity.HeroHelper;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private DbManager dbManager;
    SharedPreferences prefs = null;
    private HeroListAdapter adapter;
    private Button deleteSelectedButton;
    private ListView listView;
    private EditText editText;
    private ImageView moreButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.heros_list);
        dbManager = new DbManager(MainActivity.this);
        prefs = getSharedPreferences("com.pear.ThreeKingdom", MODE_PRIVATE);

        adapter = new HeroListAdapter(dbManager, MainActivity.this, null);
        listView.setAdapter(adapter);
        deleteSelectedButton = (Button)findViewById(R.id.deleteSelected);
        deleteSelectedButton.setVisibility(View.INVISIBLE);
        editText = (EditText)findViewById(R.id.search_edittext);
        moreButton = (ImageView)findViewById(R.id.more_button);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, Detail.class);
                intent.putExtras(HeroHelper.HeroToBundle((Hero)adapter.getItem(i)));
                startActivity(intent);
            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapter.whetherInMutipleSelected() == false) {
                    toMutipleDeleteMode();
                }
                return true;
            }
        });
        deleteSelectedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getVisibility() == View.VISIBLE) {
                    toNormalNode();
                }
            }
        });

        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (editText.getCompoundDrawables()[2] == null) {
                    return  false;
                }
                if (motionEvent.getAction() != MotionEvent.ACTION_UP) {
                    return false;
                }
                if (motionEvent.getX() > editText.getWidth() - editText.getCompoundDrawables()[2].getBounds().width()) {
                    // touch on search button
                    adapter.searchHerosByName(editText.getText().toString());
                    return true;
                }
                return false;
            }
        });
        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.main_activity_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.add_hero:
                                startActivity(new Intent(MainActivity.this, Detail.class));
                                break;
                            case R.id.similar_hero_test:
                                startActivity(new Intent(MainActivity.this, TestTitleActivity.class));
                                break;
                            default:
                                break;
                        }
                        return true;
                    }
                });

                popupMenu.show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (prefs.getBoolean("firstrun", true)) {
            try {
                dbManager.insertHeroesFromFile(this, R.raw.insert_heroes);
            } catch (IOException e) {
                e.printStackTrace();
            }
            prefs.edit().putBoolean("firstrun", false).commit();
        }
        adapter.updateDataWhenResume();
    }

    private void toMutipleDeleteMode() {
        adapter.setRadioButtonAppear(true);
        deleteSelectedButton.setVisibility(View.VISIBLE);
        editText.setText("");
        editText.setVisibility(View.INVISIBLE);
    }

    private void toNormalNode() {
        adapter.confirmDeleteHeros();
        deleteSelectedButton.setVisibility(View.INVISIBLE);
        editText.setVisibility(View.VISIBLE);
    }
}
