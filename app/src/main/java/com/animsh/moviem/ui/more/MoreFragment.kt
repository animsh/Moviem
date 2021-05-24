package com.animsh.moviem.ui.more

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.animsh.moviem.R
import com.animsh.moviem.databinding.FragmentMoreBinding
import kotlinx.android.synthetic.main.fragment_more.*

class MoreFragment : Fragment() {

    private var _binding: FragmentMoreBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoreBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            themeLayout.setOnClickListener {
                showAlertDialog()
            }

            aboutLayout.setOnClickListener {
                val openBottomSheet: AboutBottomSheetFragment =
                    AboutBottomSheetFragment().newInstance()
                openBottomSheet.show(childFragmentManager, AboutBottomSheetFragment.TAG)
            }

            val sharedPreference =
                binding!!.root.context.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
            when (sharedPreference.getString("theme", "system default")) {
                "light" -> theme.text = "Light"
                "dark" -> theme.text = "Dark"
                "system default" -> theme.text = "System Default"
            }

            val manager = requireContext().packageManager
            val info = manager?.getPackageInfo(
                requireContext().packageName, 0
            )
            val versionName = info?.versionName
            val versionNumber = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                info?.longVersionCode
            } else {
                info?.versionCode
            }

            val appVersionTxt = "Moviem : $versionName ($versionNumber)"
            app_version.text = appVersionTxt
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showAlertDialog() {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(
            requireContext(),
            R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Background
        ).setTitle("Theme")
        val items = arrayOf("Light", "Dark", "System Default")
        val sharedPreference =
            binding!!.root.context.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        var checkedItem = 2
        when (sharedPreference.getString("theme", "system default")) {
            "light" -> checkedItem = 0
            "dark" -> checkedItem = 1
            "system default" -> checkedItem = 2
        }
        alertDialog.setSingleChoiceItems(
            items, checkedItem
        ) { dialog, which ->
            when (which) {
                0 -> {
                    dialog.dismiss()
                    editor.putString("theme", "light")
                    editor.apply()
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }

                1 -> {
                    dialog.dismiss()
                    editor.putString("theme", "dark")
                    editor.apply()
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }

                2 -> {
                    dialog.dismiss()
                    editor.putString("theme", "system default")
                    editor.apply()
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                }
            }
        }
        val alert: AlertDialog = alertDialog.create()
        alert.show()
    }


}