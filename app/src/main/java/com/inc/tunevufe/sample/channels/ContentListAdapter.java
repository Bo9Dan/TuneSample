package com.inc.tunevufe.sample.channels;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inc.cheipesh.sample.R;
import com.inc.tunevufe.channels.model.ShowsResponse;

import java.util.ArrayList;
import java.util.List;

public class ContentListAdapter extends RecyclerView.Adapter<ContentListAdapter.ChannelHolder> {

    private List<ShowsResponse.ShowItem> list = new ArrayList<>();


    private LayoutInflater mInflater;


    public ContentListAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ChannelHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_channel, viewGroup, false);
        return new ChannelHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChannelHolder channelHolder, int i) {
        channelHolder.bindData(get(i));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    ShowsResponse.ShowItem get(int position) {
        return list.get(position);
    }

    void update(List<ShowsResponse.ShowItem> items) {
        list.addAll(items);
        notifyDataSetChanged();
    }

    class ChannelHolder extends RecyclerView.ViewHolder {
        AppCompatTextView title, description;

        ChannelHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text_view_title);
            description = itemView.findViewById(R.id.text_view_description);
        }

        void bindData(ShowsResponse.ShowItem itemValue) {
            title.setText(itemValue.getShowTitle());
            description.setText(itemValue.getDescription());
        }
    }
}
