/*
 * Date 	: 2015.03.10 19:23:46
 * Modify	: 2015.03.13 10:11:12
 * Author	: Newpub
 */
package com.pefis.natives;

import android.graphics.Bitmap;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

public class PfsBioInfo {

    public static final int PFSBIO_OK = 0;
    public static final int PFSBIO_FAIL = 1;


    public static final int PFSBIO_ERR_INVALID_CONTEXT = (-101);
    public static final int PFSBIO_ERR_NOT_CONNECT_DEV = (-102);
    public static final int PFSBIO_ERR_INVALID_PARAM = (-103);
    public static final int PFSBIO_ERR_SYSTEM_MEMORY_ALLOC = (-104);
    public static final int PFSBIO_ERR_TEMPLATE_ARRAY_OVER = (-105);
    public static final int PFSBIO_ERR_CONTEXT_OVER = (-106);
    public static final int PFSBIO_ERR_UNKNOWN = (-107);

    public static final int PFSBIO_ERR_DEV_STOP = (-201);
    public static final int PFSBIO_ERR_DEV_BUSY = (-202);
    public static final int PFSBIO_ERR_DEV_CONTROL = (-203);
    public static final int PFSBIO_ERR_PRO_FUNC = (-301);


    public static final int PFSBIO_ERR_INIT_LIB = (-1007);
    public static final int PFSBIO_ERR_NOT_FIND_LIB_NAME = (-1008);
    public static final int PFSBIO_ERR_GET_FACE_OUTLINE = (-1011);
    public static final int PFSBIO_ERR_GET_ENROLL_TEMPLATE = (-1012);
    public static final int PFSBIO_ERR_ENROLL = (-1013);
    public static final int PFSBIO_ERR_GET_FEATURE = (-1014);
    public static final int PFSBIO_ERR_IDENTIFY = (-1015);
    public static final int PFSBIO_ERR_VERIFY = (-1016);
    public static final int PFSBIO_ERR_DOUBLE_CHECK = (-1017);
    public static final int PFSBIO_ERR_ID_NOT_EXIST = (-1018);
    public static final int PFSBIO_ERR_INVALID_TEMPLATE = (-1019);
    public static final int PFSBIO_ERR_ENROLL_COUNT_OVER = (-1020);


    public static final int PFSBIO_ERR_AST3000P = (-1024);
    public static final int PFSBIO_ERR_NOT_SUPPORTED = (-1025);
    public static final int PFSBIO_ERR_CAMERA_OPEN = (-1026);


    /* Face process parameter kinds */
    public static final int PFSBIO_FACE_PARAM_KIND_MIN = (1001);
    public static final int PFSBIO_FACE_PARAM_KIND_IMG_W = (1001);
    public static final int PFSBIO_FACE_PARAM_KIND_IMG_H = (1002);
    public static final int PFSBIO_FACE_PARAM_KIND_TEMPLATE_SIZE = (1003);
    public static final int PFSBIO_FACE_PARAM_KIND_FEATURE_SIZE = (1004);
    public static final int PFSBIO_FACE_PARAM_KIND_ENROLL_FACE_COUNT = (1005);
    public static final int PFSBIO_FACE_PARAM_KIND_FACE_PROC_NAME = (1006);
    public static final int PFSBIO_FACE_PARAM_KIND_FACE_PROC_VER = (1007);
    public static final int PFSBIO_FACE_PARAM_KIND_DUAL_CAMERA = (1008);
    public static final int PFSBIO_FACE_PARAM_KIND_MAX = (1008);


    public static final int PFSBIO_MAX_CONTEXT_COUNTS = 4;
    public static final int PFSBIO_MAX_DEVICE_COUNTS = 4;

    public static final int PFSBIO_CAND_STRUCT = 9;

    public static final int PFSBIO_OKNOLED = (0x00);
    public static final int PFSBIO_OKLED = (0x01);
    public static final int PFSBIO_NOLED = (0x02);
    public static final int PFSBIO_IR_LED_LOW_ON = (0x00);
    public static final int PFSBIO_IR_LED_HIGH_ON = (0x01);
    public static final int PFSBIO_IR_LED_OFF = (0x02);

    public static final int PFSBIO_LED_ON = (0x00);
    public static final int PFSBIO_LED_OFF = (0x01);

