package com.example.scango

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.vision.barcode.Barcode
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning

/**
 * A simple [Fragment] subclass.
 * Use the [ScanFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ScanFragment : Fragment() {

    private lateinit var overviewButton: Button
    private lateinit var cameraView: PreviewView

    private lateinit var camera: Camera
    private lateinit var preview: Preview
    private lateinit var barcodeScanner: BarcodeScanner
    private lateinit var barcodeOptions: BarcodeScannerOptions

    private val permissionCode = 100
    private val backCamera = CameraSelector.LENS_FACING_BACK;
    private val cameraSelector = CameraSelector.Builder().requireLensFacing(backCamera).build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_scan, container, false)
        overviewButton = view.findViewById(R.id.backToOverviewButton)
        cameraView = view.findViewById(R.id.cameraView)
        preview = Preview.Builder().build()

        checkPermissions()
        setNavigation()
        setupBarcodeScanner()

        return view;
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context!!)

        // used to bind the lifecycle of camera to the lifecycle owner
        val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

        // add a listener to the cameraProviderFuture
        val cameraExecutor = ContextCompat.getMainExecutor(context!!)
        cameraProviderFuture.addListener(Runnable {cameraProviderFuture.addListener(Runnable {
            cameraProvider.unbindAll()
            camera = cameraProvider.bindToLifecycle(
                this as LifecycleOwner,
                cameraSelector,
                preview
            )
            preview.setSurfaceProvider(cameraView.surfaceProvider)
        }, cameraExecutor)}, cameraExecutor)
    }

    private fun checkPermissions() {
        if (ActivityCompat.checkSelfPermission(context!!.applicationContext,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            startCamera()
        } else {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), permissionCode)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == permissionCode) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                startCamera()
            }
        }
    }

    private fun setNavigation() {
        overviewButton.setOnClickListener {
            val manager = activity?.supportFragmentManager
            val transaction = manager?.beginTransaction()
            if (transaction != null) {
                transaction.replace(R.id.fragmentContainer, OverviewFragment())
                transaction.commit()
            }
        }
    }

    private fun setupBarcodeScanner() {
        barcodeOptions = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(
                Barcode.FORMAT_EAN_13,
                Barcode.FORMAT_UPC_A)
            .build()
        barcodeScanner = BarcodeScanning.getClient(barcodeOptions)
    }

    /*
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment ScanFragment.
         */
        @JvmStatic
        fun newInstance() =
            ScanFragment()
    }
    */
}