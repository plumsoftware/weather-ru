package ru.plumsoftware.weatherforecastru.presentation.aboutapp.presentation

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat
import ru.plumsoftware.weatherforecast.BuildConfig
import ru.plumsoftware.weatherforecastru.data.constants.Constants

internal object AboutAppActions {

    fun openDeveloperEmail(context: Context) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = buildMailtoUri(email = Constants.Links.developerEmail)
        }
        launchEmailChooser(context, intent)
    }

    fun openBugReport(context: Context, subject: String, version: String) {
        val body = "Версия приложения: $version\n\n"
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = buildMailtoUri(
                email = Constants.Links.developerEmail,
                subject = subject,
                body = body,
            )
        }
        launchEmailChooser(context, intent)
    }

    fun openRateApp(context: Context, applicationId: String) {
        val url = if (BuildConfig.platform == "RuStore") {
            Constants.Links.leaveFeedback
        } else {
            Constants.Links.playStoreMarketUri(applicationId)
        }
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        try {
            context.startActivity(intent)
        } catch (_: ActivityNotFoundException) {
            if (BuildConfig.platform != "RuStore") {
                val webIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(Constants.Links.playStoreWebUri(applicationId)),
                ).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                launch(context, webIntent)
            }
        }
    }

    private fun buildMailtoUri(
        email: String,
        subject: String? = null,
        body: String? = null,
    ): Uri {
        val query = buildList {
            if (!subject.isNullOrBlank()) add("subject=${Uri.encode(subject)}")
            if (!body.isNullOrBlank()) add("body=${Uri.encode(body)}")
        }.joinToString("&")
        return if (query.isEmpty()) {
            Uri.parse("mailto:$email")
        } else {
            Uri.parse("mailto:$email?$query")
        }
    }

    private fun launchEmailChooser(context: Context, intent: Intent) {
        try {
            ContextCompat.startActivity(
                context,
                Intent.createChooser(intent, null),
                null,
            )
        } catch (_: ActivityNotFoundException) {
            // No email client available on this device.
        }
    }

    private fun launch(context: Context, intent: Intent) {
        try {
            ContextCompat.startActivity(context, intent, null)
        } catch (_: ActivityNotFoundException) {
            // No handler available on this device.
        }
    }
}
