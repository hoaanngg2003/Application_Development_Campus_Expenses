package vn.lvc.quanlychitieu.fragment;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import vn.lvc.quanlychitieu.R;
import vn.lvc.quanlychitieu.dao.ChiTieuDAO;
import vn.lvc.quanlychitieu.dao.ThuNhapDAO;

import java.text.DecimalFormat;
import java.util.Calendar;

public class TKThangFragment extends Fragment {
    AppCompatSpinner spn_Nam,spn_Thang;
    TextView tv_tongThang,tv_chiThang,tv_duThang;
    View view;
    final String[] thang = {"1","2","3","4","5","6","7","8","9","10","11","12"};
    final String[] nam = {"2018","2019","2020","2021","2022","2023"};
    ChiTieuDAO chiTieuDAO;
    ThuNhapDAO thuNhapDAO;
    final DecimalFormat decimalFormat = new DecimalFormat("###,###.###");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_thong_ke_thang, container, false);
        initView();

        chiTieuDAO = new ChiTieuDAO(getContext());
        thuNhapDAO = new ThuNhapDAO(getContext());
        setupSpinner();

        spn_Thang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getData(spn_Nam.getSelectedItem().toString(),spn_Thang.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spn_Nam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getData(spn_Nam.getSelectedItem().toString(),spn_Thang.getSelectedItem().toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }
    public void initView(){
        spn_Nam = view.findViewById(R.id.spn_Nam);
        spn_Thang = view.findViewById(R.id.spn_Thang);
        tv_chiThang = view.findViewById(R.id.tv_chiThang);
        tv_tongThang = view.findViewById(R.id.tv_tongThang);
        tv_duThang = view.findViewById(R.id.tv_duThang);
    }
    public void setupSpinner(){
        ArrayAdapter thangAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item,thang);
        ArrayAdapter namAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item,nam);

        spn_Thang.setAdapter(thangAdapter);
        spn_Nam.setAdapter(namAdapter);

        Calendar calendar = Calendar.getInstance();
        int m = calendar.get(Calendar.MONTH);
        int y = calendar.get(Calendar.YEAR);

        spn_Thang.setSelection(m);
        spn_Nam.setSelection(y-2018);
    }
    public void getData(String y,String m){
        Double tongThu = thuNhapDAO.getTongThuThang(y,m);
        Double tongChi = chiTieuDAO.getTongChiThang(y,m);

        tv_tongThang.setText(decimalFormat.format(tongThu) + " VND");
        tv_chiThang.setText(decimalFormat.format(tongChi) + " VND");
        tv_duThang.setText(decimalFormat.format(tongThu - tongChi) + " VND");
    }
}