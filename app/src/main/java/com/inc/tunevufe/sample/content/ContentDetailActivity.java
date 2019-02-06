package com.inc.tunevufe.sample.content;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.inc.cheipesh.sample.R;
import com.inc.tunevufe.channels.base.Provider;
import com.inc.tunevufe.channels.events.EventPayload;
import com.inc.tunevufe.channels.model.podcast.ChannelConnectionResponse;
import com.inc.tunevufe.channels.playback.PlayProgressTelevisionProvider;
import com.inc.tunevufe.channels.playback.view.EpisodesCallback;

import static android.provider.Settings.Secure.ANDROID_ID;

public class ContentDetailActivity extends AppCompatActivity
        implements /*IConnection*/ EpisodesCallback {

    private EditText etMedia, etPin, etPrevProgress, etCurrProgress;
    private Button btnSetMedia, btnSetPin, btnSetProgress, btnUpdProgress, btnConnect;
    private Button btnClear, btnDef;
    private EventListAdapter adapter;
    private TextView tvEventCounter;

    private String mMedia, mPin, mPrev, mCurr;

    //    private TuneVu mTuneVuProvider;
    private PlayProgressTelevisionProvider tvProvider;
//    private PlayProgressMobileProvider mblProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        setupUI();
        initTVP();
    }

    /**
     * using for setup Screen elements
     */
    private void setupUI() {
        etMedia = findViewById(R.id.etMedia);
        etPin = findViewById(R.id.etPin);
        etPrevProgress = findViewById(R.id.etPrevPro);
        etCurrProgress = findViewById(R.id.etCurrPro);

        btnUpdProgress = findViewById(R.id.btnUpdPro);
        btnSetProgress = findViewById(R.id.btnPro);
        btnSetPin = findViewById(R.id.btnPin);
        btnSetMedia = findViewById(R.id.btnMedia);
        btnConnect = findViewById(R.id.btnOpenChannel);

        tvEventCounter = findViewById(R.id.tvEventsCount);

        btnSetProgress.setOnClickListener(btnListener);
        btnUpdProgress.setOnClickListener(btnListener);
        btnSetPin.setOnClickListener(btnListener);
        btnSetMedia.setOnClickListener(btnListener);
        btnConnect.setOnClickListener(btnListener);

        btnClear = findViewById(R.id.btnClear);
        btnDef = findViewById(R.id.btnDef);

        final RecyclerView recyclerView = findViewById(R.id.recycler_view_events);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapter = new EventListAdapter(this);
        recyclerView.setAdapter(adapter);

        btnDef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                ContentDetailActivity.this.onClick(v1);
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnConnect.isEnabled()) {
                    etMedia.setText("");
                    etPin.setText("");
                    etPrevProgress.setText("");
                    etCurrProgress.setText("");
                }
            }
        });

    }

    /**
     * hard code values
     */
    private void easyWay() {
//        etMedia.setText("https://s3.amazonaws.com/tunevu/static/Media/1314_MasterChef+Australia+S09E54.mp4");
        etMedia.setText("https://s3.amazonaws.com/tunevu/static/Media/AudioBrother+vs-+Brother+S05E02_CH8_-c_1315_-e_on.mp4");
        etPin.setText("1234");
        etCurrProgress.setText("6");
        etPrevProgress.setText("0");
    }

    /**
     * enable ui elements for user
     */
    private void setEnabled() {
        btnSetProgress.setEnabled(true);
        btnUpdProgress.setEnabled(true);
        btnSetPin.setEnabled(true);
        btnSetMedia.setEnabled(true);
        btnConnect.setEnabled(true);
        btnDef.setEnabled(true);
        btnClear.setEnabled(true);
    }

    private void updateProgress() {
        if (mPrev != null && mCurr != null) {
            etPrevProgress.setText(mCurr);
            etCurrProgress.setText(String.valueOf(Integer.valueOf(mCurr) + 6));
        }
    }

    /**
     * initialize tune vu provider with current (this) IConnection interface
     */
    private void initTVP() {
//        deprecated
//        mTuneVuProvider = new TuneVu(this, false);
//        mTuneVuProvider.connectToServer();
        tvProvider = new PlayProgressTelevisionProvider(Provider.Version.RELEASE, (EpisodesCallback)this);
        tvProvider.connectToServer();
        //or
//        mblProvider = new PlayProgressMobileProvider(this);
//        mblProvider.connectToServer();
    }

    /**
     * trigger onBtn clicks
     */
    private View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnMedia:
                    ContentDetailActivity.this.setMediaId();
                    break;
                case R.id.btnPin:
                    ContentDetailActivity.this.setPin();
                    break;
                case R.id.btnPro:
                    ContentDetailActivity.this.pushProgress();
                    break;
                case R.id.btnUpdPro:
                    ContentDetailActivity.this.updateProgress();
                    break;
                case R.id.btnOpenChannel:
                    ContentDetailActivity.this.connectChannel();
                    break;
            }
        }
    };


    private void setPin() {
        mPin = etPin.getText().toString();
    }

    private void setMediaId() {
        mMedia = etMedia.getText().toString();
//        deprecated
        /*if (mTuneVuProvider != null && !mMedia.isEmpty()) {
            mTuneVuProvider.setMediaId(mMedia);
        }*/


        if (tvProvider != null && !mMedia.isEmpty()) {
            tvProvider.setMediaTopic(mMedia);
        }
        /*or*/
        /*if (mblProvider != null && !mMedia.isEmpty()) {
            mblProvider.setMediaTopic(mMedia);
        }*/
    }

    /**
     * set device id and pin param
     */
    private void connectChannel() {
        if (mMedia != null && mPin != null) {
//            mTuneVuProvider.connectToChannel(getDevId(), mPin);
            tvProvider.connectToChannel(getDevId(), mPin);
//            or
//            mblProvider.connectToChannel(getDevId());
        }
    }

    /**
     * read progress from UI
     */
    private void pushProgress() {
        mPrev = etPrevProgress.getText().toString();
        mCurr = etCurrProgress.getText().toString();

        if ((mPrev != null && !mPrev.isEmpty()) && (mCurr != null && !mCurr.isEmpty())) {
            pushProgressData(Long.parseLong(mPrev), Long.parseLong(mCurr));
        }
    }

    /**
     * push the progress
     */
    private void pushProgressData(final long prev, final long curr) {
//        mTuneVuProvider.updateProgress(prev, curr);
        tvProvider.updateProgress(prev, curr);
//        or
//        mblProvider.updateProgress(prev, curr);
    }

    /**
     * calling when socket connection open
     */
    @Override
    public void onConnectedToServer() {
        Toast.makeText(
                getApplicationContext(),
                "onConnectedToServer: ",
                Toast.LENGTH_SHORT
        )
                .show();
        setEnabled();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onEventReceived(EventPayload eventPayload) {
        Toast.makeText(
                getApplicationContext(),
                "onEventReceived: ",
                Toast.LENGTH_SHORT
        )
                .show();
        adapter.update(eventPayload.getEvents());
        tvEventCounter.setText("Events : " + adapter.getItemCount());
    }

    @Override
    public void onEpisodeReceived(ChannelConnectionResponse.EpisodeData episodeData) {
        Toast.makeText(
                getApplicationContext(),
                "onEpisodeReceived: ",
                Toast.LENGTH_SHORT
        )
                .show();
    }

    @Override
    public void onConnectedToChannel() {
        Toast.makeText(
                getApplicationContext(),
                "onConnectedToChannel: ",
                Toast.LENGTH_SHORT
        )
                .show();
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

    /**
     * Don`t forget disconnect from server
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mTuneVuProvider.disconnectFromServer();
        tvProvider.disconnectFromServer();
//        or
//        mblProvider.disconnectFromServer();
    }

    @SuppressLint("HardwareIds")
    private String getDevId() {
        return Settings.Secure.getString(getContentResolver(), ANDROID_ID);
    }

    private void onClick(View v) {
        easyWay();
    }
}
