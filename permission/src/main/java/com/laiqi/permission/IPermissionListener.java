package com.laiqi.permission;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by laiqi on 2017-09-19.
 */

public interface IPermissionListener {
    void onSuccess(int requestCode, @NonNull List<String> grantedPermissions);
    void onFail(int requestCode, @NonNull List<String> deniedPermissions);
}
