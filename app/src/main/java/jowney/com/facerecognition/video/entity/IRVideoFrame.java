package jowney.com.facerecognition.video.entity;

import java.util.Date;

/**
 * @author Jowney
 * @time 2017-12-27  16:56
 * @describe
 */
public class IRVideoFrame  {
    public static int PREVIEW_WIDTH = 480;
    public  static int PREVIEW_HEIGHT = 640;

    private Date dateTime = null;

    private byte[] colorFrame = new byte[PREVIEW_WIDTH * PREVIEW_HEIGHT * 2];
    private byte[] grayFrame = new byte[PREVIEW_HEIGHT * PREVIEW_WIDTH * 2];

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Date getDateTime() {
        return this.dateTime;
    }

    public int setAllFrame(byte[] mColorFrame, byte[] mGrayFrame) {
        int nReturn = 0;

        if(mColorFrame != null && mGrayFrame != null){
            System.arraycopy(mGrayFrame, 0, this.grayFrame, 0, mGrayFrame.length);
            System.arraycopy(mColorFrame, 0, this.colorFrame, 0, mColorFrame.length);
            return nReturn;
        }
        if(mColorFrame == null && mGrayFrame != null){
            System.arraycopy(mGrayFrame, 0, this.grayFrame, 0, mGrayFrame.length);
            return nReturn;
        }
        if(mColorFrame != null && mGrayFrame == null){
            System.arraycopy(mColorFrame, 0, this.colorFrame, 0, mColorFrame.length);
            return nReturn;
        }
       /*
         public static void (Object src ,int srcPos, Object dest, int destPos, int length)
         src:源数组；	srcPos:源数组要复制的起始位置；
         dest:目的数组；	destPos:目的数组放置的起始位置；
         length:复制的长度。*/
       return -1;

    }

    public byte[] getGrayFrame() {
        return grayFrame;
    }

    public byte[] getColorFrame() {
        return colorFrame;
    }
    public void recycleFrame(){
      /*  this.grayFrame = null;
        this.colorFrame = null;
        this.dateTime = null;*/
    }
}
