package jowney.com.facerecognition.video.facerecognize_jni;

/**
 * 人脸聚焦，关于人脸位置以及人脸五个点的位置，为人脸比对做准备
 * @author lenovo
 *
 */
public class FaceInfo {

	/**
	 * 人脸位置底部
	 */
	private int bottom;
	
	/**
	 * 人脸位置右边
	 */
	private int right;
	
	/**
	 * 人脸位置左边
	 */
	private int left;
	
	/**
	 * 人脸位置上方
	 */
	private int top;
	
	/**
	 * 右眼中心坐标X
	 */
	private int landmark_right_eye_center_x;
	
	/**
	 * 右眼中心坐标Y
	 */
	private int landmark_right_eye_center_y;
	
	/**
	 * 左眼眼中心坐标X
	 */
	private int landmark_left_eye_center_x;
	
	/**
	 * 左眼中心坐标Y
	 */
	private int landmark_left_eye_center_y;
	
	/**
	 * 左嘴角坐标X
	 */
	private int landmark_mouth_left_corner_x;
	
	/**
	 * 左嘴角坐标Y
	 */
	private int landmark_mouth_left_corner_y;
	
	/**
	 * 右嘴角坐标X
	 */
	private int landmark_mouth_right_corner_x;
	
	/**
	 * 右嘴角坐标Y
	 */
	private int landmark_mouth_right_corner_y;
	
	/**
	 * 鼻尖坐标X
	 */
	private int landmark_nose_top_x;
	
	/**
	 * 鼻尖坐标Y
	 */
	private int landmark_nose_top_y;
	
	public int getBottom() {
		return bottom;
	}
	public void setBottom(int bottom) {
		this.bottom = bottom;
	}
	public int getRight() {
		return right;
	}
	public void setRight(int right) {
		this.right = right;
	}
	public int getLeft() {
		return left;
	}
	public void setLeft(int left) {
		this.left = left;
	}
	public int getTop() {
		return top;
	}
	public void setTop(int top) {
		this.top = top;
	}
	public int getLandmark_right_eye_center_x() {
		return landmark_right_eye_center_x;
	}
	public void setLandmark_right_eye_center_x(int landmark_right_eye_center_x) {
		this.landmark_right_eye_center_x = landmark_right_eye_center_x;
	}
	public int getLandmark_right_eye_center_y() {
		return landmark_right_eye_center_y;
	}
	public void setLandmark_right_eye_center_y(int landmark_right_eye_center_y) {
		this.landmark_right_eye_center_y = landmark_right_eye_center_y;
	}
	public int getLandmark_left_eye_center_x() {
		return landmark_left_eye_center_x;
	}
	public void setLandmark_left_eye_center_x(int landmark_left_eye_center_x) {
		this.landmark_left_eye_center_x = landmark_left_eye_center_x;
	}
	public int getLandmark_left_eye_center_y() {
		return landmark_left_eye_center_y;
	}
	public void setLandmark_left_eye_center_y(int landmark_left_eye_center_y) {
		this.landmark_left_eye_center_y = landmark_left_eye_center_y;
	}
	public int getLandmark_mouth_left_corner_x() {
		return landmark_mouth_left_corner_x;
	}
	public void setLandmark_mouth_left_corner_x(int landmark_mouth_left_corner_x) {
		this.landmark_mouth_left_corner_x = landmark_mouth_left_corner_x;
	}
	public int getLandmark_mouth_left_corner_y() {
		return landmark_mouth_left_corner_y;
	}
	public void setLandmark_mouth_left_corner_y(int landmark_mouth_left_corner_y) {
		this.landmark_mouth_left_corner_y = landmark_mouth_left_corner_y;
	}
	public int getLandmark_mouth_right_corner_x() {
		return landmark_mouth_right_corner_x;
	}
	public void setLandmark_mouth_right_corner_x(int landmark_mouth_right_corner_x) {
		this.landmark_mouth_right_corner_x = landmark_mouth_right_corner_x;
	}
	public int getLandmark_mouth_right_corner_y() {
		return landmark_mouth_right_corner_y;
	}
	public void setLandmark_mouth_right_corner_y(int landmark_mouth_right_corner_y) {
		this.landmark_mouth_right_corner_y = landmark_mouth_right_corner_y;
	}
	public int getLandmark_nose_top_x() {
		return landmark_nose_top_x;
	}
	public void setLandmark_nose_top_x(int landmark_nose_top_x) {
		this.landmark_nose_top_x = landmark_nose_top_x;
	}
	public int getLandmark_nose_top_y() {
		return landmark_nose_top_y;
	}
	public void setLandmark_nose_top_y(int landmark_nose_top_y) {
		this.landmark_nose_top_y = landmark_nose_top_y;
	}

}


