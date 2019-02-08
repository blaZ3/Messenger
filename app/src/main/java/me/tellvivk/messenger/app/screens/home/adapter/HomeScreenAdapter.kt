package me.tellvivk.messenger.app.screens.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_item_header.view.*
import kotlinx.android.synthetic.main.layout_item_sms.view.*
import me.tellvivk.messenger.R
import me.tellvivk.messenger.app.model.sms.SMS
import me.tellvivk.messenger.helpers.valueFormatter.ValueFormatterI
import java.lang.ref.WeakReference

class HomeScreenAdapter(context: Context,
                        var items: ArrayList<SMSListItem>,
                        private val valueFormatter: ValueFormatterI): RecyclerView.Adapter<HomeScreenAdapter.HomeScreenAdapterViewHolder>() {


    private var weakContext: WeakReference<Context> = WeakReference(context)

    abstract class HomeScreenAdapterViewHolder(private val view: View): RecyclerView.ViewHolder(view){
        abstract fun onBind(item: SMSListItem)
    }

    class HomeScreenAdapterHeaderViewHolder(private val view: View):
        HomeScreenAdapterViewHolder(view){
        override fun onBind(item: SMSListItem) {
            (item.item as String).apply{
                view.txtMessageListHeader.text = this
            }
        }
    }

    class HomeScreenAdapterSmsViewHolder(private val view: View,
                                         private val valueFormatter: ValueFormatterI):
        HomeScreenAdapterViewHolder(view){
        override fun onBind(item: SMSListItem) {
            (item.item as SMS).apply{
                view.txtMessageItemDP.text = valueFormatter.getDpString(this.address!!)
                view.txtMessageItemAddress.text = this.address
                view.txtMessageItemSubject.text = valueFormatter
                    .getSmsSubject(this)
                view.txtMessageItemTime.text = valueFormatter
                    .getTimeForDisplay(this.date)
                view.txtMessageItemBody.text = this.body
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            HomeScreenAdapterViewHolder {
        weakContext.get()?.let { context ->
            if (viewType == VIEW_TYPE_HEADER) {
                val view = LayoutInflater.from(context).inflate(R.layout.layout_item_header, parent,
                    false)
                return HomeScreenAdapterHeaderViewHolder(view)
            }

            val view = LayoutInflater.from(context).inflate(R.layout.layout_item_sms, parent,
                false)
            return HomeScreenAdapterSmsViewHolder(view, valueFormatter)
        }

        throw IllegalStateException("Context cannot be null")
    }

    override fun onBindViewHolder(holder: HomeScreenAdapterViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun getItemViewType(position: Int): Int {
        if(items[position].type == SMSListItemHeader){
            return VIEW_TYPE_HEADER
        }

        return VIEW_TYPE_SMS
    }

    override fun getItemCount(): Int {
        return items.size
    }

}


data class SMSListItem(
    val type: SMSListItemType,
    val item: Any
)

sealed class SMSListItemType
object SMSListItemHeader: SMSListItemType()
object SMSListItemSMS: SMSListItemType()

const val VIEW_TYPE_HEADER = 10000
const val VIEW_TYPE_SMS = 10001