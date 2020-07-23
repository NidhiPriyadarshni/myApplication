package com.example.tracksun;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


//9448c8f3bc5949c3e536314b332aec05
public class Home extends AppCompatActivity {

    EditText city;
    ImageView find;
    TextView temp;
    TextView date;
    TextView desc;
    String url;
    String cityname;
    ProgressBar pbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        city=findViewById(R.id.city);
        find=findViewById(R.id.search);
        temp=findViewById(R.id.temp);
        desc=findViewById(R.id.desc);
        date=findViewById(R.id.date);
        pbar=findViewById(R.id.pb);

        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });

    }

    void search(){
        cityname=city.getText().toString().trim();
        url="https://api.openweathermap.org/data/2.5/weather?q="+cityname+"&appid=9448c8f3bc5949c3e536314b332aec05&units=imperial";
        pbar.setVisibility(ProgressBar.VISIBLE);
        JsonObjectRequest j=new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pbar.setVisibility(ProgressBar.GONE);
                try {
                    JSONObject main=response.getJSONObject("main");
                    JSONArray array=response.getJSONArray("weather");
                    JSONObject job=array.getJSONObject(0);
                    String tempp=String.valueOf(main.getDouble("temp"));
                    String descc=job.getString("description");
                    String cityy=response.getString("name");

                    temp.setText(tempp+"\u00B0"+"F");
                    desc.setText(descc);
                    city.setText(cityy);
                    DateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
                    date.setText(dateFormat.format(new Date()));

                } catch (JSONException e) {

                    e.printStackTrace();
                    Toast.makeText(Home.this,e.getMessage().toString(),Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pbar.setVisibility(ProgressBar.GONE);
                Toast.makeText(Home.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue queue= Volley.newRequestQueue(Home.this);
        queue.add(j);

    }
}