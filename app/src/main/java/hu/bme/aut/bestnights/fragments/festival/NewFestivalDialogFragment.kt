package hu.bme.aut.bestnights.fragments.festival

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
import hu.bme.aut.bestnights.model.Festival

class NewFestivalDialogFragment : DialogFragment() {

    interface NewFestivalDialogListener {
        fun onFestivalCreated(newFestival: Festival)
    }

    private lateinit var listener: NewFestivalDialogListener

    private lateinit var fn : EditText
    private lateinit var fnp : EditText
    private lateinit var fna : EditText
    private lateinit var fl : EditText
    private lateinit var fsd : DatePicker
    private lateinit var fed : DatePicker

    private lateinit var c: Context

    private fun isValid(): Boolean {
        return fn.text.isNotEmpty() &&
                fnp.text.isNotEmpty() &&
                fna.text.isNotEmpty() &&
                fl.text.isNotEmpty()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        c = context
        listener = context as? NewFestivalDialogListener
            ?: throw RuntimeException("Activity must implement the NewPartyDialogListener interface!")
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
                    listener.onFestivalCreated(getFestival())
                    dismiss()
                } else {
                    Toast.makeText(c, "Please fill all of the data", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return dialog
    }

    private fun getFestival() = Festival(
        id = null,
        name = fn.text.toString(),
        normalPrice = fnp.text.toString().toInt(),
        normalAmount = fna.text.toString().toInt(),
        location = fl.text.toString(),
        startDate = (fsd.year.toString() + "/" + fsd.month.toString() + "/" + fsd.dayOfMonth.toString()),
        endDate = (fed.year.toString() + "/" + fed.month.toString() + "/" + fed.dayOfMonth.toString())
    )

    private fun getContentView(): View {
        val contentView = LayoutInflater.from(context).inflate(R.layout.dialog_new_festival, null)
        fn = contentView.findViewById(R.id.FestivalName)
        fnp = contentView.findViewById(R.id.FestivalNormalPrice)
        fna = contentView.findViewById(R.id.FestivalNormalAmount)
        fl = contentView.findViewById(R.id.FestivalLocation)
        fsd = contentView.findViewById(R.id.StartDate)
        fed = contentView.findViewById(R.id.EndDate)

        return contentView
    }

    companion object {
        const val TAG = "NewFestivalDialogFragment"
    }

}