package com.example.uzmobile.ui.operator

import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainer
import androidx.navigation.fragment.findNavController
import com.example.uzmobile.R
import com.example.uzmobile.adapters.operatorAdapter.Adapteroperator
import com.example.uzmobile.databinding.DialogOperatorBinding
import com.example.uzmobile.databinding.FragmentOperatorBinding
import com.example.uzmobile.language.LocaleHelper
import com.example.uzmobile.models.Operator
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OperatorFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OperatorFragment : Fragment(R.layout.fragment_operator) {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    private val binding by viewBinding(FragmentOperatorBinding::bind)
    lateinit var adapterOperator:Adapteroperator
    lateinit var firebaseFireStore:FirebaseFirestore
    lateinit var listOperators:ArrayList<Operator>
    lateinit var sharedPreferences: SharedPreferences
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listOperators = ArrayList()
        binding.apply {
            val onAttach = LocaleHelper.onAttach(requireContext())
            (activity as AppCompatActivity).supportActionBar?.title = onAttach.getText(R.string.operator_my)
            firebaseFireStore = FirebaseFirestore.getInstance()
            sharedPreferences = requireActivity().getSharedPreferences("Language",0)
            val language = sharedPreferences.getString("lan", "")
            adapterOperator = Adapteroperator(object:Adapteroperator.OnItemClickListener{
                override fun onItemClick(operator: Operator, position: Int) {
                    var alertDialog = AlertDialog.Builder(requireContext(),R.style.BottomSheetDialogThem)
                    val create = alertDialog.create()
                    var dialogOperatorBinding = DialogOperatorBinding.inflate(LayoutInflater.from(requireContext()),null,false)
                    create.setView(dialogOperatorBinding.root)

                    if (operator.code?.lowercase(Locale.getDefault())!="-".lowercase(Locale.getDefault())){
                        dialogOperatorBinding.phonCall.visibility = View.VISIBLE
                    }else{
                        dialogOperatorBinding.phonCall.visibility = View.GONE
                    }

                    dialogOperatorBinding.info.text = operator.information
                    dialogOperatorBinding.phoneNumber.text = operator.code
                    dialogOperatorBinding.clouse.setOnClickListener {
                        create.dismiss()
                    }
                    dialogOperatorBinding.phonCall.setOnClickListener {
                        var intent = Intent(Intent.ACTION_CALL)
                        intent.data = Uri.parse("tel:${operator.code}")
                        try {
                            startActivity(intent)
                        }catch (e: Exception){
                            e.printStackTrace()
                        }
                        create.dismiss()
                    }
                    create.show()

                }
            })
            when(language?.lowercase(Locale.getDefault())){
                "uz".lowercase(Locale.getDefault())->{
                    firebaseFireStore.collection("Operator").addSnapshotListener { value, error ->
                        value?.documentChanges?.forEach { documentChange ->
                            when(documentChange.type){
                                DocumentChange.Type.ADDED->{
                                    val operator = documentChange.document.toObject(Operator::class.java)
                                    listOperators.add(operator)
                                }
                            }
                        }
                        adapterOperator.submitList(listOperators)
                        rvMy.adapter = adapterOperator
                    }
                }
                "ru".lowercase(Locale.getDefault())->{
                    firebaseFireStore.collection("OperatorRu").addSnapshotListener { value, error ->
                        value?.documentChanges?.forEach { documentChange ->
                            when(documentChange.type){
                                DocumentChange.Type.ADDED->{
                                    val operator = documentChange.document.toObject(Operator::class.java)
                                    listOperators.add(operator)
                                }
                            }
                        }
                        adapterOperator.submitList(listOperators)
                        rvMy.adapter = adapterOperator
                    }
                }
                "kril".lowercase(Locale.getDefault())->{
                    firebaseFireStore.collection("OperatorCril").addSnapshotListener { value, error ->
                        value?.documentChanges?.forEach { documentChange ->
                            when(documentChange.type){
                                DocumentChange.Type.ADDED->{
                                    val operator = documentChange.document.toObject(Operator::class.java)
                                    listOperators.add(operator)
                                }
                            }
                        }
                        adapterOperator.submitList(listOperators)
                        rvMy.adapter = adapterOperator
                    }
                }
            }

        }
        var calback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
                findNavController().navigate(R.id.nav_home)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, calback)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OperatorFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OperatorFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}