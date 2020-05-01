package com.application.covid19tracker.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.application.covid19tracker.AffectedCountriesActivity;
import com.application.covid19tracker.Model.CountryModel;
import com.application.covid19tracker.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class CountryAdapter extends ArrayAdapter<CountryModel> {

    private Context context;
    private List<CountryModel> countryList;
    private List<CountryModel> countryListFiltered;

    public CountryAdapter(Context context, List<CountryModel> countryList) {
        super(context, R.layout.country_list_item, countryList);

        this.context = context;
        this.countryList = countryList;
        this.countryListFiltered = countryList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_list_item, null, true);

        TextView countryName = view.findViewById(R.id.country_name);
        ImageView countryFlag = view.findViewById(R.id.country_flag);

        countryName.setText(countryListFiltered.get(position).getCountry());
        Glide.with(context).load(countryListFiltered.get(position).getFlag()).into(countryFlag);

        return view;
    }

    @Override
    public int getCount() {
        return countryListFiltered.size();
    }

    @Nullable
    @Override
    public CountryModel getItem(int position) {
        return countryListFiltered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                    filterResults.count = countryList.size();
                    filterResults.values = countryList;
                } else {
                    List<CountryModel> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for (CountryModel itemsModel : countryList) {
                        if (itemsModel.getCountry().toLowerCase().contains(searchStr)) {
                            resultsModel.add(itemsModel);
                        }
                        filterResults.count = resultsModel.size();
                        filterResults.values = resultsModel;
                    }
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                countryListFiltered = (List<CountryModel>) results.values;
                AffectedCountriesActivity.countryModelList = (List<CountryModel>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }
}
