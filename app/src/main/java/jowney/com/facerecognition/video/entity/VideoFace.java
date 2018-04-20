package jowney.com.facerecognition.video.entity;

import android.graphics.Bitmap;
import android.graphics.Rect;

import java.util.Date;

/**
 * @author Jowney
 * @time 2017-10-30  15:56
 * @describe
 */
public class VideoFace {
    private Bitmap extendFaceBitmap = null;
    private Bitmap maxFaceBitmap = null;
    private Bitmap videoFrameBitmap = null;
    private Rect faceRect = null;
    private Date dateTime = null;
    private float[] feature = null;

    public VideoFace() {

    }

    public VideoFace(Bitmap videoFrameBitmap, Bitmap extendFaceBitmap, Bitmap maxFaceBitmap, Rect faceRect, Date dateTime, float[] feature) {
        this.videoFrameBitmap = videoFrameBitmap;
        this.extendFaceBitmap = extendFaceBitmap;
        this.maxFaceBitmap = maxFaceBitmap;
        this.faceRect = faceRect;
        this.dateTime = dateTime;
        this.feature = feature;
    }
    //FaceInfo faceInfo = null;

    public Bitmap getVideoFrameBitmap() {
        return videoFrameBitmap;
    }

    public void setVideoFrameBitmap(Bitmap videoFrameBitmap) {
        this.videoFrameBitmap = videoFrameBitmap;
    }

    public float[] getFeature() {
        return feature;
    }

    public void setFeature(float[] feature) {
        this.feature = feature;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }


    public Rect getFaceRect() {
        return faceRect;
    }

    public void setFaceRect(Rect faceRect) {
        this.faceRect = faceRect;
    }

    public Bitmap getExtendFaceBitmap() {
        return extendFaceBitmap;
    }

    //比对成功时 用于显示的
    public void setExtendFaceBitmap(Bitmap extendFaceBitmap) {
        this.extendFaceBitmap = extendFaceBitmap;
    }

    public Bitmap getMaxFaceBitmap() {
        return maxFaceBitmap;
    }

    //用于比对的人脸照片
    public void setMaxFaceBitmap(Bitmap maxFaceBitmap) {
        this.maxFaceBitmap = maxFaceBitmap;
    }

    public void recycleFace() {

        if (this.maxFaceBitmap != null) {
            this.maxFaceBitmap.recycle();
            this.maxFaceBitmap = null;
        }
        if(this.extendFaceBitmap != null){
            this.extendFaceBitmap.recycle();
            this.extendFaceBitmap = null;
        }
        if(this.videoFrameBitmap != null ){
            this.videoFrameBitmap.recycle();
            this.videoFrameBitmap = null;
        }
        //  System.gc();
    }
}