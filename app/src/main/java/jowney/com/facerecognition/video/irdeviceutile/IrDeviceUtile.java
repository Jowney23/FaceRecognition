package jowney.com.facerecognition.video.irdeviceutile;

import com.pefis.natives.Ast2600JNI;
import com.pefis.natives.PfsBioInfo;

import java.io.File;

import jowney.com.common.utils.L;

/**
 * @author Jowney
 * @time 2017-12-26  15:43
 * @describe
 */
public class IrDeviceUtile {
    private static int[] m_nContextID = new int[1];
    private static File m_File = new File(PfsBioInfo.TEMPLATE_DB_PATH);

    /**
     * 1/初始化设备打开摄像头
     * 2/初始化PfsBioInfo中数据
     * 3/加载本地模型文件到内存
     * @return 状态码 '1'成功
     */
    public static int initDevice() {
        String mStrDevName;
        String mStrDevId;
        int vnRet;
        int i = 0;
        byte[] vByteDeviceDescription = new byte[256];
        byte[] vDeviceIdentifier = new byte[256];

        vnRet = Ast2600JNI.getInstance().pisEnumerateDevice(i, vDeviceIdentifier, vByteDeviceDescription);
        if (vnRet == PfsBioInfo.PFSBIO_OK) {
            mStrDevId = Ast2600JNI.ByteArrayUtf8ToString(vDeviceIdentifier);
            mStrDevName = Ast2600JNI.ByteArrayUtf8ToString(vByteDeviceDescription);

        } else {
            L.i("hwwlog", "检测当前存在的设备错误 : vnRet = " + vnRet);
            mStrDevId = "";
            mStrDevName = "";
            return -1;
        }

        PfsBioInfo.getInstance().mEnrollID = -1;
        vnRet = Ast2600JNI.getInstance().pisCreateContext(m_nContextID);

        if (vnRet != PfsBioInfo.PFSBIO_OK) {
            L.i("hwwlog", "USB口设备检测错误 : vnRet = " + vnRet);
            return 0;
        }

        PfsBioInfo.getInstance().mContextID = m_nContextID[0];

        vnRet = Ast2600JNI.getInstance().pisOpenDevice(m_nContextID[0],
                mStrDevId.getBytes(), PfsBioInfo.INIT_DATA_PATH.getBytes());

        if (vnRet != PfsBioInfo.PFSBIO_OK) {
            Ast2600JNI.getInstance().pisDestroyContext(m_nContextID[0]);
            L.i("hwwlog", "打开摄像头等设备错误 : vnRet = " + vnRet);
            return 0;
        }
        initDeviceDate();
        return 1;
    }
    private static void initDeviceDate() {
        Ast2600JNI.getInstance().pisFcClearTptArray(PfsBioInfo.getInstance().mContextID);
        try {

            // Get Face process parameters
            Ast2600JNI.getInstance().pisGetInfo(m_nContextID[0],
                    PfsBioInfo.PFSBIO_FACE_PARAM_KIND_IMG_W,
                    PfsBioInfo.getInstance().face_img_w);
            Ast2600JNI.getInstance().pisGetInfo(m_nContextID[0],
                    PfsBioInfo.PFSBIO_FACE_PARAM_KIND_IMG_H,
                    PfsBioInfo.getInstance().face_img_h);
            Ast2600JNI.getInstance().pisGetInfo(m_nContextID[0],
                    PfsBioInfo.PFSBIO_FACE_PARAM_KIND_TEMPLATE_SIZE,
                    PfsBioInfo.getInstance().enroll_template_size);
            Ast2600JNI.getInstance().pisGetInfo(m_nContextID[0],
                    PfsBioInfo.PFSBIO_FACE_PARAM_KIND_FEATURE_SIZE,
                    PfsBioInfo.getInstance().match_feature_size);
            Ast2600JNI.getInstance().pisGetInfo(m_nContextID[0],
                    PfsBioInfo.PFSBIO_FACE_PARAM_KIND_ENROLL_FACE_COUNT,
                    PfsBioInfo.getInstance().enroll_face_count);
            // Ast2600JNI.getInstance().pisGetInfo(m_nContextID[0],PfsBioInfo.PFSBIO_FACE_PARAM_KIND_FACE_PROC_VER,
            // PfsBioInfo.getInstance().face_proc_version);

            loadtemplateFiles(PfsBioInfo.PHOTO_DB_PATH);
        } catch (Exception e) {
            // e.printStackTrace();
            Ast2600JNI.getInstance().pisCloseDevice(m_nContextID[0]);
            Ast2600JNI.getInstance().pisDestroyContext(m_nContextID[0]);
        }
    }

    private static void loadtemplateFiles(String photoTemplatePath){

        if (!m_File.isDirectory()) {
            if (!m_File.mkdir()) {
                return;
            }
        }
        File mPhotoDir = new File(photoTemplatePath);
        if (!mPhotoDir.isDirectory()) {
            if (!mPhotoDir.mkdir()) {
                return;
            }

        }
        String[] vFileNameList = m_File.list();
        int vFileCounts = vFileNameList.length;
        int vnTemplateID;

        PfsBioInfo.getInstance().malloc_vars();

        int vReadRet;
        int vnRet = 0;
        while (vFileCounts != 0) {
            vReadRet = PfsBioInfo.getInstance().ReadByteFromFile(
                    PfsBioInfo.TEMPLATE_DB_PATH
                            + vFileNameList[vFileCounts - 1],
                    PfsBioInfo.getInstance().m_pTemplate,
                    PfsBioInfo.getInstance().enroll_template_size[0]);
            if (vReadRet == -1) {
                return;
            }
            vnTemplateID = 0;
            try {
                vnTemplateID = Integer.valueOf(vFileNameList[vFileCounts - 1]);
            } catch (Exception e) {
                e.printStackTrace();
            }

            vnRet = Ast2600JNI.getInstance().pisFcSetAtTptArray(
                    m_nContextID[0], (int) vnTemplateID,
                    PfsBioInfo.getInstance().m_pTemplate);
            if (vnRet != PfsBioInfo.PFSBIO_OK) {

                vFileCounts -= 1;
                continue;
            }
            vFileCounts -= 1;
        }

    }
}
