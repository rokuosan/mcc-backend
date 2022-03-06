package com.deviseworks.mccBackend.domain

import org.jetbrains.annotations.NotNull
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name="players")
data class Player(
    @Id
    @NotNull
    @Column(name="uuid")
    var uuid: String = "",
    @NotNull
    @Column(name="name")
    var name: String = "",
    @Column(name="display_name")
    var displayName: String? = null,
    @Column(name="admin_flag")
    var isAdmin: Boolean = false
)