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
import vn.lvc.quanlychitieu.dao.LoaiChiDAO;
import vn.lvc.quanlychitieu.model.LoaiChi;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class ThemLoaiChiActivity extends AppCompatActivity {
    TextInputLayout textInputLoaiChi;
    ImageView ivIconChi;
    GridView gvIcon;
    LoaiChiDAO loaiChiDAO;
    IconLoaiAdapter adapter;
    List<Integer> iconLoaiLists;
    Integer icon = null;
    Bundle bundle;
    String loai = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_loai_chi);
        setupToolbar();
        loaiChiDAO = new LoaiChiDAO(this);

        initView();
        setupIcon();
        getBundle();

    }
    public void setupToolbar(){
        setTitle("");
        Toolbar toolbar = findViewById(R.id.toolbarThemLoaiChi);
        setSupportActionBar(toolbar);
        TextView tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText("Expense type");
        ImageView ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
    public void initView() {
        textInputLoaiChi = findViewById(R.id.textInputLoaiChi);
        ivIconChi = findViewById(R.id.ivIconChi);
        gvIcon = findViewById(R.id.gvIconChi);
    }
    public int validate() {
        String loai = textInputLoaiChi.getEditText().getText().toString();
        if (loai.isEmpty()) {
            textInputLoaiChi.setError("Empty field found");
            return -1;
        } else if (loai.length() > 15) {
            textInputLoaiChi.setError("Maximum 15 characters");
            return -1;
        } else {
            textInputLoaiChi.setError(null);
        }
        return 1;
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
        iconLoaiLists.add(R.drawable.ic_duoc_tang);
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
                ivIconChi.setImageResource(icon);
            }
        });
    }
    public void getBundle(){
        Intent intent = getIntent();
        bundle = intent.getExtras();
        if (bundle != null){
            loai = bundle.getString("loaichi");
            icon = bundle.getInt("icon");
            ivIconChi.setImageResource(icon);
            textInputLoaiChi.getEditText().setText(loai);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_confirm,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_confirm){
            if (bundle==null){
                if (validate()==1){
                    if (icon == null){
                        icon = iconLoaiLists.get(0);
                    }
                    if (loaiChiDAO.insertLoaiChi(new LoaiChi(textInputLoaiChi.getEditText().getText().toString().trim(),icon)) < 0){
                        Toast.makeText(this,"Expense type existed",Toast.LENGTH_SHORT).show();
                    }else{
                        finish();
                    }
                }
            }else{
                if (validate() == 1){
                    LoaiChi loaiChi = new LoaiChi(textInputLoaiChi.getEditText().getText().toString().trim(),icon);
                    if (loaiChiDAO.updateLoaiChi(loaiChi,loai) < 0){
                        Toast.makeText(this,"Expense type existed",Toast.LENGTH_SHORT).show();
                    }else{
                        finish();
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }
}