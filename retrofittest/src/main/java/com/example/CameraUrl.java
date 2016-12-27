package com.example;

/**
 * Created by robert on 2016/11/1.
 */

public class CameraUrl {


    /**
     * id : 911
     * isNewRecord : false
     * remarks : null
     * createDate : null
     * updateDate : null
     * bccameraSeq : 1
     * bcmonitorName : 有室外防护罩的摄像机 : 标准
     * bcIPAddress : 0.0.0.0
     * bcmatch : 匹配
     * bcmodelName : big
     * bcguid : 83ec9c76-d226-4dbf-a2dd-a83e0e55c24d-001a7372
     * bcfloorName : F1
     * bcisUseNVR : 否
     * bcproduct : 海康
     * bcnvrChanne : -1
     * bcuserName : admin
     * bcpassWord : yz123456
     * bcport : 8000
     * bcnvrip : 0.0.0.0
     * httpUrl : m3u8
     */

    private String id;
    private boolean isNewRecord;
    private Object remarks;
    private Object createDate;
    private Object updateDate;
    private String bccameraSeq;
    private String bcmonitorName;
    private String bcIPAddress;
    private String bcmatch;
    private String bcmodelName;
    private String bcguid;
    private String bcfloorName;
    private String bcisUseNVR;
    private String bcproduct;
    private String bcnvrChanne;
    private String bcuserName;
    private String bcpassWord;
    private String bcport;
    private String bcnvrip;
    private String httpUrl;
    private String thumbnail;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isIsNewRecord() {
        return isNewRecord;
    }

    public void setIsNewRecord(boolean isNewRecord) {
        this.isNewRecord = isNewRecord;
    }

    public Object getRemarks() {
        return remarks;
    }

    public void setRemarks(Object remarks) {
        this.remarks = remarks;
    }

    public Object getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Object createDate) {
        this.createDate = createDate;
    }

    public Object getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Object updateDate) {
        this.updateDate = updateDate;
    }

    public String getBccameraSeq() {
        return bccameraSeq;
    }

    public void setBccameraSeq(String bccameraSeq) {
        this.bccameraSeq = bccameraSeq;
    }

    public String getBcmonitorName() {
        return bcmonitorName;
    }

    public void setBcmonitorName(String bcmonitorName) {
        this.bcmonitorName = bcmonitorName;
    }

    public String getBcIPAddress() {
        return bcIPAddress;
    }

    public void setBcIPAddress(String bcIPAddress) {
        this.bcIPAddress = bcIPAddress;
    }

    public String getBcmatch() {
        return bcmatch;
    }

    public void setBcmatch(String bcmatch) {
        this.bcmatch = bcmatch;
    }

    public String getBcmodelName() {
        return bcmodelName;
    }

    public void setBcmodelName(String bcmodelName) {
        this.bcmodelName = bcmodelName;
    }

    public String getBcguid() {
        return bcguid;
    }

    public void setBcguid(String bcguid) {
        this.bcguid = bcguid;
    }

    public String getBcfloorName() {
        return bcfloorName;
    }

    public void setBcfloorName(String bcfloorName) {
        this.bcfloorName = bcfloorName;
    }

    public String getBcisUseNVR() {
        return bcisUseNVR;
    }

    public void setBcisUseNVR(String bcisUseNVR) {
        this.bcisUseNVR = bcisUseNVR;
    }

    public String getBcproduct() {
        return bcproduct;
    }

    public void setBcproduct(String bcproduct) {
        this.bcproduct = bcproduct;
    }

    public String getBcnvrChanne() {
        return bcnvrChanne;
    }

    public void setBcnvrChanne(String bcnvrChanne) {
        this.bcnvrChanne = bcnvrChanne;
    }

    public String getBcuserName() {
        return bcuserName;
    }

    public void setBcuserName(String bcuserName) {
        this.bcuserName = bcuserName;
    }

    public String getBcpassWord() {
        return bcpassWord;
    }

    public void setBcpassWord(String bcpassWord) {
        this.bcpassWord = bcpassWord;
    }

    public String getBcport() {
        return bcport;
    }

    public void setBcport(String bcport) {
        this.bcport = bcport;
    }

    public String getBcnvrip() {
        return bcnvrip;
    }

    public void setBcnvrip(String bcnvrip) {
        this.bcnvrip = bcnvrip;
    }

    public String getHttpUrl() {
        return httpUrl;
    }

    public void setHttpUrl(String httpUrl) {
        this.httpUrl = httpUrl;
    }
    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public String toString() {
        return "CameraUrl{" +
                "id='" + id + '\'' +
                ", isNewRecord=" + isNewRecord +
                ", remarks=" + remarks +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", bccameraSeq='" + bccameraSeq + '\'' +
                ", bcmonitorName='" + bcmonitorName + '\'' +
                ", bcIPAddress='" + bcIPAddress + '\'' +
                ", bcmatch='" + bcmatch + '\'' +
                ", bcmodelName='" + bcmodelName + '\'' +
                ", bcguid='" + bcguid + '\'' +
                ", bcfloorName='" + bcfloorName + '\'' +
                ", bcisUseNVR='" + bcisUseNVR + '\'' +
                ", bcproduct='" + bcproduct + '\'' +
                ", bcnvrChanne='" + bcnvrChanne + '\'' +
                ", bcuserName='" + bcuserName + '\'' +
                ", bcpassWord='" + bcpassWord + '\'' +
                ", bcport='" + bcport + '\'' +
                ", bcnvrip='" + bcnvrip + '\'' +
                ", httpUrl='" + httpUrl + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                '}';
    }
}
