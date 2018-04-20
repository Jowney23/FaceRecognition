package jowney.com.facerecognition.video.model;

/**
 * @author Jowney
 * @time 2017-12-28  10:28
 * @describe
 */
public class VideoModelFactory {
    public static IVideoModel createModle(int type){
        switch (type){
            case IVideoModel.VIDEO_MODEL:
                return new VideoModel() ;
            case IVideoModel.IR_VIDEO_MODEL:
                return new IRVideoModel();
            default:
                return null;
        }
    }
}
