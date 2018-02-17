package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {


        // FindViewByID
        TextView name = findViewById(R.id.name_tv);
        TextView alsoKnownAs = findViewById(R.id.also_known_tv);
        TextView origin = findViewById(R.id.origin_tv);
        TextView description = findViewById(R.id.description_tv);
        TextView ingredients = findViewById(R.id.ingredients_tv);

        name.setText(sandwich.getMainName());

        //Set text for place of origin
        if (sandwich.getPlaceOfOrigin().isEmpty()) {
            //If there's no information to show
            origin.setText(R.string.no_data);
        } else {
            origin.setText(sandwich.getPlaceOfOrigin());
            //origin.setText(noData(sandwich.getPlaceOfOrigin()));
        }

        // Set text for description
        if (sandwich.getDescription().isEmpty()) {
            //If there's no information to show
            description.setText(R.string.no_data);
        } else {
            description.setText(sandwich.getDescription());
            //description.setText(noData(sandwich.getDescription()));
        }

        //Set text for also known as
        List<String> alsoKnown = sandwich.getAlsoKnownAs();

        if (alsoKnown.isEmpty()) {
            //If there's no information to show
            alsoKnownAs.setText(R.string.no_data);

        } else {
            String result = "";
            for (String s : alsoKnown) {
                result += s + ", ";
            }

            if (result.length() > 0) {
                result = result.substring(0, result.length() - 2);
            }
            alsoKnownAs.setText(result);
            //alsoKnownAs.setText(noData(result));
            //alsoKnownAs.setText(listMaker(sandwich.getAlsoKnownAs()));

            result = "";

            // Set text for ingredients
            List<String> listIngredients = sandwich.getIngredients();
            if (listIngredients.isEmpty()) {
                //If there's no info to show
                ingredients.setText(R.string.no_data);

            } else {

                for (String s : listIngredients) {
                    result += s + "\n";

                    //if (resultForIngredients.length() > 0) {
                    //    resultForIngredients = resultForIngredients.substring(0, resultForIngredients.length() - 2);
                }
                ingredients.setText(result);
                //ingredients.setText(noData(result));
                //ingredients.setText(listMaker(sandwich.getIngredients()));

            }
        }
    }



    //private String noData(String s) {
    //    if (s.equals("")) {
    //        return getString(R.string.no_data);
    //    } else {
    //        return s;
    //    }
    //}

    //public StringBuilder listMaker(List<String> list){
    //   StringBuilder stringBuilder= new StringBuilder();
    //    for (int i=0;i<list.size();i++){
    //       stringBuilder.append(list.get(i)).append("\n");
    //    }
    //return stringBuilder;
    //}
}







