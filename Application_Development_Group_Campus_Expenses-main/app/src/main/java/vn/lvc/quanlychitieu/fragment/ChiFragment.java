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

import vn.lvc.quanlychitieu.ChiTieuActivity;
import vn.lvc.quanlychitieu.MainActivity;
import vn.lvc.quanlychitieu.R;
import vn.lvc.quanlychitieu.adapter.ChiTieuAdapter;
import vn.lvc.quanlychitieu.dao.ChiTieuDAO;
import vn.lvc.quanlychitieu.model.ChiTieu;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class ChiFragment extends Fragment {
    RecyclerView rcvChi;
    FloatingActionButton fabChi;
    ChiTieuDAO chiTieuDAO;
    List<ChiTieu> chiTieuList;
    ChiTieuAdapter chiTieuAdapter;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       view = inflater.inflate(R.layout.fragment_chi, container, false);
       initView();
       setupRecyclerView();
       showHideWhenScroll();


       fabChi.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(getContext(), ChiTieuActivity.class));
           }
       });

        return view;
    }
    public void initView(){
        rcvChi = view.findViewById(R.id.rcvChi);
        fabChi = view.findViewById(R.id.fabChi);
    }
    public void setupRecyclerView(){
        chiTieuDAO = new ChiTieuDAO(getContext());
        chiTieuList = chiTieuDAO.getAllChiTieu();
        rcvChi.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        rcvChi.setHasFixedSize(true);
        chiTieuAdapter = new ChiTieuAdapter(getContext(),chiTieuList);
        rcvChi.setAdapter(chiTieuAdapter);
    }
    public void showHideWhenScroll(){
        rcvChi.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0){
                    fabChi.hide();
                }else{
                    fabChi.show();
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        chiTieuList.clear();
        chiTieuList = chiTieuDAO.getAllChiTieu();
        chiTieuAdapter.setDataChange(chiTieuList);
    }

}