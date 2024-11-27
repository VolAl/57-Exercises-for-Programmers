package com.flickrphotosearchapp;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flickrphotosearchapp.models.SubjectData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView list;
    private ArrayList<SubjectData> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        list = findViewById(R.id.list);

        Button searchBtn = findViewById(R.id.search_button);
        searchBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.search_button) {
            getPhotos();
        }
    }

    private void getPhotos() {
        EditText searchStringValue = findViewById(R.id.search_string_value);
        String searchString = searchStringValue.getText().toString();

        Retrofit retrofitFlickr = new Retrofit.Builder()
                .baseUrl("https://api.flickr.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiServiceFlickr = retrofitFlickr.create(ApiService.class);
        Call<ResponseBody> callFlickr = apiServiceFlickr.getFlickrFeed(searchString);
        callFlickr.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    assert response.body() != null;
                    String jsonString = response.body().string();
                    jsonString = jsonString
                            .replace("jsonFlickrFeed(", "")
                            .replace("})", "}");

                    JSONObject jsonObj = new JSONObject(jsonString);
                    JSONArray photos = jsonObj.getJSONArray("items");
                    arrayList = new ArrayList<>();
                    for (int i = 0 ; i < photos.length(); i++) {
                        JSONObject photo = photos.getJSONObject(i);
                        String name = photo.getString("title");
                        String link = photo.getString("link").replace("\\/", "/");
                        String image = photo.getJSONObject("media").getString("m").replace("\\/", "/");

                        arrayList.add(new SubjectData(name, link, image));
                    }

                    CustomAdapter customAdapter = new CustomAdapter(MainActivity.this, arrayList);
                    list.setAdapter(customAdapter);
                } catch (JSONException | IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}