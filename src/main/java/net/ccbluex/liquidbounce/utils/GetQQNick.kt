package net.ccbluex.liquidbounce.utils

import net.ccbluex.liquidbounce.utils.ClientUtils.LOGGER
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class GetQQNick {
    fun getQQNick(): String {
        val apiUrl =
            "https://h5.qzone.qq.com/proxy/domain/vip.qzone.qq.com/fcg-bin/fcg_get_vipinfo_mobile?get_all=1&uin=" + QQUtils.getAllQQ() + "&g_tk=800771428"
        try {
            val url = URL(apiUrl)
            with(url.openConnection() as HttpURLConnection) {
                requestMethod = "GET"

                inputStream.bufferedReader().use {
                    val response = it.readText()
                    val jsonObject = JSONObject(response)

                    val sub = jsonObject.getJSONObject("data")
                    return "${sub.getString("nick")}"
                }
            }
        } catch (e: Exception) {
            LOGGER.error("[QQUtils] Failed Get QQ Nick.")
            return "何树友"
        }
    }
}