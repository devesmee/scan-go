package com.example.scango

import android.media.Image
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.github.sumimakito.awesomeqr.AwesomeQrRenderer
import com.github.sumimakito.awesomeqr.RenderResult
import com.github.sumimakito.awesomeqr.option.RenderOption
import com.github.sumimakito.awesomeqr.option.color.Color
import java.text.DecimalFormat

/**
 * A simple [Fragment] subclass.
 * Use the [CheckoutFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CheckoutFragment : Fragment() {

    private lateinit var cancelButton: ImageButton
    private lateinit var checkoutPrice: TextView
    private lateinit var qrCodeImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_checkout, container, false)
        cancelButton = view.findViewById(R.id.cancelButton)
        checkoutPrice = view.findViewById(R.id.checkoutPrice)
        qrCodeImageView = view.findViewById(R.id.qrCodeImageView)
        val format = DecimalFormat("0.00")
        checkoutPrice.text = format.format(GroceriesManager.getTotalPrice())

        setNavigation()
        generateQRCode()

        return view;
    }

    private fun setNavigation() {
        cancelButton.setOnClickListener {
            val manager = activity?.supportFragmentManager
            val transaction = manager?.beginTransaction()
            if (transaction != null) {
                transaction.replace(R.id.fragmentContainer, OverviewFragment())
                transaction.commit()
            }
        }
    }

    private fun generateQRCode() {
        val qrCode = RenderOption()
        val format = DecimalFormat("0.00")
        qrCode.size = 800
        qrCode.borderWidth = 20
        qrCode.color = Color(false, ContextCompat.getColor(requireContext(), R.color.white),
            ContextCompat.getColor(requireContext(), R.color.yellow_light), ContextCompat.getColor(requireContext(), R.color.yellow))
        qrCode.content = format.format(GroceriesManager.getTotalPrice())

        try {
            val result = AwesomeQrRenderer.render(qrCode)
            if (result.bitmap != null) {
                qrCodeImageView.setImageBitmap(result.bitmap)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}