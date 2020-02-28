package net.logiase.rita.service.bg

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.mamoe.mirai.Bot
import net.mamoe.mirai.contact.Contact
import net.mamoe.mirai.event.subscribeGroupMessages

fun Bot.randomBg() {
    this.subscribeGroupMessages {

    }
}

suspend fun getBg(contact: Contact) {
    withContext(Dispatchers.IO) {

    }
}