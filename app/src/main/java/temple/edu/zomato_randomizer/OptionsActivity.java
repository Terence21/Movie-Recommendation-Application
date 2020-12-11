package temple.edu.zomato_randomizer;

import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import temple.edu.zomato_randomizer.restaraunts.FindRestarauntsFragment;
import temple.edu.zomato_randomizer.restaraunts.UserRestarauntsFragment;

public class OptionsActivity extends AppCompatActivity {

    FrameLayout frame;
    TabLayout tabLayout;
    TabItem myRestaraunts;
    TabItem findRestaraunts;

    UserRestarauntsFragment userRestarauntsFragment;
    FindRestarauntsFragment findRestarauntsFragment;
    FragmentManager fm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        tabLayout = findViewById(R.id._tabLayout);
        frame = findViewById(R.id._frameLayout);
        myRestaraunts = findViewById(R.id._tabPreviousRestaraunts);
        findRestaraunts = findViewById(R.id._tabFindRestaraunts);

        fm = getSupportFragmentManager();
        //  userRestarauntsFragment = (UserRestarauntsFragment) fm.findFragmentById(R.layout.fragment_find_restaraunts);
        findRestarauntsFragment = (FindRestarauntsFragment) fm.findFragmentById(R.layout.fragment_find_restaraunts);


        userRestarauntsFragment = UserRestarauntsFragment.newInstance();

        fm.beginTransaction()
                .add(R.id._frameLayout, userRestarauntsFragment)
                .commit();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() == 0){
                    userRestarauntsFragment = UserRestarauntsFragment.newInstance();
                    fm.beginTransaction()
                            .replace(R.id._frameLayout, userRestarauntsFragment)
                            .commit();
                }
                if (tab.getPosition() == 1){ // findRestaraunts tab
                    Log.i("Selected:", "onTabSelected: selected");
                    findRestarauntsFragment = FindRestarauntsFragment.newInstance();
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



    }
