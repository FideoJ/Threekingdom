package com.pear.threekingdom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by bowenwu on 2017/11/25.
 */

public class TestTitleActivity extends AppCompatActivity {

    ImageView back;
    Button startButton;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        back = (ImageView)findViewById(R.id.back_button);
        startButton = (Button)findViewById(R.id.start_test_button);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestTitleActivity.this.finish();
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TestTitleActivity.this, TestProblemAvtivity.class));
                TestTitleActivity.this.finish();
            }
        });

        textView = (TextView)findViewById(R.id.test_introduce);
        textView.setText(getResources().getString(R.string.testIntroduction));
    }
}
