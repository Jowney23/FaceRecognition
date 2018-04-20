/*************************************************************************
 * Package	: com.pefis.natives
 * Class    : Ast2600JNI
 * Function : Perform biometric identification functions(JNI)
 * Company  : PEFIS Electronic Technology (Beijing)Co.,Ltd.
 * Date		: 2016.08.04 15:32:09
 * Author	: Newpub
 ************************************************************************/
package com.pefis.natives;

public class Ast2600JNI {
	private static Ast2600JNI myself = null;
	static
	{
		try
		{
			System.loadLibrary("ast2600fc");
		}
		catch(UnsatisfiedLinkError e)
		{
			e.printStackTrace();
		}
	}


	/**
     * 打开摄像头等设备
     * @param contextID 设备ID
     * @param DevID     设备的序列号
     * @param path      默认文件路径 INIT_DATA_PATH 此参数可以自己配置, 该目录有预装的件
     * @return int类型          PFSBIO_OK(0)成功，其他为失败
     */
	  public native int pisOpenDevice(int contextID, byte[] DevID,byte[] path);
	  
	  
	  /**
	   * 关闭摄像头等设备
	   * @param contextID 设备ID
	   * @return int类型          PFSBIO_OK(0)成功，其他为失败
	  */
	public native int pisCloseDevice(int contextID);

	/**
     * 检测当前存在的设备，并返回设备序列号 设备名称
     * @param enumerateDevID    设备编号  默认只有一个设备  传入0
     * @param devIdentifier     设备序列号             [out]
     * @param vstrDeviceDescription 设备名称    [out]
     * @return int类型          PFSBIO_OK(0)成功，其他为失败
     */
	public native int pisEnumerateDevice(
	    		int enumerateDevID,  
				byte[] devIdentifier,
				byte[] vstrDeviceDescription);


    /**
     * USB口设备的检测，在打开设备函数之前调用, 创建设备ID
     * @param contextID 设备ID    [OUT]
     * @return int类型          PFSBIO_OK(0)成功，其他为失败
     */
	public native int pisCreateContext(int[] contextID);

	
	/**
     * 关闭USB口设备
     * @param contextID 设备ID
     * @return int类型          PFSBIO_OK(0)成功，其他为失败
     */
	public native int pisDestroyContext(int contextID);

	
	/**
     *获取摄像头对应的信息 比如宽 高，建模的特征长度，最大建模数量，摄像头的目数
     * @param contextID 设备ID
     * @param index     设备的某个信息索引  PFSBIO_FACE_PARAM_KIND_IMG_W-PFSBIO_FACE_PARAM_KIND_DUAL_CAMERA
     * @param value     设备的信息   [out]
     * @return int类型          PFSBIO_OK(0)成功，其他为失败
     */
	public native int pisGetInfo(
	    		int contextID,
	    		int index, 
				int[] value);

	
	/**
	   *LED灯亮的控制
	   * @param contextID 设备ID
	   * @param ledKind 灯的类型  PFSBIO_OKNOLED PFSBIO_OKLED PFSBIO_NOLED等
	   * @param controlValue 开或者关 PFSBIO_LED_ON PFSBIO_LED_OFF
	   * @return int类型          PFSBIO_OK(0)成功，其他为失败
	 */
	public native int pisLedControl(int contextID, int ledKind, int controlValue);

	
	/**
	   *可见光数据帧的抓取
	   * @param contextID 设备ID
	   * @param imgBuffer 抓取到的数据 [out]
	   * @return int类型          PFSBIO_OK(0)成功，其他为失败
	 */
	public native int pisFcColorImageCapture(int contextID, byte[] imgBuffer);

	
	/**
	 *红外数据帧的抓取
	 * @param contextID 设备ID
	 * @param imgBuffer 抓取到的数据 [out]
	 * @return int类型          PFSBIO_OK(0)成功，其他为失败
	 */	
	public native int pisFcGrayImageCapture(int contextID, byte[] imgBuffer);

	
	/**
	 * 人脸抓拍
	 * 
	 * @param contextID 设备ID
	 * @param imgBuffer 抓取到的数据
	 * @param width 宽 默认值480
	 * @param height 高 默认值640
	 * @param faceRect 抓到的人脸的区域 [out]
	 * @return int类型 PFSBIO_OK(0)成功，其他为失败
	 */
	public native int pisFcCheck(int contextID, byte[] imgBuffer,int width,int height,short[] faceRect);

	
	/**
	 *人脸建模
	 * @param contextID 设备ID
	 * @param enrollCount  建模图像标号，表示第几张图像在建模 0-7  从0开始， 就是说当我们对着摄像头建模时，共取8帧图来建模
	 * @param grayImageBuffer 灰度图像数据
	 * @param width 宽         默认值480
	 * @param height 高      默认值640  
	 * @param templateData 生成的特征码  [out]
	 * @return int类型          PFSBIO_OK(0)成功，其他为失败
	 */
	public native int pisFcEnroll(
	    		int contextID, 
	    		int enrollCount, 
	    		byte[] grayImageBuffer, 
	    		int width, 
	    		int height, 
	    		byte[] templateData
	    		);

	
	
