package com.example.polygonsview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.chauncey.view.PolygonsView;
import com.example.polygonsview.R;


/**
 * Created by Administrator on 2016/11/15 0015.
 */

public class ProgressAdapter extends RecyclerView.Adapter<ProgressAdapter.ViewHolder> {
    private Context context;
    private int size;
    private PolygonsView polygonsView;


    public ProgressAdapter(Context context, int size, PolygonsView polygonsView) {
        this.context = context;
        this.size = size;
        this.polygonsView = polygonsView;
    }

    public void addData(int index) {
        size++;
        notifyItemInserted(index);
    }

    public void removeData(int index) {
        size--;
        notifyItemRemoved(index);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_progress, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.index.setText(position + "");

        holder.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                polygonsView.setProgress(position, i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return size;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView index;
        private SeekBar seekBar;

        public ViewHolder(View itemView) {
            super(itemView);
            index = (TextView) itemView.findViewById(R.id.tv_progress_index);
            seekBar = (SeekBar) itemView.findViewById(R.id.progress);
        }
    }
}
