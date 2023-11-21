package com.example.listaapifilmes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ListView listViewItensApi;

    EditText editTextSearch;

    Button btnSearch;

    SearchModel searchModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewItensApi = findViewById(R.id.listViewItensApi);

        editTextSearch = findViewById(R.id.editTextSearch);

        btnSearch = findViewById(R.id.btnSearch);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.omdbapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OmdbApiService omdbApiService = retrofit.create(OmdbApiService.class);

                //isso sim precisa entender:
                String title = editTextSearch.getText().toString();
                Call<SearchModel> call = omdbApiService.searchByTitle(title);

                call.enqueue(new Callback<SearchModel>() {
                    @Override
                    public void onResponse(Call<SearchModel> call, Response<SearchModel> response) {
                        searchModel = response.body();


                        CustomListAdapter adapter = new CustomListAdapter(MainActivity.this, searchModel.Search);

                        listViewItensApi.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<SearchModel> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Se fudeu!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                });
            }
        });


    }
}