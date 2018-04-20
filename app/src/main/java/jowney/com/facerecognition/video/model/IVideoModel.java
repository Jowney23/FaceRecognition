package jowney.com.facerecognition.video.model;

import java.util.Deque;

import jowney.com.facerecognition.cardreader.IdentityCard;
import jowney.com.facerecognition.video.entity.VideoFace;

/**
 * @author Jowney
 * @time 2017-10-24  14:56
 * @describe
 */
public interface IVideoModel<T> {

    String TAG = "VideoModel";
    //用于在工厂模式中选取model类型,model类型取决于摄像头类型
    int VIDEO_MODEL = 8;
    int IR_VIDEO_MODEL = 9;


    int CHACHE_FRAME_COUNT = 2;
    int CHACHE_FACE_COUNT = 2;
    int CHACHE_MATCHFACE_COUNT = 2;
    int CHACKE_IDCARD_COUNT = 1;
    int CHACKE_IDCARDFEATURE_COUNT = 1;

    T getFrameFromQueue();

    void putFrameToQueue(byte[]... bytes);

  //  void recycleFrame(VideoFrame videoFrame);

    //视频帧中预处理人脸(没有特征值的人脸)
    void putFaceInQueue(VideoFace videoFace);

    VideoFace getFaceFromQueue();

    //已添加特征值的预处理人脸(待比对)
    void putFeatureFaceInQueue(VideoFace videoFace);

    Deque<VideoFace> getFeatureFaceFromQueue();

    //将含有
    //身份证信息(待生成身份证照片模板)
    void putIDCardInforInQueue(IdentityCard identityCard);

    IdentityCard getIDCardInforFromQueue();

    //含有身份证照片模板的实例队列(待比对)
    void putFeatureIDCardInforInQueue(IdentityCard identityCard);

    IdentityCard getFeatureIDCardInfoeFromQueue();
}
