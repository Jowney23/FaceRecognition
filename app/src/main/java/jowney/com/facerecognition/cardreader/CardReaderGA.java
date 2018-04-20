package jowney.com.facerecognition.cardreader;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;

import com.sdt.Sdtapi;
import com.zkteco.android.IDReader.WLTService;

import jowney.com.common.utils.L;
import jowney.com.facerecognition.cardreader.utils.IDPhotoHelper;

/**
 * @author Jowney
 * @time 2017-10-31  15:19
 * @describe
 */
public class CardReaderGA implements IIDCardReader {

    /*民族列表*/
    String[] nation = {"汉族", "蒙古族", "回族", "藏族", "维吾尔族", "苗族", "彝族", "壮族", "布依族", "朝鲜族",
            "满族", "侗族", "瑶族", "白族", "土家族", "哈尼族", "哈萨克族", "傣族", "黎族", "傈僳族",
            "佤族", "畲族", "高山族", "拉祜族", "水族", "东乡族", "纳西族", "景颇族", "克尔克孜族", "土族",
            "达斡尔族", "仫佬族", "羌族", "布朗族", "撒拉族", "毛南族", "仡佬族", "锡伯族", "阿昌族", "普米族",
            "塔吉克族", "怒族", "乌兹别克族", "俄罗斯族", "鄂温克族", "德昂族", "保安族", "裕固族", "京族", "塔塔尔族",
            "独龙族", "鄂伦春族", "赫哲族", "门巴族", "珞巴族", "基诺族"
    };

    private Activity mActivity = null;
    private Sdtapi sdta;
    IdentityCard msg = null;

    public CardReaderGA(Activity mActivity) {
        this.mActivity = mActivity;
    }

    private static final int VID = 1024;    //IDR VID
    private static final int PID = 50010;     //IDR PID
    boolean mIsFind = false;

    //字节解码函数
    void DecodeByte(byte[] msg, char[] msg_str) throws Exception {
        byte[] newmsg = new byte[msg.length + 2];
        newmsg[0] = (byte) 0xff;
        newmsg[1] = (byte) 0xfe;

        for (int i = 0; i < msg.length; i++)
            newmsg[i + 2] = msg[i];

        String s = new String(newmsg, "UTF-16");
        for (int i = 0; i < s.toCharArray().length; i++)
            msg_str[i] = s.toCharArray()[i];


    }

    //读取身份证中的文字信息（可阅读格式的）
    public int ReadBaseMsgToStr(IdentityCard msg) {
        int ret;
        int[] puiCHMsgLen = new int[1];
        int[] puiPHMsgLen = new int[1];
        byte[] pucCHMsg = new byte[256];
        byte[] pucPHMsg = new byte[1024];
        byte[] pucFPMsg = new byte[2048];
        int[] pucFPMsgLen = new int[1];

        puiPHMsgLen[0] = 0;

        //sdtapi中标准接口，输出字节格式的信息。
        ret = sdta.SDT_ReadBaseMsg(pucCHMsg, puiCHMsgLen, pucPHMsg, puiPHMsgLen);
        //ret =sdta.SDT_ReadBaseFPMsg(pucCHMsg, puiCHMsgLen, pucPHMsg, puiPHMsgLen,pucFPMsg,pucFPMsgLen);
        if (ret == 0x90) {
            try {
                char[] pucCHMsgStr = new char[128];
                DecodeByte(pucCHMsg, pucCHMsgStr);//将读取的身份证中的信息字节，解码成可阅读的文字
                PareseItem(pucCHMsgStr, msg);     //将信息解析到msg中
                Bitmap bmpPhoto = null;
                byte[] buf = new byte[WLTService.imgLength];
                int retCode = WLTService.wlt2Bmp(pucPHMsg, buf);
                if (1 == retCode) {
                    bmpPhoto = IDPhotoHelper.Bgr2ARGB_Mirror(buf);
                }
                /*
				 * ByteArrayOutputStream baos = new ByteArrayOutputStream();
				 * String skRoot =
				 * Environment.getExternalStorageDirectory().toString(); skRoot
				 * = skRoot+"/test.raw"; try { FileOutputStream out = new
				 * FileOutputStream(skRoot); out.write(pucPHMsg); Log.d("zxd",
				 * "Write video picture success."); out.close(); } catch
				 * (FileNotFoundException e) { e.printStackTrace(); Log.e("zxd",
				 * "Write video picture fail:"+e.getMessage()); } catch
				 * (IOException e) { e.printStackTrace(); Log.e("zxd",
				 * "Write video picture fail:"+e.getMessage()); }
				 */
                //= idPhotoHelper.decodePhoto(pucPHMsg);
                if (bmpPhoto == null) {
                    Log.e("zxd", "读身份照片证解码失败. 照片长度=" + puiPHMsgLen[0]);
                } else {
                    Log.e("zxd", "读身份照片证解码成功.");
                }
                msg.setBmpPhoto(bmpPhoto);

            } catch (Exception e) {
                Log.e("zxd", "读身份证失败！" + e.getMessage());
                e.printStackTrace();
            }
        }

        return ret;
    }


