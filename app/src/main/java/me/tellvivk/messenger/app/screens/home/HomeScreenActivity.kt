package me.tellvivk.messenger.app.screens.home

import android.app.ProgressDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.provider.Telephony
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDisposable
import kotlinx.android.synthetic.main.activity_home.*
import me.tellvivk.messenger.EXTRA_NEW_SMS_HASH
import me.tellvivk.messenger.R
import me.tellvivk.messenger.app.base.BaseActivity
import me.tellvivk.messenger.app.base.BaseView
import me.tellvivk.messenger.app.base.StateModel
import me.tellvivk.messenger.app.base.ViewEvent
import me.tellvivk.messenger.app.screens.home.adapter.MessagesAdapter
import me.tellvivk.messenger.app.screens.home.adapter.MessagesDiffCallback
import org.koin.android.ext.android.get


class HomeScreenActivity : BaseActivity() {

    private lateinit var adapter: MessagesAdapter
    private lateinit var viewModel: HomeScreenViewModel
    private lateinit var progressDialog: ProgressDialog

    private var newSMSHashCode: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        progressDialog = ProgressDialog(this)
        progressDialog.setCancelable(false)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Loading sms...")

        intent.extras?.let {
            if (it.containsKey(EXTRA_NEW_SMS_HASH)) {
                newSMSHashCode = it.getInt(EXTRA_NEW_SMS_HASH, -1)
            }
        }

        initView()
        registerToNewSms()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterToNewSms()
    }

    override fun initView() {
        viewModel = get()

        adapter = MessagesAdapter(
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
            .subscribe { updateView(it) }

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
                MessagesDiffCallback(
                    adapter.items,
                    this.items
                )
            )

            adapter.items = this.items
            diffResult.dispatchUpdatesTo(adapter)

            Handler().postDelayed({
                if (newSMSHashCode > 0 && adapter.items.size > 1) {
                    adapter.shake(1)
                    newSMSHashCode = -1
                }
            }, 300)

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

    private fun registerToNewSms() {
        val filter = IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)
        registerReceiver(smsReceivedReceiver, filter)
    }

    private fun unregisterToNewSms() {
        unregisterReceiver(smsReceivedReceiver)
    }

    private val smsReceivedReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            viewModel.loadSmses()
        }
    }


    companion object {
        fun start(activity: BaseActivity) {
            activity.apply {
                startActivity(Intent(activity, HomeScreenActivity::class.java))
            }
        }
    }
}
