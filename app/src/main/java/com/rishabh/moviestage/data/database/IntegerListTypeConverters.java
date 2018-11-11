package com.rishabh.moviestage.data.database;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class IntegerListTypeConverters {

    @TypeConverter
    public static List<Integer> stringToIntegerList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Integer>>() {}.getType();

        return new Gson().fromJson(data, listType);
    }

    @TypeConverter
    public static String integerList(List<Integer> generList) {
        return new Gson().toJson(generList);
    }
}




