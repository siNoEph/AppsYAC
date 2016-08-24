package com.yogyakartaandroidcommunity.yac.Serialize;

/**
 * Created by Septian on 20-Aug-16.
 */
public class AnggotaSerialize {

    int id;
    String memberId, nama, email, hp;

    public AnggotaSerialize() {
    }

    public AnggotaSerialize(int id, String memberId) {
        this.id = id;
        this.memberId = memberId;
    }

    public AnggotaSerialize(int id, String memberId, String nama, String email, String hp) {
        this.id = id;
        this.memberId = memberId;
        this.nama = nama;
        this.email = email;
        this.hp = hp;
    }

    public AnggotaSerialize(String memberId, String nama, String email, String hp) {
        this.memberId = memberId;
        this.nama = nama;
        this.email = email;
        this.hp = hp;
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

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHp() {
        return hp;
    }

    public void setHp(String hp) {
        this.hp = hp;
    }
}
