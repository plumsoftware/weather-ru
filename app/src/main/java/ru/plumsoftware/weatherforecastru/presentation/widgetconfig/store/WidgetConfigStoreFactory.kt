package ru.plumsoftware.weatherforecastru.presentation.widgetconfig.store

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import kotlinx.coroutines.launch
import ru.plumsoftware.weatherforecastru.domain.models.widget.WidgetConfig
import ru.plumsoftware.weatherforecastru.domain.storage.SharedPreferencesStorage

class WidgetConfigStoreFactory(
    private val storeFactory: StoreFactory,
    private val sharedPreferencesStorage: SharedPreferencesStorage
) {

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): WidgetConfigStore =
        object : WidgetConfigStore,
            Store<WidgetConfigStore.Intent, WidgetConfigStore.State, WidgetConfigStore.Label> by storeFactory.create(
                name = "Location",
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
    }

    private object ReducerImpl : Reducer<WidgetConfigStore.State, Msg> {
        override fun WidgetConfigStore.State.reduce(msg: Msg): WidgetConfigStore.State =
            when (msg) {
                is Msg.WidgetConfigMsg -> copy(widgetConfig = msg.value)
                is Msg.BlueMsg -> copy(blue = msg.value)
                is Msg.GreenMsg -> copy(green = msg.value)
                is Msg.RadiusMsg -> copy(radius = msg.value)
                is Msg.RedMsg -> copy(red = msg.value)
            }
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<WidgetConfigStore.Intent, Action, WidgetConfigStore.State, Msg, WidgetConfigStore.Label>() {
        override fun executeIntent(
            intent: WidgetConfigStore.Intent,
            getState: () -> WidgetConfigStore.State
        ) = when (intent) {
            WidgetConfigStore.Intent.BackButtonClicked -> publish(WidgetConfigStore.Label.BackButtonClicked)
            is WidgetConfigStore.Intent.BlueChanged -> {
                dispatch(Msg.BlueMsg(value = intent.value))
            }

            is WidgetConfigStore.Intent.GreenChanged -> {
                dispatch(Msg.GreenMsg(value = intent.value))
            }

            is WidgetConfigStore.Intent.RadiusChanged -> {
                dispatch(Msg.RadiusMsg(value = intent.value))
            }

            is WidgetConfigStore.Intent.RedChanged -> {
                dispatch(Msg.RedMsg(value = intent.value))
            }

            WidgetConfigStore.Intent.Save -> {
                sharedPreferencesStorage.saveWidget(
                    widgetConfig = WidgetConfig(
                        radius = getState().radius,
                        red = getState().red,
                        green = getState().green,
                        blue = getState().blue
                    )
                )
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
            }
        }
    }
}