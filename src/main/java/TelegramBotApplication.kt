import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.ApiContext
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow

class TelegramBotApplication

fun main(){
    ApiContextInitializer.init()
    TelegramBotsApi().registerBot(Bot())
}

class Bot: TelegramLongPollingBot() {
    override fun getBotUsername() = "HomeTskOOPbot"

    override fun getBotToken() = "1377140018:AAF5ay0Ud3IlvGhpD08TwtSmp3uHBlLwQSA"

    override fun onUpdateReceived(update: Update) {
        if (update.message.text == "/start"){
            val keyboard = ReplyKeyboardMarkup()
            keyboard.keyboard = listOf(
                    KeyboardRow().apply {
                        add(element = KeyboardButton( "Добавить товар"))
                    },KeyboardRow().apply {
                        add(element = KeyboardButton( "Посмотреть доступные товары"))
                    }
            )
            execute(SendMessage()
                    .setReplyMarkup(keyboard)
                    .setChatId(update.message.chatId).setText("Hi, Bullshit"))
        }
        if (update.message.text == "Добавить товар"){
            execute(SendMessage()
                    .setChatId(update.message.chatId).setText("Введите название товара"))
        }
    }

}