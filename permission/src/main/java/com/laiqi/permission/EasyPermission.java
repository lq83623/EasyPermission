package com.laiqi.permission;

import android.app.Activity;
import android.support.annotation.NonNull;

/**
 * Created by laiqi on 2017-09-28.
 */

public class EasyPermission {

    @NonNull
    public static IPermissionQuery initialize(@NonNull Activity activity){
        return new DefaultPermissionQuery(activity);
    }

}
