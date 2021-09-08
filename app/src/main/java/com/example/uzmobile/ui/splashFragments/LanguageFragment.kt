package com.example.uzmobile.ui.splashFragments

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentContainer
import com.example.uzmobile.MainActivity
import com.example.uzmobile.R
import com.example.uzmobile.databinding.FragmentLanguageBinding
import com.example.uzmobile.language.LocaleHelper
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding


class LanguageFragment : Fragment(R.layout.fragment_language) {
   private val binding by viewBinding(FragmentLanguageBinding::bind)
    lateinit var sharedPreferences: SharedPreferences
    @SuppressLint("CommitPrefEdits")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            sharedPreferences = requireActivity().getSharedPreferences("Language",0)
            uz.setOnClickListener {
                val edit = sharedPreferences.edit()
                edit.putString("lan","uz")
                LocaleHelper.setLocale(requireContext(),"uz")
                edit.apply()
                setIntent()
            }
            ru.setOnClickListener {
                val edit = sharedPreferences.edit()
                edit.putString("lan","ru")
                LocaleHelper.setLocale(requireContext(),"ru")
                edit.apply()
                setIntent()
            }
            cril.setOnClickListener {
                val edit = sharedPreferences.edit()
                edit.putString("lan","kril")
                LocaleHelper.setLocale(requireContext(),"en")
                edit.apply()
                setIntent()
            }



            var calback = object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                        (activity as AppCompatActivity).finish()
                }
            }
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, calback)

        }
    }

    private fun setIntent() {
        var intent = Intent(requireContext(),MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
}