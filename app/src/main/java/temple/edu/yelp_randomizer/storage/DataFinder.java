package temple.edu.yelp_randomizer.storage;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import temple.edu.yelp_randomizer.restaraunts.RestaurantsFinder;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class DataFinder {

    Context context;

    public DataFinder(Context context){
        this.context = context;
    }

    public ArrayList<String> getCategories(){
        String path = context.getFilesDir() + "/categories.txt";
        try{
            File file = new File(path);
            boolean hasCategoriesWritten = false;
            if (file.exists()) {
                Scanner scanner = new Scanner(file);
                ArrayList<String> categories = new ArrayList<>();
                if (scanner.hasNextBoolean()) {
                    hasCategoriesWritten = scanner.nextBoolean();
                    Log.i("hasCategoriesWritten", "hasCategories: " + hasCategoriesWritten);
                }

                if (hasCategoriesWritten) {
                    scanner.nextLine(); // because of boolean
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        Log.i("hasCategoriesWritten:", "getCategories2: " + line);
                        categories.add(line);
                    }
                    scanner.close();
                    return categories;
                }
            }
            if (!file.exists() || !hasCategoriesWritten) {
                writeCategories();
                Scanner scanner = new Scanner(file);
                ArrayList<String> categories = new ArrayList<>();
                scanner.nextLine(); // because of boolean
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    Log.i("Line:", "getCategories: " + line);
                    categories.add(line);
                }
                scanner.close();
                return categories;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void writeCategories(){
        Log.i("writeFile", "writeCategories: writing file");
        String path = context.getFilesDir() + "/categories.txt";
        try{
            File file = new File(path);
            FileWriter writer = new FileWriter(file, false);
            writer.write("true");
            writer.write('\n');

            RestaurantsFinder restaurantsFinder = new RestaurantsFinder();
            ArrayList<String> categories = restaurantsFinder.getCategoriesList();
            for (String item: categories){
                writer.write(item);
                writer.write('\n');
            }
            writer.flush();
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
