package hu.bme.aut.bestnights.adapter.party

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.bestnights.R
import hu.bme.aut.bestnights.fragments.festival.EditFestivalDialogFragment
import hu.bme.aut.bestnights.fragments.party.EditPartyDialogFragment
import hu.bme.aut.bestnights.model.Party

class AdminPartyAdapter(private val listener: PartyClickListener, private val supportFragmentManager: FragmentManager) : RecyclerView.Adapter<AdminPartyAdapter.AdminPartyViewHolder>() {

    private val parties = mutableListOf<Party>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminPartyViewHolder {
        val PartyView: View = LayoutInflater.from(parent.context).inflate(R.layout.festival_list_admin, parent, false)
        return AdminPartyViewHolder(PartyView)
    }

    override fun onBindViewHolder(holder: AdminPartyViewHolder, position: Int) {
        val party = parties[position]
        holder.nameTextView.text = party.name

        holder.party = party
    }

    override fun getItemCount(): Int {
        return parties.size
    }

    interface PartyClickListener {
        fun onItemDeleted(item: Party)
    }

    inner class AdminPartyViewHolder(partyView: View) : RecyclerView.ViewHolder(partyView){
        val nameTextView: TextView
        val editButton: ImageButton
        val removeButton: ImageButton

        var party: Party? = null

        init {
            nameTextView = partyView.findViewById(R.id.eventName)
            editButton = partyView.findViewById(R.id.EditButton)
            editButton.setOnClickListener {
                party?.let { it1 ->
                    EditPartyDialogFragment(it1).show(
                        supportFragmentManager,
                        EditFestivalDialogFragment.TAG
                    )
                }
            }
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

    fun update(party: Party) {
        var idx = parties.indexOf(party)
        var newP: Party = parties.get(parties.indexOf(party))
        parties.remove(party)
        parties.add(idx, newP)
        notifyDataSetChanged()
    }

    fun removeItem(party: Party){
        parties.remove(party)
        notifyDataSetChanged()
    }
}