    public static final String INIT_PROC = "Init";
    public static final String EXIT_PROC = "Exit";
    public static final String ENROLL_PROC = "Enroll";
    public static final String VERIFY_PROC = "Verify";
    public static final String VERIFY_FAST_PROC = "VerifyFast";
    public static final String IDENTIFY_PROC = "Identify";
    public static final String DELETE_DATA_PROC = "DeleteData";
    public static final String CAP_FP_PROC = "CaptureFp";

    public static final String ENUMERATE_DEVICE_FUNC = "pisEnumerateDevice";
    public static final String CREATE_CONTEXT_FUNC = "pisCreateContext";
    public static final String DESTROY_CONTEXT_FUNC = "pisDestroyContext";
    public static final String OPEN_DEVICE_FUNC = "pisOpenDevice";
    public static final String CLOSE_DEVICE_FUNC = "pisCloseDevice";
    public static final String GET_INFO_FUNC = "pisGetInfo";
    public static final String COLOR_CAPTURE_FUNC = "pisFcColorImageCapture";
    public static final String GRAY_CAPTURE_FUNC = "pisFcGrayImageCapture";
    public static final String CHECK_FUNC = "pisFcCheck";
    public static final String ENROLL_FUNC = "pisFcEnroll";
    public static final String IDENTIFY_FUNC = "pisFcIdentify";
    public static final String VERIFY_FUNC = "pisFcVerify";
    public static final String CHECKTPL_FUNC = "pisFcCheckTpl";
    public static final String CREATE_TEMPLATE_FUNC = "pisFcCreateTemplate";
    public static final String IDENTIFYTPL_FUNC = "pisFcIdentifyTpl";
    public static final String VERIFYTPL_FUNC = "pisFcVerifyTpl";
    public static final String CHECK_DUP_FUNC = "pisFcCheckDuplicate";

    public static final String GET_COUNT_TPT_ARRAY_FUNC = "pisGetCountTptArray";

    public static final String DELETE_TPT_ARRAY_FUNC = "pisDeleteTptArray";
    public static final String GET_TPT_ARRAY_FUNC = "pisGetAtTptArray";
    public static final String SET_TPT_ARRAY_FUNC = "pisSetAtTptArray";
    public static final String CLEAR_TPT_ARRAY_FUNC = "pisClearTptArray";


    public static final int ERR_ID_EXIST = 10;
    public static final String INIT_DATA_PATH = "/sdcard/GDFace";

    public static final String PROTECT_DEVICE_NAME = "/dev/ttyS1";

    public static final int JIANGSU_HS = -3;
    public static final String TEMPLATE_DB_PATH = INIT_DATA_PATH + "/templateDB/";
    public static final String PHOTO_DB_PATH = INIT_DATA_PATH + "/photo/";

    public static final String AUDIO_DING = INIT_DATA_PATH + "/ding.wav";
    public static final String AUDIO_OK = INIT_DATA_PATH + "/ok_e.wav";

    public int[] face_img_w = new int[1];
    public int[] face_img_h = new int[1];
    public int[] enroll_template_size = new int[1];
    public int[] match_feature_size = new int[1];
    public int[] enroll_face_count = new int[1];
    public int[] face_proc_version = new int[1];

    public byte[] m_pTemplate;
    public byte[] m_pFeature;
    public byte[] m_pUpdatedTemplate;

    public byte[] m_BmpBits;
    public int[] m_BmpIntBits;
    public Bitmap m_bmp;
    public int mContextID = 0;
    public int mEnrollID = -1;
    public int mVerifyID = -1;
    private MediaPlayer mediaPlayer;

    public boolean mShowMessageFlag = false;
    public int mMessageKind = -1;

    private static PfsBioInfo myself;
    public static final int mCameraID = 1;

    public static final int mOrientation = 90;

    public static final int mCamOffSet = 0;

    public static synchronized PfsBioInfo getInstance() {
        if (myself == null) myself = new PfsBioInfo();
        return myself;
    }

    byte[] gnRBuf = new byte[640 * 480];
    byte[] gnGBuf = new byte[640 * 480];
    byte[] gnBBuf = new byte[640 * 480];
    private int m_nWidth;
    private int m_nHeight;

