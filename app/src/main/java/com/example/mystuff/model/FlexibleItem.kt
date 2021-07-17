package com.example.mystuff.model

import android.app.Activity
import android.content.res.Resources
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mystuff.R
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.viewholders.FlexibleViewHolder

class FlexibleItem( val item:Item ):AbstractFlexibleItem<FlexibleItem.ViewHolder>() {

    val id: String

    init {
        this.id = item.id!!
    }

    class ViewHolder(
        view: View,
        flexibleAdapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>
    ) : FlexibleViewHolder(view, flexibleAdapter) {

        val txtvNome:TextView
        val txtvQuantidade:TextView

        init {
            txtvNome = view.findViewById(R.id.txtvNomeItem)
            txtvQuantidade = view.findViewById(R.id.txtvQuantidadeItem)
        }

    }

    override fun createViewHolder( p0:View?, p1:FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>? ):ViewHolder {
        return ViewHolder(p0!!, p1!!)
    }

    override fun bindViewHolder(
        p0: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?,
        p1: ViewHolder?,
        p2: Int,
        p3: MutableList<Any>?
    ) {
        p1!!.apply {
            txtvNome.text = item.nome
            //txtvQuantidade.text = Resources.getSystem().getString(R.string.telaPrincipal_quantidade, item.quantidadeTotal)
            txtvQuantidade.text = Resources.getSystem().getString(R.string.telaPrincipal_quantidade,1)
        }
    }

    override fun equals( other:Any? ): Boolean {
        if(other is FlexibleItem) return this.id.equals(other.id)
        else return false
    }

    override fun hashCode():Int {
        return id.hashCode()
    }

    override fun getLayoutRes(): Int {
        return R.layout.item_lista_principal_item
    }

}