package com.zzlbe.core.util;

import java.text.DecimalFormat;

public class NumberFormat {
    /*
     * 如果是小数，保留两位，非小数，保留整数
     * @param number
     */
    public String DoubleToString(double number) {
        DecimalFormat df = new DecimalFormat("######0.00");
        return df.format(number);
    }

    public double StringToDouble(String number) {
        return Double.valueOf(number.toString());
    }

    public int StringToInt(String number) {
        return Integer.parseInt(number);
    }

    public String IntToString(int number) {
        return number+"";
    }
}
