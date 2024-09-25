package vn.lvc.quanlychitieu.model;

public class LoaiThu {
    private String maLoai;
    private int icon;

    public LoaiThu() {
    }

    public LoaiThu(String maLoai, int icon) {
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
