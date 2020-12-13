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
import temple.edu.zomato_randomizer.restaraunts.FindRestarauntsFragment;
import temple.edu.zomato_randomizer.restaraunts.Holder;
import temple.edu.zomato_randomizer.restaraunts.RandomRestaurantsFragment;
import temple.edu.zomato_randomizer.restaraunts.UserRestarauntsFragment;

import java.util.ArrayList;

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

    /**
     * TODO:
     *      1. create logic for saving restaurants using the same bookmark logic
     *      2. create logic for saving place in tabs
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
        userRestarauntsFragment = (UserRestarauntsFragment) fm.findFragmentById(R.layout.fragment_find_restaraunts);
        findRestarauntsFragment = (FindRestarauntsFragment) fm.findFragmentById(R.layout.fragment_find_restaraunts);


      /*  userRestarauntsFragment = UserRestarauntsFragment.newInstance(); //standard to have open
        fm.beginTransaction()
                .add(R.id._frameLayout, userRestarauntsFragment)
                .commit();*/

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() == 0){ // my restaurant tab
                    if (userRestarauntsFragment == null) {
                        Log.i("isNull", "onTabSelected: null user frag");
                        userRestarauntsFragment = UserRestarauntsFragment.newInstance(savedRestaurants);
                    }
                    fm.beginTransaction()
                            .replace(R.id._frameLayout, userRestarauntsFragment)
                            .commit();
                }
                if (tab.getPosition() == 1){ // find Restaurant tab
                    if (findRestarauntsFragment == null) {
                        Log.i("isNull", "onTabSelected: null find frag");
                        findRestarauntsFragment = FindRestarauntsFragment.newInstance();
                    }
                    fm.beginTransaction().replace(R.id._frameLayout, findRestarauntsFragment).commit();
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
            randomRestaurantsFragment = (RandomRestaurantsFragment) fm.findFragmentById(R.layout.fragment_random_restaurants);
            if (randomRestaurantsFragment == null) {
                randomRestaurantsFragment = RandomRestaurantsFragment.newInstance(savedRestaurants);
            }
            fm.beginTransaction().replace(R.id._frameLayout, randomRestaurantsFragment).commit();
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
