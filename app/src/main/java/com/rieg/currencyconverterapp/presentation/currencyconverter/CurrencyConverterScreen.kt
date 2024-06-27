package com.rieg.currencyconverterapp.presentation.currencyconverter

import android.content.res.Configuration
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.SwapVert
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rieg.currencyconverterapp.ui.components.ErrorScreen
import com.rieg.currencyconverterapp.ui.components.LoadingScreen
import com.rieg.currencyconverterapp.ui.theme.CurrencyConverterAppTheme

@Composable
fun CurrencyConverterScreen(
    currencyConverterViewModel: CurrencyConverterViewModel
) {
    val currencyConverterUiState by currencyConverterViewModel.currencyConverterUiState.collectAsState()
    CurrencyConverterBody(
        currencyConverterUiState = currencyConverterUiState,
        onClickRepeat = currencyConverterViewModel::onRepeat,
        onClickUpdateValue = currencyConverterViewModel::onUpdateCurrencyValue,
        onSwapConverter = currencyConverterViewModel::onSwapConverter
    )
}

@Composable
fun CurrencyConverterBody(
    modifier: Modifier = Modifier,
    currencyConverterUiState: CurrencyConverterUiState,
    onClickRepeat: () -> Unit,
    onClickUpdateValue: (String) -> Unit,
    onSwapConverter: () -> Unit
){
    Column(modifier = modifier.fillMaxSize()) {
        when(currencyConverterUiState) {
            is CurrencyConverterUiState.Loading -> LoadingScreen()
            is CurrencyConverterUiState.Error -> ErrorScreen(onClickRepeat = onClickRepeat)
            is CurrencyConverterUiState.Success -> CurrencyConverter(
                inputCurrencyLabel = currencyConverterUiState.inputCurrencyLabel,
                inputCurrency = currencyConverterUiState.inputCurrency,
                inputCurrencyValue = currencyConverterUiState.inputValue,
                outputCurrencyLabel = currencyConverterUiState.outputCurrencyLabel,
                outputCurrency = currencyConverterUiState.outputCurrency,
                outputCurrencyValue = currencyConverterUiState.outputValue,
                onValueChange = onClickUpdateValue,
                onSwapConverter = onSwapConverter,
            )
        }
    }
}
@Composable
fun CurrencyConverter(
    modifier: Modifier = Modifier,
    inputCurrencyLabel: String,
    inputCurrency: String,
    outputCurrency: String,
    inputCurrencyValue: String,
    outputCurrencyLabel: String,
    outputCurrencyValue: String,
    onValueChange: (String) -> Unit,
    onSwapConverter: () -> Unit
) {
    Column(
        modifier = modifier.padding(8.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            CurrencyCard(
                modifier = Modifier.weight(1f),
                value = inputCurrency
            )
            Spacer(modifier = Modifier.width(20.dp))
            CurrencyField(
                modifier = Modifier.weight(2f),
                value = inputCurrencyValue,
                onValueChange = onValueChange,
                label = inputCurrencyLabel
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                modifier = Modifier.weight(1f),
                onClick = onSwapConverter
            ) {
                Icon(imageVector = Icons.Rounded.SwapVert, contentDescription = null)
            }
            Spacer(modifier = Modifier.width(20.dp))
            Spacer(modifier = Modifier.weight(2f))
        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            CurrencyCard(
                modifier = Modifier.weight(1f),
                value = outputCurrency
            )
            Spacer(modifier = Modifier.width(20.dp))
            CurrencyField(
                modifier = Modifier.weight(2f),
                value = outputCurrencyValue,
                label = outputCurrencyLabel,
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
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
            )
        }
    }
}
@Composable
fun CurrencyField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit = {},
    label: String,
    maxLength: Int = 14,
    enabled: Boolean = true,
) {
    val focusManager = LocalFocusManager.current
    TextField(
        modifier = modifier.height(60.dp),
        value = value,
        onValueChange = { inputValue ->
            if (inputValue.length != maxLength) {
                val filteredValue = inputValue.filter { char ->
                    char.isDigit() || char == '.'
                }
                val parts = filteredValue.split(".")
                if (parts.size <= 2 && (parts.size < 2 || parts[1].length <= 2)) {
                    onValueChange(filteredValue)
                }
            }
        },
        label = { Text(
            text = label,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        ) },
        placeholder = {
            Text(text = "0") },
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


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(showBackground = true)
@Composable
fun CurrencyConverterPreview() {
    CurrencyConverterAppTheme {
        CurrencyConverter(
            inputCurrencyLabel = "Доллар США",
            inputCurrency = "USD",
            outputCurrencyLabel = "Российский рубль",
            outputCurrency = "RUB",
            inputCurrencyValue = "1",
            outputCurrencyValue = "82.21",
            onValueChange = {},
            onSwapConverter = {}
        )
    }
}