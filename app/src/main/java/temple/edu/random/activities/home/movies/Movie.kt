package temple.edu.random.activities.home.movies

data class Movie(
    val title: String,
    val director: String,
    val genre: String,
    val synopsis: String,
    val imageUrl: String,
    val rating: Double
)

data class CastMember(
    val Rname: String,
    val characterName: String,
    val gender: Int,
    val popularity: Double,
    val order: Int,
)
