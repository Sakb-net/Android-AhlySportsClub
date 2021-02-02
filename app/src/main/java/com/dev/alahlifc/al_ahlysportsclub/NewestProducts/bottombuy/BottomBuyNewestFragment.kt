package com.dev.alahlifc.al_ahlysportsclub.NewestProducts.bottombuy

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.dev.alahlifc.al_ahlysportsclub.Base.*
import com.dev.alahlifc.al_ahlysportsclub.Fees.SelectFeesActivity
import com.dev.alahlifc.al_ahlysportsclub.Login.LoginActivity

import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.databinding.BottomBuyNewestFragmentBinding
import com.dev.alahlifc.al_ahlysportsclub.databinding.BottomBuyNewestFragmentBindingImpl
import com.dev.alahlifc.al_ahlysportsclub.models.mProduct
import com.dev.alahlifc.al_ahlysportsclub.models.szModel
import com.dev.alahlifc.al_ahlysportsclub.store.PrefManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.greenrobot.eventbus.EventBus
import timber.log.Timber
import java.io.IOException
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog


class BottomBuyNewestFragment : BottomSheetDialogFragment() {

    private lateinit var binding : BottomBuyNewestFragmentBinding
    private lateinit var viewModel: BottomBuyNewestViewModel
    private  var newItemArgs  : mProduct.Data? = null

    private  var result : ArrayList<mProduct.Fee>? = null
    private  var printName :String? = ""
    private lateinit var adapter: SelectNSizeAdapter
    private lateinit var adapterColor: SelectNColorAdapter


    private val mBottomSheetBehaviorCallback = object : BottomSheetBehavior.BottomSheetCallback() {

        override fun onStateChanged(bottomSheet: View, newState: Int) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN)
                dismiss()
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {}
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //  context?.toast("req code "+requestCode)
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                //    context?.toast("req code "+requestCode)
                result = data!!.getParcelableArrayListExtra("result")
                printName = data!!.getStringExtra("printName")
                Timber.e("++comes intent+++ "+ result.toString())
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }

        //  super.onActivityResult(requestCode, resultCode, data)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newItemArgs = arguments?.getParcelable("newItem")


    }
   /* override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding  = DataBindingUtil.inflate(inflater, R.layout.bottom_buy_newest_fragment, container, false)
        return binding.root
    }*/


    fun addToCart(weight : String, color : String, fesslist: MutableList<String>) {


        val user = PrefManager.getUser()

        val lang = PrefManager.getLanguage()

        Timber.e("addUpdateCartNow===="+
                ""+user?.access_token!!+"\n"+ ""+newItemArgs?.link+"\n"+ ""+binding.quantityNum.text.toString()+"\n"
                +  weight+"\n"+ color+ "\n"+
               ""+ printName!!+ "\n"+fesslist.toString()+ "\n"+lang!!)

        viewModel.AddUpdateCart(
            user?.access_token!!,
            ""+newItemArgs?.link,
            ""+binding.quantityNum.text.toString(),
            weight,
            color,
            printName!!,
            fesslist
            ,lang!!)
            .observe(this, Observer {


                it.apply {
                    when (this) {
                        is Resource.Progress -> {
                            if (loading);
                            /*    dialog = context?.showLoadingDialog()!!
                            else
                                dialog.dismiss()*/
                        }
                        is Resource.Success -> {

                            // context?.toast(""+data.message)
                            // Timber.e(""+data.data.toString())

                            if (data.statusCode==11) {
                                //  context?.toast("status " + data.statusCode)
                                PrefManager.saveUser(null)
                                val logoutIntent = Intent(context, LoginActivity::class.java)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                context?.startActivity(logoutIntent)
                                activity?.finish()
                                return@Observer

                            }


                            //  num = PrefManager.getCartCount()!!
                            //   invalidateOptionsMenu()

                            Timber.e("cart_num_is"+""+data.data.productCart.size)
                            PrefManager.saveCartCount(data.data.productCart.size)

                            EventBus.getDefault().post("cart_update")
                            context?.toast(""+data.message)
                            dismiss()


                        }
                        is Resource.Failure -> {
                            if (e is IOException) {
                                context?.toast(getString(R.string.need_internet))
                            } else {
                                context?.toast(getString(R.string.something_wrong))
                            }
                        }
                    }
                }
            })
    }
 /**   private fun changeViewsFonts() {
        Util.changeViewTypeFace(context,
            Constants.FONT_REGULAR,
            binding.name,binding.chooseSz,
            binding.show,
            binding.chooseQntity,
            binding.chooseColor,
            binding.feesChoose,
            binding.chooseColorSelect,
            binding.positiveBtn)



    }*/
