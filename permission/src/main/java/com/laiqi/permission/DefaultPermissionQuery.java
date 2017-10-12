package com.laiqi.permission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by laiqi on 2017-09-19.
 */

class DefaultPermissionQuery implements IPermissionQuery,PermissionActivity.PermissionListner {
    private int mReuqestCode;
    private List<String> mDeniedPermissions;
    private String[] mPermissions;
    private Activity mActivity;
    private boolean mRationale = false;
    private String  mShowText;
    private IPermissionListener mPermissionListener;


    DefaultPermissionQuery(Activity activity) {
        mActivity = activity;
    }

    @NonNull
    @Override
    public IPermissionQuery permission(String[]... permissionsArray) {
        List<String> permissionList = new ArrayList<>();
        for (String[] permissions : permissionsArray) {
            for (String permission : permissions) {
                permissionList.add(permission);
            }
        }
        this.mPermissions = permissionList.toArray(new String[permissionList.size()]);
        return this;
    }

    @NonNull
    @Override
    public IPermissionQuery requestCode(int requestCode) {
        mReuqestCode = requestCode;
        return this;
    }

    @NonNull
    @Override
    public IPermissionQuery callback(IPermissionListener permissionListener) {
        mPermissionListener = permissionListener;
        return this;
    }

    @Nullable
    @Override
    public IPermissionQuery showRationale(@NonNull String showText ) {
        mRationale = true;
        mShowText = showText;
        return this;
    }

    @Override
    public void query() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            //callback
            mPermissionListener.onSuccess(mReuqestCode, Arrays.asList(mPermissions));
        } else {
            getDeniedPermissions();
            if (!mDeniedPermissions.isEmpty()) {
                start();
            } else {
                // callback
                mPermissionListener.onSuccess(mReuqestCode, Arrays.asList(mPermissions));
            }
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requery() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            mPermissionListener.onSuccess(mReuqestCode, Arrays.asList(mPermissions));
        } else {
            if (shouldShowRequestPermissionRationale()) {
                // denied once request again;
                showMessageOKCancel(mShowText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        start();
                    }
                });
            } else {
                getDeniedPermissions();
                if(!mDeniedPermissions.isEmpty()) {
                    mPermissionListener.onFail(mReuqestCode, mDeniedPermissions);
                } else {
                    // callback
                    mPermissionListener.onSuccess(mReuqestCode, Arrays.asList(mPermissions));
                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        ArrayList<String>  deniedList = new ArrayList<>();
        for(int i = 0; i < grantResults.length ; i++) {
            if(grantResults[i] == PackageManager.PERMISSION_DENIED) {
                deniedList.add(permissions[i]);
            }
        }

        if(deniedList.isEmpty()) {
            mPermissionListener.onSuccess(requestCode, Arrays.asList(permissions));
        } else {
            if(mRationale) {

                requery();
            } else {
                mPermissionListener.onFail(requestCode, deniedList);
            }
        }
    }

    private List<String> getDeniedPermissions() {
        mDeniedPermissions = new ArrayList<>();
        if(mPermissions != null) {
            for (int i = 0; i < mPermissions.length; i++) {
                if (PackageManager.PERMISSION_DENIED == ContextCompat.checkSelfPermission(mActivity, mPermissions[i])) {
                    mDeniedPermissions.add(mPermissions[i]);
                }
            }
        }
        return mDeniedPermissions;
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(mActivity)
                .setMessage(message)
                .setPositiveButton("设置", okListener)
                .setNegativeButton("取消", null)
                .create()
                .show();
    }
    @TargetApi(Build.VERSION_CODES.M)
    private boolean shouldShowRequestPermissionRationale(){
      getDeniedPermissions();
        boolean flag = false;
        for(String permission : mDeniedPermissions) {
          flag = mActivity.shouldShowRequestPermissionRationale(permission);
            if(flag) break;
        }
        return flag;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void start() {
        Intent intent = new Intent(mActivity, PermissionActivity.class);
        String[] deniedPermissions = mDeniedPermissions.toArray(new String[mDeniedPermissions.size()]);
        intent.putExtra(PermissionActivity.EXTRA_PERMISSION, deniedPermissions);
        intent.putExtra(PermissionActivity.EXTRA_REQUESTCODE, mReuqestCode);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PermissionActivity.setPermissionListener(this);
        mActivity.startActivity(intent);
    }

}
