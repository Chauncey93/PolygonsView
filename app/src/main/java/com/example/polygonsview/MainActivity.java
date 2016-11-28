package com.example.polygonsview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.chauncey.view.PolygonsView;
import com.example.polygonsview.adapter.MyViewPagerAdapter;
import com.example.polygonsview.adapter.PolygonsAdapter;
import com.example.polygonsview.adapter.ProgressAdapter;
import com.example.polygonsview.adapter.VertexTextAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private PolygonsView polygonsView;
    private int[] colors = {Color.WHITE, Color.BLACK, Color.BLUE, Color.RED, Color.CYAN, Color.DKGRAY, Color.GREEN, Color.LTGRAY, Color.MAGENTA, Color.YELLOW, Color.TRANSPARENT};
    private boolean centerLineEnable;
    private PolygonsAdapter polygonsAdapter;
    private RecyclerView rv_polygons;
    private RecyclerView rvvertex;
    private TextView tvpolygoncount, tvprogresswidth, tvvertex;
    private ProgressAdapter progressAdapter;
    private VertexTextAdapter vertexTextAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }


    private void init() {
        polygonsView = (PolygonsView) findViewById(R.id.PolygonsView);
        ViewPager viewPager = (ViewPager) findViewById(R.id.ViewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.TabLayout);
        List<View> views = new ArrayList<>();
        View view_diagonals = getLayoutInflater().inflate(R.layout.view_diagonals, null);

        View view_polygons = getLayoutInflater().inflate(R.layout.view_polygons, null);

        View view_progress = getLayoutInflater().inflate(R.layout.view_progress, null);

        views.add(view_diagonals);
        views.add(view_polygons);
        views.add(view_progress);
        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(views);
        viewPager.setAdapter(myViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText("Diagonal");
        tabLayout.getTabAt(1).setText("Polygons");
        tabLayout.getTabAt(2).setText("Progress");

        //第一个View
        rvvertex = (RecyclerView) view_diagonals.findViewById(R.id.rv_vertex);
        Button btncenterLineenable = (Button) view_diagonals.findViewById(R.id.btn_centerLine_enable);
        Button btncenterLinecolor = (Button) view_diagonals.findViewById(R.id.btn_centerLine_color);
        Button btnvertexsub = (Button) view_diagonals.findViewById(R.id.btn_vertex_sub);
        Button btnvertexadd = (Button) view_diagonals.findViewById(R.id.btn_vertex_add);
        tvvertex = (TextView) view_diagonals.findViewById(R.id.tv_vertex);
        tvvertex.setText(polygonsView.getVertexs() + "");
        rvvertex.setLayoutManager(new LinearLayoutManager(this));
        vertexTextAdapter = new VertexTextAdapter(this, polygonsView.getVertexs(), polygonsView);
        rvvertex.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rvvertex.setAdapter(vertexTextAdapter);
        btncenterLineenable.setOnClickListener(this);
        btnvertexadd.setOnClickListener(this);
        btnvertexsub.setOnClickListener(this);
        btncenterLinecolor.setOnClickListener(this);

        //第二个View
        Button btnedgesub = (Button) view_polygons.findViewById(R.id.btn_count_sub);
        Button btnedgeadd = (Button) view_polygons.findViewById(R.id.btn_count_add);
        tvpolygoncount = (TextView) view_polygons.findViewById(R.id.tv_polygon_count);
        tvpolygoncount.setText(polygonsView.getPolygonCount() + "");
        rv_polygons = (RecyclerView) view_polygons.findViewById(R.id.rv_polygons);
        btnedgesub.setOnClickListener(this);
        btnedgeadd.setOnClickListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_polygons.setLayoutManager(linearLayoutManager);
        polygonsAdapter = new PolygonsAdapter(this, polygonsView.getPolygonCount(), polygonsView);
        rv_polygons.setAdapter(polygonsAdapter);
        rv_polygons.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        //第三个View
        RecyclerView rv_edge = (RecyclerView) view_progress.findViewById(R.id.rv_edge);
        Button btnprogresswidth_add = (Button) view_progress.findViewById(R.id.btn_progress_width_add);
        Button btnprogresswidth_sub = (Button) view_progress.findViewById(R.id.btn_progress_width_sub);
        Button btnprogresscolor = (Button) view_progress.findViewById(R.id.btn_progress_color);
        Button btnprogresstoggle = (Button) view_progress.findViewById(R.id.btn_progress_toggle);
        tvprogresswidth = (TextView) view_progress.findViewById(R.id.tv_progress_width);
        tvprogresswidth.setText(polygonsView.getProgressLineWidth() + "");
        btnprogresswidth_add.setOnClickListener(this);
        btnprogresswidth_sub.setOnClickListener(this);
        btnprogresscolor.setOnClickListener(this);
        btnprogresstoggle.setOnClickListener(this);
        progressAdapter = new ProgressAdapter(this, polygonsView.getVertexs(), polygonsView);
        rv_edge.setAdapter(progressAdapter);
        rv_edge.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            //改变对角线颜色
            case R.id.btn_centerLine_color:
                Random random = new Random();
                int i = random.nextInt(colors.length);
                polygonsView.setDiagonalsLineColor(colors[i]);

                break;
            //对角线是否开启
            case R.id.btn_centerLine_enable:
                polygonsView.setDiagonalsLineEnable(centerLineEnable);
                centerLineEnable = !centerLineEnable;
                break;
            //增加顶点
            case R.id.btn_vertex_add:
                int vertex = polygonsView.getVertexs();
                polygonsView.setVertexs(vertex + 1);
                tvvertex.setText(vertex + 1 + "");
                vertexTextAdapter.addData(vertex);
                rvvertex.scrollToPosition(vertex);
                break;
            //减少顶点
            case R.id.btn_vertex_sub:
                vertex = polygonsView.getVertexs();
                if (vertex == 3) {
                    break;
                }
                polygonsView.setVertexs(vertex - 1);
                tvvertex.setText(vertex - 1 + "");
                vertexTextAdapter.removeData(vertex);
                break;
            //增加多边形个数
            case R.id.btn_count_add:
                int count = polygonsView.getPolygonCount();
                polygonsView.setPolygonCount(count + 1);
                polygonsAdapter.addData(count);
                rv_polygons.scrollToPosition(count);
                tvpolygoncount.setText(count + 1 + "");
                break;
            //减少多边形个数
            case R.id.btn_count_sub:
                count = polygonsView.getPolygonCount();
                if (count == 1) {
                    break;
                }
                polygonsView.setPolygonCount(count - 1);
                polygonsAdapter.removeData(count);
                tvpolygoncount.setText(count - 1 + "");
                break;
            //改变进度线颜色
            case R.id.btn_progress_color:
                polygonsView.setProgressLineColor(colors[new Random().nextInt(colors.length)]);
                break;
            //增加进度线宽度
            case R.id.btn_progress_width_add:
                float width = polygonsView.getProgressLineWidth() + 4;
                polygonsView.setProgressLineWidth(width);
                tvprogresswidth.setText(width + "");
                break;
            //减小进度线宽度
            case R.id.btn_progress_width_sub:
                width = polygonsView.getProgressLineWidth() - 4;
                if (width < 0) break;
                polygonsView.setProgressLineWidth(width);
                tvprogresswidth.setText(width + "");
                break;
            //进度线是否启用
            case R.id.btn_progress_toggle:
                polygonsView.setProgressLineEnable(!polygonsView.getProgressLineEnable());
                break;
        }
    }
}
