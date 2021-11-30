package com.wasnikaditya.marriagematches;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    List<RecyclerViewData> recyclerViewDataList;
    RecyclerView recyclerView;
    private RVAdapter rvAdapter;
    private static final String TAG="Aditya";
    DBController db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Marriage List");

        recyclerViewDataList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        if(isNetworkAvailable())
        {
            // int count = db.getRowCountCloud();
            // System.out.println("count:::"+count);

            //  deleteMarriageList();   //check condition first time
            MakeVolleyConnection();
        }
        else
        {
            Toast.makeText(this, "Check your Internet Connection..!", Toast.LENGTH_LONG).show();
        }

    }

    public void deleteMarriageList()
    {
        db.deleteMarriageList();
    }

    private void MakeVolleyConnection()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(this);


        recyclerViewDataList = new ArrayList<>();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "https://randomuser.me/api/?results=10", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response)
            {
                try
                {
                    System.out.println("response::::59 "+response.toString());

                    JSONArray dataArray = response.getJSONArray("results");

                    System.out.println("dataArray::::63 "+dataArray);

                    //JSONArray dataArray = response.getJSONArray("data");
                    for (int i = 0; i < dataArray.length(); i++)
                    {

                        JSONObject userData = dataArray.getJSONObject(i);
                        JSONObject nameObj = userData.getJSONObject("name");

                        String name = nameObj.getString("title") + ". " + nameObj.getString("first") + " " + nameObj.getString("last");
                        String email = userData.getString("email");
                        String gender = userData.getString("gender");
                        String image_url = userData.getJSONObject("picture").getString("medium");
                        String birthdate = userData.getJSONObject("dob").getString("date");
                        String age = userData.getJSONObject("dob").getString("age");


                        RecyclerViewData recyclerViewData = new RecyclerViewData();

                   /*     recyclerViewData.setId(userData.getInt("id"));
                        recyclerViewData.setFirstname(userData.getString("first_name"));
                        recyclerViewData.setLastname(userData.getString("last_name"));
                        recyclerViewData.setAvatar(userData.getString("avatar"));*/

                        recyclerViewData.setName(name);
                        recyclerViewData.setEmail(email);
                        recyclerViewData.setGender(gender);
                        recyclerViewData.setImage(image_url);
                        recyclerViewData.setBirthdate(birthdate);
                        recyclerViewData.setAge(age);

                        recyclerViewDataList.add(recyclerViewData);

                       /* FileInputStream fis = new FileInputStream(image_url);
                        byte[] image= new byte[fis.available()];
                        fis.read(image);*/
                        DBController db = new DBController(MainActivity.this);
                        //  db.insertMarriageDetail(name, email, gender, Integer.parseInt(image_url), birthdate, age );
                        db.insertMarriageDetail(name, email, gender, birthdate, age );
                        System.out.println(":::116 ");

                    }
                    rvAdapter = new RVAdapter(recyclerViewDataList, MainActivity.this);
                    recyclerView.setAdapter(rvAdapter);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, ""+error.networkResponse,Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService( CONNECTIVITY_SERVICE );
        @SuppressLint("MissingPermission") NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
