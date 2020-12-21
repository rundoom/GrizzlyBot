import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.dispatcher.message
import com.github.kotlintelegrambot.extensions.filters.Filter
import okhttp3.logging.HttpLoggingInterceptor


val bot = bot {
    token = config.botToken
    logLevel = HttpLoggingInterceptor.Level.NONE

    dispatch {
        command("get_facts") { bot, update ->
            collectFacts().take(3).forEach {
                bot.sendMessage(chatId = config.channelId, text = "Гризли $it")
            }
        }
    }
}

fun initTelegramBot() {
    bot.startPolling()
}