	/**
	 *人脸比对
	 * @param contextID 设备ID
	 * @param grayImageBuffer 灰度图像数据
	 * @param width 宽         默认值480
	 * @param height 高      默认值640
	 * @param identifiedTID  比对成功返回对应的ID [out] 
	 * @param candInfo 比对成功的ID与百分数                             [out]
	 * @param updatedTemplateData 比对成功后重新更新的特征码  [out]
	 * @param updatedFlag 需要更新的标志  [out]
	 * @return int类型          PFSBIO_OK(0)成功，其他为失败
	 */
	public native int pisFcIdentify(
	    		int contextID, 
	    		byte[] grayImageBuffer, 
	    		int width, 
	    		int height, 
	    		int[] identifiedTID,
	    		long[] candInfo,
				byte[] updatedTemplateData,
				int[] updatedFlag);

	/**
	 *人脸1对1比对
	 * @param contextID 设备ID
	 * @param grayImageBuffer 灰度图像数据
	 * @param width 宽         默认值480
	 * @param height 高      默认值640
	 * @param enrollTemplateData 要比对的特征码
	 * @param candInfo 比对成功的ID与百分数                             [out]
	 * @param updatedTemplateData 比对成功后重新更新的特征码  [out]
	 * @param updatedFlag 需要更新的标志  [out]
	 * @return int类型          PFSBIO_OK(0)成功，其他为失败
	 */
	public native int pisFcVerify(
	    		int contextID, 
	    		byte[] grayImageBuffer, 
	    		int width, 
	    		int height, 
	    		byte[] enrollTemplateData,
	    		long[] candInfo,
				byte[] updatedTemplateData,
				int[] updatedFlag);

	
	
	/**
	 *人脸识别
	 * @param contextID 设备ID
	 * @param imgBuffer 灰度图像数据
	 * @param width 宽         默认值480
	 * @param height 高      默认值640
	 * @param faceRect 返回的人脸信息 [out]
	 * @return int类型          PFSBIO_OK(0)成功，其他为失败
	 */
	public native int pisFcCheckTpl(int contextID, byte[] imgBuffer,int width,int height,short[] faceRect);

	
	/**
     *创建人脸模型
     * @param contextID 设备ID
     * @param enrollCount  生成特征码的第几张图
     * @param grayImageBuffer 灰度图像数据
	 * @param width 宽         默认值480
	 * @param height 高      默认值640
	 * @param templateData 生成的特征码 [out]
	 * @return int类型          PFSBIO_OK(0)成功，其他为失败
	 */
	public native int pisFcCreateTemplate(
	    		int contextID, 
	    		int enrollCount, 
	    		byte[] grayImageBuffer, 
	    		int width, 
	    		int height, 
	    		int[] templateData
	    		);
	    public native int pisFcIdentifyTpl(
	    		int contextID, 
	    		byte[] identifyTemplateData,
	    		int[] identifiedTID,
	    		long[] candInfo,
				byte[] updatedTemplateData,
				int[] updatedFlag);
	    
