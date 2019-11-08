package com.github.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "VOTE_PERSON")
public class VotePerson {
    private String stId;
    private String stItemId;
    private String stPersonName;
    private String stHeadImage;
    private String stSex;
    private String stCity;
    private String stProvince;
    private String stCountry;
    private String stUnionid;
    private String stOpenid;
    private Date dtVoteTime;

    @Id
    @Column(name = "ST_ID", nullable = false, length = 50)
    public String getStId() {
        return stId;
    }

    public void setStId(String stId) {
        this.stId = stId;
    }

    @Basic
    @Column(name = "ST_ITEM_ID", nullable = true, length = 50)
    public String getStItemId() {
        return stItemId;
    }

    public void setStItemId(String stItemId) {
        this.stItemId = stItemId;
    }

    @Basic
    @Column(name = "ST_PERSON_NAME", nullable = true, length = 200)
    public String getStPersonName() {
        return stPersonName;
    }

    public void setStPersonName(String stPersonName) {
        this.stPersonName = stPersonName;
    }

    @Basic
    @Column(name = "ST_SEX", nullable = true, length = 50)
    public String getStSex() {
        return stSex;
    }

    public void setStSex(String stSex) {
        this.stSex = stSex;
    }

    @Basic
    @Column(name = "ST_CITY", nullable = true, length = 100)
    public String getStCity() {
        return stCity;
    }

    public void setStCity(String stCity) {
        this.stCity = stCity;
    }

    @Basic
    @Column(name = "ST_PROVINCE", nullable = true, length = 100)
    public String getStProvince() {
        return stProvince;
    }

    public void setStProvince(String stProvince) {
        this.stProvince = stProvince;
    }

    @Basic
    @Column(name = "ST_COUNTRY", nullable = true, length = 100)
    public String getStCountry() {
        return stCountry;
    }

    public void setStCountry(String stCountry) {
        this.stCountry = stCountry;
    }

    @Basic
    @Column(name = "ST_UNIONID", nullable = true, length = 100)
    public String getStUnionid() {
        return stUnionid;
    }

    public void setStUnionid(String stUnionid) {
        this.stUnionid = stUnionid;
    }

    @Basic
    @Column(name = "ST_OPENID", nullable = true, length = 50)
    public String getStOpenid() {
        return stOpenid;
    }

    public void setStOpenid(String stOpenid) {
        this.stOpenid = stOpenid;
    }

    @Basic
    @Column(name = "DT_VOTE_TIME", nullable = true)
    public Date getDtVoteTime() {
        return dtVoteTime;
    }

    public void setDtVoteTime(Date dtVoteTime) {
        this.dtVoteTime = dtVoteTime;
    }

    @Basic
    @Column(name = "ST_HEAD_IMAGE", nullable = true, length = 500)
    public String getStHeadImage() {
        return stHeadImage;
    }

    public void setStHeadImage(String stHeadImage) {
        this.stHeadImage = stHeadImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VotePerson that = (VotePerson) o;
        return Objects.equals(stId, that.stId) &&
                Objects.equals(stItemId, that.stItemId) &&
                Objects.equals(stPersonName, that.stPersonName) &&
                Objects.equals(stHeadImage, that.stHeadImage) &&
                Objects.equals(stSex, that.stSex) &&
                Objects.equals(stCity, that.stCity) &&
                Objects.equals(stProvince, that.stProvince) &&
                Objects.equals(stCountry, that.stCountry) &&
                Objects.equals(stUnionid, that.stUnionid) &&
                Objects.equals(stOpenid, that.stOpenid) &&
                Objects.equals(dtVoteTime, that.dtVoteTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stId, stItemId, stPersonName, stHeadImage, stSex, stCity, stProvince, stCountry, stUnionid, stOpenid, dtVoteTime);
    }

    @Override
    public String toString() {
        return "VotePerson{" +
                "stId='" + stId + '\'' +
                ", stItemId='" + stItemId + '\'' +
                ", stPersonName='" + stPersonName + '\'' +
                ", stHeadImage='" + stHeadImage + '\'' +
                ", stSex='" + stSex + '\'' +
                ", stCity='" + stCity + '\'' +
                ", stProvince='" + stProvince + '\'' +
                ", stCountry='" + stCountry + '\'' +
                ", stUnionid='" + stUnionid + '\'' +
                ", stOpenid='" + stOpenid + '\'' +
                ", dtVoteTime=" + dtVoteTime +
                '}';
    }
}
