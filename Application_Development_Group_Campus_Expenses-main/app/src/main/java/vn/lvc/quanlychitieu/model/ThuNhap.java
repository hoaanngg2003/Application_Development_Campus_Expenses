package vn.lvc.quanlychitieu.model;

import java.util.Date;

public class ThuNhap {
    private int maThu;
    private String maLoai;
    private double tien;
    private Date ngay;
    private String ghiChu;

    public ThuNhap() {
    }

    public ThuNhap(int maThu, String maLoai, double tien, Date ngay, String ghiChu) {
        this.maThu = maThu;
        this.maLoai = maLoai;
        this.tien = tien;
        this.ngay = ngay;
        this.ghiChu = ghiChu;
    }

    public int getMaThu() {
        return maThu;
    }

    public void setMaThu(int maThu) {
        this.maThu = maThu;
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
