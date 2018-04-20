package jowney.com.facerecognition.video;

import android.graphics.ImageFormat;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;

import java.io.IOException;

import jowney.com.facerecognition.MainActivity;
import jowney.com.facerecognition.cardreader.CardReaderGA;
import jowney.com.facerecognition.cardreader.IIDCardReader;
import jowney.com.facerecognition.video.entity.VideoFrame;
import jowney.com.facerecognition.video.model.IVideoModel;
import jowney.com.facerecognition.video.model.VideoModel;
import jowney.com.facerecognition.video.thread.FaceTrackThread;
import jowney.com.facerecognition.video.thread.GenCardFaceFeatureThread;
import jowney.com.facerecognition.video.thread.GenVideoFaceFeatureThread;
import jowney.com.facerecognition.video.thread.MatchThread;
import jowney.com.facerecognition.video.thread.SwipeCardThread;

/**
 * @author Jowney
 * @time 2017-10-24  11:18
 * @describe
 */
public class VideoPresenter implements SurfaceHolder.Callback, IVideoPresenter {
    private IVideoView iVideoView;
    private IVideoModel  iVideoModel;
    private SurfaceHolder cameraPreviewHolder;
    private SurfaceHolder drawFaceRectHolder;
    private FaceTrackThread faceTrackThread;
    private GenVideoFaceFeatureThread genFeatureThread;
    private SwipeCardThread swipeCardThread;
    private GenCardFaceFeatureThread genCardFaceFeatureThread;
    private MatchThread matchThread;
    private Camera mCamera;
    private final static int MAX_FPS = 10;    //帧率
    private final static int FRAME_PERIOD = (1000 / MAX_FPS); // the frame period
    private long lastTime = 0;
    private long timeDiff = 0;


     VideoPresenter(IVideoView iVideoView) {
        this.iVideoView = iVideoView;
        this.initViewWidget();
        this.cameraPreViewHolderCallBack();
        this.iVideoModel = new VideoModel();
        //检测人脸线程
        faceTrackThread = new FaceTrackThread(this.iVideoModel, this);
        faceTrackThread.start();
        //生成人脸特征值线程
        genFeatureThread = new GenVideoFaceFeatureThread(this.iVideoModel);
        genFeatureThread.start();
        //打开读卡器,并开启刷卡线程
        IIDCardReader iidCardReader = new CardReaderGA(MainActivity.getMainActivity());
        iidCardReader.openCardReader();
        swipeCardThread = new SwipeCardThread(this.iVideoModel,iidCardReader);
        swipeCardThread.start();
        //生成身份证图片特征值线程
        genCardFaceFeatureThread = new GenCardFaceFeatureThread(this.iVideoModel);
        genCardFaceFeatureThread.start();
        //最终比对线程(视频人脸与身份证照)
        matchThread = new MatchThread(this.iVideoModel);
        matchThread.start();

    }

    //用于初始化 Fragment中控件
    private void initViewWidget() {
        //设置cameraPreviewHolder
        this.iVideoView.getCameraPreViewView().getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        this.cameraPreviewHolder = this.iVideoView.getCameraPreViewView().getHolder();
        //设置DrawFaceRectView ,drawFaceRectHolder
        this.iVideoView.getDrawFaceRectView().setZOrderOnTop(true);
        this.iVideoView.getDrawFaceRectView().getHolder().setFormat(PixelFormat.TRANSLUCENT);
        this.drawFaceRectHolder = this.iVideoView.getDrawFaceRectView().getHolder();
    }

    public void cameraPreViewHolderCallBack() {
        this.cameraPreviewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        this.cameraPreviewHolder.setKeepScreenOn(true);
        this.cameraPreviewHolder.addCallback(this);
    }

    //启动预览
    private void startPreview() {
        {
            if (null != mCamera) {
                return;
            }
            try {
                mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            Camera.Parameters params = mCamera.getParameters();
            params.setPreviewFormat(ImageFormat.NV21);
            params.setPreviewSize(VideoFrame.PREVIEW_WIDTH, VideoFrame.PREVIEW_HEIGHT);
            mCamera.setParameters(params);
            // 设置显示的偏转角度，大部分机器是顺时针90度，某些机器需要按情况设置
            mCamera.setDisplayOrientation(0);
            mCamera.setPreviewCallback(new Camera.PreviewCallback() {
                @Override
                public void onPreviewFrame(byte[] bytes, Camera camera) {
                    timeDiff = System.currentTimeMillis() - lastTime;
                    if (timeDiff < FRAME_PERIOD) {
                        return;
                    }
                    lastTime = System.currentTimeMillis();
                    iVideoModel.putFrameToQueue(bytes);
                }
            });

            try {

                mCamera.setPreviewDisplay(cameraPreviewHolder);
                mCamera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Camera.Size getBestPreviewSize(int width, int height, Camera.Parameters parameters) {
        Camera.Size result = null;

        for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
            Log.i("joy", "宽：" + String.valueOf(size.width) + "高：" + String.valueOf(size.height));
            if (size.width <= width && size.height <= height) {
                if (result == null) {
                    result = size;
                } else {
                    int resultArea = result.width * result.height;
                    int newArea = size.width * size.height;
                    if (newArea > resultArea) {
                        result = size;
                    }
                }
            }
        }

        return result;
    }

    @Override
    public SurfaceHolder getCameraPreviewHolder() {
        return this.cameraPreviewHolder;
    }

    @Override
    public SurfaceHolder getDrawFaceRectHolder() {
        return this.drawFaceRectHolder;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        startPreview();

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }
}
