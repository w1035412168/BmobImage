package com.example.anxia.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.nio.file.Path;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

public class Main2Activity extends AppCompatActivity {
    private Button backbtn,btnUpload;
    private Uri uri;
    private File file;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
           uri = data.getData();
            Log.i("绝对地址", "onActivityResult: "+uri);
            uploadImage();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
//        final Intent intent= getIntent();
        btnUpload=(Button) findViewById(R.id.upload);
//        String str=intent.getExtras().getString("data");
        backbtn = (Button) findViewById(R.id.btn_back);
//       Toast.makeText(this,str,Toast.LENGTH_LONG).show();

//        backbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Bundle bundle = new Bundle();
//                bundle.putString("返回数据","这是返回的数据");
//                Intent intent = new Intent();
//                intent.putExtras(bundle);
//                setResult(1,intent);
//                finish();
//
//            }
//        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);


            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void uploadImage() {
        file=new File(PathGetter.getPath(this,uri));
        final BmobFile bmobFile=new BmobFile(file);
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
             if (e==null){
                 Toast.makeText(Main2Activity.this, "Succsse", Toast.LENGTH_SHORT).show();
                BmobImage bmobImage=new BmobImage(bmobFile);
                bmobImage.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e==null){
                            Log.i("img", "done: suc");
                        }else{
                            Log.i("img", "done: fal");
                        }
                    }
                });

             }else{
                 Toast.makeText(Main2Activity.this, ""+e.getMessage()+e.getErrorCode(), Toast.LENGTH_SHORT).show();
             }
            }
        });
    }
}
