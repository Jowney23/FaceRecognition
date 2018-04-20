package jowney.com.facerecognition.video.thread;

import android.os.Environment;

import java.util.Date;
import java.util.Deque;

import android_facerecgonize_api.FaceRecogize;
import jowney.com.common.utils.L;
import jowney.com.common.utils.Sleep;
import jowney.com.facerecognition.cardreader.IdentityCard;
import jowney.com.facerecognition.video.model.IVideoModel;
import jowney.com.facerecognition.video.entity.MatchResult;
import jowney.com.facerecognition.video.entity.VideoFace;

/**
 * @author Jowney
 * @time 2017-10-27  17:49
 * @describe
 */
public class MatchThread extends Thread {
    private static final String TAG = "MatchThread";
    //设置缓存人脸超时时间
    private static final int SET_LIMIT_FACE_TIMEOUT = 10000;
    //设置缓存身份证超时时间
    private static final int SET_LIMIT_IDCARD_TIMEOUT = 10000;
    private IVideoModel iVideoModel;
    private Deque<VideoFace> listMatchingFace;
    private IdentityCard identityCard;
    private MatchResult matchResult;
    //读到身份证时的时间
    private long readCardTime;
    private long videoFaceStartTime;
    private long currentTime;
    private FaceRecogize faceRecogize;
    public MatchThread(IVideoModel iVideoModel) {
        this.iVideoModel = iVideoModel;
    }

    @Override
    public void run() {
        super.run();
        faceRecogize = new FaceRecogize();
        faceRecogize.InitialParam(0.30f, Environment.getExternalStorageDirectory().toString()+"/wltlib");
        while (true) {
         //   L.i(GenCardFaceFeatureThread.TAG, "比对线程");
            Sleep.s(10);
            identityCard = iVideoModel.getFeatureIDCardInfoeFromQueue();
            //确保将要比对的两个实例都不为空
            if (identityCard != null) {
                do {
                    L.i(GenCardFaceFeatureThread.TAG, "比对线程");
                    //得到身份证信息实例后,先判断是否过期,如果刷卡时间距现在超过3秒,判定为过期
                    readCardTime = identityCard.getReadCardTime().getTime();
                    currentTime = new Date(System.currentTimeMillis()).getTime();
                    if (currentTime - readCardTime >= SET_LIMIT_IDCARD_TIMEOUT) {
                        L.i(GenCardFaceFeatureThread.TAG, "身份证信息过期,请重新刷卡");
                        identityCard.recyclePhoto();
                        break;
                    }
                    listMatchingFace = iVideoModel.getFeatureFaceFromQueue();
                    if (listMatchingFace == null) {
                        Sleep.s(5);
                        L.i(GenCardFaceFeatureThread.TAG, "没有人脸模板,继续检测");
                        // identityCard.recyclePhoto();
                        continue;
                    }
                    for (VideoFace videoFace : listMatchingFace) {
                        //通过这两个时间来判断缓存的人脸是否超时
                        videoFaceStartTime = videoFace.getDateTime().getTime();
                        currentTime = new Date(System.currentTimeMillis()).getTime();
                        //如果缓存的人脸时间超过
                        if (currentTime - videoFaceStartTime <= SET_LIMIT_FACE_TIMEOUT) {
                            //进行匹配
                            faceMatch(identityCard, videoFace);
                            /**
                             * 若成功
                             */

                            /**
                             * 若失败
                             */
                            //不管成功失败 ,都将数据放在Lrucatch

                        } else {
                            //人脸比对失败,将实例中的Bitmap回收
                            L.i(GenCardFaceFeatureThread.TAG, "人脸模板超时,请重新刷卡");
                            videoFace.recycleFace();
                            identityCard.recyclePhoto();
                        }
                    }

                    break;
                } while (true);

            } else {
                continue;
            }
        }
    }


    private boolean faceMatch(IdentityCard identityCard, VideoFace videoFace) {

        Date startDate = null;
        Date endDate = null;
        long diff = 0;
        int nVideoWidth = 0;
        int nVideoHeight = 0;
        boolean bVideoFeature = false;
        boolean isPass = false;
        float fSim = 0.0f;
        float fMaxSim = 0.0f;
        //以读到身份证的时间为最初时间
        startDate = identityCard.getReadCardTime();
        fSim =  faceRecogize.getSimCos(videoFace.getFeature(), identityCard.getFeature(), identityCard.getFeature().length);
       // fSim = FaceRecogize.getSimCos(videoFace.getFeature(), identityCard.getFeature(), identityCard.getFeature().length);
        bVideoFeature = faceRecogize.getSim();
        endDate = new Date(System.currentTimeMillis());
        diff = endDate.getTime() - startDate.getTime();
        L.d(GenCardFaceFeatureThread.TAG, "比对分数: " + fSim + "  视频人脸建模+比对时间(ms): " + diff + "    比对结果:  " + bVideoFeature);

        return isPass;
    }

}
