package com.gospel.divineministriestv.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast

fun Context.openDialer(phoneNumber: String) {
    try {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
        startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(this, "Unable to open dialer", Toast.LENGTH_SHORT).show()
    }
}

fun Context.openEmail(email: String, subject: String = "") {
    try {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            putExtra(Intent.EXTRA_SUBJECT, subject)
        }
        startActivity(Intent.createChooser(intent, "Send Email"))
    } catch (e: Exception) {
        Toast.makeText(this, "Unable to open email client", Toast.LENGTH_SHORT).show()
    }
}

fun Context.openBrowser(url: String) {
    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(this, "Unable to open browser", Toast.LENGTH_SHORT).show()
    }
}

fun Context.openWhatsApp(phoneNumber: String) {
    try {
        val url = "https://api.whatsapp.com/send?phone=$phoneNumber"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(this, "WhatsApp not installed", Toast.LENGTH_SHORT).show()
    }
}
