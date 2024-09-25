package vn.lvc.quanlychitieu.model;

public class LoaiChi {
    private String maLoai;
    private int icon;

    public LoaiChi() {
    }

    public LoaiChi(String maLoai, int icon) {
        this.maLoai = maLoai;
        this.icon = icon;
    }

    public String getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(String maLoai) {
        this.maLoai = maLoai;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
