package com.app.football.launchandguide;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.app.football.main.MainActivity;
import com.app.football.R;
import com.app.football.util.MyUtil;

import okhttp3.Cache;

public class LaunchAndGuideActivity extends AppCompatActivity implements LaunchAndGuideContract.View{

    //public static final String TAG = "LaunchAndGuideActivity";

    private static final String[] PERMISSIONS = {"android.permission.READ_EXTERNAL_STORAGE", "android.permission.READ_PHONE_STATE"};
    private static final int PERMISSION_MULTI_CODE = 2;

    // 通过SharedPreferences记录用户是否第一次使用app
    private static final String APP_STATUS = "app_status";
    private static final String IS_FIRST_INSTALL = "is_first_install";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestPermissionAndInitMyUtil();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_and_guide);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_MULTI_CODE :
            {
                for (int i : grantResults) {
                    if (i != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "这些权限是必须的,应用无法正常工作",Toast.LENGTH_LONG).show();
                        // 必须的权限，不然无法运行
                        System.exit(0);
                    }
                }
                updateInstallStatus();
                endGuide();
            }
        }
    }

    private void updateInstallStatus() {
        SharedPreferences sp = getSharedPreferences(APP_STATUS, Context.MODE_PRIVATE);
        sp.edit()
                .putBoolean(IS_FIRST_INSTALL, false)
                .apply();
    }

    // Contract.View

    @Override
    public void requestPermissionAndInitMyUtil() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        MyUtil.sScreenPPI = dm.densityDpi;
        MyUtil.sScreenWidthPixel = dm.widthPixels;

        MyUtil.sCache = new Cache(getExternalCacheDir(), 1024 * 10240);

        SharedPreferences sp = getSharedPreferences(APP_STATUS, Context.MODE_PRIVATE);
        if (sp.getBoolean(IS_FIRST_INSTALL,true)) {
            showGuide();
        } else {
            // TODO 如果用户在使用过后撤销权限，如何处理？
            endGuide();
        }
    }

    private void showGuide() {

        boolean havePermissions = true;
        for (String str : PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, str) == PackageManager.PERMISSION_DENIED) {
                havePermissions = false;
                break;
            }
        }
        if (havePermissions) {
            endGuide();
        } else {
            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.ic_warning_black_24dp)
                    .setMessage("在安卓6.0以上,应用需要你手动授予这些权限才能正常使用")
                    .setCancelable(false)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(LaunchAndGuideActivity.this, PERMISSIONS, PERMISSION_MULTI_CODE);
                        }
                    })
                    .create()
                    .show();
        }
    }

    private void endGuide() {
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

}
