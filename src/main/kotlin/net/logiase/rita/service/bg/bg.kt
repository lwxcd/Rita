package net.logiase.rita.service.bg

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.mamoe.mirai.Bot
import net.mamoe.mirai.contact.Contact
import net.mamoe.mirai.contact.sendMessage
import net.mamoe.mirai.event.subscribeGroupMessages
import net.mamoe.mirai.message.sendAsImageTo
import java.net.URL

fun Bot.randomBg() {
    this.subscribeGroupMessages {

        case("一张壁纸") {
            GlobalScope.launch {
                getBg(contact = group)
            }
        }

    }
}

suspend fun getBg(contact: Contact) {
    withContext(Dispatchers.IO) {
        when ((0..1).random()) {
            0 -> try {
                URL("https://s0.xinger.ink/acgimg/acgurl.php").sendAsImageTo(contact)
            } catch (e: Exception) {
                contact.sendMessage("0: $e")
            }

            1 -> try {
                URL("http://www.dmoe.cc/random.php").sendAsImageTo(contact)
            } catch (e: Exception) {
                contact.sendMessage("1: $e")
            }

            2 -> try {
                URL("https://acg.toubiec.cn/random").sendAsImageTo(contact)
            } catch (e: Exception) {
                contact.sendMessage("2: $e")
            }

            else -> return@withContext
        }
    }
}