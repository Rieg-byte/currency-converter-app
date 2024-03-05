package com.example.currencyconverterapp.ui.screens.currencyList

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.data.model.Currency
import com.example.currencyconverterapp.ui.components.ErrorScreen
import com.example.currencyconverterapp.ui.components.LoadingScreen
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyListScreen(
    currencyListViewModel: CurrencyListViewModel,
    onNavigateToConverter: (String) -> Unit
) {
    val currencyListUiState by currencyListViewModel.currencyListUiState.collectAsState()
    val refreshState = rememberPullToRefreshState()
    CurrencyListBody(
        currencyListUiState = currencyListUiState,
        currencyListViewModel = currencyListViewModel,
        refreshState = refreshState,
        onNavigateToConverter = onNavigateToConverter
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CurrencyListBody(
    modifier: Modifier = Modifier,
    currencyListUiState: CurrencyListUiState,
    currencyListViewModel: CurrencyListViewModel,
    refreshState: PullToRefreshState,
    onNavigateToConverter: (String) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        when (currencyListUiState) {
            is CurrencyListUiState.Success -> {
                CurrencyList(
                    timestamp = currencyListUiState.timestamp,
                    listOfCurrency = currencyListUiState.listOfCurrency,
                    refreshState = refreshState,
                    refresh = currencyListViewModel::updateCurrencyList,
                    onNavigateToConverter = onNavigateToConverter
                )
            }

            is CurrencyListUiState.Error -> ErrorScreen(currencyListViewModel::repeat)
            is CurrencyListUiState.Loading -> LoadingScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyList(
    timestamp: String,
    listOfCurrency: List<Currency>,
    refreshState: PullToRefreshState,
    refresh: () -> Unit,
    onNavigateToConverter: (String) -> Unit
) {
    if (refreshState.isRefreshing) {
        LaunchedEffect(true) {
            delay(1500)
            refresh()
            refreshState.endRefresh()
        }
    }
    Box(modifier = Modifier.nestedScroll(refreshState.nestedScrollConnection)) {
        Column {
            Timestamp(value = timestamp)
            LazyColumn() {
                items(listOfCurrency) {
                    CurrencyCard(
                        currency = it,
                        onNavigateToConverter = onNavigateToConverter
                    )
                }
            }
        }
        PullToRefreshContainer(
            modifier = Modifier.align(Alignment.TopCenter),
            state = refreshState
        )
    }
}

@Composable
fun Timestamp(value: String) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
    ) {
        Text(text = stringResource(id = R.string.timestamp))
        Text(text = value)
    }
}

@Composable
fun CurrencyCard(
    currency: Currency,
    onNavigateToConverter: (String) -> Unit = {}
){
    val diff = currency.value - currency.previous
    val color = when {
        diff > 0 -> Color(0xFF87E455)
        diff < 0 -> Color(0xFFF44336)
        else -> Color(0xFFD5D5D5)
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onNavigateToConverter(currency.charCode) }
            .height(60.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    text = "${currency.nominal} ${currency.charCode}"
                )
                Text(
                    fontSize = 14.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    text = currency.name
                )
            }
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.End,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    text = "%.2f".format(currency.value)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(
                        containerColor = color
                    )
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Normal,
                            text = "%.2f".format(diff)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CurrencyCardPreview1(){
    CurrencyCard(
        currency = Currency(
            id = "2",
            numCode = "011",
            charCode = "USD",
            nominal = 1,
            name = "Американский доллар",
            value = 90.11,
            previous = 90.10
        )
    )
}

@Preview(showBackground = true)
@Composable
fun CurrencyCardPreview2(){
    CurrencyCard(
        currency = Currency(
            id = "2",
            numCode = "011",
            charCode = "USD",
            nominal = 1,
            name = "Американский доллар",
            value = 90.11,
            previous = 90.11
        )
    )
}

@Preview(showBackground = true)
@Composable
fun CurrencyCardPreview3(){
    CurrencyCard(
        currency = Currency(
            id = "2",
            numCode = "011",
            charCode = "USD",
            nominal = 1,
            name = "Американский доллар",
            value = 90.11,
            previous = 92.3
        )
    )
}