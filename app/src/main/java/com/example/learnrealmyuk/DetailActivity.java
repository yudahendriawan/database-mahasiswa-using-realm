package com.example.learnrealmyuk;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etNama, etNim;
    String nim, nama;
    Integer id;
    Button btn_ubah, btn_hapus, btn_kembali;
    RealmHelper realmHelper;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //setup
        Realm.init(DetailActivity.this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(configuration);
        realmHelper = new RealmHelper(realm);

        //inisialisasi
        etNim = findViewById(R.id.etNim);
        etNama = findViewById(R.id.etNama);
        btn_ubah = findViewById(R.id.btnUpdate);
        btn_hapus = findViewById(R.id.btnHapus);
        btn_kembali = findViewById(R.id.btnCancel);

        //get extras from intent
        if(getIntent().hasExtra("id")) {
            id = Integer.parseInt(getIntent().getStringExtra("id"));

            if(getIntent().hasExtra("nim")) {
                nim = getIntent().getStringExtra("nim");
                etNim.setText(nim);
            }

            if(getIntent().hasExtra("nama")) {
                nama = getIntent().getStringExtra("nama");
                etNama.setText(nama);
            }
        }

        btn_kembali.setOnClickListener(this);
        btn_hapus.setOnClickListener(this);
        btn_ubah.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == btn_ubah) {
            new AlertDialog.Builder(this)
                    .setTitle("Confirmation")
                    .setMessage("Do you really want to update this item?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            realmHelper.update(id, Integer.parseInt(etNim.getText().toString()), etNama.getText().toString());
                            Toast.makeText(DetailActivity.this, "Update Success", Toast.LENGTH_SHORT).show();
                            etNim.setText("");
                            etNama.setText("");
                            finish();
                        }})
                    .setNegativeButton("Tidak", null).show();

        }

        else if (v == btn_hapus) {
            new AlertDialog.Builder(this)
                    .setTitle("Confirmation")
                    .setMessage("Do you really want to delete this item?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            realmHelper.delete(id);
                            Toast.makeText(DetailActivity.this, "Delete Success", Toast.LENGTH_SHORT).show();
                            finish();
                        }})
                    .setNegativeButton("Tidak", null).show();


        }

        else if (v == btn_kembali) {
            startActivity(new Intent(DetailActivity.this, MahasiswaActivity.class));
            finish();
        }
    }
}