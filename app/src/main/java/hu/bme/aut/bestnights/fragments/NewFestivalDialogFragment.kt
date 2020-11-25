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
import hu.bme.aut.bestnights.model.Festival
import kotlinx.android.synthetic.main.dialog_new_festival.*

class NewFestivalDialogFragment : DialogFragment() {

    interface NewFestivalDialogListener {
        fun onFestivalCreated(newFestival: Festival)
    }

    private lateinit var listener: NewFestivalDialogListener

    private lateinit var fn : EditText
    private lateinit var fep : EditText
    private lateinit var fnp : EditText
    private lateinit var fea : EditText
    private lateinit var fna : EditText
    private lateinit var fl : EditText

    private fun isValid() = fn.text.isNotEmpty()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? NewFestivalDialogListener
            ?: throw RuntimeException("Activity must implement the NewShoppingItemDialogListener interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.new_festival)
            .setView(getContentView())
            .setPositiveButton(R.string.ok) { dialogInterface, i ->
                if (isValid()) {
                    listener.onFestivalCreated(getFestival())
                }
            }
            .setNegativeButton(R.string.cancel, null)
            .create()
    }

    private fun getFestival() = Festival(
        id = null,
        name = fn.text.toString(),
        earlybirdPrice = fep.text.toString().toInt()
        //normalPrice = fnp.text.toString().toInt(),
        //earlybirdAmount = fea.text.toString().toInt(),
        //normalAmount = fna.text.toString().toInt(),
        //location = fl.text.toString()
    )

    private fun getContentView(): View {
        val contentView = LayoutInflater.from(context).inflate(R.layout.dialog_new_festival, null)
        fn = contentView.findViewById(R.id.FestivalName)
        fep = contentView.findViewById(R.id.FestivalEarlyPrice)
        fnp = contentView.findViewById(R.id.FestivalNormalPrice)
        fea = contentView.findViewById(R.id.FestivalEarlyAmount)
        fna = contentView.findViewById(R.id.FestivalNormalAmount)
        fl = contentView.findViewById(R.id.FestivalLocation)

        return contentView
    }

    companion object {
        const val TAG = "NewFestivalDialogFragment"
    }

}