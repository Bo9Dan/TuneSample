package com.inc.tunevufe.sample.channels;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.inc.cheipesh.sample.R;
import com.inc.tunevufe.channels.model.ShowsResponse;
import com.inc.tunevufe.channels.playback.VideoChannelsProvider;
import com.inc.tunevufe.channels.playback.view.ChannelsCallback;

import java.util.List;

public class ContentListActivity extends AppCompatActivity implements ChannelsCallback {

    private ContentListAdapter adapter;
    private VideoChannelsProvider provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        setupUI();
        initTVP();
    }

    /**
     * using for setup Screen elements
     */
    private void setupUI() {
        final RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new ContentListAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    /**
     * initialize tune vu channel provider with current (this) -IConnection- *ChannelsCallback* interface
     */
    private void initTVP() {
        provider = new VideoChannelsProvider(this);
        provider.connectToServer();
    }

    @Override
    public void onConnectedToServer() {
        Toast.makeText(getApplicationContext(), "onConnectedToServer: ", Toast.LENGTH_SHORT ).show();
        provider.connectToChannel();
    }

    @Override
    public void onChannelsReceived(List<ShowsResponse.ShowItem> list) {
        showChannels(list);
    }

    @Override
    public void onConnectedToChannel() {
        Toast.makeText(getApplicationContext(), "onConnectedToChannel: ", Toast.LENGTH_SHORT ).show();
    }

    @Override
    public void onError(Throwable throwable) {
        Toast.makeText(
                getApplicationContext(),
                throwable.getMessage(),
                Toast.LENGTH_SHORT
        )
                .show();
    }

    public void showChannels(List<ShowsResponse.ShowItem> list) {
        adapter.update(list);
    }

    /**
     * Don`t forget disconnect from server
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        provider.disconnectFromServer();
    }
}
