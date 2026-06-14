package ru.plumsoftware.weatherforecastru.presentation.airquality.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import kotlinx.coroutines.launch
import ru.plumsoftware.weatherforecastru.data.models.airquality.AirQualityData

class AirQualityStoreFactory(
    private val storeFactory: StoreFactory,
    private val airQualityData: AirQualityData,
) {

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): AirQualityStore =
        object : AirQualityStore,
            Store<AirQualityStore.Intent, AirQualityStore.State, AirQualityStore.Label> by storeFactory.create(
                name = "AirQuality",
                initialState = AirQualityStore.State(),
                bootstrapper = coroutineBootstrapper {
                    launch {
                        dispatch(
                            AirQualityStoreFactory.Action.AirQualityAction(value = airQualityData)
                        )
                    }
                },
                reducer = AirQualityStoreFactory.ReducerImpl,
                executorFactory = ::ExecutorImpl,
            ) {}

    sealed interface Action {
        data class AirQualityAction(val value: AirQualityData) : Action
    }

    sealed interface Msg {
        data class AirQualityMsg(val value: AirQualityData) : Msg
    }

    private object ReducerImpl : Reducer<AirQualityStore.State, Msg> {
        override fun AirQualityStore.State.reduce(msg: Msg): AirQualityStore.State = when (msg) {
            is Msg.AirQualityMsg -> copy(isLoading = false, airQualityData = msg.value)
        }
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<AirQualityStore.Intent, Action, AirQualityStore.State, Msg, AirQualityStore.Label>() {

        override fun executeIntent(intent: AirQualityStore.Intent, getState: () -> AirQualityStore.State) =
            when (intent) {
                AirQualityStore.Intent.BackButtonClicked -> publish(AirQualityStore.Label.BackButtonClicked)
            }

        override fun executeAction(action: Action, getState: () -> AirQualityStore.State) = when (action) {
            is Action.AirQualityAction -> initAirQuality(value = action.value)
        }

        private fun initAirQuality(value: AirQualityData) {
            scope.launch {
                dispatch(Msg.AirQualityMsg(value = value))
            }
        }
    }
}
