package com.dev.alahlifc.al_ahlysportsclub.Details

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dev.alahlifc.al_ahlysportsclub.AddComment.AddCommentActivity
import com.dev.alahlifc.al_ahlysportsclub.Base.Constants
import com.dev.alahlifc.al_ahlysportsclub.Base.Util
import com.dev.alahlifc.al_ahlysportsclub.Base.loadCircleImage
import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.databinding.ItemCommentBinding
import com.dev.alahlifc.al_ahlysportsclub.models.mComments


class DetailsCommentsAdapter(private val mutableList: MutableList<mComments.Data?>, private val video_link : String) :
    RecyclerView.Adapter<DetailsCommentsAdapter.ViewHolder>() {

    private  val VIEW_TYPE_ITEM = 1
    private  val VIEW_TYPE_LOADING = 2
    var onItemClick: ((position : Int) -> Unit)? = null
    var onOpenReplyClick: ((position : Int) -> Unit)? = null
    var onDeleteClick: ((position : Int) -> Unit)? = null


    inner class ViewHolder(val binding: ItemCommentBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            changeViewsFonts()

            binding.replyll.setOnClickListener {
                onOpenReplyClick?.invoke(adapterPosition)
            }

            binding.reply.setOnClickListener {
                binding.root.context.startActivity(
                    Intent(binding.root.context, AddCommentActivity::class.java)
                        .putExtra("link", mutableList.get(adapterPosition)?.link)
                        .putExtra("video_link",video_link)
                        .putExtra("reply",true)
                        .putExtra("type","video")
                )
            }

            binding.del.setOnClickListener {
                onDeleteClick?.invoke(adapterPosition)
            }




        }

        fun bind(data: Any) {
            val comment = data as mComments.Data
            binding.commentData = comment

            if (!comment.childComments.isNullOrEmpty()){
                binding.replyll.visibility = View.VISIBLE
                binding.commentReply.text = comment.childComments[0]!!.content
                binding.nameReply.text = comment.childComments[0]!!.replayUserName
                binding.personPhotoReply.loadCircleImage(Constants.baseUrl + comment.childComments[0]!!.replayUserImage)


            }else{
                binding.replyll.visibility = View.GONE
            }

           /* binding.root.setOnClickListener {
                onItemClick?.invoke(adapterPosition)

            }*/

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

        val binding: ItemCommentBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_comment, parent, false)

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

    fun addMoreItems(data: List<mComments.Data?>) {
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

    fun addComment(obj: mComments.Data) {

        mutableList.add(0,obj)
        notifyItemInserted(0)
    }


}