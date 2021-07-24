package temple.edu.random.activities.home.bottomnav
import temple.edu.random.R

enum class MenuItemEnum {
    FAVORITES, LANDING, SEARCH, BAD
}

enum class MenuItemDestinationEnum(val destination: Int) {
    FAVORITES_TO_LANDING(R.id.action_favoritesFragment_to_landingFragment),
    FAVORITES_TO_SEARCH(R.id.action_favoritesFragment_to_searchFragment),
    LANDING_TO_FAVORITES(R.id.action_landingFragment_to_favoritesFragment),
    LANDING_TO_SEARCH(R.id.action_landingFragment_to_searchFragment),
    SEARCH_TO_FAVORITES(R.id.action_searchFragment_to_favoritesFragment),
    SEARCH_TO_LANDING(R.id.action_searchFragment_to_landingFragment),
    BAD_DESTINATION(-1)
}