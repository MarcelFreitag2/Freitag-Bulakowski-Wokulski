package com.example.wokolskidashboard.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.wokolskidashboard.model.Transaction
import com.example.wokolskidashboard.ui.components.BalanceHeader
import com.example.wokolskidashboard.ui.components.IncomeForm
import com.example.wokolskidashboard.ui.components.TransactionCard
import com.example.wokulskidashboard.ui.components.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    // --- STAN APLIKACJI (State Hoisting) ---
    // Tu spływają dane z obu formularzy
    var transactions by remember { mutableStateOf(listOf<Transaction>()) }

    // Obliczanie salda na podstawie listy transakcji
    val currentBalance = transactions.sumOf {
        if (it.isExpense) -it.amount else it.amount
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Wokulski Dashboard", style = MaterialTheme.typography.titleLarge) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            // 1. ZADANIE 4: Nagłówek z saldem (Developer B)
            BalanceHeader(balance = currentBalance)

            // 2. Obszar z formularzami i listą
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // SEKCJA RZECKIEGO (PRZYCHODY)
                item {
                    Text(
                        text = "Księga Przychodów (Rzecki)",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    // ZADANIE 2: Formularz Developera A
                    IncomeForm(onTransactionAdded = { newIncome ->
                        transactions = transactions + newIncome
                    })
                }

                item { HorizontalDivider(thickness = 1.dp) }

                // SEKCJA WOKULSKIEGO (WYDATKI)
                item {
                    Text(
                        text = "Rejestr Wydatków (Wokulski)",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                    // ZADANIE 5: Formularz Developera B
                    ExpenseForm(onAddExpense = { newExpense ->
                        transactions = transactions + newExpense
                    })
                }

                item { HorizontalDivider(thickness = 2.dp) }

                // 3. ZADANIE 3 & 6: Historia operacji
                item {
                    Text(
                        text = "Ostatnie zapisy w księdze:",
                        style = MaterialTheme.typography.labelLarge
                    )
                }

                items(transactions.reversed()) { transaction ->
                    // Zgodnie z zadaniem 3: Wyświetlamy karty transakcji
                    TransactionCard(transaction = transaction)
                }
            }
        }
    }
}