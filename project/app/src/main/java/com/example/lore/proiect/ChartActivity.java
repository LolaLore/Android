package com.example.lore.proiect;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class ChartActivity extends AppCompatActivity {

    Button back;



    private int[] yData = {25, 10,4,6,7};
    private String[] xData = {"Ford Mustang", "Ford Shelby", "Lexus", "Chevrollete", "Camaro"};

    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChartActivity.this, UserActivity.class));
            }
        });

        pieChart = (PieChart) findViewById(R.id.idPieChart);

        // pieChart.setDescription("Movies by genre");

        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(20f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("Type of cras");
        pieChart.setCenterTextSize(10);
        pieChart.setDrawEntryLabels(true);
        addDataSet();
    }

    private void addDataSet() {
        ArrayList<PieEntry> yEntries = new ArrayList<>();
        ArrayList<String> xEntries = new ArrayList<>();
        ArrayList<LegendEntry> legendEntries = new ArrayList<>();

        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntries, "Cars's types");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        //add colors to data set
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.BLUE);
        colors.add(Color.CYAN);
        colors.add(Color.YELLOW);
        colors.add(Color.GRAY);
        colors.add(Color.RED);

        pieDataSet.setColors(colors);

        for (int i = 0; i < yData.length; i++) {
            yEntries.add(new PieEntry(yData[i], i));
        }
        DashPathEffect effect = new DashPathEffect(new float[] { 5, 5, 5, 5, 5 }, 3);
        for (int i = 0; i < xData.length; i++) {
            xEntries.add(xData[i]);
            legendEntries.add(new LegendEntry(xData[i], Legend.LegendForm.SQUARE, 10f,10f, effect, colors.get(i)));
        }

        //add legend to chart
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART_CENTER);
        legend.setEnabled(true);
        legend.setCustom(legendEntries);
        legend.setTextSize(15f);

        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }
}