package com.yogyakartaandroidcommunity.yac;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setElevation(0);
    }

    public void HandleClick(View view) {
        switch (view.getId()) {
            case R.id.btnAbsen:
                Intent intentAbsen = new Intent(this, CaptureAbsenActivity.class);
                startActivity(intentAbsen);
                break;
            case R.id.btnNewProfil:
                Intent intentNewProfil = new Intent(this, NewProfilActivity.class);
                startActivity(intentNewProfil);
                break;
            case R.id.btnProfil:
                Intent intentProfil = new Intent(this, ProfilMemberActivity.class);
                startActivity(intentProfil);
                break;
            case R.id.btnHadir:
                Intent intentHadir = new Intent(this, DaftarHadirActivity.class);
                startActivity(intentHadir);
                break;
        }
    }
}
