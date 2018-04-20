package jowney.com.common.utils;

/**
 * @author Jowney
 * @time 2017-11-03  10:33
 * @describe 只能降低帧率 将帧率限制为MAX_FPS
 */
public class FrameControlUtils {
    private  static int MAX_FPS ;    //帧率
    private  static int FRAME_PERIOD; // the frame period
    private static long lastTime = 0;
    private static long timeDiff = 0;

    public static boolean limitFrame(int MAX_FPS){
        FRAME_PERIOD = (1000 / MAX_FPS);
        timeDiff = System.currentTimeMillis() - lastTime;
        if (timeDiff < FRAME_PERIOD) {
            return false;
        }
        lastTime = System.currentTimeMillis();
        return true;
    }

}
