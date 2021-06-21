package temple.edu.random.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import temple.edu.random.fragments.FindRestarauntsFragment.FindRestaurantsChooser
import temple.edu.random.fragments.RandomRestaurantsFragment.SavedRestaurantListener
import temple.edu.random.fragments.ChooseRestaurantsFormFragment.LaunchChooseRestaurantsListener
import temple.edu.random.fragments.SearchedRestaurantsFragment.SavedChooseRestaurantListener
import temple.edu.random.fragments.RestaurantContentFragment.RestaurantContentListener
import android.widget.FrameLayout
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabItem
import temple.edu.random.fragments.UserRestarauntsFragment
import temple.edu.random.fragments.FindRestarauntsFragment
import temple.edu.random.fragments.RandomRestaurantsFragment
import temple.edu.random.fragments.ChooseRestaurantsFormFragment
import temple.edu.random.fragments.SearchedRestaurantsFragment
import temple.edu.random.fragments.RestaurantContentFragment
import temple.edu.random.models.RestaurantModel
import android.location.LocationManager
import android.location.LocationListener
import android.os.Bundle
import temple.edu.random.R
import temple.edu.random.storage.DataFinder
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import android.content.Intent
import android.location.Location
import android.net.Uri
import android.os.Parcelable
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentManager
import temple.edu.random.models.ReviewsModel
import temple.edu.random.models.DetailsModel
import temple.edu.random.restaraunts.RestaurantsFinder
import java.lang.Exception
import java.util.ArrayList
import java.util.HashMap

/**
 * TODO:
 * 1. implement logic to properly randomize restaurants shown
 * 2. create a refresh menu item to refresh the random restaurants
 * 3. firebase for login, also google login auth
 * 4. save restaurants information into database to have restaurants loaded (only unique restaurants)
 * 5. save review button in review activity
 */
class OptionsActivity : AppCompatActivity(), FindRestaurantsChooser, SavedRestaurantListener, LaunchChooseRestaurantsListener, SavedChooseRestaurantListener, RestaurantContentListener {
    var frame: FrameLayout? = null
    var tabLayout: TabLayout? = null
    var myRestaraunts: TabItem? = null
    var findRestaraunts: TabItem? = null
    var userRestarauntsFragment: UserRestarauntsFragment? = null
    var findRestarauntsFragment: FindRestarauntsFragment? = null
    var randomRestaurantsFragment: RandomRestaurantsFragment? = null
    var chooseRestaurantsFormFragment: ChooseRestaurantsFormFragment? = null
    var searchedRestaurantsFragment: SearchedRestaurantsFragment? = null
    var restaurantContentFragment: RestaurantContentFragment? = null
    var fm: FragmentManager? = null
    var savedRestaurants: ArrayList<RestaurantModel>? = null
    var categories: ArrayList<String>? = null
    var locationManager: LocationManager? = null
    var locationListener: LocationListener? = null
    override var latitude = 0.0
    override var longitude = 0.0

    /**
     * level 0: options menu (random or choose)
     * level 1: random=> listView       choose=> form
     * level 2: random=> content        choose=> listView
     * level 3:                         choose=> content
     */
    var level = 0
    var showBackButton = false
    var saveMenu = false

    /**
     * randomLevel: is currently in random
     * chooseLevel: is currently in choose
     */
    var randomLevel = false
    var chooseLevel = false

