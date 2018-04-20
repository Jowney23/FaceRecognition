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
 * @time 2017-12-29  10:25
 * @describe
 */
public class IRFragment extends Fragment {
    private Unbinder unBinder;
    @BindView(R.id.irView)
    SurfaceView myIRView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_irvideo, container, false);
        unBinder = ButterKnife.bind(this, view);
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
}
