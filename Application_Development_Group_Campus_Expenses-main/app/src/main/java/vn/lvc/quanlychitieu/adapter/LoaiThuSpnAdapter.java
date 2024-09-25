package vn.lvc.quanlychitieu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import vn.lvc.quanlychitieu.R;
import vn.lvc.quanlychitieu.model.LoaiThu;

import java.util.List;

public class LoaiThuSpnAdapter extends BaseAdapter {
    List<LoaiThu> loaiThuList;
    final Context context;
    public LoaiThuSpnAdapter(List<LoaiThu> loaiThuList, Context context) {
        this.loaiThuList = loaiThuList;
        this.context = context;
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
    public static class LoaiThuSpnHolder{
        ImageView ivLoai;
        TextView tvLoai;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LoaiThuSpnHolder holder;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_loai,viewGroup,false);

            holder = new LoaiThuSpnHolder();
            holder.ivLoai = view.findViewById(R.id.ivLoai);
            holder.tvLoai = view.findViewById(R.id.tvLoai);

            view.setTag(holder);
        }else{
            holder = (LoaiThuSpnHolder) view.getTag();
        }

         LoaiThu loaiThu = loaiThuList.get(i);

        holder.ivLoai.setImageResource(loaiThu.getIcon());
        holder.tvLoai.setText(loaiThu.getMaLoai());

        return view;
    }
    public void setDataChange(List<LoaiThu> items){
        loaiThuList = items;
        notifyDataSetChanged();
    }
}
