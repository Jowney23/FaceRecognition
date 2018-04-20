package jowney.com.facerecognition.video.entity;

import java.util.Date;

/**
 * @author Jowney
 * @time 2017-10-24  14:58
 * @describe
 */
public class VideoFrame {

    public static final int PREVIEW_WIDTH = 640;
    public static final int PREVIEW_HEIGHT = 480;
    private Date dateTime = null;
    private byte[] nv21 = new byte[PREVIEW_WIDTH * PREVIEW_HEIGHT * 2];



    public Date getDateTime() {
        return dateTime;
    }

    public byte[] getNv21() {
        return this.nv21;
    }

    public int setNv21(byte[] nv21) {
        int nReturn = -1;

        if (nv21.length > PREVIEW_WIDTH * PREVIEW_HEIGHT * 2) {
            return nReturn;
        }
        System.arraycopy(nv21, 0, this.nv21, 0, nv21.length);
        return 0;
    }

    public void recycleFrame(){

    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
}
