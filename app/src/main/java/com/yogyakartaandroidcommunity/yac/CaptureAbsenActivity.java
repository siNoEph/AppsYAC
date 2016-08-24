package com.yogyakartaandroidcommunity.yac;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.yogyakartaandroidcommunity.yac.Helper.OtherMethod;
import com.yogyakartaandroidcommunity.yac.Helper.SQLHelper;
import com.yogyakartaandroidcommunity.yac.Serialize.AnggotaSerialize;
import com.yogyakartaandroidcommunity.yac.Serialize.DaftarHadirSerialize;

import java.util.List;

public class CaptureAbsenActivity extends AppCompatActivity {

    private DecoratedBarcodeView barcodeView;
    String memberId, namaAnggota, sudahAbsen;

    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(final BarcodeResult result) {
            if (result.getText() != null) {
                barcodeView.pause();

                try {
                    SQLHelper dbAnggota = new SQLHelper(getApplicationContext());
                    AnggotaSerialize anggotaSerialize = dbAnggota.getAnggota(result.getText());

                    memberId = anggotaSerialize.getMemberId();
                    namaAnggota = anggotaSerialize.getNama();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                AlertDialog.Builder alertDataAnggota = new AlertDialog.Builder(CaptureAbsenActivity.this);
                alertDataAnggota.setTitle("Data Anggota");

                if (memberId != null) {
                    try {
                        SQLHelper dbHadir = new SQLHelper(getApplicationContext());
                        DaftarHadirSerialize hadirSerialize = dbHadir.getSudahAbsen(memberId, OtherMethod.dateNow("yyyy-MM-dd"));

                        sudahAbsen = hadirSerialize.getHadir();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    alertDataAnggota.setMessage("Member ID : " + memberId + "\n\nAnggota      : " + namaAnggota);
                    if (sudahAbsen != null) {
                        alertDataAnggota.setNegativeButton(R.string.text_dialog_sudah_absen, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                barcodeView.resume();
                            }
                        });
                    } else {
                        alertDataAnggota
                                .setPositiveButton(R.string.text_dialog_yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        saveDataAbsen(memberId);
                                        barcodeView.resume();
                                    }
                                })
                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        barcodeView.resume();
                                    }
                                });
                    }
                } else {
                    alertDataAnggota
                            .setMessage("Member belum terdaftar")
                            .setPositiveButton(R.string.text_btn_new_member, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(getApplicationContext(), NewProfilActivity.class);
                                    intent.putExtra("codeIntent", "new");
                                    intent.putExtra("QrCode", result.getText());
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    barcodeView.resume();
                                }
                            });
                }

                alertDataAnggota.setIcon(R.mipmap.ic_launcher).setCancelable(false).show();
            }
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
        }
    };

    private void saveDataAbsen(String dataMemberId) {
        SQLHelper dbDaftarHadir = new SQLHelper(this);
        dbDaftarHadir.addDaftarHadir(new DaftarHadirSerialize(dataMemberId));

        Toast.makeText(CaptureAbsenActivity.this, "Terimakasih sudah hadir !", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_portrait);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setSubtitle("Tgl. " + OtherMethod.dateNow("dd MMMM yyyy"));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        ((TextView) findViewById(R.id.textViewDesc)).setText("Scan a QR Code on Member Card");

        barcodeView = (DecoratedBarcodeView) findViewById(R.id.barcode_scanner);
        barcodeView.setStatusText("");
        barcodeView.decodeContinuous(callback);
    }

    @Override
    protected void onResume() {
        super.onResume();
        barcodeView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        barcodeView.pause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }
}
