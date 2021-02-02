package com.dev.alahlifc.al_ahlysportsclub.FootballTeam


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.dev.alahlifc.al_ahlysportsclub.Base.Constants
import com.dev.alahlifc.al_ahlysportsclub.Base.Util
import com.dev.alahlifc.al_ahlysportsclub.Base.spinnerHelperAdapter
import com.dev.alahlifc.al_ahlysportsclub.FootballTeam.coach.CoachFragment
import com.dev.alahlifc.al_ahlysportsclub.FootballTeam.player.PlayersViewModel
import com.dev.alahlifc.al_ahlysportsclub.FootballTeam.player.PlayersFragment
import com.dev.alahlifc.al_ahlysportsclub.Main.HomeActivity

import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.databinding.FragmentFootballTeamBinding
import com.dev.alahlifc.al_ahlysportsclub.models.mTeams
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_home.*
import org.greenrobot.eventbus.EventBus

class FootballTeamFragment : Fragment() {

    private lateinit var binding : FragmentFootballTeamBinding
    private lateinit var viewModel: PlayersViewModel
    private lateinit var matchesSliderAdapter: TeamsFragmentAdapter
    private var title : String ?=""
    private var link : String ?=""
    private var obj : mTeams.Data ? = null
    private var byClick : Boolean  = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = arguments?.getString("title")
        link = arguments?.getString("link")
        obj = arguments?.getParcelable("obj")
    }
  //  private lateinit var adapter: PlayersAdapter
  //  private lateinit var dialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_football_team, container, false)
        binding  = DataBindingUtil.inflate(
            inflater, R.layout.fragment_football_team, container, false)
        changeViewsFonts()
        return binding.root
    }


    private fun changeViewsFonts() {

        Util.changeViewTypeFace(context, Constants.FONT_REGULAR,  binding.categoryPlayer)



    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PlayersViewModel::class.java)


        if (title!=null)
        (activity as HomeActivity).toolbarTitle.text = title

       initSpinner()
      //  getObservableData()



        matchesSliderAdapter = TeamsFragmentAdapter(childFragmentManager!!)


        matchesSliderAdapter.addFragment(PlayersFragment()
            .apply {
            val bundle = Bundle().apply {
                // putInt("gallery_id", post.id)
                putString("link",link)
            }
                arguments = bundle}


            ,getString(R.string.players))
        matchesSliderAdapter.addFragment(CoachFragment()
            .apply {
                val bundle = Bundle().apply {
                    // putInt("gallery_id", post.id)
                    putString("link",link)
                }
                arguments = bundle}



            ,getString(R.string.coash))


        if (binding.viewpagerSlider.adapter == null)
            binding.viewpagerSlider.adapter =matchesSliderAdapter


        binding.tabs.setupWithViewPager(binding.viewpagerSlider, true)

        binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) = Unit
            override fun onTabUnselected(tab: TabLayout.Tab?) = Unit
            override fun onTabSelected( tab : TabLayout.Tab) = changeTabsFonts()
        });



    }

    override fun onResume() {
        super.onResume()
        changeTabsFonts()
    }

    private fun changeTabsFonts() {
        Util.changeTabsFont(context!!, binding.tabs)
    }

    private fun initSpinner() {
        val objectCityEnglish : MutableList<String> = mutableListOf()

        obj?.subteams?.map {
            objectCityEnglish.add( it.name)
        }
        objectCityEnglish.add( "---end---")

       // binding.categoryPlayer.hint=objectCityEnglish[0]


          /*  arrayOf<String>(
                "TihamaQahtan",
                "TihamaQahtan2",
                "---end---")*/

            val adapter = spinnerHelperAdapter(context!!, objectCityEnglish?.toList()!!, android.R.layout.simple_spinner_item)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            binding.spinner.adapter = adapter

            // show hint
        if (viewModel.selection==-1) {
            viewModel.selection = adapter.count
            binding.spinner.setSelection(viewModel.selection)
        }




            binding.spinner.onItemSelectedListener = object :  AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?)  = Unit
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                //   context?.toast("pos "+position)
                  //  binding.categoryPlayer.hint=objectCityEnglish[position]
                    viewModel.selection = position
                    if (position!=(objectCityEnglish?.size!!-1) ) {

                        binding.categoryPlayer.text = objectCityEnglish[position]
                        if (byClick){
                            EventBus.getDefault().post(obj?.subteams?.get(position)?.link)
                            byClick = false
                        }



                    }
                    else
                        binding.categoryPlayer.hint=objectCityEnglish[0]
                }

            }







        binding.headerll.setOnClickListener {
            //  if (binding.spinner.selectedItemPosition==adapter.count)
            //    binding.spinner.setSelection(-1)

            byClick = true
            binding.spinner.performClick()
        }

    }





}
