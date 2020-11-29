package hu.bme.aut.bestnights.fragments.festival

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

    private lateinit var fn : EditText
    private lateinit var fp : EditText
    private lateinit var fna : EditText
    private lateinit var fl : EditText

    private var f: Festival = festival

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? EditFestivalDialogListener
            ?: throw RuntimeException("Activity must implement the EditFestivalDialogListener interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext(), R.style.CustomDialogTheme)
            .setTitle(R.string.edit_festival)
            .setView(getContentView())
            .setPositiveButton(R.string.ok) { dialogInterface, i ->
                listener.onFestivalEdited(getFestival())
            }
            .setNegativeButton(R.string.cancel, null)
            .create()
    }

    private fun getFestival(): Festival {
        f.name = fn.text.toString()
        f.normalPrice = fp.text.toString().toInt()
        f.normalAmount = fna.text.toString().toInt()

        return f
    }

    private fun getContentView(): View {
        val contentView = LayoutInflater.from(context).inflate(R.layout.dialog_new_festival, null)
        fn = contentView.findViewById(R.id.FestivalName)
        fp = contentView.findViewById(R.id.FestivalNormalPrice)
        fna = contentView.findViewById(R.id.FestivalNormalAmount)
        //efl = contentView.findViewById(R.id.FestivalLocation)

        fn.setText(f.name)
        fp.setText(f.normalPrice.toString())
        fna.setText(f.normalAmount.toString())


        return contentView
    }

    companion object {
        const val TAG = "EditFestivalDialogFragment"
    }
}