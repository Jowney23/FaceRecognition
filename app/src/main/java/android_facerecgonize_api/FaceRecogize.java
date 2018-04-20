package android_facerecgonize_api;

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
		mNativeFAlign = initAli(path);
	}
		
	public void ReleaseParam()
	{
		ReleaseP(mNativeFDetect, mNativeFAlign, mNativeFRecog);
	}
	

	public native boolean getSim();

	public native float getSimCos(float[] feat1, float[] feat2,int length);


	public float[] genSiFaceFeature(int[] data, int w, int h, int c, int flag)
	{
		return genProcessF(data, w, h, c, flag, mNativeFDetect, mNativeFAlign, mNativeFRecog);
	}
	
	public native float[] genProcessF(int[] data, int w, int h, int c, int flag, long paramF, long paramA, long paramR);
	//public 
	public native void ReleaseP(long paramF, long paramA, long paramR); 
		
	//////////////////////////////////////
	private long mNativeFDetect;  
	private long mNativeFAlign;  
	private long mNativeFRecog;  
	private  native long  initDet(float thre, String path);
	private  native long  initAli(String path);
	public  native long  initRecog(float thre, String path);
		
}
