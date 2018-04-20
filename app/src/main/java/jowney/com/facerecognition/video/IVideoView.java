package jowney.com.facerecognition.video;

import android.view.SurfaceView;

/**
 * @author Jowney
 * @time 2017-10-24  11:13
 * @describe
 */
public interface IVideoView {
    /**
     * 获取surfaceView
     *
     * @return
     */
    SurfaceView getCameraPreViewView();

    SurfaceView getDrawFaceRectView();
}
