package ru.plumsoftware.weatherforecastru.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


class ShortcutReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // Обработка событий, связанных с ярлыками
        if (intent.action == Intent.ACTION_CREATE_SHORTCUT) {
            // Ярлык был создан
        }
        //        else if (intent.action == Intent.SHORT) {
//            // Ярлык был закреплен
//        } else if (intent.action == Intent.SHORTCUT) {
//            // Ярлык был удален
//        }
    }
}