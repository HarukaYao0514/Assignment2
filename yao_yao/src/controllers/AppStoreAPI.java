package controllers;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import models.*;
import utils.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;//
import java.util.List;//
import java.util.Random;

import static java.lang.Math.random;
import static utils.RatingUtility.generateRandomRating;

public class AppStoreAPI implements ISerializer {

    private List<App> apps = new ArrayList<>();


    public boolean addApp(App app) {
        return apps.add(app);
    }

    public String listAllApps() {
        if (apps.isEmpty()) {
            return "No apps";
        }
        String listAllApps = "";
        for (int i = 0; i < apps.size(); i++) {
            App app = apps.get(i);
            listAllApps += i + ": " + app.toString() + "\n";
        }
        return listAllApps;
    }

    public String listAllGameApps() {
        String listGameApps = "";
        for (int i = 0; i < apps.size(); i++) {
            App app = apps.get(i);
            if (app instanceof GameApp) {
                listGameApps += i + ": " + app.toString() + "\n";
            }
        }
        if (listGameApps.length() == 0) {
            return "No Game apps";
        }
        return listGameApps;
    }

    public String listAllEducationApps() {
        String listEducationApps = "";
        for (int i = 0; i < apps.size(); i++) {
            App app = apps.get(i);
            if (app instanceof EducationApp) {
                listEducationApps += i + ": " + app.toString() + "\n";
            }
        }
        if (listEducationApps.length() == 0) {
            return "No Education apps";
        }
        return listEducationApps;
    }

    public String listAllProductivityApps() {
        String listProductivityApps = "";
        for (int i = 0; i < apps.size(); i++) {
            App app = apps.get(i);
            if (app instanceof ProductivityApp) {
                listProductivityApps += i + ": " + app.toString() + "\n";
            }
        }
        if (listProductivityApps.length() == 0) {
            return "No Productivity apps";
        }
        return listProductivityApps;
    }

    public String listAllRecommendedApps() {
        String listRecommendedApps = "";
        for (int i = 0; i < apps.size(); i++) {
            App app = apps.get(i);
            if (app.isRecommendedApp()) {
                listRecommendedApps += i + ": " + app.toString() + "\n";
            }
        }

        if (listRecommendedApps.length() == 0) {
            return "No recommended apps";
        }
        return listRecommendedApps;
    }

    public String listSummaryOfAllApps(){
        if (apps.isEmpty()) {
            return "No apps";
        }
        String listSummaryOfApps = "";
        for (int i = 0; i < apps.size(); i++) {
            App app = apps.get(i);
            listSummaryOfApps += i + ": " + app.appSummary() + "\n";
        }
        return listSummaryOfApps;
    }

    public String listAllAppsByName(String appName) {
        String listAppsByName = "";
        for (int i = 0; i < apps.size(); i++) {
            App app = apps.get(i);
            if (app.getAppName().equalsIgnoreCase(appName)) {
                listAppsByName += i + ": " + app.toString() + "\n";
            }
        }

        if (listAppsByName.length() == 0) {
            return "No apps for name \"" + appName + "\" exists";
        }
        return listAppsByName;

    }

    public String listAllAppsByChosenDeveloper(Developer developer) {
        String listAppsByDeveloper = "";
        for (int i = 0; i < apps.size(); i++) {
            App app = apps.get(i);
            if (app.getDeveloper().equals(developer)) {
                listAppsByDeveloper += i + ": " + app.toString() + "\n";
            }
        }

        if (listAppsByDeveloper.length() == 0) {
            return "No apps for developer: " + developer;
        }
        return listAppsByDeveloper;
    }

    public int numberOfAppsByChosenDeveloper(Developer developer) {
        int count = 0;
        for (int i = 0; i < apps.size(); i++) {
            App app = apps.get(i);
            if (app.getDeveloper().equals(developer)) {
                count++;
            }
        }

        return count;
    }

    public String listAllAppsAboveOrEqualAGivenStarRating(int rating) {
        String str = "";
        for (int i = 0; i < apps.size(); i++) {
            App app = apps.get(i);
            if (Utilities.greaterThanOrEqualTo(app.calculateRating(), rating)) {
                str += i + ": " + app.toString() + "\n";
            }
        }

        if (str.length() == 0) {
            return "No apps have a rating of " + rating + " or above";
        }
        return str;
    }

    public boolean updateAppByIndex(int index, App app) {
        if (!Utilities.isValidIndex(apps, index)) {
            return false;
        }

        apps.set(index, app);
        return true;
    }

    public App deleteAppsByIndex(int index) {
        if (!Utilities.isValidIndex(apps, index)) {
            return null;
        }

        App app = apps.get(index);
        apps.remove(index);
        return app;
    }

    public App randomApp(){
        if (apps.isEmpty()) {
            return null;
        }
        Random random = new Random();
        int index = random.nextInt(apps.size());
        return apps.get(index);
    }

    public void simulateRatings() {
        for (App app : apps) {
            app.addRating(generateRandomRating());
        }
    }

    public boolean isValidAppName(String appName) {
        for (App app : apps){
            if (app.getAppName().equalsIgnoreCase(appName)){
                return true;
            }
        }
        return false;
    }

    public App getAppByName(String appName) {
        for (App app: apps) {
            if (app.getAppName().equalsIgnoreCase(appName)) {
                return app;
            }
        }
        return null;
    }

    public App getAppByIndex(int index) {
        if (Utilities.isValidIndex(apps, index)) {
            return apps.get(index);
        } else {
            return null;
        }
    }

    public boolean isValidIndex(int index) {return (index >= 0) && (index < apps.size());}


    public void sortAppsByNameAscending() {
        for (int i = apps.size() -1; i >= 0; i--)
        {
            int highestIndex = 0;
            for (int j = 0; j <= i; j++)
            {
                if (apps.get(j).getAppName().compareTo(apps.get(highestIndex).getAppName()) > 0) {
                    highestIndex = j;
                }
            }
            swapApps(apps, i, highestIndex);
        }
    }

    private void swapApps(List<App> apps, int i, int j) {
        App smaller = apps.get(i);
        App bigger = apps.get(j);

        apps.set(i, bigger);
        apps.set(j, smaller);
    }

    public int numberOfApps() {return apps.size();}

    public void load() throws Exception {
        //list of classes that you wish to include in the serialisation, separated by a comma
        Class<?>[] classes = new Class[]{App.class, EducationApp.class, GameApp.class, ProductivityApp.class, Rating.class};

        //setting up the xstream object with default security and the above classes
        XStream xstream = new XStream(new DomDriver());
        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(classes);

        //doing the actual serialisation to an XML file
        ObjectInputStream in = xstream.createObjectInputStream(new FileReader(fileName()));
        apps = (List<App>) in.readObject();
        in.close();
    }

    public void save() throws Exception {
        XStream xstream = new XStream(new DomDriver());
        ObjectOutputStream out = xstream.createObjectOutputStream(new FileWriter(fileName()));
        out.writeObject(apps);
        out.close();
    }

    public String fileName() {return "apps.xml";}

}