package com.dev.alahlifc.al_ahlysportsclub.Base

/*
 * Copyright 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



import androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP
import androidx.viewpager2.widget.ViewPager2.SCROLL_STATE_DRAGGING
import androidx.viewpager2.widget.ViewPager2.SCROLL_STATE_IDLE
import androidx.viewpager2.widget.ViewPager2.SCROLL_STATE_SETTLING
import androidx.annotation.RestrictTo
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

import com.google.android.material.tabs.TabLayout

import java.lang.ref.WeakReference
import java.lang.reflect.Method

/**
 * A mediator to link a TabLayout with a ViewPager2. The mediator will synchronize the ViewPager2's
 * position with the selected tab when a tab is selected, and the TabLayout's scroll position when
 * the user drags the ViewPager2.
 *
 * Establish the link by creating an instance of this class, make sure the ViewPager2 has an adapter
 * and then call [.attach] on it. When creating an instance of this class, you must supply
 * an implementation of [OnConfigureTabCallback] in which you set the text of the tab, and/or
 * perform any styling of the tabs that you require.
 *
 * @hide
 */
@RestrictTo(LIBRARY_GROUP)
class TabLayoutMediator
/**
 * Creates a TabLayoutMediator to synchronize a TabLayout and a ViewPager2 together. If `autoRefresh` is true, it will update the tabs automatically when the data set of the view
 * pager's adapter changes. The link will be established after [.attach] is called.
 *
 * @param tabLayout The tab bar to link
 * @param viewPager The view pager to link
 * @param autoRefresh If `true`, will recreate all tabs when the data set of the view
 * pager's adapter changes.
 */
    (
    private val mTabLayout: TabLayout, private val mViewPager: ViewPager2,
    private val mAutoRefresh: Boolean, private val mOnConfigureTabCallback: OnConfigureTabCallback
) {
    private var mAdapter: RecyclerView.Adapter<*>? = null
    private var mAttached: Boolean = false

    private var mOnPageChangeCallback: TabLayoutOnPageChangeCallback? = null
    private var mOnTabSelectedListener: TabLayout.OnTabSelectedListener? = null
    private var mPagerAdapterObserver: RecyclerView.AdapterDataObserver? = null

    /**
     * A callback interface that must be implemented to set the text and styling of newly created
     * tabs.
     */
    interface OnConfigureTabCallback {
        /**
         * Called to configure the tab for the page at the specified position. Typically calls
         * [TabLayout.Tab.setText], but any form of styling can be applied.
         *
         * @param tab The Tab which should be configured to represent the title of the item at the
         * given position in the data set.
         * @param position The position of the item within the adapter's data set.
         */
        fun onConfigureTab(tab: TabLayout.Tab, position: Int)
    }

    /**
     * Creates a TabLayoutMediator to synchronize a TabLayout and a ViewPager2 together. It will
     * update the tabs automatically when the data set of the view pager's adapter changes. The link
     * will be established after [.attach] is called.
     *
     * @param tabLayout The tab bar to link
     * @param viewPager The view pager to link
     */
    constructor(
        tabLayout: TabLayout, viewPager: ViewPager2,
        onConfigureTabCallback: OnConfigureTabCallback
    ) : this(tabLayout, viewPager, true, onConfigureTabCallback) {
    }

    /**
     * Link the TabLayout and the ViewPager2 together.
     * @throws IllegalStateException If the mediator is already attached, or the ViewPager2 has no
     * adapter.
     */
    fun attach() {
        if (mAttached) {
            throw IllegalStateException("TabLayoutMediator is already attached")
        }
        mAdapter = mViewPager.adapter
        if (mAdapter == null) {
            throw IllegalStateException("TabLayoutMediator attached before ViewPager2 has an " + "adapter")
        }
        mAttached = true

        // Add our custom OnPageChangeCallback to the ViewPager
        mOnPageChangeCallback = TabLayoutOnPageChangeCallback(mTabLayout)
        mViewPager.registerOnPageChangeCallback(mOnPageChangeCallback!!)

        // Now we'll add a tab selected listener to set ViewPager's current item
        mOnTabSelectedListener = ViewPagerOnTabSelectedListener(mViewPager)
        mTabLayout.addOnTabSelectedListener(mOnTabSelectedListener!!)

        // Now we'll populate ourselves from the pager adapter, adding an observer if
        // autoRefresh is enabled
        if (mAutoRefresh) {
            // Register our observer on the new adapter
            mPagerAdapterObserver = PagerAdapterObserver()
            mAdapter!!.registerAdapterDataObserver(mPagerAdapterObserver!!)
        }

        populateTabsFromPagerAdapter()

        // Now update the scroll position to match the ViewPager's current item
        mTabLayout.setScrollPosition(mViewPager.currentItem, 0f, true)
    }

    /**
     * Unlink the TabLayout and the ViewPager
     */
    fun detach() {
        mAdapter!!.unregisterAdapterDataObserver(mPagerAdapterObserver!!)
        mTabLayout.removeOnTabSelectedListener(mOnTabSelectedListener!!)
        mViewPager.unregisterOnPageChangeCallback(mOnPageChangeCallback!!)
        mPagerAdapterObserver = null
        mOnTabSelectedListener = null
        mOnPageChangeCallback = null
        mAttached = false
    }

    internal fun populateTabsFromPagerAdapter() {
        mTabLayout.removeAllTabs()

        if (mAdapter != null) {
            val adapterCount = mAdapter!!.itemCount
            for (i in 0 until adapterCount) {
                val tab = mTabLayout.newTab()
                mOnConfigureTabCallback.onConfigureTab(tab, i)
                mTabLayout.addTab(tab, false)
            }

            // Make sure we reflect the currently set ViewPager item
            if (adapterCount > 0) {
                val currItem = mViewPager.currentItem
                if (currItem != mTabLayout.selectedTabPosition) {
                    mTabLayout.getTabAt(currItem)!!.select()
                }
            }
        }
    }

    /**
     * A [ViewPager2.OnPageChangeCallback] class which contains the necessary calls back to
     * the provided [TabLayout] so that the tab position is kept in sync.
     *
     *
     * This class stores the provided TabLayout weakly, meaning that you can use [ ][ViewPager2.registerOnPageChangeCallback] without removing
     * the callback and not cause a leak.
     */
    private class TabLayoutOnPageChangeCallback internal constructor(tabLayout: TabLayout) :
        ViewPager2.OnPageChangeCallback() {
        private val mTabLayoutRef: WeakReference<TabLayout>
        private var mPreviousScrollState: Int = 0
        private var mScrollState: Int = 0

        init {
            mTabLayoutRef = WeakReference(tabLayout)
            reset()
        }

        override fun onPageScrollStateChanged(state: Int) {
            mPreviousScrollState = mScrollState
            mScrollState = state
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            val tabLayout = mTabLayoutRef.get()
            if (tabLayout != null) {
                // Only update the text selection if we're not settling, or we are settling after
                // being dragged
                val updateText = mScrollState != SCROLL_STATE_SETTLING || mPreviousScrollState == SCROLL_STATE_DRAGGING
                // Update the indicator if we're not settling after being idle. This is caused
                // from a setCurrentItem() call and will be handled by an animation from
                // onPageSelected() instead.
                val updateIndicator =
                    !(mScrollState == SCROLL_STATE_SETTLING && mPreviousScrollState == SCROLL_STATE_IDLE)
                setScrollPosition(tabLayout, position, positionOffset, updateText, updateIndicator)
            }
        }

        override fun onPageSelected(position: Int) {
            val tabLayout = mTabLayoutRef.get()
            if (tabLayout != null
                && tabLayout.selectedTabPosition != position
                && position < tabLayout.tabCount
            ) {
                // Select the tab, only updating the indicator if we're not being dragged/settled
                // (since onPageScrolled will handle that).
                val updateIndicator =
                    mScrollState == SCROLL_STATE_IDLE || mScrollState == SCROLL_STATE_SETTLING && mPreviousScrollState == SCROLL_STATE_IDLE
                selectTab(tabLayout, tabLayout.getTabAt(position), updateIndicator)
            }
        }

        internal fun reset() {
            mScrollState = SCROLL_STATE_IDLE
            mPreviousScrollState = mScrollState
        }
    }

    // endregion

    /**
     * A [TabLayout.OnTabSelectedListener] class which contains the necessary calls back to
     * the provided [ViewPager2] so that the tab position is kept in sync.
     */
    private class ViewPagerOnTabSelectedListener internal constructor(private val mViewPager: ViewPager2) :
        TabLayout.OnTabSelectedListener {

        override fun onTabSelected(tab: TabLayout.Tab) {
            mViewPager.setCurrentItem(tab.position, true)
        }

        override fun onTabUnselected(tab: TabLayout.Tab) {
            // No-op
        }

        override fun onTabReselected(tab: TabLayout.Tab) {
            // No-op
        }
    }

    private inner class PagerAdapterObserver internal constructor() : RecyclerView.AdapterDataObserver() {

        override fun onChanged() {
            populateTabsFromPagerAdapter()
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            populateTabsFromPagerAdapter()
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
            populateTabsFromPagerAdapter()
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            populateTabsFromPagerAdapter()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            populateTabsFromPagerAdapter()
        }

        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
            populateTabsFromPagerAdapter()
        }
    }

    companion object {

        // region Reflective calls

        // Temporarily call methods TabLayout.setScrollPosition(int, float, boolean, boolean) and
        // TabLayout.selectTab(TabLayout.Tab, boolean) through reflection, until they have been made
        // public in the Material Design Components library.

        private var sSetScrollPosition: Method? = null
        private var sSelectTab: Method? = null
        private val SET_SCROLL_POSITION_NAME = "TabLayout.setScrollPosition(int, float," + " boolean, boolean)"
        private val SELECT_TAB_NAME = "TabLayout.selectTab(TabLayout.Tab, boolean)"

        init {
            try {
                sSetScrollPosition = TabLayout::class.java.getDeclaredMethod(
                    "setScrollPosition", Int::class.javaPrimitiveType,
                    Float::class.javaPrimitiveType, Boolean::class.javaPrimitiveType, Boolean::class.javaPrimitiveType
                )
                sSetScrollPosition!!.isAccessible = true

                sSelectTab = TabLayout::class.java.getDeclaredMethod(
                    "selectTab", TabLayout.Tab::class.java,
                    Boolean::class.javaPrimitiveType
                )
                sSelectTab!!.isAccessible = true
            } catch (e: NoSuchMethodException) {
                throw IllegalStateException("Can't reflect into method TabLayout" + ".setScrollPosition(int, float, boolean, boolean)")
            }

        }

        internal fun setScrollPosition(
            tabLayout: TabLayout, position: Int, positionOffset: Float,
            updateSelectedText: Boolean, updateIndicatorPosition: Boolean
        ) {
            try {
                if (sSetScrollPosition != null) {
                    sSetScrollPosition!!.invoke(
                        tabLayout, position, positionOffset, updateSelectedText,
                        updateIndicatorPosition
                    )
                } else {
                    throwMethodNotFound(SET_SCROLL_POSITION_NAME)
                }
            } catch (e: Exception) {
                throwInvokeFailed(SET_SCROLL_POSITION_NAME)
            }

        }

        internal fun selectTab(tabLayout: TabLayout, tab: TabLayout.Tab?, updateIndicator: Boolean) {
            try {
                if (sSelectTab != null) {
                    sSelectTab!!.invoke(tabLayout, tab, updateIndicator)
                } else {
                    throwMethodNotFound(SELECT_TAB_NAME)
                }
            } catch (e: Exception) {
                throwInvokeFailed(SELECT_TAB_NAME)
            }

        }

        private fun throwMethodNotFound(method: String) {
            throw IllegalStateException("Method $method not found")
        }

        private fun throwInvokeFailed(method: String) {
            throw IllegalStateException("Couldn't invoke method $method")
        }
    }
}