package com.example.polygonsview.adapter;

import android.content.Context;

import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.chauncey.view.PolygonsView;
import com.example.polygonsview.R;

import java.util.Random;

/**
 * Created by Administrator on 2016/11/14 0014.
 */

public class PolygonsAdapter extends RecyclerView.Adapter<PolygonsAdapter.ViewHolder> {
    private Context context;
    private int size;
    private PolygonsView polygonsView;
    private int[] colors = {Color.BLUE, Color.RED, Color.CYAN, Color.DKGRAY, Color.GREEN, Color.LTGRAY, Color.MAGENTA, Color.YELLOW, Color.TRANSPARENT};

    public PolygonsAdapter(Context context, int size, PolygonsView polygonsView) {
        this.context = context;
        this.size = size;
        this.polygonsView = polygonsView;
    }

    public void addData(int index) {
        size += 1;
        notifyItemInserted(index);
    }

    public void removeData(int index) {
        size -= 1;
        notifyItemRemoved(index);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType != -1) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_polygons, parent, false);
            return new ViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.index.setText(position + "");
        holder.width.setText(polygonsView.getEdgeWidth(position) + "");
        holder.btn_edge_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                polygonsView.setPolygonColor(position, colors[new Random().nextInt(colors.length)]);
            }
        });
        holder.btn_edge_fill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                polygonsView.setPolygonStyle(position, Paint.Style.FILL);
            }
        });
        holder.btn_edge_stroke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                polygonsView.setPolygonStyle(position, Paint.Style.STROKE);
            }
        });
        holder.btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float width = polygonsView.getEdgeWidth(position) + 4;
                polygonsView.setEdgeWidth(position, width);
                holder.width.setText(width + "");
            }
        });
        holder.btn_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float width = polygonsView.getEdgeWidth(position) - 4;
                if (width < 0) {
                    return;
                }
                polygonsView.setEdgeWidth(position, width);
                holder.width.setText(width + "");
            }
        });

    }

    @Override
    public int getItemCount() {
        return size;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private Button btn_edge_color,
                btn_edge_fill, btn_edge_stroke, btn_add, btn_sub;
        private TextView index, width;

        public ViewHolder(View itemView) {
            super(itemView);
            index = (TextView) itemView.findViewById(R.id.tv_edge_index);
            width = (TextView) itemView.findViewById(R.id.tv_polygon_width);
            btn_edge_color = (Button) itemView.findViewById(R.id.btn_edge_color);
            btn_edge_fill = (Button) itemView.findViewById(R.id.btn_edge_fill);
            btn_edge_stroke = (Button) itemView.findViewById(R.id.btn_edge_stroke);
            btn_add = (Button) itemView.findViewById(R.id.btn_edge_width_add);
            btn_sub = (Button) itemView.findViewById(R.id.btn_edge_width_sub);
        }
    }

}
