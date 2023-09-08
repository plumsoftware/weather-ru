package ru.plumsoftware.weatherforecastru.presentation.airquality.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import kotlinx.coroutines.launch
import ru.plumsoftware.weatherforecastru.data.remote.dto.weatherapi.AirQuality

class AirQualityStoreFactory(
    private val storeFactory: StoreFactory,
    private val airQuality: AirQuality
) {

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): AirQualityStore =
        object : AirQualityStore,
            Store<AirQualityStore.Intent, AirQualityStore.State, AirQualityStore.Label> by storeFactory.create(
                name = "Authorization",
                initialState = AirQualityStore.State(),
                bootstrapper = coroutineBootstrapper {
                    launch { dispatch(AirQualityStoreFactory.Action.AirQualityAction(value = airQuality)) }
                },
                reducer = AirQualityStoreFactory.ReducerImpl,
                executorFactory = ::ExecutorImpl
            ) {
        }

    sealed interface Action {
        data class AirQualityAction(val value: AirQuality) : Action
    }

    sealed interface Msg {
        data class AirQualityMsg(
            val value: AirQuality
        ) : Msg
    }

    private object ReducerImpl : Reducer<AirQualityStore.State, AirQualityStoreFactory.Msg> {

        override fun AirQualityStore.State.reduce(msg: AirQualityStoreFactory.Msg): AirQualityStore.State =
            when (msg) {
                is Msg.AirQualityMsg -> copy(airQuality = msg.value)
            }
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<AirQualityStore.Intent, AirQualityStoreFactory.Action, AirQualityStore.State, AirQualityStoreFactory.Msg, AirQualityStore.Label>() {

        override fun executeIntent(
            intent: AirQualityStore.Intent,
            getState: () -> AirQualityStore.State
        ) =
            when (intent) {
                AirQualityStore.Intent.BackButtonClicked -> {
                    publish(AirQualityStore.Label.BackButtonClicked)
                }
            }

        override fun executeAction(
            action: AirQualityStoreFactory.Action,
            getState: () -> AirQualityStore.State
        ) =
            when (action) {
                is Action.AirQualityAction -> initAirQuality(value = airQuality)
            }

        private fun initAirQuality(value: AirQuality) {
            scope.launch {
                dispatch(AirQualityStoreFactory.Msg.AirQualityMsg(value = value))
            }
        }
    }
}