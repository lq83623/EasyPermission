package com.laiqi.easypermission.sample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;


import com.laiqi.easypermission.sample.R;

import java.util.List;

/**
 * Created by laiqi on 2017-09-28.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        findViewById(R.id.phone_btn).setOnClickListener(this);
        findViewById(R.id.camera_btn).setOnClickListener(this);
        findViewById(R.id.photo_btn).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.phone_btn:
                PermissionCommon.getInstance().queryPermission(this, PermissionCommon.CALL_REQUEST_CODE,
                        new PermissionCommon.IPermissionResults() {
                            @NonNull
                            @Override
                            public void success() {
                                Log.d("laiqi" ,"successed");
                            }

                            @Nullable
                            @Override
                            public void failed(List<String> deniedPermissions) {
                                Log.d("laiqi" ,"failed");
                            }
                        });
                break;
            case R.id.camera_btn:
                PermissionCommon.getInstance().queryPermission(this, PermissionCommon.CAMERA_REQUEST_CODE,
                        new PermissionCommon.IPermissionResults() {
                            @NonNull
                            @Override
                            public void success() {
                                Log.d("laiqi" ,"successed");
                            }

                            @Nullable
                            @Override
                            public void failed(List<String> deniedPermissions) {
                                Log.d("laiqi" ,"failed");
                            }
                        });


                break;
            case R.id.photo_btn:
                PermissionCommon.getInstance().queryPermission(this, PermissionCommon.PHOTO_REQUEST_CODE,
                        new PermissionCommon.IPermissionResults() {
                            @NonNull
                            @Override
                            public void success() {
                                Log.d("laiqi" ,"successed");
                            }

                            @Nullable
                            @Override
                            public void failed(List<String> deniedPermissions) {
                                Log.d("laiqi" ,"failed");
                            }
                        });
                break;
        }

    }
}
