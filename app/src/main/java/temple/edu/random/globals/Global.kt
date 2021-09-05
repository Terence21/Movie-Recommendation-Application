package temple.edu.random.globals

import android.app.Application
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Global : Application() {

    val movieManager: MovieManager by lazy { MovieManager() }

    override fun onCreate() {
        super.onCreate()
        application = this
        CoroutineScope(Dispatchers.IO).launch {
            with(movieManager) {
                Log.i(TAG, "BEGIN REQUESTING LANDING PREVIEW MOVIES")
                initializeLandingMovies()
                Log.i(TAG, "NOW PLAYING PREVIEW MOVIES: $nowPlayingMovies")
                Log.i(TAG, "TOP RATED PREVIEW MOVIES: $topRatedMovies")
                Log.i(TAG, "POPULAR PREVIEW MOVIES: $popularMovies")
            }
        }
    }

    companion object {
        lateinit var application: Global
        const val TAG = "GLOBAL"
        fun safeUnit(error: Exception = Exception(), input: () -> Unit) {
            try {
                input.invoke()
            } catch (e: Exception) {
                Log.i("GLOBAL", "safeUnitException($input): ${error.printStackTrace()}")
            }
        }

        //change to inline
        fun safeNotNull(vararg args: Any?, input: () -> Unit) {
            try {
                for (value in args) {
                    if (value == null) {
                        throw Exception("NULL VALUE($value} IN ARGS")
                    }
                }
                input.invoke()
            } catch (e: Exception) {
                Log.i(TAG, "safeNotNull: ${e.printStackTrace()}")
            }
        }

        fun tryNotError(block: () -> Unit) {
            try {
                block.invoke()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun <T> tryNotErrorExpression(block: () -> T?): T? {
            var output: T? = null
            try {
                output = block.invoke()
            } catch (e: Exception) {
                e.printStackTrace()
                Log.i("MOVIES", "tryNotErrorExpression: failed: ${e.message}")
            }
            return output
        }
    }
}