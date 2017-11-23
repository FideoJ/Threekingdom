package com.pear.threekingdom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.pear.threekingdom.db.DbManager;
import com.pear.threekingdom.entity.Hero;

import java.util.ArrayList;

/**
 * Created by Young on 2017/11/23.
 */

public class Result extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        init();
    }
    private void init() {
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
        //  待添加
    }
}
