package hu.bme.aut.bestnights.fragments.festival

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import hu.bme.aut.bestnights.R
import hu.bme.aut.bestnights.model.Festival

class DetailsFestivalDialogFragment(festival: Festival) : DialogFragment() {

    private lateinit var pn : TextView
    private lateinit var pp : TextView
    private lateinit var pa : TextView
    private lateinit var pl : TextView
    private lateinit var psd : TextView
    private lateinit var ped : TextView

    private var f: Festival = festival

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext(), R.style.CustomDialogTheme)
            .setTitle(R.string.show_festival)
            .setView(getContentView())
            .setNegativeButton(R.string.back, null)
            .create()
    }

    private fun getContentView(): View {
        val contentView = LayoutInflater.from(context).inflate(R.layout.dialog_show_festival, null)
        pn = contentView.findViewById(R.id.FestivalName)
        pp = contentView.findViewById(R.id.FestivalPrice)
        pa = contentView.findViewById(R.id.FestivalAmount)
        pl = contentView.findViewById(R.id.FestivalLocation)
        psd = contentView.findViewById(R.id.StartDate)
        ped = contentView.findViewById(R.id.EndDate)
        pn.setText(f.name)
        pp.setText(f.normalPrice.toString())
        pa.setText("1")
        pl.setText(f.location)
        psd.setText(f.startDate)
        ped.setText(f.endDate)

        return contentView
    }

    companion object {
        const val TAG = "DetailsFestivalDialogFragment"
    }
}