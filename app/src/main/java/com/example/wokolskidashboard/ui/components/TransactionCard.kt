package com.example.wokolskidashboard.ui.components


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.wokolskidashboard.model.Transaction

@Composable
fun TransactionCard(
    transaction: Transaction,
    modifier: Modifier = Modifier
) {
    // kolorystka zalezna od typu transakcii
    val color = if (transaction.isExpense) Color(0xFFD32F2F) else Color(0xFF388E3C)
    val symbol = if (transaction.isExpense) "-" else "+"

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = transaction.name, style = MaterialTheme.typography.bodyLarge)
                if (transaction.isExpense && transaction.isUnnecessary) {
                    Text(
                        text = "Wydatek zbyteczny!",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
                    )
                }
            }
            Text(
                text = "$symbol ${transaction.amount} Rub",
                color = color,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}