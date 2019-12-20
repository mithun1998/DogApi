package com.example.dogapi;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class DogAdapter extends RecyclerView.Adapter<DogAdapter.ViewHolder>{

    private final OnDogClick click;
    private List<Dog> dogList;
    private Context context;


    public DogAdapter(List<Dog> dataBase, Context context, OnDogClick click)
    {
        dogList =dataBase;
        this.context=context;
        this.click=click;
    }



    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView nameDog;
        public View layout;


        public ViewHolder(View vu)
        {
            super(vu);
            layout = vu;
            nameDog = (TextView) vu.findViewById(R.id.dog_name);
        }
    }

    @Override
    public DogAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View vu=inflater.inflate(R.layout.dog_recycler,parent,false);
        ViewHolder vuH = new ViewHolder(vu);
        return vuH;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position)
    {
        final Dog dogCurrent = dogList.get(position);
        final String name=dogCurrent.getName();

        holder.nameDog.setText(name);

        holder.itemView.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.onItemClick(dogCurrent);
            }
        }));
    }

    @Override
    public int getItemCount()
    {
        return dogList.size();
    }
}
