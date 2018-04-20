package jowney.com.facerecognition.video.facerecognize_jni;


public class FaceRecogize {
    static{
        System.loadLibrary("testFaceRecognize");
    }
    public FaceRecogize(){

    }

    public void InitialParam(float thre, String path)
    {
        mNativeFDetect = initDet(thre, path);
        mNativeFRecog = initRecog(thre, path);
        mNativeFRecon_neon=initRecog_neon(path);
        mNativeFAlign = initAli(path);
    }

    public void ReleaseParam()
    {
        ReleaseP(mNativeFDetect, mNativeFAlign, mNativeFRecog);
    }
    public void ReleaseParam_neon()
    {
        ReleaseP(mNativeFDetect, mNativeFAlign, mNativeFRecon_neon);
    }

    /**
     * 获取前面两次图片特征的相似度
     * @return 两张图片的相似度
     */
    public native boolean getSim();

    public native float getSimCos(float[] feat1, float[] feat2,int length);

    /**
     * 用于生成图片特征，目前暂时只适用于身份证图片和视频人脸比对，需要在getSim()生成两个特征
     * @param data 图片像素
     * @param w 图片宽
     * @param h 图片高
     * @param faceInfo 图片的人脸位置信息
     * @param flag 图片来源, 1-身份证图片,2-视频图片
     * @return
     */
    public float[] genSiFaceFeature(int[] data, int w, int h, int c, int flag)
    {
        return genProcessF(data, w, h, c, flag, mNativeFDetect, mNativeFAlign, mNativeFRecog);
    }
    public float[] genSiFaceFeature_neon(int[] data, int w, int h, int c, int flag)
    {
        return genProcessF_neon(data, w, h, c, flag, mNativeFDetect, mNativeFAlign, mNativeFRecon_neon, mNativeFRecog);
    }

    public native float[] genProcessF(int[] data, int w, int h, int c, int flag, long paramF, long paramA, long paramR);
    public native float[] genProcessF_neon(int[] data, int w, int h, int c, int flag, long paramF, long paramA, long paramR, long paramRst);
    //public
    public native void ReleaseP(long paramF, long paramA, long paramR);
    public native void ReleaseP_neon(long paramF, long paramA, long paramR);
    //////////////////////////////////////
    private long mNativeFDetect;
    private long mNativeFAlign;
    private long mNativeFRecog;
    private long mNativeFRecon_neon;
    private  native long  initDet(float thre, String path);
    private  native long  initAli(String path);
    public  native long  initRecog(float thre, String path);
    public  native long  initRecog_neon(String path);

}

/*{

    static {
        System.loadLibrary("test");
    }

    public FaceRecogize() {

    }


    *//**
     * 人脸识别初始化，需要在genFaceFeature()前执行
     *
     * @return
     *//*
    public native boolean init(float thre, String path);

    public native void release();

    *//**
     * 获取前面两次图片特征的相似度
     *
     * @return 两张图片的相似度
     *//*
    public native boolean getSim();

    public native float getSimF();

    *//*
    * 1 证件大头照
    * 3 视频人脸
    *//*
    public native float[] getFeat(int iflag);

    public native float getSimCos(float[] feat1, float[] feat2, int length);

    *//**
     * 用于生成图片特征，目前暂时只适用于身份证图片和视频人脸比对，需要在getSim()生成两个特征
     *
     * @param data     图片像素
     * @param w        图片宽
     * @param h        图片高
     * @param faceInfo 图片的人脸位置信息
     * @param flag     图片来源, 1-身份证图片,2-视频图片
     * @return
     *//*
    public native boolean genFaceFeature(int[] data, int w, int h, FaceInfo faceInfo, int flag);

}*/
