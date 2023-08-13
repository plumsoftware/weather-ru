package ru.plumsoftware.weatherforecast.presentation.content.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import kotlinx.coroutines.launch

class ContentStoreFactory(private val storeFactory: StoreFactory) {

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): ContentStore =
        object : ContentStore,
            Store<ContentStore.Intent, ContentStore.State, ContentStore.Label> by storeFactory.create(
                name = "Authorization",
                initialState = ContentStore.State(),
                bootstrapper = coroutineBootstrapper {
                    launch { }
                },
                reducer = ContentStoreFactory.ReducerImpl,
                executorFactory = ::ExecutorImpl
            ) {
        }

    sealed interface Action {

    }

    sealed interface Msg {
        data class Todo(
            val value: Boolean
        ) : Msg
    }

    private object ReducerImpl : Reducer<ContentStore.State, Msg> {

        override fun ContentStore.State.reduce(msg: Msg): ContentStore.State =
            when (msg) {
                is Msg.Todo -> copy(
                    todo = msg.value,
                )
            }
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<ContentStore.Intent, Action, ContentStore.State, Msg, ContentStore.Label>() {

        override fun executeIntent(
            intent: ContentStore.Intent,
            getState: () -> ContentStore.State
        ) =
            when (intent) {
                is ContentStore.Intent.Todo -> {
                    dispatch(
                        Msg.Todo(
                            value = false
                        )
                    )
                }
            }

        override fun executeAction(action: Action, getState: () -> ContentStore.State) =
            when (action) {
                else -> {}
            }
    }
}