    public void malloc_vars() {
        m_pTemplate = new byte[enroll_template_size[0]];
        m_pUpdatedTemplate = new byte[enroll_template_size[0]];
        m_pFeature = new byte[match_feature_size[0]];
        m_BmpBits = new byte[face_img_w[0] * face_img_h[0] * 4];
        m_BmpIntBits = new int[face_img_w[0] * face_img_h[0]];
        m_bmp = Bitmap.createBitmap(face_img_w[0], face_img_h[0], Bitmap.Config.ARGB_8888);
        m_nWidth = face_img_w[0];
        m_nHeight = face_img_h[0];

    }


    public void setCameraInfo(Camera camera) {
        camera.setDisplayOrientation(mOrientation);

        Parameters param = camera.getParameters();
        Camera.Size vSize;
        int width = face_img_h[0], height = face_img_w[0];

        Log.e("", ": width = " + param.getPreviewSize().width + " height = " + param.getPreviewSize().height);
        int existFlag = 0;
        List<Camera.Size> sizeList = param.getSupportedPictureSizes();
        for (int i = 0; i < sizeList.size(); i++) {
            vSize = sizeList.get(i);
            Log.e("", i + ": width = " + vSize.width + " height = " + vSize.height);
            if (vSize.width == width && vSize.height == height) {
                existFlag = 1;

                break;
            }
        }
        if (existFlag == 1) {

            param.setPreviewSize(width, height);//.setPictureSize(width, height);
            camera.setParameters(param);
            param = camera.getParameters();

        }

    }


