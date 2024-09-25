package vn.lvc.quanlychitieu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import vn.lvc.quanlychitieu.adapter.IconLoaiAdapter;
import vn.lvc.quanlychitieu.dao.LoaiThuDAO;
import vn.lvc.quanlychitieu.dao.ThuNhapDAO;
import vn.lvc.quanlychitieu.model.LoaiThu;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class ThemLoaiThuActivity extends AppCompatActivity {
    TextInputLayout textInputLoaiThu;
    ImageView ivIconThu, ivBack;
    GridView gvIcon;
    LoaiThuDAO loaiThuDAO;
    IconLoaiAdapter adapter;
    List<Integer> iconLoaiLists;
    Integer icon = null;
    Bundle bundle;
    String loai = "";
    ThuNhapDAO thuNhapDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_loai_thu);
        thuNhapDAO = new ThuNhapDAO(this);
        loaiThuDAO = new LoaiThuDAO(this);

        initView();
        setupToolbar();
        setupIcon();
        Intent intent = getIntent();
        bundle = intent.getExtras();
        if (bundle != null){
            loai = bundle.getString("loaithu");
            icon = bundle.getInt("icon");
            textInputLoaiThu.getEditText().setText(loai);
            ivIconThu.setImageResource(icon);
        }

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void initView() {
        setTitle("");
        textInputLoaiThu = findViewById(R.id.textInputLoaiThu);
        ivIconThu = findViewById(R.id.ivIconThu);
        ivBack = findViewById(R.id.ivBack);
        gvIcon = findViewById(R.id.gvIconThu);
    }

    public void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbarThemLoaiThu);
        TextView tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText("Income type");
        setSupportActionBar(toolbar);
    }

    public int validate() {
        String loai = textInputLoaiThu.getEditText().getText().toString();
        if (loai.isEmpty()) {
            textInputLoaiThu.setError("Empty field found");
            return -1;
        } else if (loai.length() > 15) {
            textInputLoaiThu.setError("Maximum 15 characters");
            return -1;
        } else {
            textInputLoaiThu.setError(null);
        }
        return 1;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_confirm, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_confirm) {
            if (bundle == null) {
                if (validate() == 1) {
                    if (icon == null) {
                        icon = iconLoaiLists.get(0);
                    }
                    if (loaiThuDAO.insertLoaiThu(new LoaiThu(textInputLoaiThu.getEditText().getText().toString().trim(),icon)) < 0){
                        Toast.makeText(this,"Income type existed",Toast.LENGTH_SHORT).show();
                    }else{
                        finish();
                    }
                }
            }else {
                if (validate() == 1){
                    LoaiThu loaiThu = new LoaiThu(textInputLoaiThu.getEditText().getText().toString().trim(),
                            icon);
                    if (loaiThuDAO.updateLoaiThu(loaiThu,loai) < 0){
                        Toast.makeText(getApplicationContext(),"Income type existed",Toast.LENGTH_SHORT).show();
                    }else{
                        finish();
                    }
                }
            }

        }
        return super.onOptionsItemSelected(item);
    }

    public void setupIcon() {
        iconLoaiLists = new ArrayList<>();
        iconLoaiLists.add(R.drawable.ic_luong);
        iconLoaiLists.add(R.drawable.ic_tien_dien);
        iconLoaiLists.add(R.drawable.ic_an_uong);
        iconLoaiLists.add(R.drawable.ic_ban_do);
        iconLoaiLists.add(R.drawable.ic_bao_duong);
        iconLoaiLists.add(R.drawable.ic_bao_hiem);
        iconLoaiLists.add(R.drawable.ic_cham_soc_ca_nhan);
        iconLoaiLists.add(R.drawable.ic_chi_phi);
        iconLoaiLists.add(R.drawable.ic_coffee);
        iconLoaiLists.add(R.drawable.ic_con_cai1);
        iconLoaiLists.add(R.drawable.ic_con_cai2);
        iconLoaiLists.add(R.drawable.ic_cuoi_hoi);
        iconLoaiLists.add(R.drawable.ic_duoc_tang);
        iconLoaiLists.add(R.drawable.ic_dau_tu);
        iconLoaiLists.add(R.drawable.ic_di_chuyen);
        iconLoaiLists.add(R.drawable.ic_dich_vu_gia_dinh);
        iconLoaiLists.add(R.drawable.ic_khoang_chi_khac);
        iconLoaiLists.add(R.drawable.ic_kinh_doanh);
        iconLoaiLists.add(R.drawable.ic_lai);
        iconLoaiLists.add(R.drawable.ic_mua_sam);
        iconLoaiLists.add(R.drawable.ic_nha_hang);
        iconLoaiLists.add(R.drawable.ic_phim_anh);
        iconLoaiLists.add(R.drawable.ic_phu_kien);
        iconLoaiLists.add(R.drawable.ic_qua_tang_va_quyen_gop);
        iconLoaiLists.add(R.drawable.ic_quan_ao);
        iconLoaiLists.add(R.drawable.ic_rut_tien);
        iconLoaiLists.add(R.drawable.ic_sach);
        iconLoaiLists.add(R.drawable.ic_nha);
        iconLoaiLists.add(R.drawable.ic_suc_khoe);
        iconLoaiLists.add(R.drawable.ic_tang_le);
        iconLoaiLists.add(R.drawable.ic_taxi);
        iconLoaiLists.add(R.drawable.ic_the_thao);
        iconLoaiLists.add(R.drawable.ic_thiet_bi_dien_tu);
        iconLoaiLists.add(R.drawable.ic_thue_nha);
        iconLoaiLists.add(R.drawable.ic_thuoc);
        iconLoaiLists.add(R.drawable.ic_thuong);
        iconLoaiLists.add(R.drawable.ic_tro_choi);
        iconLoaiLists.add(R.drawable.ic_tu_thien);
        iconLoaiLists.add(R.drawable.ic_vat_nuoi);
        iconLoaiLists.add(R.drawable.ic_vi_tien);
        iconLoaiLists.add(R.drawable.ic_xang_dau);
        iconLoaiLists.add(R.drawable.ic_other);
        adapter = new IconLoaiAdapter(this, iconLoaiLists);
        gvIcon.setAdapter(adapter);
        gvIcon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                icon = iconLoaiLists.get(i);
                ivIconThu.setImageResource(icon);
            }
        });
    }

}