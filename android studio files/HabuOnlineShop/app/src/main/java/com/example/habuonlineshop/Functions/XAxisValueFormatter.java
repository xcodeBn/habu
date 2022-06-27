package com.example.habuonlineshop.Functions;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

public class XAxisValueFormatter extends ValueFormatter implements IAxisValueFormatter {


    private String[] values;

    public XAxisValueFormatter(String[] values) {
        this.values = values;
    }


    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return this.values[(int) value];
    }
}
