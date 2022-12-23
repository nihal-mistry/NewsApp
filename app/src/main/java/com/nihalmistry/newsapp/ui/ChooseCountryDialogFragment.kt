package com.nihalmistry.newsapp.ui

import android.content.SharedPreferences
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.nihalmistry.newsapp.databinding.FragmentChooseCountryDialogBinding
import com.nihalmistry.newsapp.databinding.ItemCountryDialogListBinding
import com.nihalmistry.newsapp.di.USER_PREFS
import com.nihalmistry.newsapp.utils.Constants
import com.nihalmistry.newsapp.utils.Constants.Companion.COUNTRY_FLAG_MAP
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named


class ChooseCountryDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentChooseCountryDialogBinding? = null

    val prefs by inject<SharedPreferences>(named(USER_PREFS))

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    val countries = COUNTRY_FLAG_MAP.keys.toList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseCountryDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.list.setHasFixedSize(true)
        binding.list.layoutManager = layoutManager
        binding.list.adapter = ItemAdapter()
    }

    private inner class ViewHolder(binding: ItemCountryDialogListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val tvCountry: TextView = binding.tvCountry
        val ivCountryFlag: ImageView = binding.ivCountryFlag
    }

    private inner class ItemAdapter :
        RecyclerView.Adapter<ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                ItemCountryDialogListBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val country = countries[position]
            holder.tvCountry.text = country
            holder.ivCountryFlag.setImageResource(COUNTRY_FLAG_MAP.get(country)!!)
            holder.itemView.setOnClickListener {
                prefs.edit()
                    .putString(Constants.COUNTRY_KEY, countries[holder.bindingAdapterPosition])
                    .commit()
                dismiss()
            }
        }

        override fun getItemCount(): Int {
            return countries.size
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}