package com.inc.tunevufe.sample.content;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inc.cheipesh.sample.R;
import com.inc.tunevufe.channels.events.Event;
import com.inc.tunevufe.channels.events.InterstitialEvent;

import java.util.ArrayList;
import java.util.List;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.ChannelHolder> {

    private List<Event> list = new ArrayList<>();


    private LayoutInflater mInflater;


    public EventListAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ChannelHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_event, viewGroup, false);
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

    Event get(int position) {
        return list.get(position);
    }

    void update(List<Event> items) {
        list.addAll(items);
        notifyDataSetChanged();
    }

    class ChannelHolder extends RecyclerView.ViewHolder {
        AppCompatTextView title, description, type;

        ChannelHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text_view_title);
            description = itemView.findViewById(R.id.text_view_description);
            type = itemView.findViewById(R.id.text_view_type);
        }

        void bindData(Event itemValue) {
            title.setText(itemValue.getTitle());
            description.setText(itemValue.getName());
            type.setText(getTypeName(itemValue));
        }


        private String getTypeName(Event event) {
            String name = "";

            if (event instanceof InterstitialEvent) {
                InterstitialEvent interstitialEvent = (InterstitialEvent) event;

                interstitialEvent.getWebUrl();
                interstitialEvent.getFacebookPage();
                interstitialEvent.getTitle();

            }

            return name;
        }

    }
}
