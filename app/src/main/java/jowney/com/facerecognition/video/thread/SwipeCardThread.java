package jowney.com.facerecognition.video.thread;

import java.util.Date;

import jowney.com.common.utils.L;
import jowney.com.common.utils.Sleep;
import jowney.com.facerecognition.cardreader.IIDCardReader;
import jowney.com.facerecognition.cardreader.IdentityCard;
import jowney.com.facerecognition.video.model.IVideoModel;

/**
 * @author Jowney
 * @time 2017-10-30  11:32
 * @describe
 */
public class     SwipeCardThread extends Thread {
    private static final String TAG = "SwipeCardThread";
    private IVideoModel iVideoModel;
    private IIDCardReader iidCardReader;
    private IdentityCard identityCardInfor = null;

    private int readCardResult = 0;

    public SwipeCardThread(IVideoModel iVideoModel, IIDCardReader iidCardReader) {
        this.iVideoModel = iVideoModel;
        this.iidCardReader = iidCardReader;
    }

    public SwipeCardThread() {

    }

    @Override
    public void run() {
        super.run();
        while (true) {

            //返回值为"1",代表读卡成功
         //   L.i(GenCardFaceFeatureThread.TAG, "............读卡线程");
            Sleep.s(200);
            readCardResult = iidCardReader.readCard();
            if (readCardResult != 1) {
                //L.i(GenCardFaceFeatureThread.TAG,"............读卡失败");
                Sleep.s(10);
                continue;
            }
            L.i(GenCardFaceFeatureThread.TAG, ".........................读卡成功");
            identityCardInfor = iidCardReader.getInfo();
            identityCardInfor.setReadCardTime(new Date(System.currentTimeMillis()));
            iVideoModel.putIDCardInforInQueue(identityCardInfor);
        }
    }


}
