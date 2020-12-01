package hu.bme.aut.bestnights.fragments.party

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import hu.bme.aut.bestnights.R
import hu.bme.aut.bestnights.fragments.festival.EditFestivalDialogFragment
import hu.bme.aut.bestnights.model.Party

class EditPartyDialogFragment(party: Party): DialogFragment() {

    interface EditPartyDialogListener {
        fun onPartyEdited(party: Party)
    }

    private lateinit var listener: EditPartyDialogListener

    private lateinit var pn : EditText
    private lateinit var pp : EditText
    private lateinit var pa : EditText
    private lateinit var pl : EditText
    private lateinit var pd : DatePicker

    private var p: Party = party

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? EditPartyDialogListener
            ?: throw RuntimeException("Activity must implement the EditFestivalDialogListener interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext(), R.style.CustomDialogTheme)
            .setTitle(R.string.edit_festival)
            .setView(getContentView())
            .setPositiveButton(R.string.ok) { dialogInterface, i ->
                listener.onPartyEdited(getParty())
            }
            .setNegativeButton(R.string.cancel, null)
            .create()
    }

    private fun getParty(): Party {
        p.name = pn.text.toString()
        p.price = pp.text.toString().toInt()
        p.amount = pa.text.toString().toInt()
        p.location = pl.text.toString()
        p.date = (pd.year.toString() + "/" + pd.month.toString() + "/" + pd.dayOfMonth.toString())

        return p
    }

    private fun getContentView(): View {
        val contentView = LayoutInflater.from(context).inflate(R.layout.dialog_new_party, null)
        pn = contentView.findViewById(R.id.PartyName)
        pp = contentView.findViewById(R.id.PartyPrice)
        pa = contentView.findViewById(R.id.PartyAmount)
        pl = contentView.findViewById(R.id.PartyLocation)
        pd = contentView.findViewById(R.id.PartyDate)

        pn.setText(p.name)
        pp.setText(p.price.toString())
        pa.setText(p.amount.toString())
        pl.setText(p.location)

        val temps = p.date.split("/")
        pd.init(temps[0].toInt(), temps[1].toInt(), temps[2].toInt(), null)

        return contentView
    }

    companion object {
        const val TAG = "EditPartyDialogFragment"
    }

}