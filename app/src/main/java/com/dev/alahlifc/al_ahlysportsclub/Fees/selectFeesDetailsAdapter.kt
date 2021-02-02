package com.dev.alahlifc.al_ahlysportsclub.Fees

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.alahlifc.al_ahlysportsclub.Base.Constants
import com.dev.alahlifc.al_ahlysportsclub.Base.Util
import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.models.mItemsInCategory
import com.dev.alahlifc.al_ahlysportsclub.models.mProduct
import com.dev.alahlifc.al_ahlysportsclub.models.mProductSingle
import kotlinx.android.synthetic.main.item_select_fees.view.*


class selectFeesDetailsAdapter(private val mutableList: ArrayList<mProductSingle.Data.Fee>) :
    RecyclerView.Adapter<selectFeesDetailsAdapter.ViewHolder>() {

    var onItemClick: ((isButton : Int,position : Int) -> Unit)? = null

   fun getListAfterEdit() = mutableList



    inner class ViewHolder(val v: View) : RecyclerView.ViewHolder(v) {

        init {
            changeViewsFonts()
        }


        private fun changeViewsFonts() {
            Util.changeViewTypeFace(v.context,
                Constants.FONT_REGULAR
                ,
               v.fees_name, v.btn_addName)
        }
        fun bind(fee: mProductSingle.Data.Fee) {

            with(v){
                fees_name.text = fee.name
               btn_addName.setOnClickListener {
                   onItemClick?.invoke(1,adapterPosition)
               }
               if (fee.id==3){
                   btn_addName.visibility = View.VISIBLE
               }else{
                   btn_addName.visibility = View.GONE
               }

                checkIv.setOnClickListener {
                    //  onItemClick?.invoke(0,adapterPosition)

                    if (mutableList[adapterPosition].isSelected == false) {

                        mutableList[adapterPosition].isSelected= true

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            checkIv.setImageDrawable(
                                getResources().getDrawable(
                                    R.drawable.ic_check_circle_black_24dp,
                                    v.context.getApplicationContext().getTheme()
                                )
                            )
                        } else {
                            checkIv.setImageDrawable(getResources().getDrawable(R.drawable.ic_check_circle_black_24dp))
                        }


                    }else {
                         mutableList[adapterPosition].isSelected = false


                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            checkIv.setImageDrawable(
                                getResources().getDrawable(
                                    R.drawable.ic_iconfinder_icon_ios_circle_outline_,
                                    v.context.getApplicationContext().getTheme()
                                )
                            )
                        } else {
                            checkIv.setImageDrawable(getResources().getDrawable(R.drawable.ic_iconfinder_icon_ios_circle_outline_))
                        }




                    }
                }

            }
        }


//        fun bind(data: Any) {
//          //  binding.categoriesData = data as mProductCategories.Data
//            binding.root.setOnClickListener {
//                onItemClick?.invoke(adapterPosition)
//
//            }
//           /// changeViewsFonts()
//        }
//
//       private fun changeViewsFonts() {
//           Util.changeViewTypeFace(binding.root.context,
//               Constants.FONT_REGULAR,
//               binding.feesName)
//
//       }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)

        val v=
            layoutInflater.inflate( R.layout.item_select_fees, parent, false)

    return  ViewHolder(v)
    }





    override fun getItemCount(): Int =mutableList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int)  {
           holder.bind(mutableList[position]!!)

    }





}