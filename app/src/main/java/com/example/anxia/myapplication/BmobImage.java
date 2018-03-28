package com.example.anxia.myapplication;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import rx.Subscription;

/**
 * Created by anxia on 2018/3/25.
 */

public class BmobImage extends BmobObject {
    private BmobFile bmobFile;
    public BmobImage(BmobFile bmobFile){
        this.bmobFile=bmobFile;
    }

    @Override
    public Subscription save(SaveListener<String> listener) {
        return super.save(listener);
    }
}
