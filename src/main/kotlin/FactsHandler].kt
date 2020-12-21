import com.github.salomonbrys.kotson.array
import com.github.salomonbrys.kotson.get
import com.github.salomonbrys.kotson.string

fun collectFacts() = sequence {
    while (true) {
        val strResult = client.newCall(request).execute().body()!!.string()
        val results = parser.parse(strResult)["replies"].array.map { it.string }
        yieldAll(results)
    }
}