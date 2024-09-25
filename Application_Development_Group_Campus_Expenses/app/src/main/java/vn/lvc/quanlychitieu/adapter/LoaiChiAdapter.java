package vn.lvc.quanlychitieu.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import vn.lvc.quanlychitieu.R;
import vn.lvc.quanlychitieu.ThemLoaiChiActivity;
import vn.lvc.quanlychitieu.dao.ChiTieuDAO;
import vn.lvc.quanlychitieu.dao.LoaiChiDAO;
import vn.lvc.quanlychitieu.model.LoaiChi;

import java.util.List;

public class LoaiChiAdapter extends BaseAdapter {
    final Context context;
    List<LoaiChi> loaiChiList;
    LoaiChiDAO loaiChiDAO;
    public LoaiChiAdapter(Context context, List<LoaiChi> loaiChiList) {
        this.context = context;
        this.loaiChiList = loaiChiList;
    }
    @Override
    public int getCount() {
        return loaiChiList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    public static class LoaiChiHolder{
        ImageView ivLoaiChi;
        TextView tvLoaiChi;
        ImageView ivSuaLoaiChi,ivXoaLoaiChi;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LoaiChiHolder holder;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_loai2,viewGroup,false);

            holder = new LoaiChiHolder();
            holder.ivLoaiChi = view.findViewById(R.id.ivIconLoai);
            holder.tvLoaiChi = view.findViewById(R.id.tvTenLoai);
            holder.ivSuaLoaiChi = view.findViewById(R.id.ivSuaLoai);
            holder.ivXoaLoaiChi = view.findViewById(R.id.ivXoaLoai);

            view.setTag(holder);
        }else{
            holder = (LoaiChiHolder) view.getTag();
        }
        loaiChiDAO = new LoaiChiDAO(context);
        final LoaiChi loaiChi = loaiChiList.get(i);

        holder.ivLoaiChi.setImageResource(loaiChi.getIcon());
        holder.tvLoaiChi.setText(loaiChi.getMaLoai());


        holder.ivXoaLoaiChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkUsedLoai(loaiChi.getMaLoai()) > 0){
                    if(loaiChiList.size() == 1){
                        Toast.makeText(context,"Không thể xóa",Toast.LENGTH_SHORT).show();
                    }else {
                        if (loaiChiDAO.deleteLoaiChi(loaiChi.getMaLoai()) > 0){
                            loaiChiList.remove(loaiChi);
                            notifyDataSetChanged();
                        }
                    }
                }else{
                    Toast.makeText(context,"Loại chi đã được sử dụng không thể xóa",Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.ivSuaLoaiChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ThemLoaiChiActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("loaichi",loaiChi.getMaLoai());
                bundle.putInt("icon",loaiChi.getIcon());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        return view;
    }
    public void setDataChange(List<LoaiChi> items){
        loaiChiList = items;
        notifyDataSetChanged();
  }
    public int checkUsedLoai(String loai){
        ChiTieuDAO chiTieuDAO = new ChiTieuDAO(context);
        for (int i = 0 ; i < chiTieuDAO.getAllChiTieu().size() ; i++){
            if (loai.equals(chiTieuDAO.getAllChiTieu().get(i).getMaLoai())){
                return -1;
            }
        }
        return 1;
    }
}
