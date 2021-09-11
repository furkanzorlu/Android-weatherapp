package com.example.weatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {
    private Context context;
    private ArrayList<WeatherModel> weatherModelArrayList;

    public WeatherAdapter(Context context, ArrayList<WeatherModel> weatherModelArrayList) {
        this.context = context;
        this.weatherModelArrayList = weatherModelArrayList;
    }

    @NonNull
    @Override
    public WeatherAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(context).inflate(R.layout.weather_item,parent,false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherAdapter.ViewHolder holder, int position) {
        WeatherModel model=weatherModelArrayList.get(position);
        holder.temp.setText(model.getTemperature()+"°C");
        Picasso.get().load("https://".concat(model.getIcon())).into(holder.condition);
        holder.windd.setText(model.getWindspeed()+"Km/h");
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm");
        SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("hh:mm aa");
        try {
            Date date=simpleDateFormat.parse(model.getTime());
            holder.time.setText(simpleDateFormat1.format(date));
        }catch (ParseException e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return weatherModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView windd,temp,time;
        private ImageView condition ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            windd=itemView.findViewById(R.id.windspeedd);
            temp=itemView.findViewById(R.id.idtvtemparature);
            time=itemView.findViewById(R.id.idtvtime);
            condition=itemView.findViewById(R.id.condiiton);
        }
    }
}
