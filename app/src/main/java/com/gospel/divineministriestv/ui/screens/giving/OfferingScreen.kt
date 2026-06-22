package com.gospel.divineministriestv.ui.screens.giving

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gospel.divineministriestv.R
import com.gospel.divineministriestv.ui.theme.DivineMinistriesTVTheme
import com.gospel.divineministriestv.util.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OfferingScreen(
    onMethodClick: (String) -> Unit = {},
    onBack: () -> Unit = {}
) {
    val scrollState = rememberScrollState()

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = { Text("GIVING", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    navigationIconContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .padding(16.dp),
        ) {
            Text(
                text = "Give an Offering",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Support the International Prayer Gathering",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(32.dp))

            // M-Pesa Tithing
            PaymentMethodRowItem(
                title = "M-Pesa Tithing",
                details = Constants.MPESA_TITHING_NUMBER,
                logoRes = R.drawable.mpesa_logo,
                onClick = { onMethodClick("M-Pesa Tithing") }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // M-Pesa Offering
            PaymentMethodRowItem(
                title = "M-Pesa Offering",
                details = "Lipa Number: ${Constants.MPESA_OFFERING_LIPA_NUMBER}",
                logoRes = R.drawable.mpesa_logo,
                onClick = { onMethodClick("M-Pesa Offering") }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // CRDB Bank
            PaymentMethodRowItem(
                title = "CRDB Bank",
                details = Constants.CRDB_ACCOUNT_NUMBER,
                logoRes = R.drawable.crdb_logo,
                onClick = { onMethodClick("CRDB Bank") }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Zelle
            PaymentMethodRowItem(
                title = "Zelle",
                details = Constants.ZELLE_NUMBER,
                logoRes = R.drawable.zelle_logo,
                logoBackground = Color.White,
                onClick = { onMethodClick("Zelle") }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // WorldRemit
            PaymentMethodRowItem(
                title = "WorldRemit",
                details = Constants.WORLDREMIT_NUMBER,
                logoRes = R.drawable.worldremit_logo,
                logoBackground = Color.White,
                onClick = { onMethodClick("WorldRemit") }
            )
            
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun PaymentMethodRowItem(
    title: String,
    details: String,
    logoRes: Int,
    logoBackground: Color = Color.Transparent,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                color = logoBackground,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.size(40.dp)
            ) {
                AsyncImage(
                    model = logoRes,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(if (logoBackground != Color.Transparent) 4.dp else 0.dp)
                        .fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = details,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Go to Details",
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
            )
        }
    }
}

@Preview(name = "Dark Mode", showBackground = true)
@Composable
fun OfferingScreenPreviewDark() {
    DivineMinistriesTVTheme(darkTheme = true) {
        OfferingScreen()
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Composable
fun OfferingScreenPreviewLight() {
    DivineMinistriesTVTheme(darkTheme = false) {
        OfferingScreen()
    }
}