    /**
     * Initialize fields; fragments loaded from tab, define primitives for dictating level
     * Pre load categories for ChooseRestaurantsFormFragment
     * set level chang depending on tab clicked
     * => restore state of layout dependent on tab
     * set backButton availability based on button clicked
     *
     * @param savedInstanceState
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)
        val toolbar = findViewById<Toolbar>(R.id._optionsToolbar)
        setSupportActionBar(toolbar)
        savedRestaurants = ArrayList()
        tabLayout = findViewById(R.id._tabLayout)
        frame = findViewById(R.id._frameLayout)
        myRestaraunts = findViewById(R.id._tabPreviousRestaraunts)
        findRestaraunts = findViewById(R.id._tabFindRestaraunts)
        fm = supportFragmentManager
        userRestarauntsFragment = fm!!.findFragmentByTag("urf") as UserRestarauntsFragment? // use tag on replace/add and find by tag to get specific fragment and not detach/garbage them automatically
        findRestarauntsFragment = fm!!.findFragmentByTag("frf") as FindRestarauntsFragment?
        level = 0
        showBackButton = false
        randomLevel = false
        chooseLevel = false
        saveMenu = false
        val context: Context = this
        val thread: Thread = object : Thread() {
            override fun run() {
                val dataFinder = DataFinder(context)
                categories = dataFinder.categories
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        tabLayout?.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.position == 0) { // my restaurant tab
                    showBackButton = false
                    saveMenu = false
                    invalidateOptionsMenu()
                    userRestarauntsFragment = fm!!.findFragmentByTag("urf") as UserRestarauntsFragment?
                    if (userRestarauntsFragment == null) {
                        Log.i("isNull", "onTabSelected: null user frag")
                        userRestarauntsFragment = UserRestarauntsFragment.newInstance(savedRestaurants)
                    }
                    fm!!.beginTransaction()
                            .replace(R.id._frameLayout, userRestarauntsFragment!!, "urf")
                            .addToBackStack(null)
                            .commit()
                }
                if (tab.position == 1) { // find Restaurant tab
                    showBackButton = true
                    invalidateOptionsMenu()
                    if (level == 0) {
                        findRestarauntsFragment = fm!!.findFragmentByTag("frf") as FindRestarauntsFragment?
                        if (findRestarauntsFragment == null) {
                            Log.i("isNull", "onTabSelected: null find frag")
                            findRestarauntsFragment = FindRestarauntsFragment.newInstance()
                        }
                        fm!!.beginTransaction().replace(R.id._frameLayout, findRestarauntsFragment!!, "frf").addToBackStack(null).commit()
                    } else {
                        changeFragment()
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("savedRestaurants", savedRestaurants)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        var savedInstanceState = savedInstanceState
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState = savedInstanceState.getParcelable("savedRestaurants")!!
    }

    /**
     * dependent on level of fragment, go back to the correct fragment
     * @param item object that is pressed
     * @return
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                if (level == 1) {
                    level = 0
                    fm!!.beginTransaction().replace(R.id._frameLayout, findRestarauntsFragment!!, "frf").addToBackStack(null).commit()
                    randomLevel = false
                    chooseLevel = false
                    invalidateOptionsMenu()
                } else if (level == 2) {
                    level = 1
                    if (randomLevel) {
                        fm!!.beginTransaction().replace(R.id._frameLayout, randomRestaurantsFragment!!, "rrf").addToBackStack(null).commit()
                    } else if (chooseLevel) {
                        fm!!.beginTransaction().replace(R.id._frameLayout, chooseRestaurantsFormFragment!!, "crff").addToBackStack(null).commit()
                    }
                } else if (level == 3) {
                    level = 2
                    fm!!.beginTransaction().replace(R.id._frameLayout, searchedRestaurantsFragment!!, "srf").addToBackStack(null).commit()
                }
                invalidateOptionsMenu()
                saveMenu = false // every time back is clicked saveMenu is not displayed
            }
            R.id._saveMenuItem -> restaurantContentFragment!!.currentRestaurant?.let { savedRestaurants!!.add(it) }
            R.id._launchMenuItem -> {
                val webpage = Uri.parse(restaurantContentFragment!!.currentRestaurant?.url)
                val launchIntent = Intent(Intent.ACTION_VIEW, webpage)
                val lShareIntent = Intent.createChooser(launchIntent, null)
                startActivity(lShareIntent)
            }
            R.id._callMenuItem -> {
                val phoneNumber = Uri.parse("tel:" + (restaurantContentFragment!!.currentRestaurant?.phone ?: "N/A"))
                val callIntent = Intent(Intent.ACTION_DIAL, phoneNumber)
                val cShareIntent = Intent.createChooser(callIntent, null)
                startActivity(cShareIntent)
            }
            R.id._visitMenuItem -> {
                val location_string = restaurantContentFragment!!.currentRestaurant?.location?.replace(" ".toRegex(), "+")
                val location = Uri.parse("geo:0,0?q=$location_string")
                val mapIntent = Intent(Intent.ACTION_VIEW, location)
                val mShareIntent = Intent.createChooser(mapIntent, null)
                startActivity(mShareIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * when invalidateOptionsMenu() is called, prepareOptionsMenu is run... use this method to update the menu
     * DO NOT have opnCreateOptionsMenu and onPrepareOptionsMenu as they will overlap and show duplicates
     *
     * -- display the correct menu based on the fragment displayed (based on showBackButton) --
     * @param menu
     * @return
     */
    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        if (level > 0 && showBackButton) {
            menuInflater.inflate(R.menu.regular_menu, menu)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            Log.i("OptionsMenu", "level: $level\t choose: $chooseLevel\trandom: $randomLevel\tsave: $saveMenu")
            if ((level == 2 && randomLevel || level == 3 && chooseLevel) && !saveMenu) {
                menuInflater.inflate(R.menu.content_menu, menu)
                saveMenu = true
            }
        } else {
            menuInflater.inflate(R.menu.regular_menu, menu)
            supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    /**
     * callback method that shows that loads level 1 fragments based on option selected
     * level 1: random=> listView       choose=> form
     * should be new instances if newly created, or loaded if already created
     */
    override fun findOptionFragment() {
        if (findRestarauntsFragment!!.currentOptionPosition == 0) { // if random selected
            level = 1
            randomLevel = true
            chooseLevel = false
            invalidateOptionsMenu()
            randomRestaurantsFragment = fm!!.findFragmentByTag("rrf") as RandomRestaurantsFragment?
            if (randomRestaurantsFragment == null) {
                Log.i("RandomNull:", "changeFragment: random is null")
                randomRestaurantsFragment = RandomRestaurantsFragment.newInstance(savedRestaurants)
            }
            fm!!.beginTransaction().replace(R.id._frameLayout, randomRestaurantsFragment!!, "rrf").addToBackStack(null).commit()
        } else if (findRestarauntsFragment!!.currentOptionPosition == 1) { // if choose selected
            level = 1
            randomLevel = false
            chooseLevel = true
            invalidateOptionsMenu()
            chooseRestaurantsFormFragment = fm!!.findFragmentByTag("crff") as ChooseRestaurantsFormFragment?
            if (chooseRestaurantsFormFragment == null) {
                chooseRestaurantsFormFragment = ChooseRestaurantsFormFragment.newInstance(categories)
            }
            fm!!.beginTransaction().replace(R.id._frameLayout, chooseRestaurantsFormFragment!!, "crff").addToBackStack(null).commit()
            // display random chooser
        }
    }

    /**
     * change the current fragment in the find options menu
     * changeFragment only replaces current fragment, does not create new instance
     * fragments already exist, and "level" ensures they do
     */
    fun changeFragment() {
        if (level == 1) {
            if (randomLevel) {
                fm!!.beginTransaction().replace(R.id._frameLayout, randomRestaurantsFragment!!, "rrf").addToBackStack(null).commit()
            } else if (chooseLevel) {
                fm!!.beginTransaction().replace(R.id._frameLayout, chooseRestaurantsFormFragment!!, "crff").addToBackStack(null).commit()
            }
        } else if (level == 2) {
            if (randomLevel) {
                // show random pages fragment
                fm!!.beginTransaction().replace(R.id._frameLayout, restaurantContentFragment!!, "rcf").addToBackStack(null).commit()
            } else if (chooseLevel) {
                fm!!.beginTransaction().replace(R.id._frameLayout, searchedRestaurantsFragment!!, "srf").addToBackStack(null).commit()
            }
        } else if (level == 3) {
            // show choose pages fragment
            fm!!.beginTransaction().replace(R.id._frameLayout, restaurantContentFragment!!, "rcf").addToBackStack(null).commit()
        }
        invalidateOptionsMenu()
    }

    /**
     * should never be null, is called every time a new search is pressed
     * every time search button is presseed, create a new instance of fragment and replace the previous with the same tag
     * @param selectors
     */
    override fun searchChooseRestaurants(selectors: HashMap<String, String>?) {
        invalidateOptionsMenu()
        level = 2
        searchedRestaurantsFragment = SearchedRestaurantsFragment.newInstance(savedRestaurants, selectors)
        fm!!.beginTransaction().replace(R.id._frameLayout, searchedRestaurantsFragment!!, "srf").addToBackStack(null).commit()
    }

    override fun launchRandomContent(restaurant: RestaurantModel?) {
        level = 2
        try {
            val reviews: Array<ArrayList<*>> = arrayOf<ArrayList<*>>(ArrayList<Any>())
            val details = arrayOf(DetailsModel())
            val review_thread: Thread = object : Thread() {
                override fun run() {
                    val restaurantsFinder = RestaurantsFinder(restaurant?.id)
                    reviews[0] = restaurantsFinder.reviewsList
                }
            }
            review_thread.start()
            val details_thread: Thread = object : Thread() {
                override fun run() {
                    val restaurantsFinder = RestaurantsFinder(restaurant?.id)
                    details[0] = restaurantsFinder.details
                }
            }
            details_thread.start()
            details_thread.join()
            review_thread.join()
            restaurantContentFragment = RestaurantContentFragment.newInstance(restaurant, savedRestaurants, reviews[0] as ArrayList<out Parcelable>, details[0])
        } catch (e: Exception) {
            e.printStackTrace()
        }
        fm!!.beginTransaction().replace(R.id._frameLayout, restaurantContentFragment!!, "rcf").addToBackStack(null).commit()
        invalidateOptionsMenu()
    }

    override fun launchSearchedContent(restaurant: RestaurantModel?) {
        level = 3
        try {
            val reviews: Array<ArrayList<*>> = arrayOf<ArrayList<*>>(ArrayList<Any>())
            val details = arrayOf(DetailsModel())
            val review_thread: Thread = object : Thread() {
                override fun run() {
                    val restaurantsFinder = RestaurantsFinder(restaurant?.id)
                    reviews[0] = restaurantsFinder.reviewsList
                }
            }
            review_thread.start()
            val details_thread: Thread = object : Thread() {
                override fun run() {
                    val restaurantsFinder = RestaurantsFinder(restaurant?.id)
                    details[0] = restaurantsFinder.details
                }
            }
            details_thread.start()
            details_thread.join()
            review_thread.join()
            restaurantContentFragment = RestaurantContentFragment.newInstance(restaurant, savedRestaurants, reviews[0] as ArrayList<out Parcelable>, details[0])
        } catch (e: Exception) {
            e.printStackTrace()
        }
        fm!!.beginTransaction().replace(R.id._frameLayout, restaurantContentFragment!!, "rcf").addToBackStack(null).commit()
        invalidateOptionsMenu()
    }

    /**
     * callback method for searchRestaurantsFragment saveRestaurantsButton
     */
    override fun updateSaveListChoose_() {
        savedRestaurants = searchedRestaurantsFragment!!.savedRestaurants
    }

    /**
     * callback method for randomRestaurantsFragment saveRestaurants button
     */
    override fun updateSaveList() {
        savedRestaurants = randomRestaurantsFragment!!.savedRestaurants
    }

    /**
     * @return latitude as string
     */
    fun getLatitude(): String {
        return latitude.toString()
    }

    /**
     * @return longitude as String
     */
    fun getLongitude(): String {
        return longitude.toString()
    }

    override fun launchReviewActivity(reviews: ArrayList<ReviewsModel?>?) {
        val intent = Intent(this, ReviewsActivity::class.java)
        intent.putParcelableArrayListExtra("reviews", reviews)
        startActivity(intent)
    }

    /**
     * local class to retrieve current location from gps
     */
    internal inner class UserLocationListener : LocationListener {
        override fun onLocationChanged(location: Location) {
            if (location != null) {
                latitude = location.latitude
                longitude = location.longitude
            }
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }
}