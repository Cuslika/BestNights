package hu.bme.aut.bestnights.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.bestnights.R
import hu.bme.aut.bestnights.model.Party

class AdminPartyAdapter(private val listener: PartyClickListener) : RecyclerView.Adapter<AdminPartyAdapter.PartyViewHolder>() {

    private val parties = mutableListOf<Party>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartyViewHolder{
        val PartyView: View = LayoutInflater.from(parent.context).inflate(R.layout.party_list, parent, false)
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

    interface PartyClickListener {
        fun onItemChanged(item: Party)
        fun onItemDeleted(item: Party)
    }

    inner class PartyViewHolder(partyView: View) : RecyclerView.ViewHolder(partyView){
        val nameTextView: TextView
        val editButton: Button
        val removeButton: Button

        var party: Party? = null

        init {
            nameTextView = partyView.findViewById(R.id.PartyName)
            editButton = partyView.findViewById(R.id.EditButton)
            removeButton = partyView.findViewById(R.id.RemoveButton)
            removeButton.setOnClickListener {
                party?.let { it -> removeItem(it)
                    listener.onItemDeleted(it)}
            }
        }
    }

    fun addParty(party: Party) {
        parties.add(party)
        notifyItemInserted(parties.size - 1)
    }

    fun update(f: List<Party>) {
        parties.clear()
        parties.addAll(f)
        notifyDataSetChanged()
    }

    fun removeItem(party: Party){
        parties.remove(party)
        notifyDataSetChanged()
    }
}