    //分段信息提取
    void PareseItem(char[] pucCHMsgStr, IdentityCard msg) {
        String name = String.copyValueOf(pucCHMsgStr, 0, 15);
        String sex = "";
        String sex_code = String.copyValueOf(pucCHMsgStr, 15, 1);
        String birdthDay;
        String useful_s_date;
        String useful_e_date;

        if (sex_code.equals("1")) sex = "男";
        else if (sex_code.equals("2")) sex = "女";
        else if (sex_code.equals("0")) sex = "未知";
        else if (sex_code.equals("9")) sex = "未说明";

        String nation_code = String.copyValueOf(pucCHMsgStr, 16, 2);
        msg.setName(name);
        msg.setNation(nation[Integer.valueOf(nation_code) - 1]);
        msg.setNationCode(nation_code);
        birdthDay = String.copyValueOf(pucCHMsgStr, 18, 4) + "-" + String.copyValueOf(pucCHMsgStr, 22, 2) + "-" + String.copyValueOf(pucCHMsgStr, 24, 2);
        String address = String.copyValueOf(pucCHMsgStr, 26, 35);
        String id_num = String.copyValueOf(pucCHMsgStr, 61, 18);

        msg.setBirdthDay(birdthDay.trim());
        msg.setAddress(address.trim());
        msg.setGender(sex);
        msg.setGenderCode(sex_code);
        msg.setIdentity(id_num.trim());
        msg.setSignOffice(String.copyValueOf(pucCHMsgStr, 79, 15).trim());
        useful_s_date = String.copyValueOf(pucCHMsgStr, 94, 4) + "-" + String.copyValueOf(pucCHMsgStr, 98, 2) + "-" + String.copyValueOf(pucCHMsgStr, 100, 2);
        useful_e_date = String.copyValueOf(pucCHMsgStr, 102, 4) + "-" + String.copyValueOf(pucCHMsgStr, 106, 2) + "-" + String.copyValueOf(pucCHMsgStr, 108, 2);
        msg.setStartDate(useful_s_date);
        msg.setEndDate(useful_e_date);
        L.i("zxd", "name=" + name + "ID=" + id_num);
    }



    @Override
    public int openCardReader() {
        int ret = 0;
        String show;
        char[] puSAMID = new char[36];
        do {
            if (sdta == null) {
                try {
                    sdta = new Sdtapi(mActivity);
                } catch (Exception e1) {//捕获异常，
                    if (e1.getCause() == null) //USB设备异常或无连接，应用程序即将关闭。
                    {
                        L.e("zxd", "USB设备异常或无连接，应用程序即将关闭。");
                    } else //USB设备未授权，需要确认授权
                    {
                        L.e("zxd", "USB设备授权失败");
                    }
                }
            }
            try{
                ret = sdta.SDT_GetSAMStatus();
            }catch (Exception e){

            }

            if (ret != 0x90) {
                show = "模块状态错误:" + String.format("0x%02x", ret);
                L.e("zxd", show);
                break;
            }

            show = "模块状态良好";
            L.i("zxd", show);
            //ret= sdta.SDT_GetSAMIDToStr( puSAMID);
            //if(ret!=0x90)
            //{
            //	show ="错误:"+ String.format("0x%02x", ret);
            //	Log.e("zxd",show);
            //	break;
            //}
            //Log.i("zxd","SAMID:"+puSAMID);

            ret = 0;

        } while (false);

        return ret;
    }

    @Override
    public int readCard() {
        int ret = 0;
        //byte[] pucFPMsg;
        //int[]  puiFPMsgLen = new int[1];
        int[] puiCHMsgLen = new int[1];
        int[] puiPHMsgLen = new int[1];

        byte[] pucCHMsg = new byte[256];
        byte[] pucPHMsg = new byte[1024];

        do {
				/*
				ret = openCardReader();
				if (ret!=0) {
					break;
				}*/
            if (!mIsFind) {
                ret = sdta.SDT_StartFindIDCard();//寻找身份证
                if (ret != 0x90) {
                    //Log.e("zxd", "寻找失败！"+ret);
                    ret = 0;
                    mIsFind = false;
                } else {
                    mIsFind = true;
                    //Log.i("zxd", "寻找成功！");
                }
            }
            //
            ret = sdta.SDT_SelectIDCard();//选取身份证
            if (ret != 0x90) {
                //Log.e("zxd", "选取身份证失败！"+ ret);
                ret = -1;
                mIsFind = false;
                break;
            }
            //Log.i("zxd", "选取身份证成功");
            msg = new IdentityCard();//身份证信息对象，存储身份证上的文字信息
            ret = ReadBaseMsgToStr(msg);
            if (ret != 0x90) {
                //Log.e("zxd", "读身份证失败！");
                ret = -1;
                break;
            }
            L.i("zxd", "读身份证成功！");
            ret = 1;
            //ret = sdta.SDT_ReadBaseFPMsg(pucCHMsg,puiCHMsgLen,pucPHMsg,puiPHMsgLen,pucFPMsg,puiFPMsgLen);

        } while (false);

        return ret;
    }

    @Override
    public Bitmap getPhoto() {
        return msg.getBmpPhoto();
    }

    @Override
    public IdentityCard getInfo() {
        return msg;
    }

    @Override
    public int closeCardReader() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int destoryCardReader() {
        // TODO Auto-generated method stub
        //IDCardReaderFactory.destroy(idCardReader);
        return 0;
    }

    @Override
    public void setContext(Activity context) {
        mActivity = context;
    }

}
