package hu.bme.aut.bestnights.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import hu.bme.aut.bestnights.R
import hu.bme.aut.bestnights.model.Festival

class EditFestivalDialogFragment(festival: Festival) : DialogFragment() {

    interface EditFestivalDialogListener {
        fun onFestivalEdited(festival: Festival)
    }

    private lateinit var listener: EditFestivalDialogListener

    private lateinit var efn : EditText
    private lateinit var efep : EditText
    private lateinit var efnp : EditText
    private lateinit var efea : EditText
    private lateinit var efna : EditText
    private lateinit var efl : EditText

    private var f: Festival = festival

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? EditFestivalDialogListener
            ?: throw RuntimeException("Activity must implement the NewShoppingItemDialogListener interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.edit_festival)
            .setView(getContentView())
            .setPositiveButton(R.string.ok) { dialogInterface, i ->
                listener.onFestivalEdited(getFestival())
            }
            .setNegativeButton(R.string.cancel, null)
            .create()
    }

    private fun getFestival(): Festival {
        var n: String
        var ep: Int
        var en: Int
        var eea: Int
        var ena: Int
        var el: Int

        f.name = efn.text.toString()
        f.earlybirdPrice = efep.text.toString().toInt()

        return f
    }

    private fun getContentView(): View {
        val contentView = LayoutInflater.from(context).inflate(R.layout.dialog_edit_festival, null)
        efn = contentView.findViewById(R.id.EFestivalName)
        efep = contentView.findViewById(R.id.EFestivalEarlyPrice)
        efnp = contentView.findViewById(R.id.EFestivalNormalPrice)
        efea = contentView.findViewById(R.id.EFestivalEarlyAmount)
        efna = contentView.findViewById(R.id.EFestivalNormalAmount)
        efl = contentView.findViewById(R.id.EFestivalLocation)

        Log.d("Debug", f.name)

        efn.hint = f.name
        efep.hint = f.earlybirdPrice.toString()
        //efep.setHint(f.normalPrice)
        //efep.setHint(f.earlybirdAmount)
        //efep.setHint(f.normalAmount)
        //efep.setHint(f.location)
        //efep.setHint(f.)
        //efep.setHint(f.)

        return contentView
    }

    companion object {
        const val TAG = "EditFestivalDialogFragment"
    }
}