package com.deviseworks.mccBackend.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.jetbrains.annotations.NotNull
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@kotlinx.serialization.Serializable
@Entity
@Table(name="PLAYERS")
data class Player(
    // UUID
    @Id
    @NotNull
    @Column(name="uuid")
    @SerialName("uuid")
    var uuid: String = "",

    // 名前
    @NotNull
    @SerialName("name")
    @Column(name="name")
    var name: String = "",

    // 表示名
    @SerialName("display_name")
    @Column(name="display_name")
    var displayName: String? = null,

    // 管理者フラグ
    @SerialName("admin_flag")
    @Column(name="admin_flag")
    var isAdmin: Boolean? = null,

    // 接続状況
    @SerialName("status")
    @Column(name="status")
    var status: String? = null
){
    fun parse(body: String): Player? {
        return Json.decodeFromString(body)
    }
}

@Repository
interface PlayerRepository: JpaRepository<Player, String>

@Service
class PlayerService(private val playerRepository: PlayerRepository){
    fun findAll(): List<Player> = playerRepository.findAll()
    fun getByUUID(uuid: String): Player = playerRepository.getById(uuid)
    fun save(player: Player) = playerRepository.save(player)
    fun delete(player: Player) = playerRepository.delete(player)
    fun deleteByUUId(uuid: String) = playerRepository.deleteById(uuid)
}

enum class PlayerConnection(val status: String) {
    ONLINE("ONLINE"),
    OFFLINE("OFFLINE"),
    AFK("AFK");
}
/*
Expected JSON Example:
{
    "uuid": "This is UUID v4.",
    "name": "Foo",
    "display_name": "Bar",
    "admin_flag": false,
    "status": "offline"
}
 */
