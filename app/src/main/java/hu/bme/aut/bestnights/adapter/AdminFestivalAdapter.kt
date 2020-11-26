package hu.bme.aut.bestnights.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.bestnights.R
import hu.bme.aut.bestnights.fragments.festival.EditFestivalDialogFragment
import hu.bme.aut.bestnights.model.Festival

class AdminFestivalAdapter(private val listener: AdminFestivalClickListener, private val supportFragmentManager: FragmentManager) : RecyclerView.Adapter<AdminFestivalAdapter.FestivalViewHolder>() {

    private val festivals = mutableListOf<Festival>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FestivalViewHolder{
        val festivalView: View = LayoutInflater.from(parent.context).inflate(R.layout.festival_list_admin, parent, false)
        return FestivalViewHolder(festivalView)
    }

    override fun onBindViewHolder(holder: FestivalViewHolder, position: Int) {
        val festival = festivals[position]
        holder.nameTextView.text = festival.name

        holder.festival = festival
    }

    override fun getItemCount(): Int {
        return festivals.size
    }

    interface AdminFestivalClickListener {
        fun onItemDeleted(item: Festival)
    }

    inner class FestivalViewHolder(festivalView: View) : RecyclerView.ViewHolder(festivalView){
        val nameTextView: TextView
        val editButton: ImageButton
        val removeButton: ImageButton

        var festival: Festival? = null

        init {
            nameTextView = festivalView.findViewById(R.id.eventName)
            editButton = festivalView.findViewById(R.id.EditButton)
            editButton.setOnClickListener {
                festival?.let { it1 ->
                    EditFestivalDialogFragment(it1).show(
                        supportFragmentManager,
                        EditFestivalDialogFragment.TAG
                    )
                }
            }
            removeButton = festivalView.findViewById(R.id.RemoveButton)
            removeButton.setOnClickListener {
                festival?.let { it -> removeItem(it)
                    listener.onItemDeleted(it)}
            }
        }
    }

    fun addFestival(festival: Festival) {
        festivals.add(festival)
        notifyItemInserted(festivals.size - 1)
    }

    fun update(festival: Festival) {
        var idx = festivals.indexOf(festival)
        var newF: Festival = festivals.get(festivals.indexOf(festival))
        festivals.remove(festival)
        festivals.add(idx, newF)
        notifyDataSetChanged()
    }

    fun update(f: List<Festival>) {
        festivals.clear()
        festivals.addAll(f)
        notifyDataSetChanged()
    }

    fun removeItem(festival: Festival){
        festivals.remove(festival)
        notifyDataSetChanged()
    }
}