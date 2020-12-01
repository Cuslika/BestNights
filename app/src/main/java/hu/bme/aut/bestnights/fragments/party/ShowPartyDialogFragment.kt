package hu.bme.aut.bestnights.fragments.party

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import hu.bme.aut.bestnights.R
import hu.bme.aut.bestnights.model.Party

class ShowPartyDialogFragment(party: Party): DialogFragment() {

    interface PurchasePartyDialogListener {
        fun onPartyPurchased(party: Party)
    }

    private lateinit var listener: PurchasePartyDialogListener

    private lateinit var pn : TextView
    private lateinit var pp : TextView
    private lateinit var pa : TextView
    private lateinit var pc : TextView
    private lateinit var pl : TextView
    private lateinit var pd : TextView

    private var p: Party = party

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? PurchasePartyDialogListener
            ?: throw RuntimeException("Activity must implement the PurchasePartyDialogListener interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext(), R.style.CustomDialogTheme)
            .setTitle(R.string.show_party)
            .setView(getContentView())
            .setPositiveButton(R.string.purchase) { dialogInterface, i ->
                listener.onPartyPurchased(p)
            }
            .setNegativeButton(R.string.cancel, null)
            .create()
    }

    private fun getContentView(): View {
        val contentView = LayoutInflater.from(context).inflate(R.layout.dialog_show_party, null)
        pn = contentView.findViewById(R.id.PartyName)
        pp = contentView.findViewById(R.id.PartyPrice)
        pa = contentView.findViewById(R.id.PartyAmount)
        pc = contentView.findViewById(R.id.PartyPrice)
        pl = contentView.findViewById(R.id.PartyLocation)
        pd = contentView.findViewById(R.id.PartyDate)

        pn.setText(p.name)
        pp.setText(p.price.toString())
        pa.setText(p.amount.toString())
        pd.setText(p.date)

        return contentView
    }

    companion object {
        const val TAG = "ShowPartyDialogFragment"
    }
}