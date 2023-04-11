package pham.hien.barcodescanner

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.budiyev.android.codescanner.*
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import pham.hien.barcodescanner.databinding.ActivityMainBinding
import java.lang.Exception
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        const val RESULT = "RESULT"
    }

    private val TAG = "YingMing"
    private var bankId = ""

    private var mBankAccountRecent: BankAccountRecent? = null
    private lateinit var codeScanner: CodeScanner
    private var mBankSelected = BankModel()
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel: MainViewModel by lazy { ViewModelProvider(this)[MainViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        mBankAccountRecent = SharePre.getBankAccountRecent(this)
        mBankAccountRecent?.let {
            bankId = it.bankId.toString()
            binding.tvBankName.text = it.bankName
            Glide.with(this).load(it.logo).into(binding.imvLogo)
            binding.tvTenTk.text = it.accountName
            binding.edSoTk.setText(it.bankNumber)
            binding.tvCheckAccount.visibility = View.GONE
        }

        binding.layoutBank.setOnClickListener(this)
        binding.tvCheckAccount.setOnClickListener(this)
        binding.btnQrCode.setOnClickListener(this)

        val adapter = BankAdapter(this, ArrayList<BankModel>()) {
            Glide.with(this).load(it.logo).into(binding.imvLogo)
            binding.tvBankName.text = it.name
            binding.rcvListBank.visibility = View.GONE
            bankId = it.bin
            mBankSelected = it
            binding.tvCheckAccount.visibility = View.VISIBLE
        }

        binding.rcvListBank.adapter = adapter
        viewModel.getListBank(this)
        viewModel.mListBank.observe(this) {
            adapter.setData(it)
        }
        viewModel.mBankAccountRequest.observe(this) { bankAccountRequest ->
            if (bankAccountRequest.code == "00") {
                binding.tvTenTk.text = bankAccountRequest.data?.accountName
                SharePre.setBankAccountRecent(this,
                    BankAccountRecent(bankId.toInt(),
                        mBankSelected.logo,
                        mBankSelected.name,
                        binding.edSoTk.text.toString(),
                        binding.tvTenTk.text.toString()))
                binding.tvCheckAccount.visibility = View.GONE
            } else {
                binding.tvTenTk.text = bankAccountRequest.desc
            }
        }

//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
//        ) {
//            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 1)
//        } else {
//            startScanning()
//        }
    }

    private fun getQuickQRCode(
        bankID: String,
        accountNumber: String,
        amount: Int,
        name: String,
    ): String {
        return "https://img.vietqr.io/image/$bankID-$accountNumber-3X8QDX1.jpg?amount=$amount&accountName=$name"
    }

    override fun onClick(v: View) {
        when (v) {
            binding.layoutBank -> {
                binding.rcvListBank.visibility = View.VISIBLE
            }
            binding.tvCheckAccount -> {
                viewModel.checkBankAccount(this,
                    BankAccountCheck(bankId.toInt(), binding.edSoTk.text.toString()))
            }
            binding.btnQrCode -> {
                var amount = 0
                amount = try {
                    binding.edSoTien.text.toString().toInt()
                } catch (e: Exception) {
                    0
                }
                val url = getQuickQRCode(
                    bankID = bankId,
                    accountNumber = binding.edSoTk.text.toString(),
                    amount = amount,
                    name = binding.tvTenTk.text.toString().replace(" ", "%20")
                )
                Glide.with(this).load(url).into(binding.imvQrCode)
            }
        }
    }

//    private fun startScanning() {
//        codeScanner = CodeScanner(this, binding.scannerView)
//        codeScanner.camera = CodeScanner.CAMERA_BACK
//        codeScanner.formats = CodeScanner.ALL_FORMATS
//        codeScanner.autoFocusMode = AutoFocusMode.SAFE
//        codeScanner.scanMode = ScanMode.SINGLE
//        codeScanner.isAutoFocusEnabled = true
//        codeScanner.isFlashEnabled = false
//        codeScanner.decodeCallback = DecodeCallback {
//            runOnUiThread {
//                Snackbar.make(binding.root, "${it.text}", Snackbar.LENGTH_INDEFINITE)
//                    .setAction("Ẩn") {
//                        codeScanner.startPreview()
//                    }.show()
//            }
//            Log.d(TAG, "startScanning: ${it.barcodeFormat}")
//        }
//        codeScanner.errorCallback = ErrorCallback {
//            runOnUiThread {
//                Toast.makeText(this, "Result${it.message}", Toast.LENGTH_LONG).show()
//            }
//        }
//        binding.scannerView.setOnClickListener {
//            codeScanner.startPreview()
//        }
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray,
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == 1) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "Đã cấp quyền", Toast.LENGTH_LONG).show()
//                startScanning()
//            } else {
//                Toast.makeText(this, "Chưa có quyền", Toast.LENGTH_LONG).show()
//            }
//        }
//    }
//
//    override fun onResume() {
//        super.onResume()
//        if (::codeScanner.isInitialized) {
//            codeScanner?.startPreview()
//        }
//    }
//
//    override fun onPause() {
//        if (::codeScanner.isInitialized) {
//            codeScanner?.releaseResources()
//        }
//        super.onPause()
//    }


}