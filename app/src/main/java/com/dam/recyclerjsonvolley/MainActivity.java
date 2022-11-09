package com.dam.recyclerjsonvolley;

import static com.dam.recyclerjsonvolley.Nodes.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterItem.OnItemClickListener {

    //VARS GLOBALES
    private RecyclerView recyclerView;
    private EditText etSearch;
    private Button btnSearch;

    private ArrayList<ModelItem> itemArrayList;
    private String search;
    private AdapterItem adapterItem; // Ton adapter

    private RequestQueue requestQueue; // Pour Volley

    // Initialisation des composants
    private void initUI(){
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        etSearch = findViewById(R.id.etSearch);
        btnSearch = findViewById(R.id.btnSearch);

        itemArrayList = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(this);
    }

    public void newSearch(View view){
        itemArrayList.clear();
        search = etSearch.getText().toString().trim();
        parseJSON(search);
    }

    private void parseJSON(String search){
        String pixabayKey = "24175925-f2016e765d25a20f1cb0a6989";
        String urlJSONFile = "https://pixabay.com/api/"
                + "?key="
                + pixabayKey
                + "&q="
                + search
                + "&image_type=photo"
                + "&orientation=horizontal"
                + "&per_page=20"
                + "&pretty=true";

        Log.i("TAG", "parseJSON: " + urlJSONFile);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, urlJSONFile, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            JSONArray jsonArray = response.getJSONArray("hits");

                            for(int i =0; i<jsonArray.length(); i++){
                                JSONObject hit = jsonArray.getJSONObject(i);
                                String imageUrl = hit.getString("webformatURL");
                                String creator = hit.getString("user");
                                int likes = hit.getInt("likes");

                                itemArrayList.add(new ModelItem(imageUrl, creator, likes));
                            }
                            // ICI ;)
                            adapterItem = new AdapterItem(MainActivity.this, itemArrayList);

                            recyclerView.setAdapter(adapterItem);

                            adapterItem.setOnItemClickListener(MainActivity.this);

                        } catch (JSONException e){
                                e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        parseJSON(search);
    }

    @Override
    public void onItemClick(int position) {
        // Action pour afficher les choses
        Intent intent = new Intent(this, DetailActivity.class);
        ModelItem currentItem = itemArrayList.get(position);

        String imageUrl = currentItem.getImageUrl();
        String creator = currentItem.getCreator();
        int likes = currentItem.getLikes();

        intent.putExtra(EXTRA_URL, imageUrl);
        intent.putExtra(EXTRA_CREATOR, creator);
        intent.putExtra(EXTRA_LIKES, likes);

        startActivity(intent);

    }
}