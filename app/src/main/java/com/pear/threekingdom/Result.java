package com.pear.threekingdom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pear.threekingdom.db.DbManager;
import com.pear.threekingdom.entity.Hero;

import java.util.ArrayList;

/**
 * Created by Young on 2017/11/23.
 */

public class Result extends AppCompatActivity {
    private TextView result;
    private ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        init();
    }
    private void init() {
        result = (TextView) findViewById(R.id.result);
        img = (ImageView)findViewById(R.id.result_logo);
        randomHero();
        ImageView back = (ImageView)findViewById(R.id.back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void randomHero() {
        DbManager db = new DbManager(this);
        Hero hero = db.queryOneHeroRandomly();
        String msg = "你是"+hero.name + ",别名"+ hero.alias + ",性别:" +hero.gender
                + "。\n生于"+hero.birth_year+ "年，卒于"+ hero.death_year
                +"年。在世时为"+hero.work_for+"效力。\n主要成就："+hero.achievement;
        result.setText(msg);
        img.setImageBitmap(hero.avatar);
    }
}
