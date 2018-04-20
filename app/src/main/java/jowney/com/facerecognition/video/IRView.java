package jowney.com.facerecognition.video;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.pefis.natives.Ast2600JNI;
import com.pefis.natives.PfsBioInfo;

import jowney.com.common.utils.L;
import jowney.com.common.utils.T;
import jowney.com.facerecognition.MyApplication;
import jowney.com.facerecognition.video.irdeviceutile.IrDeviceUtile;

/**
 * @author Jowney
 * @time 2017-12-29  10:10
 * @describe
 */
public class IRView extends SurfaceView implements SurfaceHolder.Callback {
    private final static String LOG_TAG = "IRView";
    private int left,top;

    private DrawThread mThread = null;

    public IRView(Context context) {
        super(context);
        init( left, top);
    }     //Simple constructor to use when creating a view from code

    public IRView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(0,0);
    }     //Constructor that is called when inflating a view from XML

    public IRView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init( left, top);
    }     //Perform inflation from XML and apply a class-specific base style

    private void init(int left,int top) {
        this.left = left;
        this.top = top;
        //打开设设备摄像头
        //控制红外设备
        if(IrDeviceUtile.initDevice() == 1){
            T.showShort(MyApplication.getAppContext(),"设备初始化成功");
        }else {
            T.showShort(MyApplication.getAppContext(),"设备初始化失败");
        }
        //打开设备的红外灯
        Ast2600JNI.getInstance().pisFcInfraredLedControl(
                PfsBioInfo.getInstance().mContextID,
                PfsBioInfo.PFSBIO_IR_LED_HIGH_ON);
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        mThread = new DrawThread(holder);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mThread.setRun(true);
        mThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mThread.setRun(false);
        //关闭红外灯
        Ast2600JNI.getInstance().pisFcInfraredLedControl(
                PfsBioInfo.getInstance().mContextID,
                PfsBioInfo.PFSBIO_IR_LED_OFF);
        //关闭设备相机
        Ast2600JNI.getInstance().pisCloseDevice(PfsBioInfo.getInstance().mContextID);
        Ast2600JNI.getInstance().pisDestroyContext(PfsBioInfo.getInstance().mContextID);
    }

    public class DrawThread extends Thread {
        private SurfaceHolder mHolder = null;
        private boolean isRun = false;

        DrawThread(SurfaceHolder holder) {
            L.d(IRView.LOG_TAG, "DrawThread Constructor");
            mHolder = holder;

        }

        public void setRun(boolean isRun) {
            L.d(IRView.LOG_TAG, "DrawThread setRun: " + isRun);
            this.isRun = isRun;
        }

        @Override
        public void run() {
            L.d(IRView.LOG_TAG, "DrawThread run");
            int count = 0;
            byte[] mColorImageFrame = new byte[640 * 480];
            while (isRun) {
                Canvas canvas = null;
                synchronized (mHolder) {
                    try {
                        canvas = mHolder.lockCanvas();
                        canvas.drawColor(Color.WHITE);
                        Ast2600JNI.getInstance().pisFcColorImageCapture(PfsBioInfo.getInstance().mContextID, mColorImageFrame);
                        PfsBioInfo.getInstance().CreateColorBitmapFromBayer(mColorImageFrame);
                        canvas.drawBitmap(PfsBioInfo.getInstance().m_bmp, IRView.this.left, IRView.this.top, null);
                       /* L.d(IRView.LOG_TAG, "Drawing-------------");
                        canvas = mHolder.lockCanvas();
                        canvas.drawColor(Color.WHITE);
                        Paint p = new Paint();
                        p.setColor(Color.BLACK);

                        Rect r = new Rect(100, 50, 300, 250);
                        canvas.drawRect(r, p);
                        canvas.drawText("这是第" + (count++) + "秒", 100, 310, p);

                        Thread.sleep(1000);// 睡眠时间为1秒*/

                    } catch (Exception e) {
                        L.d(IRView.LOG_TAG, "throw Exception in run");
                        e.printStackTrace();
                    } finally {
                        if (null != canvas) {
                            //    L.d(IRView.LOG_TAG, "_____unlockCanvasAndPost(canvas)_____");
                            mHolder.unlockCanvasAndPost(canvas);
                        }
                    }
                }

            }
        }

    }
}
