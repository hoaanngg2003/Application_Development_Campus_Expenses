package vn.lvc.quanlychitieu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import vn.lvc.quanlychitieu.R;
import vn.lvc.quanlychitieu.model.LoaiChi;

import java.util.List;

public class LoaiChiSpnAdapter  extends BaseAdapter {
    List<LoaiChi> loaiChiList;
    final Context context;
    public LoaiChiSpnAdapter(List<LoaiChi> loaiChiList, Context context) {
        this.loaiChiList = loaiChiList;
        this.context = context;
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
    public static class LoaiChiSpnHolder{
        ImageView ivLoai;
        TextView tvLoai;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LoaiChiSpnHolder holder;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_loai,viewGroup,false);

            holder = new LoaiChiSpnHolder();
            holder.ivLoai = view.findViewById(R.id.ivLoai);
            holder.tvLoai = view.findViewById(R.id.tvLoai);

            view.setTag(holder);
        }else{
            holder = (LoaiChiSpnHolder) view.getTag();
        }

        LoaiChi loaiChi = loaiChiList.get(i);

        holder.ivLoai.setImageResource(loaiChi.getIcon());
        holder.tvLoai.setText(loaiChi.getMaLoai());

        return view;
    }
    public void setDataChange(List<LoaiChi> items){
        loaiChiList = items;
        notifyDataSetChanged();
    }
}
