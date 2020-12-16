package temple.edu.yelp_randomizer.activities;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import temple.edu.yelp_randomizer.R;
import temple.edu.yelp_randomizer.fragments.*;
import temple.edu.yelp_randomizer.models.RestaurantHolder;

import java.util.ArrayList;
import java.util.HashMap;

public class OptionsActivity extends AppCompatActivity implements FindRestarauntsFragment.FindRestaurantsChooser, RandomRestaurantsFragment.SavedRestaurantListener, ChooseRestaurantsFormFragment.LaunchChooseRestaurantsListener, SearchedRestaurantsFragment.SavedChooseRestaurantListener {

    FrameLayout frame;
    TabLayout tabLayout;
    TabItem myRestaraunts;
    TabItem findRestaraunts;


    UserRestarauntsFragment userRestarauntsFragment;
    FindRestarauntsFragment findRestarauntsFragment;
    RandomRestaurantsFragment randomRestaurantsFragment;
    ChooseRestaurantsFormFragment chooseRestaurantsFormFragment;
    SearchedRestaurantsFragment searchedRestaurantsFragment;

    FragmentManager fm;

    ArrayList<RestaurantHolder> savedRestaurants;

    LocationManager locationManager;
    LocationListener locationListener;
    private double latitude;
    private double longitude;

    /**
     * level 0: options menu (random or choose)
     * level 1: random=> listView       choose=> form
     * level 2: random=> page           choose=> listView
     * level 3:                         choose=> page
     */
    int level;
    boolean showBackButton;

    /**
     * randomLevel: is currently in random
     * chooseLevel: is currently in choose
     */
    boolean randomLevel;
    boolean chooseLevel;

    /**
     * TODO:
     *      1a. write down what's learned so far
     *      1b. make chooseRestaurantsFormFragment work properly
     *      1c. make further fragments run properly
     *      2. make onclick for each item in listview which displays more details and the ability to save the restaurant
     *      3. document code extensively before going further
     *      4. create logic for grabbing the correct coordinates (using service??)
     *      5. test application features extensively and clean up code
     * @param savedInstanceState
     */



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        savedRestaurants = new ArrayList<>();

        tabLayout = findViewById(R.id._tabLayout);
        frame = findViewById(R.id._frameLayout);
        myRestaraunts = findViewById(R.id._tabPreviousRestaraunts);
        findRestaraunts = findViewById(R.id._tabFindRestaraunts);

        fm = getSupportFragmentManager();
        userRestarauntsFragment = (UserRestarauntsFragment) fm.findFragmentByTag("urf"); // use tag on replace/add and find by tag to get specific fragment and not detach/garbage them automatically
        findRestarauntsFragment = (FindRestarauntsFragment) fm.findFragmentByTag("frf");

