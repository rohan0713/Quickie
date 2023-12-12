package com.delivery.quickie.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "cart")
data class CartItems(
    val foodName : String,
    val quantity : Int,
    val amount : Int,
    @PrimaryKey
    val id : String = UUID.randomUUID().toString()
)