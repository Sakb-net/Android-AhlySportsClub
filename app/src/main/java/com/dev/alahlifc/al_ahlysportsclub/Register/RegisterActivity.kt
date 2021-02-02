package com.dev.alahlifc.al_ahlysportsclub.Register

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.databinding.ActivityRegisterBinding
import timber.log.Timber
import java.io.IOException
import com.dev.alahlifc.al_ahlysportsclub.Base.*
import com.dev.alahlifc.al_ahlysportsclub.Main.HomeActivity
import com.dev.alahlifc.al_ahlysportsclub.store.PrefManager


class RegisterActivity : LocalizationActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel
    private lateinit var context : Context
    private lateinit var dialog: Dialog
    private var objCity : Array<String> ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        context = this
        viewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)
        changeViewsFonts()

        initSpinner()

        binding.loginAc.setOnClickListener {
           // startActivity(Intent(this , LoginActivity::class.java))
            finish()
        }


        binding.buttonRegister.setOnClickListener {
            //startActivity(Intent(this , HomeActivity::class.java))
           Timber.e("button register clicked")
          //  this.toast("clicked")


            val userName = binding.UserNameEt.text.toString()
            val phone = binding.PhoneEt.text.toString()
            val email = binding.EmailEt.text.toString()

            val city = binding.CityTv.text.toString()
            val state = binding.StateEt.text.toString()
            val password =  binding.PasswordEt.text.toString()

            val confirmPassword = binding.confirmPasswordEt.text.toString()

            Timber.e("val is $userName -- $phone-- $email -- $city -- $state -- $password -- $confirmPassword")

          if(validateRegisterFields(userName,phone,email,city,state,password,confirmPassword))
            performRegister(userName,phone,email,city,state,password,confirmPassword)

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

     /*   val objects = ArrayList<String>()
        objects.add(getString(R.string.riyadh))
        objects.add(getString(R.string.jeddah))
        objects.add("---END---")*/

        val adapter = spinnerHelperAdapter(context, objCity?.toList()!!, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.citySpinner.adapter = adapter

        // show hint
        binding.citySpinner.setSelection(adapter.getCount())


        binding.citySpinner.onItemSelectedListener = object :  AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?)  = Unit
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

    private fun performRegister(userName: String,
                                phone : String ,
                                email: String,
                                city : String,
                                state : String,
                                password: String,
                                confirmPassword : String) {
        viewModel.register(email = email, display_name = userName, password = password,
            country = "SA", city = city, state = state, reg_site = "android" ,phone = phone,fcm_token = "")

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

                            if(data.StatusCode==1){ // success

                                PrefManager.saveUser(data.data)

                                // to reGetTheToken
                                PrefManager.saveUserName_or_email(email)
                                PrefManager.savePassword(password)

                                startActivity(
                                    Intent(this@RegisterActivity, HomeActivity::class.java)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)

                                )
                              //  overridePendingTransition(R.anim.push_right_enter, R.anim.push_right_exit)


                            }

                            else{
                                context.toast(""+data.Message)
                            }

                          /**  if (data.requestType == 1){
                                context?.toast(""+data.message)
                                PrefManager?.saveUser(data)
                                PrefManager?.savePassword(password)

                                view?.findNavController()?.navigate(R.id.action_registerFragment_to_mainFragment)
                            }else{
                                context?.toast(""+data.message)

                            }

                            view?.isEnabled = true*/
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

    private fun changeViewsFonts() {
        Util.changeViewTypeFace(this@RegisterActivity, Constants.FONT_REGULAR,
            binding.textView2,
            binding.buttonRegister,
            binding.textView4,
            binding.loginAc
            )

    }

    private fun validateRegisterFields ( userName: String,
                                         phone : String ,
                                         email: String,
                                         city : String,
                                         state : String,
                                         password: String,
                                         confirmPassword : String) : Boolean {

        if(TextUtils.isEmpty(userName)) {
            binding.UserNameEt.error = getString(R.string.required_field)
            return false
        }
        if(TextUtils.isEmpty(phone)) {
            binding.PhoneEt.error = getString(R.string.required_field)
            return false
        }
        if(TextUtils.isEmpty(email)) {
            binding.EmailEt.error = getString(R.string.required_field)
            return false
        }
        if(TextUtils.isEmpty(city)) {
           context.toast(getString(R.string.select_city))
            return false
        }
        if(TextUtils.isEmpty(state)) {
            binding.StateEt.error = getString(R.string.required_field)
            return false
        }
        if(TextUtils.isEmpty(password)) {
            binding.PasswordEt.error = getString(R.string.required_field)
            return false
        }
        if(TextUtils.isEmpty(confirmPassword)) {
            binding.confirmPasswordEt.error = getString(R.string.required_field)
            return false
        }

        if(password != confirmPassword){
            context.toast(getString(R.string.pass_not_match))
            return false
        }

        else return true


    }

}
