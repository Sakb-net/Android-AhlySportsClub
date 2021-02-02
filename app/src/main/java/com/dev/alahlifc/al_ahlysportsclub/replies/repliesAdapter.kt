package com.dev.alahlifc.al_ahlysportsclub.replies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dev.alahlifc.al_ahlysportsclub.Base.Constants
import com.dev.alahlifc.al_ahlysportsclub.Base.Util
import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.databinding.ItemReplyBinding
import com.dev.alahlifc.al_ahlysportsclub.models.mComments


class repliesAdapter(private val mutableList: MutableList<mComments.ChildComment?>, private val  video_link : String) :
    RecyclerView.Adapter<repliesAdapter.ViewHolder>() {

    private  val VIEW_TYPE_ITEM = 1
    private  val VIEW_TYPE_LOADING = 2
    var onItemClick: ((position : Int) -> Unit)? = null
    var onOpenReplyClick: ((position : Int) -> Unit)? = null
    var onDeleteClick: ((position : Int) -> Unit)? = null


    inner class ViewHolder(val binding: ItemReplyBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            changeViewsFonts()

            binding.del.setOnClickListener {
                onDeleteClick?.invoke(adapterPosition)
            }


            /**  binding.reply.setOnClickListener {

                Timber.e(""+mutableList.get(adapterPosition))

                binding.root.context.startActivity(Intent(binding.root.context, AddCommentActivity::class.java)
                    .putExtra("link",mutableList.get(adapterPosition)?.link)
                    .putExtra("video_link",video_link)
                    .putExtra("reply",true))



            }*/


        }

        fun bind(data: Any) {
            val comment = data as mComments.ChildComment
            binding.commentData = comment





            if (comment.like)
                Glide.with(binding.like).load(R.drawable.ic_solid).into(binding.like)
            else
                Glide.with(binding.like).load(R.drawable.ic_like).into(binding.like)

            binding.like.setOnClickListener {

                onItemClick?.invoke(adapterPosition)

            }

            if (comment.ownerData=="1"){
                binding.del.visibility = View.VISIBLE
            }
            else{
                binding.del.visibility = View.GONE
            }

        }

       private fun changeViewsFonts() {
          Util.changeViewTypeFace(binding.root.context,
               Constants.FONT_REGULAR
               ,
               binding.name,binding.comment)
       }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)

        val binding: ItemReplyBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_reply, parent, false)

    return  ViewHolder(binding)
    }


    override fun getItemViewType(position: Int): Int {
        return if (mutableList.get(position) != null)
            VIEW_TYPE_ITEM
        else
            VIEW_TYPE_LOADING
    }

    fun addNullData() {
        mutableList.add(mutableList.size,null)
        notifyItemInserted(mutableList.size)
    }

    fun removeNull() {
        mutableList.removeAt(mutableList.size-1)
        notifyItemRemoved(mutableList.size-1)
    }

    fun addMoreItems(data: List<mComments.ChildComment?>) {
        mutableList.addAll(mutableList.size,data)
        notifyItemInserted(mutableList.size)
    }

    override fun getItemCount(): Int = mutableList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int)  {
            holder.bind(mutableList[position]!!)

    }

    fun updateLike(data: String, pos : Int) {
        // reverse
        if (data=="1") {
            mutableList[pos]?.like = true
            mutableList[pos]?.numLike = ""+ ((mutableList[pos]?.numLike)!!.toInt()+1).toString()
        }
        else {
            mutableList[pos]?.like = false
            val x = (mutableList[pos]?.numLike)!!.toInt()-1

            mutableList[pos]?.numLike = ""+if (x<=0)0 else x
        }

        notifyItemChanged(pos)
    }

    fun DeleteCmnt(pos: Int) {
        mutableList.removeAt(pos)
        notifyItemRemoved(pos)
    }


}