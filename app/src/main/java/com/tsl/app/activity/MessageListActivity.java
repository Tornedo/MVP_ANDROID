package com.tsl.app.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


import com.tsl.app.R;
import com.tsl.app.adapter.ItemAdapter;

import com.tsl.app.databinding.ActivityMessageListBinding;
import com.tsl.app.presenter.MessagePresenter;
import com.tsl.app.service.responsedto.tsl.MessageListResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageListActivity extends BaseActivity {

    private String TAG = this.getClass().getSimpleName();

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */


    private ActivityMessageListBinding binding;
    private MessagePresenter tslMessagePresenter;
    List<MessageListResponse> dataList;
    private RecyclerView rcview;
    ItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate ActiveItemsActivity");
        super.onCreate(savedInstanceState);
        super.setContext(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_message_list);
        rcview = binding.recyclerView;
        tslMessagePresenter = new MessagePresenter(null);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        tslMessagePresenter.getMessage(wishListResponse);
    }
    private Callback<ArrayList<MessageListResponse>> wishListResponse = new Callback<ArrayList<MessageListResponse>>() {
        @Override
        public void onResponse(Call<ArrayList<MessageListResponse>> call, Response<ArrayList<MessageListResponse>> response) {
            hideProcessing();
            if (response.isSuccess()) {
                dataList = new ArrayList();
                ArrayList<MessageListResponse> data = response.body();
                dataList = data;
                Log.e("sda", "Asda");
                setupRecycleListView();
            } else {
                try {
                    showToast(getString(R.string.generic_error));

                } catch (Exception e) {

                }
            }
        }

        @Override
        public void onFailure(Call<ArrayList<MessageListResponse>> call, Throwable t) {
            hideProcessing();
            Log.e(TAG + "", t.getMessage(), t);
            showToast(getString(R.string.generic_error));
        }
    };

    private void setupRecycleListView() {


        itemAdapter = new ItemAdapter(dataList, MessageListActivity.this);
        rcview.setAdapter(itemAdapter);
        rcview.setLayoutManager(new LinearLayoutManager(MessageListActivity.this));
        rcview.setHasFixedSize(true);

    }



    @Override
    protected void onResume() {
        super.onResume();
        Log.e("active item , test", "");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
