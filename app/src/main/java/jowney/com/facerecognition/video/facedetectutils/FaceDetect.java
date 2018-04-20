package jowney.com.facerecognition.video.facedetectutils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.YuvImage;

import com.iflytek.cloud.FaceDetector;

import java.io.ByteArrayOutputStream;

import jowney.com.facerecognition.MyApplication;
import jowney.com.facerecognition.video.entity.VideoFrame;
import jowney.com.facerecognition.video.facedetectutils.entity.FaceRect;

/**
 * @author Jowney
 * @time 2017-10-24  17:33
 * @describe
 */
public class FaceDetect {

    private static FaceDetector faceDetector = null;
    private static String strResult = null;
    private static FaceRect[] result = null;

    static {
        faceDetector = FaceDetector.createDetector(MyApplication.getAppContext(), null);
    }

    /**
     * 检测静态图片中的人脸
     * @param bitmap
     * @return
     */
    public static String faceDetectARGB(Bitmap bitmap) {

        return faceDetector.detectARGB(bitmap);
    }

    /**
     * 检测视频流中人脸
     *
     * @param byFrame
     * @param width
     * @param height
     * @param flag
     * @param direction
     * @return 将检测到的人脸信息封装在FaceRect数组
     */
    public static FaceRect[] trackFace(byte[] byFrame, int width, int height, int flag, int direction) {
        strResult = faceDetector.trackNV21(byFrame, width, height, flag, direction);
        if (!strResult.isEmpty()) {
            result = ParseResult.parseResult(strResult);
        }
        return result;
    }

    /**
     * @param bitmap
     * @return
     */
    public static Bitmap Mirror(Bitmap bitmap) {
        // 图片旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postScale(-1, 1);   //镜像水平翻转
        // 得到旋转后的图片
        //m.postScale(1, -1);   //镜像垂直翻转
        //m.postScale(-1, 1);   //镜像水平翻转
        //m.postRotate(-90);  //旋转-90度
        Bitmap retBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return retBitmap;
    }

    /**
     * 从多张人脸中获取最大人脸框
     *
     * @param faceRect
     * @return
     */
    public static Rect getMaxSizeFace(FaceRect[] faceRect) {

        Rect maxRect = new Rect(0, 0, 0, 0);
        boolean bFind = false;
        if (faceRect == null || faceRect.length == 0) {
            return null;
        }

        for (FaceRect faceRect2 : faceRect) {
            int width = faceRect2.bound.width();
            if (width > maxRect.width()) {
                maxRect.set(faceRect2.bound.left, faceRect2.bound.top, faceRect2.bound.right, faceRect2.bound.bottom);
                bFind = true;
            }
        }
        if (!bFind) {

            maxRect = null;
        }
        return maxRect;
    }

    /**
     * 扩展检测到的人脸框
     *
     * @param maxFaceRect
     * @param nWidth
     * @param nHeight
     * @return
     */
    public static Rect extendFaceRect(Rect maxFaceRect, int nWidth, int nHeight) {
        Rect rect = new Rect(maxFaceRect.left, maxFaceRect.top, maxFaceRect.right, maxFaceRect.bottom);
        int nIrisDistance = (rect.right - rect.left) / 2;
        int nFaceX = (rect.left - nIrisDistance * 0.8) < 0 ? 0 : (int) (rect.left - nIrisDistance * 0.8);
        int nFaceY = (rect.top - nIrisDistance * 1.8) < 0 ? 0 : (int) (rect.top - nIrisDistance * 1.8);
        int nFaceW = (nFaceX + nIrisDistance * 3.6) > nWidth ? (nWidth - nFaceX) : (int) (nIrisDistance * 3.6);
        int nFaceH = (nFaceY + nIrisDistance * 4.4) > nHeight ? (nHeight - nFaceY) : (int) (nIrisDistance * 4.4);
        rect.left = nFaceX;
        rect.top = nFaceY;
        rect.right = nFaceX + nFaceW;
        rect.bottom = nFaceY + nFaceH;
        return rect;
    }

