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


public class TKNamFragment extends Fragment {
    ThuNhapDAO thuNhapDAO;
    ChiTieuDAO chiTieuDAO;
    AppCompatSpinner spn_nam;
    TextView tv_tongNam, tv_chiNam, tv_duNam;
    final String[] nam = {"2018","2019","2020","2021","2022","2023"};
    final DecimalFormat decimalFormat = new DecimalFormat("###,###.###");
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_thong_ke_nam, container, false);
        initView();

        thuNhapDAO = new ThuNhapDAO(getContext());
        chiTieuDAO = new ChiTieuDAO(getContext());

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item,nam);
        spn_nam.setAdapter(arrayAdapter);

        Calendar calendar = Calendar.getInstance();
        int y = calendar.get(Calendar.YEAR);
        spn_nam.setSelection(y-2018);

        spn_nam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getData(nam[spn_nam.getSelectedItemPosition()]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }
    public void initView(){
        spn_nam = view.findViewById(R.id.spn_nam);
        tv_tongNam = view.findViewById(R.id.tv_tongNam);
        tv_chiNam = view.findViewById(R.id.tv_chiNam);
        tv_duNam = view.findViewById(R.id.tv_duNam);
    }
    public void getData(String nam){
        Double tongThu = thuNhapDAO.getTongThuNam(nam);
        Double tongChi = chiTieuDAO.getTongChiNam(nam);
        tv_tongNam.setText(decimalFormat.format(tongThu) + " VND");
        tv_chiNam.setText(decimalFormat.format(tongChi) + " VND");
        tv_duNam.setText(decimalFormat.format(tongThu - tongChi) + " VND");
    }


}