package temple.edu.yelp_randomizer.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import io.grpc.stub.AbstractFutureStub;
import temple.edu.yelp_randomizer.R;
import temple.edu.yelp_randomizer.activities.account.LoginActivity;
import temple.edu.yelp_randomizer.fragments.*;
import temple.edu.yelp_randomizer.models.DetailsModel;
import temple.edu.yelp_randomizer.models.RestaurantModel;
import temple.edu.yelp_randomizer.models.ReviewsModel;
import temple.edu.yelp_randomizer.restaraunts.RestaurantsFinder;
import temple.edu.yelp_randomizer.storage.DataFinder;

import java.util.ArrayList;
import java.util.HashMap;


public class OptionsActivity extends AppCompatActivity implements FindRestarauntsFragment.FindRestaurantsChooser, RandomRestaurantsFragment.SavedRestaurantListener, ChooseRestaurantsFormFragment.LaunchChooseRestaurantsListener, SearchedRestaurantsFragment.SavedChooseRestaurantListener, RestaurantContentFragment.RestaurantContentListener, UserRestarauntsFragment.FragmentContentListener {

    FrameLayout frame;
    TabLayout tabLayout;
    TabItem myRestaraunts;
    TabItem findRestaraunts;
    TextView signOutTextView;


    UserRestarauntsFragment userRestarauntsFragment;
    FindRestarauntsFragment findRestarauntsFragment;
    RandomRestaurantsFragment randomRestaurantsFragment;
    ChooseRestaurantsFormFragment chooseRestaurantsFormFragment;
    SearchedRestaurantsFragment searchedRestaurantsFragment;
    RestaurantContentFragment restaurantContentFragment;

    FragmentManager fm;

    ArrayList<RestaurantModel> savedRestaurants;
    ArrayList<String> categories;

    LocationManager locationManager;
    LocationListener locationListener;
    private double latitude;
    private double longitude;

    /**
     * level 0: options menu (random or choose)
     * level 1: random=> listView       choose=> form
     * level 2: random=> content        choose=> listView
     * level 3:                         choose=> content
     */
    int level;
    boolean userTab;
    boolean showBackButton;
    boolean saveMenu;
    boolean refreshMenu;

    /**
     * randomLevel: is currently in random
     * chooseLevel: is currently in choose
     */
    boolean randomLevel;
    boolean chooseLevel;

    FirebaseUser currentUser;
    FirebaseFirestore firestore;

    public void handleFirestore(){
        firestore = FirebaseFirestore.getInstance();
    }
    /**
     * Initialize fields; fragments loaded from tab, define primitives for dictating level
     * Pre load categories for ChooseRestaurantsFormFragment
     * set level chang depending on tab clicked
     *      => restore state of layout dependent on tab
     * set backButton availability based on button clicked
     *
     * @param savedInstanceState
     */

