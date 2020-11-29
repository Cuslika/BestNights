package hu.bme.aut.bestnights.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.bestnights.R
import hu.bme.aut.bestnights.fragments.festival.ShowFestivalDialogFragment
import hu.bme.aut.bestnights.fragments.party.ShowPartyDialogFragment
import hu.bme.aut.bestnights.model.Festival
import hu.bme.aut.bestnights.model.Party

class FestivalAdapter(private val supportFragmentManager: FragmentManager) : RecyclerView.Adapter<FestivalAdapter.FestivalViewHolder>() {

    private val festivals = mutableListOf<Festival>()

    inner class FestivalViewHolder(festivalView: View) : RecyclerView.ViewHolder(festivalView){
        val nameTextView: TextView = festivalView.findViewById(R.id.eventName)
        val moreButton: ImageButton

        var festival: Festival? = null

        init {
            moreButton = festivalView.findViewById(R.id.MoreButton)
            moreButton.setOnClickListener {
                festival?.let { it ->
                    ShowFestivalDialogFragment(it).show(
                        supportFragmentManager,
                        ShowPartyDialogFragment.TAG
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FestivalAdapter.FestivalViewHolder {
        val FestivalView: View = LayoutInflater.from(parent.context).inflate(R.layout.festival_list, parent, false)
        return FestivalViewHolder(FestivalView)
    }

    override fun onBindViewHolder(holder: FestivalViewHolder, position: Int) {
        val festival = festivals[position]
        holder.nameTextView.text = festival.name

        holder.festival = festival
    }

    override fun getItemCount(): Int {
        return festivals.size
    }

    fun update(f: List<Festival>) {
        festivals.clear()
        for(festival: Festival in f) {
            if(festival.normalAmount > 0)
                festivals.add(festival)
        }
        notifyDataSetChanged()
    }

    fun updateAll(p: List<Festival> ,fs: ArrayList<String?>?) {
        festivals.clear()
        if(!fs.isNullOrEmpty()) {
            for (festival: Festival in p)
                for (s: String? in fs!!)
                    if (festival.name == s)
                        festivals.add(festival)
        }
        notifyDataSetChanged()
    }

    fun purchaseFestival(fs: Festival) {
        var idx = festivals.indexOf(fs)
        festivals.remove(fs)
        fs.normalAmount -= 1
        if(fs.normalAmount > 0) {
            festivals.add(idx, fs)
        }
        notifyDataSetChanged()
    }

}