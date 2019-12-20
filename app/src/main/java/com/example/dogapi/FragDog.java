package com.example.dogapi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragDog extends Fragment{
    private View v;
    private RecyclerView recyclerView;
    private DogAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressBar progressBar;
    public List<Dog> dogList;
    private SharedPreferences sharedPreferences;

    public FragDog() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_dog, container, false);
        recyclerView = v.findViewById(R.id.my_recycler_view);
        progressBar = v.findViewById(R.id.loader);
        sharedPreferences=getActivity().getSharedPreferences("done", Context.MODE_PRIVATE);

        if(sharedPreferences.contains("done")){
            showLoader();
            String listB= sharedPreferences.getString("done",null);
            Type listT=new TypeToken<List<Dog>>(){}.getType();
            List<Dog> dogList1=new Gson().fromJson(listB,listT);
            showList(dogList1);
            hideLoader();
        }
        else{
            showLoader();
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.thedogapi.com/v1/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            DogApi dogApi = retrofit.create(DogApi.class);

            Call<List<Dog>> call = dogApi.getList();
            call.enqueue(new Callback<List<Dog>>() {
                @Override
                public void onResponse(Call<List<Dog>> call, Response<List<Dog>> response) {
                    dogList = response.body();
                    Gson gson = new Gson();
                    String listB = gson.toJson(dogList);

                    sharedPreferences
                            .edit()
                            .putString("done", listB)
                            .apply();

                    showList(dogList);
                    hideLoader();
                }
                @Override
                public void onFailure(Call<List<Dog>> call, Throwable t) {
                    Log.d("Erreur", "API erreur");
                }
            });
        }
        return v;
    }

    public void showLoader(){
        progressBar.setVisibility(View.VISIBLE);
    }
    public void hideLoader(){
        progressBar.setVisibility(View.GONE);
    }

    public void showList(List<Dog> dogList) {
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new DogAdapter(dogList, getActivity().getApplicationContext(), new OnDogClick() {
            @Override
            public void onItemClick(Dog dog) {
                Toast.makeText(getActivity().getApplicationContext(),dog.getName(),Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity().getApplicationContext(),Dog_Details.class);
                intent.putExtra("name",dog.getName());
                intent.putExtra("life",dog.getLife_span());
                intent.putExtra("temperament",dog.getTemperament());
                intent.putExtra("origin",dog.getOrigin());
                FragDog.this.startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
    }
}
