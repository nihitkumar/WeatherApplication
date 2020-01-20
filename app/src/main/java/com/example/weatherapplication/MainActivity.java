package com.example.weatherapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Button button;
    EditText city;
    TextView result,temp10;

    //http://api.openweathermap.org/data/2.5/weather?q=Paris&appid=5f56d525d1619d0a2cd2eac4ce55588e

    String baseURL = "http://api.openweathermap.org/data/2.5/weather?q=";
    String API = "&appid=5f56d525d1619d0a2cd2eac4ce55588e";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        city = (EditText) findViewById(R.id.getCity);
        result = (TextView) findViewById(R.id.result);
        temp10 = (TextView) findViewById(R.id.temp);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String myURL = baseURL + city.getText().toString() + API;
                //Log.i("URL", "URL: " + myURL);

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, myURL, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                Log.i("JSON", "JSON: " + jsonObject);

                                try {
                                    String info = jsonObject.getString("weather");
                                    Log.i("INFO", "INFO: "+ info);

                                    JSONArray ar = new JSONArray(info);

                                    for (int i = 0; i < ar.length(); i++){
                                        JSONObject parObj = ar.getJSONObject(i);

                                        String myWeather = parObj.getString("main");
                                        result.setText(myWeather);
                                        Log.i("ID", "ID: " + parObj.getString("id"));
                                        Log.i("MAIN", "MAIN: " + parObj.getString("main"));
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                                try {
                                    String main = jsonObject.getString("main");
                                    Log.i("main", "main: " + main);
                                    JSONObject co = new JSONObject(main);

                                    String temp = co.getString("temp");
                                    Log.i("main", "main: " + temp);
                                    float z = (float) 273.15;
                                    float f = Float.parseFloat(temp);
                                    int final_temp =  Math.round((f-z)*100)/100;

                                    String final_temp1 =""+final_temp+" "+"C";

                                   temp10.setText(final_temp1);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        },

                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Log.i("Error", "Something went wrong" + volleyError);

                            }
                        }


                );
                MySingleton.getInstance(MainActivity.this).addToRequestQue(jsonObjectRequest);



            }
        });






    }
}