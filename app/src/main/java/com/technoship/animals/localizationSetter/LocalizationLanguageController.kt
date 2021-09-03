package com.technoship.animals.localizationSetter

//import com.sun.org.apache.xerces.internal.dom.DOMMessageFormatter.setLocale

import android.app.Activity
import android.app.AlertDialog
import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Build
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.core.os.ConfigurationCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.technoship.animals.R
import java.util.*
import kotlin.collections.ArrayList


//import javax.swing.JColorChooser.createDialog


class LocalizationLanguageController(
    context: Activity, view: View, var onLocalizationChanged: OnLocalizationChanged?,
) {
    private lateinit var dialog: AlertDialog
//    private var editor: SharedPreferences.Editor? = null
    private var languages: ArrayList<Lang>? = null
    private val sp: SharedPreferences = context.getSharedPreferences("your_prefs", Activity.MODE_PRIVATE)
    private var adapter: LanguageAdapter? = null
    private var currentLangId = 0
//    private val imageButton: ImageButton? = null

    lateinit var selectLangTextView: TextView
    lateinit var systemButton: Button
    lateinit var cancelButton: Button
    lateinit var applyButton: Button
    lateinit var currentLang: Lang

    init {
        doIt(context)
//        textView!!.text = context.getString(R.string.LocalizationLanguageController_language)
        view.setOnClickListener { showDialog(context) }
    }

//    constructor(context1: Activity): this(context1, null,null){
//        doIt(context1)
//    }

//    private fun doContextWrapper() {
//        val context2: Context =
//            MyContextWrapper.wrap(context /*in fragment use getContext() instead of this*/,
//                currentLang!!.langSuffix)
//        context.getResources().updateConfiguration(context1.getResources().getConfiguration(),
//            context1.getResources().getDisplayMetrics())
//    }

    fun startDoing(context2: Activity) {
        doIt(context2)
    }


//    private fun showDialog() {
//
//        selectLangTextView.setText(context.getResources().getString(R.string.LocalizationLanguageController_select_language));
//        systemButton.setText(context.getResources().getString(R.string.LocalizationLanguageController_system));
//        applyButton.setText(context.getResources().getString(R.string.LocalizationLanguageController_apply));
//        cancelButton.setText(context.getResources().getString(R.string.LocalizationLanguageController_cancel));
//
//        dialog.show();
//    }

    private fun doIt(context: Activity) {
        val isFirstTime = sp.getBoolean("it_is_first_open", true)

        createdLanguagesArrayList()
        if (isFirstTime) {
            isFirstTime()
        }

        currentLangId = sp.getInt("applied_lang_id", 1)
        val currentLangSuffix = sp.getString("applied_lang_suffix", "en")

        setLocale(currentLangSuffix!!)

        setCurrentLanguageInAdapterArrayList(currentLangId - 1)
        createDialog(context)
    }

    private fun createdLanguagesArrayList() {
        languages = ArrayList()
        languages!!.add(Lang(R.drawable.gb, R.string.LocalizationLanguageController_gb, "en"))
        languages!!.add(Lang(R.drawable.ru, R.string.LocalizationLanguageController_ru, "ru"))
        languages!!.add(Lang(R.drawable.zh, R.string.LocalizationLanguageController_zh, "zh"))
        languages!!.add(Lang(R.drawable.ae, R.string.LocalizationLanguageController_ar, "ar"))
        Lang.manageIt(languages!!)

//        languages.add(new Lang(R.drawable.br, R.string.br, 8, "br"));
//        languages.add(new Lang(R.drawable.cn, R.string.cn, 9, "cn"));
//        languages.add(new Lang(R.drawable.cz, R.string.cz, 10, "cz"));
//        languages.add(new Lang(R.drawable.dk, R.string.dk, 11, "dk"));
//        languages.add(new Lang(R.drawable.el, R.string.el, 12, "el"));
//        languages.add(new Lang(R.drawable.fi, R.string.fi, 13, "fi"));
//        languages.add(new Lang(R.drawable.fr, R.string.fr, 14, "fr"));
//        languages.add(new Lang(R.drawable.hr, R.string.hr, 15, "hr"));
//        languages.add(new Lang(R.drawable.hu, R.string.hu, 16, "hu"));
//        languages.add(new Lang(R.drawable.id, R.string.id, 17, "id"));
//        languages.add(new Lang(R.drawable.in, R.string.in, 18, "in"));
//        languages.add(new Lang(R.drawable.ir, R.string.ir, 19, "ir"));
//        languages.add(new Lang(R.drawable.it, R.string.it, 20, "it"));
//        languages.add(new Lang(R.drawable.jp, R.string.jp, 21, "jp"));
//        languages.add(new Lang(R.drawable.ks, R.string.ks, 22, "ks"));
//        languages.add(new Lang(R.drawable.ms, R.string.ms, 23, "ms"));
//        languages.add(new Lang(R.drawable.nl, R.string.nl, 24, "nl"));
//        languages.add(new Lang(R.drawable.no, R.string.no, 25, "no"));
//        languages.add(new Lang(R.drawable.ph, R.string.ph, 26, "ph"));
//        languages.add(new Lang(R.drawable.pl, R.string.pl, 27, "pl"));
//        languages.add(new Lang(R.drawable.ro, R.string.ro, 28, "ro"));
//        languages.add(new Lang(R.drawable.rs, R.string.rs, 29, "rs"));
//        languages.add(new Lang(R.drawable.ru, R.string.ru, 30, "ru"));
//        languages.add(new Lang(R.drawable.se, R.string.se, 31, "se"));
//        languages.add(new Lang(R.drawable.sk, R.string.sk, 32, "sk"));
//        languages.add(new Lang(R.drawable.sl, R.string.sl, 33, "sl"));
//        languages.add(new Lang(R.drawable.th, R.string.th, 34, "th"));
//        languages.add(new Lang(R.drawable.ua, R.string.ua, 35, "ua"));
//        languages.add(new Lang(R.drawable.vn, R.string.vn, 36, "vn"));
//        languages.add(new Lang(R.drawable.il, R.string.il, 37, "il"));
    }

    private fun isFirstTime() {
        val editor = sp.edit()
        editor.putBoolean("it_is_first_open", false)
        editor.apply()
        val currentSuffix: String = ConfigurationCompat.getLocales(Resources.getSystem().configuration).get(0).language
        for (lang in languages!!) {
            if (lang.langSuffix == currentSuffix) {
                editor.putString("applied_lang_suffix", lang.langSuffix)
                editor.putInt("applied_lang_id", lang.id)
                editor.putString("system_lang_suffix", lang.langSuffix)
                editor.putInt("system_lang_id", lang.id)
                editor.apply()
            }
        }
    }

    private fun setCurrentLanguageInAdapterArrayList(id: Int) {
        for (lang in languages!!) {
            if (lang.id == id) {
                lang.checked = true
            }
        }
        currentLangId = id
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private fun getAndroidDefaultLocal(): String? {
        return Resources.getSystem().configuration.locales.get(0).language
    }

    private fun createDialog(context: Activity) {
        val dialogBuilder = AlertDialog.Builder(context)
        @NonNull
        val view:View = View.inflate(context, R.layout.select_language_pop, null)

//        val view: SelectLangPopBinding =
//            LayoutInflater.inflate(R.layout.language_item, false)
        applyButton = view.findViewById(R.id.okButton)
        systemButton = view.findViewById(R.id.systemLangButton)
        cancelButton = view.findViewById(R.id.cancelButton)
        selectLangTextView = view.findViewById(R.id.title)
        applyButton.setOnClickListener {
            if (!adapter!!.isThereLangChange) {
                dialog.dismiss()
                return@setOnClickListener
            } else if (!adapter!!.thereIsLangSelected()) {
                return@setOnClickListener
            }
            val s = adapter!!.selectedLang
            setLocale(s.langSuffix)
            saveLangPreferences(s)
            dialog.dismiss()
            languages = adapter!!.languages
            adapter!!.notifyDataSetChanged()
        }
        cancelButton.setOnClickListener { dialog.dismiss() }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) systemButton.setOnClickListener {
            val suffix = getAndroidDefaultLocal()
            for (lang in languages!!) {
                if (lang.langSuffix == suffix) {
                    saveLangPreferences(lang)
                    adapter!!.setSelected(lang.id - 1)
                }
            }
            adapter!!.notifyDataSetChanged()
            if (suffix != null) {
                setLocale(suffix)
            }
            dialog.dismiss()
        } else systemButton.visibility = View.INVISIBLE
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = LanguageAdapter(languages!!, currentLangId)
        recyclerView.adapter = adapter
        dialogBuilder.setView(view)
        dialog = dialogBuilder.create()
//        dialog.setOnDismissListener({ dialogInterface: DialogInterface? -> refreshTextView() })
    }

    private fun saveLangPreferences(lang: Lang) {
        val editor = sp.edit()
        editor.putString("applied_lang_suffix", lang.langSuffix)
        editor.putInt("applied_lang_id", lang.id)
        editor.apply()
        currentLang = lang
//        imageButton.setImageResource(currentLang.iconImg)
//        doContextWrapper();
    }

//    private fun refreshTextView(context: Activity) {
//        textView.setText(context.resources
//            .getText(R.string.LocalizationLanguageController_language))
//    }

    private fun showDialog(context: Activity) {
        selectLangTextView.text = context.resources
            .getString(R.string.LocalizationLanguageController_select_language)
        systemButton.text = context.resources
            .getString(R.string.LocalizationLanguageController_system)
        applyButton.text = context.resources
            .getString(R.string.LocalizationLanguageController_apply)
        cancelButton.text = context.resources
            .getString(R.string.LocalizationLanguageController_cancel)
        dialog.show()
    }

    //    public static void setLocale(Activity activity, String languageCode) {
//        Locale locale = new Locale(languageCode);
//        Locale.setDefault(locale);
//        Resources resources = activity.getResources();
//        Configuration config = resources.getConfiguration();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            config.setLocale(locale);
//        }
//        resources.updateConfiguration(config, resources.getDisplayMetrics());
//    }

    //    public static void setLocale(Activity activity, String languageCode) {
    //        Locale locale = new Locale(languageCode);
    //        Locale.setDefault(locale);
    //        Resources resources = activity.getResources();
    //        Configuration config = resources.getConfiguration();
    //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
    //            config.setLocale(locale);
    //        }
    //        resources.updateConfiguration(config, resources.getDisplayMetrics());
    //    }
    private fun setLanguage(language: String) {
        onLocalizationChanged!!.onChanged(language)

//        Locale locale = new Locale(language);
//        Configuration configuration = context.getResources().getConfiguration();
//        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
//        Locale.setDefault(locale);
//        configuration.locale = locale;
//        context.getResources().updateConfiguration(configuration, displayMetrics);
    }

    private fun setLocale(suffix: String) {
        setLanguage(suffix)

//        Locale locale = new Locale(suff);
//        Locale.setDefault(locale);
//        Configuration config = new Configuration();
//        config.locale = locale;
//        context.getBaseContext().getResources().updateConfiguration(config,
//                context.getBaseContext().getResources().getDisplayMetrics());
//        Resources resources = context.getResources();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            config.setLocale(locale);
//        }
    }

//    public static void setLocale(Activity context) {
//        Locale locale = new Locale(currentLang.getLangSuffix());
//        Locale.setDefault(locale);
//        Configuration config = new Configuration();
//        config.locale = locale;
//        context.getBaseContext().getResources().updateConfiguration(config,
//                context.getBaseContext().getResources().getDisplayMetrics());
//        Resources resources = context.getResources();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            config.setLocale(locale);
//        }
//    }

    interface OnLocalizationChanged {
        fun onChanged(language: String?)
    }
}