package com.pear.threekingdom;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by bowenwu on 2017/11/25.
 */

public class TestProblemAvtivity extends AppCompatActivity {
    private int problemNo;
    private int[] problemID = {
            R.string.sampleQuestion1, R.string.sampleQuestion2, R.string.sampleQuestion3,
            R.string.sampleQuestion4, R.string.sampleQuestion5, R.string.sampleQuestion6};
    private int[][] choiceID = {
            {R.string.sampleChoice_1_1, R.string.sampleChoice_1_2, R.string.sampleChoice_1_3, R.string.sampleChoice_1_4},
            {R.string.sampleChoice_2_1, R.string.sampleChoice_2_2, R.string.sampleChoice_2_3, R.string.sampleChoice_2_4},
            {R.string.sampleChoice_3_1, R.string.sampleChoice_3_2, R.string.sampleChoice_3_3, R.string.sampleChoice_3_4},
            {R.string.sampleChoice_4_1, R.string.sampleChoice_4_2, R.string.sampleChoice_4_3, R.string.sampleChoice_4_4},
            {R.string.sampleChoice_5_1, R.string.sampleChoice_5_2, R.string.sampleChoice_5_3, R.string.sampleChoice_5_4},
            {R.string.sampleChoice_6_1, R.string.sampleChoice_6_2, R.string.sampleChoice_6_3, R.string.sampleChoice_6_4}};

    RadioGroup radioGroup;
    Button nextButton;
    ImageView backButton;
    TextView problem;
    TextView problemTitle;

    RadioButton[] choiceButton = {null, null, null, null};
    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_test_problem);
        problemNo = -1;

        choiceButton[0] = (RadioButton)findViewById(R.id.choice_0);
        choiceButton[1] = (RadioButton)findViewById(R.id.choice_1);
        choiceButton[2] = (RadioButton)findViewById(R.id.choice_2);
        choiceButton[3] = (RadioButton)findViewById(R.id.choice_3);

        nextButton = (Button)findViewById(R.id.checkout_result_button);
        problemTitle = (TextView)findViewById(R.id.problemNo);

        backButton = (ImageView) findViewById(R.id.back_button);
        radioGroup = (RadioGroup)findViewById(R.id.choice_group);
        problem = (TextView)findViewById(R.id.problem);

        nextProblem();
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check whether there is a radio button is checked
                // if (radioGroup.getCheckedRadioButtonId() == -1) {
                // the method above not work
                if (checkRadioGroupWhetherHaveAChoice() == false) {
                    Toast toast =  Toast.makeText(TestProblemAvtivity.this, getResources().getString(R.string.pleaseChooseOneChoice), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM, 0, getWindowManager().getDefaultDisplay().getHeight() / 4);
                    toast.show();
                } else {
                    nextProblem();
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(TestProblemAvtivity.this);
                dialogBuilder.setTitle(getResources().getString(R.string.confirmQuitTestTitle));
                dialogBuilder.setMessage(getResources().getString(R.string.confirmQuitTestMsg));
                dialogBuilder.setNegativeButton(getResources().getString(R.string.negative), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // do nothing
                    }
                });
                dialogBuilder.setPositiveButton(getResources().getString(R.string.positive), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TestProblemAvtivity.this.finish();
                    }
                });
                dialogBuilder.show();
            }
        });
    }

    private void nextProblem() {
        ++problemNo;
        if (problemNo < problemID.length) {
            // not last problem
            if (problemNo < problemID.length - 1) {
                nextButton.setText(getResources().getString(R.string.nextProblem));
            } else {
                nextButton.setText(getResources().getString(R.string.checkoutResult));
            }
            problem.setText(getResources().getString(problemID[problemNo]));
            problemTitle.setText("第" + Integer.toString(problemNo + 1) + "题");
            radioGroup.clearCheck();
        } else {
            // go to result
            startActivity(new Intent(TestProblemAvtivity.this, Result.class));
            TestProblemAvtivity.this.finish();
        }
    }

    private boolean checkRadioGroupWhetherHaveAChoice() {
        for (int i = 0; i < 4; ++i) {
            if (choiceButton[i].isChecked() == true)
                return true;
        }
        return false;
    }
}
