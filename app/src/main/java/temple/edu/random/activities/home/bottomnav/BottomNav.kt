package temple.edu.random.activities.home.bottomnav

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.LinearLayout
import androidx.core.view.forEach
import androidx.navigation.NavController
import androidx.navigation.findNavController
import temple.edu.random.Global.Companion.TAG
import temple.edu.random.R
import temple.edu.random.databinding.BottomNavigationBinding

class BottomNavView(ctx: Context, attrs: AttributeSet?) :
    LinearLayout(ctx, attrs) {

    var controller: NavController? = null
    private val binding: BottomNavigationBinding =
        BottomNavigationBinding.inflate(LayoutInflater.from(ctx), this, true)
    private val menuItemClickListener by lazy { MenuItemClickListener() }

    init {
        binding.bottomNavigationView.menu.forEach {
            it.setOnMenuItemClickListener(menuItemClickListener)
        }
    }

    private inner class MenuItemClickListener :
        MenuItem.OnMenuItemClickListener {
        override fun onMenuItemClick(item: MenuItem?): Boolean {
            val destination = item?.let {
                val current = findCurrentItem(binding.bottomNavigationView.selectedItemId)
                when (item.itemId) {
                    R.id.bottom_nav_menu_favorite -> {
                        menuItemDestinationHelper(current, MenuItemEnum.FAVORITES)
                    }
                    R.id.bottom_nav_menu_landing -> {
                        menuItemDestinationHelper(current, MenuItemEnum.LANDING)
                    }
                    R.id.bottom_nav_menu_search -> {
                        menuItemDestinationHelper(current, MenuItemEnum.SEARCH)
                    }
                    else -> {
                        menuItemDestinationHelper(current, MenuItemEnum.BAD)
                        return false
                    }
                }
            }
            item.isChecked = true
            if (destination != null) {
                Log.i(TAG, "onMenuItemClick: ${destination.destination}")
                controller?.navigate(destination.destination)
            }
            return true
        }
    }

    private fun findCurrentItem(currentId: Int) =
        when (currentId) {
            R.id.bottom_nav_menu_favorite -> {
                MenuItemEnum.FAVORITES
            }
            R.id.bottom_nav_menu_landing -> {
                MenuItemEnum.LANDING
            }
            R.id.bottom_nav_menu_search -> {
                MenuItemEnum.SEARCH
            }
            else -> {
                MenuItemEnum.BAD
            }
        }

    private fun menuItemDestinationHelper(
        current: MenuItemEnum,
        destination: MenuItemEnum,
    ): MenuItemDestinationEnum =
        when (current) {
            MenuItemEnum.FAVORITES -> {
                when (destination) {
                    MenuItemEnum.SEARCH -> {
                        MenuItemDestinationEnum.FAVORITES_TO_SEARCH
                    }

                    MenuItemEnum.LANDING -> {
                        MenuItemDestinationEnum.FAVORITES_TO_LANDING
                    }
                    else -> {
                        MenuItemDestinationEnum.BAD_DESTINATION
                    }
                }
            }
            MenuItemEnum.LANDING -> {
                when (destination) {
                    MenuItemEnum.FAVORITES -> {
                        MenuItemDestinationEnum.LANDING_TO_FAVORITES
                    }
                    MenuItemEnum.SEARCH -> {
                        MenuItemDestinationEnum.LANDING_TO_SEARCH
                    }
                    else -> {
                        MenuItemDestinationEnum.BAD_DESTINATION
                    }
                }
            }
            MenuItemEnum.SEARCH -> {
                when (destination) {
                    MenuItemEnum.FAVORITES -> {
                        MenuItemDestinationEnum.SEARCH_TO_FAVORITES
                    }
                    MenuItemEnum.LANDING -> {
                        MenuItemDestinationEnum.SEARCH_TO_LANDING
                    }
                    else -> {
                        MenuItemDestinationEnum.BAD_DESTINATION
                    }
                }
            }
            else -> {
                MenuItemDestinationEnum.BAD_DESTINATION
            }
        }
}



