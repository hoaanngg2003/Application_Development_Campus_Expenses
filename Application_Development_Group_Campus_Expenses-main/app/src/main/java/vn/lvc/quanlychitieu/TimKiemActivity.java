package vn.lvc.quanlychitieu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import vn.lvc.quanlychitieu.adapter.ViewPagerAdapter;
import vn.lvc.quanlychitieu.fragment.TimChiTieuFragment;
import vn.lvc.quanlychitieu.fragment.TimThuNhapFragment;
import com.google.android.material.tabs.TabLayout;

public class TimKiemActivity extends AppCompatActivity {
    ViewPager vp_timkiem;
    TabLayout tl_timkiem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_kiem);
        setupToolbar();
        initView();

        addTabs(vp_timkiem);
        tl_timkiem.setupWithViewPager(vp_timkiem);
    }
    public void setupToolbar(){
        setTitle("");
        Toolbar toolbar = findViewById(R.id.toolbar_timkiem);
        setSupportActionBar(toolbar);

        TextView tvTitle =  findViewById(R.id.tvTitle);
        tvTitle.setText("Search");

        ImageView ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public void initView(){
        vp_timkiem = findViewById(R.id.vp_timKiem);
        tl_timkiem = findViewById(R.id.tl_timKiem);
    }
    private void addTabs(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new TimThuNhapFragment(),"Income");
        adapter.addFrag(new TimChiTieuFragment(),"Expense");
        viewPager.setAdapter(adapter);
    }
}