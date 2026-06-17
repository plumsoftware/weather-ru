package ru.plumsoftware.weatherforecastru.presentation.widgetconfig.store

import android.content.Context
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.plumsoftware.weatherforecastru.data.models.widget.WidgetConfig
import ru.plumsoftware.weatherforecastru.data.storage.SharedPreferencesStorage
import ru.plumsoftware.weatherforecastru.widget.utilites.WidgetConfigUpdateHelper

class WidgetConfigStoreFactory(
    private val storeFactory: StoreFactory,
    private val sharedPreferencesStorage: SharedPreferencesStorage,
    private val appContext: Context,
) {

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): WidgetConfigStore =
        object : WidgetConfigStore,
            Store<WidgetConfigStore.Intent, WidgetConfigStore.State, WidgetConfigStore.Label> by storeFactory.create(
                name = "WidgetConfig",
                initialState = WidgetConfigStore.State(),
                bootstrapper = coroutineBootstrapper {
                    launch {
                        dispatch(Action.InitWidgetConfig)
                    }
                },
                reducer = ReducerImpl,
                executorFactory = ::ExecutorImpl
            ) {
        }

    sealed interface Action {
        object InitWidgetConfig : Action
    }

    sealed interface Msg {
        data class WidgetConfigMsg(val value: WidgetConfig) : Msg

        data class RadiusMsg(val value: Int) : Msg
        data class RedMsg(val value: Int) : Msg
        data class GreenMsg(val value: Int) : Msg
        data class BlueMsg(val value: Int) : Msg
        data class OpacityMsg(val value: Float) : Msg
    }

    private object ReducerImpl : Reducer<WidgetConfigStore.State, Msg> {
        override fun WidgetConfigStore.State.reduce(msg: Msg): WidgetConfigStore.State =
            when (msg) {
                is Msg.WidgetConfigMsg -> copy(widgetConfig = msg.value)
                is Msg.BlueMsg -> copy(blue = msg.value)
                is Msg.GreenMsg -> copy(green = msg.value)
                is Msg.RadiusMsg -> copy(radius = msg.value)
                is Msg.RedMsg -> copy(red = msg.value)
                is Msg.OpacityMsg -> copy(opacity = msg.value)
            }
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<WidgetConfigStore.Intent, Action, WidgetConfigStore.State, Msg, WidgetConfigStore.Label>() {

        private var persistJob: Job? = null

        override fun executeIntent(
            intent: WidgetConfigStore.Intent,
            getState: () -> WidgetConfigStore.State
        ) = when (intent) {
            WidgetConfigStore.Intent.BackButtonClicked -> publish(WidgetConfigStore.Label.BackButtonClicked)
            is WidgetConfigStore.Intent.BlueChanged -> {
                dispatch(Msg.BlueMsg(value = intent.value))
                schedulePersist(getState)
            }

            is WidgetConfigStore.Intent.GreenChanged -> {
                dispatch(Msg.GreenMsg(value = intent.value))
                schedulePersist(getState)
            }

            is WidgetConfigStore.Intent.RadiusChanged -> {
                dispatch(Msg.RadiusMsg(value = intent.value))
                schedulePersist(getState)
            }

            is WidgetConfigStore.Intent.RedChanged -> {
                dispatch(Msg.RedMsg(value = intent.value))
                schedulePersist(getState)
            }

            is WidgetConfigStore.Intent.OpacityChanged -> {
                dispatch(Msg.OpacityMsg(value = intent.value))
                schedulePersist(getState)
            }

            is WidgetConfigStore.Intent.ColorPresetSelected -> {
                dispatch(Msg.RedMsg(value = intent.red))
                dispatch(Msg.GreenMsg(value = intent.green))
                dispatch(Msg.BlueMsg(value = intent.blue))
                schedulePersist(getState)
            }
        }

        override fun executeAction(action: Action, getState: () -> WidgetConfigStore.State) =
            when (action) {
                Action.InitWidgetConfig -> initWidgetConfig(widgetConfig = sharedPreferencesStorage.getWidget())
            }

        private fun initWidgetConfig(widgetConfig: WidgetConfig) {
            scope.launch {
                dispatch(Msg.WidgetConfigMsg(value = widgetConfig))
                dispatch(Msg.RadiusMsg(value = widgetConfig.radius))
                dispatch(Msg.RedMsg(value = widgetConfig.red))
                dispatch(Msg.GreenMsg(value = widgetConfig.green))
                dispatch(Msg.BlueMsg(value = widgetConfig.blue))
                dispatch(Msg.OpacityMsg(value = widgetConfig.opacity))
            }
        }

        private fun schedulePersist(getState: () -> WidgetConfigStore.State) {
            persistJob?.cancel()
            persistJob = scope.launch {
                delay(150)
                val state = getState()
                sharedPreferencesStorage.saveWidget(
                    widgetConfig = WidgetConfig(
                        radius = state.radius,
                        red = state.red,
                        green = state.green,
                        blue = state.blue,
                        opacity = state.opacity,
                    )
                )
                WidgetConfigUpdateHelper.requestWidgetUpdate(appContext)
            }
        }
    }
}
