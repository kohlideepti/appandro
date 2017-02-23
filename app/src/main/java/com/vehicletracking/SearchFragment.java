package com.vehicletracking;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.vehicletracking.model.Vehicle;
import com.vehicletracking.search.SearchAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2/21/2017.
 */

public class SearchFragment extends Fragment {

    private List<Vehicle> vehicles = new ArrayList<>();
    private ListView searchResults;
    private SearchAdapter searchAdapter;



    public static SearchFragment newInstance(){
        return new SearchFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search,container,false);



        searchAdapter = new SearchAdapter(getContext(),R.layout.searchable_listview,vehicles);
        searchResults = (ListView) view.findViewById(R.id.search_results);
        searchResults.setAdapter(searchAdapter);

        return view;
    }




    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem mSearchMenuItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) mSearchMenuItem.getActionView();
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {

                } else {
                     searchAdapter.filter(newText);
                }
                return true;
            }
        });
    }

}
