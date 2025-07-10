package com.example.stationdechange.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class YearMonthDayArrayToDateDeserializer extends JsonDeserializer<Date> {
    @Override
    public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        int[] arr = p.readValueAs(int[].class);
        if (arr.length == 3) {
            Calendar cal = Calendar.getInstance();
            cal.set(arr[0], arr[1] - 1, arr[2], 0, 0, 0);
            cal.set(Calendar.MILLISECOND, 0);
            return cal.getTime();
        }
        return null;
    }
} 