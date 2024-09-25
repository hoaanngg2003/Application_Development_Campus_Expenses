package vn.lvc.quanlychitieu.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import vn.lvc.quanlychitieu.numberformat.NumberTextWatcherForThousand;
import vn.lvc.quanlychitieu.R;
import vn.lvc.quanlychitieu.adapter.ChiTieuAdapter;
import vn.lvc.quanlychitieu.adapter.LoaiChiSpnAdapter;
import vn.lvc.quanlychitieu.dao.ChiTieuDAO;
import vn.lvc.quanlychitieu.dao.LoaiChiDAO;
import vn.lvc.quanlychitieu.model.ChiTieu;
import vn.lvc.quanlychitieu.model.LoaiChi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class TimChiTieuFragment extends Fragment {
    final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");

    Spinner spn_loaichi;
    ChiTieuAdapter chiTieuAdapter;
    RecyclerView rcv_chi;
    EditText edt_timchi;
    Button btn_ngaychistart,btn_ngaychiend,btn_timchi;
    ChiTieuDAO chiTieuDAO;
    LoaiChiDAO loaiChiDAO;
    List<LoaiChi> loaiChiList;
    List<ChiTieu> chiTieuList;


    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tim_chi_tieu, container, false);
        chiTieuDAO = new ChiTieuDAO(getContext());
        loaiChiDAO = new LoaiChiDAO(getContext());
        initView();
        getLoaiChi();
        numberFormat();
        chonNgayBatDau();
        chonNgayKetThuc();
        setupRecycleView();
        tim();

        return view;
    }
    public void initView(){
        spn_loaichi = view.findViewById(R.id.spn_loaichi);
        edt_timchi = view.findViewById(R.id.edt_timchi);
        btn_ngaychistart = view.findViewById(R.id.btn_ngaychistart);
        btn_ngaychiend = view.findViewById(R.id.btn_ngaychiend);
        rcv_chi = view.findViewById(R.id.rcv_chi);
        btn_timchi = view.findViewById(R.id.btn_timchi);
    }
    public void numberFormat(){
        edt_timchi.addTextChangedListener(new NumberTextWatcherForThousand(edt_timchi));
    }
    public void getLoaiChi(){
        loaiChiList = loaiChiDAO.getAllLoaiChi();
        loaiChiList.add(new LoaiChi("All",R.mipmap.ic_launcher));
        LoaiChiSpnAdapter loaiChiSpnAdapter = new LoaiChiSpnAdapter(loaiChiList,getContext());
        spn_loaichi.setAdapter(loaiChiSpnAdapter);
        spn_loaichi.setSelection(loaiChiList.size()-1);
    }
    Calendar calendar1;
    public void chonNgayBatDau(){
        btn_ngaychistart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar1 = Calendar.getInstance();
                int year = calendar1.get(Calendar.YEAR);
                int month = calendar1.get(Calendar.MONTH);
                int day = calendar1.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendar1.set(i,i1,i2);
                        btn_ngaychistart.setText(sdf.format(calendar1.getTime()));
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
    }
    Calendar calendar2;
    public void chonNgayKetThuc(){
        btn_ngaychiend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar2 = Calendar.getInstance();
                int year = calendar2.get(Calendar.YEAR);
                int month = calendar2.get(Calendar.MONTH);
                int day = calendar2.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendar2.set(i,i1,i2);
                        btn_ngaychiend.setText(sdf.format(calendar2.getTime()));
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
    }
    public void setupRecycleView(){
        chiTieuList = new ArrayList<>();
        rcv_chi.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        rcv_chi.setHasFixedSize(true);
        chiTieuAdapter = new ChiTieuAdapter(getContext(),chiTieuList);
        rcv_chi.setAdapter(chiTieuAdapter);
    }
    String loai ="",tien = "",ngayBatDau = "", ngayKetThuc = "";
    public void tim(){
        btn_timchi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tien = "";
                ngayBatDau="";
                ngayKetThuc="";

                loai = loaiChiList.get(spn_loaichi.getSelectedItemPosition()).getMaLoai();

                if (btn_ngaychistart.getText().toString().equals("Choose start day")
                        && btn_ngaychiend.getText().toString().equals("Choose end day")) {
                    if (!edt_timchi.getText().toString().trim().isEmpty()) {
                        tien =edt_timchi.getText().toString().trim().replace(",","");
                    }
                }else if (btn_ngaychiend.getText().toString().equals("Choose end day")) {
                    ngayBatDau = sdf1.format(calendar1.getTime());
                    if (!edt_timchi.getText().toString().trim().isEmpty()) {
                        tien = edt_timchi.getText().toString().trim().replace(",","");
                    }
                } else if (btn_ngaychistart.getText().toString().equals("Choose start day")) {
                    ngayKetThuc = sdf1.format(calendar2.getTime());
                    if (!edt_timchi.getText().toString().trim().isEmpty()) {
                        tien = edt_timchi.getText().toString().trim().replace(",", "");
                    }
                }
                else {
                    ngayBatDau = sdf1.format(calendar1.getTime());
                    ngayKetThuc = sdf1.format(calendar2.getTime());
                    if (!edt_timchi.getText().toString().trim().isEmpty()) {
                        tien = edt_timchi.getText().toString().trim().replace(",","");
                    }
                }

                    changeData(chiTieuDAO.tim(loai, tien, ngayBatDau, ngayKetThuc));

                }

        });
    }
    public void changeData(List<ChiTieu> lists){
        chiTieuList.clear();
        chiTieuList = lists;
        chiTieuAdapter.setDataChange(chiTieuList);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (loai.isEmpty() && tien.isEmpty() && ngayBatDau.isEmpty() && ngayKetThuc.isEmpty()){

        }else{
            changeData(chiTieuDAO.tim(loai, tien, ngayBatDau, ngayKetThuc));
        }
    }
}