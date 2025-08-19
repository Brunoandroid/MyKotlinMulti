package com.example.mykoltinmulti.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mykoltinmulti.Todo

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoScreen()
        }
    }
}

@Composable
fun TodoScreen(
    viewModel: TodoViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    var input by remember { mutableStateOf(TextFieldValue("")) }
    var items by remember { mutableStateOf(viewModel.list()) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Box(modifier = Modifier.padding(all = 16.dp)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Text(text = "KMP To-Do", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    OutlinedTextField(
                        value = input,
                        onValueChange = { input = it },
                        label = { Text("New task") },
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(Modifier.width(8.dp))
                    Button(onClick = {
                        val text = input.text
                        if (text.isNotBlank()) {
                            viewModel.add(text)
                            items = viewModel.list()
                            input = TextFieldValue("")
                        }
                    }) {
                        Text("Add")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(items, key = { it.id }) { todo ->
                        TodoRow(
                            todo = todo,
                            onToggle = {
                                viewModel.toggle(todo.id)
                                items = viewModel.list()
                            },
                            onDelete = {
                                viewModel.remove(todo.id)
                                items = viewModel.list()
                            }
                        )
                        Divider()
                    }
                }
            }
        }
    }
}

@Composable
fun TodoRow(todo: Todo, onToggle: () -> Unit, onDelete: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.weight(1f)) {
            Checkbox(checked = todo.done, onCheckedChange = { onToggle() })
            Spacer(Modifier.width(8.dp))
            Text(text = if (todo.done) "✔ ${todo.text}" else todo.text)
        }
        TextButton(onClick = onDelete) { Text("Delete") }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        TodoScreen()
    }
}
