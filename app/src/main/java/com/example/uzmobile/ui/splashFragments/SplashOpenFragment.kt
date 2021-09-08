package com.example.uzmobile.ui.splashFragments

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.uzmobile.MainActivity
import com.example.uzmobile.R
import com.example.uzmobile.databinding.FragmentSplashOpenBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class SplashOpenFragment : Fragment(R.layout.fragment_splash_open) {
    private val binding by viewBinding(FragmentSplashOpenBinding::bind)
    lateinit var sharedPreferences: SharedPreferences
    lateinit var handler:Handler
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireActivity().getSharedPreferences("Language",0)

        var language = sharedPreferences.getString("lan", "")
        handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            if(language==""){
                var bundle = Bundle()
                var navOptions = NavOptions.Builder()
                navOptions.setEnterAnim(R.anim.enter)
                navOptions.setExitAnim(R.anim.exit)
                navOptions.setPopEnterAnim(R.anim.pop_enter)
                navOptions.setPopExitAnim(R.anim.pop_exit)
                findNavController().navigate(R.id.languageFragment,bundle,navOptions.build())
            }else{
                var intent = Intent(requireContext(),MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        },1500)
    }
}