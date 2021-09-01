package temple.edu.random

import android.app.Application
import android.util.Log
import java.lang.Exception

class Global : Application() {
    companion object {
        const val TAG = "GLOBAL"
        fun safeUnit(error: Exception = Exception(), input: () -> Unit) {
            try {
                input.invoke()
            } catch (e: Exception) {
                Log.i("GLOBAL", "safeUnitException($input): ${error.printStackTrace()}")
            }
        }
        //change to inline
        fun safeNotNull(vararg args: Any?, input: () -> Unit){
            try {
                for (value in args){
                    if (value == null){
                        throw Exception("NULL VALUE($value} IN ARGS")
                    }
                }
                input.invoke()
            } catch (e: Exception){
                Log.i(TAG, "safeNotNull: ${e.printStackTrace()}")
            }
        }

    }
}