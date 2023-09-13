package ru.plumsoftware.weatherforecastru.service

import android.app.job.JobParameters
import android.app.job.JobService
import android.os.Handler
import android.os.Looper
import ru.plumsoftware.weatherforecastru.application.App
import ru.plumsoftware.weatherforecastru.messanging.local.SimpleNotificationService

const val JOB_ID = 123

class MyJobService : JobService() {

    private val uiHandler = Handler(Looper.getMainLooper())
    private val context = App.INSTANCE.applicationContext

    override fun onStartJob(params: JobParameters?): Boolean {
        // Выполнение фоновых операций здесь

        // Обновление интерфейса пользователя в главном потоке
        uiHandler.post {
            val service = SimpleNotificationService(context = context)
            service.showNotification()
        }

        // Переходите на false, если выполнение задачи закончено
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        // Вернуть true, чтобы выполнить еще одну попытку выполнения задачи,
        // если она должна быть выполнена снова
        return true
    }
}