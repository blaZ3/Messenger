package me.tellvivk.messenger.app.screens.home

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.tellvivk.messenger.app.base.BaseViewModel
import me.tellvivk.messenger.app.base.StateModel
import me.tellvivk.messenger.app.base.ViewEvent
import me.tellvivk.messenger.app.model.sms.SMSRepositoryI
import me.tellvivk.messenger.app.model.sms.hrInMillis
import me.tellvivk.messenger.app.screens.home.adapter.SMSListItem
import me.tellvivk.messenger.app.screens.home.adapter.SMSListItemHeader
import me.tellvivk.messenger.app.screens.home.adapter.SMSListItemSMS

class HomeScreenViewModel(private val smsRepo: SMSRepositoryI) : BaseViewModel() {

    init {
        model = HomeScreenStateModel()
        initEvent = InitHomeScreen
    }

    fun loadSmses() {
        var currPage: Int
        (model as HomeScreenStateModel).apply {
            currPage = this.page
        }
        sendEvent(LoadingSMS)
        smsRepo.getSMS(page = currPage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess { list ->
                var currMillis = System.currentTimeMillis()
                var currHrThreshold = hrInMillis
                var currHrs = 0

                val newList = ArrayList<SMSListItem>()
                var headerItem = SMSListItem(
                    type = SMSListItemHeader,
                    item = "$currHrs hr ago"
                )
                val tempList = arrayListOf<SMSListItem>()
                list.map { sms ->
                    if (currMillis.minus(sms.date!!) < currHrThreshold) {
                        tempList.add(
                            SMSListItem(
                                type = SMSListItemSMS,
                                item = sms
                            )
                        )
                    } else {
                        if (tempList.size > 0) {
                            newList.add(headerItem)
                            newList.addAll(tempList)
                            tempList.clear()
                        }

                        currHrThreshold += currHrThreshold
                        currHrs++
                        headerItem = SMSListItem(
                            type = SMSListItemHeader,
                            item = if(currHrs > 0) "$currHrs hrs ago" else "$currHrs hr ago"
                        )
                    }
                }

                if (tempList.size>0){
                    newList.add(headerItem)
                    newList.addAll(tempList)
                }

                sendEvent(LoadingSMSDone)
                (model as HomeScreenStateModel).apply {
                    updateModel(this.copy(items = newList))
                }
            }.doOnError {
                sendEvent(LoadingSMSError(it.message.toString()))
            }.subscribe()

    }

}


data class HomeScreenStateModel(
    val page: Int = 1,
    val items: ArrayList<SMSListItem> = arrayListOf()
) : StateModel()


sealed class HomeScreenViewEvent : ViewEvent
object InitHomeScreen : HomeScreenViewEvent()
object LoadingSMS : HomeScreenViewEvent()
object LoadingSMSDone : HomeScreenViewEvent()
data class LoadingSMSError(val msg: String) : HomeScreenViewEvent()

