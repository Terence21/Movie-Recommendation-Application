package temple.edu.random.storage

import android.content.Context
import android.util.Log
import temple.edu.random.restaraunts.RestaurantsFinder
import java.io.File
import java.io.FileWriter
import java.lang.Exception
import java.util.*

class DataFinder(  // because of boolean// because of boolean
        var context: Context) {
    /**
     *
     * @return list of categories as a String form file categories.txt
     */
    val categories: ArrayList<String>?
        get() {
            val path = context.filesDir.toString() + "/categories.txt"
            try {
                val file = File(path)
                var hasCategoriesWritten = false
                if (file.exists()) {
                    val scanner = Scanner(file)
                    val categories = ArrayList<String>()
                    if (scanner.hasNextBoolean()) {
                        hasCategoriesWritten = scanner.nextBoolean()
                    }
                    if (hasCategoriesWritten) {
                        scanner.nextLine() // because of boolean
                        while (scanner.hasNextLine()) {
                            val line = scanner.nextLine()
                            categories.add(line)
                        }
                        scanner.close()
                        return categories
                    }
                }
                if (!file.exists() || !hasCategoriesWritten) {
                    writeCategories()
                    val scanner = Scanner(file)
                    val categories = ArrayList<String>()
                    scanner.nextLine() // because of boolean
                    while (scanner.hasNextLine()) {
                        val line = scanner.nextLine()
                        categories.add(line)
                    }
                    scanner.close()
                    return categories
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

    /**
     * write all categories to categories.txt in context file path
     */
    fun writeCategories() {
        Log.i("writeFile", "writeCategories: writing file")
        val path = context.filesDir.toString() + "/categories.txt"
        try {
            val file = File(path)
            val writer = FileWriter(file, false)
            writer.write("true")
            writer.write('\n'.toInt())
            val restaurantsFinder = RestaurantsFinder()
            val categories = restaurantsFinder.categoriesList
            for (item in categories!!) {
                writer.write(item)
                writer.write('\n'.toInt())
            }
            writer.flush()
            writer.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}