    public boolean WriteByteToFile(byte[] a_WriteBuffer, String a_Filename) {

        try {
            File file = new File(a_Filename);
            FileOutputStream FOS = new FileOutputStream(file);
            FOS.write(a_WriteBuffer);
            FOS.flush();
            FOS.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean SaveUserPhotoFile(String a_Filename) {


        try {
            File file = new File(a_Filename);
            FileOutputStream FOS = new FileOutputStream(file);
            FOS.write(m_BmpBits);
            FOS.flush();
            FOS.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public int ReadByteFromFile(String a_Filename, byte[] a_WriteBuffer, int a_FileLen) {

        long size = 0;
        try {
            File file = new File(a_Filename);
            size = file.length();
            if (a_FileLen == 0) {
                a_FileLen = (int) size;
            }
            FileInputStream FIS = new FileInputStream(file);
            FIS.read(a_WriteBuffer, 0, a_FileLen);
            FIS.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return -1;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return -1;
        }
        return a_FileLen;
    }

    public void ShowPfsFaceErrorMsg(TextView v, int errorCode) {
        String err_title = "Error : ";
        String err_msg;

        switch (errorCode) {
            case 0:
                err_msg = "";
            case -1:
                err_msg = err_title + "Unable to load pisface.so!";
                break;
            case PFSBIO_ERR_INVALID_CONTEXT:
                err_msg = err_title + "Invalid context!";
            case PFSBIO_ERR_INVALID_PARAM:
                err_msg = err_title + "Invalid parameters!";
                break;
            case PFSBIO_ERR_SYSTEM_MEMORY_ALLOC:
                err_msg = err_title + "low system memory!";
                break;
            case PFSBIO_ERR_TEMPLATE_ARRAY_OVER:
                err_msg = err_title + "template array over!";
                break;
            case PFSBIO_ERR_CONTEXT_OVER:
                err_msg = err_title + "device context over!";
                break;
            case PFSBIO_ERR_DEV_STOP:
                err_msg = err_title + "device has been stoped!";
                break;
            case PFSBIO_ERR_DEV_BUSY:
                err_msg = err_title + "device is busy!";
                break;
            case PFSBIO_ERR_DEV_CONTROL:
                err_msg = err_title + "device control error!";
                break;
            case PFSBIO_ERR_PRO_FUNC:
                err_msg = err_title + "face process error!";
                break;
            case PFSBIO_ERR_ID_NOT_EXIST:
                err_msg = err_title + "this id is not exist!";
                break;
                /*case PFSBIO_ERR_IDENTIFY:
	            err_msg = err_title + "Fail to identify!";
	            break;

	        case PFSBIO_ERR_VERIFY:
	            err_msg = err_title + "Fail to Verify!";
	            break;

	        case PFSBIO_ERR_ID_NOT_EXIST:
	            err_msg = err_title + "This ID is not exist!";
	            break;
	        case PFSBIO_ERR_CAMERA_OPEN:
	        	err_msg = err_title + "The camera could not open!";
	            break;*/


            default:
                err_msg = err_title + "Unknown error";
                break;
        }

        v.setText(err_msg);
    }

    public void playAudio(String url) throws Exception {
        killMediaPlayer();

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(url);
        mediaPlayer.prepare();
        mediaPlayer.start();
    }

    public void killMediaPlayer() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void ConvertBayerToRGB(byte[] inBuf, byte[] rBuf, byte[] gBuf, byte[] bBuf, int w, int h) {
        int i, j;
        int h1 = h / 2, w1 = w / 2;
        int p1, p2, p3;

        p1 = 1;
        p2 = p1 + w;
        p3 = p2 + w;
        for (i = 1; i < h1; i++) {
            for (j = 1; j < w1; j++) {
                bBuf[p2] = inBuf[p2];
                bBuf[p2 + 1] = inBuf[p2];
                bBuf[p3] = inBuf[p2];
                bBuf[p3 + 1] = inBuf[p2];
                gBuf[p2] = inBuf[p2 - 1];//(byte)((byte)g1/2);
                gBuf[p2 + 1] = inBuf[p2 + 1];//(byte) ((byte)g2/2);
                gBuf[p3] = inBuf[p3];//(byte) ((byte)g3/2);
                gBuf[p3 + 1] = inBuf[p3];//(byte) ((byte)g4/2);
                rBuf[p2] = inBuf[p1 - 1];
                rBuf[p2 + 1] = inBuf[p1 + 1];
                rBuf[p3] = inBuf[p3 - 1];
                rBuf[p3 + 1] = inBuf[p3 + 1];
                p1 += 2;
                p2 += 2;
                p3 += 2;
            }
            p1 += (2 + w);
            p2 = p1 + w;
            p3 = p2 + w;
        }
    }

    public void ConvertBayerToGray(byte[] inBayerBuf, byte[] outGrayBuf, int nWid, int nHei) {
        int i, j, r, g, b;

        ConvertBayerToRGB(inBayerBuf, gnRBuf, gnGBuf, gnBBuf, nWid, nHei);

        for (i = 0; i < nHei; i++) {
            for (j = 0; j < nWid; j++) {
                r = gnRBuf[i * nWid + j];// R
                g = gnGBuf[i * nWid + j];// G
                b = gnBBuf[i * nWid + j];// B
                outGrayBuf[(nHei - i - 1) * nWid + nWid - j - 1] = (byte) ((299 * r + 587 * g + 114 * b) / 1000);
            }
        }
    }

    public void CreateColorBitmapFromBayer(byte[] inBuf) {
        int lnii, lnjj;
        //Ast2600JNI.getInstance().pisFcConvertBayer2RGB(0, inBuf, gnRBuf, gnGBuf, gnBBuf, m_nWidth, m_nHeight);
        ConvertBayerToRGB(inBuf, gnRBuf, gnGBuf, gnBBuf, m_nWidth, m_nHeight);
        //	ConvertBayerToRGB2(inBuf, gnRBuf, gnGBuf, gnBBuf, m_nWidth, m_nHeight);


        int i;
        int l;
        for (int j = 0; j < m_nHeight; j++)
            for (int k = 0; k < m_nWidth; k++) {
                i = j * m_nWidth + (m_nWidth - k - 1);
                l = (m_nHeight - j - 1) * m_nWidth + k;
                m_BmpBits[i * 4] = gnBBuf[l];
                m_BmpBits[i * 4 + 1] = gnGBuf[l];
                m_BmpBits[i * 4 + 2] = gnRBuf[l]; //Invert the source bits
                m_BmpBits[i * 4 + 3] = -1; //0xff, that's the alpha.
            }

        m_bmp.copyPixelsFromBuffer(ByteBuffer.wrap(m_BmpBits));

    }

    public void CreateGrayBitmapFromBayer(byte[] inBuf) {
        int i;
        for (int j = 0; j < m_nHeight; j++)
            for (int k = 0; k < m_nWidth; k++) {
                i = j * m_nWidth + k;


                m_BmpBits[i * 4] =
                        m_BmpBits[i * 4 + 1] =
                                m_BmpBits[i * 4 + 2] = inBuf[i]; //Invert the source bits

                m_BmpBits[i * 4 + 3] = -1; //0xff, that's the alpha.

            }


        m_bmp.copyPixelsFromBuffer(ByteBuffer.wrap(m_BmpBits));
    }


}
