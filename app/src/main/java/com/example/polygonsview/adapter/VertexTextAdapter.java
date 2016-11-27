package com.example.polygonsview.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chauncey.view.PolygonsView;
import com.example.polygonsview.R;


/**
 * Created by Administrator on 2016/11/16 0016.
 */

public class VertexTextAdapter extends RecyclerView.Adapter<VertexTextAdapter.ViewHolder> {
    private Context context;
    private int size;
    private PolygonsView polygonsView;

    public VertexTextAdapter(Context context, int size, PolygonsView polygonsView) {
        this.context = context;
        this.polygonsView = polygonsView;
        this.size = size;
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_vertex_text, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.vertex_text.setText(position + "");
        float text_size = polygonsView.getVertexTextSize(position);
        holder.textsize.setText(text_size + "");
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float size = polygonsView.getVertexTextSize(position) + 10;
                polygonsView.setVertexTextSize(position, size);
                holder.textsize.setText(size + "");
            }
        });
        holder.sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float size = polygonsView.getVertexTextSize(position) - 10;
                if (size <= 0) {
                    return;
                }

                polygonsView.setVertexTextSize(position, size);
                holder.textsize.setText(size + "");
            }
        });

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view_dialog = LayoutInflater.from(context).inflate(R.layout.item_diaglog, null);
                final EditText editText = (EditText) view_dialog.findViewById(R.id.et_text);
                builder.setView(editText).setTitle("setTitle").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        polygonsView.setVertexText(position, editText.getText().toString());
                    }
                }).create().show();


            }
        });
    }

    @Override
    public int getItemCount() {
        return size;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView vertex_text, textsize;
        private Button button, add, sub;

        public ViewHolder(View itemView) {
            super(itemView);
            vertex_text = (TextView) itemView.findViewById(R.id.tv_vertex_index);
            textsize = (TextView) itemView.findViewById(R.id.tv_size);
            button = (Button) itemView.findViewById(R.id.btn_text_change);
            add = (Button) itemView.findViewById(R.id.btn_text_size_add);
            sub = (Button) itemView.findViewById(R.id.btn_text_size_sub);
        }
    }
}
