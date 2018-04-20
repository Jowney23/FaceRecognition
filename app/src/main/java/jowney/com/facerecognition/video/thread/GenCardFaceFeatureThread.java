package jowney.com.facerecognition.video.thread;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.util.Date;

import android_facerecgonize_api.FaceInfo;
import android_facerecgonize_api.FaceRecogize;
import jowney.com.common.utils.L;
import jowney.com.common.utils.Sleep;
import jowney.com.facerecognition.cardreader.IdentityCard;
import jowney.com.facerecognition.video.model.IVideoModel;
import jowney.com.facerecognition.video.facedetectutils.FaceDetect;
import jowney.com.facerecognition.video.facedetectutils.ParseResult;
import jowney.com.facerecognition.video.facedetectutils.entity.FaceRect;

/**
 * @author Jowney
 * @time 2017-10-31  14:59
 * @describe
 */
public class GenCardFaceFeatureThread extends Thread {
    public static final String TAG = "GenCardFaceFeatureThread";
    private IVideoModel iVideoModel;
    private IdentityCard identityCardInfor;
    private Bitmap mIDCardBitmap;
    private float[] feature;

    public GenCardFaceFeatureThread(IVideoModel iVideoModel) {
        this.iVideoModel = iVideoModel;
    }
   private  FaceRecogize faceRecogize;
    @Override
    public void run() {
        super.run();
        faceRecogize = new FaceRecogize();
        faceRecogize.InitialParam(0.30f, Environment.getExternalStorageDirectory().toString()+"/wltlib");
        while (true) {
          //  L.i(GenCardFaceFeatureThread.TAG, "身份证图片模板 线程");
            Sleep.s(10);
            identityCardInfor = iVideoModel.getIDCardInforFromQueue();
            if (identityCardInfor == null) continue;

            mIDCardBitmap = identityCardInfor.getBmpPhoto();
            feature = genCardFeature(mIDCardBitmap);
            if (feature == null) continue;

            identityCardInfor.setFeature(feature);
            //将含有身份证跟图片特征值的IDCardInfor放入新的队列 等待对比
            iVideoModel.putFeatureIDCardInforInQueue(identityCardInfor);
        }
    }

    private float[] genCardFeature(Bitmap mIDCardBitmap) {

        if (mIDCardBitmap == null) {
            Log.e("god", "genCardFeature=>bitmapIdCard==null");
            return null;
        }
        String detectResult = null;
        boolean genCardFaceFeatureSucceed = false;
        Date startDate = null;
        int[] pixels1 = null;
        int cardPictureWidth = mIDCardBitmap.getWidth();
        int cardPictureHeight = mIDCardBitmap.getHeight();

        FaceInfo faceInfoCard = null;
        FaceRect[] faceListCard;
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~证内照人脸检测开始
        startDate = new Date(System.currentTimeMillis());

        pixels1 = new int[cardPictureWidth * cardPictureHeight];
        mIDCardBitmap.getPixels(pixels1, 0, cardPictureWidth, 0, 0, cardPictureWidth,
                cardPictureHeight);

        //synchronized (lockFaceDetect) {
        detectResult = FaceDetect.faceDetectARGB(mIDCardBitmap);
        //}
        if (detectResult == null)
            return null;
        Date endDate = new Date(System.currentTimeMillis());
        long diff = endDate.getTime() - startDate.getTime();
        L.d(TAG, "证内照片 检测时间(ms): " + diff);
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~检测人脸结束

        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~证内照建模开始
        startDate = new Date(System.currentTimeMillis());
        faceListCard = ParseResult.parseResult(detectResult);

        if (faceListCard != null) {
            if (faceListCard.length == 0) {
             //   faceInfoCard = new FaceInfo(17, 29, 17 + 66, 29 + 66);
                Log.e("FaceDetect", "faceListCard.length==0, use default face position.");

            } else {
               /* faceInfoCard = new FaceInfo(faceListCard[0].bound.left,
                        faceListCard[0].bound.top,
                        faceListCard[0].bound.right,
                        faceListCard[0].bound.bottom);*/
            }
        } else {
          //  faceInfoCard = new FaceInfo(17, 29, 17 + 66, 29 + 66);
            L.e(TAG, "faceListCard == null, use defult face position.");
        }
        float [] dd =   faceRecogize.genSiFaceFeature(pixels1, cardPictureWidth, cardPictureHeight,4,1);
     //   genCardFaceFeatureSucceed = FaceRecogize.genFaceFeature(pixels1, cardPictureWidth, cardPictureHeight, faceInfoCard, 1);
      /*  if (!genCardFaceFeatureSucceed) {
            L.e(TAG, "Create card feature fail.");
            return null;
        }*/
        endDate = new Date(System.currentTimeMillis());
        diff = endDate.getTime() - startDate.getTime();
        L.d(GenCardFaceFeatureThread.TAG, "证内照片 建模时间(ms): " + diff);
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~证内照建模结束
        return dd;
    }
}
