package com.cczu.lcy.cloudplatform;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;


public class Show extends FragmentActivity implements OnChartGestureListener, OnChartValueSelectedListener {


    private LineChart mChart;
    private TextView tvX, tvY;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);


        // 设置表格的一些属性
        mChart = (LineChart) findViewById(R.id.chart1);
        // 在图表执行动作时，为定制回调设置一个动作监听器。
        mChart.setOnChartGestureListener(this);
        // 为图表设置一个既定的监听器
        mChart.setOnChartValueSelectedListener(this);
        // 把这个设为true来绘制网格背景，如果false则不绘制
        mChart.setDrawGridBackground(false);

        // 把这个设置为false，禁用所有手势和图表上的触摸，默认：true
        mChart.setTouchEnabled(false);

        // 设置图标拖动为允许
        mChart.setDragEnabled(true);
        // 设置图表缩放为允许
        mChart.setScaleEnabled(true);
        mChart.setScaleXEnabled(true);
        mChart.setScaleYEnabled(true);

        mChart.setDescription("");

        // 挤压缩放设置为允许，即X轴和Y轴会成比例缩放，如果设置为false，则变成单独缩放
        mChart.setPinchZoom(true);

        // 返回代表所有x标签的对象，这个方法可以用来获得XAxis对象并修改它（例如改变标签的位置、样式等）
        XAxis xAxis = mChart.getXAxis();
        // 返回左y轴对象。在水平的柱状图中，这是最上面的轴。
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.removeAllLimitLines();

        // 使网格线在虚线模式下绘制，例如像这个“------”。只有在硬件加速被关闭的情况下才会起作用。记住，硬件加速会提高性能。
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        // 将其设置为true，无论是否启用其他网格线，都要画出零线。默认值:假
        leftAxis.setDrawZeroLine(false);

        // limit lines are drawn behind data (and not on top)
        // 如果这被设置为true，那么界限就会被绘制在实际的数据后面，否则就在上面。默认值:假
        leftAxis.setDrawLimitLinesBehindData(true);

        mChart.getAxisRight().setEnabled(false);
        mChart.getAxisLeft().setEnabled(false);
        mChart.getXAxis().setEnabled(false);

        // add data
        // 这是自己设定的添加数据的方法，count设置了数据的个数，range设置了波动范围
        setData(45, 100);

        // 调用动画对图表显示进行处理
        mChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);

        // get the legend (only possible after setting data)
        // 返回图表的图例对象。这个方法可以用来获得图例的实例，以便定制自动生成的图例。
        Legend l = mChart.getLegend();
        l.setForm(Legend.LegendForm.LINE);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    // 这个应该是设置数据的函数了
    private void setData(int count, float range) {

        // 这个应该就是x轴的数据了
        ArrayList<String> xVals = new ArrayList<String>();
        // 从 0 到 count设置x轴的数据
        for (int i = 0; i < count; i++) {
            xVals.add((i) + "");
        }
        // 这个是y轴的数据
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        // 设置y轴的数据，在这里，是用random函数来生成的
        for (int i = 0; i < count; i++) {

            float mult = (range + 1);
            float val = (float) (Math.random() * mult) + 3;// + (float)
            // ((mult *
            // 0.1) / 10);
            yVals.add(new Entry(val, i));
        }
        // MPAC自定义的一种类
        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals, "BVP - WAVE");
        // set1.setFillAlpha(110);
        // set1.setFillColor(Color.RED);

        // set the line to be drawn like this "- - - - - -"
        // 下面是设置线的各种属性
        // 允许在虚线模式下画出线，例如像这个“------”。只有在硬件加速被关闭的情况下才会起作用。记住，硬件加速会提高性能。
        set1.enableDashedLine(10f, 5f, 0f);
        // 允许在虚线模式下画出高光线，例如，像这样“------”
        set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.setColor(Color.RED);
        set1.setCircleColor(Color.RED);
        set1.setLineWidth(2f);
        set1.setCircleRadius(0f);
        // 把这个设置为true，允许在每个数据圆上画一个洞。
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(0f);
        //

        set1.setDrawFilled(false);


        if(Utils.getSDKInt() >= 18) {
            // fill drawable only supported on api level 18 and above
            //Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);
            //set1.setFillDrawable(drawable);
            set1.setFillColor(Color.BLUE);
        } else {
            set1.setFillColor(Color.BLACK);
        }

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(xVals,dataSets);

        // set data
        mChart.setData(data);
    }

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "START, x: " + me.getX() + ", y: " + me.getY());
    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "END, lastGesture: " + lastPerformedGesture);

        // un-highlight values after the gesture is finished and no single-tap
        if(lastPerformedGesture != ChartTouchListener.ChartGesture.SINGLE_TAP)
            mChart.highlightValues(null); // or highlightTouch(null) for callback to onNothingSelected(...)
    }

    @Override
    public void onChartLongPressed(MotionEvent me) {
        Log.i("LongPress", "Chart longpressed.");
    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {
        Log.i("DoubleTap", "Chart double-tapped.");
    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
        Log.i("SingleTap", "Chart single-tapped.");
    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
        Log.i("Fling", "Chart flinged. VeloX: " + velocityX + ", VeloY: " + velocityY);
    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        Log.i("Scale / Zoom", "ScaleX: " + scaleX + ", ScaleY: " + scaleY);
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
        Log.i("Translate / Move", "dX: " + dX + ", dY: " + dY);
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        Log.i("Entry selected", e.toString());
        Log.i("LOWHIGH", "low: " + mChart.getLowestVisibleXIndex() + ", high: " + mChart.getHighestVisibleXIndex());
        Log.i("MIN MAX", "xmin: " + mChart.getXChartMin() + ", xmax: " + mChart.getXChartMax() + ", ymin: " + mChart.getYChartMin() + ", ymax: " + mChart.getYChartMax());
    }

    @Override
    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
    }

}
