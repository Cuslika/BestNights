package hu.bme.aut.bestnights.adapter.party

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.bestnights.R
import hu.bme.aut.bestnights.fragments.party.ShowPartyDialogFragment
import hu.bme.aut.bestnights.model.Party

class PartyAdapter(private val supportFragmentManager: FragmentManager) :
    RecyclerView.Adapter<PartyAdapter.PartyViewHolder>() {

    private val parties = mutableListOf<Party>()

    inner class PartyViewHolder(partyView: View) : RecyclerView.ViewHolder(partyView) {
        val nameTextView: TextView = partyView.findViewById(R.id.eventName)
        val moreButton: ImageButton

        var party: Party? = null

        init {
            moreButton = partyView.findViewById(R.id.PurchaseButton)
            moreButton.setOnClickListener {
                party?.let { it ->
                    ShowPartyDialogFragment(it).show(
                        supportFragmentManager,
                        ShowPartyDialogFragment.TAG
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartyViewHolder {
        val PartyView: View = LayoutInflater.from(parent.context).inflate(R.layout.festival_list, parent, false)
        return PartyViewHolder(PartyView)
    }

    override fun onBindViewHolder(holder: PartyViewHolder, position: Int) {
        val party = parties[position]
        holder.nameTextView.text = party.name

        holder.party = party
    }

    override fun getItemCount(): Int {
        return parties.size
    }

    fun update(f: List<Party>) {
        parties.clear()
        for(party: Party in f) {
            if(party.amount > 0)
                parties.add(party)
        }
        notifyDataSetChanged()
    }

    fun purchaseParty(pp: Party) {
        var idx = parties.indexOf(pp)
        parties.remove(pp)
        pp.amount -= 1
        if(pp.amount > 0) {
            parties.add(idx, pp)
        }
        notifyItemChanged(idx)
    }

}