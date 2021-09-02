package temple.edu.random.globals

import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.*
import temple.edu.random.activities.home.movies.PreviewMovie
import temple.edu.random.globals.MovieManager.MOVIES_TYPE.*
import java.io.IOException

class MovieManager : EventManager<MovieEventListener>() {
    private val movieListeners by lazy { mutableListOf<MovieEventListener>() }
    lateinit var currentPreviewMovie: PreviewMovie

    override fun subscribeToEvent(subscriber: MovieEventListener) {
        movieListeners.add(subscriber)
    }

    override fun unsubscribeToEvent(unsubscriber: MovieEventListener) {
        movieListeners.remove(unsubscriber)
    }

    fun updateCurrentPreviewMovie(movie: PreviewMovie) {
        currentPreviewMovie = movie
        movieListeners.forEach { it.handleMovieUpdate(movie) }
    }

    fun initializeLandingMovies() = runBlocking<Boolean> {
        var result = true
        try {
            val nowPlayingJob = launch { initializeNowPlayingMovies() }
            val topRatedJob = launch { initializeTopRatedMovies() }
            val popularJob = launch { initializePopularMovies() }
            nowPlayingJob.start()
            topRatedJob.start()
            popularJob.start()
            joinAll(nowPlayingJob, topRatedJob, popularJob)
        } catch (e: Exception){
            e.printStackTrace()
            result = false
        }
        result
    }

    private suspend fun initializeNowPlayingMovies() = coroutineScope {
        val url = "$BASE_MOVIE_URL${NOW_PLAYING.param}$PAGE1"
        fetchMovieSet(url, NOW_PLAYING)
    }

    private suspend fun initializeTopRatedMovies() = coroutineScope {
        val url = "$BASE_MOVIE_URL${TOP_RATED.param}$PAGE1"
        fetchMovieSet(url, TOP_RATED)
    }

    private suspend fun initializePopularMovies() = coroutineScope {
        val url = "$BASE_MOVIE_URL${POPULAR.param}$PAGE1"
        fetchMovieSet(url, POPULAR)
    }

    private suspend fun fetchMovieSet(url: String, type: MOVIES_TYPE) = coroutineScope {
        Log.i("Movies", "fetchMovieSet: running")
        launch {
            val client = OkHttpClient()
            var gson = Gson()
            val request = Request.Builder()
                .url(url)
                .addHeader(
                    AUTHORIZATION,
                    "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2NmU1NTRhMzBhMzRkNGJlY2FmYjZmNGY3Y2ZkNzZmYyIsInN1YiI6IjYxMmU2NzRjMjIzZTIwMDAyZmRlMDgxZCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.O4SmPGWsWkF-6nr_AtEZ_DRDwCd9tvkR5V7DSC94Zoo"
                )
                .addHeader(CONTENT_TYPE, CONTENT_TYPE_VALUE)
                .build()
            val call = client.newCall(request)
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) = Global.tryNotError {
                    var jsonMap: Map<String, Any> = HashMap<String, Any>()
                    val gson = Gson()
                    Log.i("MOVIES", "json: $response ")
                    jsonMap = gson.fromJson(
                        response.body.toString().trim(),
                        jsonMap.javaClass
                    ) as Map<String, Any>
                    Log.i("MOVIES:", "onResponse: ")
                    jsonMap.forEach {
                        Log.i("MOVIES", "onResponse: ${it.key} : ${it.value}")
                    }
                }
            })
        }.start()
    }


    interface MovieLandingUpdater {
        fun moviesSucessfullyLoaded()
        fun moviesFailedLoaded()
    }

    private enum class MOVIES_TYPE(val param: String) {
        NOW_PLAYING(NOW_PLAYING_PARAM), TOP_RATED(TOP_RATED_PARAM), POPULAR(POPULAR_PARAM)
    }

    private companion object {
        const val AUTHORIZATION = "Authorization"
        const val CONTENT_TYPE = "Content-Type"
        const val CONTENT_TYPE_VALUE = "application/json;charset=utf-8"
        const val BASE_MOVIE_URL = "https://api.themoviedb.org/3/movie/"
        const val NOW_PLAYING_PARAM = "now_playing"
        const val TOP_RATED_PARAM = "top_rated"
        const val POPULAR_PARAM = "popular"
        const val PAGE1 = "?page=1"
    }
}