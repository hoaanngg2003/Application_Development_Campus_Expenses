package vn.lvc.quanlychitieu.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import vn.lvc.quanlychitieu.R;
import vn.lvc.quanlychitieu.ThuNhapActivity;
import vn.lvc.quanlychitieu.dao.LoaiThuDAO;
import vn.lvc.quanlychitieu.dao.ThuNhapDAO;
import vn.lvc.quanlychitieu.model.ThuNhap;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class    ThuNhapAdapter extends RecyclerView.Adapter<ThuNhapAdapter.ThuNhapHolder> {
    final Context context;
    List<ThuNhap> thuNhapList;
    final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    ThuNhapDAO thuNhapDAO;
    LoaiThuDAO loaiThuDAO;
    final DecimalFormat decimalFormat = new DecimalFormat("###,###.###");
    NumberFormat n = NumberFormat.getCurrencyInstance(Locale.US);
    public ThuNhapAdapter(Context context, List<ThuNhap> thuNhapList) {
        this.context = context;
        this.thuNhapList = thuNhapList;
    }

    @NonNull
    @Override
    public ThuNhapHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_thu_chi,parent,false);
        return new ThuNhapHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThuNhapHolder holder, int position) {
        loaiThuDAO = new LoaiThuDAO(context);
        thuNhapDAO = new ThuNhapDAO(context);
        final ThuNhap thuNhap = thuNhapList.get(position);
        int icon = thuNhapDAO.getIconThu(thuNhap.getMaLoai());

        holder.iv_Thu.setImageResource(icon);
        holder.tv_LoaiThu.setText(thuNhap.getMaLoai());
        holder.tv_NgayThu.setText(sdf.format(thuNhap.getNgay()));
        holder.tv_GhiChuThu.setText(thuNhap.getGhiChu());
        holder.tv_TienThu.setText("+"+decimalFormat.format(thuNhap.getTien()) + " VND");

        holder.iv_SuaThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int ma = thuNhap.getMaThu();
                String loai = thuNhap.getMaLoai();
                Double tien = thuNhap.getTien();
                String ngay = sdf.format(thuNhap.getNgay());
                String ghiChu = thuNhap.getGhiChu();

                Bundle bundle = new Bundle();
                bundle.putInt("ma",ma);
                bundle.putString("loai",loai);
                bundle.putDouble("tien",tien);
                bundle.putString("ngay",ngay);
                bundle.putString("ghichu",ghiChu);

                Intent intent = new Intent(context, ThuNhapActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return thuNhapList.size();
    }

    public static class ThuNhapHolder extends RecyclerView.ViewHolder {
        final ImageView iv_Thu;
        final ImageView iv_SuaThu;
        final TextView tv_LoaiThu;
        final TextView tv_NgayThu;
        final TextView tv_GhiChuThu;
        final TextView tv_TienThu;
        public ThuNhapHolder(@NonNull View itemView) {
            super(itemView);
            iv_Thu = itemView.findViewById(R.id.iv_Loai);
            iv_SuaThu = itemView.findViewById(R.id.iv_Update);
            tv_LoaiThu = itemView.findViewById(R.id.tv_TenLoai);
            tv_NgayThu = itemView.findViewById(R.id.tv_NgayThang);
            tv_GhiChuThu = itemView.findViewById(R.id.tv_GhiChu);
            tv_TienThu = itemView.findViewById(R.id.tv_Tien);

        }
    }
    public void setDataChange(List<ThuNhap> itemsThu){
        thuNhapList = itemsThu;
        notifyDataSetChanged();
    }



}
