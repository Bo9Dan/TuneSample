package com.inc.tunevufe.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.inc.cheipesh.sample.R;
import com.inc.tunevufe.channels.events.Event;
import com.inc.tunevufe.channels.events.EventPayload;
import com.inc.tunevufe.channels.model.ShowsResponse;
import com.inc.tunevufe.channels.model.podcast.ChannelConnectionResponse;
import com.inc.tunevufe.channels.playback.PlayProgressMobileProvider;
import com.inc.tunevufe.channels.playback.PlayProgressTelevisionProvider;
import com.inc.tunevufe.channels.playback.QuizProvider;
import com.inc.tunevufe.channels.playback.RadioChannelsProvider;
import com.inc.tunevufe.channels.playback.view.ChannelsCallback;
import com.inc.tunevufe.channels.playback.view.EpisodesCallback;
import com.inc.tunevufe.channels.playback.view.QuizCallback;
import com.inc.tunevufe.sample.channels.ContentListActivity;
import com.inc.tunevufe.sample.content.ContentDetailActivity;

import java.util.List;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        setupUI();
//        openManySockets();
    }

    private void setupUI() {
        findViewById(R.id.tvBtnContent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LaunchActivity.this.startActivity(new Intent(LaunchActivity.this,
                        ContentListActivity.class));
            }
        });

        findViewById(R.id.tvBtnDetails).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LaunchActivity.this.startActivity(new Intent(LaunchActivity.this,
                        ContentDetailActivity.class));
            }
        });
    }

    private void openManySockets() {
        QuizProvider quizProvider = new QuizProvider(new QuizCallback() {
            @Override
            public void onEventReceived(Event event) {

            }

            @Override
            public void onConnectedToServer() {

            }

            @Override
            public void onConnectedToChannel() {

            }

            @Override
            public void onError(Throwable throwable) {

            }
        });

        RadioChannelsProvider radioChannelsProvider = new RadioChannelsProvider(new ChannelsCallback() {
            @Override
            public void onChannelsReceived(List<ShowsResponse.ShowItem> list) {

            }

            @Override
            public void onConnectedToServer() {

            }

            @Override
            public void onConnectedToChannel() {

            }

            @Override
            public void onError(Throwable throwable) {

            }
        });

        PlayProgressMobileProvider progressMobileProvider = new PlayProgressMobileProvider(new EpisodesCallback() {
            @Override
            public void onEpisodeReceived(ChannelConnectionResponse.EpisodeData episodeData) {

            }

            @Override
            public void onEventReceived(EventPayload eventPayload) {

            }

            @Override
            public void onConnectedToServer() {

            }

            @Override
            public void onConnectedToChannel() {

            }

            @Override
            public void onError(Throwable throwable) {

            }
        });

        PlayProgressTelevisionProvider progressTelevisionProvider = new PlayProgressTelevisionProvider(new EpisodesCallback() {
            @Override
            public void onEpisodeReceived(ChannelConnectionResponse.EpisodeData episodeData) {

            }

            @Override
            public void onEventReceived(EventPayload eventPayload) {

            }

            @Override
            public void onConnectedToServer() {

            }

            @Override
            public void onConnectedToChannel() {

            }

            @Override
            public void onError(Throwable throwable) {

            }
        });
            radioChannelsProvider.connectToServer();
//            radioChannelsProvider.connectToServer();
//        quizProvider.connectToServer();
//        quizProvider.connectToServer();
//        quizProvider.connectToServer();
//        quizProvider.connectToServer();
//        quizProvider.connectToServer();
//        quizProvider.connectToServer();
//        quizProvider.connectToServer();
//        quizProvider.connectToServer();
//        quizProvider.connectToServer();
//        quizProvider.connectToServer();

    }
}
