package com.example.currencyconverterapp.ui.screens.currencyList

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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.data.model.Currency
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyListScreen(currencyListViewModel: CurrencyListViewModel = viewModel()) {
    val currencyListUiState by currencyListViewModel.currencyListUiState.collectAsState()
    val refreshState = rememberPullToRefreshState()
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        CurrencyListBody(
            currencyListUiState = currencyListUiState,
            currencyListViewModel = currencyListViewModel,
            refreshState = refreshState
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CurrencyListBody(
    currencyListUiState: CurrencyListUiState,
    currencyListViewModel: CurrencyListViewModel,
    refreshState: PullToRefreshState,
) {
    when(currencyListUiState) {
        is CurrencyListUiState.Success -> {
            CurrencyList(
                timestamp = currencyListUiState.timestamp,
                listOfCurrency = currencyListUiState.listOfCurrency,
                refreshState = refreshState,
                refresh = currencyListViewModel::refresh
            )
        }
        is CurrencyListUiState.Error -> ErrorScreen(currencyListViewModel::repeat)
        is CurrencyListUiState.Loading -> LoadingScreen()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyList(
    timestamp: String,
    listOfCurrency: List<Currency>,
    refreshState: PullToRefreshState,
    refresh: () -> Unit
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
                    CurrencyCard(it)
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
fun LoadingScreen(modifier: Modifier = Modifier){
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(
    onRepeat: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = stringResource(id = R.string.error))
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onRepeat) {
            Text(text = stringResource(id = R.string.repeat))
        }
    }
}

@Composable
fun CurrencyCard(currency: Currency){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
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
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    text = "%.2f".format(currency.value)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Normal,
                    text = "(%.2f)".format(currency.value - currency.previous)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CurrencyCardPreview(){
    CurrencyCard(
        Currency(
            id = "2",
            numCode = "011",
            charCode = "USD",
            nominal = 1,
            name = "Американский доллар",
            value = 90.11,
            previous = 92.01
        )
    )
}