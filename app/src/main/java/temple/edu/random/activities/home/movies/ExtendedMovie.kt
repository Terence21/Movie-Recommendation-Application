package temple.edu.random.activities.home.movies

data class ExtendedMovie(
    val previewMovie: PreviewMovie,
    val director: String,
    val synopsis: String,
    val cast: List<CastMember>
)

data class CastMember(val name: String, val character: String, val imageUrl: String)