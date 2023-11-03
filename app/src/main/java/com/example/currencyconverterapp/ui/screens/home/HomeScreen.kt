package com.example.currencyconverterapp.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.currencyconverterapp.data.remote.model.Valute
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(homeViewModel: HomeViewModel = viewModel()){
    val homeUiState by homeViewModel.homeUiState.collectAsState()
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            CurrencySelection(
                modifier = Modifier.weight(1f),
                currencyCode = homeUiState.inputCurrency,
                enabled = true,
                selected = openBottomSheet,
                onClick = { openBottomSheet = true }
            )
            Spacer(modifier = Modifier.width(20.dp))
            CurrencyField(modifier = Modifier.weight(2f), value = homeUiState.amount, onValueChange = homeViewModel::updateAmount)
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(modifier = Modifier
            .fillMaxWidth()
            , horizontalArrangement = Arrangement.Center) {
            CurrencySelection(
                modifier = Modifier.weight(1f),
                currencyCode = homeUiState.outputCurrency,
                selected = false,
            )
            Spacer(modifier = Modifier.width(20.dp))
            CurrencyField(modifier = Modifier.weight(2f), value = homeUiState.convertedAmount, enabled = false)
        }
        Text(text = "Текущий курс 1 ${homeUiState.inputCurrency} - ${homeUiState.currentRate} RUB")
        if (openBottomSheet) {
            CurrencyModalBottomSheet(
                onDismissRequest = {openBottomSheet = false},
                sheetState = sheetState,
                items = homeUiState.listOfCurrency,
                onClick = {
                    homeViewModel.updateInputCurrency(it.charCode)
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            openBottomSheet = false
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun CurrencyField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit = {},
    enabled: Boolean = true
    ) {
    val focusManager = LocalFocusManager.current
    TextField(
        modifier = modifier.height(60.dp),
        value = value,
        onValueChange = onValueChange,
        shape = RoundedCornerShape(15.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
            }
        ),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        enabled = enabled
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyModalBottomSheet(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    onClick: (Valute) -> Unit,
    sheetState: SheetState,
    items: List<Valute>
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        modifier = modifier
    ) {
        LazyColumn {
            items(items) {
                CurrencyItem(
                    valute = it,
                    onClick = {onClick(it)}
                )
            }
        }
    }


}

@Composable
fun CurrencyItem(
    modifier: Modifier = Modifier,
    valute: Valute,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(8.dp)
    ) {
        Text(text = valute.name, fontSize = 16.sp)
        Text(text = valute.charCode, fontSize = 16.sp, color = Color.DarkGray)
    }
}
@Composable
fun CurrencySelection(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center,
    enabled: Boolean = false,
    selected: Boolean,
    onClick: () -> Unit = {},
    currencyCode: String
) {
    Box(modifier = modifier
        .height(60.dp)
        .clip(RoundedCornerShape(15.dp))
        .selectable(enabled = enabled, selected = selected, onClick = onClick)
        .background(Color.DarkGray)
        .padding(8.dp),
        contentAlignment = contentAlignment
    ) {
        Row {
            Text(text = currencyCode)
            Icon(imageVector = Icons.Rounded.KeyboardArrowDown, contentDescription = null)
        }
    }
}
