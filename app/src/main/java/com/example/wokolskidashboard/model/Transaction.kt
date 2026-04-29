package com.example.wokolskidashboard.model
import java.util.UUID

/**
 * Model reprezentujący pojedynczy wpis w księdze Wokulskiego.
 */
data class Transaction(
    val id: String = UUID.randomUUID().toString(), // Unikalny klucz dla LazyColumn
    val title: String,                            // Nazwa towaru/celu
    val amount: Double,                           // Kwota w rublach
    val isExpense: Boolean,                       // true = Koszt, false = Zysk
    val isUnnecessary: Boolean = false            // Specjalna flaga Wokulskiego (dla wydatków)
)