    private final Thread task = new Thread() {
        @Override
        public void run() {
            while (userRestarauntsFragment == null){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            initializeSavedRestaurants();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);


       // currentUser = getIntent().getParcelableExtra("user");
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        firestore = FirebaseFirestore.getInstance();

        Toolbar toolbar = findViewById(R.id._optionsToolbar);
        setSupportActionBar(toolbar);

        savedRestaurants = new ArrayList<>();


        tabLayout = findViewById(R.id._tabLayout);
        frame = findViewById(R.id._frameLayout);
        myRestaraunts = findViewById(R.id._tabPreviousRestaraunts);
        findRestaraunts = findViewById(R.id._tabFindRestaraunts);
        signOutTextView = findViewById(R.id._signOutTextView);

        fm = getSupportFragmentManager();
        userRestarauntsFragment = (UserRestarauntsFragment) fm.findFragmentByTag("urf"); // use tag on replace/add and find by tag to get specific fragment and not detach/garbage them automatically
        initializeSavedRestaurants();
        findRestarauntsFragment = (FindRestarauntsFragment) fm.findFragmentByTag("frf");

        level = 0;
        userTab = false;
        showBackButton = false;
        randomLevel = false;
        chooseLevel = false;
        saveMenu = false;
        refreshMenu = false;

        Context context = this;
        Thread thread = new Thread(){
            @Override
            public void run() {
                DataFinder dataFinder = new DataFinder(context);
                categories = dataFinder.getCategories();
            }
        };
        thread.start();
        try {
            thread.join();
        }catch (Exception e){
            e.printStackTrace();
        }

        Intent receiveIntent = getIntent();
        GoogleSignInAccount account = receiveIntent.getParcelableExtra("google");

        signOutTextView.setPaintFlags(signOutTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        signOutTextView.setTextColor(Color.BLUE);
        signOutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build();
                GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(context,gso);
                googleSignInClient.signOut();
                FirebaseAuth.getInstance().signOut();
                launchLogin();

            }

            private void launchLogin(){
                Intent intent = new Intent(context, LoginActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });



        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() == 0){ // my restaurant tab
                    showBackButton = false;
                    saveMenu = false;
                    refreshMenu = false;

                    invalidateOptionsMenu();
                    userRestarauntsFragment = (UserRestarauntsFragment) fm.findFragmentByTag("urf");
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
                        findRestarauntsFragment = (FindRestarauntsFragment) fm.findFragmentByTag("frf");
                        if (findRestarauntsFragment == null) {
                            Log.i("isNull", "onTabSelected: null find frag");
                            findRestarauntsFragment = FindRestarauntsFragment.newInstance();
                        }
                        fm.beginTransaction().replace(R.id._frameLayout, findRestarauntsFragment, "frf").addToBackStack(null).commit();
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

        TabLayout tabLayout = (TabLayout) findViewById(R.id._tabLayout);
        TabLayout.Tab tab = tabLayout.getTabAt(1);
        tab.select();


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

    /**
     * dependent on level of fragment, go back to the correct fragment
     * @param item object that is pressed
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (level == 1) {
                    level = 0;
                    fm.beginTransaction().replace(R.id._frameLayout, findRestarauntsFragment, "frf").addToBackStack(null).commit();
                    randomLevel = false;
                    chooseLevel = false;
                    invalidateOptionsMenu();
                } else if (level == 2) {
                    level = 1;
                    if (randomLevel) {
                        fm.beginTransaction().replace(R.id._frameLayout, randomRestaurantsFragment, "rrf").addToBackStack(null).commit();

                    } else if (chooseLevel) {
                        fm.beginTransaction().replace(R.id._frameLayout, chooseRestaurantsFormFragment, "crff").addToBackStack(null).commit();
                    }
                } else if (level == 3) {
                    level = 2;
                    fm.beginTransaction().replace(R.id._frameLayout, searchedRestaurantsFragment, "srf").addToBackStack(null).commit();
                }
                invalidateOptionsMenu();
                saveMenu = false; // every time back is clicked saveMenu is not displayed
                refreshMenu = false;
                break;

            case R.id._saveMenuItem:
                if (!OptionsActivity.containsRestaurant(savedRestaurants, restaurantContentFragment.getCurrentRestaurant().getId())) {
                    savedRestaurants.add(restaurantContentFragment.getCurrentRestaurant());
                    firestore.collection("users").document(currentUser.getUid()).collection("savedRestaurants").document(
                            restaurantContentFragment.getCurrentRestaurant().getId()
                    ).set(restaurantContentFragment.getCurrentRestaurant(), SetOptions.merge());
                }
                break;

            case R.id._launchMenuItem:
                Uri webpage = Uri.parse(restaurantContentFragment.getCurrentRestaurant().getUrl());
                Intent launchIntent = new Intent(Intent.ACTION_VIEW, webpage);
                Intent lShareIntent = Intent.createChooser(launchIntent, null);
                startActivity(lShareIntent);
                break;

            case R.id._callMenuItem: // need tel: to use action_dial
                Uri phoneNumber = Uri.parse("tel:" + restaurantContentFragment.getCurrentRestaurant().getPhone());
                Intent callIntent = new Intent(Intent.ACTION_DIAL, phoneNumber);
                Intent cShareIntent = Intent.createChooser(callIntent, null);
                startActivity(cShareIntent);
                break;

            case R.id._visitMenuItem:
                String location_string = restaurantContentFragment.getCurrentRestaurant().getLocation().replaceAll(" ", "+");
                Uri location = Uri.parse("geo:0,0?q=" + location_string);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
                Intent mShareIntent = Intent.createChooser(mapIntent, null);
                startActivity(mShareIntent);
                break;

            case R.id._refreshMenuItem:
                randomRestaurantsFragment = RandomRestaurantsFragment.newInstance(savedRestaurants);
                fm.beginTransaction().replace(R.id._frameLayout, randomRestaurantsFragment, "rrf").addToBackStack(null).commit();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * when invalidateOptionsMenu() is called, prepareOptionsMenu is run... use this method to update the menu
     * DO NOT have opnCreateOptionsMenu and onPrepareOptionsMenu as they will overlap and show duplicates
     *
     * -- display the correct menu based on the fragment displayed (based on showBackButton) --
     * @param menu
     * @return
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (level > 0 && showBackButton){
            getMenuInflater().inflate(R.menu.regular_menu, menu);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            Log.i("OptionsMenu", "level: " + level + "\t choose: " + chooseLevel + "\trandom: " + randomLevel + "\tsave: " + saveMenu);
            if (((level == 2 && randomLevel) || (level == 3 && chooseLevel)) && !saveMenu){
                getMenuInflater().inflate(R.menu.content_menu, menu);
                saveMenu = true;
            }
            if (level == 1 && randomLevel && !refreshMenu){
                getMenuInflater().inflate(R.menu.refresh_menu, menu);
                refreshMenu = true;
            }
        }
        else{
            getMenuInflater().inflate(R.menu.regular_menu, menu);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();

    }

    /**
     * callback method that shows that loads level 1 fragments based on option selected
     * level 1: random=> listView       choose=> form
     * should be new instances if newly created, or loaded if already created
     */
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
                    chooseRestaurantsFormFragment = ChooseRestaurantsFormFragment.newInstance(categories);
                }
                fm.beginTransaction().replace(R.id._frameLayout, chooseRestaurantsFormFragment, "crff").addToBackStack(null).commit();
                // display random chooser
            }

    }

    /**
     * change the current fragment in the find options menu
     * changeFragment only replaces current fragment, does not create new instance
     * fragments already exist, and "level" ensures they do
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
                fm.beginTransaction().replace(R.id._frameLayout, restaurantContentFragment, "rcf").addToBackStack(null).commit();

            } else if (chooseLevel){
                fm.beginTransaction().replace(R.id._frameLayout, searchedRestaurantsFragment, "srf").addToBackStack(null).commit();
            }

        } else if (level == 3){
            // show choose pages fragment
            fm.beginTransaction().replace(R.id._frameLayout, restaurantContentFragment, "rcf").addToBackStack(null).commit();

        }
        invalidateOptionsMenu();
    }

    /**
     * should never be null, is called every time a new search is pressed
     * every time search button is presseed, create a new instance of fragment and replace the previous with the same tag
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
    public void launchRandomContent(RestaurantModel restaurant) {
        level = 2;

        try{
            final ArrayList<ReviewsModel>[] reviews = new ArrayList[]{new ArrayList<>()};
            final DetailsModel[] details = {new DetailsModel()};
            Thread review_thread = new Thread(){
                @Override
                public void run() {

                    RestaurantsFinder restaurantsFinder = new RestaurantsFinder(restaurant.getId());
                    reviews[0] = restaurantsFinder.getReviewsList();
                }
            };
            review_thread.start();
            Thread details_thread = new Thread(){
                @Override
                public void run() {
                    RestaurantsFinder restaurantsFinder = new RestaurantsFinder(restaurant.getId());
                    details[0] = restaurantsFinder.getDetails();
                }
            };
            details_thread.start();
            details_thread.join();
            review_thread.join();
            restaurantContentFragment = RestaurantContentFragment.newInstance(restaurant, savedRestaurants, reviews[0], details[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        fm.beginTransaction().replace(R.id._frameLayout, restaurantContentFragment, "rcf").addToBackStack(null).commit();
        invalidateOptionsMenu();




    }
    @Override
    public void launchSearchedContent(RestaurantModel restaurant) {
        level = 3;

        try{

            final ArrayList<ReviewsModel>[] reviews = new ArrayList[]{new ArrayList<>()};
            final DetailsModel[] details = {new DetailsModel()};
            Thread review_thread = new Thread(){
                @Override
                public void run() {

                    RestaurantsFinder restaurantsFinder = new RestaurantsFinder(restaurant.getId());
                    reviews[0] = restaurantsFinder.getReviewsList();
                }
            };
            review_thread.start();
            Thread details_thread = new Thread(){
                @Override
                public void run() {
                    RestaurantsFinder restaurantsFinder = new RestaurantsFinder(restaurant.getId());
                    details[0] = restaurantsFinder.getDetails();
                }
            };
            details_thread.start();
            details_thread.join();
            review_thread.join();
            restaurantContentFragment = RestaurantContentFragment.newInstance(restaurant, savedRestaurants, reviews[0], details[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        fm.beginTransaction().replace(R.id._frameLayout, restaurantContentFragment, "rcf").addToBackStack(null).commit();
        invalidateOptionsMenu();

    }

    /**
     * callback method for searchRestaurantsFragment saveRestaurantsButton
     */
    @Override
    public void updateSaveListChoose_() {
        savedRestaurants = searchedRestaurantsFragment.getSavedRestaurants();
    }

    /**
     * callback method for randomRestaurantsFragment saveRestaurants button
     */
    @Override
    public void updateSaveList() {
        savedRestaurants = randomRestaurantsFragment.getSavedRestaurants();
    }

    /**
     * @return latitude as string
     */
    @Override
    public String getLatitude(){
        return String.valueOf(latitude);
    }

    /**
     * @return longitude as String
     */
    @Override
    public String getLongitude(){
        return String.valueOf(longitude);
    }

    @Override
    public void launchReviewActivity(ArrayList<ReviewsModel> reviews) {
        Intent intent = new Intent(this, ReviewsActivity.class);
        intent.putParcelableArrayListExtra("reviews", reviews);
        intent.putParcelableArrayListExtra("savedRestaurants", savedRestaurants);
        intent.putExtra("restaurant", restaurantContentFragment.getCurrentRestaurant());
        //startActivity(intent);
        startActivityForResult(intent, 400);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 400){
            assert data != null;
            savedRestaurants = data.getParcelableArrayListExtra("savedRestaurants");
        }
    }

    /**
     * local class to retrieve current location from gps
     */
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

    public static boolean containsRestaurant(ArrayList<RestaurantModel> restaurants, String resId){
        for (RestaurantModel restaurant: restaurants) {
            if (restaurant.getId().equals(resId)){
                return true;
            }
        }
        return false;
    }

    public void initializeSavedRestaurants(){
        firestore.collection("users").document(currentUser.getUid()).collection("savedRestaurants")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            savedRestaurants = new ArrayList<>();
                            for (QueryDocumentSnapshot document: task.getResult()){
                                try {
                                    RestaurantModel restaurant = new RestaurantModel(
                                            ((Long) document.get("rating")).intValue(),
                                            (String) document.get("id"),
                                            (String) document.get("name"),
                                            (String) document.get("phone"),
                                            (String) document.get("image"),
                                            (String) document.get("location"),
                                            (String) document.get("url")
                                    );
                                    restaurant.setReview((String) document.get("review"));
                                    savedRestaurants.add(restaurant);
                                }catch (NullPointerException ignored){
                                    Log.w("nullPointerException", "null rating" );
                                }
                            }

                        }
                    }
                });
    }



}
