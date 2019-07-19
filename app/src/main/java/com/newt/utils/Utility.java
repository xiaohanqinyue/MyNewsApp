package com.newt.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Administrator on 2019/4/9.
 */

public class Utility {


    /**
     * 陷入通知栏
     */
    public static void immerse(Activity context) {
        if (Build.VERSION.SDK_INT >= 21) {  //安卓版本号>=5.0
            View decorView = context.getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            context.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }


}