//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
//        bottomSheetDialog.setOnShowListener { dia ->
//            val dialog = dia as BottomSheetDialog
//            val bottomSheet = dialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
//            BottomSheetBehavior.from(bottomSheet!!).state = BottomSheetBehavior.STATE_EXPANDED
//           // BottomSheetBehavior.from(bottomSheet).skipCollapsed = true
//            BottomSheetBehavior.from(bottomSheet).isHideable = true
//        }
//        return bottomSheetDialog
//
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding  = DataBindingUtil.inflate(
            inflater, R.layout.bottom_buy_newest_fragment, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.setOnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            val bottomSheet = d.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout
            val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
          //  bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            bottomSheetBehavior.peekHeight = bottomSheet.height

        }


        viewModel = ViewModelProviders.of(this).get(BottomBuyNewestViewModel::class.java)



        binding.name.text = ""+newItemArgs?.name.toString()
        binding.productImg.loadImage(""+ Constants.baseUrl+newItemArgs?.image,92,95)
        binding.mRecyclerView.setHasFixedSize(true)
        binding.mRecyclerViewColors.setHasFixedSize(true)





        val handler = Handler()
        Thread(Runnable {
            //your code
            val  mModelList = ArrayList<szModel>()
            for (i in 0.. (newItemArgs?.weight?.size)!!-1) {
                val weight : String = newItemArgs?.weight?.get(i)?.name.toString()
                mModelList.add(szModel(""+weight))
            }

            val  mColorModelList  = ArrayList<szModel>()
            for (i in 0.. (newItemArgs?.color?.size)!!-1) {
                val color : String = newItemArgs?.color?.get(i)?.name.toString()
                mColorModelList?.add(szModel(""+color))
            }

            handler.postDelayed(Runnable {
                adapter =  SelectNSizeAdapter(mModelList)
                binding.mRecyclerView.adapter = adapter


                adapterColor =  SelectNColorAdapter(mColorModelList)
                binding.mRecyclerViewColors.adapter = adapterColor


                binding.positiveBtn.setOnClickListener {

                    if (adapter.previousPosition==-1&& mModelList.size>0){
                        context?.toast(getString(R.string.select_sz))
                    }
                    else if (adapterColor.previousPosition==-1 && mColorModelList!!.size>0){
                        context?.toast(getString(R.string.select_color))
                    }

                    else {
                        val fesslist : MutableList<String> = mutableListOf()
                        result?.map {
                            fesslist.add(it.link)
                        }

                        val sz : String = if(adapter.previousPosition!=-1){ mModelList[adapter.previousPosition].text}
                        else {""}
                        val colr : String = if(adapterColor.previousPosition!=-1) {
                            mColorModelList!![adapterColor.previousPosition].text}
                        else {""}





                        val user = PrefManager.getUser()
                        if (user==null){
                            context?.showDialog(R.drawable.dialog_warning,getString(R.string.are_sure_logIn), {
                                //   it ->it?.dismiss()
                                val intent = Intent(activity , LoginActivity::class.java)
                                startActivity(intent)


                            },{
                                    it -> it?.dismiss()


                            })}
                        else{
                            addToCart(sz,colr,fesslist)
                        }

                    }
                }
                // todo
                binding.show.setOnClickListener{
                    startActivityForResult(Intent(context, SelectFeesActivity::class.java)
                        .putExtra("feesNewest",newItemArgs),1)

                }
                binding.plusTv.setOnClickListener {
                    //  binding.quantityNum.text =
                    val quantity : Int =   (binding.quantityNum.text.toString()).toInt()
                    binding.quantityNum.text = (quantity+1).toString()


                }
                binding.minusTv.setOnClickListener {
                    //  binding.quantityNum.text =
                    val quantity : Int =   (binding.quantityNum.text.toString()).toInt()
                    if (quantity>=2)
                        binding.quantityNum.text = (quantity-1).toString()
                    else
                        context?.toast(getString(R.string.order_one_item))


                }

            },50)
        }).start()


        /**  val  mModelList = ArrayList<szModel>()
        for (i in 0.. (newItemArgs?.weight?.size)!!-1) {
        val weight : String = newItemArgs?.weight?.get(i)?.name.toString()
        mModelList.add(szModel(""+weight))
        }
        adapter =  SelectNSizeAdapter(mModelList)
        binding.mRecyclerView.adapter = adapter
        val  mColorModelList = ArrayList<szModel>()
        for (i in 0.. (newItemArgs?.color?.size)!!-1) {
        val color : String = newItemArgs?.color?.get(i)?.name.toString()
        mColorModelList.add(szModel(""+color))
        }
        adapterColor =  SelectNColorAdapter(mColorModelList)
        binding.mRecyclerViewColors.adapter = adapterColor
        binding.positiveBtn.setOnClickListener {

        if (adapter.previousPosition==-1&& mModelList.size>0){
        context?.toast(getString(R.string.select_sz))
        }
        else if (adapterColor.previousPosition==-1 && mColorModelList.size>0){
        context?.toast(getString(R.string.select_color))
        }

        else {
        val fesslist : MutableList<String> = mutableListOf()
        result?.map {
        fesslist.add(it.link)
        }

        val sz : String = if(adapter.previousPosition!=-1){ mModelList[adapter.previousPosition].text}
        else {""}
        val colr : String = if(adapterColor.previousPosition!=-1) {mColorModelList[adapterColor.previousPosition].text}
        else {""}





        val user = PrefManager.getUser()
        if (user==null){
        context?.showDialog(R.drawable.dialog_warning,getString(R.string.are_sure_logIn), {
        //   it ->it?.dismiss()
        val intent = Intent(activity , LoginActivity::class.java)
        startActivity(intent)


        },{
        it -> it?.dismiss()


        })}
        else{
        addToCart(sz,colr,fesslist)
        }

        }
        }
        // todo
        binding.show.setOnClickListener{
        startActivityForResult(Intent(context, SelectFeesActivity::class.java)
        .putExtra("feesNewest",newItemArgs),1)

        }
        binding.plusTv.setOnClickListener {
        //  binding.quantityNum.text =
        val quantity : Int =   (binding.quantityNum.text.toString()).toInt()
        binding.quantityNum.text = (quantity+1).toString()


        }
        binding.minusTv.setOnClickListener {
        //  binding.quantityNum.text =
        val quantity : Int =   (binding.quantityNum.text.toString()).toInt()
        if (quantity>=2)
        binding.quantityNum.text = (quantity-1).toString()
        else
        context?.toast(getString(R.string.order_one_item))


        }*/


    }

  /***  override fun setupDialog(dialog: Dialog, style: Int) {
        val inflater = LayoutInflater.from(activity)
      //  val v = LayoutInflater.from(activity).inflate(R.layout.dialog, null)
        binding  = DataBindingUtil.inflate(inflater, R.layout.bottom_buy_newest_fragment, null, false)

        dialog.setContentView(binding.root)

        val params = (binding.root.getParent() as View).layoutParams as CoordinatorLayout.LayoutParams
        val behavior = params.getBehavior()
        (behavior as BottomSheetBehavior<*>).state = BottomSheetBehavior.STATE_EXPANDED



       // changeViewsFonts()


    }*/



}

