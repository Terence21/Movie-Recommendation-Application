package temple.edu.random.activities.home.movies

data class ExpandedMovieModel(
    val previewMovie: PreviewMovie,
    val director: String,
    val synopsis: String,
    val cast: List<CastMemberModel>
)

data class CastMemberModel(val name: String, val character: String, val imageUrl: String)