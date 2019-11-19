package com.github.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "CARD_LOSE_INFO")
public class CardLoseInfo {
    private String stId;
    private String st01;
    private String st02;
    private String st03;
    private String st04;
    private String st05;
    private String st06;
    private String st07;
    private String st08;
    private String st09;
    private String st10;
    private String st11;
    private String st12;
    private String st13;
    private String st14;
    private String st15;
    private String st16;
    private String stType;

    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @Column(name = "ST_ID", nullable = false, length = 50)
    public String getStId() {
        return stId;
    }

    public void setStId(String stId) {
        this.stId = stId;
    }

    @Basic
    @Column(name = "ST_01", nullable = true, length = 200)
    public String getSt01() {
        return st01;
    }

    public void setSt01(String st01) {
        this.st01 = st01;
    }

    @Basic
    @Column(name = "ST_02", nullable = true, length = 200)
    public String getSt02() {
        return st02;
    }

    public void setSt02(String st02) {
        this.st02 = st02;
    }

    @Basic
    @Column(name = "ST_03", nullable = true, length = 200)
    public String getSt03() {
        return st03;
    }

    public void setSt03(String st03) {
        this.st03 = st03;
    }

    @Basic
    @Column(name = "ST_04", nullable = true, length = 200)
    public String getSt04() {
        return st04;
    }

    public void setSt04(String st04) {
        this.st04 = st04;
    }

    @Basic
    @Column(name = "ST_05", nullable = true, length = 200)
    public String getSt05() {
        return st05;
    }

    public void setSt05(String st05) {
        this.st05 = st05;
    }

    @Basic
    @Column(name = "ST_06", nullable = true, length = 200)
    public String getSt06() {
        return st06;
    }

    public void setSt06(String st06) {
        this.st06 = st06;
    }

    @Basic
    @Column(name = "ST_07", nullable = true, length = 200)
    public String getSt07() {
        return st07;
    }

    public void setSt07(String st07) {
        this.st07 = st07;
    }

    @Basic
    @Column(name = "ST_08", nullable = true, length = 200)
    public String getSt08() {
        return st08;
    }

    public void setSt08(String st08) {
        this.st08 = st08;
    }

    @Basic
    @Column(name = "ST_09", nullable = true, length = 200)
    public String getSt09() {
        return st09;
    }

    public void setSt09(String st09) {
        this.st09 = st09;
    }

    @Basic
    @Column(name = "ST_10", nullable = true, length = 200)
    public String getSt10() {
        return st10;
    }

    public void setSt10(String st10) {
        this.st10 = st10;
    }

    @Basic
    @Column(name = "ST_11", nullable = true, length = 200)
    public String getSt11() {
        return st11;
    }

    public void setSt11(String st11) {
        this.st11 = st11;
    }

    @Basic
    @Column(name = "ST_12", nullable = true, length = 200)
    public String getSt12() {
        return st12;
    }

    public void setSt12(String st12) {
        this.st12 = st12;
    }

    @Basic
    @Column(name = "ST_13", nullable = true, length = 200)
    public String getSt13() {
        return st13;
    }

    public void setSt13(String st13) {
        this.st13 = st13;
    }

    @Basic
    @Column(name = "ST_14", nullable = true, length = 200)
    public String getSt14() {
        return st14;
    }

    public void setSt14(String st14) {
        this.st14 = st14;
    }

    @Basic
    @Column(name = "ST_15", nullable = true, length = 200)
    public String getSt15() {
        return st15;
    }

    public void setSt15(String st15) {
        this.st15 = st15;
    }

    @Basic
    @Column(name = "ST_16", nullable = true, length = 200)
    public String getSt16() {
        return st16;
    }

    public void setSt16(String st16) {
        this.st16 = st16;
    }

    @Basic
    @Column(name = "ST_TYPE", nullable = true, length = 50)
    public String getStType() {
        return stType;
    }

    public void setStType(String stType) {
        this.stType = stType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardLoseInfo that = (CardLoseInfo) o;
        return Objects.equals(stId, that.stId) &&
                Objects.equals(st01, that.st01) &&
                Objects.equals(st02, that.st02) &&
                Objects.equals(st03, that.st03) &&
                Objects.equals(st04, that.st04) &&
                Objects.equals(st05, that.st05) &&
                Objects.equals(st06, that.st06) &&
                Objects.equals(st07, that.st07) &&
                Objects.equals(st08, that.st08) &&
                Objects.equals(st09, that.st09) &&
                Objects.equals(st10, that.st10) &&
                Objects.equals(st11, that.st11) &&
                Objects.equals(st12, that.st12) &&
                Objects.equals(st13, that.st13) &&
                Objects.equals(st14, that.st14) &&
                Objects.equals(st15, that.st15) &&
                Objects.equals(st16, that.st16) &&
                Objects.equals(stType, that.stType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stId, st01, st02, st03, st04, st05, st06, st07, st08, st09, st10, st11, st12, st13, st14, st15, st16, stType);
    }
}
