# PolygonsView
可自定义的多边形雷达图

## 一、前言

看了这篇[仿掌上英雄联盟能力值分析效果](http://blog.csdn.net/as7210636/article/details/52692102)受到了一些启发，觉得该文对于雷达图的绘制写得非常详细。但同时又感觉该雷达图的灵活性偏低，于是打算自己封装一个，于是就有了这个PolygonsView。废话不多说，先上几张图。
<img src="http://img.blog.csdn.net/20161130103128709" width="270" height="480"/> <img src="http://img.blog.csdn.net/20161130104135964" width="270" height="480"/> <img src="http://img.blog.csdn.net/20161130104313621" width="270" height="480"/> 
<img src="http://img.blog.csdn.net/20161130104429908" width="270" height="480"/>
####**使用方式**
直接拿来撸：
```
compile 'com.chauncey.view:polygonsview:1.0.1'
```
下载源码：

```
https://github.com/Chauncey93/PolygonsView
```

对了，别忘了在布局文件中添加：

```
    <com.chauncey.view.PolygonsView
        android:id="@+id/PolygonsView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"/>
```

##二、PolygonsView的使用方法

方法名     | 注释| 返回值
-------- | ---| ---
setDiagonalsLineColor(int color) | 设置对角线颜色 | void
getDiagonalsLineColor()    | 获取对角线颜色值| int
setDiagonalsLineEnable(boolean b) | 设置对角线是否启用 | void
getDiagonalsLineEnable()    | 获取对角线启用状态（默认启用）| boolean 
setProgress(int index, int value)    | 设置对应顶点进度值| void
getProgress(int index)     | 获取指定顶点进度值（默认50）| int
setProgressLineWidth(float width)     | 设置进度值线宽度| void
getProgressLineWidth()     | 获取进度值线宽度值| float
setProgressLineColor(int color)     | 设置进度值线颜色| void
getProgressLineColor()     | 获取进度值线颜色值| int
setProgressLineEnable(boolean b)     | 设置进度值线是否启用| void
getProgressLineEnable()     | 获取进度值线启用状态（默认启用）| boolean 
setVertexs(int vertexs)     | 设置多边形顶点个数（边数）|void
getVertexs()     | 获取多边形顶点个数（边数）（默认为5）| int
setPolygonCount(int count)     | 设置嵌套的多边形个数| void
getPolygonCount()     |  获取嵌套的多边形个数（默认为3）| int
setPolygonStyle(int index, Paint.Style style)     | 设置多边形风格（空心、实心）| void
getPolygonStyle()     | 获取多边形风格（空心、实心）| Paint.Style
setPolygonColor(int index, int color)     | 设置多边形颜色| void
getPolygonColor(int index)     | 获取多边形颜色| int
setVertexText(int index, String text)     | 设置顶点文本| void
getVertexText()    | 获取顶点文本| String
setVertexTextColor(int index, int color)     | 设置顶点文本颜色| void
getVertexTextColor(int index)    | 获取顶点文本颜色| int
setVertexTextSize(int index, float size)     | 设置顶点文本尺寸| void
getVertexTextSize(int index)     | 获取顶点文本尺寸| float

##三、源码解析

```
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
		//defalutSize为200，单位dp
		//当高/宽度为wrap_cpntent时，则尺寸为默认值
        width = widthMode == MeasureSpec.AT_MOST ? defalutSize : width;
        height = heightMode == MeasureSpec.AT_MOST ? defalutSize : height;

        //以最小长度为边，确保形状是正方形
        width = height = Math.min(width, height);
        mCenter = width / 2;
        //必须调用，否则绘制无效
        setMeasuredDimension(width, height);
    }
```

```
  /**
     * 绘制对角线
     *
     * @param canvas 画布
     */
    private void onDrawDiagonalsLine(Canvas canvas) {
	    //先判断是否启用
        if (!mDiagonalsLineEnable) {
            return;
        }
        canvas.save();
        float degree = getDegree();
        //旋转画布，并留出1/4的空间放置文字，有几个顶点就旋转几次
        for (int i = 0; i < mVertex; i++) {
            canvas.rotate(degree, mCenter, mCenter);//画布把后两个参数做为原点，旋转degree度
            canvas.drawLine(mCenter, mCenter, mCenter, (float) (0.25 * mCenter), mDiagonalsLinePaint);
        }
        canvas.restore();
    }
```
关于getDegree()请看

```
 /**
     * 获取角度值
     *
     * @return 角度值
     */
    private float getDegree() {

        //mVertex为顶点数，7无法被360整除，会导致畸形，所以要加上0.5
        if (mVertex == 7) {
            return (float) (360 / mVertex + 0.5);
        } else {
            return (float) (360 / mVertex);
        }
    }
```

```
 /**
     * 绘制边
     *
     * @param canvas     画布
     * @param diagonalsLineLength 对角线长，第一次调用时传入mCenter
     * @param count      剩余所需绘制个数
     */


    private void onDrawEdges(Canvas canvas, float diagonalsLineLength, int count) {
        float degree = getDegree();
		//
        Path path = new Path();
        //每次递归都按比例缩小1/4
        path.moveTo(mCenter, mCenter - (float) 0.75 * diagonalsLineLength);
        for (int i = 1; i < mVertex; i++) {
            path.lineTo((float) (mCenter + 0.75 * diagonalsLineLength* Math.sin(Math.toRadians(degree * (i)))), (float) (mCenter - 0.75 * diagonalsLineLength* Math.cos(Math.toRadians(degree * (i)))));
        }
        path.close();
        canvas.drawPath(path, mPolygonPaintList.get(mCount - count));
        count--;
        //递归循环嵌套多个多边形
        if (count != 0) {
        //mCount是总的多边形个数
            onDrawEdges(canvas, mCenter / mCount * count, count);

        }
    }
```
中间的for循环画边是不是一脸懵逼？三角函数没忘吧![三角函数](http://img.blog.csdn.net/20161128151313600)，来张图你就懂了

![绘制边](http://img.blog.csdn.net/20161128151024862)
同理，有多少边（顶点）就循环画（顶点数-1）次。

```
  /**
     * 绘制进度线
     *
     * @param canvas 画布
     */
    private void onDrawProgressLine(Canvas canvas) {
        if (!mProgressLineEnable) {
            return;
        }
        canvas.save();
        float degree = getDegree();
        Path path = new Path();

        path.moveTo(mCenter, mCenter - (float) (0.75 * mProgressLinePercent.get(0)) * mCenter);
        //mProgressLinePercent是存放各个顶点进度值的集合
        for (int i = 1; i < mVertex; i++) {
            path.lineTo((float) (mCenter + (0.75 * mProgressLinePercent.get(i)) * mCenter * Math.sin(Math.toRadians(degree * (i)))), (float) (mCenter - (0.75 * mProgressLinePercent.get(i)) * mCenter * Math.cos(Math.toRadians(degree * (i)))));
        }
        path.close();
        canvas.drawPath(path, mProgressLinePaint);
        canvas.restore();
    }
```
与绘制边同理，只不过加入了一个百分比值而已，是不是so easy。

```

    /**
     * 绘制顶点文本
     * 
     * @param canvas
	 */
    private void onDrawText(Canvas canvas) {
        float degree = getDegree();
        for (int i = 0; i < mVertex; i++) {
            canvas.drawText(mTextList.get(i), (float) (mCenter - mTextPaintList.get(i).measureText(mTextList.get(i)) / 2 + mCenter * 0.88 * Math.sin(Math.toRadians(degree * (i)))), (float) (mCenter * 1.05 - mCenter * 0.85 * Math.cos(Math.toRadians(degree * (i)))), mTextPaintList.get(i));
        }
```
为了将文本和顶点对齐，所以要测量文本的物理长度，取其一半长度进行放置。

最后，奉上源码[PolygonsView](https://github.com/Chauncey93/PolygonsView)，如果你觉得对你有帮助，记得给我点个star哟。





