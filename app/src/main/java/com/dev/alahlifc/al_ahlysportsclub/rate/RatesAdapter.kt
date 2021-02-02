package com.dev.alahlifc.al_ahlysportsclub.rate

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.databinding.ItemRateBinding
import com.dev.alahlifc.al_ahlysportsclub.models.mComments
import com.dev.alahlifc.al_ahlysportsclub.databinding.ItemLoadingBinding
import kotlinx.android.synthetic.main.layout_loading_item.view.*


class RatesAdapter(private var mutableList: MutableList<mComments.Data?>, private val video_link : String) :
    RecyclerView.Adapter< RecyclerView.ViewHolder>() {

    private  val VIEW_TYPE_ITEM = 1
    private  val VIEW_TYPE_LOADING = 2
    var onItemClick: ((position : Int) -> Unit)? = null
    var onOpenReplyClick: ((position : Int) -> Unit)? = null
    var onDeleteClick: ((position : Int) -> Unit)? = null


    inner class ViewHolder(val binding: ItemRateBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
         //   changeViewsFonts()

//            binding.replyll.setOnClickListener {
//                onOpenReplyClick?.invoke(adapterPosition)
//            }
//
//            binding.reply.setOnClickListener {
//                binding.root.context.startActivity(
//                    Intent(binding.root.context, AddCommentActivity::class.java)
//                        .putExtra("link", mutableList.get(adapterPosition)?.link)
//                        .putExtra("video_link",video_link)
//                        .putExtra("reply",true)
//                        .putExtra("type","news")
//                )
//            }
//
            binding.del.setOnClickListener {
                onDeleteClick?.invoke(adapterPosition)
            }




        }

        fun onRecycled() {
            binding.commentData  = (null)
            binding.executePendingBindings()
        }

        fun bind(data: Any) {
            val comment = data as mComments.Data
            binding.commentData = comment

            if (comment.ownerData=="1"){
                binding.del.visibility = View.VISIBLE
            }
            else{
                binding.del.visibility = View.GONE
            }

//            if (!comment.childComments.isNullOrEmpty()){
//                binding.replyll.visibility = View.VISIBLE
//                binding.commentReply.text = comment.childComments[0]!!.content
//                binding.nameReply.text = comment.childComments[0]!!.replayUserName
//                binding.personPhotoReply.loadCircleImage(Constants.baseUrl + comment.childComments[0]!!.replayUserImage)
//
//
//            }else{
//                binding.replyll.visibility = View.GONE
//            }

           /* binding.root.setOnClickListener {
                onItemClick?.invoke(adapterPosition)

            }*/

//            if (comment.like)
//                Glide.with(binding.like).load(R.drawable.ic_solid).into(binding.like)
//            else
//                Glide.with(binding.like).load(R.drawable.ic_like).into(binding.like)
//
//            binding.like.setOnClickListener {
//
//                onItemClick?.invoke(adapterPosition)
//
//            }
//


        }

//       private fun changeViewsFonts() {
//          Util.changeViewTypeFace(binding.root.context,
//               Constants.FONT_REGULAR
//               ,
//               binding.name,binding.comment)
//       }

    }

    inner class LoadingViewHolder(val binding: View) : RecyclerView.ViewHolder(binding) {
        fun bind() {
            binding.progressBar.isIndeterminate = true
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {



        if (viewType == VIEW_TYPE_ITEM) {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding: ItemRateBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_rate, parent, false)

            return ViewHolder(binding)
        }

        else {
            // Create a new view.
            val v = LayoutInflater.from(parent.context).inflate(R.layout.layout_loading_item, parent, false)

            return LoadingViewHolder(v)

        }

    }

    override fun getItemId(position: Int): Long {
        if (mutableList[position]==null)
            return super.getItemId(position)
            else
        return mutableList[position]!!.link.hashCode().toLong()
    }




   /* fun addNullData() {
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
    }*/

    override fun getItemCount(): Int = mutableList.size


    override fun onBindViewHolder(holder:  RecyclerView.ViewHolder, position: Int)  {
        if (holder is ViewHolder)
            holder.bind(mutableList[position]!!)
        else if (holder is LoadingViewHolder)
            holder.bind()

    }

    override fun onViewRecycled(holder:  RecyclerView.ViewHolder) {
        if (holder.itemViewType==VIEW_TYPE_ITEM)
            (holder as ViewHolder) .onRecycled()
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

    fun setItems(data: MutableList<mComments.Data?>) {
       // mutableList.addAll(0,data)
        mutableList = data
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (mutableList[position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    fun loadMore(data: MutableList<mComments.Data?>) {
        mutableList.addAll(mutableList.size-1,data)
        notifyDataSetChanged()
    }


}