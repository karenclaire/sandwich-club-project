package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.

        try {
            // Create a JSONObject from the JSON response string
            JSONObject jsonObject = new JSONObject(json);
            JSONObject name = jsonObject.getJSONObject("name");

            //Extract Sandwich Name
            String mainName = name.optString("mainName");

            // Extract the JSONArray associated with "also known as,"
            // which represents a list of alternative names of a sandwich
            JSONArray alsoKnownAs = name.getJSONArray("alsoKnownAs");
            ArrayList<String> alsoKnownAsList = new ArrayList<>();
            for (int i = 0; i < alsoKnownAs.length(); i++) {
                alsoKnownAsList.add(alsoKnownAs.getString(i));
            }

            // Extract the sandwich's place of origin
            String placeOfOrigin = jsonObject.getString("place_of_origin");

            // Extract the description of Sandwich
            String description = jsonObject.getString("description");

            // Extract image of Sandwich
            String image = jsonObject.getString("image");

            // Extract the JSONArray associated with "ingredients,"
            // which represents a list of sandwich ingredients
            JSONArray ingredients = jsonObject.getJSONArray("ingredients");
            ArrayList<String> ingredientsList = new ArrayList<>();
            for (int i = 0; i < ingredients.length(); i++) {
                ingredientsList.add(ingredients.getString(i));
            }

            //return Sandwich details
            return new Sandwich(mainName, alsoKnownAsList, placeOfOrigin, description, image, ingredientsList);

            // Catch the exception so the app doesn't crash, and print the error message to the logs.
        } catch (JSONException e) {
            Log.e("JsonUtils", "Problem parsing the Sandwich JSON results");
            //e.printStackTrace();
        }
        return null;
    }
}


