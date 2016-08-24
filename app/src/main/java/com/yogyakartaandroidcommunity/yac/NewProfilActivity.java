package com.yogyakartaandroidcommunity.yac;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yogyakartaandroidcommunity.yac.Helper.OtherMethod;
import com.yogyakartaandroidcommunity.yac.Helper.SQLHelper;
import com.yogyakartaandroidcommunity.yac.Serialize.AnggotaSerialize;

public class NewProfilActivity extends AppCompatActivity {

    EditText memberId, namaAnggota, emailAnggota, hpAnggota;
    Button saveMember;
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_profil);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        memberId = (EditText) findViewById(R.id.editTextMemberId);
        namaAnggota = (EditText) findViewById(R.id.editTextNama);
        emailAnggota = (EditText) findViewById(R.id.editTextEmail);
        hpAnggota = (EditText) findViewById(R.id.editTextHp);
        saveMember = (Button) findViewById(R.id.buttonSaveMember);

        extras = getIntent().getExtras();
        if (extras != null) {
            String codeIntent = extras.getString("codeIntent");
            if (codeIntent.equals("new")) {
                memberId.setText(extras.getString("QrCode"));
                namaAnggota.requestFocus();
            } else if (codeIntent.equals("edit")) {
                memberId.setText(extras.getString("memberId"));
                namaAnggota.setText(extras.getString("nama"));
                emailAnggota.setText(extras.getString("email"));
                hpAnggota.setText(extras.getString("hp"));

                android.support.v7.app.ActionBar actionBar = getSupportActionBar();
                actionBar.setTitle("Update Member YAC");
            }
        }

        saveMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (memberId.getText().toString().length() == 0) {
                    memberId.setError("Member ID harus terisi !");
                } else if (namaAnggota.getText().toString().length() == 0) {
                    namaAnggota.setError("Nama Anggota harus terisi !");
                } else if (!OtherMethod.isValidEmail(emailAnggota.getText().toString().trim())) {
                    emailAnggota.setError("Email Anggota tidak valid !");
                } else if (hpAnggota.getText().toString().length() == 0) {
                    hpAnggota.setError("Harap mengisi Nomer HP !");
                } else {
                    saveDataMember(
                            memberId.getText().toString(),
                            namaAnggota.getText().toString(),
                            emailAnggota.getText().toString(),
                            hpAnggota.getText().toString()
                    );
                }
            }
        });
    }

    private void saveDataMember(String dataMemberId,
                                String dataNamaAnggota,
                                String dataEmailAnggota,
                                String dataHpAnggota) {

        SQLHelper dbAnggota = new SQLHelper(this);
        if (extras != null && extras.getString("codeIntent").equals("edit")) {
            int id = extras.getInt("id");
            dbAnggota.updateAnggota(new AnggotaSerialize(dataMemberId, dataNamaAnggota, dataEmailAnggota, dataHpAnggota), id);
            Toast.makeText(NewProfilActivity.this, "Update Data Member Saved", Toast.LENGTH_SHORT).show();
            ProfilMemberActivity.objProfilMember.getDataMember();
            finish();
        } else {
            dbAnggota.addAnggota(new AnggotaSerialize(dataMemberId, dataNamaAnggota, dataEmailAnggota, dataHpAnggota));
            Toast.makeText(NewProfilActivity.this, "New Members Saved", Toast.LENGTH_SHORT).show();

            // Clean Edit Text
            memberId.getText().clear();
            namaAnggota.getText().clear();
            emailAnggota.getText().clear();
            hpAnggota.getText().clear();
            memberId.requestFocus();
        }
    }
}
