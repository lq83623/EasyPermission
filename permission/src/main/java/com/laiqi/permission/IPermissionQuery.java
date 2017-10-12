package com.laiqi.permission;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by laiqi on 2017-09-19.
 */

public interface IPermissionQuery {

    @NonNull
    IPermissionQuery permission(String[]... permissions);

    @NonNull
    IPermissionQuery requestCode(int requestCode);

    @NonNull
    IPermissionQuery callback(IPermissionListener permissionListener);

    @Nullable
    IPermissionQuery showRationale(@NonNull String rationale);

    void query();
}
