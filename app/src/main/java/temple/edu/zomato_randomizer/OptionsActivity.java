package temple.edu.zomato_randomizer;

import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import temple.edu.zomato_randomizer.restaraunts.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OptionsActivity extends AppCompatActivity implements FindRestarauntsFragment.FindRestaurantsChooser, RandomRestaurantsFragment.SavedRestaurantListener {

    FrameLayout frame;
    TabLayout tabLayout;
    TabItem myRestaraunts;
    TabItem findRestaraunts;

    UserRestarauntsFragment userRestarauntsFragment;
    FindRestarauntsFragment findRestarauntsFragment;
    RandomRestaurantsFragment randomRestaurantsFragment;
    FragmentManager fm;

    ArrayList<Holder> savedRestaurants;
    int level;

    /**
     * TODO:
     *      1. create logic for saving restaurants using the same bookmark logic (done for moment)
     *      2. create logic for saving place in random tab (done for moment)
     *      2a. avoid making multiple requests for findRestaurants
     *      3. create logic for grabbing the correct coordinates
     *      4. test application features extensively and clean up code
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

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() == 0){ // my restaurant tab
                    if (userRestarauntsFragment == null) {
                        Log.i("isNull", "onTabSelected: null user frag");
                        userRestarauntsFragment = UserRestarauntsFragment.newInstance(savedRestaurants);
                    }
                        fm.beginTransaction()
                                .replace(R.id._frameLayout, userRestarauntsFragment,"urf")
                                .addToBackStack(null)
                                .commit();


                }
                if (tab.getPosition() == 1){ // find Restaurant tab
                    if (level == 0) {
                        if (findRestarauntsFragment == null) {
                            Log.i("isNull", "onTabSelected: null find frag");
                            findRestarauntsFragment = FindRestarauntsFragment.newInstance();
                        }
                            fm.beginTransaction().replace(R.id._frameLayout, findRestarauntsFragment,"frf").addToBackStack(null).commit();


                    } else{
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
    public void changeFragment() {

        if (findRestarauntsFragment.getCurrentOptionPosition() == 0){ // if random selected
            level = 1;
            randomRestaurantsFragment = (RandomRestaurantsFragment) fm.findFragmentByTag("rrf");
            if (randomRestaurantsFragment == null) {
                Log.i("RandomNull:", "changeFragment: random is null");
                randomRestaurantsFragment = RandomRestaurantsFragment.newInstance(savedRestaurants);
            }
            fm.beginTransaction().replace(R.id._frameLayout, randomRestaurantsFragment,"rrf").addToBackStack(null).commit();



        }
        else if (findRestarauntsFragment.getCurrentOptionPosition() == 1){
            // display random chooser
        }
    }

    @Override
    public void updateSaveList() {
        savedRestaurants = randomRestaurantsFragment.getSavedRestaurants();
    }

}
