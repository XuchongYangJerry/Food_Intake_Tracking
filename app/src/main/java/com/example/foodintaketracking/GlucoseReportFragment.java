package com.example.foodintaketracking;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.foodintaketracking.databinding.FragmentGlucoseReportBinding;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class GlucoseReportFragment extends Fragment {
    private FragmentGlucoseReportBinding binding;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGlucoseReportBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.chart.setTouchEnabled(true);
        binding.chart.setPinchZoom(true);
        MyMarkerView mv = new MyMarkerView(getContext(), R.layout.marker_view);
        mv.setChartView(binding.chart);
        binding.chart.setMarker(mv);
        renderData();

        return view;
    }

    public void renderData() {
        LimitLine llXAxis = new LimitLine(10f, "Index 10");
        llXAxis.setLineWidth(4f);
        llXAxis.enableDashedLine(10f, 10f, 0f);
        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        llXAxis.setTextSize(15f);

        XAxis xAxis = binding.chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.enableGridDashedLine(2f, 2f, 0f);
        xAxis.setAxisMaximum(17f);
        xAxis.setAxisMinimum(0f);

        xAxis.setDrawLimitLinesBehindData(true);

        LimitLine ll1 = new LimitLine(10f, "High Glucose");
        ll1.setLineWidth(4f);
        ll1.enableDashedLine(10f, 10f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll1.setTextSize(12f);

        LimitLine ll2 = new LimitLine(5f, "Low Glucose");
        ll2.setLineWidth(4f);
        ll2.enableDashedLine(10f, 10f, 0f);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll2.setTextSize(12f);

        YAxis leftAxis = binding.chart.getAxisLeft();
        leftAxis.removeAllLimitLines();
        leftAxis.addLimitLine(ll1);
        leftAxis.addLimitLine(ll2);
        leftAxis.setAxisMaximum(12f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.enableGridDashedLine(2f, 2f, 0f);
        leftAxis.setDrawZeroLine(true);
        leftAxis.setDrawLimitLinesBehindData(false);

        binding.chart.getAxisRight().setEnabled(false);
        setData();
    }


    private void setData() {
        ArrayList<Entry> values = new ArrayList<>();

        values.add(new Entry(0, 6.4f));
        values.add(new Entry(1, 6.4f));
        values.add(new Entry(2, 6.1f));
        values.add(new Entry(3, 6.6f));
        values.add(new Entry(4, 6.8f));
        values.add(new Entry(5, 6.8f));
        values.add(new Entry(6, 6.8f));
        values.add(new Entry(7, 6.9f));
        values.add(new Entry(8, 7.2f));
        values.add(new Entry(9, 7.4f));
        values.add(new Entry(10, 7.3f));
        values.add(new Entry(11, 7.2f));
        values.add(new Entry(12, 7.1f));
        values.add(new Entry(13, 6.8f));
        values.add(new Entry(14, 6.9f));
        values.add(new Entry(15, 7.4f));
        values.add(new Entry(16, 7.4f));
        values.add(new Entry(17, 7.3f));

        LineDataSet set1;
        if (binding.chart.getData() != null &&
                binding.chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) binding.chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            binding.chart.getData().notifyDataChanged();
            binding.chart.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(values, "Time (15 min)");
            set1.setDrawIcons(false);
            set1.enableDashedLine(10f, 5f, 0f);
            set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setColor(Color.CYAN);
            set1.setCircleColor(Color.CYAN);
            set1.setLineWidth(2f);
            set1.setCircleRadius(5f);
            set1.setDrawCircleHole(true);
            set1.setDrawValues(false);
            set1.setValueTextSize(12f);
            set1.setDrawFilled(true);
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);

            if (Utils.getSDKInt() >= 18) {
                Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.fade_blue);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(Color.DKGRAY);
            }
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            LineData data = new LineData(dataSets);
            binding.chart.setData(data);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}