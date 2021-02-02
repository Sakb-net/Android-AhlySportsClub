package com.dev.alahlifc.al_ahlysportsclub.EditProfile

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.dev.alahlifc.al_ahlysportsclub.Base.*
import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.databinding.ActivityEditProfileBinding
import com.dev.alahlifc.al_ahlysportsclub.models.mMyProfile
import com.dev.alahlifc.al_ahlysportsclub.store.PrefManager
import com.dev.alahlifc.al_ahlysportsclub.utils.ImageCompression
import com.dev.alahlifc.al_ahlysportsclub.utils.ImageCompressionListener
import timber.log.Timber
import java.io.IOException
const val STORAGE_REQUEST_CODE = 15
const val GALLERY_REQUEST_CODE = 12

class EditProfileActivity : LocalizationActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var viewModel: EditProfileViewModel
    private lateinit var context : Context
    private lateinit var dialog: Dialog
    private var userInfo : mMyProfile.Data? = null
    private var objCity : Array<String> ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile)
        context = this
        viewModel = ViewModelProviders.of(this).get(EditProfileViewModel::class.java)

        binding.toolbar.setTitle("")
        setSupportActionBar(binding.toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)
        changeViewsFonts()

        userInfo = intent.getParcelableExtra("myProfile_key")
        Timber.e(""+userInfo)

        if (userInfo!=null)
            viewModel.userInfo = userInfo

         if(viewModel.userInfo!=null)
             binding.profileInfoData = viewModel.userInfo

        initSpinner()

        binding.personPhoto.setOnClickListener {

            if (isPermissionGranted()) {
                pickPhotoFromGallery()
            }
            else {
                // IF he refuse before ...
                if (ActivityCompat.shouldShowRequestPermissionRationale( this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    || ActivityCompat.shouldShowRequestPermissionRationale( this,Manifest.permission.READ_EXTERNAL_STORAGE) ) {
                    context.toast(getString(R.string.permissions_info_sd))
                }else {
                    requestPermeation()
                }
            }
        }
      binding.buttonSave.setOnClickListener {
          if(userInfo!=null) {
             performEdit(
                 userInfo!!.accessToken!!,

                 binding.EmailEt.text.toString(),
                 binding.UserNameEt.text.toString(),
                 binding.PhoneEt.text.toString(),
                 userInfo!!.image!!,

                  "saudi",
                  binding.CityTv.text.toString(),
                  binding.StateEt.text.toString(),
                  userInfo!!.gender.toString()
              )

          }
              else
              context.toast(getString(R.string.reload_page_plz))
      }


    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            STORAGE_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED&&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    pickPhotoFromGallery()
                }
            }

        }
    }
    private fun isPermissionGranted(): Boolean {
        return (  ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED &&

                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED

                )



    }
    private fun requestPermeation() = ActivityCompat.requestPermissions(this,
        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE),
        STORAGE_REQUEST_CODE
    )
    private fun pickPhotoFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        if (intent.resolveActivity(context.packageManager) != null) {
            startActivityForResult(intent, GALLERY_REQUEST_CODE)
        }
    }

    private fun initSpinner() {
        val objectCityEnglish= arrayOf<String>(
            "TihamaQahtan",
            "Herica",
            "HoutaSudair",
            "Tanuma",
            "Dyer",
            "Riyadh",
            "Alkharag ",
            "Mecca",
            "Jeddah",
            "Taif",
            "Medina",
            "AdDammam",
            "Dhahran",
            "Khobar",
            "Hofuf",
            "Rama",
            "Onaiza",
            "Bakariya",
            "Tabuk",
            "Arar",
            "Abha",
            "KhamisMushait",
            "AlBahah",
            "Belgrishi",
            "Najran",
            "Jazan",
            "Hail",
            "AlJawf",
            "Buraydah",
            "Sbia",
            "Qatif",
            "Muzahmiyah",
            "Diriyah",
            "Ehsaa",
            "ELmoznb",
            "Badaa",
            "Shamsia",
            "Qunfudah",
            "Leith",
            "Rabigh",
            "ElJom",
            "Khulais",
            "AlKaml",
            "Khorama",
            "Rania",
            "Torba",
            "Yanbu",
            "Ola",
            "AlMahd",
            "Badr",
            "Khyber",
            "Dawadmi",
            "Majmaa",
            "Quwayia",
            "WadiAl-Dawasir",
            "Aflaj",
            "Zulfi",
            "Blonde",
            "HoutaBaniTamim",
            "Afif",
            "Alsalil",
            "Darmah",
            "Ramah",
            "Hanakiyah",
            "Thadec",
            "Harela",
            "ElRarik",
            "ElGhat",
            "HafrAl-Batin",
            "AlJubail",
            "Khafji",
            "RasTanura",
            "Abqaiq",
            "ElNairiya",
            "ElkaryElolia",
            "Skaka",
            "Qurays",
            "DomatAl-Jandal",
            "Bekaa",
            "Ghazala",
            "AbuArish",
            "Samtah",
            "ElHarth",
            "Dammad",
            "Al-Rayth",
            "Pich",
            "Fursan",
            "BaniMalik-Algeria",
            "ALMosarh",
            "Eidebi",
            "AlArida",
            "AlDarb",
            "Sharoura",
            "Hobona",
            "BadrSouth",
            "Yadma",
            "Thar",
            "Khabash",
            "Kharjir",
            "Almond",
            "AlMahwah",
            "AlAgate",
            "Kalwa",
            "AlCora",
            "Bisha",
            "AlNamas",
            "Mhael",
            "SarraObaida",
            "Tthlith",
            "RegalElma",
            "Rufaida",
            "SouthDhahran ",
            "Balqarn",
            "Almgarda",
            "Rafha",
            "Tarif",
            "Alwagh",
            "Duba",
            "Taima",
            "Amalge",
            "hakl",
            "Asiyah",
            "AlNaphaeya",
            "EyonElgwa",
            "RiyadhExpert",
            "AlRoboa",
            "Alhakw",
            "Adam",
            "AlQuoz",
            "Hall",
            "Maysan",
            "AlAyes",
            "Artawi",
            "Dulm",
            "AlHaet",
            "Tabarjal",
            "Darba",
            "AlForsha",
            "Qaysumah",
            "SabtAlOla",
            "AlFawara",
            "Shannan",
            "Ammar",
            "Asir",
            "Otay",
            "Nafy",
            "AlOrdiat",
            "WadyElFare",
            "Mok",
            "WadyEbnHASHBEL",
            "Mastora",
            "Shamli",
            "---END---"
        )
        val objectCityArabic = arrayOf<String>(
            "تهامة قحطان",
            "الحريضة",
            "حوطة سدير",
            "تنومة",
            "الداير",
            "الرياض",
            "الخرج",
            "مكة",
            "جدة",
            "الطائف",
            "المدينة المنورة",
            "الدمام",
            "الظهران",
            "الخبر",
            "الهفوف",
            "الرس",
            "عنيزة",
            "البكيرية",
            "تبوك",
            "عرعر",
            "أبها",
            "خميس مشيط",
            "الباحة",
            "بلجرشي",
            "نجران",
            "جازان",
            "حائل",
            "الجوف",
            "بريدة",
            "صبيا",
            "القطيف",
            "المزاحمية",
            "الدرعية",
            "الإحساء",
            "المذنب",
            "البدائع",
            "الشماسية",
            "القنفذة",
            "الليث",
            "رابغ",
            "الجموم",
            "خليص",
            "الكامل",
            "الخرمة",
            "رنية",
            "تربة",
            "ينبع",
            "العلا",
            "المهد",
            "بدر",
            "خيبر",
            "الدوادمي",
            "المجمعة",
            "القويعية",
            "وادي الدواسر",
            "الأفلاج",
            "الزلفي",
            "شقراء",
            "حوطة بني تميم",
            "عفيف",
            "السليل",
            "ضرماء",
            "رماح",
            "الحناكيه",
            "ثادق",
            "حريملاء",
            "الحريق",
            "الغاط",
            "حفر الباطن",
            "الجبيل",
            "الخفجي",
            "رأس تنورة",
            "بقيق",
            "النعيرية",
            "قريه العليا",
            "سكاكا",
            "القريات",
            "دومة الجندل",
            "بقعاء",
            "الغزالة",
            "أبو عريش",
            "صامطه",
            "الحرث",
            "ضمد",
            "الريث",
            "بيش",
            "فرسان",
            "بني مالك -الدائر",
            "أحد المسارحه",
            "العيدابي",
            "العارضه",
            "الدرب",
            "شرورة",
            "حبونا",
            "بدر الجنوب",
            "يدمه",
            "ثار",
            "خباش",
            "الخرخير",
            "المندق",
            "المخواة",
            "العقيق",
            "قلوه",
            "القرى",
            "بيشة",
            "النماص",
            "محائل",
            "سراة عبيدة",
            "تثليث",
            "رجال المع",
            "احد رفيدة",
            "ظهران الجنوب",
            "بلقرن",
            "المجاردة",
            "رفحاء",
            "طريف",
            "الوجه",
            "ضباء",
            "تيماء",
            "أملج",
            "حقل",
            "الأسياح",
            "النبهائيه",
            "عيون الجواء",
            "رياض الخبراء",
            "الربوعة",
            "الحقو",
            "أضم",
            "القوز",
            "حلي",
            "ميسان",
            "العيص",
            "الأرطاوية",
            "الدلم",
            "الحائط",
            "طبرجل",
            "ضرية",
            "الفرشة",
            "القيصومة",
            "سبت العلايا",
            "الفوارة",
            "الشنان",
            "حالة عمار",
            "عسير",
            "عطى",
            "نفي",
            "العرضيات",
            "وادي الفرع",
            "موق",
            "وادي بن هشبل",
            "مستورة",
            "الشملي",
            "---END---"
        );



        if(PrefManager.getLanguage()=="ar")
            objCity = objectCityArabic;

        else
            objCity = objectCityEnglish
      /*  val objects = ArrayList<String>()
        objects.add(getString(R.string.riyadh))
        objects.add(getString(R.string.jeddah))
        objects.add("---END---")*/

        val adapter = spinnerHelperAdapter(context,  objCity?.toList()!!, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.citySpinner.adapter = adapter

        // show hint
        binding.citySpinner.setSelection(adapter.getCount())

        binding.citySpinner.onItemSelectedListener = object :  AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // context.toast("pos "+position)
                if (position!=(objCity?.size!!-1))
                    binding.CityTv.text = objCity!![position]
            }

        }

        binding.CityTv.setOnClickListener {
            if (binding.citySpinner.selectedItemPosition==adapter.count)
                binding.citySpinner.setSelection(-1)
            binding.citySpinner.performClick()
        }
    }

    private fun performEdit( access_token : String, email: String, display_name: String,
                             phone : String, image: String, country: String,
                             city : String, state: String, gender: String) {

        viewModel.editProfile( access_token, email, display_name,
            phone, image, country,
            city, state, gender)
            .observe(this, Observer {


                it.apply {
                    when (this) {
                        is Resource.Progress -> {
                            if (loading)
                                dialog = context.showLoadingDialog()
                            else
                                dialog.dismiss()
                        }
                        is Resource.Success -> {
                            context.toast(""+data.message)
                        }
                        is Resource.Failure -> {
                            if (e is IOException) {
                                context.toast(getString(R.string.need_internet))
                            } else {
                                context.toast(getString(R.string.something_wrong))
                            }
                        }
                    }
                }
            })
    }

    private fun performUploadImage(access_token : String?, image: String?,bitmap: Bitmap){
        viewModel.uploadImage( access_token!!, image!!)
            .observe(this, Observer {


                it.apply {
                    when (this) {
                        is Resource.Progress -> {
                            if (loading)
                                dialog = context.showLoadingDialog()
                            else
                                dialog.dismiss()
                        }
                        is Resource.Success -> {

                           if(data.statusCode==0) {
                               binding.personPhoto.loadCircleImage(bitmap)
                               viewModel.userInfo?.image = data.data.toString()
                           }
                            context.toast(""+data.message)
                        }
                        is Resource.Failure -> {
                            if (e is IOException) {
                                context.toast(getString(R.string.need_internet))
                            } else if(e.message=="cancelled") {
                                context.toast(getString(R.string.was_canceled))
                            }else{
                                context.toast(getString(R.string.something_wrong))
                            }
                        }
                    }
                }
            })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        viewModel.cancelUpload()
    }

    private fun changeViewsFonts() {
        Util.changeViewTypeFace(this@EditProfileActivity,
            Constants.FONT_REGULAR,
            binding.toolbarTitle,
            binding.buttonSave)



    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {

        try {
            super.onActivityResult(requestCode, resultCode, intent)

            if (requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK && intent != null) {

                val realPath : String = getRealPathFromURI(intent.data!!)
                ImageCompression(this, realPath, object : ImageCompressionListener {
                    override fun onStart() {
                        //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onCompressed(filePath: String) {

                        val bitmap : Bitmap = BitmapFactory.decodeFile(filePath)
                      //  binding.personPhoto.loadCircleImage(bitmap)
                        val baseImageString = bitmap.toStringBase()

                        performUploadImage(userInfo?.accessToken,baseImageString,bitmap )

                    }

                } ).execute()


            }
        } catch (e: Exception) {

        }
    }
    fun getRealPathFromURI(contentUri: Uri) :String {
        val proj : Array<String> =Array(1) {MediaStore.Audio.Media.DATA}
        val  cursor : Cursor? = this.contentResolver.query(contentUri, proj, null, null, null)



        val columnIndex = cursor?.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
        cursor?.moveToFirst()
        return cursor?.getString(columnIndex!!)!!
    }
}
