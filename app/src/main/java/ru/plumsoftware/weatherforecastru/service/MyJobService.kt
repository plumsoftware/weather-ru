package ru.plumsoftware.weatherforecastru.service

import android.app.job.JobParameters
import android.app.job.JobService
import android.os.Handler
import android.os.Looper
import android.widget.Toast

const val JOB_ID = 123

class MyJobService : JobService() {

    private val uiHandler = Handler(Looper.getMainLooper())

    override fun onStartJob(params: JobParameters?): Boolean {
        // Выполнение фоновых операций здесь

        // Обновление интерфейса пользователя в главном потоке
        uiHandler.post {
            Toast.makeText(applicationContext, "Сервис обновляется", Toast.LENGTH_SHORT).show()
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