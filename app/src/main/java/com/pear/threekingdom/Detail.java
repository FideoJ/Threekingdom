package com.pear.threekingdom;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.pear.threekingdom.db.DbManager;
import com.pear.threekingdom.entity.Hero;
import org.w3c.dom.Text;

/**
 * Created by Young on 2017/11/21.
 */
public class Detail extends AppCompatActivity {
    public static final int MEDIA_TYPE_IMAGE = 1;
    private ImageView edit, save, img, back;
    private LinearLayout detailLayout, inputLayout ;
    private EditText nameInput, nicknameInput,
             birthyearInput,deadyearInput, birthplaceInput,workforInput,achievementInput;
    private TextView name, nickname, gender,
            birthyear,deadyear, birthplace,workfor,achievement, text0, title;
    private RadioGroup group;
    private Button delete;
    private Bitmap bm;
    private int heroId;
    private boolean isEmpty, isEdit;
    private DbManager db;
    private String _gender;
    private RadioButton female, male;
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
            title.setText(name.getText()+"的资料");
            nickname.setText(bundle.getString("alias"));
            gender.setText(bundle.getString("gender"));
            birthyear.setText(bundle.getString("birthyear"));
            deadyear.setText(bundle.getString("deadyear"));
            birthplace.setText(bundle.getString("native_place"));
            workfor.setText(bundle.getString("work_for"));
            achievement.setText("achievement");
            text0.setText(name.getText()+"("+ birthyear.getText() + "~"+ deadyear.getText()+")");
            bm =  BitmapFactory.decodeByteArray(bundle.getByteArray("img"), 0, bundle.getByteArray("img").length);
            img.setImageBitmap(bm);
            heroId = bundle.getInt("hero_id");
            _gender = bundle.getString("gender");
            if (_gender.equals("男")) {
                male.setChecked(true);
                female.setChecked(false);
            } else {
                female.setChecked(true);
                male.setChecked(false);
            }
        }
    }
    void init() {
        _gender = "不详";
        db = new DbManager(this);
        edit = (ImageView)findViewById(R.id.edit);
        save = (ImageView)findViewById(R.id.save);
        detailLayout = (LinearLayout)findViewById(R.id.detail_layout);
        inputLayout = (LinearLayout)findViewById(R.id.input_layout);
        nameInput = (EditText)findViewById(R.id.name_input);
        nicknameInput = (EditText)findViewById(R.id.nickname_input);
        birthyearInput = (EditText)findViewById(R.id.birthyear_input);
        deadyearInput = (EditText)findViewById(R.id.deadyear_input);
        birthplaceInput = (EditText)findViewById(R.id.birthplace_input);
        workforInput = (EditText)findViewById(R.id.workfor_input);
        achievementInput = (EditText)findViewById(R.id.achievement_input);
        name = (TextView)findViewById(R.id.name);
        nickname = (TextView)findViewById(R.id.nickname);
        gender = (TextView)findViewById(R.id.gender);
        birthyear = (TextView)findViewById(R.id.birthyear);
        deadyear = (TextView)findViewById(R.id.deadyear);
        birthplace = (TextView)findViewById(R.id.birthplace);
        workfor = (TextView)findViewById(R.id.workfor);
        achievement = (TextView)findViewById(R.id.achievement);
        delete = (Button)findViewById(R.id.delete);
        img = (ImageView)findViewById(R.id.img);
        text0 = (TextView)findViewById(R.id.text0);
        title = (TextView)findViewById(R.id.title);
        back = (ImageView)findViewById(R.id.back);
        bm = ((BitmapDrawable) img.getDrawable()).getBitmap();
        group = (RadioGroup)findViewById(R.id.radioGroupID);
        female = (RadioButton)findViewById(R.id.femaleGroupID);
        male = (RadioButton)findViewById(R.id.maleGroupID);
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
        birthyearInput.setText(birthyear.getText());
        deadyearInput.setText(deadyear.getText());
        birthplaceInput.setText(birthplace.getText());
        workforInput.setText(workfor.getText());
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
        String  _name = nameInput.getText().toString();
        String _nickname = nicknameInput.getText().toString();
        String _birthyear = birthyearInput.getText().toString();
        String _deadyear = deadyearInput.getText().toString();
        String _birthplace = birthplaceInput.getText().toString();
        String _workfor = workforInput.getText().toString();
        String _achievement = achievementInput.getText().toString();

        //  只判断是否输入名称
        if (TextUtils.isEmpty(_name)) {
            Toast.makeText(getApplicationContext(), "请输入人物名称", Toast.LENGTH_SHORT).show();
        } else {
            if (TextUtils.isEmpty(_nickname)) {
                _nickname = "不详";
            }
            if (TextUtils.isEmpty(_birthplace)) {
                _birthplace= "不详";
            }
            if (TextUtils.isEmpty(_birthyear)) {
                _birthyear = "不详";
            }
            if (TextUtils.isEmpty(_deadyear)) {
                _deadyear = "不详";
            }
            if (TextUtils.isEmpty(_workfor)) {
                _workfor= "不详";
            }
            if (TextUtils.isEmpty(_achievement)) {
                _achievement = "不详";
            }
            save.setVisibility(View.GONE);
            edit.setVisibility(View.VISIBLE);
            inputLayout.setVisibility(View.GONE);
            detailLayout.setVisibility(View.VISIBLE);
            delete.setVisibility(View.GONE);

            name.setText(_name);
            nickname.setText(_nickname);
            gender.setText(_gender);
            birthyear.setText(_birthyear);
            deadyear.setText(_deadyear);
            birthplace.setText(_birthplace);
            workfor.setText(_workfor);
            achievement.setText(_achievement);
            String msg = "您已成功修改此人物";
            Hero hero = new Hero(_name, _nickname, bm,_gender, _birthyear, _deadyear, _birthplace, _workfor, _achievement);
            if (isEmpty) {
                isEmpty = false;
                msg = "您已成功创建人物"+_name;
                db.addOneHero(hero);
                //  add(); 向数据库添加新的
            } else {
                db.updateOneHero(heroId, hero);
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
                        db.deleteOneHero(heroId);
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
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton rb = (RadioButton)findViewById(checkedId);
                _gender = rb.getText().toString();
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
    private void showImage(String imagePath){
        bm = BitmapFactory.decodeFile(imagePath);
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