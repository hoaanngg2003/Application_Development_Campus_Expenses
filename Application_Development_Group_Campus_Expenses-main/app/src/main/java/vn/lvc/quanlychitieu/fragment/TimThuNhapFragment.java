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
import vn.lvc.quanlychitieu.adapter.LoaiThuSpnAdapter;
import vn.lvc.quanlychitieu.adapter.ThuNhapAdapter;
import vn.lvc.quanlychitieu.dao.LoaiThuDAO;
import vn.lvc.quanlychitieu.dao.ThuNhapDAO;
import vn.lvc.quanlychitieu.model.LoaiThu;
import vn.lvc.quanlychitieu.model.ThuNhap;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class TimThuNhapFragment extends Fragment {
    final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");

    Spinner spn_loaithu;
    ThuNhapAdapter thuNhapAdapter;
    RecyclerView rcv_thu;
    EditText edt_timthu;
    Button btn_ngaythustart,btn_ngaythuend,btn_timthu;
    ThuNhapDAO thuNhapDAO;
    LoaiThuDAO loaiThuDAO;
    List<LoaiThu> loaiThuList;
    List<ThuNhap> thuNhapList;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tim_thu_nhap, container, false);
        thuNhapDAO = new ThuNhapDAO(getContext());
        loaiThuDAO = new LoaiThuDAO(getContext());
        initView();
        getLoaiThu();
        numberFormat();
        chonNgayBatDau();
        chonNgayKetThuc();
        setupRecycleView();
        tim();
        return view;
    }
    public void initView(){
        spn_loaithu = view.findViewById(R.id.spn_loaithu);
        edt_timthu = view.findViewById(R.id.edt_timthu);
        btn_ngaythustart = view.findViewById(R.id.btn_ngaythustart);
        btn_ngaythuend = view.findViewById(R.id.btn_ngaythuend);
        rcv_thu = view.findViewById(R.id.rcv_thu);
        btn_timthu = view.findViewById(R.id.btn_timthu);
    }
    public void numberFormat(){
        edt_timthu.addTextChangedListener(new NumberTextWatcherForThousand(edt_timthu));
    }
    public void getLoaiThu(){
        loaiThuList = loaiThuDAO.getAllLoaiThu();
        loaiThuList.add(new LoaiThu("All",R.mipmap.ic_launcher));
        LoaiThuSpnAdapter loaiThuSpnAdapter = new LoaiThuSpnAdapter(loaiThuList,getContext());
        spn_loaithu.setAdapter(loaiThuSpnAdapter);
        spn_loaithu.setSelection(loaiThuList.size()-1);
    }
    Calendar calendar1;
    public void chonNgayBatDau(){
        btn_ngaythustart.setOnClickListener(new View.OnClickListener() {
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
                        btn_ngaythustart.setText(sdf.format(calendar1.getTime()));
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
    }
     Calendar calendar2;
    public void chonNgayKetThuc(){
        btn_ngaythuend.setOnClickListener(new View.OnClickListener() {
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
                        btn_ngaythuend.setText(sdf.format(calendar2.getTime()));
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
    }
    public void setupRecycleView(){
        thuNhapList = new ArrayList<>();
        rcv_thu.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        rcv_thu.setHasFixedSize(true);
        thuNhapAdapter = new ThuNhapAdapter(getContext(),thuNhapList);
        rcv_thu.setAdapter(thuNhapAdapter);
    }
    String loai ="",tien = "",ngayBatDau = "", ngayKetThuc = "";
    public void tim(){
        btn_timthu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tien = "";
                ngayBatDau="";
                ngayKetThuc="";

                 loai = loaiThuList.get(spn_loaithu.getSelectedItemPosition()).getMaLoai();

                if (btn_ngaythustart.getText().toString().equals("Choose start day")
                && btn_ngaythuend.getText().toString().equals("Choose end day")) {
                    if (!edt_timthu.getText().toString().trim().isEmpty()) {
                        tien =edt_timthu.getText().toString().trim().replace(",","");
                    }
                }else if (btn_ngaythuend.getText().toString().equals("Choose end day")) {
                    ngayBatDau = sdf1.format(calendar1.getTime());
                    if (!edt_timthu.getText().toString().trim().isEmpty()) {
                        tien = edt_timthu.getText().toString().trim().replace(",","");
                    }
                } else if (btn_ngaythustart.getText().toString().equals("Choose start day")) {
                    ngayKetThuc = sdf1.format(calendar2.getTime());
                    if (!edt_timthu.getText().toString().trim().isEmpty()) {
                        tien = edt_timthu.getText().toString().trim().replace(",", "");
                    }
                }
                else {
                    ngayBatDau = sdf1.format(calendar1.getTime());
                    ngayKetThuc = sdf1.format(calendar2.getTime());
                    if (!edt_timthu.getText().toString().trim().isEmpty()) {
                        tien = edt_timthu.getText().toString().trim().replace(",","");
                    }
                }
                    changeData(thuNhapDAO.tim(loai, tien, ngayBatDau, ngayKetThuc));
            }
        });
    }
    public void changeData(List<ThuNhap> lists){
        thuNhapList.clear();
        thuNhapList = lists;
        thuNhapAdapter.setDataChange(thuNhapList);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (loai.isEmpty() && tien.isEmpty() && ngayBatDau.isEmpty() && ngayKetThuc.isEmpty()){

        }else{
            changeData(thuNhapDAO.tim(loai, tien, ngayBatDau, ngayKetThuc));
            }
        }
}
