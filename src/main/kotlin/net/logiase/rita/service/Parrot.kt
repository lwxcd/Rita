package net.logiase.rita.service

import net.mamoe.mirai.Bot
import net.mamoe.mirai.event.subscribeAlways
import net.mamoe.mirai.event.subscribeGroupMessages
import net.mamoe.mirai.message.GroupMessage
import net.mamoe.mirai.message.data.Message
import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.messageRandom
import java.util.*

object Parrot {

    private val s = emptyMap<Long, Stack<MessageChain>>().toMutableMap()

    suspend fun parrot(bot: Bot) {
        bot.subscribeAlways<GroupMessage> {
            if (s[group.id].isNullOrEmpty()) {
                print("init stack ${group.id}\n")
                s[group.id] = Stack()
            }
            if (s[group.id]!!.search(message) == 1) {
                if (s[group.id]!!.size >= 3) {
                    group.sendMessage(message)
                    s[group.id]!!.clear()
                    return@subscribeAlways
                }
            } else {
                s[group.id]!!.clear()
            }
            s[group.id]!!.push(message)
        }
    }

}