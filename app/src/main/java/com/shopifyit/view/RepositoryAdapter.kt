package com.shopifyit.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shopifyit.R
import com.shopifyit.data.model.Repository
import com.shopifyit.domain.parseIsoDate
import com.shopifyit.domain.toStringDateFormat
import com.shopifyit.domain.toStringYesOrNo
import kotlinx.android.synthetic.main.item_repository.view.*

/**
 * [RecyclerView.Adapter] that can display a [Repository] and makes a call to the
 * specified [MainActivity.OnListRepositoryInteractionListener].
 */
class RepositoryAdapter(
    private val mValues: List<Repository>,
    private val mListener: MainActivity.OnListRepositoryInteractionListener?
) : RecyclerView.Adapter<RepositoryAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Repository
            mListener?.onRepositoryClick(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_repository, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mDateView.text = holder.mView.resources.getString(
            R.string.date_created,
            item.created_at.parseIsoDate().toStringDateFormat()
        )

        holder.mNameView.text = item.name

        holder.mForkView.text = item.fork.toStringYesOrNo()

        holder.mStargazersView.text = item.stargazers_count.toString()

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mNameView: TextView = mView.nameView
        val mDateView: TextView = mView.dateView
        val mStargazersView: TextView = mView.stargazersView
        val mForkView: TextView = mView.forkView

        override fun toString(): String {
            return super.toString() + " '" + mNameView.text + "'"
        }
    }
}