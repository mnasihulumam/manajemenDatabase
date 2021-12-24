package com.example.modul_8_m_nasihul_umam;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    private ListView obj;
    DBHelper mydb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mydb = new DBHelper(this);
        final ArrayList array_list = mydb.getAllContacts();
        ArrayAdapter arrayAdapter=new
                ArrayAdapter(this,android.R.layout.simple_list_item_1, array_list);
        obj = (ListView)findViewById(R.id.listView1);
        obj.setAdapter(arrayAdapter);
        obj.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
// TODO Auto-generated method stub
                String id_To_Search = (String) array_list.get(arg2);
                Bundle dataBundle = new Bundle();
                dataBundle.putString("nim", id_To_Search);
                Intent intent = new Intent(getApplicationContext(),DisplayContact.class);
                intent.putExtras(dataBundle);
                startActivity(intent);
            }
        });
        final TextView jml = (TextView) findViewById(R.id.jml_data);
        jml.setText("Jumlah Data : " + mydb.numberOfRows());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);
        switch(item.getItemId()) {
            case R.id.tambah_kontak: Bundle dataBundle = new Bundle();
                dataBundle.putString("nim", "");
                Intent intent = new Intent(getApplicationContext(),DisplayContact.class);
                intent.putExtras(dataBundle);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public boolean onKeyDown(int keycode, KeyEvent event) {if (keycode == KeyEvent.KEYCODE_BACK) {
        moveTaskToBack(true);
    }
        return super.onKeyDown(keycode, event);
    }
}