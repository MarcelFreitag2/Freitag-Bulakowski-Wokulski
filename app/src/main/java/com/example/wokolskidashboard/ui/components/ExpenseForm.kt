package com.example.wokulskidashboard.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.wokolskidashboard.model.Transaction

@Composable
fun ExpenseForm(
    onAddExpense: (Transaction) -> Unit,
    modifier: Modifier = Modifier
) {
    var title by remember { mutableStateOf("") }
    var amountStr by remember { mutableStateOf("") }
    var isUnnecessary by remember { mutableStateOf(false) }
    var showErrors by remember { mutableStateOf(false) }

    // Przygotowanie wartości do sprawdzenia
    val amountDouble = amountStr.toDoubleOrNull()

    // JAWNE WARUNKI WALIDACJI:
    val isTitleError = showErrors && title.isBlank()

    // 1. Czy nie jest liczbą?
    val isNotANumber = amountStr.isNotEmpty() && amountDouble == null
    // 2. Czy jest mniejsza bądź równa zero?
    val isLessThanZero = amountDouble != null && amountDouble <= 0

    // Łączny błąd dla pola kwoty (aktywowany przy próbie zapisu lub gdy wpisano bzdury)
    val isAmountError = showErrors || isNotANumber || isLessThanZero

    Card(
        modifier = modifier.padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Księguj Wydatki (Wokulski)", style = MaterialTheme.typography.titleMedium)

            Spacer(modifier = Modifier.height(8.dp))

            // Nazwa wydatku
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Cel wydatku (np. Kareta)") },
                isError = isTitleError,
                supportingText = {
                    if (isTitleError) Text("Nazwa wydatku nie może być pusta!")
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Kwota z jasnym sprawdzaniem błędów
            OutlinedTextField(
                value = amountStr,
                onValueChange = { amountStr = it },
                label = { Text("Kwota w Rublach") },
                isError = isAmountError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                supportingText = {
                    if (isAmountError) {
                        if (amountStr.isBlank()) {
                            Text("Pole nie może być puste!")
                        } else if (isNotANumber) {
                            Text("To nie jest poprawna liczba!")
                        } else if (isLessThanZero) {
                            Text("Kwota nie może być mniejsza lub równa 0!")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(checked = isUnnecessary, onCheckedChange = { isUnnecessary = it })
                Text("Wydatek zbyteczny (np. kwiaty dla Izabeli)")
            }

            Button(
                onClick = {
                    // Blokada zapisu: tytuł nie może być pusty, kwota MUSI być liczbą i MUSI być > 0
                    if (title.isNotBlank() && amountDouble != null && amountDouble > 0) {
                        onAddExpense(
                            Transaction(
                                name = title,
                                amount = amountDouble,
                                isExpense = true
                            )
                        )
                        title = ""
                        amountStr = ""
                        isUnnecessary = false
                        showErrors = false
                    } else {
                        showErrors = true // Włącz czerwone błędy, jeśli warunki nie są spełnione
                    }
                },
                modifier = Modifier.align(Alignment.End),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Zapisz Koszt")
            }
        }
    }
}