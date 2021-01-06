package com.sassy.contentproviderexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText editName,editProfile;
    Button btnSave,btnLoad;
    ContentValues contentValues = new ContentValues();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editName = findViewById(R.id.edit1);
        editProfile = findViewById(R.id.edit2);
        //btnSave = findViewById(R.id.btnSave);
        //btnLoad = findViewById(R.id.btnLoad);


    }

    public void doSaveContent(View view) {
        contentValues.put("emp_name",editName.getText().toString());
        contentValues.put("profile",editProfile.getText().toString());
        Uri uri = getContentResolver().insert(CompanyProvider.CONTENT_URI_1,contentValues);
        Toast.makeText(MainActivity.this, "Data - " + uri.toString(),
                Toast.LENGTH_SHORT).show();
    }

    public void doLoadContent(View view) {
        Cursor cr = getContentResolver().query(CompanyProvider.CONTENT_URI_1,null,
                null,null,"_id");
        StringBuilder stringBuilder = new StringBuilder();
        while (cr.moveToNext()){
            int id = cr.getInt(0);
            String s1 = cr.getString(1);
            String s2 = cr.getString(2);
            stringBuilder.append(id + "     "+s1 + "        "+s2 +"/n");
        }
        Toast.makeText(this, ""+stringBuilder.toString(), Toast.LENGTH_LONG).show();
    }
}