    /**
     * 将视频流转换为Bitmap
     *
     * @param nv21
     * @param width
     * @param height
     * @return
     */
    public static Bitmap nv21ToBitmap(byte[] nv21, int width, int height) {
        YuvImage yuvimage = new YuvImage(nv21, ImageFormat.NV21, width, height, null);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        yuvimage.compressToJpeg(new Rect(0, 0, VideoFrame.PREVIEW_WIDTH, VideoFrame.PREVIEW_HEIGHT), 90,
                baos);
        byte[] jdata = baos.toByteArray();
        Bitmap bitmapVideo = BitmapFactory.decodeByteArray(jdata, 0, jdata.length);// 视频
        return bitmapVideo;
    }

    /**
     * 裁剪视频图片中的人脸
     *
     * @param bm
     * @param r
     * @return
     */
    public static Bitmap cutVideoBitmap(Bitmap bm, Rect r) {
        int width = r.width(); // CR: final == happy panda!
        int height = r.height();
        Bitmap croppedImage = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        {
            Canvas canvas = new Canvas(croppedImage);
            Rect dstRect = new Rect(0, 0, width, height);
            canvas.drawBitmap(bm, r, dstRect, null);
        }
        return croppedImage;
    }

    public static void drawFaceRectTest(Canvas canvas, FaceRect face) {
        if (canvas == null) {
            return;
        }
        int d = canvas.getWidth();
        Paint paint = new Paint();
        paint.setColor(Color.rgb(255, 203, 15));
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(face.bound, paint);
    }

    /**
     * 在指定画布上将人脸框出来
     *
     * @param canvas      给定的画布
     * @param face        需要绘制的人脸信息
     * @param width       原图宽
     * @param height      原图高
     * @param frontCamera 是否为前置摄像头，如为前置摄像头需左右对称
     * @param DrawOriRect 可绘制原始框，也可以只画四个角
     */
    public static void drawFaceRect(Canvas canvas, FaceRect face, int width, int height, boolean frontCamera, boolean DrawOriRect) {
        if (canvas == null) {
            return;
        }

        Paint paint = new Paint();
        paint.setColor(Color.rgb(255, 203, 15));
        int len = (face.bound.bottom - face.bound.top) / 8;
        if (len / 8 >= 2)
            paint.setStrokeWidth(len / 8);
        else
            paint.setStrokeWidth(2);

        Rect rect = face.bound;

        if (frontCamera) {
            int left = rect.left;
            rect.left = width - rect.right;
            rect.right = width - left;
        }

        if (DrawOriRect) {
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawRect(rect, paint);
        } else {
            int drawl = rect.left - len;
            int drawr = rect.right + len;
            int drawu = rect.top - len;
            int drawd = rect.bottom + len;

            canvas.drawLine(drawl, drawd, drawl, drawd - len, paint);
            canvas.drawLine(drawl, drawd, drawl + len, drawd, paint);
            canvas.drawLine(drawr, drawd, drawr, drawd - len, paint);
            canvas.drawLine(drawr, drawd, drawr - len, drawd, paint);
            canvas.drawLine(drawl, drawu, drawl, drawu + len, paint);
            canvas.drawLine(drawl, drawu, drawl + len, drawu, paint);
            canvas.drawLine(drawr, drawu, drawr, drawu + len, paint);
            canvas.drawLine(drawr, drawu, drawr - len, drawu, paint);
        }

        if (face.point != null) {
            for (Point p : face.point) {
                if (frontCamera) {
                    p.x = width - p.x;
                }
                canvas.drawPoint(p.x, p.y, paint);
            }
        }
    }

    /**
     * 将矩形随原图顺时针旋转90度
     *
     * @param r      待旋转的矩形
     * @param width  输入矩形对应的原图宽
     * @param height 输入矩形对应的原图高
     * @return 旋转后的矩形
     */
    public static Rect RotateDeg90(Rect r, int width, int height) {
        int left = r.left;
        r.left = height - r.bottom;
        r.bottom = r.right;
        r.right = height - r.top;
        r.top = left;
        return r;
    }

    /**
     * 将点随原图顺时针旋转90度
     *
     * @param p      待旋转的点
     * @param width  输入点对应的原图宽
     * @param height 输入点对应的原图宽
     * @return 旋转后的点
     */
    public static Point RotateDeg90(Point p, int width, int height) {
        int x = p.x;
        p.x = height - p.y;
        p.y = x;
        return p;
    }
}

