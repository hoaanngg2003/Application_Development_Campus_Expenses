package vn.lvc.quanlychitieu.model;

import java.util.Date;

public class ChiTieu {
    private int maChi;
    private String maLoai;
    private double tien;
    private Date ngay;
    private String ghiChu;

    public ChiTieu() {
    }

    public ChiTieu(int maChi, String maLoai, double tien, Date ngay, String ghiChu) {
        this.maChi = maChi;
        this.maLoai = maLoai;
        this.tien = tien;
        this.ngay = ngay;
        this.ghiChu = ghiChu;
    }

    public int getMaChi() {
        return maChi;
    }

    public void setMaChi(int maChi) {
        this.maChi = maChi;
    }

    public String getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(String maLoai) {
        this.maLoai = maLoai;
    }

    public double getTien() {
        return tien;
    }

    public void setTien(double tien) {
        this.tien = tien;
    }

    public Date getNgay() {
        return ngay;
    }

    public void setNgay(Date ngay) {
        this.ngay = ngay;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}
