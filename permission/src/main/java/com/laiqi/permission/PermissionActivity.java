package com.laiqi.permission;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;

/**
 * Created by laiqi on 2017-09-19.
 */
@RequiresApi(api = Build.VERSION_CODES.M)
public class PermissionActivity extends Activity {
    public static final String EXTRA_PERMISSION = "extra_Permission";
    public static final String EXTRA_REQUESTCODE = "extra_requestCode";
    public static final String EXTRA_LISTENER = "extra_listener";

    private static PermissionListner mPermissionListener;

    public static void setPermissionListener(PermissionListner permissionListener) {
        if(mPermissionListener != null) {
            mPermissionListener = null;
        }
        mPermissionListener = permissionListener;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String[] permissions = intent.getStringArrayExtra(PermissionActivity.EXTRA_PERMISSION);
        if( permissions == null ) {
            finish();
            return;
        }

        int requestCode = intent.getIntExtra(PermissionActivity.EXTRA_REQUESTCODE,-1);
        ActivityCompat.requestPermissions(this, permissions, requestCode);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        if(mPermissionListener != null) {
            mPermissionListener.onRequestPermissionsResult(requestCode,permissions,grantResults);
        }
        finish();
    }


    interface PermissionListner{
        void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                        @NonNull int[] grantResults);
    }
}
