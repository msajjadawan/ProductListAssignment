package com.innovidio.androidbootstrap.db.converters;


import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.List;


public class IntegerListConverter {
    @TypeConverter
    public List<Integer> gettingListFromString(String genreIds) {
        List<Integer> list = new ArrayList<>();

        String[] array = genreIds.split(",");

        for (String s : array) {
            if (!s.isEmpty()) {
                list.add(Integer.parseInt(s));
            }
        }
        return list;
    }

    @TypeConverter
    public String writingStringFromList(List<Integer> list) {
        String genreIds = "";
        for (int i : list) {
            genreIds += "," + i;
        }
        return genreIds;
    }
}
