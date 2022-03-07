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
@Table(name="players")
data class Player(
    // UUID
    @Id
    @NotNull
    @Column(name="uuid")
    @SerialName("uuid")
    var uuid: String = "",

    // Name
    @NotNull
    @SerialName("name")
    @Column(name="name")
    var name: String = "",

    @SerialName("display_name")
    @Column(name="display_name")
    var displayName: String? = null,

    @SerialName("admin_flag")
    @Column(name="admin_flag")
    var isAdmin: Boolean = false
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
    fun save(player: Player) = playerRepository.save(player)
    fun delete(player: Player) = playerRepository.delete(player)
    fun deleteById(uuid: String) = playerRepository.deleteById(uuid)
}
/*
Expected JSON Example:
{
    "uuid": "This is UUID v4.",
    "name": "Foo",
    "display_name": "Bar",
    "admin_flag": false
}
 */
