package net.logiase.rita.service.pixivc

import kotlinx.coroutines.*
import net.logiase.rita.conf.Conf
import net.mamoe.mirai.Bot
import net.mamoe.mirai.contact.Contact
import net.mamoe.mirai.contact.sendMessage
import net.mamoe.mirai.event.subscribeGroupMessages
import net.mamoe.mirai.message.sendAsImageTo
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL

object Pixivc {

    // Api服务
    private val SERVICE: PixivicApiService by lazy {
        Retrofit.Builder()
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.pixivic.com/")
            .build()
            .create(PixivicApiService::class.java)
    }

    /**
     * 搜索画作
     * @param keyword 关键字
     * @param contact 来源 QQ
     */
    suspend fun searchIllust(keyword: String, contact: Contact) =
        withTimeoutOrNull(10 * 1000) {
            //contact.bot.logger.info("into pixivic")
            // IO线程
            withContext(Dispatchers.IO) {
                val response = SERVICE.search(keyword = keyword)
                if (response.data.isNullOrEmpty()) {
                    contact.sendMessage("无结果")
                    return@withContext
                }
                try {
                    //contact.bot.logger.info("start upload")
                    for (url in response.data.random().imageUrls) {
                        URL(url.original.replace("pximg.net", "pixiv.cat"))
                            .sendAsImageTo(contact)
                            .recallIn(60 * 1000)
                    }
                } catch (e: Exception) {
                    contact.bot.logger.info(e)
                }
                //contact.bot.logger.info("finish pixivic")
            }
        }

}

/**
 * 扩展函数，实现pixivc功能
 */
suspend fun Bot.pixivic() {

    this.subscribeGroupMessages {

        startsWith("pixivic", removePrefix = true) {
            bot.logger.info("start pixivic")
            if (Conf.checkPermission(sender.id) != -1) {
                GlobalScope.launch {
                    Pixivc.searchIllust(it.removePrefix(" "), group)
                }
            }

        }

    }

}