	    public native int pisFcVerifyTpl(
	    		int contextID, 
	    		byte[] verifyTemplateData,
	    		byte[] enrollTemplateData,
	    		long[] candInfo,
				byte[] updatedTemplateData,
				int[] updatedFlag);

	
	/**
	 *检测重复建模
	 * @param contextID 设备ID
	 * @param enrollTemplateData  当前的特征码
	 * @param identifiedTID 返回原来的已有的特征码ID
	 * @return int类型          PFSBIO_OK(0)成功，其他为失败
	 */
	public native int pisFcCheckDuplicate(
	    		int contextID, 
	    		byte[] enrollTemplateData,
	    		int[] identifiedTID);

	// ===============TptArray Management
	/**
     *获取当前建模的个数
     * @param contextID 设备ID
     * @param totalCounts  当前建模总格数 [out]
	 * @return int类型          PFSBIO_OK(0)成功，其他为失败
	 */
	public native int pisFcGetCountTptArray(int contextID, int[] totalCounts);

	
	/**
     *删除指定建好的人脸模型
     * @param contextID 设备ID
     * @param templateID  要删除的模型ID
	 * @return int类型          PFSBIO_OK(0)成功，其他为失败
	 */
	public native int pisFcDeleteAtTptArray(int contextID, int templateID);

	
	/**
     *获取模型数据
     * @param contextID 设备ID
     * @param templateID  模型ID
     * @param templateData  模型数据 [out]
	 * @return int类型          PFSBIO_OK(0)成功，其他为失败
	 */
	public native int pisFcGetAtTptArray(	
	    		int contextID, 
	    		int templateID, 
				byte[] templateData);

	
	/**
     *设置模型数据
     * @param contextID 设备ID
     * @param templateID  模型ID
     * @param templateData  模型数据
	 * @return int类型          PFSBIO_OK(0)成功，其他为失败
	 */
	public native int pisFcSetAtTptArray(
	    		int contextID,
	    		int templateID,
				byte[] templateData);

	
	/**
     *清除所有模型数据
     * @param contextID 设备ID
	 * @return int类型          PFSBIO_OK(0)成功，其他为失败
	 */
	public native int pisFcClearTptArray(int contextID);

	
	/**
     *打开红外灯
     * @param contextID 设备ID
     * @param controlValue 控制红外灯开或者关 PFSBIO_IR_LED_HIGH_ON PFSBIO_IR_LED_HIGH_OFF
	 * @return int类型          PFSBIO_OK(0)成功，其他为失败
	 */
	public native int pisFcInfraredLedControl(
	    		int contextID,
	    		int controlValue);

	public native int pisFcConvertBayer2RGB(int contextID,byte[] inBuff,byte[] rBuf, byte[] gBuf, byte[] bBuf, int width, int height);

	public static String trimEnd(String s, String suffix) {

		if (s.endsWith(suffix)) {

			return s.substring(0, s.length() - suffix.length());

		}
		return s;
	}

	public static String ByteArrayUtf8ToString(byte[] aByteArray)
			    {
			        String strRet = ""; 
			        try
			        {
			            strRet = new String(aByteArray, "UTF-8");//System.Encoding.GetEncoding("utf-8").GetString(aByteArray, 0, aByteArray.Length);
			             
			            strRet = strRet.trim();
			        }
			        catch (Exception ex)
			        {
			            //MessageBox.Show(ex.Message);
			        }
			        return strRet;
			    } 

	 public static synchronized Ast2600JNI getInstance()
	 {
	    	if(myself == null) myself = new Ast2600JNI();
	    	return myself;
	 }
}
