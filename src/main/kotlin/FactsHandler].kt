import com.github.salomonbrys.kotson.array
import com.github.salomonbrys.kotson.get
import com.github.salomonbrys.kotson.string
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.RequestBody

fun collectFacts(postFix: String = "Гризли") = sequence {
    while (true) {
        try {
            val strResult = client.newCall(buildRequest(postFix)).execute().body()!!.string()
            val results = parser.parse(strResult)["replies"].array.map { it.string }
            yieldAll(results)
        } catch (e: Exception){
        }
    }
}

fun buildRequest(postFix: String): Request {
    val requestEntity = PorfRequest(50, "$baseContextPrompt$postFix")
    return requestBuilder.url("https://pelevin.gpt.dobro.ai/generate/")
            .post(RequestBody.create(MediaType.parse("text/plain"), gson.toJson(requestEntity)))
            .build()!!
}