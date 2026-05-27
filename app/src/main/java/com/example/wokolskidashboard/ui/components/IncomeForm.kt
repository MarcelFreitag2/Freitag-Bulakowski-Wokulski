package com.example.wokolskidashboard.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.wokolskidashboard.model.Transaction

@Composable
fun IncomeForm(
    onTransactionAdded: (Transaction) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var showErrors by remember { mutableStateOf(false) }

    // Przygotowanie wartości do sprawdzenia
    val amountDouble = amount.toDoubleOrNull()

    // JAWNE WARUNKI WALIDACJI (Dokładnie takie same jak w ExpenseForm):
    val isNameError = showErrors && name.isBlank()

    // 1. Czy nie jest liczbą?
    val isNotANumber = amount.isNotEmpty() && amountDouble == null
    // 2. Czy jest mniejsza bądź równa zero?
    val isLessThanZero = amountDouble != null && amountDouble <= 0

    // Łączny błąd dla pola kwoty
    val isAmountError = showErrors || isNotANumber || isLessThanZero

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "Nowy Przychód (Subiekt Rzecki)", style = MaterialTheme.typography.titleMedium)

        // Nazwa przychodu z walidacją
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Co sprzedano?") },
            isError = isNameError,
            supportingText = {
                if (isNameError) Text("Nazwa towaru nie może być pusta!")
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Kwota przychodu z jasnym sprawdzaniem błędów (tekst / ujemne / zero)
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Kwota (Ruble)") },
            isError = isAmountError,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            supportingText = {
                if (isAmountError) {
                    if (amount.isBlank()) {
                        Text("Pole nie może być puste!")
                    } else if (isNotANumber) {
                        Text("To nie jest poprawna liczba!")
                    } else if (isLessThanZero) {
                        Text("Zysk musi być większy od zera!")
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                // Blokada zapisu: nazwa nie pusta, kwota musi być liczbą i musi być większa od 0
                if (name.isNotBlank() && amountDouble != null && amountDouble > 0) {
                    val tx = Transaction(
                        name = name,
                        amount = amountDouble,
                        isExpense = false
                    )
                    onTransactionAdded(tx)

                    // Czyszczenie pól po udanym zapisie
                    name = ""
                    amount = ""
                    showErrors = false
                } else {
                    // Aktywacja błędów w UI, jeśli warunki nie są spełnione
                    showErrors = true
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Zaksięguj Zysk")
        }
    }
}