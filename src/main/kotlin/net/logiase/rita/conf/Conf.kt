package net.logiase.rita.conf

import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import java.io.File
import kotlin.system.exitProcess

object Conf {


    var conf: ConfData

    // 是否回应屏蔽
    private var ifResponse: Boolean = true

    // 配置文件
    private val confFile = File("conf.json")

    init {
        if (confFile.exists()) {
            conf = Json(JsonConfiguration.Stable).parse(ConfData.serializer(), confFile.readText())
        } else {
            print("conf.json Not Found")
            exitProcess(-1)
        }
    }

    /**
     * 检查[id]再配置中的权限
     * @param id
     * @return 1-主人 0-可以回应 -1-拒绝回应
     */
    fun checkPermission(id: Long): Int {
        if (id == conf.master) return 1
        if (ifResponse) return 0
        if (id in conf.blockAccounts) return -1
        return 0
    }

    /**
     * 屏蔽[id]
     * @param id 账号
     */
    fun addBlock(id: Long) {
        if (id !in conf.blockAccounts) {
            conf.blockAccounts.add(id)
            saveConf()
        }
    }

    /**
     * 解除[id]屏蔽
     * @param id 账号
     */
    fun removeBlock(id: Long) {
        if (id in conf.blockAccounts) {
            conf.blockAccounts.remove(id)
            saveConf()
        }
    }

    /**
     * 保存配置
     */
    private fun saveConf() {
        confFile.writeText(Json(JsonConfiguration.Stable).stringify(ConfData.serializer(), conf))
    }

    /**
     * 切换屏蔽状态
     * @return 当前回应状态
     */
    fun limit(): Boolean {
        ifResponse = !ifResponse
        return ifResponse
    }

    @Serializable
    data class ConfData(
        @SerialName("account") val account: Account,
        @SerialName("block_accounts") val blockAccounts: MutableSet<Long>,
        @SerialName("master") val master: Long
    )

    @Serializable
    data class Account(
        val commit: String,
        val account: Long,
        val password: String
    )
}

