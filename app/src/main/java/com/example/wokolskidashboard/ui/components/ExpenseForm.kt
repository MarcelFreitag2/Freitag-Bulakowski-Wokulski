package com.example.wokulskidashboard.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.wokolskidashboard.model.Transaction

@Composable
fun ExpenseForm(
    onAddExpense: (Transaction) -> Unit, // Callback do MainScreen
    modifier: Modifier = Modifier
) {
    // Lokalne stany dla pól tekstowych (UI state - dopuszczalne w formularzu)
    var title by remember { mutableStateOf("") }
    var amountStr by remember { mutableStateOf("") }
    var isUnnecessary by remember { mutableStateOf(false) }

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
                modifier = Modifier.fillMaxWidth()
            )

            // Kwota
            OutlinedTextField(
                value = amountStr,
                onValueChange = { amountStr = it },
                label = { Text("Kwota w Rublach") },
                modifier = Modifier.fillMaxWidth()
            )

            // Specjalny Switch Wokulskiego (Wydatek zbyteczny)
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isUnnecessary,
                    onCheckedChange = { isUnnecessary = it }
                )
                Text("Wydatek zbyteczny (np. kwiaty dla Izabeli)")
            }

            Button(
                onClick = {
                    val amount = amountStr.toDoubleOrNull() ?: 0.0
                    if (title.isNotBlank() && amount > 0) {
                        onAddExpense(
                            Transaction(
                                title = title,
                                amount = amount,
                                isExpense = true,
                                isUnnecessary = isUnnecessary
                            )
                        )
                        title = ""
                        amountStr = ""
                        isUnnecessary = false
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

