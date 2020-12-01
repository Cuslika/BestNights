package hu.bme.aut.bestnights.fragments.party

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import hu.bme.aut.bestnights.R
import hu.bme.aut.bestnights.model.Party

class NewPartyDialogFragment : DialogFragment() {

    interface NewPartyDialogListener {
        fun onPartyCreated(newParty: Party)
    }

    private lateinit var listener: NewPartyDialogListener

    private lateinit var pn : EditText
    private lateinit var pp : EditText
    private lateinit var pa : EditText
    private lateinit var pl : EditText
    private lateinit var pd : DatePicker

    private lateinit var c: Context

    private fun isValid(): Boolean {
        return pn.text.isNotEmpty() &&
                pp.text.isNotEmpty() &&
                pa.text.isNotEmpty() &&
                pl.text.isNotEmpty()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        c = context
        listener = context as? NewPartyDialogListener
            ?: throw RuntimeException("Activity must implement the NewShoppingItemDialogListener interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = AlertDialog.Builder(requireContext(), R.style.CustomDialogTheme)
            .setTitle(R.string.new_party)
            .setView(getContentView())
            .setPositiveButton(R.string.ok, null)
            .setNegativeButton(R.string.cancel, null)
            .create()

        dialog.setOnShowListener {
            val okButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            okButton.setOnClickListener {
                if (isValid()) {
                    listener.onPartyCreated(getParty())
                    dismiss()
                } else {
                    Toast.makeText(c, "Please fill all of the data", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return dialog
    }

    private fun getParty() = Party(
        id = null,
        name = pn.text.toString(),
        price = pp.text.toString().toInt(),
        amount = pa.text.toString().toInt(),
        location = pl.text.toString(),
        date = (pd.year.toString() + "/" + pd.month.toString() + "/" + pd.dayOfMonth.toString())
    )

    private fun getContentView(): View {
        val contentView = LayoutInflater.from(context).inflate(R.layout.dialog_new_party, null)
        pn = contentView.findViewById(R.id.PartyName)
        pp = contentView.findViewById(R.id.PartyPrice)
        pa = contentView.findViewById(R.id.PartyAmount)
        pl = contentView.findViewById(R.id.PartyLocation)
        pd = contentView.findViewById(R.id.PartyDate)

        return contentView
    }

    companion object {
        const val TAG = "NewPartyDialogFragment"
    }

}