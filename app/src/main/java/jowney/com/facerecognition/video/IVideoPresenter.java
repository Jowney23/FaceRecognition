package jowney.com.facerecognition.video;

import android.view.SurfaceHolder;

/**
 * @author Jowney
 * @time 2017-10-25  17:23
 * @describe
 */
public interface IVideoPresenter {
    SurfaceHolder getCameraPreviewHolder();
    SurfaceHolder getDrawFaceRectHolder();
}
