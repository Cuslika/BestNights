package hu.bme.aut.bestnights.fragments.party

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import hu.bme.aut.bestnights.R
import hu.bme.aut.bestnights.model.Party

class DetailsPartyDialogFragment(party: Party) : DialogFragment() {

    private lateinit var pn : TextView
    private lateinit var pp : TextView
    private lateinit var pa : TextView
    private lateinit var pl : TextView
    private lateinit var pd : TextView

    private var p: Party = party

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext(), R.style.CustomDialogTheme)
            .setTitle(R.string.show_party)
            .setView(getContentView())
            .setNegativeButton(R.string.back, null)
            .create()
    }

    private fun getContentView(): View {
        val contentView = LayoutInflater.from(context).inflate(R.layout.dialog_show_party, null)
        pn = contentView.findViewById(R.id.PartyName)
        pp = contentView.findViewById(R.id.PartyPrice)
        pa = contentView.findViewById(R.id.PartyAmount)
        pl = contentView.findViewById(R.id.PartyLocation)
        pd = contentView.findViewById(R.id.PartyDate)

        pn.setText(p.name)
        pp.setText(p.price.toString())
        pa.setText("1")
        pl.setText(p.location)
        pd.setText(p.date)

        return contentView
    }

    companion object {
        const val TAG = "DetailsPartyDialogFragment"
    }
}