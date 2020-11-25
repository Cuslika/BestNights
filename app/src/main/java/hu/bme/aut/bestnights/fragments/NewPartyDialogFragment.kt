package hu.bme.aut.bestnights.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.opengl.ETC1.isValid
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import hu.bme.aut.bestnights.R
import hu.bme.aut.bestnights.model.Party
import kotlinx.android.synthetic.main.dialog_new_party.*

class NewPartyDialogFragment : DialogFragment() {

    interface NewPartyDialogListener {
        fun onPartyCreated(newParty: Party)
    }

    private lateinit var listener: NewPartyDialogListener

    private lateinit var pn : EditText
    private lateinit var pp : EditText
    private lateinit var pa : EditText
    private lateinit var pc : EditText
    private lateinit var pl : EditText

    private fun isValid() = pn.text.isNotEmpty()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? NewPartyDialogListener
            ?: throw RuntimeException("Activity must implement the NewShoppingItemDialogListener interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.new_party)
            .setView(getContentView())
            .setPositiveButton(R.string.ok) { dialogInterface, i ->
                if (isValid()) {
                    listener.onPartyCreated(getParty())
                }
            }
            .setNegativeButton(R.string.cancel, null)
            .create()
    }

    private fun getParty() = Party(
        id = null,
        name = pn.text.toString(),
        price = pp.text.toString().toInt(),
        amount = pa.text.toString().toInt()
        //capacity = pc.text.toString().toInt(),
        //location = pl.text.toString()
    )

    private fun getContentView(): View {
        val contentView = LayoutInflater.from(context).inflate(R.layout.dialog_new_party, null)
        pn = contentView.findViewById(R.id.PartyName)
        pp = contentView.findViewById(R.id.PartyPrice)
        pa = contentView.findViewById(R.id.PartyAmount)
        pc = contentView.findViewById(R.id.PartyPrice)
        pl = contentView.findViewById(R.id.PartyLocation)

        return contentView
    }

    companion object {
        const val TAG = "NewPartyDialogFragment"
    }

}