        level = 0;
        showBackButton = false;
        randomLevel = false;
        chooseLevel = false;


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() == 0){ // my restaurant tab
                    showBackButton = false;
                    invalidateOptionsMenu();
                    if (userRestarauntsFragment == null) {
                        Log.i("isNull", "onTabSelected: null user frag");
                        userRestarauntsFragment = UserRestarauntsFragment.newInstance(savedRestaurants);
                    }
                        fm.beginTransaction()
                                .replace(R.id._frameLayout, userRestarauntsFragment,"urf")
                                .addToBackStack(null)
                                .commit();


                }
                if (tab.getPosition() == 1) { // find Restaurant tab
                    showBackButton = true;
                    invalidateOptionsMenu();
                    if (level == 0) {
                        if (findRestarauntsFragment == null) {
                            Log.i("isNull", "onTabSelected: null find frag");
                            findRestarauntsFragment = FindRestarauntsFragment.newInstance();
                        }
                        fm.beginTransaction().replace(R.id._frameLayout, findRestarauntsFragment, "frf").addToBackStack(null).commit();
                        level = 1;
                    }else {
                        changeFragment();

                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("savedRestaurants", savedRestaurants);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        savedInstanceState = savedInstanceState.getParcelable("savedRestaurants");
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id._randomBackButton:
                if (level == 1) {
                    level = 0;
                    fm.beginTransaction().replace(R.id._frameLayout, findRestarauntsFragment, "frf").addToBackStack(null).commit();
                    invalidateOptionsMenu();
                }if (level == 2){
                    level = 1;
                    fm.beginTransaction().replace(R.id._frameLayout, chooseRestaurantsFormFragment, "crff").addToBackStack(null).commit();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * when invalidateOptionsMenu() is called, prepareOptionsMenu is run... use this method to update the menu
     * DO NOT have opnCreateOptionsMenu and onPrepareOptionsMenu as they will overlap and show duplicates
     * @param menu
     * @return
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (level > 0 && showBackButton){
            getMenuInflater().inflate(R.menu.multi_tabs_toolbar, menu);
        }
        else{
            getMenuInflater().inflate(R.menu.regular_menu, menu);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void findOptionFragment() {

            if (findRestarauntsFragment.getCurrentOptionPosition() == 0) { // if random selected
                level = 1;
                randomLevel = true; chooseLevel = false;
                invalidateOptionsMenu();
                randomRestaurantsFragment = (RandomRestaurantsFragment) fm.findFragmentByTag("rrf");
                if (randomRestaurantsFragment == null) {
                    Log.i("RandomNull:", "changeFragment: random is null");
                    randomRestaurantsFragment = RandomRestaurantsFragment.newInstance(savedRestaurants);
                }
                fm.beginTransaction().replace(R.id._frameLayout, randomRestaurantsFragment, "rrf").addToBackStack(null).commit();
            } else if (findRestarauntsFragment.getCurrentOptionPosition() == 1) { // if choose selected
                level = 1;
                randomLevel = false;
                chooseLevel = true;
                invalidateOptionsMenu();
                chooseRestaurantsFormFragment = (ChooseRestaurantsFormFragment) fm.findFragmentByTag("crff");
                if (chooseRestaurantsFormFragment == null) {
                    chooseRestaurantsFormFragment = ChooseRestaurantsFormFragment.newInstance();
                }
                fm.beginTransaction().replace(R.id._frameLayout, chooseRestaurantsFormFragment, "crff").addToBackStack(null).commit();
                // display random chooser
            }

    }

    /**
     * change the current fragment in the find options menu
     */
    public void changeFragment(){
        if (level == 1){
            if (randomLevel){
                fm.beginTransaction().replace(R.id._frameLayout, randomRestaurantsFragment, "rrf").addToBackStack(null).commit();
            }else if (chooseLevel){
                fm.beginTransaction().replace(R.id._frameLayout, chooseRestaurantsFormFragment, "crff").addToBackStack(null).commit();
            }
        } else if (level == 2){
            if (randomLevel){
                // show random pages fragment
            } else if (chooseLevel){
                fm.beginTransaction().replace(R.id._frameLayout, searchedRestaurantsFragment, "srf").addToBackStack(null).commit();
            }

        } else if (level == 3){
            // show choose pages fragment
        }
    }

    /**
     * should never be null, is called every time a new search is pressed
     * @param selectors
     */
    @Override
    public void searchChooseRestaurants(HashMap<String, String> selectors) {
        invalidateOptionsMenu();
        level = 2;
        searchedRestaurantsFragment = SearchedRestaurantsFragment.newInstance(savedRestaurants, selectors);
        fm.beginTransaction().replace(R.id._frameLayout, searchedRestaurantsFragment, "srf").addToBackStack(null).commit();
    }

    @Override
    public void updateSaveList() {
        savedRestaurants = randomRestaurantsFragment.getSavedRestaurants();
    }

    @Override
    public String getLatitude(){
        return String.valueOf(latitude);
    }

    @Override
    public String getLongitude(){
        return String.valueOf(longitude);
    }

    @Override
    public void updateSaveListChoose_() {
        savedRestaurants = searchedRestaurantsFragment.getSavedRestaurants();
    }

    class UserLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            if (location != null){
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(@NonNull String provider) {

        }

        @Override
        public void onProviderDisabled(@NonNull String provider) {

        }



    }

}
