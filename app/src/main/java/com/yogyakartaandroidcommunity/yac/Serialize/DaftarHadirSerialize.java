package com.yogyakartaandroidcommunity.yac.Serialize;

/**
 * Created by Septian on 21-Aug-16.
 */
public class DaftarHadirSerialize {

    int id;
    String memberId, hadir;

    public DaftarHadirSerialize() {
    }

    public DaftarHadirSerialize(String memberId) {
        this.memberId = memberId;
    }

    public DaftarHadirSerialize(String memberId, String hadir) {
        this.memberId = memberId;
        this.hadir = hadir;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getHadir() {
        return hadir;
    }

    public void setHadir(String hadir) {
        this.hadir = hadir;
    }
}
