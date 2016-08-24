package com.yogyakartaandroidcommunity.yac;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yogyakartaandroidcommunity.yac.Adapter.DaftarHadirAdapter;
import com.yogyakartaandroidcommunity.yac.Helper.SQLHelper;
import com.yogyakartaandroidcommunity.yac.Helper.SimpleDividerItemDecoration;
import com.yogyakartaandroidcommunity.yac.Serialize.DaftarHadirSerialize;

import java.util.ArrayList;
import java.util.List;

public class DaftarHadirActivity extends AppCompatActivity {

    private List<DaftarHadirSerialize> daftarList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DaftarHadirAdapter daftarHadirAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_hadir);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewDaftarHadir);

        daftarHadirAdapter = new DaftarHadirAdapter(daftarList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getApplicationContext()));
        recyclerView.setAdapter(daftarHadirAdapter);

        getDataDaftarHadir();
    }

    public void getDataDaftarHadir() {
        SQLHelper dbDaftarHadir = new SQLHelper(this);
        List<DaftarHadirSerialize> hadirs = dbDaftarHadir.getAllDaftarHadir();

        DaftarHadirSerialize daftarHadirSerialize;
        for (DaftarHadirSerialize hadir : hadirs) {
            daftarHadirSerialize = new DaftarHadirSerialize(hadir.getMemberId(), hadir.getHadir());
            daftarList.add(daftarHadirSerialize);
        }

        daftarHadirAdapter.notifyDataSetChanged();
    }
}
