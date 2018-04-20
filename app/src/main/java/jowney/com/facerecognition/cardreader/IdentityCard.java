package jowney.com.facerecognition.cardreader;

import android.graphics.Bitmap;

import java.util.Date;

/**
 * @author Jowney
 * @time 2017-10-31  15:44
 * @describe
 */
public class IdentityCard {

    private String gender;
    private String genderCode;
    private String nation;
    private String nationCode;
    private String birdthDay;
    private String address;
    private String identity;
    private Bitmap bmpPhoto;
    private float[] feature = null;
    private String signOffice;
    private String startDate;
    //设置读到身份证时的时间,方便判断从读到卡到比对完成,一共用的时间
    private Date readCardTime;

    public Date getReadCardTime() {
        return readCardTime;
    }

    public void setReadCardTime(Date readCardTime) {
        this.readCardTime = readCardTime;
    }

    public float[] getFeature() {
        return feature;
    }

    public void setFeature(float[] feature) {
        this.feature = feature;
    }

    private String endDate;

    private String name;


    private int isBlack;
    private String blackJobId;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGenderCode() {
        return genderCode;
    }

    public void setGenderCode(String genderCode) {
        this.genderCode = genderCode;
    }

    public String getNationCode() {
        return nationCode;
    }

    public void setNationCode(String nationCode) {
        this.nationCode = nationCode;
    }

    public String getSignOffice() {
        return signOffice;
    }

    public void setSignOffice(String signOffice) {
        this.signOffice = signOffice;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Bitmap getBmpPhoto() {
        return bmpPhoto;
    }

    public void setBmpPhoto(Bitmap bmpPhoto) {
        this.bmpPhoto = bmpPhoto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getBirdthDay() {
        return birdthDay;
    }

    public void setBirdthDay(String birdthDay) {
        this.birdthDay = birdthDay;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public void recyclePhoto() {

        if (this.bmpPhoto != null) {
            this.bmpPhoto.recycle();
            this.bmpPhoto = null;
        }
        // System.gc();
    }

    public int getIsBlack() {
        return isBlack;
    }

    public void setIsBlack(int isBlack) {
        this.isBlack = isBlack;
    }

    public String getBlackJobId() {
        return blackJobId;
    }

    public void setBlackJobId(String blackJobId) {
        this.blackJobId = blackJobId;
    }

}