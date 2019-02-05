package me.tellvivk.messenger.app.base

import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.BehaviorSubject

abstract class BaseViewModel : ViewModel() {
    private var stateModelSubject: BehaviorSubject<StateModel> = BehaviorSubject.create()
    private val eventsSubject: BehaviorSubject<ViewEvent> = BehaviorSubject.create()

    protected lateinit var model: StateModel
    protected lateinit var initEvent: ViewEvent

    fun getViewModelObservable(): io.reactivex.Observable<StateModel> {
        return stateModelSubject
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .startWith(model)
    }

    fun getViewEventObservable(): Observable<ViewEvent> {
        return eventsSubject
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .startWith(initEvent)
    }

    fun updateModel(newStateModel: StateModel) {
        model = newStateModel
        stateModelSubject.onNext(model)
    }

    fun sendEvent(event: ViewEvent) {
        eventsSubject.onNext(event)
    }
}

interface ViewEvent

abstract class StateModel
data class ProgressStateModel(
    val isShown: Boolean = false,
    val text: String = ""
) : StateModel()
