package com.dinnova.sharedlibrary.utils;

import android.widget.Filter;

import androidx.recyclerview.widget.RecyclerView;

import com.dinnova.kidzgram.support.webservice.models.OrganizationModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortAndFilter {

    RecyclerView.Adapter adapter;
    List<OrganizationModel> filterItems;
    List<OrganizationModel> allItems;

    public SortAndFilter(RecyclerView.Adapter adapter, List<OrganizationModel> allItems) {
        this.adapter = adapter;
        this.filterItems = allItems;
        this.allItems = allItems;
    }

    public void sortByNameTsa3ody() {
        Collections.sort(filterItems, (data1, data2) -> data1.Name.compareToIgnoreCase(data2.Name));
        adapter.notifyDataSetChanged();
    }

    public void sortByNameTnazoly() {
        Collections.sort(filterItems, (data1, data2) -> data2.Name.compareToIgnoreCase(data1.Name));
    }

    public void sortByRate() {
        Collections.sort(filterItems, (data1, data2) -> {
            if (data1.ViewCount > data2.ViewCount) {
                return -1;
            } else if (data1.ViewCount < data2.ViewCount) {
                return 1;
            } else {
                return 0;
            }
        });
        adapter.notifyDataSetChanged();
    }

    public void sortByPriceUp() {
        Collections.sort(filterItems, (data1, data2) -> {
            if (data1.BasePrice > data2.BasePrice) {
                return 1;
            } else if (data1.BasePrice < data2.BasePrice) {
                return -1;
            } else {
                return 0;
            }
        });
        adapter.notifyDataSetChanged();
    }

    public void sortByPriceDown() {
        Collections.sort(filterItems, (data1, data2) -> {
            if (data1.BasePrice > data2.BasePrice) {
                return -1;
            } else if (data1.BasePrice < data2.BasePrice) {
                return 1;
            } else {
                return 0;
            }
        });
        adapter.notifyDataSetChanged();
    }

    public void searchByName(String name) {
        new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();

                if (charString.isEmpty()) {
                    filterItems = allItems;
                } else {
                    List<OrganizationModel> filteredList = new ArrayList<>();
                    for (OrganizationModel row : filterItems) {

                        charSequence = charString.toLowerCase();
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.Name.toLowerCase().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    filterItems = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filterItems;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filterItems = (List<OrganizationModel>) filterResults.values;
                // refresh the list with filtered data
                adapter.notifyDataSetChanged();
            }
        }.filter(name);
    }


}
