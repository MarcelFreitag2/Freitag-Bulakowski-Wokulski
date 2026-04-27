package com.example.wokolskidashboard.ui.components


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.wokolskidashboard.model.Transaction

@Composable
fun IncomeForm(
    onTransactionAdded: (Transaction) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "Nowy Przychód (Subiekt Rzecki)", style = MaterialTheme.typography.titleMedium)

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Co sprzedano?") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Kwota (Ruble)") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (name.isNotBlank() && amount.isNotBlank()) {
                    val tx = Transaction(
                        name = name,
                        amount = amount.toDoubleOrNull() ?: 0.0,
                        isExpense = false
                    )
                    onTransactionAdded(tx)
                    name = ""
                    amount = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Zaksięguj Zysk")
        }
    }
}