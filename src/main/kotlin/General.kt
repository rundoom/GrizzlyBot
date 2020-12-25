import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.annotations.SerializedName
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.File
import java.util.concurrent.TimeUnit


val gson = Gson()
val parser = JsonParser()
val config = gson.fromJson<Config>(File("config.json").readText())

const val baseContextPrompt = "Гризли: название одного либо нескольких американских подвидов бурого медведя. Распространён преимущественно на Аляске (включая прилегающие острова) и в западных районах Канады. Небольшая популяция этих животных сохранилась в континентальной части США в Монтане, в районе Йеллоустона и на северо-западе Вашингтона. Факт о гризли: "

val client = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .build()!!

val requestBuilder = Request.Builder()

data class Config(
    @SerializedName("bot_token") val botToken: String,
    @SerializedName("chron_mask_for_launching") val chronMaskForLaunching: String,
    @SerializedName("channel_id") val channelId: Long
)

data class PorfRequest(
    @SerializedName("length") val length: Int,
    @SerializedName("prompt") val prompt: String
)