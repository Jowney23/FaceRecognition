package jowney.com.facerecognition;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.iflytek.cloud.SpeechUtility;

import android_facerecgonize_api.FaceRecogize;
import jowney.com.common.utils.FileUtils;
import jowney.com.common.utils.L;
import jowney.com.common.utils.T;
import jowney.com.facerecognition.video.irdeviceutile.IrDeviceUtile;

/**
 * @author Jowney
 * @time 2017-10-24  12:59
 * @describe
 */
public class MyApplication extends Application {
    private static Context context;
    private static  FaceRecogize faceRecogize;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        //打开或关闭log和Toast
        L.isDebug = true;
        T.isShow = true;
        //初始化人脸检测库
        SpeechUtility.createUtility(MyApplication.this, "appid="+getString(R.string.app_id));
        //加载人脸比对配置文件(地址固定)
        FileUtils.copyFilesFassets(context,"models", Environment.getExternalStorageDirectory().toString()+"/wltlib");
        //
        FileUtils.makeDir(Environment.getExternalStorageDirectory().toString()+"/FaceData");
        /*//加载人脸识比对库
         faceRecogize = new FaceRecogize();
        faceRecogize.InitialParam(0.30f, Environment.getExternalStorageDirectory().toString()+"/wltlib");*/
       /* if (b){
            T.showShort(context,"人脸比对库加载成功");
        }else {
            T.showShort(context,"人脸比对库加载失败请重启软件");
        }*/

    }
    public static FaceRecogize getFaceRecogize(){
        return  faceRecogize;
    }
    public static Context getAppContext(){
     return context;
 }
}
