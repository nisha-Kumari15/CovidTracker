package com.example.covidtracker.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.covidtracker.Info;
import com.example.covidtracker.Model.AllCountryModel.Country;
import com.example.covidtracker.R;


import java.util.ArrayList;
import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.Holder> implements Filterable {

    Context context;
    List<Country> countryList;
    List<Country> countryListFilter;


    public CountryAdapter(Context context, List<Country> countryList)  {
        this.context = context;
        this.countryList = countryList;
        this.countryListFilter = countryList ;
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.countries , parent , false) ;
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        String flag = countryListFilter.get(position).getFlag();
        String name = countryListFilter.get(position).getName();

        holder.countryName.setText(name);
        Glide.with(context).load(flag).into(holder.countryFlag);


    }

    @Override
    public int getItemCount() {
        return countryListFilter.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charSquense = constraint.toString();
                if (charSquense.isEmpty()){
                    countryListFilter = countryList ;
                }else {
                    List<Country> filterList = new ArrayList<>();
                    for (Country row: countryList){
                        if (row.getName().toLowerCase().contains(charSquense.toLowerCase())){
                            filterList.add(row);
                        }
                    }

                    countryListFilter = filterList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = countryListFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                countryListFilter = (ArrayList<Country>) results.values;
                notifyDataSetChanged();
            }
        };
    }


    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView countryFlag ;
        TextView countryName ;
        public Holder(@NonNull View itemView) {
            super(itemView);

            countryFlag = itemView.findViewById(R.id.flag);
            countryName = itemView.findViewById(R.id.countryName);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            Intent intent = new Intent(context , Info.class);
            intent.putExtra("countryName" , countryListFilter.get(getAdapterPosition()).getName());
            context.startActivity(intent);
        }
    }


}
