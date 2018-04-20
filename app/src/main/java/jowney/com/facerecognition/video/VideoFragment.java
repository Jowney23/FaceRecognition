package jowney.com.facerecognition.video;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import jowney.com.facerecognition.R;

/**
 * @author Jowney
 * @time 2017-10-20  15:58
 * @describe
 */
public class VideoFragment extends Fragment implements IVideoView{
    @BindView(R.id.surface_vedio)
    SurfaceView mCameraPreviewView;
    @BindView(R.id.surface_face)
    SurfaceView mdrawFaceRectView;
    private VideoPresenter videoPresenter;
    private Unbinder unBinder;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frament_vedio, container, false);
        unBinder = ButterKnife.bind(this, view);
        videoPresenter = new VideoPresenter(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unBinder.unbind();
    }
    //获取控件
    @Override
    public SurfaceView getCameraPreViewView() {
        return this.mCameraPreviewView;
    }

    @Override
    public SurfaceView getDrawFaceRectView() {
        return this.mdrawFaceRectView;
    }
}
