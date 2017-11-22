package com.pear.threekingdom;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.jar.Manifest;

/**
 * Created by Young on 2017/11/21.
 */
public class Detail extends AppCompatActivity {
    public static final int MEDIA_TYPE_IMAGE = 1;
    ImageView edit, save, img, back;
    LinearLayout detailLayout, inputLayout ;
    EditText nameInput, nicknameInput, nationInput,
             minzuInput, birthplaceInput,achievementInput;
    TextView name, nickname, nation,  minzu, birthplace,
             achievement, text0, title;
    Button delete;
    boolean isEmpty;
    boolean isEdit;
    private static final int IMAGE = 1;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);
        init();
        getBundle();
        //isEmpty = true;
        addListener();
        if (isEmpty) {
            editHero();
        }
    }
    void getBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            isEmpty = true;
        } else {
            isEmpty = false;
            name.setText(bundle.getString("name"));
            nickname.setText(bundle.getString("alias"));
            birthplace.setText(bundle.getString("native_place"));
            nation.setText(bundle.getString("work_for"));
            //minzu.setText(bundle.getString(""));
            achievement.setText("achievement");
        }

    }
    void init() {
        edit = (ImageView)findViewById(R.id.edit);
        save = (ImageView)findViewById(R.id.save);
        detailLayout = (LinearLayout)findViewById(R.id.detail_layout);
        inputLayout = (LinearLayout)findViewById(R.id.input_layout);
        nameInput = (EditText)findViewById(R.id.name_input);
        nicknameInput = (EditText)findViewById(R.id.nickname_input);
        nationInput = (EditText)findViewById(R.id.nation_input);
        minzuInput = (EditText)findViewById(R.id.minzu_input);
        birthplaceInput = (EditText)findViewById(R.id.birthplace_input);
        achievementInput = (EditText)findViewById(R.id.achievement_input);
        name = (TextView)findViewById(R.id.name);
        nickname = (TextView)findViewById(R.id.nickname);
        nation = (TextView)findViewById(R.id.nation);
        minzu = (TextView)findViewById(R.id.minzu);
        birthplace = (TextView)findViewById(R.id.birthplace);
        achievement = (TextView)findViewById(R.id.achievement);
        delete = (Button)findViewById(R.id.delete);
        img = (ImageView)findViewById(R.id.img);
        text0 = (TextView)findViewById(R.id.text0);
        title = (TextView)findViewById(R.id.title);
        back = (ImageView)findViewById(R.id.back);
    }
    //  进入编辑状态
    private void editHero() {
        isEdit = true;
        //  图标变成保存
        edit.setVisibility(View.GONE);
        save.setVisibility(View.VISIBLE);
        //  显示输入界面
        detailLayout.setVisibility(View.GONE);
        inputLayout.setVisibility(View.VISIBLE);

        text0.setVisibility(View.INVISIBLE);
        nameInput.setText(name.getText());
        nicknameInput.setText(nickname.getText());
        nationInput.setText(nation.getText());
        minzuInput.setText(minzu.getText());
        birthplaceInput.setText(birthplace.getText());
        achievementInput.setText(achievement.getText());
        if (!isEmpty) {
            delete.setVisibility(View.VISIBLE);
        } else {
            title.setText("请输入信息");
        }
    }
    //  保存
    private void saveHero() {
        isEdit = false;
        CharSequence  _name = nameInput.getText();
        CharSequence _nickname = nicknameInput.getText();
        CharSequence _nation = nationInput.getText();
        CharSequence _minzu = minzuInput.getText();
        CharSequence _birthplace = birthplaceInput.getText();
        CharSequence _achievement = achievementInput.getText();
        //  只判断是否输入名称
        if (TextUtils.isEmpty(_name)) {
            Toast.makeText(getApplicationContext(), "请输入人物名称", Toast.LENGTH_SHORT).show();
        } else {
            save.setVisibility(View.GONE);
            edit.setVisibility(View.VISIBLE);
            inputLayout.setVisibility(View.GONE);
            detailLayout.setVisibility(View.VISIBLE);
            delete.setVisibility(View.GONE);

            name.setText(_name);
            nickname.setText(_nickname);
            nation.setText(_nation);
            minzu.setText(_minzu);
            birthplace.setText(_birthplace);
            achievement.setText(_achievement);
            String msg = "您已成功修改此人物";
            if (isEmpty) {
                isEmpty = false;
                msg = "您已成功创建人物"+_name;
                //  add(); 向数据库添加新的
            } else {
                //  update(); 向数据库更新
            }
            title.setText(_name+"的资料");
            text0.setText(_name);
            text0.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_SHORT).show();
        }

    }
    private void addListener() {
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editHero();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveHero();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Detail.this);
                alertDialog.setTitle("Warning").setMessage("是否删除此人物");
                alertDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        //  回到主页面
                    }
                });
                alertDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //  do nothing
                    }
                });
                alertDialog.show();
                //  删除英雄
            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEdit) {
                    vertifyStoragePermissions(Detail.this);
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    void choosePhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMAGE);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取图片路径
        if (requestCode == IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
            if( imagePath != null) {
                showImage(imagePath);
            }
            c.close();
        }
    }
    //加载图片
    private void showImage(String imaePath){
        Bitmap bm = BitmapFactory.decodeFile(imaePath);
        img.setImageBitmap(bm);
    }
    public void vertifyStoragePermissions(Activity activity) {
        try {
            int permission = ActivityCompat.checkSelfPermission(activity, "android.permission.READ_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            } else {
                choosePhoto();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            choosePhoto();
        } else {

        }
    }
}