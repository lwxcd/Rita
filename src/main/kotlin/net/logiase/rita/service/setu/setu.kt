package net.logiase.rita.service.setu

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

fun Bot.randomSetu()
{
    this.subscribeGroupMessages
    {
        case("一张瑟图")
        {
            GlobalScope.launch
            {
                getSetu(contact = group)
            }
        }
    }
}
suspend fun getSetu(contact: Contact)
{
    withContext(Dispatchers.io)
    {
        try {
            URL("https://acg.toubiec.cn/random").sendAsImageTo(contact)
        }
        catch(Exception e) {
            contact.sendMessage("error: $e")
        }
        return@withContext
    }
}