package hu.bme.aut.bestnights.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.bestnights.R
import hu.bme.aut.bestnights.fragments.EditFestivalDialogFragment
import hu.bme.aut.bestnights.model.Festival

class FestivalAdapter(private val listener: FestivalAdapter.FestivalClickListener, private val supportFragmentManager: FragmentManager) : RecyclerView.Adapter<FestivalAdapter.FestivalViewHolder>() {

    interface FestivalClickListener {
    }

    inner class FestivalViewHolder(festivalView: View) : RecyclerView.ViewHolder(festivalView){

        var festival: Festival? = null

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FestivalViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: FestivalViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

}