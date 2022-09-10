package com.company.verbzz_app.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.company.verbzz_app.ui.theme.WindowMeasurement

@Composable
fun DropdownMenuComponent(
    measurement: WindowMeasurement,
    list: List<String>,
    label: String,
    selectedItem: MutableState<String>,
    description: String
) {
    var expanded by remember {mutableStateOf(false)}
    val icon = if(expanded) { Icons.Filled.KeyboardArrowUp }
    else { Icons.Filled.KeyboardArrowDown }
    val size = ((measurement.smallest/4)*3).dp

    Column(modifier = Modifier.padding(end = 10.dp, start = 10.dp)) {
        OutlinedTextField(
            value = selectedItem.value,
            onValueChange = { selectedItem.value = it },
            modifier = Modifier.width(size),
            label = { Text(text = label) },
            readOnly = true,
            trailingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = description,
                    modifier = Modifier.clickable { expanded = !expanded })
            }
            )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(size).padding(vertical = 10.dp)
        ) {
            list.forEach{item ->
                DropdownMenuItem(onClick = {
                    selectedItem.value = item
                    expanded = false
                }) {
                    Text(text = item)
                }
        }
        }
    }


}