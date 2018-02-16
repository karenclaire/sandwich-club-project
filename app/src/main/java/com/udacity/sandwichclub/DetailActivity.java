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
    private TextView origin;

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
        TextView alsoKnownAs = findViewById(R.id.also_known_tv);
        TextView origin = findViewById(R.id.origin_tv);
        TextView description = findViewById(R.id.description_tv);
        TextView ingredients = findViewById(R.id.ingredients_tv);


        //Set text for place of origin
        if (sandwich.getPlaceOfOrigin().isEmpty()) {
            //If there's no information to show
            origin.setText(R.string.no_data);
        } else {
            origin.setText(sandwich.getPlaceOfOrigin());
        }

        // Set text for description
        if (sandwich.getDescription().isEmpty()) {
            //If there's no information to show
            description.setText(R.string.no_data);
        } else {
            description.setText(sandwich.getDescription());
        }

        //Set text for also known as
        List<String> alsoKnown = sandwich.getAlsoKnownAs();

        if (alsoKnown.isEmpty()) {
            //If there's no information to show
            alsoKnownAs.setText(R.string.no_data);

        } else {
            String resultForAKA = "";
            for (String s : alsoKnown) {
                resultForAKA += s + ", ";
            }

            if (resultForAKA.length() > 0) {
                resultForAKA = resultForAKA.substring(0, resultForAKA.length() - 2);
            }
                alsoKnownAs.setText(resultForAKA);

                //alsoKnownAs.setText(listMaker(sandwich.getAlsoKnownAs()));
            }

        // Set text for ingredients
        List<String> ingredientList = sandwich.getIngredients();
        if (ingredientList.isEmpty()) {
            //If there's no info to show
            ingredients.setText(R.string.no_data);
        } else {
            String resultForIngredients = "";
            for (String s : ingredientList)
                resultForIngredients += s + "\n";
            if (resultForIngredients.length() > 0) {
                resultForIngredients = resultForIngredients.substring(0, resultForIngredients.length() - 2);
            }
            ingredients.setText(resultForIngredients);
        }
        //ingredients.setText(listMaker(sandwich.getIngredients()));

        }


    //public StringBuilder listMaker(List<String> list){
    //   StringBuilder stringBuilder= new StringBuilder();
    //    for (int i=0;i<list.size();i++){
    //       stringBuilder.append(list.get(i)).append("\n");
    //   }
    //return stringBuilder;



    }




