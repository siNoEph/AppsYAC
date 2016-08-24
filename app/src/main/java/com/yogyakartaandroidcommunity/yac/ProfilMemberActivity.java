package com.yogyakartaandroidcommunity.yac;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yogyakartaandroidcommunity.yac.Adapter.ProfilMemberAdapter;
import com.yogyakartaandroidcommunity.yac.Helper.SQLHelper;
import com.yogyakartaandroidcommunity.yac.Helper.SimpleDividerItemDecoration;
import com.yogyakartaandroidcommunity.yac.Serialize.AnggotaSerialize;

import java.util.ArrayList;
import java.util.List;

public class ProfilMemberActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProfilMemberAdapter profilMemberAdapter;
    public static ProfilMemberActivity objProfilMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_member);
        objProfilMember = this;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewMember);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getApplicationContext()));

        getDataMember();
    }

    public void getDataMember() {
        SQLHelper dbAnggota = new SQLHelper(this);
        List<AnggotaSerialize> anggotas = dbAnggota.getAllAnggota();
        List<AnggotaSerialize> anggotaList = new ArrayList<>();

        AnggotaSerialize anggotaSerialize;
        for (AnggotaSerialize anggota : anggotas) {
            anggotaSerialize = new AnggotaSerialize(anggota.getId(), anggota.getMemberId(), anggota.getNama(), anggota.getEmail(), anggota.getHp());
            anggotaList.add(anggotaSerialize);
        }

        profilMemberAdapter = new ProfilMemberAdapter(anggotaList);
        profilMemberAdapter.notifyDataSetChanged();

        recyclerView.setAdapter(profilMemberAdapter);
    }
}
