package com.example.jedepaepe.exercice1;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jedepaepe on 5/02/2018.
 */

public class RestaurantManager {
    List<String> getRestaurants(String type) {
        /* not usefull in this case
        String[] typeList = getResources().getStringArray(R.array.restaurant_list);
        */
        List<String> result = new ArrayList<>();
        switch(type) {
            case "Cuisine Française":
                result.add("L'auberge du Roi");
                result.add("Homard est cuit");
                result.add("Le coq chante");
                break;
            case "Indien":
                result.add("Poulet curry");
                break;
            case "Chinois":
                result.add("Boeuf aigre-doux");
                break;
            default:
                result.add("Type " + type +  "not found");
        }
        return result;
    }

    List<String> getRestaurantsFromRessource(String type) {
        List<String> typeList = R.string.restaurant_style;
        if(typeList.contains(type)) {
            int index = typeList.indexOf(type);
            string id = "restaurant_" + index;
            return new ArrayList<String>();     // TODO
        } else {
            return new ArrayList<String>();
        }
    }
}
