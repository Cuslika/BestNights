package hu.bme.aut.bestnights.adapter.festival

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.bestnights.R
import hu.bme.aut.bestnights.fragments.festival.DetailsFestivalDialogFragment
import hu.bme.aut.bestnights.model.Festival

class DetailsFestivalAdapter(private val supportFragmentManager: FragmentManager) : RecyclerView.Adapter<DetailsFestivalAdapter.FestivalViewHolder>() {

    private val festivals = mutableListOf<Festival>()

    inner class FestivalViewHolder(festivalView: View) : RecyclerView.ViewHolder(festivalView){
        val nameTextView: TextView = festivalView.findViewById(R.id.eventName)
        val moreButton: ImageButton

        var festival: Festival? = null

        init {
            moreButton = festivalView.findViewById(R.id.MoreButton)
            moreButton.setOnClickListener {
                festival?.let { it ->
                    DetailsFestivalDialogFragment(it).show(
                        supportFragmentManager,
                        DetailsFestivalDialogFragment.TAG
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsFestivalAdapter.FestivalViewHolder {
        val FestivalView: View = LayoutInflater.from(parent.context).inflate(R.layout.festival_list_show, parent, false)
        return FestivalViewHolder(FestivalView)
    }

    override fun onBindViewHolder(holder: DetailsFestivalAdapter.FestivalViewHolder, position: Int) {
        val festival = festivals[position]
        holder.nameTextView.text = festival.name

        holder.festival = festival
    }

    override fun getItemCount(): Int {
        return festivals.size
    }

    fun update(f: List<Festival>) {
        festivals.clear()
        festivals.addAll(f)
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
}