package com.project.vkmusicplayer.ui.downloaded.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.vkmusicplayer.R;
import com.project.vkmusicplayer.api.RestClient;
import com.project.vkmusicplayer.etc.Constants;
import com.project.vkmusicplayer.ui.downloaded.controllers.RealmController;
import com.project.vkmusicplayer.ui.downloaded.adapter.task.DownloadAsyncTask;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gleb on 23.02.17.
 */

public class MusicRecycleView extends RecyclerView.Adapter<MusicRecycleView.ViewHolder>{

    private List<List<String>> searchModels = new ArrayList<>();
    private OnItemClickListener onItemClickListener;
    private ArrayList<MusicRecycleView.ViewHolder> viewHolders = new ArrayList<>();
    private Context context;

    public MusicRecycleView(Context context, List<List<String>> searchModels) {
        this.searchModels = searchModels;
        this.context = context;
    }

    @Override
    public MusicRecycleView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listen_music, parent, false);
        MusicRecycleView.ViewHolder pvh = new MusicRecycleView.ViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final MusicRecycleView.ViewHolder holder, final int position) {
        holder.title.setText(searchModels.get(position).get(4) + " - " + searchModels.get(position).get(3));
    }

    @Override
    public int getItemCount() {
        return searchModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textView)
        TextView title;
        @BindView(R.id.indicator)
        ImageView indicator;
        @BindView(R.id.favorite)
        ImageView favorite;

        private boolean isClicked = false;

        ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = MusicRecycleView.ViewHolder.super.getAdapterPosition();
                    if (viewHolders.size() > 0) {
                        viewHolders.get(0).indicator.setVisibility(View.GONE);
                        viewHolders.remove(0);
                    }
                    if (isClicked) {
                        indicator.setVisibility(View.GONE);
                        onItemClickListener.onItemStopClick();
                        isClicked = false;
                    } else {
                        indicator.setVisibility(View.VISIBLE);
                        onItemClickListener.onItemClick(searchModels.get(position).get(1) + "_" + searchModels.get(position).get(0));
                        isClicked = true;
                    }
                    viewHolders.add(ViewHolder.this);
                }
            });
            favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = MusicRecycleView.ViewHolder.super.getAdapterPosition();

                    String musicId = searchModels.get(position).get(1) + "_" + searchModels.get(position).get(0);
                    final String title = searchModels.get(position).get(4) + " - " + searchModels.get(position).get(3);
                    RestClient.instance().getAudio(musicId, Constants.API_KEY).enqueue(new Callback<List<List<String>>>() {
                        @Override
                        public void onResponse(Call<List<List<String>>> call, Response<List<List<String>>> response) {
                            new DownloadAsyncTask(context).setOnAudioFileDownloaded(new DownloadAsyncTask.OnAudioFileDownloaded() {
                                @Override
                                public void downloadedFilePath(String path) {
                                    new RealmController(context).addFavorite(path, title);
                                    favorite.setImageResource(R.mipmap.checked);
                                }
                            }).execute(response.body().get(0).get(2));
                        }

                        @Override
                        public void onFailure(Call<List<List<String>>> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                }
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(String id);
        void onItemStopClick();
    }
}