package jowney.com.facerecognition.video.entity;

import android.graphics.Bitmap;

import java.util.Date;

/**
 * @author Jowney
 * @time 2017-10-30  16:01
 * @describe
 */
public class MatchResult {

    public static final class MatchState {
        /**
         * 比对成功
         */
        public static final int Success = 1;
        /**
         * 比对失败
         */
        public static final int Fail = 2;
        /**
         * 未比对
         */
        public static final int UnMatch = 3;
    }

    public Bitmap retScenePic = null;
    public Date dateTime = null;
    public float fSim = 0.0f;
    public int matchState = 3;

    public Bitmap getRetScenePic() {
        return retScenePic;
    }

    public void setRetScenePic(Bitmap retScenePic) {
        if (this.retScenePic != null)
            this.retScenePic.recycle();
        this.retScenePic = retScenePic;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public float getfSim() {
        return fSim;
    }

    public void setfSim(float fSim) {
        this.fSim = fSim;
    }

    public int getMatchState() {
        return matchState;
    }

    public void setMatchState(int matchState) {
        this.matchState = matchState;
    }


}
