package com.laiqi.easypermission.sample;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.laiqi.permission.EasyPermission;
import com.laiqi.permission.IPermissionListener;
import com.laiqi.permission.IPermissionQuery;
import com.laiqi.permission.Permission;

import java.util.List;

/**
 * Created by laiqi on 2017-09-20.
 */

public class PermissionCommon {

    public static final int CAMERA_REQUEST_CODE = 20;
    public static final int CALL_REQUEST_CODE = 21;
    public static final int PHOTO_REQUEST_CODE = 22;

    private static PermissionCommon mInstance;
    private Activity mActivity;
    private int mRequestCode;
    private String mShowToast = "";
    private String mShowText = "";

    private IPermissionQuery mPermissionQuerry;
    private IPermissionResults mPermissionResults;

    private IPermissionListener mPermissionListener = new IPermissionListener() {
        @Override
        public void onSuccess(int requestCode, @NonNull List<String> grantedPermissions) {
            if (mPermissionResults != null) {
                mPermissionResults.success();
            }
        }

        @Override
        public void onFail(int requestCode, @NonNull List<String> deniedPermissions) {
            if (mPermissionResults != null) {
                mPermissionResults.failed(deniedPermissions);
            }
        }
    };

    private PermissionCommon() {
    }

    public static PermissionCommon getInstance() {
        if (mInstance == null) {
            mInstance = new PermissionCommon();
        }
        return mInstance;
    }


    /**
     * query permission
     *
     * @return
     */
    public void queryPermission(Activity activity, int requestCode, IPermissionResults permissionResults) {
        //设置权限
        setInitData(activity, requestCode, permissionResults);
        if (mPermissionQuerry != null) {
            mPermissionQuerry.query();
        }
    }


    private void setInitData(Activity activity, int requestCode, IPermissionResults permissionResults) {

        mActivity = activity;
        mRequestCode = requestCode;
        mPermissionResults = permissionResults;
        switch (mRequestCode) {
            case CAMERA_REQUEST_CODE:
                mShowText = "请设置允许相机与外部存储卡使用权限，否则无法正常使用。";
                mShowToast = "相机权限被禁用，请在[设置][权限管理]修改。";
                mPermissionQuerry =
                        EasyPermission.initialize(activity)
                                .callback(mPermissionListener)
                                .requestCode(mRequestCode)
                                .permission(Permission.CAMERA, Permission.STORAGE)
                                .showRationale(mShowText);
                break;
            case PHOTO_REQUEST_CODE:
                mShowText = "请设置允许外部存储卡使用权限，否则无法正常使用。";
                mShowToast = "照片权限被禁用，请在[设置][权限管理]修改。";
                mPermissionQuerry =
                        EasyPermission.initialize(activity)
                                .callback(mPermissionListener)
                                .requestCode(mRequestCode)
                                .permission(Permission.STORAGE)
                                .showRationale(mShowText);
                break;
            case CALL_REQUEST_CODE:
                mShowText = "请设置允许电话使用权限，否则无法正常使用。";
                mShowToast = "电话权限被禁用，请在[设置][权限管理]修改。";
                mPermissionQuerry =
                        EasyPermission.initialize(activity)
                                .callback(mPermissionListener)
                                .requestCode(mRequestCode)
                                .permission(Permission.PHONE)
                                .showRationale(mShowText);
                break;
        }
    }



    public interface IPermissionResults {
        @NonNull
        void success();

        @Nullable
        void failed(List<String> deniedPermissions);
    }
}


