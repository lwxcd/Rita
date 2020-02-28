package net.logiase.rita.conf

import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import java.io.File

object Conf {

    lateinit var conf: ConfData

    private var ifResponse: Boolean = true

    private val confFile = File("conf.json")

    init {
        if (confFile.exists()) {
            conf = Json(JsonConfiguration.Stable).parse(ConfData.serializer(), confFile.readText())
        } else {
            print("conf.json Not Found")
        }
    }

    fun checkPermission(id: Long): Int {
        if (id == conf.master) return 1
        if (ifResponse) return 0
        if (id in conf.blockAccounts) return -1
        return 0
    }

    fun addBlock(id: Long) {
        if (id !in conf.blockAccounts) {
            conf.blockAccounts.add(id)
            saveConf()
        }
    }

    fun removeBlock(id: Long) {
        if (id in conf.blockAccounts) {
            conf.blockAccounts.remove(id)
            saveConf()
        }
    }

    private fun saveConf() {
        confFile.writeText(Json(JsonConfiguration.Stable).stringify(ConfData.serializer(), conf))
    }

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

