package com.example.ve00ym014.i2ei;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class i2e1 extends AppCompatActivity {
RecyclerView rv;
ArrayList<user> users=new ArrayList<>();
i2e1_adapter adapter;
    RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i2e1);
        rv = findViewById(R.id.users);
        queue = Volley.newRequestQueue(this);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        adapter=new i2e1_adapter(users,getApplicationContext(),width);
        String url = "http://sd2-hiring.herokuapp.com/api/users?offset=0&limit=15";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject data=response.getJSONObject("data");
                    JSONArray u=data.getJSONArray("users");

                    for(int i=0;i< u.length();i++)
                    {
                        JSONObject us=u.getJSONObject(i);
                        ArrayList<String> listdata = new ArrayList<String>();
                        JSONArray jArray = us.getJSONArray("items");
                        if (jArray != null) {
                            for (int j=0;j<jArray.length();j++){
                                listdata.add(jArray.getString(j));
                            }
                        }
                        users.add(new user(us.getString("name"),us.getString("image"),listdata));
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("v",error.getMessage()+"");

            }
        });

        queue.add(jsonObjectRequest);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(llm);
        rv.setAdapter(adapter);
    }
}
