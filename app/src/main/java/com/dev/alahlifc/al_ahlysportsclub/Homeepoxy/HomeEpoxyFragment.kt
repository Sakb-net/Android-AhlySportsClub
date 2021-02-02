package com.dev.alahlifc.al_ahlysportsclub.Homeepoxy


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.epoxy.EpoxyRecyclerView
import com.airbnb.epoxy.EpoxyVisibilityTracker
import com.dev.alahlifc.al_ahlysportsclub.Homeepoxy.models.CarouselItemCustomViewModel_
import com.dev.alahlifc.al_ahlysportsclub.Homeepoxy.view.carouselNoSnap

import com.dev.alahlifc.al_ahlysportsclub.R



class HomeEpoxyFragment : Fragment() {

    private lateinit var recyclerView: EpoxyRecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_epoxy, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recyclerView = view!!.findViewById(R.id.recycler_view)

        // Attach the visibility tracker to the RecyclerView. This will enable visibility events.
        val epoxyVisibilityTracker = EpoxyVisibilityTracker()
        epoxyVisibilityTracker.attach(recyclerView)

        recyclerView.withModels {

        //    for (i in 0 until 100) {
//                dataBindingItem {
//                    id("data binding $i")
//                    text("this is a data binding model")
//                    onClick { _ ->
//                        Toast.makeText(this@MainActivity, "clicked", Toast.LENGTH_LONG).show()
//                    }
//                    onVisibilityStateChanged { model, view, visibilityState ->
//                        Log.d(TAG, "$model -> $visibilityState")
//                    }
//                }

           /*     itemCustomView {
                    id("custom view $i")
                    color(Color.GREEN)
                    title("this is a green custom view item")
                    listener { _ ->
                        Toast.makeText(this@MainActivity, "clicked", Toast.LENGTH_LONG).show()
                    }
                }*/

//                itemEpoxyHolder {
//                    id("view holder $i")
//                    title("this is a View Holder item")
//                    listener {
//                        Toast.makeText(this@MainActivity, "clicked", Toast.LENGTH_LONG)
//                            .show()
//                    }
//                }

            carouselNoSnap {
                id("carousel $0")
                models(mutableListOf<CarouselItemCustomViewModel_>().apply {
                    val lastPage = 10
                    for (j in 0 until lastPage) {
                        add(
                            CarouselItemCustomViewModel_()
                                .id("carousel $0-$j")
                                .title("tit ")
                        )
                    }
                })
            }

            carouselNoSnap {
                id("carousel $0")
                models(mutableListOf<CarouselItemCustomViewModel_>().apply {
                    val lastPage = 10
                    for (j in 0 until lastPage) {
                        add(
                            CarouselItemCustomViewModel_()
                                .id("carousel $0-$j")
                                .title("Page $j / $lastPage")
                        )
                    }
                })
            }

            carouselNoSnap {
                id("carousel $0")
                models(mutableListOf<CarouselItemCustomViewModel_>().apply {
                    val lastPage = 10
                    for (j in 0 until lastPage) {
                        add(
                            CarouselItemCustomViewModel_()
                                .id("carousel $0-$j")
                                .title("Page $j / $lastPage")
                        )
                    }
                })
            }

            carouselNoSnap {
                id("carousel $0")
                models(mutableListOf<CarouselItemCustomViewModel_>().apply {
                    val lastPage = 10
                    for (j in 0 until lastPage) {
                        add(
                            CarouselItemCustomViewModel_()
                                .id("carousel $0-$j")
                                .title("Page $j / $lastPage")
                        )
                    }
                })
            }

           /*    carouselNoSnap {
                    id("carousel $0")
                    models(mutableListOf<CarouselItemCustomViewModel_>().apply {
                        val lastPage = 10
                        for (j in 0 until lastPage) {
                            add(
                                CarouselItemCustomViewModel_()
                                    .id("carousel $0-$j")
                                    .title("Page $j / $lastPage")
                            )
                        }
                    })
                }*/
/*
                // Since data classes do not use code generation, there's no extension generated here
                ItemDataClass("this is a Data Class Item")
                    .id("data class $i")
                    .addTo(this)*/
            }
       // }
    }

    companion object {
        private const val TAG = "MainActivity"
    }

}
