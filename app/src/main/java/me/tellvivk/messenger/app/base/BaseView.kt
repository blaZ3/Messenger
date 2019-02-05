package me.tellvivk.messenger.app.base

interface BaseView{
    fun initView()
    fun getParentView(): BaseView?
    fun updateView(stateModel: StateModel)
    fun handleEvent(event: ViewEvent)
}