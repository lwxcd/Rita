package net.logiase.rita

import net.logiase.rita.conf.Conf
import net.logiase.rita.service.pixivc.pixivc
import net.mamoe.mirai.Bot
import net.mamoe.mirai.alsoLogin
import net.mamoe.mirai.contact.sendMessage
import net.mamoe.mirai.event.events.MemberJoinEvent
import net.mamoe.mirai.event.subscribeAlways
import net.mamoe.mirai.event.subscribeMessages
import net.mamoe.mirai.message.data.*
import net.mamoe.mirai.qqandroid.Bot
import net.mamoe.mirai.qqandroid.QQAndroid
import net.mamoe.mirai.utils.FileBasedDeviceInfo
import net.mamoe.mirai.utils.SilentLogger

suspend fun main() {
    val account = Conf.conf.account

    val bot = QQAndroid.Bot(
        account.account,
        account.password
    ) {
        +FileBasedDeviceInfo
        networkLoggerSupplier = { SilentLogger }
    }.alsoLogin()

    bot.baseConfFunction()

    //Parrot.parrot(bot)
    bot.pixivc()

    bot.join()
}

fun Bot.baseConfFunction() {

    // 另开线程防止堵塞
    // GlobalScope / suspend

    this.subscribeAlways<MemberJoinEvent> {
        this.group.sendMessage("欢迎新大佬入群")
    }

    this.subscribeMessages {
        case("屏蔽列表") {
            reply(Conf.conf.blockAccounts.toString())
        }

        startsWith("/unblock") {
            if (Conf.checkPermission(sender.id) != 1) return@startsWith
            this.message.forEach {
                if (it is At) {
                    Conf.removeBlock(it.target)
                }
            }
            reply(Conf.conf.blockAccounts.toString())
        }

        startsWith("/block") {
            if (Conf.checkPermission(sender.id) != 1) return@startsWith
            this.message.forEach {
                if (it is At) {
                    Conf.addBlock(it.target)
                }
            }
            reply(Conf.conf.blockAccounts.toString())
        }

        case("/switch block") {
            if (Conf.checkPermission(sender.id) != 1) return@case
            reply(Conf.limit().toString())
        }
    }

}