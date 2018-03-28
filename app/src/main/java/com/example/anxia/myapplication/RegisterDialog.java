package com.example.anxia.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import static android.content.ContentValues.TAG;

/**
 * Created by anxia on 2017/12/10.
 */

public class RegisterDialog extends Dialog {
    EditText registerPassword,registerUsername;
    Context context;
    public RegisterDialog(@NonNull Context context) {
        super(context);
        this.context=context;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regiter);
        Button btnRegister=findViewById(R.id.btn_register);
        registerUsername=findViewById(R.id.et_user);
        registerPassword=findViewById(R.id.et_password);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobUser bmobUser=new BmobUser();
                bmobUser.setUsername(registerUsername.getText().toString());
                bmobUser.setPassword(registerPassword.getText().toString());
                bmobUser.signUp(new SaveListener<UserMessge>() {
                    @Override
                    public void done(UserMessge userMessge, BmobException e) {
                        if (e==null){

                            Toast.makeText(context, "注册成功", Toast.LENGTH_SHORT).show();
                        }else{
                            Log.e(TAG, "done: ",e);
                        }
                    }
                });
                RegisterDialog.this.dismiss();
            }
        });
    }


}
