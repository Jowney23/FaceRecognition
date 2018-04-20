package jowney.com.facerecognition.video.thread;

import android.graphics.Bitmap;
import android.os.Environment;

import java.util.Date;

import android_facerecgonize_api.FaceRecogize;
import jowney.com.common.utils.L;
import jowney.com.common.utils.Sleep;
import jowney.com.facerecognition.video.model.IVideoModel;
import jowney.com.facerecognition.video.entity.VideoFace;

/**
 * @author Jowney
 * @time 2017-10-31  9:30
 * @describe :生成VideoFace的Feature;
 */
public class GenVideoFaceFeatureThread extends Thread {
    private IVideoModel iVideoModel;
    private static final String TAG = "GenVideoFaceFeatureThread";
    private int genFeatureReturn;
    private FaceRecogize faceRecogize;
    public GenVideoFaceFeatureThread(IVideoModel iVideoModel) {
        this.iVideoModel = iVideoModel;
    }

    @Override
    public void run() {
        super.run();
        faceRecogize = new FaceRecogize();
        faceRecogize.InitialParam(0.30f, Environment.getExternalStorageDirectory().toString()+"/wltlib");
        while (true) {
       //     L.i(GenCardFaceFeatureThread.TAG, "人脸建模线程");
            //在人脸检测线程中,始终保持listFace队列中有两个没有特征值的VideoFace对象,供GenFeature线程使用
            Sleep.s(10);
            genFeatureReturn = -1;
            VideoFace videoFace = iVideoModel.getFaceFromQueue();


            if (videoFace != null) {
                genFeatureReturn = genFeature(videoFace);
            }
            if (genFeatureReturn != 0) {
                //最终建模失败,重新开始建模

              //  L.i(GenCardFaceFeatureThread.TAG, "建模失败");
            } else {
                //GenFeature成功
                L.i(GenCardFaceFeatureThread.TAG, "人脸建模成功");
                iVideoModel.putFeatureFaceInQueue(videoFace);
            }
        }
    }

    /**
     * 如果建模失败需要将VideoFace对象销毁,如果建模成功则给VideoFace的feature属性赋值
     *
     * @param videoFace (OUT)
     * @return
     */
    private int genFeature(VideoFace videoFace) {
        {
            Date startDate = null;
            Date endDate = null;
            int nVideoWidth = 0;
            int nVideoHeight = 0;
            int[] pixels2 = null;
            boolean genFeatureResult = false;
            long diff = 0;
            Bitmap videoFrameBitmap = videoFace.getVideoFrameBitmap();

          //  Bitmap mirrorVideoFrameBitmap = FaceDetect.Mirror(videoFrameBitmap);
            nVideoWidth = videoFrameBitmap.getWidth();
            nVideoHeight = videoFrameBitmap.getHeight();
            pixels2 = new int[nVideoWidth * nVideoHeight];
         /*   FaceInfo faceInfo = new FaceInfo(videoFace.getFaceRect().left,
                    videoFace.getFaceRect().top,
                    videoFace.getFaceRect().right,
                    videoFace.getFaceRect().bottom);*/

            videoFrameBitmap.getPixels(pixels2, 0, nVideoWidth, 0, 0, nVideoWidth, nVideoHeight);
            // 建模开始
            startDate = new Date(System.currentTimeMillis());
            float[] feature = faceRecogize.genSiFaceFeature(pixels2, nVideoWidth, nVideoHeight,4,3);
          //  genFeatureResult = FaceRecogize.genFaceFeature(pixels2, nVideoWidth, nVideoHeight, faceInfo, 2);
           /* if (!genFeatureResult) {
                L.e(TAG, "Create video feature fail.");
                return -2;
            }*/
            endDate = new Date(System.currentTimeMillis());
            diff = endDate.getTime() - startDate.getTime();
            L.d(GenCardFaceFeatureThread.TAG, "视频人脸の建模时间(ms): " + diff);
            if (null == feature) {
                L.e(TAG, "Get feature fail.");
                return -3;
            }
            videoFace.setFeature(feature);
            return 0;
        }
    }
}

