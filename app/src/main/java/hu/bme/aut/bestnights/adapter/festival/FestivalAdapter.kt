package hu.bme.aut.bestnights.adapter.festival

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

class FestivalAdapter(private val supportFragmentManager: FragmentManager) : RecyclerView.Adapter<FestivalAdapter.FestivalViewHolder>() {

    private val festivals = mutableListOf<Festival>()

    inner class FestivalViewHolder(festivalView: View) : RecyclerView.ViewHolder(festivalView){
        val nameTextView: TextView = festivalView.findViewById(R.id.eventName)
        val moreButton: ImageButton

        var festival: Festival? = null

        init {
            moreButton = festivalView.findViewById(R.id.PurchaseButton)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FestivalViewHolder {
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

    fun purchaseFestival(fs: Festival) {
        var idx: Int = festivals.indexOf(fs)
        festivals.remove(fs)
        fs.normalAmount -= 1
        if(fs.normalAmount > 0) {
            festivals.add(idx, fs)
        }
        notifyItemChanged(idx)
    }

}