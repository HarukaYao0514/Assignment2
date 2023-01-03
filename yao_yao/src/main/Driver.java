package main;

import controllers.AppStoreAPI;
import controllers.DeveloperAPI;
import models.*;
import utils.ScannerInput;
import utils.Utilities;

import java.util.List;

public class Driver {

    private DeveloperAPI developerAPI = new DeveloperAPI();
    private AppStoreAPI appStoreAPI = new AppStoreAPI();

    public static void main(String[] args) {
        new Driver().start();
    }

    public void start() {
        loadAllData();
        runMainMenu();
    }

    private int mainMenu() {
        System.out.println("""
                 -------------App Store------------
                |  1) Developer - Management MENU  |
                |  2) App - Management MENU        |
                |  3) Reports MENU                 |
                |----------------------------------|
                |  4) Search                       |
                |  5) Sort                         |
                |----------------------------------|
                |  6) Recommended Apps             |
                |  7) Random App of the Day        |
                |  8) Simulate ratings             |
                |----------------------------------|
                |  20) Save all                    |
                |  21) Load all                    |
                |----------------------------------|
                |  0) Exit                         |
                 ----------------------------------""");
        return ScannerInput.validNextInt("==>> ");
    }

    private void runMainMenu() {
        int option = mainMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> runDeveloperMenu();
                case 2 -> runAppStoreMenu();
                case 3 -> runReportsMenu();
                case 4 -> searchAppsBySpecificCriteria();
                case 5 -> sortAppsByName();
                case 6 -> printRecommendedApps();
                case 7 -> printRandomApp();
                case 8 -> simulateRatings();
                case 20 -> saveAllData();
                case 21 -> loadAllData();
                default -> System.out.println("Invalid option entered: " + option);
            }
            ScannerInput.validNextLine("\n Press the enter key to continue");
            option = mainMenu();
        }
        exitApp();
    }

    private void exitApp() {
        saveAllData();
        System.out.println("Exiting....");
        System.exit(0);
    }



    //--------------------------------------------------
    //  1) Developer - Management MENU
    //--------------------------------------------------
    private int developerMenu() {
        System.out.println("""
                 -------Developer Menu-------
                |   1) Add a developer       |
                |   2) List developer        |
                |   3) Update developer      |
                |   4) Delete developer      |
                |   0) RETURN to main menu   |
                 ----------------------------""");
        return ScannerInput.validNextInt("==>> ");
    }

    private void runDeveloperMenu() {
        int option = developerMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> addDeveloper();
                case 2 -> System.out.println(developerAPI.listDevelopers());
                case 3 -> updateDeveloper();
                case 4 -> deleteDeveloper();
                default -> System.out.println("Invalid option entered" + option);
            }
            ScannerInput.validNextLine("\n Press the enter key to continue");
            option = developerMenu();
        }
    }

    private void addDeveloper() {
        String developerName = ScannerInput.validNextLine("Please enter the developer name: ");
        String developerWebsite = ScannerInput.validNextLine("Please enter the developer website: ");

        if (developerAPI.addDeveloper(new Developer(developerName, developerWebsite))) {
            System.out.println("Add successful");
        } else {
            System.out.println("Add not successful");
        }
    }

    private void updateDeveloper() {
        System.out.println(developerAPI.listDevelopers());
        Developer developer = readValidDeveloperByName();
        if (developer != null) {
            String developerWebsite = ScannerInput.validNextLine("Please enter new website: ");
            if (developerAPI.updateDeveloperWebsite(developer.getDeveloperName(), developerWebsite))
                System.out.println("Developer Website Updated");
            else
                System.out.println("Developer Website NOT Updated");
        } else
            System.out.println("Developer name is NOT valid");
    }

    private void deleteDeveloper() {
        String developerName = ScannerInput.validNextLine("Please enter the developer's name: ");
        if (developerAPI.removeDeveloper(developerName) != null) {
            System.out.println("Delete successful");
        } else {
            System.out.println("Delete not successful");
        }
    }

    private Developer readValidDeveloperByName() {
        String developerName = ScannerInput.validNextLine("Please enter the developer's name: ");
        if (developerAPI.isValidDeveloper(developerName)) {
            return developerAPI.getDeveloperByName(developerName);
        } else {
            return null;
        }
    }



    //-----------------------------------------------
    //  2) App - Management MENU
    //-----------------------------------------------
    private int appStoreMenu() {
        System.out.println("""
                 -------App Store Menu-------
                |   1) Add an app            |
                |   2) List app              |
                |   3) Update app            |
                |   4) Delete app            |
                |   0) RETURN to main menu   |
                 ----------------------------""");
        return ScannerInput.validNextInt("==>> ");
    }

    private void runAppStoreMenu() {
        int option = appStoreMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> addApp();
                case 2 -> listApp();
                case 3 -> updateApp();
                case 4 -> deleteApp();
                default -> System.out.println("Invalid option entered" + option);
            }
            ScannerInput.validNextLine("\n Press the enter key to continue");
            option = appStoreMenu();
        }
    }

    private void addApp() {
        boolean isAdded = false;

        int option = ScannerInput.validNextInt("""
                    ---------------------------
                    |   1) Add an Education App |
                    |   2) Add a Game App       |
                    |   3) Add a Productivity App|
                    ---------------------------
                    ==>>  """);

        String developerName = ScannerInput.validNextLine("Enter the Developer Name:  ");
        if (!developerAPI.isValidDeveloper(developerName)) {
            System.out.println(developerName + " is not a valid developer");
            return;
        }
        Developer developer = developerAPI.getDeveloperByName(developerName);
        String appName = ScannerInput.validNextLine("Enter the App Name: ");
        if (appName.isEmpty()) {
            appName = "No app name";
        }
        if (appStoreAPI.isValidAppName(appName)) {
            System.out.println(appName + " already exists");
            return;
        }
        double appSize = ScannerInput.validNextDouble("Enter the app Size in MB: ");
        double appVersion = ScannerInput.validNextDouble("Enter the app version: ");
        double appCost = ScannerInput.validNextDouble("Enter the app cost: ");

        switch (option) {
            case 1 -> {
                int level = ScannerInput.validNextInt("Enter the app level: ");
                isAdded = appStoreAPI.addApp(new EducationApp(developer, appName, appSize, appVersion, appCost, level));
            }
            case 2 -> {
                boolean isMultiplayer = Utilities.YNtoBoolean(ScannerInput.validNextChar("isMultiplayer(y/n): "));
                isAdded = appStoreAPI.addApp(new GameApp(developer, appName, appSize, appVersion, appCost, isMultiplayer));
            }
            case 3 -> {
                isAdded = appStoreAPI.addApp(new ProductivityApp(developer, appName, appSize, appVersion, appCost));
            }
            default -> System.out.println("Invalid option entered: " + option);
        }

        if (isAdded){
            System.out.println("App Added Successfully");
        }
        else{
            System.out.println("No App Added");
        }
    }

    private void listApp() {
        String output = appStoreAPI.listAllApps();
        System.out.println(output);
    }

    private void updateApp() {
        if (appStoreAPI.numberOfApps() == 0) {
            System.out.println("no apps added yet.");
            return;
        }

        boolean isUpdated = false;
        int index = ScannerInput.validNextInt("Enter the index of the app to update ==> ");
        if (!appStoreAPI.isValidIndex(index)) {
            System.out.println("index not valid.");
            return;
        }
        App app = appStoreAPI.getAppByIndex(index);

        String developerName = ScannerInput.validNextLine("Enter the Developer Name(" + app.getDeveloper().getDeveloperName() + "):  ");
        if (!developerAPI.isValidDeveloper(developerName)) {
            System.out.println(developerName + " is not a valid developer");
            return;
        }
        Developer developer = developerAPI.getDeveloperByName(developerName);
        String appName = ScannerInput.validNextLine("Enter the App Name(" + app.getAppName() + "): ");
        if (appName.isEmpty()) {
            appName = "No app name";
        }
        if (appStoreAPI.isValidAppName(appName)) {
            System.out.println(appName + " already exists");
            return;
        }
        double appSize = ScannerInput.validNextDouble("Enter the app Size in MB(" + app.getAppSize() + "): ");
        double appVersion = ScannerInput.validNextDouble("Enter the app version(" + app.getAppVersion() + "): ");
        double appCost = ScannerInput.validNextDouble("Enter the app cost(" + app.getAppCost() + "): ");

        if (app instanceof EducationApp) {
            int level = ScannerInput.validNextInt("Enter the app level(" + ((EducationApp) app).getLevel() + "): ");
            isUpdated = appStoreAPI.updateAppByIndex(index, new EducationApp(developer, appName, appSize, appVersion, appCost, level));
        } else if (app instanceof GameApp) {
            boolean isMultiplayer = Utilities.YNtoBoolean(ScannerInput.validNextChar("isMultiplayer(y/n " + ((GameApp) app).isMultiplayer() + "): "));
            isUpdated = appStoreAPI.updateAppByIndex(index, new GameApp(developer, appName, appSize, appVersion, appCost, isMultiplayer));
        } else {
            isUpdated = appStoreAPI.updateAppByIndex(index, new ProductivityApp(developer, appName, appSize, appVersion, appCost));
        }

        if (isUpdated){
            System.out.println("App Updated Successfully");
        }
        else{
            System.out.println("No App Updated");
        }
    }

    private void deleteApp() {
        int index = ScannerInput.validNextInt("Please enter the app index: ");
        if (appStoreAPI.deleteAppsByIndex(index) != null) {
            System.out.println("Delete successful");
        } else {
            System.out.println("Delete not successful");
        }
    }


    //--------------------------------------------------
    //  3) Reports MENU
    //--------------------------------------------------
    private int reportsMenu() {
        System.out.println("""
                 --------Reports Menu--------
                |   1) Apps Overview         |
                |   2) Developers Overview   |
                |   0) RETURN to main menu   |
                 ----------------------------""");
        return ScannerInput.validNextInt("==>> ");
    }

    private void runReportsMenu() {
        int option = reportsMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> appsOverview();
                case 2 -> developersOverview();
                default -> System.out.println("Invalid option entered" + option);
            }
            ScannerInput.validNextLine("\n Press the enter key to continue");
            option = reportsMenu();
        }
    }

    private void developersOverview() {
        List<Developer> developerList = developerAPI.getDevelopers();
        System.out.println("totally " + developerList.size() + " developers");
        System.out.println("-----------------------------------------------------------------------------------------");

        for (Developer developer: developerList) {
            String output = appStoreAPI.listAllAppsByChosenDeveloper(developer);
            int count = appStoreAPI.numberOfAppsByChosenDeveloper(developer);
            System.out.println(developer + " has " + count + " apps");
            if (count > 0) {
                System.out.println(output);
            }
            System.out.println("-------------------------------------------------------------------------------------");
        }
    }

    private void appsOverview() {
        System.out.println("totally " + appStoreAPI.numberOfApps() + " apps");
        System.out.println(appStoreAPI.listSummaryOfAllApps());
        System.out.println("-----------------------------------------------------------------------------------------");

        System.out.println("Game Apps");
        System.out.println(appStoreAPI.listAllGameApps());
        System.out.println("-----------------------------------------------------------------------------------------");

        System.out.println("Education Apps");
        System.out.println(appStoreAPI.listAllEducationApps());
        System.out.println("-----------------------------------------------------------------------------------------");

        System.out.println("Productivity Apps");
        System.out.println(appStoreAPI.listAllProductivityApps());
        System.out.println("-----------------------------------------------------------------------------------------");
    }



    //--------------------------------------------------
    //  4) Search
    //--------------------------------------------------
    private void searchAppsBySpecificCriteria() {
        System.out.println("""
                What criteria would you like to search apps by:
                  1) App Name
                  2) Developer Name
                  3) Rating (all apps of that rating or above)""");
        int option = ScannerInput.validNextInt("==>> ");
        switch (option) {
            case 1 -> searchAppsByName();
            case 2 -> searchAppsByDeveloper(readValidDeveloperByName());
            case 3 -> searchAppsEqualOrAboveAStarRating();
            default -> System.out.println("Invalid option");
        }
    }

    private void searchAppsByName() {
        String name = ScannerInput.validNextLine("Please input app name: ");
        System.out.println(appStoreAPI.listAllAppsByName(name));
    }

    private void searchAppsByDeveloper(Developer developer) {
        if (developer == null) {
            System.out.println("no such developer");
            return;
        }
        System.out.println(appStoreAPI.listAllAppsByChosenDeveloper(developer));
    }

    private void searchAppsEqualOrAboveAStarRating() {
        int rating = ScannerInput.validNextInt("Please input the rating: ");
        System.out.println(appStoreAPI.listAllAppsAboveOrEqualAGivenStarRating(rating));
    }




    //--------------------------------------------------
    //  5) Sort
    //--------------------------------------------------
    private void sortAppsByName(){
       appStoreAPI.sortAppsByNameAscending();
       System.out.println(appStoreAPI.listAllApps());
    }



    //--------------------------------------------------
    //  6) Recommended Apps
    //--------------------------------------------------
    private void printRecommendedApps(){
        System.out.println(appStoreAPI.listAllRecommendedApps());
    }



    //--------------------------------------------------
    //  7) Random App of the Day
    //--------------------------------------------------
    private void printRandomApp(){
        System.out.println(appStoreAPI.randomApp().toString());
    }



    //--------------------------------------------------
    //  8) Simulate ratings
    //--------------------------------------------------
    private void simulateRatings() {
        // simulate random ratings for all apps (to give data for recommended apps and reports etc).
        if (appStoreAPI.numberOfApps() > 0) {
            System.out.println("Simulating ratings...");
            appStoreAPI.simulateRatings();
            System.out.println(appStoreAPI.listSummaryOfAllApps());
        } else {
            System.out.println("No apps");
        }
    }



    //--------------------------------------------------
    //  Persistence Menu Items
    //--------------------------------------------------
    private void saveAllData() {
        try {
            developerAPI.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(developerAPI.getDevelopers().size() + " developers saved");

        try {
            appStoreAPI.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(appStoreAPI.numberOfApps() + " apps saved");
    }

    private void loadAllData() {
        try {
            developerAPI.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(developerAPI.getDevelopers().size() + " developers loaded");

        try {
            appStoreAPI.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(appStoreAPI.numberOfApps() + " apps loaded");
    }

}