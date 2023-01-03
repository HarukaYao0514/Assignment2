package models;

public class GameApp extends App{

    //--------
    // fields
    //--------
    private boolean isMultiplayer = false;

    //-------------
    // constructor
    //-------------
    public GameApp (Developer developer,String appName,double appSize,double appVersion,double appCost,boolean isMultiplayer){
        super(developer, appName, appSize, appVersion, appCost);
        this.isMultiplayer = isMultiplayer;
    }

    //---------
    // getters
    //---------
    public boolean isMultiplayer(){ return isMultiplayer; }

    public boolean isRecommendedApp() {
        if (isMultiplayer && calculateRating() >= 4.0) {
            return true;
        }
        return false;
    }

    public String appSummary() {
        return super.appSummary() + ", isMultiplayer " + isMultiplayer;
    }

    //---------
    // setters
    //---------
    public void setMultiplayer(boolean multiplayer) {
        this.isMultiplayer = multiplayer;
    }


    public String toString() {
        String str = getAppName() + "(Version " + appVersion + ") by " + developer + ", " + appSize + "MB, Cost: " + appCost + ". IsMultiplayer: " + isMultiplayer;
        str += ", Ratings (" + calculateRating() + "): \n";
        str += listRatings();
        return str;
    }
}
