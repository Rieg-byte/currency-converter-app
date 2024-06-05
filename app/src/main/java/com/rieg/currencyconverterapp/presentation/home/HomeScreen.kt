package com.rieg.currencyconverterapp.presentation.home

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rieg.currencyconverterapp.R
import com.rieg.currencyconverterapp.domain.models.Currency
import com.rieg.currencyconverterapp.ui.components.ErrorScreen
import com.rieg.currencyconverterapp.ui.components.LoadingScreen
import com.rieg.currencyconverterapp.ui.theme.CurrencyConverterAppTheme
import com.rieg.currencyconverterapp.ui.theme.down
import com.rieg.currencyconverterapp.ui.theme.rise
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    currencyListViewModel: CurrencyListViewModel,
    onNavigateToConverter: (String) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val currencyListUiState by currencyListViewModel.currencyListUiState.collectAsState()
    val refreshState = rememberPullToRefreshState()
    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            HomeTopBar(
                titleRes = R.string.currency_list,
                onActionClick = { /*TODO*/ },
                actionIcon = Icons.Filled.Settings,
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->
        HomeBody(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            homeUiState = currencyListUiState,
            currencyListViewModel = currencyListViewModel,
            refreshState = refreshState,
            onNavigateToConverter = onNavigateToConverter
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    modifier: Modifier = Modifier,
    @StringRes titleRes: Int,
    onActionClick: () -> Unit,
    actionIcon: ImageVector,
    actionContentDescription: String? = null,
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    CenterAlignedTopAppBar(
        title = { Text(text = stringResource(id = titleRes)) },
        actions = {
            IconButton(onClick = onActionClick) {
                Icon(imageVector = actionIcon, contentDescription = actionContentDescription)
            }
        },
        scrollBehavior = scrollBehavior,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeBody(
    modifier: Modifier = Modifier,
    homeUiState: HomeUiState,
    currencyListViewModel: CurrencyListViewModel,
    refreshState: PullToRefreshState,
    onNavigateToConverter: (String) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        when (homeUiState) {
            is HomeUiState.Success -> {
                CurrencyList(
                    timestamp = homeUiState.timestamp,
                    listOfCurrency = homeUiState.listOfCurrency,
                    refreshState = refreshState,
                    refresh = currencyListViewModel::updateCurrencyList,
                    onNavigateToConverter = onNavigateToConverter
                )
            }

            is HomeUiState.Error -> ErrorScreen(currencyListViewModel::repeat)
            is HomeUiState.Loading -> LoadingScreen()
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
            LazyColumn() {
                item {
                    Timestamp(value = timestamp)
                }
                items(listOfCurrency) { currency ->
                    CurrencyCard(
                        currency = currency,
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
        Text(
            text = stringResource(id = R.string.timestamp),
            style = MaterialTheme.typography.titleSmall
        )
        Text(text = value)
    }
}

@Composable
fun CurrencyCard(
    currency: Currency,
    onNavigateToConverter: (String) -> Unit = {}
){
    val diff = currency.value - currency.previous
    val color = if (diff > 0) MaterialTheme.colorScheme.rise else MaterialTheme.colorScheme.down
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onNavigateToConverter(currency.charCode) }
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
                    style = MaterialTheme.typography.titleLarge,
                    text = currency.charCode
                )
                Text(
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    text = "${currency.nominal} ${currency.name}"
                )
            }
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    text = "%.2f".format(currency.value)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    textAlign = TextAlign.Center,
                    color = color,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    text = "%.2f".format(diff)
                )
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(showBackground = true)
@Composable
fun PreviewCurrencyCard(){
    CurrencyConverterAppTheme {
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
}
