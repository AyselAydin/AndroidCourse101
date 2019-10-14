package com.koducation.androidcourse101.ui.radios

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.koducation.androidcourse101.data.RadioDataSource
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RadiosFragmentViewModel : ViewModel() {

    private val radioDataSource = RadioDataSource()

    private val radiosLiveData: MutableLiveData<RadiosFragmentViewState> = MutableLiveData()

    init {
        loadRadiosPage()
    }

    fun getRadiosLiveData(): LiveData<RadiosFragmentViewState> = radiosLiveData

    @SuppressLint("CheckResult")
    fun loadRadiosPage() {
        Observable
            .combineLatest(
                radioDataSource.fetchPopularRadios(),
                radioDataSource.fetchLocationRadios(),
                RadiosPageCombiner()
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { radiosLiveData.value = it }
    }

}