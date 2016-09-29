package com.example.outnotin.testdatabase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    EditText insertName;
    Button insertButton;
    Button deleteButton;

    private List<Stretch> stretchList;

    private static final String TAG = "MainActivity";
    final TestDB database = new TestDB(this);

    private String readJson(){
        String json;
        
        //read json
        try{
            Log.d(TAG, "readJson:");
            InputStream inputStream = this.getAssets().open("stretch.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
            Log.d(TAG, "readJson: >>>>>>>>>" + json);
            return json;
        }catch (Exception e){
            Log.e(TAG, "readJson: ERROR ", e );
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stretchList = new ArrayList<>();

        textView = (TextView) findViewById(R.id.text_view);
        String json = readJson();

        insertName = (EditText) findViewById(R.id.edittext_name_insert);

        insertButton = (Button) findViewById(R.id.button_insert);
        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    database.insertData(insertName.getText().toString() , "", "");
                    Toast.makeText(MainActivity.this, "Insert " + insertName.getText() + " success. ", Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    Toast.makeText(MainActivity.this, "Failed to insert data.", Toast.LENGTH_LONG).show();
                }
            }
        });

        deleteButton = (Button) findViewById(R.id.button_delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    database.deleteData();
                    Toast.makeText(MainActivity.this, "Delete success.", Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    Toast.makeText(MainActivity.this, "Failed to delete.", Toast.LENGTH_LONG).show();
                }
            }
        });

        TestDB testDB = new TestDB(this);
        testDB.getWritableDatabase();
    }

}