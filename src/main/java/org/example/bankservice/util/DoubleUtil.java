package org.example.bankservice.util;

import lombok.experimental.UtilityClass;
import lombok.val;

import java.text.DecimalFormat;

@UtilityClass
public class DoubleUtil {

    public double formatDouble(double number) {
        val df = new DecimalFormat("#.##");
        val formatted = df.format(number);
        return Double.parseDouble(formatted);
    }
}
