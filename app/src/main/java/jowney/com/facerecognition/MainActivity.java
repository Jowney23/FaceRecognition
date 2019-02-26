package jowney.com.facerecognition;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;

import jowney.com.common.utils.L;
import jowney.com.facerecognition.video.IRFragment;
import jowney.com.facerecognition.video.VideoFragment;

/**
 * @author Jowney
 * @time 2017-10-20  17:18
 * @describe
 */
public class MainActivity extends Activity {
    VideoFragment videoFragment;
    IRFragment irFragment;
    private static  Activity mainActivity;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity = this;
        setDefaultFragment();

        L.i("MainActivity" + Thread.currentThread().getId());
    }
    public static Activity getMainActivity(){
        return  mainActivity;
    }
    private void setDefaultFragment() {
      /*  FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        videoFragment = new VideoFragment();
        transaction.replace(R.id.content_video, videoFragment);
        transaction.commit();*/
      //rest
        //amend

        //wo
//dog1

        //dog2
        //cat1
        //dog3

        //1
        //2
        //3
        //4
//第二次本地提交，并会发生冲突

//dog1

        //dog2
        //cat1
        //dog3

        //1
        //2
        //3

        //4

//我是王工你会不会和我冲突呢

        //我是李工



        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        irFragment = new IRFragment();
        transaction.replace(R.id.content_video, irFragment);
        transaction.commit();
    }

}
