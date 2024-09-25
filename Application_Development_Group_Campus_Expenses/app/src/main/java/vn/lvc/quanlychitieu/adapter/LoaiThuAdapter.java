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
import vn.lvc.quanlychitieu.ThemLoaiThuActivity;
import vn.lvc.quanlychitieu.dao.LoaiThuDAO;
import vn.lvc.quanlychitieu.dao.ThuNhapDAO;
import vn.lvc.quanlychitieu.model.LoaiThu;

import java.util.List;

public class LoaiThuAdapter extends BaseAdapter {
    final Context context;
    List<LoaiThu> loaiThuList;
    LoaiThuDAO loaiThuDAO;
    public LoaiThuAdapter(Context context, List<LoaiThu> loaiThuList) {
        this.context = context;
        this.loaiThuList = loaiThuList;
    }
    @Override
    public int getCount() {
        return loaiThuList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    public static class LoaiThuHolder{
        ImageView ivLoaiThu;
        TextView tvLoaiThu;
        ImageView ivSuaLoaiThu,ivXoaLoaiThu;
    }
    @Override
    public View getView( int i, View view, ViewGroup viewGroup) {
        LoaiThuHolder holder;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_loai2,viewGroup,false);

            holder = new LoaiThuHolder();
            holder.ivLoaiThu = view.findViewById(R.id.ivIconLoai);
            holder.tvLoaiThu = view.findViewById(R.id.tvTenLoai);
            holder.ivSuaLoaiThu = view.findViewById(R.id.ivSuaLoai);
            holder.ivXoaLoaiThu = view.findViewById(R.id.ivXoaLoai);

            view.setTag(holder);
        }else{
            holder = (LoaiThuHolder) view.getTag();
        }
        loaiThuDAO = new LoaiThuDAO(context);
        final LoaiThu loaiThu = loaiThuList.get(i);

        holder.ivLoaiThu.setImageResource(loaiThu.getIcon());
        holder.tvLoaiThu.setText(loaiThu.getMaLoai());


        holder.ivXoaLoaiThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkUsedLoai(loaiThu.getMaLoai()) > 0){
                    if (loaiThuList.size() == 1){
                        Toast.makeText(context,"Không thể xóa",Toast.LENGTH_SHORT).show();
                    }else {
                        if (loaiThuDAO.deleteLoaiThu(loaiThu.getMaLoai()) > 0){
                            loaiThuList.remove(loaiThu);
                            notifyDataSetChanged();
                        }
                    }
                }else{
                    Toast.makeText(context,"Loại thu đã được sử dụng không thể xóa",Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.ivSuaLoaiThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ThemLoaiThuActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("loaithu",loaiThu.getMaLoai());
                bundle.putInt("icon",loaiThu.getIcon());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        return view;
    }
    public void setDataChange(List<LoaiThu> items){
        loaiThuList = items;
        notifyDataSetChanged();
    }
    public int checkUsedLoai(String loai){
        ThuNhapDAO thuNhapDAO = new ThuNhapDAO(context);
        for (int i = 0 ; i < thuNhapDAO.getAllThuNhap().size() ; i++){
            if (loai.equals(thuNhapDAO.getAllThuNhap().get(i).getMaLoai())){
                return -1;
            }
        }
        return 1;
    }
}
