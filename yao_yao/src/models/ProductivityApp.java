package models;

import utils.Utilities;

public class ProductivityApp extends App {

    //-------------
    // constructor
    //-------------
    public ProductivityApp (Developer developer,String appName,double appSize,double appVersion,double appCost){
        super(developer, appName, appSize, appVersion, appCost);
    }

    //---------
    // getters
    //---------
    public boolean isRecommendedApp() {
        if (appCost >= 1.99 && calculateRating() > 3.0) {
            return true;
        }
        return false;
    }

    public String toString() {
        String str = getAppName() + "(Version " + appVersion + ") by " + developer + ", " + appSize + "MB, Cost: " + appCost + ". ";
        str += "Ratings (" + calculateRating() + "): \n";
        str += listRatings();
        return str;
    }
}
