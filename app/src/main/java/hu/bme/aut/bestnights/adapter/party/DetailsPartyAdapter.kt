package hu.bme.aut.bestnights.adapter.party

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.bestnights.R
import hu.bme.aut.bestnights.fragments.party.DetailsPartyDialogFragment
import hu.bme.aut.bestnights.model.Party

class DetailsPartyAdapter(private val supportFragmentManager: FragmentManager) : RecyclerView.Adapter<DetailsPartyAdapter.PartyViewHolder>() {

    private val parties = mutableListOf<Party>()

    inner class PartyViewHolder(partyView: View) : RecyclerView.ViewHolder(partyView) {
        val nameTextView: TextView = partyView.findViewById(R.id.eventName)
        val moreButton: ImageButton

        var party: Party? = null

        init {
            moreButton = partyView.findViewById(R.id.MoreButton)
            moreButton.setOnClickListener {
                party?.let { it ->
                    DetailsPartyDialogFragment(it).show(
                        supportFragmentManager,
                        DetailsPartyDialogFragment.TAG
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsPartyAdapter.PartyViewHolder {
        val PartyView: View = LayoutInflater.from(parent.context).inflate(R.layout.festival_list_show, parent, false)
        return PartyViewHolder(PartyView)
    }

    override fun onBindViewHolder(holder: DetailsPartyAdapter.PartyViewHolder, position: Int) {
        val party = parties[position]
        holder.nameTextView.text = party.name

        holder.party = party
    }

    override fun getItemCount(): Int {
        return parties.size
    }

    fun update(p: List<Party>) {
        parties.clear()
        parties.addAll(p)
        notifyDataSetChanged()
    }

    fun updateAll(p: List<Party> ,ps: ArrayList<String?>?) {
        parties.clear()
        if(!ps.isNullOrEmpty()) {
            for (party: Party in p)
                for (s: String? in ps!!)
                    if (party.name == s)
                        parties.add(party)
        }
        notifyDataSetChanged()
    }
}