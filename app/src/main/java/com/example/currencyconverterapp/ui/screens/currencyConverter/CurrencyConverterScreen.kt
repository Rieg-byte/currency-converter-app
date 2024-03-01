package com.example.currencyconverterapp.ui.screens.currencyConverter

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.ui.components.ErrorScreen
import com.example.currencyconverterapp.ui.components.LoadingScreen

@Composable
fun CurrencyConverterScreen(
    currencyConverterViewModel: CurrencyConverterViewModel
) {
    val currencyConverterUiState by currencyConverterViewModel.currencyConverterUiState.collectAsState()
    CurrencyConverterBody(
        currencyConverterViewModel = currencyConverterViewModel,
        currencyConverterUiState = currencyConverterUiState
    )
}

@Composable
fun CurrencyConverterBody(
    modifier: Modifier = Modifier,
    currencyConverterViewModel: CurrencyConverterViewModel,
    currencyConverterUiState: CurrencyConverterUiState
){
    Column(modifier = modifier.fillMaxSize()) {
        when(currencyConverterUiState) {
            is CurrencyConverterUiState.Loading -> LoadingScreen()
            is CurrencyConverterUiState.Error -> ErrorScreen(onRepeat = currencyConverterViewModel::repeat)
            is CurrencyConverterUiState.Success -> CurrencyConverter(
                fromCurrency = currencyConverterUiState.fromCurrency,
                fromValue = currencyConverterUiState.fromValue,
                toCurrency = currencyConverterUiState.toCurrency,
                toValue = currencyConverterUiState.toValue,
                onValueChange = currencyConverterViewModel::updateValue
            )
        }
    }
}
@Composable
fun CurrencyConverter(
    modifier: Modifier = Modifier,
    fromCurrency: String,
    fromValue: String,
    toCurrency: String,
    toValue: String,
    onValueChange: (String) -> Unit
) {
    Column(
        modifier = modifier.padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            CurrencyCard(
                modifier = Modifier.weight(1f),
                value = fromCurrency
            )
            Spacer(modifier = Modifier.width(20.dp))
            CurrencyField(
                modifier = Modifier.weight(2f),
                value = fromValue,
                onValueChange = onValueChange
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            CurrencyCard(
                modifier = Modifier.weight(1f),
                value = toCurrency
            )
            Spacer(modifier = Modifier.width(20.dp))
            CurrencyField(
                modifier = Modifier.weight(2f),
                value = toValue,
                enabled = false
            )
        }
    }
}

@Composable
fun CurrencyCard(
    modifier: Modifier = Modifier,
    value: String
) {
    Card(
        modifier = modifier
            .height(60.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = value)
        }
    }
}
@Composable
fun CurrencyField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit = {},
    enabled: Boolean = true,
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