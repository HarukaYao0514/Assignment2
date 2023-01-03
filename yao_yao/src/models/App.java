package models;

import java.util.ArrayList;
import java.util.List;
import utils.Utilities;

public abstract class App {
    //--------
    // fields
    //--------
    protected Developer developer;
    protected String appName = "No app name";
    protected double appSize = 0;
    protected double appVersion = 1.0;
    protected double appCost = 0.0;
    private List<Rating> ratings = new ArrayList<>();


    //-------------
    // constructor
    //-------------
    public App (Developer developer,String appName,double appSize,double appVersion,double appCost){
        this.developer = developer;
        this.appName = appName;
        if (Utilities.validRange(appSize, 1, 1000)) {
            this.appSize = appSize;
        }
        if (Utilities.greaterThanOrEqualTo(appVersion, 1.0)) {
            this.appVersion = appVersion;
        }
        if (Utilities.greaterThanOrEqualTo(appCost, 0)) {
            this.appCost = appCost;
        }
    }

    //---------
    // getters
    //---------
    public double getAppCost() {return appCost;}

    public String getAppName() {
        return appName;
    }

    public double getAppSize() {
        return appSize;
    }

    public double getAppVersion() {
        return appVersion;
    }

    public Developer getDeveloper() {
        return developer;
    }

    public abstract boolean isRecommendedApp();

    public List<Rating> getRatings() {
        return ratings;
    }

    public String appSummary() {
        return  appName + "(V" + appVersion +") by " + developer + ", €" + appCost + ". Rating: " + calculateRating();
    }

    public boolean addRating(Rating rating){
        return ratings.add(rating);
    }

    public String listRatings(){
        if (ratings.isEmpty()) {
            return "\tNo ratings added yet";
        }

        String str = "";
        for (int i = 0; i < ratings.size(); i++) {
            str += "\t" + i + ": " + ratings.get(i) + "\n";
        }
        return str;
    }

    public double calculateRating(){
        if (ratings.isEmpty()) {
            return 0;
        }
        int count = 0;
        int sum = 0;
        for (int i = 0; i < ratings.size(); i++) {
            if (ratings.get(i).getNumberOfStars() == 0) {
                continue;
            }
            count++;
            sum += ratings.get(i).getNumberOfStars();
        }

        if (count == 0) {
            return 0;
        }
        return sum * 1.0 / count;
    }

    //---------
    // setters
    //---------

    public void setAppCost(double appCost) {
        if (Utilities.greaterThanOrEqualTo(appCost, 0)) {
            this.appCost = appCost;
        }
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setAppSize(double appSize) {
        if (Utilities.validRange(appSize, 1, 1000)){
            this.appSize = appSize;
        }
    }

    public void setAppVersion(double appVersion) {
        if (Utilities.greaterThanOrEqualTo(appVersion, 1.0)) {
            this.appVersion = appVersion;
        }
    }

    public void setDeveloper(Developer developer) {
        this.developer = developer;
    }


    @Override
    public String toString() {
        String str = appName + "(Version " + appVersion + ") by " + developer + ", " + appSize + "MB, Cost: " + appCost + ". ";
        str += "Ratings (" + calculateRating() + ")";
        return str;
    }
}
