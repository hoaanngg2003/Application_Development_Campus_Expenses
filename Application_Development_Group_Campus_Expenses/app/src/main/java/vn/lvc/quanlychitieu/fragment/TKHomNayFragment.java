package vn.lvc.quanlychitieu.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import vn.lvc.quanlychitieu.R;
import vn.lvc.quanlychitieu.dao.ChiTieuDAO;
import vn.lvc.quanlychitieu.dao.ThuNhapDAO;


public class TKHomNayFragment extends Fragment {
    TextView tv_tonghn,tv_chihn,tv_duhn;
    ThuNhapDAO thuNhapDAO;
    ChiTieuDAO chiTieuDAO;
    final DecimalFormat decimalFormat = new DecimalFormat("###,###.###");
    final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_t_k_hom_nay, container, false);
        thuNhapDAO = new ThuNhapDAO(getContext());
        chiTieuDAO = new ChiTieuDAO(getContext());
        initView();

        Calendar calendar = Calendar.getInstance();
        String currentDay = sdf.format(calendar.getTime());
        tv_tonghn.setText(decimalFormat.format(thuNhapDAO.getTongThuHN(currentDay)) + " VND");
        tv_chihn.setText(decimalFormat.format(chiTieuDAO.getTongChiHN(currentDay)) + " VND");
        tv_duhn.setText(decimalFormat.format(thuNhapDAO.getTongThuHN(currentDay) - chiTieuDAO.getTongChiHN(currentDay)) + " VND");


        return view;
    }
    public void initView(){
        tv_tonghn = view.findViewById(R.id.tv_tonghn);
        tv_chihn = view.findViewById(R.id.tv_chihn);
        tv_duhn = view.findViewById(R.id.tv_duhn);
    }
}