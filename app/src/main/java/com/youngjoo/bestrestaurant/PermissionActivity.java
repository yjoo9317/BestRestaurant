package com.youngjoo.bestrestaurant;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samsung on 2017. 12. 27..
 */

public class PermissionActivity extends AppCompatActivity {
    private static final String TAG ="PermissionActivity";
    private static final int PERMISSION_MULTI_CODE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        if(Build.VERSION.SDK_INT < 23){
            goToIndexActivity();
        } else {
            if(isGranted())
                goToIndexActivity();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        if(grantResults.length == 0)
            return;
        switch(requestCode){
            case PERMISSION_MULTI_CODE:
                checkPermissionsResult(permissions, grantResults);
                break;
        }
    }

    private void checkPermissionsResult(String[] permissions, int[] grantResults){
        boolean granted = true;

        for(int i = 0; i < permissions.length; i++){
            if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                granted = false;
            }
        }
        if(granted)
            goToIndexActivity();

    }

    private boolean isGranted(){
        String[] permissions = new String[]{Manifest.permission.CAMERA,
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.ACCESS_FINE_LOCATION};

        List<String> requiredPermissionList = null;

        for(String permission : permissions){
            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
                if(requiredPermissionList == null){
                    requiredPermissionList = new ArrayList<>();
                }
                requiredPermissionList.add(permission);
            }
        }
        if(requiredPermissionList != null){
            ActivityCompat.requestPermissions(this,
                    requiredPermissionList.toArray(new String[requiredPermissionList.size()]),
                    PERMISSION_MULTI_CODE);
            return false;
        }

        return true;
    }

    private void showPermissionDialog(){
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(R.string.permission_setting_title);
        dialog.setMessage(R.string.permission_setting_message);
        dialog.setPositiveButton(R.string.setting, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                Toast.makeText(PermissionActivity.this,
                        R.string.permission_setting_restart, Toast.LENGTH_SHORT).show();
                PermissionActivity.this.finish();
                goToAppSettingActivity();
            }
        });
        dialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                PermissionActivity.this.finish();
            }
        });
        dialog.show();
    }

    private void goToIndexActivity(){
        Intent intent = new Intent(this, IndexActivity.class);
        startActivity(intent);
        finish();
    }

    private void goToAppSettingActivity(){
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }

}
