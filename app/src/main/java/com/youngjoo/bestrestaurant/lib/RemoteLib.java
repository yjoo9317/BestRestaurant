package com.youngjoo.bestrestaurant.lib;

import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;

import com.youngjoo.bestrestaurant.remote.RemoteService;
import com.youngjoo.bestrestaurant.remote.ServiceGenerator;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by samsung on 2017. 12. 28..
 */

public class RemoteLib {

    private static volatile RemoteLib sInstance;

    public static RemoteLib getInstance(){
        if(sInstance == null){
            synchronized (RemoteLib.class){
                sInstance = new RemoteLib();
            }
        }
        return sInstance;
    }

    public boolean isConnected(Context context){
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();

        if(info == null){
            return false;
        } else {
            return true;
        }
    }

    public void uploadProfileIcon(int memberSeq, File file){
        RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);

    }

    public void uploadFoodImage(int imageSeq, String memo, File file, final Handler handler){

    }
}
