package hu.bme.aut.bestnights.fragments.festival

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import hu.bme.aut.bestnights.R
import hu.bme.aut.bestnights.fragments.party.ShowPartyDialogFragment
import hu.bme.aut.bestnights.model.Festival
import hu.bme.aut.bestnights.model.Party

class ShowFestivalDialogFragment(festival: Festival) : DialogFragment(){

    interface PurchaseFestivalDialogListener {
        fun onFestivalPurchased(festival: Festival)
    }

    private lateinit var listener: ShowFestivalDialogFragment.PurchaseFestivalDialogListener

    private lateinit var pn : TextView
    private lateinit var pp : TextView
    private lateinit var pa : TextView
    private lateinit var pl : TextView
    //private lateinit var pd : TextView

    private var f: Festival = festival

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? PurchaseFestivalDialogListener
            ?: throw RuntimeException("Activity must implement the PurchaseFestivalDialogListener interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext(), R.style.CustomDialogTheme)
            .setTitle(R.string.show_festival)
            .setView(getContentView())
            .setPositiveButton(R.string.purchase) { dialogInterface, i ->
                listener.onFestivalPurchased(f)
            }
            .setNegativeButton(R.string.cancel, null)
            .create()
    }

    private fun getContentView(): View {
        val contentView = LayoutInflater.from(context).inflate(R.layout.dialog_show_festival, null)
        pn = contentView.findViewById(R.id.FestivalName)
        pp = contentView.findViewById(R.id.FestivalPrice)
        pa = contentView.findViewById(R.id.FestivalAmount)
        pl = contentView.findViewById(R.id.FestivalLocation)
        pn.setText(f.name)
        pp.setText(f.normalPrice.toString())
        pa.setText(f.normalAmount.toString())
        //pd.setText(f.date)

        return contentView
    }

    companion object {
        const val TAG = "ShowFestivalDialogFragment"
    }

}