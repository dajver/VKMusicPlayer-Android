package com.project.vkmusicplayer.ui.downloaded;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.vkmusicplayer.R;
import com.project.vkmusicplayer.api.RestClient;
import com.project.vkmusicplayer.etc.Constants;
import com.project.vkmusicplayer.ui.BaseFragment;
import com.project.vkmusicplayer.ui.downloaded.adapter.DownloadedMusicRecycleView;
import com.project.vkmusicplayer.ui.downloaded.adapter.MusicRecycleView;
import com.project.vkmusicplayer.ui.downloaded.adapter.model.SearchModel;
import com.project.vkmusicplayer.ui.downloaded.controllers.RealmController;
import com.project.vkmusicplayer.ui.downloaded.controllers.model.RealmModel;
import com.project.vkmusicplayer.ui.downloaded.player.HybridMediaPlayer;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gleb on 22.02.17.
 */

public class DownloadedFragment extends BaseFragment implements
        DownloadedMusicRecycleView.OnItemClickListener, TextWatcher {

    @BindView(R.id.listView)
    RecyclerView recyclerView;
    @BindView(R.id.listView2)
    RecyclerView recyclerView2;
    @BindView(R.id.search)
    EditText search;
    @BindView(R.id.emptyView)
    TextView emptyView;
    @BindView(R.id.title1)
    TextView title1;
    @BindView(R.id.title2)
    TextView title2;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.play)
    ImageView play;
    @BindView(R.id.forward)
    ImageView forward;
    @BindView(R.id.audioName)
    TextView audioName;
    @BindView(R.id.bottomBar)
    LinearLayout bottomBar;

    private HybridMediaPlayer mediaPlayer;
    private RealmResults<RealmModel> audioList;
    private int position;
    private boolean isPlayed = false;
    private DownloadedMusicRecycleView musicRecycleView;
    private boolean isPlayerShowed = false;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycleViewSetup(recyclerView);
        recycleViewSetup(recyclerView2);
        mediaPlayer = HybridMediaPlayer.getInstance(context);
        search.addTextChangedListener(this);

        audioList = new RealmController(context).getAllTracks();
        musicRecycleView = new DownloadedMusicRecycleView(context, audioList);
        musicRecycleView.setOnItemClickListener(this);
        recyclerView.setAdapter(musicRecycleView);
        if(new RealmController(context).getAllTracks().size() > 0)
            emptyView.setVisibility(View.GONE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_downloaded, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onItemClick(int position, String filePath) {
        this.position = position;
        mediaPlayer.setDataSource(filePath);
        mediaPlayer.prepare();
        mediaPlayer.play();

        if(!isPlayerShowed) {
            TranslateAnimation slide = new TranslateAnimation(0, 0, 500, 0);
            slide.setDuration(1000);
            slide.setFillAfter(true);
            bottomBar.startAnimation(slide);
            bottomBar.setVisibility(View.VISIBLE);
            isPlayerShowed = true;
        }

        play.setImageResource(R.mipmap.pause_icon);
        isPlayed = false;
        audioName.setText(audioList.get(position).getTitle());
    }

    @Override
    public void onItemStopClick() {
        mediaPlayer.pause();

        if(isPlayerShowed) {
            TranslateAnimation slide = new TranslateAnimation(0, 0, 0, 500);
            slide.setDuration(1000);
            slide.setFillAfter(true);
            bottomBar.startAnimation(slide);
            bottomBar.setVisibility(View.GONE);
            isPlayerShowed = false;
        }

        play.setImageResource(R.mipmap.play_icon);
        isPlayed = true;
    }

    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
    }

    @OnClick(R.id.back)
    public void onBackClick() {
        if(position > 0) {
            position--;
            mediaPlayer.setDataSource(audioList.get(position).getFilePathUrl());
            mediaPlayer.prepare();
            mediaPlayer.play();
            play.setImageResource(R.mipmap.pause_icon);
            isPlayed = true;
            audioName.setText(audioList.get(position).getTitle());

            musicRecycleView.getHolder().setSelectedItem(position);
        }
    }

    @OnClick(R.id.play)
    public void onPlayClick() {
        if(isPlayed) {
            play.setImageResource(R.mipmap.play_icon);
            mediaPlayer.pause();
            isPlayed = false;
        } else {
            play.setImageResource(R.mipmap.pause_icon);
            mediaPlayer.play();
            isPlayed = true;
        }
    }

    @OnClick(R.id.forward)
    public void onForwardClick() {
        if(position < audioList.size() - 1) {
            position++;
            mediaPlayer.setDataSource(audioList.get(position).getFilePathUrl());
            mediaPlayer.prepare();
            mediaPlayer.play();
            play.setImageResource(R.mipmap.pause_icon);
            isPlayed = true;
            audioName.setText(audioList.get(position).getTitle());

            musicRecycleView.getHolder().setSelectedItem(position);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        DownloadedMusicRecycleView downloadedMusicRecycleView = null;
        if(charSequence.length() > 0) {
            downloadedMusicRecycleView = new DownloadedMusicRecycleView(context, new RealmController(context).getByTitle(charSequence.toString()));
            recyclerView2.setVisibility(View.VISIBLE);
            title1.setVisibility(View.VISIBLE);
            title2.setVisibility(View.VISIBLE);
            RestClient.instance().searchAudio(charSequence.toString(), Constants.API_KEY).enqueue(new Callback<SearchModel>() {
                @Override
                public void onResponse(Call<SearchModel> call, final Response<SearchModel> response) {
                    if(response.body() != null) {
                        if (response.body().getList().size() > 0)
                            emptyView.setVisibility(View.GONE);
                        MusicRecycleView musicRecycleView = new MusicRecycleView(context, response.body().getList());
                        musicRecycleView.setOnItemClickListener(new MusicRecycleView.OnItemClickListener() {
                            @Override
                            public void onItemClick(String id) {
                                RestClient.instance().getAudio(id, Constants.API_KEY).enqueue(new Callback<List<List<String>>>() {
                                    @Override
                                    public void onResponse(Call<List<List<String>>> call, Response<List<List<String>>> response) {
                                        mediaPlayer.setDataSource(response.body().get(0).get(2));
                                        mediaPlayer.prepare();
                                        mediaPlayer.play();
                                    }

                                    @Override
                                    public void onFailure(Call<List<List<String>>> call, Throwable t) {
                                        t.printStackTrace();
                                    }
                                });
                            }

                            @Override
                            public void onItemStopClick() {
                                mediaPlayer.pause();
                            }
                        });
                        recyclerView2.setAdapter(musicRecycleView);
                    }
                }

                @Override
                public void onFailure(Call<SearchModel> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        } else {
            if(new RealmController(context).getAllTracks().size() > 0)
                emptyView.setVisibility(View.GONE);
            downloadedMusicRecycleView = new DownloadedMusicRecycleView(context, new RealmController(context).getAllTracks());
            recyclerView2.setVisibility(View.GONE);
            title1.setVisibility(View.GONE);
            title2.setVisibility(View.GONE);
        }
        downloadedMusicRecycleView.setOnItemClickListener(this);
        recyclerView.setAdapter(downloadedMusicRecycleView);
    }

    @Override
    public void afterTextChanged(Editable editable) { }
}
