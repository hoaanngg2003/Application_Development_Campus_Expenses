package vn.lvc.quanlychitieu.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.lvc.quanlychitieu.R;
import vn.lvc.quanlychitieu.ThuNhapActivity;
import vn.lvc.quanlychitieu.adapter.ThuNhapAdapter;
import vn.lvc.quanlychitieu.dao.ThuNhapDAO;
import vn.lvc.quanlychitieu.model.ThuNhap;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class ThuFragment extends Fragment {
    FloatingActionButton fabThu;
    RecyclerView rcvThu;
    ThuNhapDAO thuNhapDAO;
    List<ThuNhap> thuNhapList;
    ThuNhapAdapter thuNhapAdapter;

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_thu, container, false);
        initView();

        setupRecyclerView();
        showHideWhenScroll();

        fabThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ThuNhapActivity.class));
            }
        });

        return view;
    }
    public void initView(){
        fabThu = view.findViewById(R.id.fabThu);
        rcvThu = view.findViewById(R.id.rcvThu);
    }
    public void setupRecyclerView(){
        thuNhapDAO = new ThuNhapDAO(getContext());
        thuNhapList = thuNhapDAO.getAllThuNhap();
        rcvThu.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        rcvThu.setHasFixedSize(true);
        thuNhapAdapter = new ThuNhapAdapter(getContext(),thuNhapList);
        rcvThu.setAdapter(thuNhapAdapter);
    }
    public void showHideWhenScroll(){
        rcvThu.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0){
                    fabThu.hide();
                }else{
                    fabThu.show();
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        thuNhapList.clear();
        thuNhapList = thuNhapDAO.getAllThuNhap();
        thuNhapAdapter.setDataChange(thuNhapList);
    }
}