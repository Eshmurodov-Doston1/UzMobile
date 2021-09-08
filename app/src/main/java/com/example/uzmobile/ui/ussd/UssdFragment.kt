package com.example.uzmobile.ui.ussd

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.uzmobile.R
import com.example.uzmobile.adapters.definitionsAdapter.DefinitionsAdapter
import com.example.uzmobile.adapters.ussdAdapter.UssdAdapter
import com.example.uzmobile.databinding.FragmentUssdBinding
import com.example.uzmobile.language.LocaleHelper
import com.example.uzmobile.models.Definitions
import com.example.uzmobile.models.USSD
import com.github.florent37.expansionpanel.ExpansionLayout
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList


class UssdFragment : Fragment(R.layout.fragment_ussd) {
    private val binding by viewBinding(FragmentUssdBinding::bind)
    lateinit var listUSSD:ArrayList<USSD>
    lateinit var listdefinitions:ArrayList<Definitions>
    lateinit var sharedPreferences: SharedPreferences
    lateinit var firebaseFirestore: FirebaseFirestore
    lateinit var ussdAdapter: UssdAdapter
    lateinit var definitionsAdapter: DefinitionsAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseFirestore = FirebaseFirestore.getInstance()
        val name = arguments?.getString("name", "")
        val name1 = arguments?.getString("name1", "")
        (activity as AppCompatActivity).supportActionBar?.title = name1

        val onAttach = LocaleHelper.onAttach(requireContext())
        sharedPreferences = requireActivity().getSharedPreferences("Language",0)
        binding.apply {
            listUSSD = ArrayList()
            listdefinitions= ArrayList()
            val language = sharedPreferences.getString("lan", "")
            when(name?.lowercase(Locale.getDefault())){
                "USSD".lowercase(Locale.getDefault())->{
                    when(language?.lowercase(Locale.getDefault())){
                        "uz".lowercase(Locale.getDefault())->{
                            firebaseFirestore.collection("Ussd").addSnapshotListener { value, error ->
                                value?.documentChanges?.forEach {
                                    when(it.type){
                                        DocumentChange.Type.ADDED->{
                                            val ussd = it.document.toObject(USSD::class.java)
                                            listUSSD.add(ussd)
                                        }
                                    }
                                }
                                ussdAdapter.submitList(listUSSD)
                                rv.adapter = ussdAdapter
                            }
                        }
                        "ru".lowercase(Locale.getDefault())->{
                            firebaseFirestore.collection("UssdRu").addSnapshotListener { value, error ->
                                value?.documentChanges?.forEach {
                                    when(it.type){
                                        DocumentChange.Type.ADDED->{
                                            val ussd = it.document.toObject(USSD::class.java)
                                            listUSSD.add(ussd)
                                        }
                                    }
                                }
                                ussdAdapter.submitList(listUSSD)
                                rv.adapter = ussdAdapter
                            }
                        }
                        "kril".lowercase(Locale.getDefault())->{
                            firebaseFirestore.collection("UssdCril").addSnapshotListener { value, error ->
                                value?.documentChanges?.forEach {
                                    when(it.type){
                                        DocumentChange.Type.ADDED->{
                                            val ussd = it.document.toObject(USSD::class.java)
                                            listUSSD.add(ussd)
                                        }
                                    }
                                }
                                ussdAdapter.submitList(listUSSD)
                                rv.adapter = ussdAdapter
                            }
                        }
                    }

                    ussdAdapter = UssdAdapter(requireContext(),object:UssdAdapter.OnItemClickListener{
                        override fun onItemClick(ussd: USSD, position: Int) {
                            val Num = ussd.code?.substring(0, ussd.code?.length?.minus(1)!!)+Uri.encode("#")
                            var intent = Intent(Intent.ACTION_CALL)
                            intent.data = Uri.parse("tel:$Num")
                            try {
                                startActivity(intent)
                            }catch (e: Exception){
                                e.printStackTrace()
                            }
                        }
                    })
                }
                "Tariflar".lowercase(Locale.getDefault())->{
                    definitionsAdapter = DefinitionsAdapter(object:DefinitionsAdapter.OnItemClickListener{
                        override fun onItemClick(definitions: Definitions, position: Int) {
                            var bundle = Bundle()
                            bundle.putSerializable("definitions",definitions)
                            findNavController().navigate(R.id.definitionFragment,bundle)
                        }
                    })
                    when(language?.lowercase(Locale.getDefault())){
                        "uz".lowercase(Locale.getDefault())->{
                            firebaseFirestore.collection("Definitions").addSnapshotListener { value, error ->
                                value?.documentChanges?.forEach {
                                    when(it.type){
                                        DocumentChange.Type.ADDED->{
                                            val definitions = it.document.toObject(Definitions::class.java)
                                            listdefinitions.add(definitions)
                                        }
                                    }
                                }
                                definitionsAdapter.submitList(listdefinitions)
                                rv.adapter = definitionsAdapter
                            }
                        }
                        "ru".lowercase(Locale.getDefault())->{
                            firebaseFirestore.collection("DefinitionsRu").addSnapshotListener { value, error ->
                                value?.documentChanges?.forEach {
                                    when(it.type){
                                        DocumentChange.Type.ADDED->{
                                            val definitions = it.document.toObject(Definitions::class.java)
                                            listdefinitions.add(definitions)
                                        }
                                    }
                                }
                                definitionsAdapter.submitList(listdefinitions)
                                rv.adapter = definitionsAdapter
                            }
                        }
                        "kril".lowercase(Locale.getDefault())->{
                            firebaseFirestore.collection("DefinitionsCril").addSnapshotListener { value, error ->
                                value?.documentChanges?.forEach {
                                    when(it.type){
                                        DocumentChange.Type.ADDED->{
                                            val definitions = it.document.toObject(Definitions::class.java)
                                            listdefinitions.add(definitions)
                                        }
                                    }
                                }
                                definitionsAdapter.submitList(listdefinitions)
                                rv.adapter = definitionsAdapter
                            }
                        }
                    }
                }
            }


        }
    }
}