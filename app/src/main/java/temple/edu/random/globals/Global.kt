package temple.edu.random.globals

import android.app.Application
import android.util.Log

class Global : Application() {

    private val movieManager: MovieManager by lazy { MovieManager() }

    override fun onCreate() {
        super.onCreate()
        application = this
        movieManager.initializeLandingMovies().runCatching {
            if (this) Log.i("MOVIES", "onCreate: COMPLETED") else Log.i(
                "MOVIES",
                "onCreate: FAILED"
            )
        }
        Log.i("MOVIES", "onCreate: calling")
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

        fun tryNotErrorExpression(block: () -> Any): Any? {
            try {
                return block.invoke()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

    }
}