package com.example.anxia.myapplication;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button login, cancel;
    private EditText user, pass;
    private TextView lostname, createname;
    private CheckBox remember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bmob.initialize(this,"e20807faae2aca271dc11bddc481a754");
        login = (Button) findViewById(R.id.login);
        cancel = (Button) findViewById(R.id.cancel);
        user = (EditText) findViewById(R.id.username);
        pass = (EditText) findViewById(R.id.password);
        remember=(CheckBox) findViewById(R.id.remember);
        lostname = (TextView) findViewById(R.id.lostname);
        createname = (TextView) findViewById(R.id.create);
        SharedPreferences sharedPreferences=getSharedPreferences("UserMsg",
                MODE_PRIVATE);
        if(sharedPreferences.getString("password",null)==null){
            remember.setChecked(false);
        }else{
            remember.setChecked(true);
        }
        if(sharedPreferences!=null) {
            user.setText(sharedPreferences.getString("username", ""));

            pass.setText(sharedPreferences.getString("password", ""));

            }


        login.setOnClickListener(this);
        cancel.setOnClickListener(this);
        lostname.setOnClickListener(this);
        createname.setOnClickListener(this);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case 1:
                Toast.makeText(this,
                        data.getExtras().getString("返回数据"),
                        Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                login();
                break;
            case R.id.cancel:
                quit();
                break;
            case R.id.lostname:

                Bundle bundle= new Bundle();
                bundle.putString("data","已经收到数据");

                Intent intent = new Intent(MainActivity.this,Main2Activity.class);

                intent.putExtras(bundle);
                startActivityForResult(intent,1);

                break;
            case R.id.create:
                register();
                break;
        }
    }

    private void register() {
        RegisterDialog registerDialog=new RegisterDialog(this);
        registerDialog.show();
    }

    private void quit() {
        AlertDialog.Builder alert=new AlertDialog.Builder(this);
        alert.setTitle("退出")
                .setMessage("确定要退出吗？")
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.create().show();
    }


    public void login(){
        BmobUser bmobUser=new BmobUser();
        bmobUser.setUsername(user.getText().toString());
        bmobUser.setPassword(pass.getText().toString());
        bmobUser.login(new SaveListener<BmobUser>() {
            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                if (e==null){
                    Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor sharedPreferences= getSharedPreferences("UserMsg",MODE_PRIVATE).edit();
                    sharedPreferences.putString("username",user.getText().toString());
                    if(remember.isChecked()){
                        sharedPreferences.putString("password",pass.getText().toString());
                    }else{
                        sharedPreferences.remove("password").commit();
                    }
                    sharedPreferences.commit();
                }else{
                    Toast.makeText(MainActivity.this, "错误", Toast.LENGTH_SHORT).show();
                }
            }
        });





    }
}
