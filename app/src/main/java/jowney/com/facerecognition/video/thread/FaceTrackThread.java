package jowney.com.facerecognition.video.thread;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.view.SurfaceHolder;

import jowney.com.common.utils.Sleep;
import jowney.com.facerecognition.video.model.IVideoModel;
import jowney.com.facerecognition.video.IVideoPresenter;
import jowney.com.facerecognition.video.entity.VideoFace;
import jowney.com.facerecognition.video.entity.VideoFrame;
import jowney.com.facerecognition.video.facedetectutils.FaceDetect;
import jowney.com.facerecognition.video.facedetectutils.entity.FaceRect;

import static jowney.com.facerecognition.video.entity.VideoFrame.PREVIEW_HEIGHT;
import static jowney.com.facerecognition.video.entity.VideoFrame.PREVIEW_WIDTH;

/**
 * @author Jowney
 * @time 2017-10-24  16:08
 * @describe
 */
public class FaceTrackThread extends Thread {
    private static final String TAG = FaceTrackThread.class.getSimpleName();
    private IVideoModel<VideoFrame> iVideoModel;
    private VideoFrame videoFrame = null;
    private int faceNum = 0;
    private int count = 300;
    private SurfaceHolder surfaceHolder;
    private Matrix mScaleMatrix;
    private static Boolean aBoolean = true;
    private long lastTime = 0;
    private long timeDiff = 0;

    public FaceTrackThread(IVideoModel iVideoModel, IVideoPresenter iVideoPresenter) {
       iVideoModel.getFrameFromQueue();
        this.iVideoModel = iVideoModel;
        this.surfaceHolder = iVideoPresenter.getDrawFaceRectHolder();
        this.mScaleMatrix = new Matrix();
    }

    @Override
    public void run() {
        super.run();
        while (true) {
       //     L.i(GenCardFaceFeatureThread.TAG, "人脸检测线程");
            videoFrame = iVideoModel.getFrameFromQueue();

            if (videoFrame == null) {
                Sleep.s(100);
                continue;
            }
            FaceRect[] faceRects = FaceDetect.trackFace(videoFrame.getNv21(), PREVIEW_WIDTH, PREVIEW_HEIGHT, 1, 0);
            faceNum = faceRects.length;
            //   L.i(GenCardFaceFeatureThread.TAG, "人脸数量 +" + faceNum);
            Canvas canvas = null;
            canvas = surfaceHolder.lockCanvas();

            mScaleMatrix.setScale(canvas.getWidth() / (float) PREVIEW_WIDTH, canvas.getHeight() / (float) PREVIEW_HEIGHT);
            canvas.setMatrix(mScaleMatrix);
            if (null == canvas) {
                continue;
            }
            canvas.drawColor(0, PorterDuff.Mode.CLEAR);
            if (faceNum > 0) {

                //开始画人脸检测框
                for (FaceRect face : faceRects) {
                    FaceDetect.drawFaceRect(canvas, face, PREVIEW_WIDTH, PREVIEW_HEIGHT,
                            false, false);
                }
                surfaceHolder.unlockCanvasAndPost(canvas);
                //获取最大人脸
                Rect maxFaceRect = FaceDetect.getMaxSizeFace(faceRects);
                if (maxFaceRect != null) {
                    //视频帧
                    Bitmap videoBitmap = FaceDetect.nv21ToBitmap(videoFrame.getNv21(), PREVIEW_WIDTH, PREVIEW_HEIGHT);
                    //放大maxFace的人脸框,返回一个新的扩展的人脸框
                    Rect extendFaceRect = FaceDetect.extendFaceRect(maxFaceRect, PREVIEW_WIDTH, PREVIEW_HEIGHT);
                    //视频帧中最大人脸(用于比对)
                    Bitmap maxFaceBitmap = FaceDetect.cutVideoBitmap(videoBitmap, maxFaceRect);
                    //视频中扩展的最大人脸(用于显示)
                    Bitmap extendFaceBitmap = FaceDetect.cutVideoBitmap(videoBitmap, extendFaceRect);
                    //BitmapUtils.saveBitmap(maxFaceBitmap, Environment.getExternalStorageDirectory().getPath() + "/FaceTest", "test");
                    //检测到人脸以后, 初始化videoFace对象,并添加到待比对的队列
                    VideoFace videoFace = new VideoFace(videoBitmap, extendFaceBitmap, maxFaceBitmap,
                            maxFaceRect, videoFrame.getDateTime(), null);
                    iVideoModel.putFaceInQueue(videoFace);

                }
            } else {
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
       //     iVideoModel.recycleFrame(videoFrame);
        }
    }
}

