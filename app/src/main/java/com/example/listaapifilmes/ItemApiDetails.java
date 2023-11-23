package com.example.listaapifilmes;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.net.URI;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ItemApiDetails extends AppCompatActivity {

    ImageView imageViewPoster;

    RatingBar ratingBarImdb;

    ListView listViewDetails;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_api_details);

        associa();

        String imdbId = getIntent().getStringExtra("imdbId");

        Retrofit retrofit = Util.createRetrofitImdb();

        OmdbApiService omdbApiService = retrofit.create(OmdbApiService.class);

        Call<ItemDetailsModel> call = omdbApiService.searchById(imdbId);

        call.enqueue(new Callback<ItemDetailsModel>() {
            @Override
            public void onResponse(Call<ItemDetailsModel> call, Response<ItemDetailsModel> response) {
                ItemDetailsModel itemApiDetails = response.body();
                float ratingItem = (float)itemApiDetails.imdbRating;
                ratingItem = ratingItem / 2;

                Picasso.get().load(itemApiDetails.Poster).into(imageViewPoster);
                ratingBarImdb.setRating(ratingItem);

                ArrayList<String> arrayList = new ArrayList<>();
                arrayList.add("Título: " + itemApiDetails.Title);
                arrayList.add("Ano de lançamento: " + itemApiDetails.Year);
                arrayList.add("Gênero(s): " + itemApiDetails.Genre);
                arrayList.add("Duração: " + itemApiDetails.Runtime);
                arrayList.add("Diretor(s): " + itemApiDetails.Director);

                ArrayAdapter adapter = new ArrayAdapter(ItemApiDetails.this, android.R.layout.simple_list_item_1, arrayList);

                listViewDetails.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ItemDetailsModel> call, Throwable t) {
                Intent intent = new Intent(ItemApiDetails.this, MainActivity.class);
                startActivity(intent); // Retorna para o começo
                Toast.makeText(ItemApiDetails.this, "Ocorreu um erro", Toast.LENGTH_SHORT).show();
                finishAffinity(); // Finaliza todas as intents que estão na fila
            }
        });
    }

    private void associa() {
        imageViewPoster = findViewById(R.id.imageViewPoster);
        ratingBarImdb = findViewById(R.id.ratingBarImdb);
        listViewDetails = findViewById(R.id.listViewDetails);
    }
}