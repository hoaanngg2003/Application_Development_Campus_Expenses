package vn.lvc.quanlychitieu.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import vn.lvc.quanlychitieu.ChiTieuActivity;
import vn.lvc.quanlychitieu.R;
import vn.lvc.quanlychitieu.dao.ChiTieuDAO;
import vn.lvc.quanlychitieu.dao.LoaiChiDAO;
import vn.lvc.quanlychitieu.model.ChiTieu;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class ChiTieuAdapter extends RecyclerView.Adapter<ChiTieuAdapter.ChiTieuHolder> {
    final Context context;
    List<ChiTieu> chiTieuList;
    final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    ChiTieuDAO chiTieuDAO;
    LoaiChiDAO loaiChiDAO;
    final DecimalFormat decimalFormat = new DecimalFormat("###,###.###");

    public ChiTieuAdapter(Context context, List<ChiTieu> chiTieuList) {
        this.context = context;
        this.chiTieuList = chiTieuList;
    }

    @NonNull
    @Override
    public ChiTieuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_thu_chi,parent,false);
        return new ChiTieuHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChiTieuHolder holder, int position) {
        loaiChiDAO = new LoaiChiDAO(context);
        chiTieuDAO = new ChiTieuDAO(context);
        final ChiTieu chiTieu = chiTieuList.get(position);
        int icon = chiTieuDAO.getIconChi(chiTieu.getMaLoai());

        holder.iv_Chi.setImageResource(icon);
        holder.tv_LoaiChi.setText(chiTieu.getMaLoai());
        holder.tv_NgayChi.setText(sdf.format(chiTieu.getNgay()));
        holder.tv_GhiChuChi.setText(chiTieu.getGhiChu());
        holder.tv_TienChi.setText("-"+decimalFormat.format(chiTieu.getTien()) + " VND");

        holder.iv_SuaChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int ma = chiTieu.getMaChi();
                String loai = chiTieu.getMaLoai();
                Double tien = chiTieu.getTien();
                String ngay = sdf.format(chiTieu.getNgay());
                String ghiChu = chiTieu.getGhiChu();

                Bundle bundle = new Bundle();
                bundle.putInt("ma",ma);
                bundle.putString("loai",loai);
                bundle.putDouble("tien",tien);
                bundle.putString("ngay",ngay);
                bundle.putString("ghichu",ghiChu);

                Intent intent = new Intent(context, ChiTieuActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return chiTieuList.size();
    }

    public static class ChiTieuHolder extends RecyclerView.ViewHolder {
        final ImageView iv_Chi;
        final ImageView iv_SuaChi;
        final TextView tv_LoaiChi;
        final TextView tv_NgayChi;
        final TextView tv_GhiChuChi;
        final TextView tv_TienChi;
        public ChiTieuHolder(@NonNull View itemView) {
            super(itemView);
            iv_Chi = itemView.findViewById(R.id.iv_Loai);
            iv_SuaChi = itemView.findViewById(R.id.iv_Update);
            tv_LoaiChi = itemView.findViewById(R.id.tv_TenLoai);
            tv_NgayChi = itemView.findViewById(R.id.tv_NgayThang);
            tv_GhiChuChi = itemView.findViewById(R.id.tv_GhiChu);
            tv_TienChi = itemView.findViewById(R.id.tv_Tien);

            tv_TienChi.setTextColor(Color.RED);
        }
    }
    public void setDataChange(List<ChiTieu> itemsChi){
        chiTieuList = itemsChi;
        notifyDataSetChanged();
    }
}
