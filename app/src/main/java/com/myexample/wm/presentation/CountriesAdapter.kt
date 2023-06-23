package com.myexample.wm.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.myexample.wm.R
import com.myexample.wm.databinding.ItemCountryBinding
import com.myexample.wm.domain.model.Country


class CountriesAdapter :
    RecyclerView.Adapter<CountriesAdapter.ItemViewHolder>() {

    private var countries: MutableList<Country> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun update(countries: List<Country>) {
        this.countries.clear()
        this.notifyDataSetChanged()

        countries.forEachIndexed { i, country ->
            this.countries.add(country)
            this.notifyItemInserted(i)
        }
    }

    class ItemViewHolder(private val binding: ItemCountryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(country: Country) {
//            Original URL doesn't work
//            binding.ivFlag.load(country.flag) {
            binding.ivFlag.load("https://www.worldometers.info/img/flags/${country.code.lowercase()}-flag.gif"){
                placeholder(R.mipmap.ic_launcher_round)
                error(R.mipmap.ic_launcher_round)
                crossfade(true)
            }
            binding.country = country
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCountryBinding.inflate(inflater, parent, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return countries.size
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(countries[position])
    }

}