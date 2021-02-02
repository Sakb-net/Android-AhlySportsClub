package com.dev.alahlifc.al_ahlysportsclub.MatchDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.databinding.FragmentArchiveBinding
import com.dev.alahlifc.al_ahlysportsclub.models.mMatches

class ArchiveFragment : Fragment() {

    private lateinit var binding : FragmentArchiveBinding
    private var  obj: mMatches.Data? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding  = DataBindingUtil.inflate(
            inflater, R.layout.fragment_archive, container, false)

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         obj= arguments!!.getParcelable("obj")

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.firstTeam.text = obj?.firstTeam
        binding.secondTeam.text = obj?.secondTeam

        binding.t1Goal.text = ""+obj?.firstGoal
        binding.t2Goal.text = ""+obj?.secondGoal

        binding.t1Corner.text = ""+obj?.strikes1
        binding.t2Corner.text = ""+obj?.strikes2

        binding.t1Offside.text = ""+obj?.offside1
        binding.t2Offside.text = ""+obj?.offside2

        binding.t1Yellow.text = ""+obj?.cartYellow1
        binding.t2Yellow.text = ""+obj?.cartYellow2

        binding.t1Red.text = ""+obj?.cartRed1
        binding.t2Red.text = ""+obj?.cartRed2

        binding.t1Shoot.text = ""+obj?.payingGoal1
        binding.t2Shoot.text = ""+obj?.payingGoal2

        binding.t1Passes.text = ""+obj?.passes1
        binding.t2Passes.text = ""+obj?.passes2

    }
}
