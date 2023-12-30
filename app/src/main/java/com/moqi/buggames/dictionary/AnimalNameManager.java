package com.moqi.buggames.dictionary;

import android.content.Context;
import android.content.SharedPreferences;

public class AnimalNameManager {
    private static final String PREF_NAME = "AnimalNamePrefs";
    private static final String KEY_ANIMAL_NAME = "animalName";

    private static SharedPreferences getSharedPreferences(Context context){
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static void saveAnimalName(Context context, String animalName){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(KEY_ANIMAL_NAME, animalName);
        editor.apply();
    }

    public static String getAnimalName(Context context){
        return getSharedPreferences(context).getString(KEY_ANIMAL_NAME,null);
    }
}
