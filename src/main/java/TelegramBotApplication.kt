import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.ApiContext
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object Users : IntIdTable() {
    val name = varchar("name", 50).index()
    val city = reference("city", Cities)
    val age = integer("age")
}

object Cities: IntIdTable() {
    val name = varchar("name", 50)
}

class User(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<User>(Users)

    var name by Users.name
    var city by City referencedOn Users.city
    var age by Users.age
}

class City(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<City>(Cities)

    var name by Cities.name
    val users by User referrersOn Users.city
}


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
    }

}