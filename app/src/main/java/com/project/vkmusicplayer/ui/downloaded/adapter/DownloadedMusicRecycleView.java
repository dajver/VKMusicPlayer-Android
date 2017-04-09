package com.project.vkmusicplayer.ui.downloaded.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.vkmusicplayer.R;
import com.project.vkmusicplayer.ui.downloaded.controllers.RealmController;
import com.project.vkmusicplayer.ui.downloaded.controllers.model.RealmModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gleb on 26.02.17.
 */

public class DownloadedMusicRecycleView extends RecyclerView.Adapter<DownloadedMusicRecycleView.ViewHolder> {

    private List<RealmModel> realmModels = null;
    private OnItemClickListener onItemClickListener;
    private DownloadedMusicRecycleView.ViewHolder viewHolder;
    private ArrayList<DownloadedMusicRecycleView.ViewHolder> viewHolders = new ArrayList<>();
    private ArrayList<DownloadedMusicRecycleView.ViewHolder> viewHolders2 = new ArrayList<>();

    private Context context;

    public DownloadedMusicRecycleView(Context context, List<RealmModel> searchModels) {
        this.realmModels = searchModels;
        this.context = context;
    }

    @Override
    public DownloadedMusicRecycleView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_downloaded_music, parent, false);
        DownloadedMusicRecycleView.ViewHolder pvh = new DownloadedMusicRecycleView.ViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final DownloadedMusicRecycleView.ViewHolder holder, final int position) {
        this.viewHolder = holder;
        holder.title.setText(realmModels.get(position).getTitle());
    }

    public DownloadedMusicRecycleView.ViewHolder getHolder() {
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return realmModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textView)
        TextView title;
        @BindView(R.id.indicator)
        ImageView indicator;
        @BindView(R.id.remove)
        ImageView remove;

        private boolean isClicked = false;

        ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            viewHolders2.add(this);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = DownloadedMusicRecycleView.ViewHolder.super.getAdapterPosition();
                    if (viewHolders.size() > 0) {
                        viewHolders.get(0).indicator.setVisibility(View.GONE);
                        viewHolders.remove(0);
                        for(int i = 0; i < viewHolders2.size(); i++) {
                            if (viewHolders2.size() > 0) {
                                viewHolders2.get(i).indicator.setVisibility(View.GONE);
                            }
                        }
                    }
                    if (isClicked) {
                        indicator.setVisibility(View.GONE);
                        onItemClickListener.onItemStopClick();
                        isClicked = false;
                    } else {
                        indicator.setVisibility(View.VISIBLE);
                        onItemClickListener.onItemClick(position, realmModels.get(position).getFilePathUrl());
                        isClicked = true;
                    }
                    viewHolders.add(DownloadedMusicRecycleView.ViewHolder.this);
                }
            });
            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = DownloadedMusicRecycleView.ViewHolder.super.getAdapterPosition();
                    new RealmController(context).removeFavorite(realmModels.get(position).getId());
                    DownloadedMusicRecycleView.super.notifyItemRemoved(position);
                }
            });
        }

        public void setSelectedItem(int position) {
            for(int i = 0; i < viewHolders2.size(); i++) {
                if (viewHolders2.size() > 0) {
                    viewHolders2.get(i).indicator.setVisibility(View.GONE);
                }
            }
            viewHolders2.get(position).indicator.setVisibility(View.VISIBLE);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, String filePath);
        void onItemStopClick();
    }
}