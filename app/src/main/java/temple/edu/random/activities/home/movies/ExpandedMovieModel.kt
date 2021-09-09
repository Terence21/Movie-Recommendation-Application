package temple.edu.random.activities.home.movies

data class ExpandedMovieModel(
    val previewMovie: PreviewMovie,
    val synopsis: String,
    val genre: String?,
    val cast: List<CastMemberModel>
)

data class CastMemberModel(val name: String, val character: String, val imageUrl: String)