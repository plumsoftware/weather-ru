package ru.plumsoftware.weatherforecastru.messanging.rustore

import android.util.Log
import ru.plumsoftware.weatherforecast.R
import ru.plumsoftware.weatherforecastru.messanging.rustore.wrapper.RuStorePushNotificationManagerWrapper
import ru.plumsoftware.weatherforecastru.messanging.rustore.wrapper.model.AppNotification
import ru.rustore.sdk.pushclient.messaging.exception.RuStorePushClientException
import ru.rustore.sdk.pushclient.messaging.model.RemoteMessage
import ru.rustore.sdk.pushclient.messaging.service.RuStoreMessagingService

class RuStorePushListenerService : RuStoreMessagingService() {
    private val notificationManagerWrapper =
        RuStorePushNotificationManagerWrapper.getInstance(this)
    override fun onNewToken(token: String) {
        Log.d(LOG_TAG, "onNewToken token = $token")
        /*
         Вам необходимо отправить полученный пуш-токен на свой сервер,
         с которого будет осуществляться рассылка пуш-уведомлений.
         */
    }
    /*
        Получаем пуш-уведомления и пробуем отобразить контент, полученный в поле data.
     */
    override fun onMessageReceived(message: RemoteMessage) {
        val (channelId, channelName) = getChannelInfo()
        notificationManagerWrapper.showNotification(
            context = this,
            data = AppNotification(
                id = message.hashCode(),
                title = message.notification?.title,
                message = message.notification?.body,
                channelId = channelId,
                channelName = channelName
            )
        )
    }
    override fun onError(errors: List<RuStorePushClientException>) {
        errors.forEach { error -> error.printStackTrace() }
    }
    private fun getChannelInfo(): Pair<String, String> {
        val channelId = getString(R.string.rustore_push_notification_channel_id)
        val channelName = getString(R.string.rustore_push_notification_channel_name)
        return channelId to channelName
    }
    companion object{
        private const val LOG_TAG = "PushListenerService"
    }
}