package com.androidmodule.lastfm.ui.main.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.androidmodule.lastfm.R;
import com.androidmodule.lastfm.data.api.ApiServiceImpl;
import com.androidmodule.lastfm.data.api.RetrofitBuilder;
import com.androidmodule.lastfm.data.model.Album;
import com.androidmodule.lastfm.ui.base.ViewModelFactory;
import com.androidmodule.lastfm.ui.main.adapter.MainAdapter;
import com.androidmodule.lastfm.ui.main.viewmodel.MainViewModel;
import com.androidmodule.lastfm.util.Resource;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private SearchView mSearchView;
    private MainViewModel mMainViewModel;
    private ProgressBar mProgressBar;
    private MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initRecyclerView(new ArrayList<Album>());
        initSearchView();
        initViewModel();
        setObserver();
    }

    private void initSearchView() {
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String searchKey) {
                Log.d("searchKey", "" + searchKey);
                if (!searchKey.isEmpty()) {
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mProgressBar.setVisibility(View.VISIBLE);
                    mMainViewModel.searchKey(searchKey);
                }
                else {
                    initRecyclerView(new ArrayList<>());
                    mRecyclerView.setVisibility(View.GONE);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchKey) {

                mRecyclerView.setVisibility(View.GONE);
                if (!searchKey.isEmpty()) {
                    mProgressBar.setVisibility(View.VISIBLE);
                }
                else {
                    initRecyclerView(new ArrayList<>());
                }
                return false;
            }
        });
    }

    private void initRecyclerView(List<Album> albumList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mainAdapter = new MainAdapter(this, albumList);
        mRecyclerView.setAdapter(mainAdapter);
    }

    private void setObserver() {
        mMainViewModel.getAlbums().observe(this, new Observer<Resource<List<Album>>>() {
            @Override
            public void onChanged(Resource<List<Album>> listResource) {
                if(listResource != null && listResource.data != null && listResource.data.size() >0) {
                    mProgressBar.setVisibility(View.GONE);
                    renderList(listResource);
                }
            }
        });
    }

    private void renderList(Resource<List<Album>> listResource) {
        initRecyclerView(listResource.data);
    }

    private void initViewModel() {
        mMainViewModel = ViewModelProviders.of(this,
                new ViewModelFactory(new ApiServiceImpl(RetrofitBuilder.getApiService()))).get(MainViewModel.class);
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mSearchView = (SearchView) findViewById(R.id.search_album);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
    }



}
