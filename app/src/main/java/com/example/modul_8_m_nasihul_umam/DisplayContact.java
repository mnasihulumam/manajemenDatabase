package com.example.modul_8_m_nasihul_umam;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Currency;

public class DisplayContact extends AppCompatActivity {
    private DBHelper mydb;
    TextView nim, nama, telp, alamat, pos;
    String id_To_Update = "";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contact);
        nim = (TextView) findViewById(R.id.nim);
        nama = (TextView) findViewById(R.id.nama);
        telp = (TextView) findViewById(R.id.telp);
        alamat = (TextView) findViewById(R.id.alamat);
        pos = (TextView) findViewById(R.id.pos);

        mydb = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            String Value = extras.getString("nim");
            id_To_Update = Value;
            if(!Value.equals("")) {
                Cursor rs = mydb.getData(Value);
                rs.moveToFirst();
                String val_nim = rs.getString(rs.getColumnIndex("nim"));
                String val_nama = rs.getString(rs.getColumnIndex("nama"));
                String val_telp = rs.getString(rs.getColumnIndex("telp"));
                String val_alamat = rs.getString(rs.getColumnIndex("alamat"));
                String val_pos = rs.getString(rs.getColumnIndex("pos"));
                if (!rs.isClosed()) {
                    rs.close();
                }

                Button b = (Button) findViewById((R.id.buttonsimpan));
                b.setVisibility(View.INVISIBLE);

                nim.setText(val_nim);
                nim.setFocusable(false);
                nim.setClickable(false);

                nama.setText(val_nama);
                nama.setFocusable(false);
                nama.setClickable(false);

                telp.setText(val_telp);
                telp.setFocusable(false);
                telp.setClickable(false);

                alamat.setText(val_alamat);
                alamat.setFocusable(false);
                alamat.setClickable(false);

                pos.setText(val_pos);
                pos.setFocusable(false);
                pos.setClickable(false);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.display_contact, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.edit_kontak:
            Button b = (Button) findViewById(R.id.buttonsimpan);
            b.setVisibility(View.VISIBLE);

            nim.setEnabled(true);
            nim.setFocusableInTouchMode(true);
            nim.setClickable(true);

            nama.setEnabled(true);
            nama.setFocusableInTouchMode(true);
            nama.setClickable(true);

            telp.setEnabled(true);
            telp.setFocusableInTouchMode(true);
            telp.setClickable(true);

            alamat.setEnabled(true);
            alamat.setFocusableInTouchMode(true);
            alamat.setClickable(true);

            pos.setEnabled(true);
            pos.setFocusableInTouchMode(true);
            pos.setClickable(true);

            return true;
            case R.id.hapus_kontak:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Hapus Data Kontak")
                        .setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mydb.deleteContact(id_To_Update);
                                Toast.makeText(getApplicationContext(), "Hapus Data Berhasil",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),
                                        MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                AlertDialog d = builder.create();
                d.setTitle("Are you sure");
                d.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void simpan (View v){
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            String Value = extras.getString("nim");
            if(!Value.equals("")){
                if (mydb.updateContact(id_To_Update, nama.getText().toString(),
                        alamat.getText().toString() ,telp.getText().toString(),
                        pos.getText().toString()
                        )){
                    Toast.makeText(getApplicationContext(), "Input Data Berhasil",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Input Data Gagal",
                            Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        }
    }
}
