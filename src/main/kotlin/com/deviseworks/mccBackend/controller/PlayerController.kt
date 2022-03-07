package com.deviseworks.mccBackend.controller

import com.deviseworks.mccBackend.domain.Player
import com.deviseworks.mccBackend.domain.PlayerConnection
import com.deviseworks.mccBackend.domain.PlayerService
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class PlayerController(
    private val playerService: PlayerService
) {

    val onlineList = mutableListOf<Player>()

    /**
     * プレイヤーの一覧を返します。
     * パラメータに何も指定しない場合は、オンラインのユーザーのみを
     * 返却します。
     *
     * @param offline オフラインのユーザも含める
     * @return プレイヤーリスト
     */
    @GetMapping("/api/player/list")
    @ResponseBody
    fun getPlayerList(@RequestParam(required=false) offline: Boolean = false): String {
        return if(offline){
            Json.encodeToString(playerService.findAll().toMutableList())
        }else{
            Json.encodeToString(onlineList)
        }
    }


    /**
     * プレイヤーが接続したときの処理。
     * bodyをPlayerにデシリアライズして、DBに格納する
     *
     * @param body Playerにデシリアライズ可能なJSON
     */
    @PostMapping("/api/player/join")
    fun playerJoinEvent(@RequestBody body: String){
        // bodyをPlayerにデシリアライズ
        val player = Player().parse(body)?:return

        // オンラインにする
        player.status = PlayerConnection.ONLINE.status

        // DBにプレイヤーを追加
        onlineList.add(player)
        playerService.save(player)
    }


    /**
     * プレイヤーが切断したときの処理。
     * パラメータのuuidをもとに、ステータスを変更してDBに格納
     *
     * @param uuid プレイヤーのUUID
     */
    @PostMapping("/api/player/quit")
    fun playerQuitEvent(
        @RequestParam uuid: String
    ){
        // オンラインリストから削除
        onlineList.mapIndexed { i, p ->
            if(p.uuid == uuid){
                onlineList.removeAt(i)
            }
        }

        // DBからユーザを取得
        val player = playerService.getByUUID(uuid)

        // ユーザの接続情報を上書き
        player.status = PlayerConnection.OFFLINE.status

        // 書き込み
        playerService.save(player)
    }


    /**
     * UUIDを指定してユーザ情報を取得する
     *
     * @param uuid UUID
     */
    @GetMapping("/api/player/{uuid}")
    @ResponseBody
    fun getPlayerByUUID(
        @PathVariable uuid: String
    ): ResponseEntity<String>{
        return try{
            ResponseEntity(Json.encodeToString(Player.serializer(), playerService.getByUUID(uuid)), HttpStatus.OK)
        }catch(e: Exception){
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

}