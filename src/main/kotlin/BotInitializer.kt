import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.callbackQuery
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.dispatcher.message
import com.github.kotlintelegrambot.entities.InlineKeyboardButton
import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup
import com.github.kotlintelegrambot.extensions.filters.Filter
import okhttp3.logging.HttpLoggingInterceptor


private const val REPOST_MESSAGE = "repost_message"

val bot = bot {
    token = config.botToken
    logLevel = HttpLoggingInterceptor.Level.NONE

    dispatch {
        command("get_facts") { bot, update ->
            collectFacts().take(3).forEach {
                bot.sendMessage(chatId = config.channelId, text = "Гризли$it")
            }
        }

        message(Filter.Text) { bot, update ->
            val text = update.message!!.text!!
            if ("гризли" !in text) {
                bot.sendMessage(
                        update.message!!.chat.id,
                        text = "Текст должен содержать слово \"гризли\""
                )
                return@message
            }

            collectFacts(text).take(6).forEach {
                bot.sendMessage(
                        update.message!!.chat.id,
                        text = "${update.message!!.text}$it",
                        replyMarkup = generateMarkupForCandidates()
                )
            }
        }

        callbackQuery(REPOST_MESSAGE) { bot, update ->
            bot.sendMessage(config.channelId, update.callbackQuery!!.message!!.text!!)

            bot.deleteMessage(
                    update.callbackQuery?.message?.chat!!.id,
                    update.callbackQuery?.message!!.messageId
            )
        }
    }
}

private fun generateMarkupForCandidates() = InlineKeyboardMarkup(
        listOf(
                listOf(
                        InlineKeyboardButton(
                                text = "Post to channel",
                                callbackData = REPOST_MESSAGE
                        )
                )
        )
)

fun initTelegramBot() {
    bot.startPolling()
}
