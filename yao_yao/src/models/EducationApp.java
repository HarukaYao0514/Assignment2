package models;

import utils.Utilities;

public class EducationApp extends App {

  //--------
  // fields
  //--------
  private int level = 0;

  //-------------
  // constructor
  //-------------
  public EducationApp(Developer developer,String appName,double appSize,double appVersion,double appCost,int level){
    super(developer, appName, appSize, appVersion, appCost);
    if (Utilities.validRange(level, 1, 10)) {
      this.level = level;
    }
  }

  //---------
  // getters
  //---------
  public int getLevel() { return level; }

  public boolean isRecommendedApp() {
    if (appCost > 0.99 && calculateRating() >= 3.5 && level >= 3) {
      return true;
    }
    return false;
  }

  public String appSummary() {
    return super.appSummary() + ", level " + level;
  }

  //---------
  // setters
  //---------
  public void setLevel(int level) {
    if (Utilities.validRange(level,1,10)) {
      this.level = level;
    }
  }

  public String toString() {
    String str = getAppName() + "(Version " + appVersion + ") by " + developer + ", " + appSize + "MB, Cost: " + appCost + ". Level: " + level;
    str += ", Ratings (" + calculateRating() + "): \n";
    str += listRatings();
    return str;
  }
}
