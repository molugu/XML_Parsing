package com.example.incresol_078.xml_parsing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private String url ="";
    EditText editText_location;
    Button button_parse;
    private HandleXML obj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText_location=(EditText)findViewById(R.id.editText_location);
        button_parse=(Button)findViewById(R.id.button_parse);

        button_parse.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

              String location=  editText_location.getText().toString();
                url="http://maps.google.com/maps/api/geocode/xml?address="+location;

                obj=new HandleXML(url);
                obj.fetchXML();
                while(obj.parsingComplete);
               String status= obj.getStatus();
                Log.e("in main",status);
                Toast.makeText(getApplicationContext(),status,Toast.LENGTH_SHORT).show();

            }
        });


    }
}
