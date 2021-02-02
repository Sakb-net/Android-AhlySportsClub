package com.dev.alahlifc.al_ahlysportsclub.FootballTeam

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter


class TeamsFragmentAdapter(fragmentManager: FragmentManager): FragmentStatePagerAdapter(fragmentManager) {

    override fun getCount(): Int {
        return arrayList.size
    }

    private val arrayList : ArrayList<Fragment> = ArrayList()
    private val arrayListTitles : ArrayList<String> = ArrayList()

    override fun getItem(position: Int): Fragment {
        return arrayList[position]
    }

    fun addFragment(fragment: Fragment, title : String) {
        arrayList.add(fragment)
        arrayListTitles.add(title)
    }


    override fun getPageTitle( position : Int) : CharSequence? {
        if(position>=0){
            return  arrayListTitles[position]
        }

        else return null
    }

  /* override fun getItemCount(): Int {
        return arrayList.size
    }*/
}