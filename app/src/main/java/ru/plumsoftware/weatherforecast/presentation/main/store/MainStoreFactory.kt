package ru.plumsoftware.weatherforecast.presentation.main.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import com.google.gson.Gson
import io.ktor.http.HttpStatusCode
import io.ktor.util.date.GMTDate
import kotlinx.coroutines.launch
import ru.plumsoftware.weatherforecast.data.remote.dto.owm.OwmResponse
import ru.plumsoftware.weatherforecast.data.utilities.logd
import ru.plumsoftware.weatherforecast.domain.remote.dto.either.OwmEither
import ru.plumsoftware.weatherforecast.domain.storage.HttpClientStorage

class MainStoreFactory(
    private val storeFactory: StoreFactory,
    private val city: String?,
    private val httpClientStorage: HttpClientStorage
) {
    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): MainStore =
        object : MainStore,
            Store<MainStore.Intent, MainStore.State, MainStore.Label> by storeFactory.create(
                name = "Main",
                initialState = MainStore.State(),
                bootstrapper = coroutineBootstrapper {
                    launch { dispatch(MainStoreFactory.Action.Init) }
                },
                reducer = MainStoreFactory.ReducerImpl,
                executorFactory = ::ExecutorImpl
            ) {
        }

    sealed interface Action {
        object Init : Action
    }

    sealed interface Msg {
        data class Theme(val isDarkTheme: Boolean) : Msg
    }

    private object ReducerImpl : Reducer<MainStore.State, MainStoreFactory.Msg> {

        override fun MainStore.State.reduce(msg: MainStoreFactory.Msg): MainStore.State =
            when (msg) {
                is Msg.Theme -> copy(
                    isDarkTheme = msg.isDarkTheme
                )
            }
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<MainStore.Intent, MainStoreFactory.Action, MainStore.State, MainStoreFactory.Msg, MainStore.Label>() {

        override fun executeIntent(
            intent: MainStore.Intent,
            getState: () -> MainStore.State
        ) =
            when (intent) {
                is MainStore.Intent.ChangeTheme -> {
                    dispatch(Msg.Theme(isDarkTheme = intent.isDarkTheme))
                    publish(MainStore.Label.ChangeTheme(isDarkTheme = intent.isDarkTheme))
                }
            }

        override fun executeAction(
            action: MainStoreFactory.Action,
            getState: () -> MainStore.State
        ) =
            when (action) {
                is Action.Init -> init()
            }

        private fun init() {
            scope.launch {
                if (city!!.isEmpty()) {
                    publish(MainStore.Label.OpenAuthorization)
                } else {
                    val owmEither: OwmEither<String, HttpStatusCode, GMTDate> =
                        httpClientStorage.get()
                    publish(
                        MainStore.Label.SkipAuthorization(
                            city = city,
                            owmResponse = convertStringToJson(owmEither.data)
                        )
                    )
                }
            }
        }

        private fun convertStringToJson(jsonString: String): OwmResponse =
            Gson().fromJson(jsonString, OwmResponse::class.java)

    }
}