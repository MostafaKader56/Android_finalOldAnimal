package com.technoship.animals.localizationSetter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
class Lang(val iconImg: Int, val stringResource: Int, val langSuffix: String, var id:Int, var checked: Boolean) : Parcelable {

    constructor(iconImg1: Int, stringResource1: Int, langSuffix1: String): this(iconImg1, stringResource1, langSuffix1, 0, false)

    companion object{
        fun manageIt(languages: ArrayList<Lang>) {
            for (i in 0 until languages.size) {
                languages[i].id = i + 1
            }
        }
    }
}