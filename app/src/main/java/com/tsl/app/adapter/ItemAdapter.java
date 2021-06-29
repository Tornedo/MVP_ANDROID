package com.tsl.app.adapter;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.tsl.app.R;
import com.tsl.app.model.ListViewItem;
import com.tsl.app.service.responsedto.tsl.MessageListResponse;

import java.util.List;


public class ItemAdapter<T extends ListViewItem> extends
        RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private List<MessageListResponse> listData;
    private static Context context;

    public ItemAdapter(List<MessageListResponse> data, Context context) {
        listData = data;
        this.context = context;
    }

    public void update(List<MessageListResponse> data) {
        if (data != null) {
            Log.e("MessageListResponse ", data.size() + "");
            listData.addAll(data);
        }

        this.notifyDataSetChanged();
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context ctx = parent.getContext();
        LayoutInflater inflator = LayoutInflater.from(ctx);

        View listItemView = inflator.inflate(R.layout.adaptar_listview_item, parent, false);

        ViewHolder holder = new ViewHolder(listItemView, ctx);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final MessageListResponse item = listData.get(position);
        holder.setMessage( item.getMessage());
    }

    @Override
    public int getItemCount() {
        return (listData != null ? listData.size() : 0);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Snackbar.make(v, "Detail view not implemented yet", Snackbar.LENGTH_SHORT)
                    .show();
        }
    };


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView message;
        ;

        public ViewHolder(View itemView, Context context) {

            super(itemView);

            message = (TextView) itemView.findViewById(R.id.message);

        }



        public void setMessage(String txt) {
            message.setText(txt);
        }


    }


}
