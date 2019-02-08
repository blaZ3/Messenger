package me.tellvivk.messenger.app.screens.home

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDisposable
import kotlinx.android.synthetic.main.activity_home.*
import me.tellvivk.messenger.R
import me.tellvivk.messenger.app.base.BaseActivity
import me.tellvivk.messenger.app.base.BaseView
import me.tellvivk.messenger.app.base.StateModel
import me.tellvivk.messenger.app.base.ViewEvent
import me.tellvivk.messenger.app.screens.home.adapter.HomeScreenAdapter
import me.tellvivk.messenger.app.screens.home.adapter.SmsItemListDiffCallback
import org.koin.android.ext.android.get

class HomeScreen : BaseActivity() {

    private lateinit var adapter: HomeScreenAdapter
    private lateinit var viewModel: HomeScreenViewModel
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        progressDialog = ProgressDialog(this)
        progressDialog.setCancelable(false)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Loading sms...")

        initView()
    }

    override fun initView() {
        viewModel = get()

        adapter = HomeScreenAdapter(
            context = this, items = arrayListOf(),
            valueFormatter = get()
        )
        recyclerMessages.layoutManager = LinearLayoutManager(
            this,
            RecyclerView.VERTICAL, false
        )
        recyclerMessages.adapter = adapter


        viewModel.getViewModelObservable()
            .autoDisposable(AndroidLifecycleScopeProvider.from(this))
            .subscribe{ updateView(it) }

        viewModel.getViewEventObservable()
            .autoDisposable(AndroidLifecycleScopeProvider.from(this))
            .subscribe { handleEvent(it) }
    }

    override fun getParentView(): BaseView? {
        return null
    }

    override fun updateView(stateModel: StateModel) {
        (stateModel as HomeScreenStateModel).apply {
            val diffResult = DiffUtil.calculateDiff(
                SmsItemListDiffCallback(
                    adapter.items,
                    this.items
                )
            )
            adapter.items = this.items
            diffResult.dispatchUpdatesTo(adapter)
        }
    }

    override fun handleEvent(event: ViewEvent) {
        (event as HomeScreenViewEvent).apply {
            when (event) {
                InitHomeScreen -> {
                    viewModel.loadSmses()
                }
                LoadingSMS -> progressDialog.show()
                LoadingSMSDone -> progressDialog.dismiss()
                is LoadingSMSError -> {
                    progressDialog.dismiss()
                    showToast(event.msg)
                }
            }
        }
    }


    companion object {
        fun start(activity: BaseActivity) {
            activity.apply {
                startActivity(Intent(activity, HomeScreen::class.java))
            }
        }
    }
}
