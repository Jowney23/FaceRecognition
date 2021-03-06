package jowney.com.facerecognition.video.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import jowney.com.common.utils.L;
import jowney.com.facerecognition.cardreader.IdentityCard;
import jowney.com.facerecognition.video.entity.VideoFace;
import jowney.com.facerecognition.video.entity.VideoFrame;

/**
 * @author Jowney
 * @time 2017-10-24  14:54
 * @describe 调用设备自身Camera时
 */
public class VideoModel implements IVideoModel<VideoFrame> {

    private Deque<VideoFrame> listFrame;
    //待对比的视频帧中人脸实体类队列
    private Deque<VideoFace> listFace;
    //要对比的视频帧中人脸实体类队列
    private Deque<VideoFace> listMatchFace;
    //将要处理的视频帧中人脸实例 全部放入该List中
    private List<VideoFace> listMatchingFace;
    //没有身份证图片特征值的身份证信息实例队列
    private Deque<IdentityCard> listCard;
    //要对比的含有身份证图片特征身份证信息实例队列
    private Deque<IdentityCard> listMatchCard;

    public VideoModel() {
        listFace = new LinkedList<>();
        listMatchFace = new LinkedList<>();
        listFrame = new LinkedList<>();
        listCard = new LinkedList<>();
        listMatchCard = new LinkedList<>();
        listMatchingFace = new ArrayList<>();
    }

    /**
     * 只需要一个参数 彩色图的
     *
     * @param data
     */
    @Override
    public void putFrameToQueue(byte[]... data) {

        //缓存预览数据
        VideoFrame frame = null;
        synchronized (listFrame) {
            if (listFrame.size() >= CHACHE_FRAME_COUNT) {
                frame = listFrame.removeFirst();
                L.i("debug", "Detect listFrame remove size=" + listFrame.size());
            } else {
                frame = new VideoFrame();
            }

            frame.setDateTime(new Date(System.currentTimeMillis()));
            if(frame.setNv21(data[0]) == 0){
                listFrame.addLast(frame);
            }
            L.i(IVideoModel.TAG, "Detect listFace listFrame size=" + listFrame.size());
        }
    }

    @Override
    public VideoFrame getFrameFromQueue() {

            VideoFrame frame = null;
            synchronized (listFrame) {
                if (listFrame.size() <= 0) {
                    return null;
                }
                frame = listFrame.removeFirst();
            }
            return frame;
    }


    /**
     * 没有特征值的人脸实体类
     *
     * @param videoFace
     */
    @Override
    public void putFaceInQueue(VideoFace videoFace) {
        synchronized (listFace) {
            if (listFace.size() >= CHACHE_FACE_COUNT) {
                VideoFace face = listFace.removeFirst();
                face.recycleFace();
                face = null;
                L.i("debug", "Detect listFace remove size=" + listFace.size());
            }
            listFace.addLast(videoFace);
            L.i(IVideoModel.TAG, "Detect listFace inqueue size=" + listFace.size());
        }
    }

    @Override
    public VideoFace getFaceFromQueue() {
        synchronized (listFace) {
            if (listFace.isEmpty()) {
                return null;
            } else {
                return listFace.removeFirst();
            }
        }

    }

    @Override
    public void putFeatureFaceInQueue(VideoFace videoFace) {
        synchronized (listMatchFace) {
            if (listMatchFace.size() >= CHACHE_MATCHFACE_COUNT) {
                VideoFace face = listMatchFace.removeFirst();
                face.recycleFace();
                face = null;
                L.i("FeatureFace", "Detect FeatureFace remove size=" + listFace.size());
            }
            listMatchFace.addLast(videoFace);
            L.i("FeatureFace", "Detect FeatureFace in queue size=" + listFace.size());

        }
    }

    @Override
    public Deque<VideoFace> getFeatureFaceFromQueue() {
        synchronized (listMatchFace) {
            if (listMatchFace.isEmpty()) {
                return null;
            } else {
                return listMatchFace;
            }
        }

    }

    @Override
    public void putIDCardInforInQueue(IdentityCard identityCardInfor) {

        synchronized (listCard) {
            if (listCard.size() >= CHACKE_IDCARD_COUNT) {
                IdentityCard idCard = listCard.removeFirst();
                idCard.recyclePhoto();

            }
            listCard.addLast(identityCardInfor);
        }
    }

    @Override
    public IdentityCard getIDCardInforFromQueue() {
        synchronized (listCard) {
            if (listCard.isEmpty()) {
                return null;
            } else {
                return listCard.getFirst();
            }
        }
    }

    @Override
    public void putFeatureIDCardInforInQueue(IdentityCard identityCard) {
        synchronized (listMatchCard) {
            if (listMatchCard.size() >= CHACKE_IDCARDFEATURE_COUNT) {
                IdentityCard idCard = listMatchCard.removeFirst();
                idCard.recyclePhoto();

            }
            listMatchCard.add(identityCard);
        }
    }

    @Override
    public IdentityCard getFeatureIDCardInfoeFromQueue() {
        synchronized (listMatchCard) {
            if (listMatchCard.isEmpty()) {
                return null;
            } else {
                return listMatchCard.removeFirst();
            }
        }
    }
}
