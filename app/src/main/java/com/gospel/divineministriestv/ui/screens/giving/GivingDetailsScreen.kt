package com.gospel.divineministriestv.ui.screens.giving

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.gospel.divineministriestv.R
import com.gospel.divineministriestv.ui.theme.DivineMinistriesTVTheme
import com.gospel.divineministriestv.util.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GivingDetailsScreen(
    methodType: String,
    onBack: () -> Unit = {}
) {
    val context = LocalContext.current

    val detailsText = when (methodType) {
        "M-Pesa Tithing" -> Constants.MPESA_TITHING_NUMBER
        "M-Pesa Offering" -> Constants.MPESA_OFFERING_LIPA_NUMBER
        "CRDB Bank" -> Constants.CRDB_ACCOUNT_NUMBER
        "Zelle" -> Constants.ZELLE_NUMBER
        else -> Constants.WORLDREMIT_NUMBER
    }

    val accountName = "Bahati Mwakalinga"

    val logoRes = when (methodType) {
        "M-Pesa Tithing", "M-Pesa Offering" -> R.drawable.mpesa_logo
        "CRDB Bank" -> R.drawable.crdb_logo
        "Zelle" -> R.drawable.zelle_logo
        else -> R.drawable.worldremit_logo
    }

    val needsWhiteBackground = methodType == "Zelle" || methodType == "WorldRemit"

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = { Text("GIVING DETAILS", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold) },
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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            
            Surface(
                color = if (needsWhiteBackground) Color.White else Color.Transparent,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.size(100.dp)
            ) {
                AsyncImage(
                    model = logoRes,
                    contentDescription = methodType,
                    modifier = Modifier
                        .padding(if (needsWhiteBackground) 12.dp else 0.dp)
                        .fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = methodType,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold
            )
            
            Text(
                text = "Mobile Money",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Details Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    DetailRow(label = "Number", value = detailsText, context = context)
                    Spacer(modifier = Modifier.height(20.dp))
                    DetailRow(label = "Name", value = accountName, context = context)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { /* Open App Logic */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Open M-Pesa",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = { /* How to give logic */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                border = ButtonDefaults.outlinedButtonBorder(enabled = true).copy(width = 1.dp)
            ) {
                Text(
                    text = "How to Give",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun DetailRow(label: String, value: String, context: Context) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            TextButton(
                onClick = {
                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip = ClipData.newPlainText("Giving Detail", value)
                    clipboard.setPrimaryClip(clip)
                    Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show()
                }
            ) {
                Text("COPY", color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(name = "Dark Mode", showBackground = true)
@Composable
fun GivingDetailsScreenPreviewDark() {
    DivineMinistriesTVTheme(darkTheme = true) {
        GivingDetailsScreen(methodType = "M-Pesa Tithing")
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Composable
fun GivingDetailsScreenPreviewLight() {
    DivineMinistriesTVTheme(darkTheme = false) {
        GivingDetailsScreen(methodType = "M-Pesa Tithing")
    }
}
