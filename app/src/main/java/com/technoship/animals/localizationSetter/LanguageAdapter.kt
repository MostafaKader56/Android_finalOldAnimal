package com.technoship.animals.localizationSetter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.technoship.animals.R


class LanguageAdapter(val languages: ArrayList<Lang>, private var lastSelectedIndex: Int) :
    RecyclerView.Adapter<LanguageAdapter.LanguageViewHolder>() {
    private var onStartSelectedIndex: Int

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): LanguageViewHolder {
        return LanguageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.language_item, parent, false))
    }

    override fun onBindViewHolder(@NonNull holder: LanguageViewHolder, position: Int) {
        holder.langRadioButton.isChecked = position == lastSelectedIndex
        holder.langRadioButton.setText(languages[position].stringResource)
        holder.langImageView.setImageResource(languages[position].iconImg)
    }

    override fun getItemCount(): Int {
        return languages.size
    }

    inner class LanguageViewHolder(@NonNull itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val langRadioButton: RadioButton = itemView.findViewById(R.id.langRadioButton)
        val langImageView: ImageView = itemView.findViewById(R.id.langImageView)

        init {
            itemView.setOnClickListener { setSelected(adapterPosition) }
        }
    }

    fun setSelected(index: Int) {
        onStartSelectedIndex = lastSelectedIndex
        lastSelectedIndex = index
        notifyItemChanged(onStartSelectedIndex)
        notifyItemChanged(lastSelectedIndex)
    }

    fun thereIsLangSelected(): Boolean {
        return lastSelectedIndex != -1
    }

    val isThereLangChange: Boolean
        get() = lastSelectedIndex != onStartSelectedIndex

    val selectedLang: Lang
        get() = languages[lastSelectedIndex]

    init {
        onStartSelectedIndex = lastSelectedIndex
    }
}