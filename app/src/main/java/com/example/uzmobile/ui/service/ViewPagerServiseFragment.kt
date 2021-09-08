package com.example.uzmobile.ui.service

import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentContainer
import com.example.uzmobile.R
import com.example.uzmobile.adapters.serviceAdapter.*
import com.example.uzmobile.databinding.DialogCountryBinding
import com.example.uzmobile.databinding.DialogServiceBinding
import com.example.uzmobile.databinding.FragmentViewPagerServiseBinding
import com.example.uzmobile.language.LocaleHelper
import com.example.uzmobile.models.*
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.min

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ViewPagerServiseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ViewPagerServiseFragment : Fragment(R.layout.fragment_view_pager_servise) {
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

    private val binding by viewBinding(FragmentViewPagerServiseBinding::bind)
    lateinit var rvAdapterService: RvAdapterService
    lateinit var listService:ArrayList<Service>
    lateinit var sharedPreferences: SharedPreferences
    lateinit var firebaseFirestore: FirebaseFirestore
    lateinit var smsAdapter:SMSAdapter
    lateinit var minutAdapter: MinutAdapter
    lateinit var listSms:ArrayList<SMSMoney>
    lateinit var replaceAdapter:ReplaceAdapter
    lateinit var listMinut:ArrayList<Minut>
    lateinit var listInternet:ArrayList<Internet>
    lateinit var listSMS:ArrayList<SMS>
    lateinit var listNonstop:ArrayList<NonStop>
    lateinit var rvInternet: RvInternet
    lateinit var rvNonStop: RvNonStop
    lateinit var rvSMS: RvSMS
    lateinit var rvSMSCountry: RvSMSCountry
    lateinit var listSMSCountry:ArrayList<SMSCountry>
    lateinit var listreplaceMent:ArrayList<Replacement>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            sharedPreferences = requireActivity().getSharedPreferences("Language",0)
            val language = sharedPreferences.getString("lan", "")
            firebaseFirestore = FirebaseFirestore.getInstance()
            val onAttach = LocaleHelper.onAttach(requireContext())
            when(param2?.lowercase(Locale.getDefault())){
                "Xizmatlar".lowercase(Locale.getDefault())->{
                    when(language?.lowercase(Locale.getDefault())){
                        "uz".lowercase(Locale.getDefault())->{
                            when(param1?.lowercase(Locale.getDefault())){
                                "Xizmatlar".lowercase(Locale.getDefault())->{
                                    listService = ArrayList()
                                    rvAdapterService = RvAdapterService(object:RvAdapterService.OnItemClickListener{
                                        override fun onItemClick(servicew: Service, position: Int) {
                                            var alertDialog = AlertDialog.Builder(requireContext(),R.style.BottomSheetDialogThem)
                                            val create = alertDialog.create()
                                            var dialogServiceBinding = DialogServiceBinding.inflate(LayoutInflater.from(requireContext()),null,false)
                                            create.setView(dialogServiceBinding.root)
                                            dialogServiceBinding.active.text = onAttach.getText(R.string.aktivlashtirish)
                                            dialogServiceBinding.clouse.text = onAttach.getString(R.string.orqaga)
                                            dialogServiceBinding.active.setOnClickListener {
                                                val substring = servicew.onCode?.substring(servicew.onCode?.length!! - 1)
                                                var Num:String
                                                if (substring=="#"){
                                                    Num = servicew.onCode?.substring(0, servicew.onCode?.length?.minus(1)!!)+ Uri.encode("#")
                                                }else{
                                                    Num = servicew.onCode?.substring(0, servicew.onCode?.length?.minus(2)!!)+ Uri.encode("#")
                                                }
                                                var intent = Intent(Intent.ACTION_CALL)
                                                intent.data = Uri.parse("tel:$Num")
                                                try {
                                                    startActivity(intent)
                                                }catch (e: Exception){
                                                    e.printStackTrace()
                                                }

                                            }
                                            dialogServiceBinding.clouse.setOnClickListener {
                                                create.dismiss()
                                            }
                                            dialogServiceBinding.share.setOnClickListener {
                                                var shareIntent = Intent().apply {
                                                    this.action = Intent.ACTION_SEND
                                                    this.putExtra(Intent.EXTRA_SUBJECT,"Uzmobile-${servicew.onCode}\n${servicew.name}")
                                                    this.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id=uz.pdp.uzmobile")
                                                    this.type="text/plain"
                                                }
                                                startActivity(shareIntent)
                                            }
                                            dialogServiceBinding.info.text = servicew.info1
                                            dialogServiceBinding.name.text = servicew.name
                                            create.show()
                                        }
                                    })
                                            firebaseFirestore.collection("Service").addSnapshotListener { value, error ->
                                                value?.documentChanges?.forEach {
                                                    when(it.type){
                                                        DocumentChange.Type.ADDED->{
                                                            val service = it.document.toObject(Service::class.java)
                                                            listService.add(service)
                                                        }
                                                    }
                                                }
                                                rvAdapterService.submitList(listService)
                                                rvService.adapter = rvAdapterService
                                            }
                                }
                                "Pullik SMSlar".lowercase(Locale.getDefault())->{
                                    listSms = ArrayList()
                                    smsAdapter = SMSAdapter(object:SMSAdapter.OnItemClickListener{
                                        override fun onItemClick(smsMoney: SMSMoney, position: Int) {
                                            var alertDialog = AlertDialog.Builder(requireContext(),R.style.BottomSheetDialogThem)
                                            val create = alertDialog.create()
                                            var dialogServiceBinding = DialogServiceBinding.inflate(LayoutInflater.from(requireContext()),null,false)
                                            create.setView(dialogServiceBinding.root)
                                            dialogServiceBinding.active.text = onAttach.getText(R.string.aktivlashtirish)
                                            dialogServiceBinding.clouse.text = onAttach.getString(R.string.orqaga)
                                            dialogServiceBinding.active.setOnClickListener {
                                                val substring = smsMoney.code?.trim()?.substring(smsMoney.code?.trim()?.length!! - 1)
                                                var Num:String
                                                if (substring=="#"){
                                                    Num = smsMoney.code?.trim()?.substring(0, smsMoney.code?.trim()?.length?.minus(1)!!)+ Uri.encode("#")
                                                }else{
                                                    Num = smsMoney.code?.trim()?.substring(0, smsMoney.code?.trim()?.length?.minus(2)!!)+ Uri.encode("#")
                                                }
                                                var intent = Intent(Intent.ACTION_CALL)
                                                intent.data = Uri.parse("tel:$Num")
                                                try {
                                                    startActivity(intent)
                                                }catch (e: Exception){
                                                    e.printStackTrace()
                                                }

                                            }
                                            dialogServiceBinding.clouse.setOnClickListener {
                                                create.dismiss()
                                            }
                                            dialogServiceBinding.share.setOnClickListener {
                                                var shareIntent = Intent().apply {
                                                    this.action = Intent.ACTION_SEND
                                                    this.putExtra(Intent.EXTRA_SUBJECT,"Uzmobile-${smsMoney.code?.trim()}\n${smsMoney.name?.trim()}")
                                                    this.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id=uz.pdp.uzmobile")
                                                    this.type="text/plain"
                                                }
                                                startActivity(shareIntent)
                                            }
                                            dialogServiceBinding.info.text = smsMoney.info?.trim()
                                            dialogServiceBinding.name.text = smsMoney.name?.trim()
                                            create.show()
                                        }
                                    })
                                            firebaseFirestore.collection("SMSMoney").addSnapshotListener { value, error ->
                                                value?.documentChanges?.forEach {
                                                    when(it.type){
                                                        DocumentChange.Type.ADDED->{
                                                            val smsMoney = it.document.toObject(SMSMoney::class.java)
                                                            listSms.add(smsMoney)
                                                        }
                                                    }
                                                }
                                                smsAdapter.submitList(listSms)
                                                rvService.adapter = smsAdapter
                                            }
                                }
                                "Rouming".lowercase(Locale.getDefault())->{
                                    listSms = ArrayList()
                                    smsAdapter = SMSAdapter(object:SMSAdapter.OnItemClickListener{
                                        override fun onItemClick(smsMoney: SMSMoney, position: Int) {
                                            var alertDialog = AlertDialog.Builder(requireContext(),R.style.BottomSheetDialogThem)
                                            val create = alertDialog.create()
                                            var dialogServiceBinding = DialogServiceBinding.inflate(LayoutInflater.from(requireContext()),null,false)
                                            create.setView(dialogServiceBinding.root)
                                            dialogServiceBinding.active.text = onAttach.getText(R.string.aktivlashtirish)
                                            dialogServiceBinding.clouse.text = onAttach.getString(R.string.orqaga)
                                            dialogServiceBinding.active.setOnClickListener {
                                                val substring = smsMoney.code?.trim()?.substring(smsMoney.code?.trim()?.length!! - 1)
                                                var Num:String
                                                if (substring=="#"){
                                                    Num = smsMoney.code?.trim()?.substring(0, smsMoney.code?.trim()?.length?.minus(1)!!)+ Uri.encode("#")
                                                }else{
                                                    Num = smsMoney.code?.trim()?.substring(0, smsMoney.code?.trim()?.length?.minus(2)!!)+ Uri.encode("#")
                                                }
                                                var intent = Intent(Intent.ACTION_CALL)
                                                intent.data = Uri.parse("tel:$Num")
                                                try {
                                                    startActivity(intent)
                                                }catch (e: Exception){
                                                    e.printStackTrace()
                                                }

                                            }
                                            dialogServiceBinding.clouse.setOnClickListener {
                                                create.dismiss()
                                            }
                                            dialogServiceBinding.share.setOnClickListener {
                                                var shareIntent = Intent().apply {
                                                    this.action = Intent.ACTION_SEND
                                                    this.putExtra(Intent.EXTRA_SUBJECT,"Uzmobile-${smsMoney.code?.trim()}\n${smsMoney.name?.trim()}")
                                                    this.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id=uz.pdp.uzmobile")
                                                    this.type="text/plain"
                                                }
                                                startActivity(shareIntent)
                                            }
                                            dialogServiceBinding.info.text = smsMoney.info?.trim()
                                            dialogServiceBinding.name.text = smsMoney.name?.trim()
                                            create.show()
                                        }
                                    })
                                            firebaseFirestore.collection("Rouming").addSnapshotListener { value, error ->
                                                value?.documentChanges?.forEach {
                                                    when(it.type){
                                                        DocumentChange.Type.ADDED->{
                                                            val smsMoney = it.document.toObject(SMSMoney::class.java)
                                                            listSms.add(smsMoney)
                                                        }
                                                    }
                                                }
                                                smsAdapter.submitList(listSms)
                                                rvService.adapter = smsAdapter
                                            }
                                }
                            }
                        }
                        "ru".lowercase(Locale.getDefault())->{
                            when(param1?.lowercase(Locale.getDefault())){
                                "Услуги".lowercase(Locale.getDefault())->{
                                    listService = ArrayList()
                                    rvAdapterService = RvAdapterService(object:RvAdapterService.OnItemClickListener{
                                        override fun onItemClick(servicew: Service, position: Int) {
                                            var alertDialog = AlertDialog.Builder(requireContext(),R.style.BottomSheetDialogThem)
                                            val create = alertDialog.create()
                                            var dialogServiceBinding = DialogServiceBinding.inflate(LayoutInflater.from(requireContext()),null,false)
                                            create.setView(dialogServiceBinding.root)
                                            dialogServiceBinding.active.text = onAttach.getText(R.string.aktivlashtirish)
                                            dialogServiceBinding.clouse.text = onAttach.getString(R.string.orqaga)
                                            dialogServiceBinding.active.setOnClickListener {
                                                val substring = servicew.onCode?.substring(servicew.onCode?.length!! - 1)
                                                var Num:String
                                                if (substring=="#"){
                                                    Num = servicew.onCode?.substring(0, servicew.onCode?.length?.minus(1)!!)+ Uri.encode("#")
                                                }else{
                                                    Num = servicew.onCode?.substring(0, servicew.onCode?.length?.minus(2)!!)+ Uri.encode("#")
                                                }
                                                var intent = Intent(Intent.ACTION_CALL)
                                                intent.data = Uri.parse("tel:$Num")
                                                try {
                                                    startActivity(intent)
                                                }catch (e: Exception){
                                                    e.printStackTrace()
                                                }

                                            }
                                            dialogServiceBinding.clouse.setOnClickListener {
                                                create.dismiss()
                                            }
                                            dialogServiceBinding.share.setOnClickListener {
                                                var shareIntent = Intent().apply {
                                                    this.action = Intent.ACTION_SEND
                                                    this.putExtra(Intent.EXTRA_SUBJECT,"Uzmobile-${servicew.onCode}\n${servicew.name}")
                                                    this.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id=uz.pdp.uzmobile")
                                                    this.type="text/plain"
                                                }
                                                startActivity(shareIntent)
                                            }
                                            dialogServiceBinding.info.text = servicew.info1
                                            dialogServiceBinding.name.text = servicew.name
                                            create.show()
                                        }
                                    })
                                            firebaseFirestore.collection("ServiceRu").addSnapshotListener { value, error ->
                                                value?.documentChanges?.forEach {
                                                    when(it.type){
                                                        DocumentChange.Type.ADDED->{
                                                            val service = it.document.toObject(Service::class.java)
                                                            listService.add(service)
                                                        }
                                                    }
                                                }
                                                rvAdapterService.submitList(listService)
                                                rvService.adapter = rvAdapterService
                                            }
                                }
                                "Платные Смс".lowercase(Locale.getDefault())->{
                                    listSms = ArrayList()
                                    smsAdapter = SMSAdapter(object:SMSAdapter.OnItemClickListener{
                                        override fun onItemClick(smsMoney: SMSMoney, position: Int) {
                                            var alertDialog = AlertDialog.Builder(requireContext(),R.style.BottomSheetDialogThem)
                                            val create = alertDialog.create()
                                            var dialogServiceBinding = DialogServiceBinding.inflate(LayoutInflater.from(requireContext()),null,false)
                                            create.setView(dialogServiceBinding.root)
                                            dialogServiceBinding.active.text = onAttach.getText(R.string.aktivlashtirish)
                                            dialogServiceBinding.clouse.text = onAttach.getString(R.string.orqaga)
                                            dialogServiceBinding.active.setOnClickListener {
                                                val substring = smsMoney.code?.trim()?.substring(smsMoney.code?.trim()?.length!! - 1)
                                                var Num:String
                                                if (substring=="#"){
                                                    Num = smsMoney.code?.trim()?.substring(0, smsMoney.code?.trim()?.length?.minus(1)!!)+ Uri.encode("#")
                                                }else{
                                                    Num = smsMoney.code?.trim()?.substring(0, smsMoney.code?.trim()?.length?.minus(2)!!)+ Uri.encode("#")
                                                }
                                                var intent = Intent(Intent.ACTION_CALL)
                                                intent.data = Uri.parse("tel:$Num")
                                                try {
                                                    startActivity(intent)
                                                }catch (e: Exception){
                                                    e.printStackTrace()
                                                }

                                            }
                                            dialogServiceBinding.clouse.setOnClickListener {
                                                create.dismiss()
                                            }
                                            dialogServiceBinding.share.setOnClickListener {
                                                var shareIntent = Intent().apply {
                                                    this.action = Intent.ACTION_SEND
                                                    this.putExtra(Intent.EXTRA_SUBJECT,"Uzmobile-${smsMoney.code?.trim()}\n${smsMoney.name?.trim()}")
                                                    this.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id=uz.pdp.uzmobile")
                                                    this.type="text/plain"
                                                }
                                                startActivity(shareIntent)
                                            }
                                            dialogServiceBinding.info.text = smsMoney.info?.trim()
                                            dialogServiceBinding.name.text = smsMoney.name?.trim()
                                            create.show()
                                        }
                                    })
                                            firebaseFirestore.collection("SMSMoneyRu").addSnapshotListener { value, error ->
                                                value?.documentChanges?.forEach {
                                                    when(it.type){
                                                        DocumentChange.Type.ADDED->{
                                                            val smsMoney = it.document.toObject(SMSMoney::class.java)
                                                            listSms.add(smsMoney)
                                                        }
                                                    }
                                                }
                                                smsAdapter.submitList(listSms)
                                                rvService.adapter = smsAdapter
                                            }

                                }
                                "Роуминг".lowercase(Locale.getDefault())->{
                                    listSms = ArrayList()
                                    smsAdapter = SMSAdapter(object:SMSAdapter.OnItemClickListener{
                                        override fun onItemClick(smsMoney: SMSMoney, position: Int) {
                                            var alertDialog = AlertDialog.Builder(requireContext(),R.style.BottomSheetDialogThem)
                                            val create = alertDialog.create()
                                            var dialogServiceBinding = DialogServiceBinding.inflate(LayoutInflater.from(requireContext()),null,false)
                                            create.setView(dialogServiceBinding.root)
                                            dialogServiceBinding.active.text = onAttach.getText(R.string.aktivlashtirish)
                                            dialogServiceBinding.clouse.text = onAttach.getString(R.string.orqaga)
                                            dialogServiceBinding.active.setOnClickListener {
                                                val substring = smsMoney.code?.trim()?.substring(smsMoney.code?.trim()?.length!! - 1)
                                                var Num:String
                                                if (substring=="#"){
                                                    Num = smsMoney.code?.trim()?.substring(0, smsMoney.code?.trim()?.length?.minus(1)!!)+ Uri.encode("#")
                                                }else{
                                                    Num = smsMoney.code?.trim()?.substring(0, smsMoney.code?.trim()?.length?.minus(2)!!)+ Uri.encode("#")
                                                }
                                                var intent = Intent(Intent.ACTION_CALL)
                                                intent.data = Uri.parse("tel:$Num")
                                                try {
                                                    startActivity(intent)
                                                }catch (e: Exception){
                                                    e.printStackTrace()
                                                }

                                            }
                                            dialogServiceBinding.clouse.setOnClickListener {
                                                create.dismiss()
                                            }
                                            dialogServiceBinding.share.setOnClickListener {
                                                var shareIntent = Intent().apply {
                                                    this.action = Intent.ACTION_SEND
                                                    this.putExtra(Intent.EXTRA_SUBJECT,"Uzmobile-${smsMoney.code?.trim()}\n${smsMoney.name?.trim()}")
                                                    this.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id=uz.pdp.uzmobile")
                                                    this.type="text/plain"
                                                }
                                                startActivity(shareIntent)
                                            }
                                            dialogServiceBinding.info.text = smsMoney.info?.trim()
                                            dialogServiceBinding.name.text = smsMoney.name?.trim()
                                            create.show()
                                        }
                                    })
                                            firebaseFirestore.collection("RoumingRu").addSnapshotListener { value, error ->
                                                value?.documentChanges?.forEach {
                                                    when(it.type){
                                                        DocumentChange.Type.ADDED->{
                                                            val smsMoney = it.document.toObject(SMSMoney::class.java)
                                                            listSms.add(smsMoney)
                                                        }
                                                    }
                                                }
                                                smsAdapter.submitList(listSms)
                                                rvService.adapter = smsAdapter
                                            }
                                }
                            }
                        }
                        "kril".lowercase(Locale.getDefault())->{
                            when(param1?.lowercase(Locale.getDefault())){
                                "Хизматлар".lowercase(Locale.getDefault())->{
                                    listService = ArrayList()
                                    rvAdapterService = RvAdapterService(object:RvAdapterService.OnItemClickListener{
                                        override fun onItemClick(servicew: Service, position: Int) {
                                            var alertDialog = AlertDialog.Builder(requireContext(),R.style.BottomSheetDialogThem)
                                            val create = alertDialog.create()
                                            var dialogServiceBinding = DialogServiceBinding.inflate(LayoutInflater.from(requireContext()),null,false)
                                            create.setView(dialogServiceBinding.root)
                                            dialogServiceBinding.clouse.text = onAttach.getString(R.string.orqaga)
                                            dialogServiceBinding.active.text = onAttach.getText(R.string.aktivlashtirish)
                                            dialogServiceBinding.active.setOnClickListener {
                                                val substring = servicew.onCode?.substring(servicew.onCode?.length!! - 1)
                                                var Num:String
                                                if (substring=="#"){
                                                    Num = servicew.onCode?.substring(0, servicew.onCode?.length?.minus(1)!!)+ Uri.encode("#")
                                                }else{
                                                    Num = servicew.onCode?.substring(0, servicew.onCode?.length?.minus(2)!!)+ Uri.encode("#")
                                                }
                                                var intent = Intent(Intent.ACTION_CALL)
                                                intent.data = Uri.parse("tel:$Num")
                                                try {
                                                    startActivity(intent)
                                                }catch (e: Exception){
                                                    e.printStackTrace()
                                                }

                                            }
                                            dialogServiceBinding.clouse.setOnClickListener {
                                                create.dismiss()
                                            }
                                            dialogServiceBinding.share.setOnClickListener {
                                                var shareIntent = Intent().apply {
                                                    this.action = Intent.ACTION_SEND
                                                    this.putExtra(Intent.EXTRA_SUBJECT,"Uzmobile-${servicew.onCode}\n${servicew.name}")
                                                    this.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id=uz.pdp.uzmobile")
                                                    this.type="text/plain"
                                                }
                                                startActivity(shareIntent)
                                            }
                                            dialogServiceBinding.info.text = servicew.info1
                                            dialogServiceBinding.name.text = servicew.name
                                            create.show()
                                        }
                                    })
                                            firebaseFirestore.collection("ServiceCril").addSnapshotListener { value, error ->
                                                value?.documentChanges?.forEach {
                                                    when(it.type){
                                                        DocumentChange.Type.ADDED->{
                                                            val service = it.document.toObject(Service::class.java)
                                                            listService.add(service)
                                                        }
                                                    }
                                                }
                                                rvAdapterService.submitList(listService)
                                                rvService.adapter = rvAdapterService
                                            }
                                }
                                "Пуллик СМСлар".lowercase(Locale.getDefault())->{
                                    listSms = ArrayList()
                                    smsAdapter = SMSAdapter(object:SMSAdapter.OnItemClickListener{
                                        override fun onItemClick(smsMoney: SMSMoney, position: Int) {
                                            var alertDialog = AlertDialog.Builder(requireContext(),R.style.BottomSheetDialogThem)
                                            val create = alertDialog.create()
                                            var dialogServiceBinding = DialogServiceBinding.inflate(LayoutInflater.from(requireContext()),null,false)
                                            create.setView(dialogServiceBinding.root)
                                            dialogServiceBinding.clouse.text = onAttach.getString(R.string.orqaga)
                                            dialogServiceBinding.active.text = onAttach.getText(R.string.aktivlashtirish)
                                            dialogServiceBinding.active.setOnClickListener {
                                                val substring = smsMoney.code?.trim()?.substring(smsMoney.code?.trim()?.length!! - 1)
                                                var Num:String
                                                if (substring=="#"){
                                                    Num = smsMoney.code?.trim()?.substring(0, smsMoney.code?.trim()?.length?.minus(1)!!)+ Uri.encode("#")
                                                }else{
                                                    Num = smsMoney.code?.trim()?.substring(0, smsMoney.code?.trim()?.length?.minus(2)!!)+ Uri.encode("#")
                                                }
                                                var intent = Intent(Intent.ACTION_CALL)
                                                intent.data = Uri.parse("tel:$Num")
                                                try {
                                                    startActivity(intent)
                                                }catch (e: Exception){
                                                    e.printStackTrace()
                                                }

                                            }
                                            dialogServiceBinding.clouse.setOnClickListener {
                                                create.dismiss()
                                            }
                                            dialogServiceBinding.share.setOnClickListener {
                                                var shareIntent = Intent().apply {
                                                    this.action = Intent.ACTION_SEND
                                                    this.putExtra(Intent.EXTRA_SUBJECT,"Uzmobile-${smsMoney.code?.trim()}\n${smsMoney.name?.trim()}")
                                                    this.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id=uz.pdp.uzmobile")
                                                    this.type="text/plain"
                                                }
                                                startActivity(shareIntent)
                                            }
                                            dialogServiceBinding.info.text = smsMoney.info?.trim()
                                            dialogServiceBinding.name.text = smsMoney.name?.trim()
                                            create.show()
                                        }
                                    })
                                            firebaseFirestore.collection("SMSMoneyCril").addSnapshotListener { value, error ->
                                                value?.documentChanges?.forEach {
                                                    when(it.type){
                                                        DocumentChange.Type.ADDED->{
                                                            val smsMoney = it.document.toObject(SMSMoney::class.java)
                                                            listSms.add(smsMoney)
                                                        }
                                                    }
                                                }
                                                smsAdapter.submitList(listSms)
                                                rvService.adapter = smsAdapter
                                            }
                                }
                                "Роуминг".lowercase(Locale.getDefault())->{
                                    listSms = ArrayList()
                                    smsAdapter = SMSAdapter(object:SMSAdapter.OnItemClickListener{
                                        override fun onItemClick(smsMoney: SMSMoney, position: Int) {
                                            var alertDialog = AlertDialog.Builder(requireContext(),R.style.BottomSheetDialogThem)
                                            val create = alertDialog.create()
                                            var dialogServiceBinding = DialogServiceBinding.inflate(LayoutInflater.from(requireContext()),null,false)
                                            create.setView(dialogServiceBinding.root)
                                            dialogServiceBinding.clouse.text = onAttach.getString(R.string.orqaga)
                                            dialogServiceBinding.active.text = onAttach.getText(R.string.aktivlashtirish)
                                            dialogServiceBinding.active.setOnClickListener {
                                                val substring = smsMoney.code?.trim()?.substring(smsMoney.code?.trim()?.length!! - 1)
                                                var Num:String
                                                if (substring=="#"){
                                                    Num = smsMoney.code?.trim()?.substring(0, smsMoney.code?.trim()?.length?.minus(1)!!)+ Uri.encode("#")
                                                }else{
                                                    Num = smsMoney.code?.trim()?.substring(0, smsMoney.code?.trim()?.length?.minus(2)!!)+ Uri.encode("#")
                                                }
                                                var intent = Intent(Intent.ACTION_CALL)
                                                intent.data = Uri.parse("tel:$Num")
                                                try {
                                                    startActivity(intent)
                                                }catch (e: Exception){
                                                    e.printStackTrace()
                                                }

                                            }
                                            dialogServiceBinding.clouse.setOnClickListener {
                                                create.dismiss()
                                            }
                                            dialogServiceBinding.share.setOnClickListener {
                                                var shareIntent = Intent().apply {
                                                    this.action = Intent.ACTION_SEND
                                                    this.putExtra(Intent.EXTRA_SUBJECT,"Uzmobile-${smsMoney.code?.trim()}\n${smsMoney.name?.trim()}")
                                                    this.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id=uz.pdp.uzmobile")
                                                    this.type="text/plain"
                                                }
                                                startActivity(shareIntent)
                                            }
                                            dialogServiceBinding.info.text = smsMoney.info?.trim()
                                            dialogServiceBinding.name.text = smsMoney.name?.trim()
                                            create.show()
                                        }
                                    })
                                            firebaseFirestore.collection("RoumingCril").addSnapshotListener { value, error ->
                                                value?.documentChanges?.forEach {
                                                    when(it.type){
                                                        DocumentChange.Type.ADDED->{
                                                            val smsMoney = it.document.toObject(SMSMoney::class.java)
                                                            listSms.add(smsMoney)
                                                        }
                                                    }
                                                }
                                                smsAdapter.submitList(listSms)
                                                rvService.adapter = smsAdapter
                                            }
                                }
                            }
                        }

                    }
                }
                "Daqiqa".lowercase(Locale.getDefault())->{
                    when(language?.lowercase(Locale.getDefault())){
                        "uz".lowercase(Locale.getDefault())->{
                            when(param1?.lowercase(Locale.getDefault())){
                                "Daqiqa\nto'plamlari".lowercase(Locale.getDefault())->{
                                    listMinut = ArrayList()
                                    minutAdapter = MinutAdapter(object:MinutAdapter.OnItemClickListener{
                                        override fun onItemClick(smsMoney: Minut, position: Int) {
                                            var alertDialog = AlertDialog.Builder(requireContext(),R.style.BottomSheetDialogThem)
                                            val create = alertDialog.create()
                                            var dialogServiceBinding = DialogServiceBinding.inflate(LayoutInflater.from(requireContext()),null,false)
                                            create.setView(dialogServiceBinding.root)
                                            dialogServiceBinding.clouse.text = onAttach.getString(R.string.orqaga)
                                            dialogServiceBinding.active.text = onAttach.getText(R.string.aktivlashtirish)
                                            dialogServiceBinding.active.setOnClickListener {
                                                val substring = smsMoney.code?.trim()?.substring(smsMoney.code?.trim()?.length!! - 1)
                                                var Num:String
                                                if (substring=="#"){
                                                    Num = smsMoney.code?.trim()?.substring(0, smsMoney.code?.trim()?.length?.minus(1)!!)+ Uri.encode("#")
                                                }else{
                                                    Num = smsMoney.code?.trim()?.substring(0, smsMoney.code?.trim()?.length?.minus(2)!!)+ Uri.encode("#")
                                                }
                                                var intent = Intent(Intent.ACTION_CALL)
                                                intent.data = Uri.parse("tel:$Num")
                                                try {
                                                    startActivity(intent)
                                                }catch (e: Exception){
                                                    e.printStackTrace()
                                                }

                                            }
                                            dialogServiceBinding.clouse.setOnClickListener {
                                                create.dismiss()
                                            }
                                            dialogServiceBinding.share.setOnClickListener {
                                                var shareIntent = Intent().apply {
                                                    this.action = Intent.ACTION_SEND
                                                    this.putExtra(Intent.EXTRA_SUBJECT,"Uzmobile-${smsMoney.code?.trim()}\n${smsMoney.name?.trim()}")
                                                    this.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id=uz.pdp.uzmobile")
                                                    this.type="text/plain"
                                                }
                                                startActivity(shareIntent)
                                            }
                                            dialogServiceBinding.info.text = "${smsMoney.summ}\n${smsMoney.count_min?.trim()}\n${smsMoney.day_month}\n${smsMoney.onCode?.trim()}"
                                            dialogServiceBinding.name.text = smsMoney.name?.trim()
                                            create.show()
                                        }
                                    })
                                            firebaseFirestore.collection("Minutes").addSnapshotListener { value, error ->
                                                value?.documentChanges?.forEach {
                                                    when(it.type){
                                                        DocumentChange.Type.ADDED->{
                                                            val minut = it.document.toObject(Minut::class.java)
                                                            listMinut.add(minut)
                                                        }
                                                    }
                                                }
                                                minutAdapter.submitList(listMinut)
                                                rvService.adapter = minutAdapter
                                            }
                                }
                                "Foydali\nalmashtiruv".lowercase(Locale.getDefault())->{
                                    listreplaceMent = ArrayList()
                                    replaceAdapter = ReplaceAdapter(object:ReplaceAdapter.OnItemClickListener{
                                        override fun onItemClick(replacement: Replacement, position: Int) {
                                            var alertDialog = AlertDialog.Builder(requireContext(),R.style.BottomSheetDialogThem)
                                            val create = alertDialog.create()
                                            var dialogServiceBinding = DialogServiceBinding.inflate(LayoutInflater.from(requireContext()),null,false)
                                            create.setView(dialogServiceBinding.root)
                                            dialogServiceBinding.clouse.text = onAttach.getString(R.string.orqaga)
                                            dialogServiceBinding.active.text = onAttach.getText(R.string.aktivlashtirish)
                                            dialogServiceBinding.active.setOnClickListener {
                                                val substring = replacement.code?.trim()?.substring(replacement.code?.trim()?.length!! - 1)
                                                var Num:String
                                                if (substring=="#"){
                                                    Num = replacement.code?.trim()?.substring(0, replacement.code?.trim()?.length?.minus(1)!!)+ Uri.encode("#")
                                                }else{
                                                    Num = replacement.code?.substring(0, replacement.code?.trim()?.length?.minus(2)!!)+ Uri.encode("#")
                                                }
                                                var intent = Intent(Intent.ACTION_CALL)
                                                intent.data = Uri.parse("tel:$Num")
                                                try {
                                                    startActivity(intent)
                                                }catch (e: Exception){
                                                    e.printStackTrace()
                                                }

                                            }
                                            dialogServiceBinding.clouse.setOnClickListener {
                                                create.dismiss()
                                            }
                                            dialogServiceBinding.share.setOnClickListener {
                                                var shareIntent = Intent().apply {
                                                    this.action = Intent.ACTION_SEND
                                                    this.putExtra(Intent.EXTRA_SUBJECT,"Uzmobile-${replacement.code?.trim()}\n${replacement.name?.trim()}")
                                                    this.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id=uz.pdp.uzmobile")
                                                    this.type="text/plain"
                                                }
                                                startActivity(shareIntent)
                                            }
                                            dialogServiceBinding.info.text = "${replacement.getMinut?.trim()}\n${replacement.setMinut?.trim()}\n${replacement.price?.trim()}\n${replacement.day?.trim()}\n${replacement.onCodetext?.trim()}"?.trim()
                                            dialogServiceBinding.name.text = replacement.name?.trim()
                                            create.show()
                                        }
                                    })
                                            firebaseFirestore.collection("Replacement").addSnapshotListener { value, error ->
                                                value?.documentChanges?.forEach {
                                                    when(it.type){
                                                        DocumentChange.Type.ADDED->{
                                                            val replaceMent = it.document.toObject(Replacement::class.java)
                                                            listreplaceMent.add(replaceMent)
                                                        }
                                                    }
                                                }
                                                replaceAdapter.submitList(listreplaceMent)
                                                rvService.adapter = replaceAdapter
                                            }
                                }
                                "Constructor\nabonentlari".lowercase(Locale.getDefault())->{
                                    listMinut = ArrayList()
                                    minutAdapter = MinutAdapter(object:MinutAdapter.OnItemClickListener{
                                        override fun onItemClick(smsMoney: Minut, position: Int) {
                                            var alertDialog = AlertDialog.Builder(requireContext(),R.style.BottomSheetDialogThem)
                                            val create = alertDialog.create()
                                            var dialogServiceBinding = DialogServiceBinding.inflate(LayoutInflater.from(requireContext()),null,false)
                                            create.setView(dialogServiceBinding.root)
                                            dialogServiceBinding.clouse.text = onAttach.getString(R.string.orqaga)
                                            dialogServiceBinding.active.text = onAttach.getText(R.string.aktivlashtirish)
                                            dialogServiceBinding.active.setOnClickListener {
                                                val substring = smsMoney.code?.trim()?.substring(smsMoney.code?.trim()?.length!! - 1)
                                                var Num:String
                                                if (substring=="#"){
                                                    Num = smsMoney.code?.trim()?.substring(0, smsMoney.code?.trim()?.length?.minus(1)!!)+ Uri.encode("#")
                                                }else{
                                                    Num = smsMoney.code?.trim()?.substring(0, smsMoney.code?.trim()?.length?.minus(2)!!)+ Uri.encode("#")
                                                }
                                                var intent = Intent(Intent.ACTION_CALL)
                                                intent.data = Uri.parse("tel:$Num")
                                                try {
                                                    startActivity(intent)
                                                }catch (e: Exception){
                                                    e.printStackTrace()
                                                }

                                            }
                                            dialogServiceBinding.clouse.setOnClickListener {
                                                create.dismiss()
                                            }
                                            dialogServiceBinding.share.setOnClickListener {
                                                var shareIntent = Intent().apply {
                                                    this.action = Intent.ACTION_SEND
                                                    this.putExtra(Intent.EXTRA_SUBJECT,"Uzmobile-${smsMoney.code?.trim()}\n${smsMoney.name?.trim()}")
                                                    this.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id=uz.pdp.uzmobile")
                                                    this.type="text/plain"
                                                }
                                                startActivity(shareIntent)
                                            }
                                            dialogServiceBinding.info.text = "${smsMoney.summ?.trim()}\n${smsMoney.count_min?.trim()}\n${smsMoney.day_month?.trim()}\n${smsMoney.onCode?.trim()}"?.trim()
                                            dialogServiceBinding.name.text = smsMoney.name?.trim()
                                            create.show()
                                        }
                                    })
                                            firebaseFirestore.collection("Constructor").addSnapshotListener { value, error ->
                                                value?.documentChanges?.forEach {
                                                    when(it.type){
                                                        DocumentChange.Type.ADDED->{
                                                            val minut = it.document.toObject(Minut::class.java)
                                                            listMinut.add(minut)
                                                        }
                                                    }
                                                }
                                                minutAdapter.submitList(listMinut)
                                                rvService.adapter = minutAdapter
                                            }
                                }
                            }
                        }
                        "ru".lowercase(Locale.getDefault())->{
                            when(param1?.lowercase(Locale.getDefault())){
                                "Минутные\nнаборы".lowercase(Locale.getDefault())->{
                                    listMinut = ArrayList()
                                    minutAdapter = MinutAdapter(object:MinutAdapter.OnItemClickListener{
                                        override fun onItemClick(smsMoney: Minut, position: Int) {
                                            var alertDialog = AlertDialog.Builder(requireContext(),R.style.BottomSheetDialogThem)
                                            val create = alertDialog.create()
                                            var dialogServiceBinding = DialogServiceBinding.inflate(LayoutInflater.from(requireContext()),null,false)
                                            create.setView(dialogServiceBinding.root)
                                            dialogServiceBinding.clouse.text = onAttach.getString(R.string.orqaga)
                                            dialogServiceBinding.active.text = onAttach.getText(R.string.aktivlashtirish)
                                            dialogServiceBinding.active.setOnClickListener {
                                                val substring = smsMoney.code?.trim()?.substring(smsMoney.code?.trim()?.length!! - 1)
                                                var Num:String
                                                if (substring=="#"){
                                                    Num = smsMoney.code?.trim()?.substring(0, smsMoney.code?.trim()?.length?.minus(1)!!)+ Uri.encode("#")
                                                }else{
                                                    Num = smsMoney.code?.trim()?.substring(0, smsMoney.code?.trim()?.length?.minus(2)!!)+ Uri.encode("#")
                                                }
                                                var intent = Intent(Intent.ACTION_CALL)
                                                intent.data = Uri.parse("tel:$Num")
                                                try {
                                                    startActivity(intent)
                                                }catch (e: Exception){
                                                    e.printStackTrace()
                                                }

                                            }
                                            dialogServiceBinding.clouse.setOnClickListener {
                                                create.dismiss()
                                            }
                                            dialogServiceBinding.share.setOnClickListener {
                                                var shareIntent = Intent().apply {
                                                    this.action = Intent.ACTION_SEND
                                                    this.putExtra(Intent.EXTRA_SUBJECT,"Uzmobile-${smsMoney.code?.trim()}\n${smsMoney.name?.trim()}")
                                                    this.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id=uz.pdp.uzmobile")
                                                    this.type="text/plain"
                                                }
                                                startActivity(shareIntent)
                                            }
                                            dialogServiceBinding.info.text = "${smsMoney.summ}\n${smsMoney.count_min?.trim()}\n${smsMoney.day_month}\n${smsMoney.onCode?.trim()}"
                                            dialogServiceBinding.name.text = smsMoney.name?.trim()
                                            create.show()
                                        }
                                    })
                                            firebaseFirestore.collection("MinutesRu").addSnapshotListener { value, error ->
                                                value?.documentChanges?.forEach {
                                                    when(it.type){
                                                        DocumentChange.Type.ADDED->{
                                                            val minut = it.document.toObject(Minut::class.java)
                                                            listMinut.add(minut)
                                                        }
                                                    }
                                                }
                                                minutAdapter.submitList(listMinut)
                                                rvService.adapter = minutAdapter
                                            }
                                }
                                "Полезная\nзамена".lowercase(Locale.getDefault())->{
                                    listreplaceMent = ArrayList()
                                    replaceAdapter = ReplaceAdapter(object:ReplaceAdapter.OnItemClickListener{
                                        override fun onItemClick(replacement: Replacement, position: Int) {
                                            var alertDialog = AlertDialog.Builder(requireContext(),R.style.BottomSheetDialogThem)
                                            val create = alertDialog.create()
                                            var dialogServiceBinding = DialogServiceBinding.inflate(LayoutInflater.from(requireContext()),null,false)
                                            create.setView(dialogServiceBinding.root)
                                            dialogServiceBinding.clouse.text = onAttach.getString(R.string.orqaga)
                                            dialogServiceBinding.active.text = onAttach.getText(R.string.aktivlashtirish)
                                            dialogServiceBinding.active.setOnClickListener {
                                                val substring = replacement.code?.trim()?.substring(replacement.code?.trim()?.length!! - 1)
                                                var Num:String
                                                if (substring=="#"){
                                                    Num = replacement.code?.trim()?.substring(0, replacement.code?.trim()?.length?.minus(1)!!)+ Uri.encode("#")
                                                }else{
                                                    Num = replacement.code?.substring(0, replacement.code?.trim()?.length?.minus(2)!!)+ Uri.encode("#")
                                                }
                                                var intent = Intent(Intent.ACTION_CALL)
                                                intent.data = Uri.parse("tel:$Num")
                                                try {
                                                    startActivity(intent)
                                                }catch (e: Exception){
                                                    e.printStackTrace()
                                                }

                                            }
                                            dialogServiceBinding.clouse.setOnClickListener {
                                                create.dismiss()
                                            }
                                            dialogServiceBinding.share.setOnClickListener {
                                                var shareIntent = Intent().apply {
                                                    this.action = Intent.ACTION_SEND
                                                    this.putExtra(Intent.EXTRA_SUBJECT,"Uzmobile-${replacement.code?.trim()}\n${replacement.name?.trim()}")
                                                    this.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id=uz.pdp.uzmobile")
                                                    this.type="text/plain"
                                                }
                                                startActivity(shareIntent)
                                            }
                                            dialogServiceBinding.info.text = "${replacement.getMinut?.trim()}\n${replacement.setMinut?.trim()}\n${replacement.price?.trim()}\n${replacement.day?.trim()}\n${replacement.onCodetext?.trim()}"?.trim()
                                            dialogServiceBinding.name.text = replacement.name?.trim()
                                            create.show()
                                        }
                                    })
                                            firebaseFirestore.collection("ReplacementRu").addSnapshotListener { value, error ->
                                                value?.documentChanges?.forEach {
                                                    when(it.type){
                                                        DocumentChange.Type.ADDED->{
                                                            val replaceMent = it.document.toObject(Replacement::class.java)
                                                            listreplaceMent.add(replaceMent)
                                                        }
                                                    }
                                                }
                                                replaceAdapter.submitList(listreplaceMent)
                                                rvService.adapter = replaceAdapter
                                            }
                                }
                                "Абоненты\nконструкторы".lowercase(Locale.getDefault())->{
                                    listMinut = ArrayList()
                                    minutAdapter = MinutAdapter(object:MinutAdapter.OnItemClickListener{
                                        override fun onItemClick(smsMoney: Minut, position: Int) {
                                            var alertDialog = AlertDialog.Builder(requireContext(),R.style.BottomSheetDialogThem)
                                            val create = alertDialog.create()
                                            var dialogServiceBinding = DialogServiceBinding.inflate(LayoutInflater.from(requireContext()),null,false)
                                            create.setView(dialogServiceBinding.root)
                                            dialogServiceBinding.active.text = onAttach.getText(R.string.aktivlashtirish)
                                            dialogServiceBinding.clouse.text = onAttach.getString(R.string.orqaga)
                                            dialogServiceBinding.active.setOnClickListener {
                                                val substring = smsMoney.code?.trim()?.substring(smsMoney.code?.trim()?.length!! - 1)
                                                var Num:String
                                                if (substring=="#"){
                                                    Num = smsMoney.code?.trim()?.substring(0, smsMoney.code?.trim()?.length?.minus(1)!!)+ Uri.encode("#")
                                                }else{
                                                    Num = smsMoney.code?.trim()?.substring(0, smsMoney.code?.trim()?.length?.minus(2)!!)+ Uri.encode("#")
                                                }
                                                var intent = Intent(Intent.ACTION_CALL)
                                                intent.data = Uri.parse("tel:$Num")
                                                try {
                                                    startActivity(intent)
                                                }catch (e: Exception){
                                                    e.printStackTrace()
                                                }

                                            }
                                            dialogServiceBinding.clouse.setOnClickListener {
                                                create.dismiss()
                                            }
                                            dialogServiceBinding.share.setOnClickListener {
                                                var shareIntent = Intent().apply {
                                                    this.action = Intent.ACTION_SEND
                                                    this.putExtra(Intent.EXTRA_SUBJECT,"Uzmobile-${smsMoney.code?.trim()}\n${smsMoney.name?.trim()}")
                                                    this.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id=uz.pdp.uzmobile")
                                                    this.type="text/plain"
                                                }
                                                startActivity(shareIntent)
                                            }
                                            dialogServiceBinding.info.text = "${smsMoney.summ?.trim()}\n${smsMoney.count_min?.trim()}\n${smsMoney.day_month?.trim()}\n${smsMoney.onCode?.trim()}"?.trim()
                                            dialogServiceBinding.name.text = smsMoney.name?.trim()
                                            create.show()
                                        }
                                    })
                                            firebaseFirestore.collection("ConstructorRu").addSnapshotListener { value, error ->
                                                value?.documentChanges?.forEach {
                                                    when(it.type){
                                                        DocumentChange.Type.ADDED->{
                                                            val minut = it.document.toObject(Minut::class.java)
                                                            listMinut.add(minut)
                                                        }
                                                    }
                                                }
                                                minutAdapter.submitList(listMinut)
                                                rvService.adapter = minutAdapter
                                            }
                                }
                            }
                        }
                        "kril".lowercase(Locale.getDefault())->{
                            when(param1?.lowercase(Locale.getDefault())) {
                                "Дақиқа\nтўпламлари".lowercase(Locale.getDefault()) -> {
                                    listMinut = ArrayList()
                                    minutAdapter =
                                        MinutAdapter(object : MinutAdapter.OnItemClickListener {
                                            override fun onItemClick(
                                                smsMoney: Minut,
                                                position: Int
                                            ) {
                                                var alertDialog = AlertDialog.Builder(
                                                    requireContext(),
                                                    R.style.BottomSheetDialogThem
                                                )
                                                val create = alertDialog.create()
                                                var dialogServiceBinding =
                                                    DialogServiceBinding.inflate(
                                                        LayoutInflater.from(requireContext()),
                                                        null,
                                                        false
                                                    )
                                                create.setView(dialogServiceBinding.root)
                                                dialogServiceBinding.active.text =
                                                    onAttach.getText(R.string.aktivlashtirish)
                                                dialogServiceBinding.clouse.text = onAttach.getString(R.string.orqaga)
                                                dialogServiceBinding.active.setOnClickListener {
                                                    val substring = smsMoney.code?.trim()
                                                        ?.substring(smsMoney.code?.trim()?.length!! - 1)
                                                    var Num: String
                                                    if (substring == "#") {
                                                        Num = smsMoney.code?.trim()?.substring(
                                                            0,
                                                            smsMoney.code?.trim()?.length?.minus(1)!!
                                                        ) + Uri.encode("#")
                                                    } else {
                                                        Num = smsMoney.code?.trim()?.substring(
                                                            0,
                                                            smsMoney.code?.trim()?.length?.minus(2)!!
                                                        ) + Uri.encode("#")
                                                    }
                                                    var intent = Intent(Intent.ACTION_CALL)
                                                    intent.data = Uri.parse("tel:$Num")
                                                    try {
                                                        startActivity(intent)
                                                    } catch (e: Exception) {
                                                        e.printStackTrace()
                                                    }

                                                }
                                                dialogServiceBinding.clouse.setOnClickListener {
                                                    create.dismiss()
                                                }
                                                dialogServiceBinding.share.setOnClickListener {
                                                    var shareIntent = Intent().apply {
                                                        this.action = Intent.ACTION_SEND
                                                        this.putExtra(
                                                            Intent.EXTRA_SUBJECT,
                                                            "Uzmobile-${smsMoney.code?.trim()}\n${smsMoney.name?.trim()}"
                                                        )
                                                        this.putExtra(
                                                            Intent.EXTRA_TEXT,
                                                            "https://play.google.com/store/apps/details?id=uz.pdp.uzmobile"
                                                        )
                                                        this.type = "text/plain"
                                                    }
                                                    startActivity(shareIntent)
                                                }
                                                dialogServiceBinding.info.text =
                                                    "${smsMoney.summ}\n${smsMoney.count_min?.trim()}\n${smsMoney.day_month}\n${smsMoney.onCode?.trim()}"
                                                dialogServiceBinding.name.text =
                                                    smsMoney.name?.trim()
                                                create.show()
                                            }
                                        })
                                            firebaseFirestore.collection("MinutCril")
                                                .addSnapshotListener { value, error ->
                                                    value?.documentChanges?.forEach {
                                                        when (it.type) {
                                                            DocumentChange.Type.ADDED -> {
                                                                val minut =
                                                                    it.document.toObject(Minut::class.java)
                                                                listMinut.add(minut)
                                                            }
                                                        }
                                                    }
                                                    minutAdapter.submitList(listMinut)
                                                    rvService.adapter = minutAdapter
                                                }
                                }
                                "Фойдали\nалмаштирув".lowercase(Locale.getDefault()) -> {
                                    listreplaceMent = ArrayList()
                                    replaceAdapter =
                                        ReplaceAdapter(object : ReplaceAdapter.OnItemClickListener {
                                            override fun onItemClick(
                                                replacement: Replacement,
                                                position: Int
                                            ) {
                                                var alertDialog = AlertDialog.Builder(
                                                    requireContext(),
                                                    R.style.BottomSheetDialogThem
                                                )
                                                val create = alertDialog.create()
                                                var dialogServiceBinding =
                                                    DialogServiceBinding.inflate(
                                                        LayoutInflater.from(requireContext()),
                                                        null,
                                                        false
                                                    )
                                                create.setView(dialogServiceBinding.root)
                                                dialogServiceBinding.active.text =
                                                    onAttach.getText(R.string.aktivlashtirish)
                                                dialogServiceBinding.clouse.text = onAttach.getString(R.string.orqaga)
                                                dialogServiceBinding.active.setOnClickListener {
                                                    val substring = replacement.code?.trim()
                                                        ?.substring(replacement.code?.trim()?.length!! - 1)
                                                    var Num: String
                                                    if (substring == "#") {
                                                        Num = replacement.code?.trim()?.substring(
                                                            0,
                                                            replacement.code?.trim()?.length?.minus(
                                                                1
                                                            )!!
                                                        ) + Uri.encode("#")
                                                    } else {
                                                        Num = replacement.code?.substring(
                                                            0,
                                                            replacement.code?.trim()?.length?.minus(
                                                                2
                                                            )!!
                                                        ) + Uri.encode("#")
                                                    }
                                                    var intent = Intent(Intent.ACTION_CALL)
                                                    intent.data = Uri.parse("tel:$Num")
                                                    try {
                                                        startActivity(intent)
                                                    } catch (e: Exception) {
                                                        e.printStackTrace()
                                                    }

                                                }
                                                dialogServiceBinding.clouse.setOnClickListener {
                                                    create.dismiss()
                                                }
                                                dialogServiceBinding.share.setOnClickListener {
                                                    var shareIntent = Intent().apply {
                                                        this.action = Intent.ACTION_SEND
                                                        this.putExtra(
                                                            Intent.EXTRA_SUBJECT,
                                                            "Uzmobile-${replacement.code?.trim()}\n${replacement.name?.trim()}"
                                                        )
                                                        this.putExtra(
                                                            Intent.EXTRA_TEXT,
                                                            "https://play.google.com/store/apps/details?id=uz.pdp.uzmobile"
                                                        )
                                                        this.type = "text/plain"
                                                    }
                                                    startActivity(shareIntent)
                                                }
                                                dialogServiceBinding.info.text =
                                                    "${replacement.getMinut?.trim()}\n${replacement.setMinut?.trim()}\n${replacement.price?.trim()}\n${replacement.day?.trim()}\n${replacement.onCodetext?.trim()}"?.trim()
                                                dialogServiceBinding.name.text =
                                                    replacement.name?.trim()
                                                create.show()
                                            }
                                        })
                                            firebaseFirestore.collection("ReplacementCril")
                                                .addSnapshotListener { value, error ->
                                                    value?.documentChanges?.forEach {
                                                        when (it.type) {
                                                            DocumentChange.Type.ADDED -> {
                                                                val replaceMent =
                                                                    it.document.toObject(Replacement::class.java)
                                                                listreplaceMent.add(replaceMent)
                                                            }
                                                        }
                                                    }
                                                    replaceAdapter.submitList(listreplaceMent)
                                                    rvService.adapter = replaceAdapter
                                                }
                                }
                                "Cонструcтор\nабонентлари".lowercase(Locale.getDefault()) -> {
                                    listMinut = ArrayList()
                                    minutAdapter =
                                        MinutAdapter(object : MinutAdapter.OnItemClickListener {
                                            override fun onItemClick(
                                                smsMoney: Minut,
                                                position: Int
                                            ) {
                                                var alertDialog = AlertDialog.Builder(
                                                    requireContext(),
                                                    R.style.BottomSheetDialogThem
                                                )
                                                val create = alertDialog.create()
                                                var dialogServiceBinding =
                                                    DialogServiceBinding.inflate(
                                                        LayoutInflater.from(requireContext()),
                                                        null,
                                                        false
                                                    )
                                                create.setView(dialogServiceBinding.root)
                                                dialogServiceBinding.active.text =
                                                    onAttach.getText(R.string.aktivlashtirish)
                                                dialogServiceBinding.clouse.text = onAttach.getString(R.string.orqaga)
                                                dialogServiceBinding.active.setOnClickListener {
                                                    val substring = smsMoney.code?.trim()
                                                        ?.substring(smsMoney.code?.trim()?.length!! - 1)
                                                    var Num: String
                                                    if (substring == "#") {
                                                        Num = smsMoney.code?.trim()?.substring(
                                                            0,
                                                            smsMoney.code?.trim()?.length?.minus(1)!!
                                                        ) + Uri.encode("#")
                                                    } else {
                                                        Num = smsMoney.code?.trim()?.substring(
                                                            0,
                                                            smsMoney.code?.trim()?.length?.minus(2)!!
                                                        ) + Uri.encode("#")
                                                    }
                                                    var intent = Intent(Intent.ACTION_CALL)
                                                    intent.data = Uri.parse("tel:$Num")
                                                    try {
                                                        startActivity(intent)
                                                    } catch (e: Exception) {
                                                        e.printStackTrace()
                                                    }

                                                }
                                                dialogServiceBinding.clouse.setOnClickListener {
                                                    create.dismiss()
                                                }
                                                dialogServiceBinding.share.setOnClickListener {
                                                    var shareIntent = Intent().apply {
                                                        this.action = Intent.ACTION_SEND
                                                        this.putExtra(
                                                            Intent.EXTRA_SUBJECT,
                                                            "Uzmobile-${smsMoney.code?.trim()}\n${smsMoney.name?.trim()}"
                                                        )
                                                        this.putExtra(
                                                            Intent.EXTRA_TEXT,
                                                            "https://play.google.com/store/apps/details?id=uz.pdp.uzmobile"
                                                        )
                                                        this.type = "text/plain"
                                                    }
                                                    startActivity(shareIntent)
                                                }
                                                dialogServiceBinding.info.text =
                                                    "${smsMoney.summ?.trim()}\n${smsMoney.count_min?.trim()}\n${smsMoney.day_month?.trim()}\n${smsMoney.onCode?.trim()}"?.trim()
                                                dialogServiceBinding.name.text =
                                                    smsMoney.name?.trim()
                                                create.show()
                                            }
                                        })
                                            firebaseFirestore.collection("ContructorCril")
                                                .addSnapshotListener { value, error ->
                                                    value?.documentChanges?.forEach {
                                                        when (it.type) {
                                                            DocumentChange.Type.ADDED -> {
                                                                val minut =
                                                                    it.document.toObject(Minut::class.java)
                                                                listMinut.add(minut)
                                                            }
                                                        }
                                                    }
                                                    minutAdapter.submitList(listMinut)
                                                    rvService.adapter = minutAdapter
                                                }
                                }
                            }
                        }
                    }
                }
                "Internet".lowercase(Locale.getDefault())->{
                    listInternet = ArrayList()
                    listNonstop = ArrayList()
                    when(language?.lowercase(Locale.getDefault())){
                        "uz".lowercase(Locale.getDefault())->{
                            when(param1?.lowercase(Locale.getDefault())){
                                "Oylik paketlar".lowercase(Locale.getDefault())->{
                                    rvInternet = RvInternet(object:RvInternet.OnItemClickListener {
                                        override fun onItemClick(smsMoney: Internet, position: Int) {
                                            var alertDialog = AlertDialog.Builder(
                                                requireContext(),
                                                R.style.BottomSheetDialogThem
                                            )
                                            val create = alertDialog.create()
                                            var dialogServiceBinding = DialogServiceBinding.inflate(
                                                LayoutInflater.from(requireContext()), null, false
                                            )
                                            create.setView(dialogServiceBinding.root)
                                            dialogServiceBinding.clouse.text = onAttach.getString(R.string.orqaga)
                                            dialogServiceBinding.active.text = onAttach.getText(R.string.aktivlashtirish)
                                            dialogServiceBinding.active.setOnClickListener {
                                                val substring =
                                                    smsMoney.activeCode?.trim()?.substring(smsMoney.activeCode?.trim()?.length!! - 1)
                                                var Num: String
                                                if (substring == "#") {
                                                    Num = smsMoney.activeCode?.trim()?.substring(
                                                        0,
                                                        smsMoney.activeCode?.trim()?.length?.minus(1)!!
                                                    ) + Uri.encode("#")
                                                } else {
                                                    Num = smsMoney.activeCode?.trim()?.substring(
                                                        0,
                                                        smsMoney.activeCode?.trim()?.length?.minus(2)!!
                                                    ) + Uri.encode("#")
                                                }
                                                var intent = Intent(Intent.ACTION_CALL)
                                                intent.data = Uri.parse("tel:$Num")
                                                try {
                                                    startActivity(intent)
                                                } catch (e: Exception) {
                                                    e.printStackTrace()
                                                }

                                            }
                                            dialogServiceBinding.clouse.setOnClickListener {
                                                create.dismiss()
                                            }
                                            dialogServiceBinding.share.setOnClickListener {
                                                var shareIntent = Intent().apply {
                                                    this.action = Intent.ACTION_SEND
                                                    this.putExtra(
                                                        Intent.EXTRA_SUBJECT,
                                                        "Uzmobile-${smsMoney.activeCode?.trim()}\n${smsMoney.mb?.trim()}"
                                                    )
                                                    this.putExtra(
                                                        Intent.EXTRA_TEXT,
                                                        "https://play.google.com/store/apps/details?id=uz.pdp.uzmobile"
                                                    )
                                                    this.type = "text/plain"
                                                }
                                                startActivity(shareIntent)
                                            }
                                            dialogServiceBinding.info.text =
                                                "${onAttach.getText(R.string.price)} ${smsMoney.price?.trim()}\n${onAttach.getText(R.string.chrafick)} ${smsMoney.mb?.trim()}\n${onAttach.getText(R.string.day)} ${smsMoney.day_count?.trim()}\n${onAttach.getString(R.string.active_ok)}:${smsMoney.activeCode?.trim()}".trim()
                                            dialogServiceBinding.name.text = smsMoney.mb?.trim()
                                            create.show()
                                        }
                                    })
                                            firebaseFirestore.collection("InternetMonth").addSnapshotListener { value, error ->
                                                value?.documentChanges?.forEach {
                                                    when(it.type){
                                                        DocumentChange.Type.ADDED->{
                                                            val internet = it.document.toObject(Internet::class.java)
                                                            listInternet.add(internet)
                                                        }
                                                    }
                                                }
                                                rvInternet.submitList(listInternet)
                                                rvService.adapter = rvInternet
                                            }

                                }
                                "Kunlik paketlar".lowercase(Locale.getDefault())->{
                                    rvInternet = RvInternet(object:RvInternet.OnItemClickListener{
                                        override fun onItemClick(smsMoney: Internet, position: Int) {
                                            var alertDialog = AlertDialog.Builder(
                                                requireContext(),
                                                R.style.BottomSheetDialogThem
                                            )
                                            val create = alertDialog.create()
                                            var dialogServiceBinding = DialogServiceBinding.inflate(
                                                LayoutInflater.from(requireContext()), null, false
                                            )
                                            create.setView(dialogServiceBinding.root)
                                            dialogServiceBinding.clouse.text = onAttach.getString(R.string.orqaga)
                                            dialogServiceBinding.active.text = onAttach.getText(R.string.aktivlashtirish)
                                            dialogServiceBinding.active.setOnClickListener {
                                                val substring =
                                                    smsMoney.activeCode?.trim()?.substring(smsMoney.activeCode?.trim()?.length!! - 1)
                                                var Num: String
                                                if (substring == "#") {
                                                    Num = smsMoney.activeCode?.trim()?.substring(
                                                        0,
                                                        smsMoney.activeCode?.trim()?.length?.minus(1)!!
                                                    ) + Uri.encode("#")
                                                } else {
                                                    Num = smsMoney.activeCode?.trim()?.substring(
                                                        0,
                                                        smsMoney.activeCode?.trim()?.length?.minus(2)!!
                                                    ) + Uri.encode("#")
                                                }
                                                var intent = Intent(Intent.ACTION_CALL)
                                                intent.data = Uri.parse("tel:$Num")
                                                try {
                                                    startActivity(intent)
                                                } catch (e: Exception) {
                                                    e.printStackTrace()
                                                }

                                            }
                                            dialogServiceBinding.clouse.setOnClickListener {
                                                create.dismiss()
                                            }
                                            dialogServiceBinding.share.setOnClickListener {
                                                var shareIntent = Intent().apply {
                                                    this.action = Intent.ACTION_SEND
                                                    this.putExtra(
                                                        Intent.EXTRA_SUBJECT,
                                                        "Uzmobile-${smsMoney.activeCode?.trim()}\n${smsMoney.mb?.trim()}"
                                                    )
                                                    this.putExtra(
                                                        Intent.EXTRA_TEXT,
                                                        "https://play.google.com/store/apps/details?id=uz.pdp.uzmobile"
                                                    )
                                                    this.type = "text/plain"
                                                }
                                                startActivity(shareIntent)
                                            }
                                            dialogServiceBinding.info.text =
                                                "${onAttach.getText(R.string.price)} ${smsMoney.price?.trim()}\n${onAttach.getText(R.string.chrafick)} ${smsMoney.mb?.trim()}\n${onAttach.getText(R.string.day)} ${smsMoney.day_count?.trim()}\n${onAttach.getString(R.string.active_ok)}:${smsMoney.activeCode?.trim()}".trim()
                                            dialogServiceBinding.name.text = smsMoney.mb?.trim()
                                            create.show()
                                        }
                                    })
                                            firebaseFirestore.collection("DayInternet").addSnapshotListener { value, error ->
                                                value?.documentChanges?.forEach {
                                                    when(it.type){
                                                        DocumentChange.Type.ADDED->{
                                                            val internet = it.document.toObject(Internet::class.java)
                                                            listInternet.add(internet)
                                                        }
                                                    }
                                                }
                                                rvInternet.submitList(listInternet)
                                                rvService.adapter = rvInternet
                                            }
                                }
                                "Tungi internet".lowercase(Locale.getDefault())->{
                                    rvInternet = RvInternet(object:RvInternet.OnItemClickListener{
                                        override fun onItemClick(smsMoney: Internet, position: Int) {
                                            var alertDialog = AlertDialog.Builder(
                                                requireContext(),
                                                R.style.BottomSheetDialogThem
                                            )
                                            val create = alertDialog.create()
                                            var dialogServiceBinding = DialogServiceBinding.inflate(
                                                LayoutInflater.from(requireContext()), null, false
                                            )
                                            create.setView(dialogServiceBinding.root)
                                            dialogServiceBinding.clouse.text = onAttach.getString(R.string.orqaga)
                                            dialogServiceBinding.active.text = onAttach.getText(R.string.aktivlashtirish)
                                            dialogServiceBinding.active.setOnClickListener {
                                                val substring =
                                                    smsMoney.activeCode?.trim()?.substring(smsMoney.activeCode?.trim()?.length!! - 1)
                                                var Num: String
                                                if (substring == "#") {
                                                    Num = smsMoney.activeCode?.trim()?.substring(
                                                        0,
                                                        smsMoney.activeCode?.trim()?.length?.minus(1)!!
                                                    ) + Uri.encode("#")
                                                } else {
                                                    Num = smsMoney.activeCode?.trim()?.substring(
                                                        0,
                                                        smsMoney.activeCode?.trim()?.length?.minus(2)!!
                                                    ) + Uri.encode("#")
                                                }
                                                var intent = Intent(Intent.ACTION_CALL)
                                                intent.data = Uri.parse("tel:$Num")
                                                try {
                                                    startActivity(intent)
                                                } catch (e: Exception) {
                                                    e.printStackTrace()
                                                }

                                            }
                                            dialogServiceBinding.clouse.setOnClickListener {
                                                create.dismiss()
                                            }
                                            dialogServiceBinding.share.setOnClickListener {
                                                var shareIntent = Intent().apply {
                                                    this.action = Intent.ACTION_SEND
                                                    this.putExtra(
                                                        Intent.EXTRA_SUBJECT,
                                                        "Uzmobile-${smsMoney.activeCode?.trim()}\n${smsMoney.mb?.trim()}"
                                                    )
                                                    this.putExtra(
                                                        Intent.EXTRA_TEXT,
                                                        "https://play.google.com/store/apps/details?id=uz.pdp.uzmobile"
                                                    )
                                                    this.type = "text/plain"
                                                }
                                                startActivity(shareIntent)
                                            }
                                            dialogServiceBinding.info.text =
                                                "${onAttach.getText(R.string.price)} ${smsMoney.price?.trim()}\n${onAttach.getText(R.string.chrafick)} ${smsMoney.mb?.trim()}\n${onAttach.getText(R.string.day)} ${smsMoney.day_count?.trim()}\n${onAttach.getString(R.string.active_ok)}:${smsMoney.activeCode?.trim()}".trim()
                                            dialogServiceBinding.name.text = smsMoney.mb?.trim()
                                            create.show()
                                        }
                                    })
                                            firebaseFirestore.collection("NightInternet").addSnapshotListener { value, error ->
                                                value?.documentChanges?.forEach {
                                                    when(it.type){
                                                        DocumentChange.Type.ADDED->{
                                                            val internet = it.document.toObject(Internet::class.java)
                                                            listInternet.add(internet)
                                                        }
                                                    }
                                                }
                                                rvInternet.submitList(listInternet)
                                                rvService.adapter = rvInternet
                                            }

                                }
                                "TAS-IX uchun internet paketlari".lowercase(Locale.getDefault())->{
                                    rvInternet = RvInternet(object:RvInternet.OnItemClickListener{
                                        override fun onItemClick(smsMoney: Internet, position: Int) {
                                            var alertDialog = AlertDialog.Builder(
                                                requireContext(),
                                                R.style.BottomSheetDialogThem
                                            )
                                            val create = alertDialog.create()
                                            var dialogServiceBinding = DialogServiceBinding.inflate(
                                                LayoutInflater.from(requireContext()), null, false
                                            )
                                            create.setView(dialogServiceBinding.root)
                                            dialogServiceBinding.clouse.text = onAttach.getString(R.string.orqaga)
                                            dialogServiceBinding.active.text = onAttach.getText(R.string.aktivlashtirish)
                                            dialogServiceBinding.active.setOnClickListener {
                                                val substring =
                                                    smsMoney.activeCode?.trim()?.substring(smsMoney.activeCode?.trim()?.length!! - 1)
                                                var Num: String
                                                if (substring == "#") {
                                                    Num = smsMoney.activeCode?.trim()?.substring(
                                                        0,
                                                        smsMoney.activeCode?.trim()?.length?.minus(1)!!
                                                    ) + Uri.encode("#")
                                                } else {
                                                    Num = smsMoney.activeCode?.trim()?.substring(
                                                        0,
                                                        smsMoney.activeCode?.trim()?.length?.minus(2)!!
                                                    ) + Uri.encode("#")
                                                }
                                                var intent = Intent(Intent.ACTION_CALL)
                                                intent.data = Uri.parse("tel:$Num")
                                                try {
                                                    startActivity(intent)
                                                } catch (e: Exception) {
                                                    e.printStackTrace()
                                                }

                                            }
                                            dialogServiceBinding.clouse.setOnClickListener {
                                                create.dismiss()
                                            }
                                            dialogServiceBinding.share.setOnClickListener {
                                                var shareIntent = Intent().apply {
                                                    this.action = Intent.ACTION_SEND
                                                    this.putExtra(
                                                        Intent.EXTRA_SUBJECT,
                                                        "Uzmobile-${smsMoney.activeCode?.trim()}\n${smsMoney.mb?.trim()}"
                                                    )
                                                    this.putExtra(
                                                        Intent.EXTRA_TEXT,
                                                        "https://play.google.com/store/apps/details?id=uz.pdp.uzmobile"
                                                    )
                                                    this.type = "text/plain"
                                                }
                                                startActivity(shareIntent)
                                            }
                                            dialogServiceBinding.info.text =
                                                "${onAttach.getText(R.string.price)} ${smsMoney.price?.trim()}\n${onAttach.getText(R.string.chrafick)} ${smsMoney.mb?.trim()}\n${onAttach.getText(R.string.day)} ${smsMoney.day_count?.trim()}\n${onAttach.getString(R.string.active_ok)}:${smsMoney.activeCode?.trim()}".trim()
                                            dialogServiceBinding.name.text = smsMoney.mb?.trim()
                                            create.show()
                                        }
                                    })
                                            firebaseFirestore.collection("TasxInternet").addSnapshotListener { value, error ->
                                                value?.documentChanges?.forEach {
                                                    when(it.type){
                                                        DocumentChange.Type.ADDED->{
                                                            val internet = it.document.toObject(Internet::class.java)
                                                            listInternet.add(internet)
                                                        }
                                                    }
                                                }
                                                rvInternet.submitList(listInternet)
                                                rvService.adapter = rvInternet
                                            }
                                }
                                "Internet non-stop".lowercase(Locale.getDefault())->{
                                    rvNonStop = RvNonStop(object:RvNonStop.OnItemClickListener{
                                        override fun onItemClick(smsMoney: NonStop, position: Int) {
                                            var alertDialog = AlertDialog.Builder(
                                                requireContext(),
                                                R.style.BottomSheetDialogThem
                                            )
                                            val create = alertDialog.create()
                                            var dialogServiceBinding = DialogServiceBinding.inflate(
                                                LayoutInflater.from(requireContext()), null, false
                                            )
                                            create.setView(dialogServiceBinding.root)
                                            dialogServiceBinding.clouse.text = onAttach.getString(R.string.orqaga)
                                            dialogServiceBinding.active.text = onAttach.getText(R.string.aktivlashtirish)
                                            dialogServiceBinding.active.setOnClickListener {
                                                val substring =
                                                    smsMoney.activeCode?.trim()?.substring(smsMoney.activeCode?.trim()?.length!! - 1)
                                                var Num: String
                                                if (substring == "#") {
                                                    Num = smsMoney.activeCode?.trim()?.substring(
                                                        0,
                                                        smsMoney.activeCode?.trim()?.length?.minus(1)!!
                                                    ) + Uri.encode("#")
                                                } else {
                                                    Num = smsMoney.activeCode?.trim()?.substring(
                                                        0,
                                                        smsMoney.activeCode?.trim()?.length?.minus(2)!!
                                                    ) + Uri.encode("#")
                                                }
                                                var intent = Intent(Intent.ACTION_CALL)
                                                intent.data = Uri.parse("tel:$Num")
                                                try {
                                                    startActivity(intent)
                                                } catch (e: Exception) {
                                                    e.printStackTrace()
                                                }

                                            }
                                            dialogServiceBinding.clouse.setOnClickListener {
                                                create.dismiss()
                                            }
                                            dialogServiceBinding.share.setOnClickListener {
                                                var shareIntent = Intent().apply {
                                                    this.action = Intent.ACTION_SEND
                                                    this.putExtra(
                                                        Intent.EXTRA_SUBJECT,
                                                        "Uzmobile-${smsMoney.activeCode?.trim()}\n${smsMoney.mb?.trim()}"
                                                    )
                                                    this.putExtra(
                                                        Intent.EXTRA_TEXT,
                                                        "https://play.google.com/store/apps/details?id=uz.pdp.uzmobile"
                                                    )
                                                    this.type = "text/plain"
                                                }
                                                startActivity(shareIntent)
                                            }
                                            dialogServiceBinding.info.text =
                                                "${onAttach.getText(R.string.price)} ${smsMoney.price?.trim()}\n${onAttach.getText(R.string.chrafick)} ${smsMoney.mb?.trim()}\n${onAttach.getText(R.string.day)} ${smsMoney.day_count?.trim()}\n${onAttach.getString(R.string.active_ok)}:${smsMoney.activeCode?.trim()}\n ${onAttach.getText(R.string.clouse)}:${smsMoney.offCode}".trim()
                                            dialogServiceBinding.name.text = smsMoney.mb?.trim()
                                            create.show()
                                        }
                                    })
                                            firebaseFirestore.collection("Non-Stop").addSnapshotListener { value, error ->
                                                value?.documentChanges?.forEach {
                                                    when(it.type){
                                                        DocumentChange.Type.ADDED->{
                                                            val internet = it.document.toObject(NonStop::class.java)
                                                            listNonstop.add(internet)
                                                        }
                                                    }
                                                }
                                                rvNonStop.submitList(listNonstop)
                                                rvService.adapter = rvNonStop
                                            }

                                }
                                "TA'LIM tarif rejasi uchun maxsus\ninternet-paketlar".lowercase(Locale.getDefault())->{
                                    //CourceInternet
                                    rvInternet = RvInternet(object:RvInternet.OnItemClickListener{
                                        override fun onItemClick(smsMoney: Internet, position: Int) {
                                            var alertDialog = AlertDialog.Builder(
                                                requireContext(),
                                                R.style.BottomSheetDialogThem
                                            )
                                            val create = alertDialog.create()
                                            var dialogServiceBinding = DialogServiceBinding.inflate(
                                                LayoutInflater.from(requireContext()), null, false
                                            )
                                            create.setView(dialogServiceBinding.root)
                                            dialogServiceBinding.clouse.text = onAttach.getString(R.string.orqaga)
                                            dialogServiceBinding.active.text = onAttach.getText(R.string.aktivlashtirish)
                                            dialogServiceBinding.active.setOnClickListener {
                                                val substring =
                                                    smsMoney.activeCode?.trim()?.substring(smsMoney.activeCode?.trim()?.length!! - 1)
                                                var Num: String
                                                if (substring == "#") {
                                                    Num = smsMoney.activeCode?.trim()?.substring(
                                                        0,
                                                        smsMoney.activeCode?.trim()?.length?.minus(1)!!
                                                    ) + Uri.encode("#")
                                                } else {
                                                    Num = smsMoney.activeCode?.trim()?.substring(
                                                        0,
                                                        smsMoney.activeCode?.trim()?.length?.minus(2)!!
                                                    ) + Uri.encode("#")
                                                }
                                                var intent = Intent(Intent.ACTION_CALL)
                                                intent.data = Uri.parse("tel:$Num")
                                                try {
                                                    startActivity(intent)
                                                } catch (e: Exception) {
                                                    e.printStackTrace()
                                                }

                                            }
                                            dialogServiceBinding.clouse.setOnClickListener {
                                                create.dismiss()
                                            }
                                            dialogServiceBinding.share.setOnClickListener {
                                                var shareIntent = Intent().apply {
                                                    this.action = Intent.ACTION_SEND
                                                    this.putExtra(
                                                        Intent.EXTRA_SUBJECT,
                                                        "Uzmobile-${smsMoney.activeCode?.trim()}\n${smsMoney.mb?.trim()}"
                                                    )
                                                    this.putExtra(
                                                        Intent.EXTRA_TEXT,
                                                        "https://play.google.com/store/apps/details?id=uz.pdp.uzmobile"
                                                    )
                                                    this.type = "text/plain"
                                                }
                                                startActivity(shareIntent)
                                            }
                                            dialogServiceBinding.info.text =
                                                "${onAttach.getText(R.string.price)} ${smsMoney.price?.trim()}\n${onAttach.getText(R.string.chrafick)} ${smsMoney.mb?.trim()}\n${onAttach.getText(R.string.day)} ${smsMoney.day_count?.trim()}\n${onAttach.getString(R.string.active_ok)}:${smsMoney.activeCode?.trim()}".trim()
                                            dialogServiceBinding.name.text = smsMoney.mb?.trim()
                                            create.show()
                                        }
                                    })
                                            firebaseFirestore.collection("CourceInternet").addSnapshotListener { value, error ->
                                                value?.documentChanges?.forEach {
                                                    when(it.type){
                                                        DocumentChange.Type.ADDED->{
                                                            val internet = it.document.toObject(Internet::class.java)
                                                            listInternet.add(internet)
                                                        }
                                                    }
                                                }
                                                rvInternet.submitList(listInternet)
                                                rvService.adapter = rvInternet
                                            }
                                }
                                "<<Constructor>> TR abonentlari\nuchun internet paketlari".lowercase(Locale.getDefault())->{
                                    rvInternet = RvInternet(object:RvInternet.OnItemClickListener{
                                        override fun onItemClick(smsMoney: Internet, position: Int) {
                                            var alertDialog = AlertDialog.Builder(
                                                requireContext(),
                                                R.style.BottomSheetDialogThem
                                            )
                                            val create = alertDialog.create()
                                            var dialogServiceBinding = DialogServiceBinding.inflate(
                                                LayoutInflater.from(requireContext()), null, false
                                            )
                                            create.setView(dialogServiceBinding.root)
                                            dialogServiceBinding.clouse.text = onAttach.getString(R.string.orqaga)
                                            dialogServiceBinding.active.text = onAttach.getText(R.string.aktivlashtirish)
                                            dialogServiceBinding.active.setOnClickListener {
                                                val substring =
                                                    smsMoney.activeCode?.trim()?.substring(smsMoney.activeCode?.trim()?.length!! - 1)
                                                var Num: String
                                                if (substring == "#") {
                                                    Num = smsMoney.activeCode?.trim()?.substring(
                                                        0,
                                                        smsMoney.activeCode?.trim()?.length?.minus(1)!!
                                                    ) + Uri.encode("#")
                                                } else {
                                                    Num = smsMoney.activeCode?.trim()?.substring(
                                                        0,
                                                        smsMoney.activeCode?.trim()?.length?.minus(2)!!
                                                    ) + Uri.encode("#")
                                                }
                                                var intent = Intent(Intent.ACTION_CALL)
                                                intent.data = Uri.parse("tel:$Num")
                                                try {
                                                    startActivity(intent)
                                                } catch (e: Exception) {
                                                    e.printStackTrace()
                                                }

                                            }
                                            dialogServiceBinding.clouse.setOnClickListener {
                                                create.dismiss()
                                            }
                                            dialogServiceBinding.share.setOnClickListener {
                                                var shareIntent = Intent().apply {
                                                    this.action = Intent.ACTION_SEND
                                                    this.putExtra(
                                                        Intent.EXTRA_SUBJECT,
                                                        "Uzmobile-${smsMoney.activeCode?.trim()}\n${smsMoney.mb?.trim()}"
                                                    )
                                                    this.putExtra(
                                                        Intent.EXTRA_TEXT,
                                                        "https://play.google.com/store/apps/details?id=uz.pdp.uzmobile"
                                                    )
                                                    this.type = "text/plain"
                                                }
                                                startActivity(shareIntent)
                                            }
                                            dialogServiceBinding.info.text =
                                                "${onAttach.getText(R.string.price)} ${smsMoney.price?.trim()}\n${onAttach.getText(R.string.chrafick)} ${smsMoney.mb?.trim()}\n${onAttach.getText(R.string.day)} ${smsMoney.day_count?.trim()}\n${onAttach.getString(R.string.active_ok)}:${smsMoney.activeCode?.trim()}".trim()
                                            dialogServiceBinding.name.text = smsMoney.mb?.trim()
                                            create.show()
                                        }
                                    })
                                            firebaseFirestore.collection("ConstructorTr").addSnapshotListener { value, error ->
                                                value?.documentChanges?.forEach {
                                                    when(it.type){
                                                        DocumentChange.Type.ADDED->{
                                                            val internet = it.document.toObject(Internet::class.java)
                                                            listInternet.add(internet)
                                                        }
                                                    }
                                                }
                                                rvInternet.submitList(listInternet)
                                                rvService.adapter = rvInternet
                                            }
                                }
                            }
                        }
                        "ru".lowercase(Locale.getDefault())->{
                            when(param1?.lowercase(Locale.getDefault())){
                                "Ежемесячные пакеты".lowercase(Locale.getDefault())->{
                                    rvInternet = RvInternet(object:RvInternet.OnItemClickListener {
                                        override fun onItemClick(smsMoney: Internet, position: Int) {
                                            var alertDialog = AlertDialog.Builder(
                                                requireContext(),
                                                R.style.BottomSheetDialogThem
                                            )
                                            val create = alertDialog.create()
                                            var dialogServiceBinding = DialogServiceBinding.inflate(
                                                LayoutInflater.from(requireContext()), null, false
                                            )
                                            create.setView(dialogServiceBinding.root)
                                            dialogServiceBinding.clouse.text = onAttach.getString(R.string.orqaga)
                                            dialogServiceBinding.active.text = onAttach.getText(R.string.aktivlashtirish)
                                            dialogServiceBinding.active.setOnClickListener {
                                                val substring =
                                                    smsMoney.activeCode?.trim()?.substring(smsMoney.activeCode?.trim()?.length!! - 1)
                                                var Num: String
                                                if (substring == "#") {
                                                    Num = smsMoney.activeCode?.trim()?.substring(
                                                        0,
                                                        smsMoney.activeCode?.trim()?.length?.minus(1)!!
                                                    ) + Uri.encode("#")
                                                } else {
                                                    Num = smsMoney.activeCode?.trim()?.substring(
                                                        0,
                                                        smsMoney.activeCode?.trim()?.length?.minus(2)!!
                                                    ) + Uri.encode("#")
                                                }
                                                var intent = Intent(Intent.ACTION_CALL)
                                                intent.data = Uri.parse("tel:$Num")
                                                try {
                                                    startActivity(intent)
                                                } catch (e: Exception) {
                                                    e.printStackTrace()
                                                }

                                            }
                                            dialogServiceBinding.clouse.setOnClickListener {
                                                create.dismiss()
                                            }
                                            dialogServiceBinding.share.setOnClickListener {
                                                var shareIntent = Intent().apply {
                                                    this.action = Intent.ACTION_SEND
                                                    this.putExtra(
                                                        Intent.EXTRA_SUBJECT,
                                                        "Uzmobile-${smsMoney.activeCode?.trim()}\n${smsMoney.mb?.trim()}"
                                                    )
                                                    this.putExtra(
                                                        Intent.EXTRA_TEXT,
                                                        "https://play.google.com/store/apps/details?id=uz.pdp.uzmobile"
                                                    )
                                                    this.type = "text/plain"
                                                }
                                                startActivity(shareIntent)
                                            }
                                            dialogServiceBinding.info.text =
                                                "${onAttach.getText(R.string.price)} ${smsMoney.price?.trim()}\n${onAttach.getText(R.string.chrafick)} ${smsMoney.mb?.trim()}\n${onAttach.getText(R.string.day)} ${smsMoney.day_count?.trim()}\n${onAttach.getString(R.string.active_ok)}:${smsMoney.activeCode?.trim()}".trim()
                                            dialogServiceBinding.info.text =
                                                "${onAttach.getText(R.string.price)} ${smsMoney.price?.trim()}\n${onAttach.getText(R.string.chrafick)} ${smsMoney.mb?.trim()}\n${onAttach.getText(R.string.day)} ${smsMoney.day_count?.trim()}\n${onAttach.getString(R.string.active_ok)}:${smsMoney.activeCode?.trim()}".trim()
                                            dialogServiceBinding.name.text = smsMoney.mb?.trim()
                                            create.show()
                                        }
                                    })
                                            firebaseFirestore.collection("InternetMonthRu").addSnapshotListener { value, error ->
                                                value?.documentChanges?.forEach {
                                                    when(it.type){
                                                        DocumentChange.Type.ADDED->{
                                                            val internet = it.document.toObject(Internet::class.java)
                                                            listInternet.add(internet)
                                                        }
                                                    }
                                                }
                                                rvInternet.submitList(listInternet)
                                                rvService.adapter = rvInternet
                                            }
                                }
                                "Ежедневные пакеты".lowercase(Locale.getDefault())->{
                                    rvInternet = RvInternet(object:RvInternet.OnItemClickListener{
                                        override fun onItemClick(smsMoney: Internet, position: Int) {
                                            var alertDialog = AlertDialog.Builder(
                                                requireContext(),
                                                R.style.BottomSheetDialogThem
                                            )
                                            val create = alertDialog.create()
                                            var dialogServiceBinding = DialogServiceBinding.inflate(
                                                LayoutInflater.from(requireContext()), null, false
                                            )
                                            create.setView(dialogServiceBinding.root)
                                            dialogServiceBinding.clouse.text = onAttach.getString(R.string.orqaga)
                                            dialogServiceBinding.active.text = onAttach.getText(R.string.aktivlashtirish)
                                            dialogServiceBinding.active.setOnClickListener {
                                                val substring =
                                                    smsMoney.activeCode?.trim()?.substring(smsMoney.activeCode?.trim()?.length!! - 1)
                                                var Num: String
                                                if (substring == "#") {
                                                    Num = smsMoney.activeCode?.trim()?.substring(
                                                        0,
                                                        smsMoney.activeCode?.trim()?.length?.minus(1)!!
                                                    ) + Uri.encode("#")
                                                } else {
                                                    Num = smsMoney.activeCode?.trim()?.substring(
                                                        0,
                                                        smsMoney.activeCode?.trim()?.length?.minus(2)!!
                                                    ) + Uri.encode("#")
                                                }
                                                var intent = Intent(Intent.ACTION_CALL)
                                                intent.data = Uri.parse("tel:$Num")
                                                try {
                                                    startActivity(intent)
                                                } catch (e: Exception) {
                                                    e.printStackTrace()
                                                }

                                            }
                                            dialogServiceBinding.clouse.setOnClickListener {
                                                create.dismiss()
                                            }
                                            dialogServiceBinding.share.setOnClickListener {
                                                var shareIntent = Intent().apply {
                                                    this.action = Intent.ACTION_SEND
                                                    this.putExtra(
                                                        Intent.EXTRA_SUBJECT,
                                                        "Uzmobile-${smsMoney.activeCode?.trim()}\n${smsMoney.mb?.trim()}"
                                                    )
                                                    this.putExtra(
                                                        Intent.EXTRA_TEXT,
                                                        "https://play.google.com/store/apps/details?id=uz.pdp.uzmobile"
                                                    )
                                                    this.type = "text/plain"
                                                }
                                                startActivity(shareIntent)
                                            }
                                            dialogServiceBinding.info.text =
                                                "${onAttach.getText(R.string.price)} ${smsMoney.price?.trim()}\n${onAttach.getText(R.string.chrafick)} ${smsMoney.mb?.trim()}\n${onAttach.getText(R.string.day)} ${smsMoney.day_count?.trim()}\n${onAttach.getString(R.string.active_ok)}:${smsMoney.activeCode?.trim()}".trim()
                                            dialogServiceBinding.name.text = smsMoney.mb?.trim()
                                            create.show()
                                        }
                                    })
                                            firebaseFirestore.collection("DayInternetRu").addSnapshotListener { value, error ->
                                                value?.documentChanges?.forEach {
                                                    when(it.type){
                                                        DocumentChange.Type.ADDED->{
                                                            val internet = it.document.toObject(Internet::class.java)
                                                            listInternet.add(internet)
                                                        }
                                                    }
                                                }
                                                rvInternet.submitList(listInternet)
                                                rvService.adapter = rvInternet
                                            }
                                }
                                "Ночной интернет".lowercase(Locale.getDefault())->{
                                    rvInternet = RvInternet(object:RvInternet.OnItemClickListener{
                                        override fun onItemClick(smsMoney: Internet, position: Int) {
                                            var alertDialog = AlertDialog.Builder(
                                                requireContext(),
                                                R.style.BottomSheetDialogThem
                                            )
                                            val create = alertDialog.create()
                                            var dialogServiceBinding = DialogServiceBinding.inflate(
                                                LayoutInflater.from(requireContext()), null, false
                                            )
                                            create.setView(dialogServiceBinding.root)
                                            dialogServiceBinding.clouse.text = onAttach.getString(R.string.orqaga)
                                            dialogServiceBinding.active.text = onAttach.getText(R.string.aktivlashtirish)
                                            dialogServiceBinding.active.setOnClickListener {
                                                val substring =
                                                    smsMoney.activeCode?.trim()?.substring(smsMoney.activeCode?.trim()?.length!! - 1)
                                                var Num: String
                                                if (substring == "#") {
                                                    Num = smsMoney.activeCode?.trim()?.substring(
                                                        0,
                                                        smsMoney.activeCode?.trim()?.length?.minus(1)!!
                                                    ) + Uri.encode("#")
                                                } else {
                                                    Num = smsMoney.activeCode?.trim()?.substring(
                                                        0,
                                                        smsMoney.activeCode?.trim()?.length?.minus(2)!!
                                                    ) + Uri.encode("#")
                                                }
                                                var intent = Intent(Intent.ACTION_CALL)
                                                intent.data = Uri.parse("tel:$Num")
                                                try {
                                                    startActivity(intent)
                                                } catch (e: Exception) {
                                                    e.printStackTrace()
                                                }

                                            }
                                            dialogServiceBinding.clouse.setOnClickListener {
                                                create.dismiss()
                                            }
                                            dialogServiceBinding.share.setOnClickListener {
                                                var shareIntent = Intent().apply {
                                                    this.action = Intent.ACTION_SEND
                                                    this.putExtra(
                                                        Intent.EXTRA_SUBJECT,
                                                        "Uzmobile-${smsMoney.activeCode?.trim()}\n${smsMoney.mb?.trim()}"
                                                    )
                                                    this.putExtra(
                                                        Intent.EXTRA_TEXT,
                                                        "https://play.google.com/store/apps/details?id=uz.pdp.uzmobile"
                                                    )
                                                    this.type = "text/plain"
                                                }
                                                startActivity(shareIntent)
                                            }
                                            dialogServiceBinding.info.text =
                                                "${onAttach.getText(R.string.price)} ${smsMoney.price?.trim()}\n${onAttach.getText(R.string.chrafick)} ${smsMoney.mb?.trim()}\n${onAttach.getText(R.string.day)} ${smsMoney.day_count?.trim()}\n${onAttach.getString(R.string.active_ok)}:${smsMoney.activeCode?.trim()}".trim()
                                            dialogServiceBinding.name.text = smsMoney.mb?.trim()
                                            create.show()
                                        }
                                    })
                                    firebaseFirestore.collection("NightInternetRu").addSnapshotListener { value, error ->
                                                value?.documentChanges?.forEach {
                                                    when(it.type){
                                                        DocumentChange.Type.ADDED->{
                                                            val internet = it.document.toObject(Internet::class.java)
                                                            listInternet.add(internet)
                                                        }
                                                    }
                                                }
                                                rvInternet.submitList(listInternet)
                                                rvService.adapter = rvInternet
                                            }
                                }
                                "Интернет-пакеты для TAS-IX".lowercase(Locale.getDefault())->{
                                    rvInternet = RvInternet(object:RvInternet.OnItemClickListener{
                                        override fun onItemClick(smsMoney: Internet, position: Int) {
                                            var alertDialog = AlertDialog.Builder(
                                                requireContext(),
                                                R.style.BottomSheetDialogThem
                                            )
                                            val create = alertDialog.create()
                                            var dialogServiceBinding = DialogServiceBinding.inflate(
                                                LayoutInflater.from(requireContext()), null, false
                                            )
                                            create.setView(dialogServiceBinding.root)
                                            dialogServiceBinding.clouse.text = onAttach.getString(R.string.orqaga)
                                            dialogServiceBinding.active.text = onAttach.getText(R.string.aktivlashtirish)
                                            dialogServiceBinding.active.setOnClickListener {
                                                val substring =
                                                    smsMoney.activeCode?.trim()?.substring(smsMoney.activeCode?.trim()?.length!! - 1)
                                                var Num: String
                                                if (substring == "#") {
                                                    Num = smsMoney.activeCode?.trim()?.substring(
                                                        0,
                                                        smsMoney.activeCode?.trim()?.length?.minus(1)!!
                                                    ) + Uri.encode("#")
                                                } else {
                                                    Num = smsMoney.activeCode?.trim()?.substring(
                                                        0,
                                                        smsMoney.activeCode?.trim()?.length?.minus(2)!!
                                                    ) + Uri.encode("#")
                                                }
                                                var intent = Intent(Intent.ACTION_CALL)
                                                intent.data = Uri.parse("tel:$Num")
                                                try {
                                                    startActivity(intent)
                                                } catch (e: Exception) {
                                                    e.printStackTrace()
                                                }

                                            }
                                            dialogServiceBinding.clouse.setOnClickListener {
                                                create.dismiss()
                                            }
                                            dialogServiceBinding.share.setOnClickListener {
                                                var shareIntent = Intent().apply {
                                                    this.action = Intent.ACTION_SEND
                                                    this.putExtra(
                                                        Intent.EXTRA_SUBJECT,
                                                        "Uzmobile-${smsMoney.activeCode?.trim()}\n${smsMoney.mb?.trim()}"
                                                    )
                                                    this.putExtra(
                                                        Intent.EXTRA_TEXT,
                                                        "https://play.google.com/store/apps/details?id=uz.pdp.uzmobile"
                                                    )
                                                    this.type = "text/plain"
                                                }
                                                startActivity(shareIntent)
                                            }
                                            dialogServiceBinding.info.text =
                                                "${onAttach.getText(R.string.price)} ${smsMoney.price?.trim()}\n${onAttach.getText(R.string.chrafick)} ${smsMoney.mb?.trim()}\n${onAttach.getText(R.string.day)} ${smsMoney.day_count?.trim()}\n${onAttach.getString(R.string.active_ok)}:${smsMoney.activeCode?.trim()}".trim()
                                            dialogServiceBinding.name.text = smsMoney.mb?.trim()
                                            create.show()
                                        }
                                    })
                                            firebaseFirestore.collection("TasxInternetRu").addSnapshotListener { value, error ->
                                                value?.documentChanges?.forEach {
                                                    when(it.type){
                                                        DocumentChange.Type.ADDED->{
                                                            val internet = it.document.toObject(Internet::class.java)
                                                            listInternet.add(internet)
                                                        }
                                                    }
                                                }
                                                rvInternet.submitList(listInternet)
                                                rvService.adapter = rvInternet
                                            }
                                }
                                "Интернет без остановок".lowercase(Locale.getDefault())->{
                                    rvNonStop = RvNonStop(object:RvNonStop.OnItemClickListener{
                                        override fun onItemClick(smsMoney: NonStop, position: Int) {
                                            var alertDialog = AlertDialog.Builder(
                                                requireContext(),
                                                R.style.BottomSheetDialogThem
                                            )
                                            val create = alertDialog.create()
                                            var dialogServiceBinding = DialogServiceBinding.inflate(
                                                LayoutInflater.from(requireContext()), null, false
                                            )
                                            create.setView(dialogServiceBinding.root)
                                            dialogServiceBinding.clouse.text = onAttach.getString(R.string.orqaga)
                                            dialogServiceBinding.active.text = onAttach.getText(R.string.aktivlashtirish)
                                            dialogServiceBinding.active.setOnClickListener {
                                                val substring =
                                                    smsMoney.activeCode?.trim()?.substring(smsMoney.activeCode?.trim()?.length!! - 1)
                                                var Num: String
                                                if (substring == "#") {
                                                    Num = smsMoney.activeCode?.trim()?.substring(
                                                        0,
                                                        smsMoney.activeCode?.trim()?.length?.minus(1)!!
                                                    ) + Uri.encode("#")
                                                } else {
                                                    Num = smsMoney.activeCode?.trim()?.substring(
                                                        0,
                                                        smsMoney.activeCode?.trim()?.length?.minus(2)!!
                                                    ) + Uri.encode("#")
                                                }
                                                var intent = Intent(Intent.ACTION_CALL)
                                                intent.data = Uri.parse("tel:$Num")
                                                try {
                                                    startActivity(intent)
                                                } catch (e: Exception) {
                                                    e.printStackTrace()
                                                }

                                            }
                                            dialogServiceBinding.clouse.setOnClickListener {
                                                create.dismiss()
                                            }
                                            dialogServiceBinding.share.setOnClickListener {
                                                var shareIntent = Intent().apply {
                                                    this.action = Intent.ACTION_SEND
                                                    this.putExtra(
                                                        Intent.EXTRA_SUBJECT,
                                                        "Uzmobile-${smsMoney.activeCode?.trim()}\n${smsMoney.mb?.trim()}"
                                                    )
                                                    this.putExtra(
                                                        Intent.EXTRA_TEXT,
                                                        "https://play.google.com/store/apps/details?id=uz.pdp.uzmobile"
                                                    )
                                                    this.type = "text/plain"
                                                }
                                                startActivity(shareIntent)
                                            }
                                            dialogServiceBinding.info.text =
                                                "${onAttach.getText(R.string.price)} ${smsMoney.price?.trim()}\n${onAttach.getText(R.string.chrafick)} ${smsMoney.mb?.trim()}\n${onAttach.getText(R.string.day)} ${smsMoney.day_count?.trim()}\n${onAttach.getString(R.string.active_ok)}:${smsMoney.activeCode?.trim()}\n ${onAttach.getText(R.string.clouse)}:${smsMoney.offCode}".trim()
                                            dialogServiceBinding.name.text = smsMoney.mb?.trim()
                                            create.show()
                                        }
                                    })
                                            firebaseFirestore.collection("Non-StopRu").addSnapshotListener { value, error ->
                                                value?.documentChanges?.forEach {
                                                    when(it.type){
                                                        DocumentChange.Type.ADDED->{
                                                            val internet = it.document.toObject(NonStop::class.java)
                                                            listNonstop.add(internet)
                                                        }
                                                    }
                                                }
                                                rvNonStop.submitList(listNonstop)
                                                rvService.adapter = rvNonStop
                                            }
                                }
                                "Специально для тарифного плана ОБРАЗОВАНИЕ\nинтернет-пакеты".lowercase(Locale.getDefault())->{
                                    //CourceInternet
                                    rvInternet = RvInternet(object:RvInternet.OnItemClickListener{
                                        override fun onItemClick(smsMoney: Internet, position: Int) {
                                            var alertDialog = AlertDialog.Builder(
                                                requireContext(),
                                                R.style.BottomSheetDialogThem
                                            )
                                            val create = alertDialog.create()
                                            var dialogServiceBinding = DialogServiceBinding.inflate(
                                                LayoutInflater.from(requireContext()), null, false
                                            )
                                            create.setView(dialogServiceBinding.root)
                                            dialogServiceBinding.clouse.text = onAttach.getString(R.string.orqaga)
                                            dialogServiceBinding.active.text = onAttach.getText(R.string.aktivlashtirish)
                                            dialogServiceBinding.active.setOnClickListener {
                                                val substring =
                                                    smsMoney.activeCode?.trim()?.substring(smsMoney.activeCode?.trim()?.length!! - 1)
                                                var Num: String
                                                if (substring == "#") {
                                                    Num = smsMoney.activeCode?.trim()?.substring(
                                                        0,
                                                        smsMoney.activeCode?.trim()?.length?.minus(1)!!
                                                    ) + Uri.encode("#")
                                                } else {
                                                    Num = smsMoney.activeCode?.trim()?.substring(
                                                        0,
                                                        smsMoney.activeCode?.trim()?.length?.minus(2)!!
                                                    ) + Uri.encode("#")
                                                }
                                                var intent = Intent(Intent.ACTION_CALL)
                                                intent.data = Uri.parse("tel:$Num")
                                                try {
                                                    startActivity(intent)
                                                } catch (e: Exception) {
                                                    e.printStackTrace()
                                                }

                                            }
                                            dialogServiceBinding.clouse.setOnClickListener {
                                                create.dismiss()
                                            }
                                            dialogServiceBinding.share.setOnClickListener {
                                                var shareIntent = Intent().apply {
                                                    this.action = Intent.ACTION_SEND
                                                    this.putExtra(
                                                        Intent.EXTRA_SUBJECT,
                                                        "Uzmobile-${smsMoney.activeCode?.trim()}\n${smsMoney.mb?.trim()}"
                                                    )
                                                    this.putExtra(
                                                        Intent.EXTRA_TEXT,
                                                        "https://play.google.com/store/apps/details?id=uz.pdp.uzmobile"
                                                    )
                                                    this.type = "text/plain"
                                                }
                                                startActivity(shareIntent)
                                            }
                                            dialogServiceBinding.info.text =
                                                "${onAttach.getText(R.string.price)} ${smsMoney.price?.trim()}\n${onAttach.getText(R.string.chrafick)} ${smsMoney.mb?.trim()}\n${onAttach.getText(R.string.day)} ${smsMoney.day_count?.trim()}\n${onAttach.getString(R.string.active_ok)}:${smsMoney.activeCode?.trim()}".trim()
                                            dialogServiceBinding.name.text = smsMoney.mb?.trim()
                                            create.show()
                                        }
                                    })
                                            firebaseFirestore.collection("CourceInternetRu").addSnapshotListener { value, error ->
                                                value?.documentChanges?.forEach {
                                                    when(it.type){
                                                        DocumentChange.Type.ADDED->{
                                                            val internet = it.document.toObject(Internet::class.java)
                                                            listInternet.add(internet)
                                                        }
                                                    }
                                                }
                                                rvInternet.submitList(listInternet)
                                                rvService.adapter = rvInternet
                                            }
                                }
                                "<<Constructor>> Подписчики TR\nИнтернет-пакеты для".lowercase(Locale.getDefault())->{
                                    rvInternet = RvInternet(object:RvInternet.OnItemClickListener{
                                        override fun onItemClick(smsMoney: Internet, position: Int) {
                                            var alertDialog = AlertDialog.Builder(
                                                requireContext(),
                                                R.style.BottomSheetDialogThem
                                            )
                                            val create = alertDialog.create()
                                            var dialogServiceBinding = DialogServiceBinding.inflate(
                                                LayoutInflater.from(requireContext()), null, false
                                            )
                                            create.setView(dialogServiceBinding.root)
                                            dialogServiceBinding.clouse.text = onAttach.getString(R.string.orqaga)
                                            dialogServiceBinding.active.text = onAttach.getText(R.string.aktivlashtirish)
                                            dialogServiceBinding.active.setOnClickListener {
                                                val substring =
                                                    smsMoney.activeCode?.trim()?.substring(smsMoney.activeCode?.trim()?.length!! - 1)
                                                var Num: String
                                                if (substring == "#") {
                                                    Num = smsMoney.activeCode?.trim()?.substring(
                                                        0,
                                                        smsMoney.activeCode?.trim()?.length?.minus(1)!!
                                                    ) + Uri.encode("#")
                                                } else {
                                                    Num = smsMoney.activeCode?.trim()?.substring(
                                                        0,
                                                        smsMoney.activeCode?.trim()?.length?.minus(2)!!
                                                    ) + Uri.encode("#")
                                                }
                                                var intent = Intent(Intent.ACTION_CALL)
                                                intent.data = Uri.parse("tel:$Num")
                                                try {
                                                    startActivity(intent)
                                                } catch (e: Exception) {
                                                    e.printStackTrace()
                                                }

                                            }
                                            dialogServiceBinding.clouse.setOnClickListener {
                                                create.dismiss()
                                            }
                                            dialogServiceBinding.share.setOnClickListener {
                                                var shareIntent = Intent().apply {
                                                    this.action = Intent.ACTION_SEND
                                                    this.putExtra(
                                                        Intent.EXTRA_SUBJECT,
                                                        "Uzmobile-${smsMoney.activeCode?.trim()}\n${smsMoney.mb?.trim()}"
                                                    )
                                                    this.putExtra(
                                                        Intent.EXTRA_TEXT,
                                                        "https://play.google.com/store/apps/details?id=uz.pdp.uzmobile"
                                                    )
                                                    this.type = "text/plain"
                                                }
                                                startActivity(shareIntent)
                                            }
                                            dialogServiceBinding.info.text =
                                                "${onAttach.getText(R.string.price)} ${smsMoney.price?.trim()}\n${onAttach.getText(R.string.chrafick)} ${smsMoney.mb?.trim()}\n${onAttach.getText(R.string.day)} ${smsMoney.day_count?.trim()}\n${onAttach.getString(R.string.active_ok)}:${smsMoney.activeCode?.trim()}".trim()
                                            dialogServiceBinding.name.text = smsMoney.mb?.trim()
                                            create.show()
                                        }
                                    })
                                            firebaseFirestore.collection("ConstructorTrRu").addSnapshotListener { value, error ->
                                                value?.documentChanges?.forEach {
                                                    when(it.type){
                                                        DocumentChange.Type.ADDED->{
                                                            val internet = it.document.toObject(Internet::class.java)
                                                            listInternet.add(internet)
                                                        }
                                                    }
                                                }
                                                rvInternet.submitList(listInternet)
                                                rvService.adapter = rvInternet
                                            }
                                }
                            }
                        }
                        "kril".lowercase(Locale.getDefault())->{
                                when(param1?.lowercase(Locale.getDefault())) {
                                    "Ойлик пакетлар".lowercase(Locale.getDefault()) -> {
                                        rvInternet =
                                            RvInternet(object : RvInternet.OnItemClickListener {
                                                override fun onItemClick(
                                                    smsMoney: Internet,
                                                    position: Int
                                                ) {
                                                    var alertDialog = AlertDialog.Builder(
                                                        requireContext(),
                                                        R.style.BottomSheetDialogThem
                                                    )
                                                    val create = alertDialog.create()
                                                    var dialogServiceBinding =
                                                        DialogServiceBinding.inflate(
                                                            LayoutInflater.from(requireContext()),
                                                            null,
                                                            false
                                                        )
                                                    create.setView(dialogServiceBinding.root)
                                                    dialogServiceBinding.active.setOnClickListener {
                                                        val substring =
                                                            smsMoney.activeCode?.trim()
                                                                ?.substring(smsMoney.activeCode?.trim()?.length!! - 1)
                                                        var Num: String
                                                        if (substring == "#") {
                                                            Num = smsMoney.activeCode?.trim()
                                                                ?.substring(
                                                                    0,
                                                                    smsMoney.activeCode?.trim()?.length?.minus(
                                                                        1
                                                                    )!!
                                                                ) + Uri.encode("#")
                                                        } else {
                                                            Num = smsMoney.activeCode?.trim()
                                                                ?.substring(
                                                                    0,
                                                                    smsMoney.activeCode?.trim()?.length?.minus(
                                                                        2
                                                                    )!!
                                                                ) + Uri.encode("#")
                                                        }
                                                        var intent = Intent(Intent.ACTION_CALL)
                                                        intent.data = Uri.parse("tel:$Num")
                                                        try {
                                                            startActivity(intent)
                                                        } catch (e: Exception) {
                                                            e.printStackTrace()
                                                        }

                                                    }
                                                    dialogServiceBinding.clouse.setOnClickListener {
                                                        create.dismiss()
                                                    }
                                                    dialogServiceBinding.share.setOnClickListener {
                                                        var shareIntent = Intent().apply {
                                                            this.action = Intent.ACTION_SEND
                                                            this.putExtra(
                                                                Intent.EXTRA_SUBJECT,
                                                                "Uzmobile-${smsMoney.activeCode?.trim()}\n${smsMoney.mb?.trim()}"
                                                            )
                                                            this.putExtra(
                                                                Intent.EXTRA_TEXT,
                                                                "https://play.google.com/store/apps/details?id=uz.pdp.uzmobile"
                                                            )
                                                            this.type = "text/plain"
                                                        }
                                                        startActivity(shareIntent)
                                                    }
                                                    dialogServiceBinding.info.text =
                                                        "To'plam narxi ${smsMoney.price?.trim()}\nBerilgan trafik ${smsMoney.mb?.trim()}\nAmalqilish muddati ${smsMoney.day_count?.trim()}\nFaollashtirish:${smsMoney.activeCode?.trim()}".trim()
                                                    dialogServiceBinding.name.text =
                                                        smsMoney.mb?.trim()
                                                    create.show()
                                                }
                                            })
                                                firebaseFirestore.collection("InternetMonthCril")
                                                    .addSnapshotListener { value, error ->
                                                        value?.documentChanges?.forEach {
                                                            when (it.type) {
                                                                DocumentChange.Type.ADDED -> {
                                                                    val internet =
                                                                        it.document.toObject(
                                                                            Internet::class.java
                                                                        )
                                                                    listInternet.add(internet)
                                                                }
                                                            }
                                                        }
                                                        rvInternet.submitList(listInternet)
                                                        rvService.adapter = rvInternet
                                                    }

                                    }
                                    "Кунлик пакетлар".lowercase(Locale.getDefault()) -> {
                                        rvInternet =
                                            RvInternet(object : RvInternet.OnItemClickListener {
                                                override fun onItemClick(
                                                    smsMoney: Internet,
                                                    position: Int
                                                ) {
                                                    var alertDialog = AlertDialog.Builder(
                                                        requireContext(),
                                                        R.style.BottomSheetDialogThem
                                                    )
                                                    val create = alertDialog.create()
                                                    var dialogServiceBinding =
                                                        DialogServiceBinding.inflate(
                                                            LayoutInflater.from(requireContext()),
                                                            null,
                                                            false
                                                        )
                                                    create.setView(dialogServiceBinding.root)
                                                    dialogServiceBinding.active.setOnClickListener {
                                                        val substring =
                                                            smsMoney.activeCode?.trim()
                                                                ?.substring(smsMoney.activeCode?.trim()?.length!! - 1)
                                                        var Num: String
                                                        if (substring == "#") {
                                                            Num = smsMoney.activeCode?.trim()
                                                                ?.substring(
                                                                    0,
                                                                    smsMoney.activeCode?.trim()?.length?.minus(
                                                                        1
                                                                    )!!
                                                                ) + Uri.encode("#")
                                                        } else {
                                                            Num = smsMoney.activeCode?.trim()
                                                                ?.substring(
                                                                    0,
                                                                    smsMoney.activeCode?.trim()?.length?.minus(
                                                                        2
                                                                    )!!
                                                                ) + Uri.encode("#")
                                                        }
                                                        var intent = Intent(Intent.ACTION_CALL)
                                                        intent.data = Uri.parse("tel:$Num")
                                                        try {
                                                            startActivity(intent)
                                                        } catch (e: Exception) {
                                                            e.printStackTrace()
                                                        }

                                                    }
                                                    dialogServiceBinding.clouse.setOnClickListener {
                                                        create.dismiss()
                                                    }
                                                    dialogServiceBinding.share.setOnClickListener {
                                                        var shareIntent = Intent().apply {
                                                            this.action = Intent.ACTION_SEND
                                                            this.putExtra(
                                                                Intent.EXTRA_SUBJECT,
                                                                "Uzmobile-${smsMoney.activeCode?.trim()}\n${smsMoney.mb?.trim()}"
                                                            )
                                                            this.putExtra(
                                                                Intent.EXTRA_TEXT,
                                                                "https://play.google.com/store/apps/details?id=uz.pdp.uzmobile"
                                                            )
                                                            this.type = "text/plain"
                                                        }
                                                        startActivity(shareIntent)
                                                    }
                                                    dialogServiceBinding.info.text =
                                                        "To'plam narxi ${smsMoney.price?.trim()}\nBerilgan trafik ${smsMoney.mb?.trim()}\nAmalqilish muddati ${smsMoney.day_count?.trim()}\nFaollashtirish:${smsMoney.activeCode?.trim()}".trim()
                                                    dialogServiceBinding.name.text =
                                                        smsMoney.mb?.trim()
                                                    create.show()
                                                }
                                            })
                                                firebaseFirestore.collection("DayInternetCril")
                                                    .addSnapshotListener { value, error ->
                                                        value?.documentChanges?.forEach {
                                                            when (it.type) {
                                                                DocumentChange.Type.ADDED -> {
                                                                    val internet =
                                                                        it.document.toObject(
                                                                            Internet::class.java
                                                                        )
                                                                    listInternet.add(internet)
                                                                }
                                                            }
                                                        }
                                                        rvInternet.submitList(listInternet)
                                                        rvService.adapter = rvInternet
                                                    }
                                    }
                                    "Тунги интернет".lowercase(Locale.getDefault()) -> {
                                        rvInternet =
                                            RvInternet(object : RvInternet.OnItemClickListener {
                                                override fun onItemClick(
                                                    smsMoney: Internet,
                                                    position: Int
                                                ) {
                                                    var alertDialog = AlertDialog.Builder(
                                                        requireContext(),
                                                        R.style.BottomSheetDialogThem
                                                    )
                                                    val create = alertDialog.create()
                                                    var dialogServiceBinding =
                                                        DialogServiceBinding.inflate(
                                                            LayoutInflater.from(requireContext()),
                                                            null,
                                                            false
                                                        )
                                                    create.setView(dialogServiceBinding.root)
                                                    dialogServiceBinding.active.setOnClickListener {
                                                        val substring =
                                                            smsMoney.activeCode?.trim()
                                                                ?.substring(smsMoney.activeCode?.trim()?.length!! - 1)
                                                        var Num: String
                                                        if (substring == "#") {
                                                            Num = smsMoney.activeCode?.trim()
                                                                ?.substring(
                                                                    0,
                                                                    smsMoney.activeCode?.trim()?.length?.minus(
                                                                        1
                                                                    )!!
                                                                ) + Uri.encode("#")
                                                        } else {
                                                            Num = smsMoney.activeCode?.trim()
                                                                ?.substring(
                                                                    0,
                                                                    smsMoney.activeCode?.trim()?.length?.minus(
                                                                        2
                                                                    )!!
                                                                ) + Uri.encode("#")
                                                        }
                                                        var intent = Intent(Intent.ACTION_CALL)
                                                        intent.data = Uri.parse("tel:$Num")
                                                        try {
                                                            startActivity(intent)
                                                        } catch (e: Exception) {
                                                            e.printStackTrace()
                                                        }

                                                    }
                                                    dialogServiceBinding.clouse.setOnClickListener {
                                                        create.dismiss()
                                                    }
                                                    dialogServiceBinding.share.setOnClickListener {
                                                        var shareIntent = Intent().apply {
                                                            this.action = Intent.ACTION_SEND
                                                            this.putExtra(
                                                                Intent.EXTRA_SUBJECT,
                                                                "Uzmobile-${smsMoney.activeCode?.trim()}\n${smsMoney.mb?.trim()}"
                                                            )
                                                            this.putExtra(
                                                                Intent.EXTRA_TEXT,
                                                                "https://play.google.com/store/apps/details?id=uz.pdp.uzmobile"
                                                            )
                                                            this.type = "text/plain"
                                                        }
                                                        startActivity(shareIntent)
                                                    }
                                                    dialogServiceBinding.info.text =
                                                        "To'plam narxi ${smsMoney.price?.trim()}\nBerilgan trafik ${smsMoney.mb?.trim()}\nAmalqilish muddati ${smsMoney.day_count?.trim()}\nFaollashtirish:${smsMoney.activeCode?.trim()}".trim()
                                                    dialogServiceBinding.name.text =
                                                        smsMoney.mb?.trim()
                                                    create.show()
                                                }
                                            })
                                                firebaseFirestore.collection("NightInternetCril")
                                                    .addSnapshotListener { value, error ->
                                                        value?.documentChanges?.forEach {
                                                            when (it.type) {
                                                                DocumentChange.Type.ADDED -> {
                                                                    val internet =
                                                                        it.document.toObject(
                                                                            Internet::class.java
                                                                        )
                                                                    listInternet.add(internet)
                                                                }
                                                            }
                                                        }
                                                        rvInternet.submitList(listInternet)
                                                        rvService.adapter = rvInternet
                                                    }
                                    }
                                    "ТAС-ИХ учун интернет пакетлари".lowercase(Locale.getDefault()) -> {
                                        rvInternet =
                                            RvInternet(object : RvInternet.OnItemClickListener {
                                                override fun onItemClick(
                                                    smsMoney: Internet,
                                                    position: Int
                                                ) {
                                                    var alertDialog = AlertDialog.Builder(
                                                        requireContext(),
                                                        R.style.BottomSheetDialogThem
                                                    )
                                                    val create = alertDialog.create()
                                                    var dialogServiceBinding =
                                                        DialogServiceBinding.inflate(
                                                            LayoutInflater.from(requireContext()),
                                                            null,
                                                            false
                                                        )
                                                    create.setView(dialogServiceBinding.root)
                                                    dialogServiceBinding.active.setOnClickListener {
                                                        val substring =
                                                            smsMoney.activeCode?.trim()
                                                                ?.substring(smsMoney.activeCode?.trim()?.length!! - 1)
                                                        var Num: String
                                                        if (substring == "#") {
                                                            Num = smsMoney.activeCode?.trim()
                                                                ?.substring(
                                                                    0,
                                                                    smsMoney.activeCode?.trim()?.length?.minus(
                                                                        1
                                                                    )!!
                                                                ) + Uri.encode("#")
                                                        } else {
                                                            Num = smsMoney.activeCode?.trim()
                                                                ?.substring(
                                                                    0,
                                                                    smsMoney.activeCode?.trim()?.length?.minus(
                                                                        2
                                                                    )!!
                                                                ) + Uri.encode("#")
                                                        }
                                                        var intent = Intent(Intent.ACTION_CALL)
                                                        intent.data = Uri.parse("tel:$Num")
                                                        try {
                                                            startActivity(intent)
                                                        } catch (e: Exception) {
                                                            e.printStackTrace()
                                                        }

                                                    }
                                                    dialogServiceBinding.clouse.setOnClickListener {
                                                        create.dismiss()
                                                    }
                                                    dialogServiceBinding.share.setOnClickListener {
                                                        var shareIntent = Intent().apply {
                                                            this.action = Intent.ACTION_SEND
                                                            this.putExtra(
                                                                Intent.EXTRA_SUBJECT,
                                                                "Uzmobile-${smsMoney.activeCode?.trim()}\n${smsMoney.mb?.trim()}"
                                                            )
                                                            this.putExtra(
                                                                Intent.EXTRA_TEXT,
                                                                "https://play.google.com/store/apps/details?id=uz.pdp.uzmobile"
                                                            )
                                                            this.type = "text/plain"
                                                        }
                                                        startActivity(shareIntent)
                                                    }
                                                    dialogServiceBinding.info.text =
                                                        "To'plam narxi ${smsMoney.price?.trim()}\nBerilgan trafik ${smsMoney.mb?.trim()}\nAmalqilish muddati ${smsMoney.day_count?.trim()}\nFaollashtirish:${smsMoney.activeCode?.trim()}".trim()
                                                    dialogServiceBinding.name.text =
                                                        smsMoney.mb?.trim()
                                                    create.show()
                                                }
                                            })
                                                firebaseFirestore.collection("TasxInternetCril")
                                                    .addSnapshotListener { value, error ->
                                                        value?.documentChanges?.forEach {
                                                            when (it.type) {
                                                                DocumentChange.Type.ADDED -> {
                                                                    val internet =
                                                                        it.document.toObject(
                                                                            Internet::class.java
                                                                        )
                                                                    listInternet.add(internet)
                                                                }
                                                            }
                                                        }
                                                        rvInternet.submitList(listInternet)
                                                        rvService.adapter = rvInternet
                                                    }
                                    }
                                    "Интернет нон-стоп".lowercase(Locale.getDefault()) -> {
                                        rvNonStop =
                                            RvNonStop(object : RvNonStop.OnItemClickListener {
                                                override fun onItemClick(
                                                    smsMoney: NonStop,
                                                    position: Int
                                                ) {
                                                    var alertDialog = AlertDialog.Builder(
                                                        requireContext(),
                                                        R.style.BottomSheetDialogThem
                                                    )
                                                    val create = alertDialog.create()
                                                    var dialogServiceBinding =
                                                        DialogServiceBinding.inflate(
                                                            LayoutInflater.from(requireContext()),
                                                            null,
                                                            false
                                                        )
                                                    create.setView(dialogServiceBinding.root)
                                                    dialogServiceBinding.active.setOnClickListener {
                                                        val substring =
                                                            smsMoney.activeCode?.trim()
                                                                ?.substring(smsMoney.activeCode?.trim()?.length!! - 1)
                                                        var Num: String
                                                        if (substring == "#") {
                                                            Num = smsMoney.activeCode?.trim()
                                                                ?.substring(
                                                                    0,
                                                                    smsMoney.activeCode?.trim()?.length?.minus(
                                                                        1
                                                                    )!!
                                                                ) + Uri.encode("#")
                                                        } else {
                                                            Num = smsMoney.activeCode?.trim()
                                                                ?.substring(
                                                                    0,
                                                                    smsMoney.activeCode?.trim()?.length?.minus(
                                                                        2
                                                                    )!!
                                                                ) + Uri.encode("#")
                                                        }
                                                        var intent = Intent(Intent.ACTION_CALL)
                                                        intent.data = Uri.parse("tel:$Num")
                                                        try {
                                                            startActivity(intent)
                                                        } catch (e: Exception) {
                                                            e.printStackTrace()
                                                        }

                                                    }
                                                    dialogServiceBinding.clouse.setOnClickListener {
                                                        create.dismiss()
                                                    }
                                                    dialogServiceBinding.share.setOnClickListener {
                                                        var shareIntent = Intent().apply {
                                                            this.action = Intent.ACTION_SEND
                                                            this.putExtra(
                                                                Intent.EXTRA_SUBJECT,
                                                                "Uzmobile-${smsMoney.activeCode?.trim()}\n${smsMoney.mb?.trim()}"
                                                            )
                                                            this.putExtra(
                                                                Intent.EXTRA_TEXT,
                                                                "https://play.google.com/store/apps/details?id=uz.pdp.uzmobile"
                                                            )
                                                            this.type = "text/plain"
                                                        }
                                                        startActivity(shareIntent)
                                                    }
                                                    dialogServiceBinding.info.text =
                                                        "${onAttach.getText(R.string.price)} ${smsMoney.price?.trim()}\n${onAttach.getText(R.string.chrafick)} ${smsMoney.mb?.trim()}\n${onAttach.getText(R.string.day)} ${smsMoney.day_count?.trim()}\n${onAttach.getString(R.string.active_ok)}:${smsMoney.activeCode?.trim()}\n ${onAttach.getText(R.string.clouse)}:${smsMoney.offCode}".trim()
                                                    dialogServiceBinding.name.text =
                                                        smsMoney.mb?.trim()
                                                    create.show()
                                                }
                                            })
                                                firebaseFirestore.collection("Non-StopCril")
                                                    .addSnapshotListener { value, error ->
                                                        value?.documentChanges?.forEach {
                                                            when (it.type) {
                                                                DocumentChange.Type.ADDED -> {
                                                                    val internet =
                                                                        it.document.toObject(NonStop::class.java)
                                                                    listNonstop.add(internet)
                                                                }
                                                            }
                                                        }
                                                        rvNonStop.submitList(listNonstop)
                                                        rvService.adapter = rvNonStop
                                                    }
                                    }
                                    "ТAъЛИМ тариф режаси учун махсус\nинтернет-пакетлар".lowercase(Locale.getDefault()) -> {
                                        //CourceInternet
                                        rvInternet =
                                            RvInternet(object : RvInternet.OnItemClickListener {
                                                override fun onItemClick(
                                                    smsMoney: Internet,
                                                    position: Int
                                                ) {
                                                    var alertDialog = AlertDialog.Builder(
                                                        requireContext(),
                                                        R.style.BottomSheetDialogThem
                                                    )
                                                    val create = alertDialog.create()
                                                    var dialogServiceBinding =
                                                        DialogServiceBinding.inflate(
                                                            LayoutInflater.from(requireContext()),
                                                            null,
                                                            false
                                                        )
                                                    create.setView(dialogServiceBinding.root)
                                                    dialogServiceBinding.active.setOnClickListener {
                                                        val substring =
                                                            smsMoney.activeCode?.trim()
                                                                ?.substring(smsMoney.activeCode?.trim()?.length!! - 1)
                                                        var Num: String
                                                        if (substring == "#") {
                                                            Num = smsMoney.activeCode?.trim()
                                                                ?.substring(
                                                                    0,
                                                                    smsMoney.activeCode?.trim()?.length?.minus(
                                                                        1
                                                                    )!!
                                                                ) + Uri.encode("#")
                                                        } else {
                                                            Num = smsMoney.activeCode?.trim()
                                                                ?.substring(
                                                                    0,
                                                                    smsMoney.activeCode?.trim()?.length?.minus(
                                                                        2
                                                                    )!!
                                                                ) + Uri.encode("#")
                                                        }
                                                        var intent = Intent(Intent.ACTION_CALL)
                                                        intent.data = Uri.parse("tel:$Num")
                                                        try {
                                                            startActivity(intent)
                                                        } catch (e: Exception) {
                                                            e.printStackTrace()
                                                        }

                                                    }
                                                    dialogServiceBinding.clouse.setOnClickListener {
                                                        create.dismiss()
                                                    }
                                                    dialogServiceBinding.share.setOnClickListener {
                                                        var shareIntent = Intent().apply {
                                                            this.action = Intent.ACTION_SEND
                                                            this.putExtra(
                                                                Intent.EXTRA_SUBJECT,
                                                                "Uzmobile-${smsMoney.activeCode?.trim()}\n${smsMoney.mb?.trim()}"
                                                            )
                                                            this.putExtra(
                                                                Intent.EXTRA_TEXT,
                                                                "https://play.google.com/store/apps/details?id=uz.pdp.uzmobile"
                                                            )
                                                            this.type = "text/plain"
                                                        }
                                                        startActivity(shareIntent)
                                                    }
                                                    dialogServiceBinding.info.text =
                                                        "To'plam narxi ${smsMoney.price?.trim()}\nBerilgan trafik ${smsMoney.mb?.trim()}\nAmalqilish muddati ${smsMoney.day_count?.trim()}\nFaollashtirish:${smsMoney.activeCode?.trim()}".trim()
                                                    dialogServiceBinding.name.text =
                                                        smsMoney.mb?.trim()
                                                    create.show()
                                                }
                                            })
                                                firebaseFirestore.collection("CourceInternetCril")
                                                    .addSnapshotListener { value, error ->
                                                        value?.documentChanges?.forEach {
                                                            when (it.type) {
                                                                DocumentChange.Type.ADDED -> {
                                                                    val internet =
                                                                        it.document.toObject(
                                                                            Internet::class.java
                                                                        )
                                                                    listInternet.add(internet)
                                                                }
                                                            }
                                                        }
                                                        rvInternet.submitList(listInternet)
                                                        rvService.adapter = rvInternet
                                                    }
                                    }
                                    "<<Cонструcтор>> ТР абонентлари\nучун интернет пакетлари".lowercase(Locale.getDefault()) -> {
                                        rvInternet =
                                            RvInternet(object : RvInternet.OnItemClickListener {
                                                override fun onItemClick(
                                                    smsMoney: Internet,
                                                    position: Int
                                                ) {
                                                    var alertDialog = AlertDialog.Builder(
                                                        requireContext(),
                                                        R.style.BottomSheetDialogThem
                                                    )
                                                    val create = alertDialog.create()
                                                    var dialogServiceBinding =
                                                        DialogServiceBinding.inflate(
                                                            LayoutInflater.from(requireContext()),
                                                            null,
                                                            false
                                                        )
                                                    create.setView(dialogServiceBinding.root)
                                                    dialogServiceBinding.active.setOnClickListener {
                                                        val substring =
                                                            smsMoney.activeCode?.trim()
                                                                ?.substring(smsMoney.activeCode?.trim()?.length!! - 1)
                                                        var Num: String
                                                        if (substring == "#") {
                                                            Num = smsMoney.activeCode?.trim()
                                                                ?.substring(
                                                                    0,
                                                                    smsMoney.activeCode?.trim()?.length?.minus(
                                                                        1
                                                                    )!!
                                                                ) + Uri.encode("#")
                                                        } else {
                                                            Num = smsMoney.activeCode?.trim()
                                                                ?.substring(
                                                                    0,
                                                                    smsMoney.activeCode?.trim()?.length?.minus(
                                                                        2
                                                                    )!!
                                                                ) + Uri.encode("#")
                                                        }
                                                        var intent = Intent(Intent.ACTION_CALL)
                                                        intent.data = Uri.parse("tel:$Num")
                                                        try {
                                                            startActivity(intent)
                                                        } catch (e: Exception) {
                                                            e.printStackTrace()
                                                        }

                                                    }
                                                    dialogServiceBinding.clouse.setOnClickListener {
                                                        create.dismiss()
                                                    }
                                                    dialogServiceBinding.share.setOnClickListener {
                                                        var shareIntent = Intent().apply {
                                                            this.action = Intent.ACTION_SEND
                                                            this.putExtra(
                                                                Intent.EXTRA_SUBJECT,
                                                                "Uzmobile-${smsMoney.activeCode?.trim()}\n${smsMoney.mb?.trim()}"
                                                            )
                                                            this.putExtra(
                                                                Intent.EXTRA_TEXT,
                                                                "https://play.google.com/store/apps/details?id=uz.pdp.uzmobile"
                                                            )
                                                            this.type = "text/plain"
                                                        }
                                                        startActivity(shareIntent)
                                                    }
                                                    dialogServiceBinding.info.text =
                                                        "To'plam narxi ${smsMoney.price?.trim()}\nBerilgan trafik ${smsMoney.mb?.trim()}\nAmalqilish muddati ${smsMoney.day_count?.trim()}\nFaollashtirish:${smsMoney.activeCode?.trim()}".trim()
                                                    dialogServiceBinding.name.text =
                                                        smsMoney.mb?.trim()
                                                    create.show()
                                                }
                                            })
                                                firebaseFirestore.collection("ContructorCrilCril")
                                                    .addSnapshotListener { value, error ->
                                                        value?.documentChanges?.forEach {
                                                            when (it.type) {
                                                                DocumentChange.Type.ADDED -> {
                                                                    val internet =
                                                                        it.document.toObject(
                                                                            Internet::class.java
                                                                        )
                                                                    listInternet.add(internet)
                                                                }
                                                            }
                                                        }
                                                        rvInternet.submitList(listInternet)
                                                        rvService.adapter = rvInternet
                                                    }
                                    }
                                                    }
                        }
                    }
                }

                "SMS".lowercase(Locale.getDefault())->{
                    listSMS = ArrayList()
                    when(language?.lowercase(Locale.getDefault())){
                        "uz".lowercase(Locale.getDefault())-> {
                            when (param1?.lowercase(Locale.getDefault())) {
                                "Kunlik SMS paketlar".lowercase(Locale.getDefault()) -> {
                                    rvSMS = RvSMS(object : RvSMS.OnItemClickListener {
                                        override fun onItemClick(sms: SMS, position: Int) {
                                            var alertDialog = AlertDialog.Builder(
                                                requireContext(),
                                                R.style.BottomSheetDialogThem
                                            )
                                            val create = alertDialog.create()
                                            var dialogServiceBinding = DialogServiceBinding.inflate(
                                                LayoutInflater.from(requireContext()), null, false
                                            )
                                            create.setView(dialogServiceBinding.root)
                                            dialogServiceBinding.active.text = onAttach.getText(R.string.aktivlashtirish)
                                            dialogServiceBinding.clouse.text = onAttach.getText(R.string.orqaga)
                                            dialogServiceBinding.active.setOnClickListener {
                                                val substring =
                                                    sms.activeCode?.trim()
                                                        ?.substring(sms.activeCode?.trim()?.length!! - 1)
                                                var Num: String
                                                if (substring == "#") {
                                                    Num = sms.activeCode?.trim()?.substring(
                                                        0,
                                                        sms.activeCode?.trim()?.length?.minus(1)!!
                                                    ) + Uri.encode("#")
                                                } else {
                                                    Num = sms.activeCode?.trim()?.substring(
                                                        0,
                                                        sms.activeCode?.trim()?.length?.minus(2)!!
                                                    ) + Uri.encode("#")
                                                }
                                                var intent = Intent(Intent.ACTION_CALL)
                                                intent.data = Uri.parse("tel:$Num")
                                                try {
                                                    startActivity(intent)
                                                } catch (e: Exception) {
                                                    e.printStackTrace()
                                                }

                                            }
                                            dialogServiceBinding.clouse.setOnClickListener {
                                                create.dismiss()
                                            }
                                            dialogServiceBinding.share.setOnClickListener {
                                                var shareIntent = Intent().apply {
                                                    this.action = Intent.ACTION_SEND
                                                    this.putExtra(
                                                        Intent.EXTRA_SUBJECT,
                                                        "Uzmobile-${sms.activeCode?.trim()}\n${sms.name?.trim()}"
                                                    )
                                                    this.putExtra(
                                                        Intent.EXTRA_TEXT,
                                                        "https://play.google.com/store/apps/details?id=uz.pdp.uzmobile"
                                                    )
                                                    this.type = "text/plain"
                                                }
                                                startActivity(shareIntent)
                                            }
                                            dialogServiceBinding.info.text =
                                                "${sms.price?.trim()}\n${sms.summ_user?.trim()}\n${sms.count_sms?.trim()}\n${sms.day_sms}\n${onAttach.getText(R.string.active_ok)}: ${sms.activeCode?.trim()}\n${onAttach.getText(R.string.clouse)}: ${sms.offCode?.trim()}".trim()
                                            dialogServiceBinding.name.text = sms.name?.trim()
                                            create.show()
                                        }
                                    })
                                    firebaseFirestore.collection("SMSDay")
                                        .addSnapshotListener { value, error ->
                                            value?.documentChanges?.forEach {
                                                when (it.type) {
                                                    DocumentChange.Type.ADDED -> {
                                                        val sms =
                                                            it.document.toObject(SMS::class.java)
                                                        listSMS.add(sms)
                                                    }
                                                }
                                            }
                                            rvSMS.submitList(listSMS)
                                            rvService.adapter = rvSMS
                                        }
                                }
                                "Oylik SMS paketlar".lowercase(Locale.getDefault()) -> {
                                    rvSMS = RvSMS(object : RvSMS.OnItemClickListener {
                                        override fun onItemClick(sms: SMS, position: Int) {
                                            var alertDialog = AlertDialog.Builder(
                                                requireContext(),
                                                R.style.BottomSheetDialogThem
                                            )
                                            val create = alertDialog.create()
                                            var dialogServiceBinding = DialogServiceBinding.inflate(
                                                LayoutInflater.from(requireContext()), null, false
                                            )
                                            create.setView(dialogServiceBinding.root)
                                            dialogServiceBinding.active.text = onAttach.getText(R.string.aktivlashtirish)
                                            dialogServiceBinding.clouse.text = onAttach.getText(R.string.orqaga)
                                            dialogServiceBinding.active.setOnClickListener {
                                                val substring =
                                                    sms.activeCode?.trim()
                                                        ?.substring(sms.activeCode?.trim()?.length!! - 1)
                                                var Num: String
                                                if (substring == "#") {
                                                    Num = sms.activeCode?.trim()?.substring(
                                                        0,
                                                        sms.activeCode?.trim()?.length?.minus(1)!!
                                                    ) + Uri.encode("#")
                                                } else {
                                                    Num = sms.activeCode?.trim()?.substring(
                                                        0,
                                                        sms.activeCode?.trim()?.length?.minus(2)!!
                                                    ) + Uri.encode("#")
                                                }
                                                var intent = Intent(Intent.ACTION_CALL)
                                                intent.data = Uri.parse("tel:$Num")
                                                try {
                                                    startActivity(intent)
                                                } catch (e: Exception) {
                                                    e.printStackTrace()
                                                }

                                            }
                                            dialogServiceBinding.clouse.setOnClickListener {
                                                create.dismiss()
                                            }
                                            dialogServiceBinding.share.setOnClickListener {
                                                var shareIntent = Intent().apply {
                                                    this.action = Intent.ACTION_SEND
                                                    this.putExtra(
                                                        Intent.EXTRA_SUBJECT,
                                                        "Uzmobile-${sms.activeCode?.trim()}\n${sms.name?.trim()}"
                                                    )
                                                    this.putExtra(
                                                        Intent.EXTRA_TEXT,
                                                        "https://play.google.com/store/apps/details?id=uz.pdp.uzmobile"
                                                    )
                                                    this.type = "text/plain"
                                                }
                                                startActivity(shareIntent)
                                            }
                                            dialogServiceBinding.info.text =
                                                "${sms.price?.trim()}\n${sms.summ_user?.trim()}\n${sms.count_sms?.trim()}\n${sms.day_sms}\n${onAttach.getText(R.string.active_ok)}: ${sms.activeCode?.trim()}\n${onAttach.getText(R.string.clouse)}: ${sms.offCode?.trim()}".trim()
                                            dialogServiceBinding.name.text = sms.name?.trim()
                                            create.show()
                                        }
                                    })
                                    firebaseFirestore.collection("SMSMonth")
                                        .addSnapshotListener { value, error ->
                                            value?.documentChanges?.forEach {
                                                when (it.type) {
                                                    DocumentChange.Type.ADDED -> {
                                                        val sms =
                                                            it.document.toObject(SMS::class.java)
                                                        listSMS.add(sms)
                                                    }
                                                }
                                            }
                                            rvSMS.submitList(listSMS)
                                            rvService.adapter = rvSMS
                                        }
                                }
                                "Xalqaro SMS paketlar".lowercase(Locale.getDefault()) -> {
                                    listSMSCountry = ArrayList()
                                    rvSMSCountry =
                                        RvSMSCountry(object : RvSMSCountry.OnItemClickListener {
                                            override fun onItemClick(
                                                sms: SMSCountry,
                                                position: Int
                                            ) {
                                                var alertDialog = AlertDialog.Builder(
                                                    requireContext(),
                                                    R.style.BottomSheetDialogThem
                                                )
                                                val create = alertDialog.create()
                                                var dialogServiceBinding =
                                                    DialogCountryBinding.inflate(
                                                        LayoutInflater.from(requireContext()),
                                                        null,
                                                        false
                                                    )
                                                create.setView(dialogServiceBinding.root)
                                                dialogServiceBinding.active.text = onAttach.getText(R.string.aktivlashtirish)
                                                dialogServiceBinding.clouse.text = onAttach.getText(R.string.orqaga)
                                                dialogServiceBinding.active.setOnClickListener {
                                                    val substring =
                                                        sms.activeCode?.trim()
                                                            ?.substring(sms.activeCode?.trim()?.length!! - 1)
                                                    var Num: String
                                                    if (substring == "#") {
                                                        Num = sms.activeCode?.trim()?.substring(
                                                            0,
                                                            sms.activeCode?.trim()?.length?.minus(1)!!
                                                        ) + Uri.encode("#")
                                                    } else {
                                                        Num = sms.activeCode?.trim()?.substring(
                                                            0,
                                                            sms.activeCode?.trim()?.length?.minus(2)!!
                                                        ) + Uri.encode("#")
                                                    }
                                                    var intent = Intent(Intent.ACTION_CALL)
                                                    intent.data = Uri.parse("tel:$Num")
                                                    try {
                                                        startActivity(intent)
                                                    } catch (e: Exception) {
                                                        e.printStackTrace()
                                                    }

                                                }
                                                dialogServiceBinding.clouse.setOnClickListener {
                                                    create.dismiss()
                                                }
                                                dialogServiceBinding.share.setOnClickListener {
                                                    var shareIntent = Intent().apply {
                                                        this.action = Intent.ACTION_SEND
                                                        this.putExtra(
                                                            Intent.EXTRA_SUBJECT,
                                                            "Uzmobile-${sms.activeCode?.trim()}\n${sms.name?.trim()}"
                                                        )
                                                        this.putExtra(
                                                            Intent.EXTRA_TEXT,
                                                            "https://play.google.com/store/apps/details?id=uz.pdp.uzmobile"
                                                        )
                                                        this.type = "text/plain"
                                                    }
                                                    startActivity(shareIntent)
                                                }
                                                dialogServiceBinding.info.text =
                                                    "${sms.price?.trim()}\n${sms.count_sms?.trim()}\n${sms.day_sms}\n${onAttach.getText(R.string.active_ok)}: ${sms.activeCode?.trim()}".trim()
                                                dialogServiceBinding.info1.text = sms.info
                                                dialogServiceBinding.name.text = sms.name?.trim()
                                                create.show()
                                            }
                                        })
                                    firebaseFirestore.collection("SMSCountry")
                                        .addSnapshotListener { value, error ->
                                            value?.documentChanges?.forEach {
                                                when (it.type) {
                                                    DocumentChange.Type.ADDED -> {
                                                        val smsCountry =
                                                            it.document.toObject(SMSCountry::class.java)
                                                        listSMSCountry.add(smsCountry)
                                                    }
                                                }
                                            }
                                            rvSMSCountry.submitList(listSMSCountry)
                                            rvService.adapter = rvSMSCountry
                                        }
                                }
                                "<<Constructor>> TR abonentlari uchun SMS paketlar".lowercase(Locale.getDefault()) -> {
                                    listSMSCountry = ArrayList()
                                    rvSMSCountry =
                                        RvSMSCountry(object : RvSMSCountry.OnItemClickListener {
                                            override fun onItemClick(
                                                sms: SMSCountry,
                                                position: Int
                                            ) {
                                                var alertDialog = AlertDialog.Builder(
                                                    requireContext(),
                                                    R.style.BottomSheetDialogThem
                                                )
                                                val create = alertDialog.create()
                                                var dialogServiceBinding =
                                                    DialogServiceBinding.inflate(
                                                        LayoutInflater.from(requireContext()),
                                                        null,
                                                        false
                                                    )
                                                create.setView(dialogServiceBinding.root)
                                                dialogServiceBinding.active.text = onAttach.getText(R.string.aktivlashtirish)
                                                dialogServiceBinding.clouse.text = onAttach.getText(R.string.orqaga)
                                                dialogServiceBinding.active.setOnClickListener {
                                                    val substring =
                                                        sms.activeCode?.trim()
                                                            ?.substring(sms.activeCode?.trim()?.length!! - 1)
                                                    var Num: String
                                                    if (substring == "#") {
                                                        Num = sms.activeCode?.trim()?.substring(
                                                            0,
                                                            sms.activeCode?.trim()?.length?.minus(1)!!
                                                        ) + Uri.encode("#")
                                                    } else {
                                                        Num = sms.activeCode?.trim()?.substring(
                                                            0,
                                                            sms.activeCode?.trim()?.length?.minus(2)!!
                                                        ) + Uri.encode("#")
                                                    }
                                                    var intent = Intent(Intent.ACTION_CALL)
                                                    intent.data = Uri.parse("tel:$Num")
                                                    try {
                                                        startActivity(intent)
                                                    } catch (e: Exception) {
                                                        e.printStackTrace()
                                                    }

                                                }
                                                dialogServiceBinding.clouse.setOnClickListener {
                                                    create.dismiss()
                                                }
                                                dialogServiceBinding.share.setOnClickListener {
                                                    var shareIntent = Intent().apply {
                                                        this.action = Intent.ACTION_SEND
                                                        this.putExtra(
                                                            Intent.EXTRA_SUBJECT,
                                                            "Uzmobile-${sms.activeCode?.trim()}\n${sms.name?.trim()}"
                                                        )
                                                        this.putExtra(
                                                            Intent.EXTRA_TEXT,
                                                            "https://play.google.com/store/apps/details?id=uz.pdp.uzmobile"
                                                        )
                                                        this.type = "text/plain"
                                                    }
                                                    startActivity(shareIntent)
                                                }
                                                dialogServiceBinding.info.text =
                                                    "${sms.price?.trim()}\n${sms.count_sms?.trim()}\n${sms.day_sms}\n${onAttach.getText(R.string.active_ok)}: ${sms.activeCode?.trim()}\n".trim()
                                                dialogServiceBinding.name.text = sms.name?.trim()
                                                create.show()
                                            }
                                        })
                                    firebaseFirestore.collection("SMSConstructor")
                                        .addSnapshotListener { value, error ->
                                            value?.documentChanges?.forEach {
                                                when (it.type) {
                                                    DocumentChange.Type.ADDED -> {
                                                        val smsCountry =
                                                            it.document.toObject(SMSCountry::class.java)
                                                        listSMSCountry.add(smsCountry)
                                                    }
                                                }
                                            }
                                            rvSMSCountry.submitList(listSMSCountry)
                                            rvService.adapter = rvSMSCountry
                                        }
                                }
                            }
                        }
                            "ru".lowercase(Locale.getDefault())->{
                                when(param1?.lowercase(Locale.getDefault())){
                                    "Ежедневные SMS-пакеты".lowercase(Locale.getDefault())->{
                                        rvSMS = RvSMS(object:RvSMS.OnItemClickListener{
                                            override fun onItemClick(sms: SMS, position: Int) {
                                                var alertDialog = AlertDialog.Builder(
                                                    requireContext(),
                                                    R.style.BottomSheetDialogThem
                                                )
                                                val create = alertDialog.create()
                                                var dialogServiceBinding = DialogServiceBinding.inflate(
                                                    LayoutInflater.from(requireContext()), null, false
                                                )
                                                create.setView(dialogServiceBinding.root)
                                                dialogServiceBinding.active.text = onAttach.getText(R.string.aktivlashtirish)
                                                dialogServiceBinding.clouse.text = onAttach.getText(R.string.orqaga)
                                                dialogServiceBinding.active.setOnClickListener {
                                                    val substring =
                                                        sms.activeCode?.trim()?.substring(sms.activeCode?.trim()?.length!! - 1)
                                                    var Num: String
                                                    if (substring == "#") {
                                                        Num = sms.activeCode?.trim()?.substring(
                                                            0,
                                                            sms.activeCode?.trim()?.length?.minus(1)!!
                                                        ) + Uri.encode("#")
                                                    } else {
                                                        Num = sms.activeCode?.trim()?.substring(
                                                            0,
                                                            sms.activeCode?.trim()?.length?.minus(2)!!
                                                        ) + Uri.encode("#")
                                                    }
                                                    var intent = Intent(Intent.ACTION_CALL)
                                                    intent.data = Uri.parse("tel:$Num")
                                                    try {
                                                        startActivity(intent)
                                                    } catch (e: Exception) {
                                                        e.printStackTrace()
                                                    }

                                                }
                                                dialogServiceBinding.clouse.setOnClickListener {
                                                    create.dismiss()
                                                }
                                                dialogServiceBinding.share.setOnClickListener {
                                                    var shareIntent = Intent().apply {
                                                        this.action = Intent.ACTION_SEND
                                                        this.putExtra(
                                                            Intent.EXTRA_SUBJECT,
                                                            "Uzmobile-${sms.activeCode?.trim()}\n${sms.name?.trim()}"
                                                        )
                                                        this.putExtra(
                                                            Intent.EXTRA_TEXT,
                                                            "https://play.google.com/store/apps/details?id=uz.pdp.uzmobile"
                                                        )
                                                        this.type = "text/plain"
                                                    }
                                                    startActivity(shareIntent)
                                                }
                                                dialogServiceBinding.info.text =
                                                    "${sms.price?.trim()}\n${sms.summ_user?.trim()}\n${sms.count_sms?.trim()}\n${sms.day_sms}\n${onAttach.getText(R.string.active_ok)}: ${sms.activeCode?.trim()}\n${onAttach.getText(R.string.clouse)}: ${sms.offCode?.trim()}".trim()
                                                dialogServiceBinding.name.text = sms.name?.trim()
                                                create.show()
                                            }
                                        })
                                        firebaseFirestore.collection("SMSDayRu").addSnapshotListener { value, error ->
                                            value?.documentChanges?.forEach {
                                                when(it.type){
                                                    DocumentChange.Type.ADDED->{
                                                        val sms = it.document.toObject(SMS::class.java)
                                                        listSMS.add(sms)
                                                    }
                                                }
                                            }
                                            rvSMS.submitList(listSMS)
                                            rvService.adapter = rvSMS
                                        }
                                    }
                                    "Ежемесячные SMS-пакеты".lowercase(Locale.getDefault())->{
                                        rvSMS = RvSMS(object:RvSMS.OnItemClickListener{
                                            override fun onItemClick(sms: SMS, position: Int) {
                                                var alertDialog = AlertDialog.Builder(
                                                    requireContext(),
                                                    R.style.BottomSheetDialogThem
                                                )
                                                val create = alertDialog.create()
                                                var dialogServiceBinding = DialogServiceBinding.inflate(
                                                    LayoutInflater.from(requireContext()), null, false
                                                )
                                                create.setView(dialogServiceBinding.root)
                                                dialogServiceBinding.active.text = onAttach.getText(R.string.aktivlashtirish)
                                                dialogServiceBinding.clouse.text = onAttach.getText(R.string.orqaga)
                                                dialogServiceBinding.active.setOnClickListener {
                                                    val substring =
                                                        sms.activeCode?.trim()?.substring(sms.activeCode?.trim()?.length!! - 1)
                                                    var Num: String
                                                    if (substring == "#") {
                                                        Num = sms.activeCode?.trim()?.substring(
                                                            0,
                                                            sms.activeCode?.trim()?.length?.minus(1)!!
                                                        ) + Uri.encode("#")
                                                    } else {
                                                        Num = sms.activeCode?.trim()?.substring(
                                                            0,
                                                            sms.activeCode?.trim()?.length?.minus(2)!!
                                                        ) + Uri.encode("#")
                                                    }
                                                    var intent = Intent(Intent.ACTION_CALL)
                                                    intent.data = Uri.parse("tel:$Num")
                                                    try {
                                                        startActivity(intent)
                                                    } catch (e: Exception) {
                                                        e.printStackTrace()
                                                    }

                                                }
                                                dialogServiceBinding.clouse.setOnClickListener {
                                                    create.dismiss()
                                                }
                                                dialogServiceBinding.share.setOnClickListener {
                                                    var shareIntent = Intent().apply {
                                                        this.action = Intent.ACTION_SEND
                                                        this.putExtra(
                                                            Intent.EXTRA_SUBJECT,
                                                            "Uzmobile-${sms.activeCode?.trim()}\n${sms.name?.trim()}"
                                                        )
                                                        this.putExtra(
                                                            Intent.EXTRA_TEXT,
                                                            "https://play.google.com/store/apps/details?id=uz.pdp.uzmobile"
                                                        )
                                                        this.type = "text/plain"
                                                    }
                                                    startActivity(shareIntent)
                                                }
                                                dialogServiceBinding.info.text =
                                                    "${sms.price?.trim()}\n${sms.summ_user?.trim()}\n${sms.count_sms?.trim()}\n${sms.day_sms}\n${onAttach.getText(R.string.active_ok)}: ${sms.activeCode?.trim()}\n${onAttach.getText(R.string.clouse)}: ${sms.offCode?.trim()}".trim()
                                                dialogServiceBinding.name.text = sms.name?.trim()
                                                create.show()
                                            }
                                        })
                                        firebaseFirestore.collection("SMSMonthRu").addSnapshotListener { value, error ->
                                            value?.documentChanges?.forEach {
                                                when(it.type){
                                                    DocumentChange.Type.ADDED->{
                                                        val sms = it.document.toObject(SMS::class.java)
                                                        listSMS.add(sms)
                                                    }
                                                }
                                            }
                                            rvSMS.submitList(listSMS)
                                            rvService.adapter = rvSMS
                                        }
                                    }
                                    "Международные SMS-пакеты".lowercase(Locale.getDefault())->{
                                        listSMSCountry = ArrayList()
                                        rvSMSCountry = RvSMSCountry(object:RvSMSCountry.OnItemClickListener{
                                            override fun onItemClick(sms: SMSCountry, position: Int) {
                                                var alertDialog = AlertDialog.Builder(
                                                    requireContext(),
                                                    R.style.BottomSheetDialogThem
                                                )
                                                val create = alertDialog.create()
                                                var dialogServiceBinding = DialogCountryBinding.inflate(
                                                    LayoutInflater.from(requireContext()), null, false
                                                )
                                                create.setView(dialogServiceBinding.root)
                                                dialogServiceBinding.active.text = onAttach.getText(R.string.aktivlashtirish)
                                                dialogServiceBinding.clouse.text = onAttach.getText(R.string.orqaga)
                                                dialogServiceBinding.active.setOnClickListener {
                                                    val substring =
                                                        sms.activeCode?.trim()?.substring(sms.activeCode?.trim()?.length!! - 1)
                                                    var Num: String
                                                    if (substring == "#") {
                                                        Num = sms.activeCode?.trim()?.substring(
                                                            0,
                                                            sms.activeCode?.trim()?.length?.minus(1)!!
                                                        ) + Uri.encode("#")
                                                    } else {
                                                        Num = sms.activeCode?.trim()?.substring(
                                                            0,
                                                            sms.activeCode?.trim()?.length?.minus(2)!!
                                                        ) + Uri.encode("#")
                                                    }
                                                    var intent = Intent(Intent.ACTION_CALL)
                                                    intent.data = Uri.parse("tel:$Num")
                                                    try {
                                                        startActivity(intent)
                                                    } catch (e: Exception) {
                                                        e.printStackTrace()
                                                    }

                                                }
                                                dialogServiceBinding.clouse.setOnClickListener {
                                                    create.dismiss()
                                                }
                                                dialogServiceBinding.share.setOnClickListener {
                                                    var shareIntent = Intent().apply {
                                                        this.action = Intent.ACTION_SEND
                                                        this.putExtra(
                                                            Intent.EXTRA_SUBJECT,
                                                            "Uzmobile-${sms.activeCode?.trim()}\n${sms.name?.trim()}"
                                                        )
                                                        this.putExtra(
                                                            Intent.EXTRA_TEXT,
                                                            "https://play.google.com/store/apps/details?id=uz.pdp.uzmobile"
                                                        )
                                                        this.type = "text/plain"
                                                    }
                                                    startActivity(shareIntent)
                                                }
                                                dialogServiceBinding.info.text =
                                                    "${sms.price?.trim()}\n${sms.count_sms?.trim()}\n${sms.day_sms}\n${onAttach.getText(R.string.active_ok)}: ${sms.activeCode?.trim()}".trim()
                                                dialogServiceBinding.info1.text = sms.info
                                                dialogServiceBinding.name.text = sms.name?.trim()
                                                create.show()
                                            }
                                        })
                                        firebaseFirestore.collection("SMSCountryRu").addSnapshotListener { value, error ->
                                            value?.documentChanges?.forEach {
                                                when(it.type){
                                                    DocumentChange.Type.ADDED->{
                                                        val smsCountry = it.document.toObject(SMSCountry::class.java)
                                                        listSMSCountry.add(smsCountry)
                                                    }
                                                }
                                            }
                                            rvSMSCountry.submitList(listSMSCountry)
                                            rvService.adapter = rvSMSCountry
                                        }
                                    }
                                    "<<Конструктор>> SMS-пакеты для абонентов TR".lowercase(Locale.getDefault())->{
                                        listSMSCountry = ArrayList()
                                        rvSMSCountry = RvSMSCountry(object:RvSMSCountry.OnItemClickListener{
                                            override fun onItemClick(sms: SMSCountry, position: Int) {
                                                var alertDialog = AlertDialog.Builder(
                                                    requireContext(),
                                                    R.style.BottomSheetDialogThem
                                                )
                                                val create = alertDialog.create()
                                                var dialogServiceBinding = DialogServiceBinding.inflate(
                                                    LayoutInflater.from(requireContext()), null, false
                                                )
                                                create.setView(dialogServiceBinding.root)
                                                dialogServiceBinding.active.text = onAttach.getText(R.string.aktivlashtirish)
                                                dialogServiceBinding.clouse.text = onAttach.getText(R.string.orqaga)
                                                dialogServiceBinding.active.setOnClickListener {
                                                    val substring =
                                                        sms.activeCode?.trim()?.substring(sms.activeCode?.trim()?.length!! - 1)
                                                    var Num: String
                                                    if (substring == "#") {
                                                        Num = sms.activeCode?.trim()?.substring(
                                                            0,
                                                            sms.activeCode?.trim()?.length?.minus(1)!!
                                                        ) + Uri.encode("#")
                                                    } else {
                                                        Num = sms.activeCode?.trim()?.substring(
                                                            0,
                                                            sms.activeCode?.trim()?.length?.minus(2)!!
                                                        ) + Uri.encode("#")
                                                    }
                                                    var intent = Intent(Intent.ACTION_CALL)
                                                    intent.data = Uri.parse("tel:$Num")
                                                    try {
                                                        startActivity(intent)
                                                    } catch (e: Exception) {
                                                        e.printStackTrace()
                                                    }

                                                }
                                                dialogServiceBinding.clouse.setOnClickListener {
                                                    create.dismiss()
                                                }
                                                dialogServiceBinding.share.setOnClickListener {
                                                    var shareIntent = Intent().apply {
                                                        this.action = Intent.ACTION_SEND
                                                        this.putExtra(
                                                            Intent.EXTRA_SUBJECT,
                                                            "Uzmobile-${sms.activeCode?.trim()}\n${sms.name?.trim()}"
                                                        )
                                                        this.putExtra(
                                                            Intent.EXTRA_TEXT,
                                                            "https://play.google.com/store/apps/details?id=uz.pdp.uzmobile"
                                                        )
                                                        this.type = "text/plain"
                                                    }
                                                    startActivity(shareIntent)
                                                }
                                                dialogServiceBinding.info.text =
                                                    "${sms.price?.trim()}\n${sms.count_sms?.trim()}\n${sms.day_sms}\n${onAttach.getText(R.string.active_ok)}: ${sms.activeCode?.trim()}\n".trim()
                                                dialogServiceBinding.name.text = sms.name?.trim()
                                                create.show()
                                            }
                                        })
                                        firebaseFirestore.collection("SMSConstructorRu").addSnapshotListener { value, error ->
                                            value?.documentChanges?.forEach {
                                                when(it.type){
                                                    DocumentChange.Type.ADDED->{
                                                        val smsCountry = it.document.toObject(SMSCountry::class.java)
                                                        listSMSCountry.add(smsCountry)
                                                    }
                                                }
                                            }
                                            rvSMSCountry.submitList(listSMSCountry)
                                            rvService.adapter = rvSMSCountry
                                        }
                                    }
                                }
                            }
                            "kril".lowercase(Locale.getDefault())->{
                                when(param1?.lowercase(Locale.getDefault())){
                                    "Кунлик СМС пакетлар".lowercase(Locale.getDefault())->{
                                        rvSMS = RvSMS(object:RvSMS.OnItemClickListener{
                                            override fun onItemClick(sms: SMS, position: Int) {
                                                var alertDialog = AlertDialog.Builder(
                                                    requireContext(),
                                                    R.style.BottomSheetDialogThem
                                                )
                                                val create = alertDialog.create()
                                                var dialogServiceBinding = DialogServiceBinding.inflate(
                                                    LayoutInflater.from(requireContext()), null, false
                                                )
                                                create.setView(dialogServiceBinding.root)
                                                dialogServiceBinding.active.text = onAttach.getText(R.string.aktivlashtirish)
                                                dialogServiceBinding.clouse.text = onAttach.getText(R.string.orqaga)
                                                dialogServiceBinding.active.setOnClickListener {
                                                    val substring =
                                                        sms.activeCode?.trim()?.substring(sms.activeCode?.trim()?.length!! - 1)
                                                    var Num: String
                                                    if (substring == "#") {
                                                        Num = sms.activeCode?.trim()?.substring(
                                                            0,
                                                            sms.activeCode?.trim()?.length?.minus(1)!!
                                                        ) + Uri.encode("#")
                                                    } else {
                                                        Num = sms.activeCode?.trim()?.substring(
                                                            0,
                                                            sms.activeCode?.trim()?.length?.minus(2)!!
                                                        ) + Uri.encode("#")
                                                    }
                                                    var intent = Intent(Intent.ACTION_CALL)
                                                    intent.data = Uri.parse("tel:$Num")
                                                    try {
                                                        startActivity(intent)
                                                    } catch (e: Exception) {
                                                        e.printStackTrace()
                                                    }

                                                }
                                                dialogServiceBinding.clouse.setOnClickListener {
                                                    create.dismiss()
                                                }
                                                dialogServiceBinding.share.setOnClickListener {
                                                    var shareIntent = Intent().apply {
                                                        this.action = Intent.ACTION_SEND
                                                        this.putExtra(
                                                            Intent.EXTRA_SUBJECT,
                                                            "Uzmobile-${sms.activeCode?.trim()}\n${sms.name?.trim()}"
                                                        )
                                                        this.putExtra(
                                                            Intent.EXTRA_TEXT,
                                                            "https://play.google.com/store/apps/details?id=uz.pdp.uzmobile"
                                                        )
                                                        this.type = "text/plain"
                                                    }
                                                    startActivity(shareIntent)
                                                }
                                                dialogServiceBinding.info.text =
                                                    "${sms.price?.trim()}\n${sms.summ_user?.trim()}\n${sms.count_sms?.trim()}\n${sms.day_sms}\n${onAttach.getText(R.string.active_ok)}: ${sms.activeCode?.trim()}\n${onAttach.getText(R.string.clouse)}: ${sms.offCode?.trim()}".trim()
                                                dialogServiceBinding.name.text = sms.name?.trim()
                                                create.show()
                                            }
                                        })
                                                firebaseFirestore.collection("SMSDayCril").addSnapshotListener { value, error ->
                                                    value?.documentChanges?.forEach {
                                                        when(it.type){
                                                            DocumentChange.Type.ADDED->{
                                                                val sms = it.document.toObject(SMS::class.java)
                                                                listSMS.add(sms)
                                                            }
                                                        }
                                                    }
                                                    rvSMS.submitList(listSMS)
                                                    rvService.adapter = rvSMS
                                                }
                                    }
                                    "Ойлик СМС пакетлар".lowercase(Locale.getDefault())->{
                                        rvSMS = RvSMS(object:RvSMS.OnItemClickListener{
                                            override fun onItemClick(sms: SMS, position: Int) {
                                                var alertDialog = AlertDialog.Builder(
                                                    requireContext(),
                                                    R.style.BottomSheetDialogThem
                                                )
                                                val create = alertDialog.create()
                                                var dialogServiceBinding = DialogServiceBinding.inflate(
                                                    LayoutInflater.from(requireContext()), null, false
                                                )
                                                create.setView(dialogServiceBinding.root)
                                                dialogServiceBinding.active.text = onAttach.getText(R.string.aktivlashtirish)
                                                dialogServiceBinding.clouse.text = onAttach.getText(R.string.orqaga)
                                                dialogServiceBinding.active.setOnClickListener {
                                                    val substring =
                                                        sms.activeCode?.trim()?.substring(sms.activeCode?.trim()?.length!! - 1)
                                                    var Num: String
                                                    if (substring == "#") {
                                                        Num = sms.activeCode?.trim()?.substring(
                                                            0,
                                                            sms.activeCode?.trim()?.length?.minus(1)!!
                                                        ) + Uri.encode("#")
                                                    } else {
                                                        Num = sms.activeCode?.trim()?.substring(
                                                            0,
                                                            sms.activeCode?.trim()?.length?.minus(2)!!
                                                        ) + Uri.encode("#")
                                                    }
                                                    var intent = Intent(Intent.ACTION_CALL)
                                                    intent.data = Uri.parse("tel:$Num")
                                                    try {
                                                        startActivity(intent)
                                                    } catch (e: Exception) {
                                                        e.printStackTrace()
                                                    }

                                                }
                                                dialogServiceBinding.clouse.setOnClickListener {
                                                    create.dismiss()
                                                }
                                                dialogServiceBinding.share.setOnClickListener {
                                                    var shareIntent = Intent().apply {
                                                        this.action = Intent.ACTION_SEND
                                                        this.putExtra(
                                                            Intent.EXTRA_SUBJECT,
                                                            "Uzmobile-${sms.activeCode?.trim()}\n${sms.name?.trim()}"
                                                        )
                                                        this.putExtra(
                                                            Intent.EXTRA_TEXT,
                                                            "https://play.google.com/store/apps/details?id=uz.pdp.uzmobile"
                                                        )
                                                        this.type = "text/plain"
                                                    }
                                                    startActivity(shareIntent)
                                                }
                                                dialogServiceBinding.info.text =
                                                    "${sms.price?.trim()}\n${sms.summ_user?.trim()}\n${sms.count_sms?.trim()}\n${sms.day_sms}\n${onAttach.getText(R.string.active_ok)}: ${sms.activeCode?.trim()}\n${onAttach.getText(R.string.clouse)}: ${sms.offCode?.trim()}".trim()
                                                dialogServiceBinding.name.text = sms.name?.trim()
                                                create.show()
                                            }
                                        })
                                                firebaseFirestore.collection("SMSMonthCril").addSnapshotListener { value, error ->
                                                    value?.documentChanges?.forEach {
                                                        when(it.type){
                                                            DocumentChange.Type.ADDED->{
                                                                val sms = it.document.toObject(SMS::class.java)
                                                                listSMS.add(sms)
                                                            }
                                                        }
                                                    }
                                                    rvSMS.submitList(listSMS)
                                                    rvService.adapter = rvSMS
                                                }
                                    }
                                    "Халқаро СМС пакетлар".lowercase(Locale.getDefault())->{
                                        listSMSCountry = ArrayList()
                                        rvSMSCountry = RvSMSCountry(object:RvSMSCountry.OnItemClickListener{
                                            override fun onItemClick(sms: SMSCountry, position: Int) {
                                                var alertDialog = AlertDialog.Builder(
                                                    requireContext(),
                                                    R.style.BottomSheetDialogThem
                                                )
                                                val create = alertDialog.create()
                                                var dialogServiceBinding = DialogCountryBinding.inflate(
                                                    LayoutInflater.from(requireContext()), null, false
                                                )
                                                create.setView(dialogServiceBinding.root)
                                                dialogServiceBinding.active.text = onAttach.getText(R.string.aktivlashtirish)
                                                dialogServiceBinding.clouse.text = onAttach.getText(R.string.orqaga)
                                                dialogServiceBinding.active.setOnClickListener {
                                                    val substring =
                                                        sms.activeCode?.trim()?.substring(sms.activeCode?.trim()?.length!! - 1)
                                                    var Num: String
                                                    if (substring == "#") {
                                                        Num = sms.activeCode?.trim()?.substring(
                                                            0,
                                                            sms.activeCode?.trim()?.length?.minus(1)!!
                                                        ) + Uri.encode("#")
                                                    } else {
                                                        Num = sms.activeCode?.trim()?.substring(
                                                            0,
                                                            sms.activeCode?.trim()?.length?.minus(2)!!
                                                        ) + Uri.encode("#")
                                                    }
                                                    var intent = Intent(Intent.ACTION_CALL)
                                                    intent.data = Uri.parse("tel:$Num")
                                                    try {
                                                        startActivity(intent)
                                                    } catch (e: Exception) {
                                                        e.printStackTrace()
                                                    }

                                                }
                                                dialogServiceBinding.clouse.setOnClickListener {
                                                    create.dismiss()
                                                }
                                                dialogServiceBinding.share.setOnClickListener {
                                                    var shareIntent = Intent().apply {
                                                        this.action = Intent.ACTION_SEND
                                                        this.putExtra(
                                                            Intent.EXTRA_SUBJECT,
                                                            "Uzmobile-${sms.activeCode?.trim()}\n${sms.name?.trim()}"
                                                        )
                                                        this.putExtra(
                                                            Intent.EXTRA_TEXT,
                                                            "https://play.google.com/store/apps/details?id=uz.pdp.uzmobile"
                                                        )
                                                        this.type = "text/plain"
                                                    }
                                                    startActivity(shareIntent)
                                                }
                                                dialogServiceBinding.info.text =
                                                    "${sms.price?.trim()}\n${sms.count_sms?.trim()}\n${sms.day_sms}\n${onAttach.getText(R.string.active_ok)}: ${sms.activeCode?.trim()}".trim()
                                                dialogServiceBinding.info1.text = sms.info
                                                dialogServiceBinding.name.text = sms.name?.trim()
                                                create.show()
                                            }
                                        })
                                                firebaseFirestore.collection("SMSCountryCril").addSnapshotListener { value, error ->
                                                    value?.documentChanges?.forEach {
                                                        when(it.type){
                                                            DocumentChange.Type.ADDED->{
                                                                val smsCountry = it.document.toObject(SMSCountry::class.java)
                                                                listSMSCountry.add(smsCountry)
                                                            }
                                                        }
                                                    }
                                                    rvSMSCountry.submitList(listSMSCountry)
                                                    rvService.adapter = rvSMSCountry
                                                }
                                    }
                                    "<<Cонструcтор>> ТР абонентлари учун СМС пакетлар".lowercase(Locale.getDefault())->{
                                        listSMSCountry = ArrayList()
                                        rvSMSCountry = RvSMSCountry(object:RvSMSCountry.OnItemClickListener{
                                            override fun onItemClick(sms: SMSCountry, position: Int) {
                                                var alertDialog = AlertDialog.Builder(
                                                    requireContext(),
                                                    R.style.BottomSheetDialogThem
                                                )
                                                val create = alertDialog.create()
                                                var dialogServiceBinding = DialogServiceBinding.inflate(
                                                    LayoutInflater.from(requireContext()), null, false
                                                )
                                                create.setView(dialogServiceBinding.root)
                                                dialogServiceBinding.active.text = onAttach.getText(R.string.aktivlashtirish)
                                                dialogServiceBinding.clouse.text = onAttach.getText(R.string.orqaga)
                                                dialogServiceBinding.active.setOnClickListener {
                                                    val substring =
                                                        sms.activeCode?.trim()?.substring(sms.activeCode?.trim()?.length!! - 1)
                                                    var Num: String
                                                    if (substring == "#") {
                                                        Num = sms.activeCode?.trim()?.substring(
                                                            0,
                                                            sms.activeCode?.trim()?.length?.minus(1)!!
                                                        ) + Uri.encode("#")
                                                    } else {
                                                        Num = sms.activeCode?.trim()?.substring(
                                                            0,
                                                            sms.activeCode?.trim()?.length?.minus(2)!!
                                                        ) + Uri.encode("#")
                                                    }
                                                    var intent = Intent(Intent.ACTION_CALL)
                                                    intent.data = Uri.parse("tel:$Num")
                                                    try {
                                                        startActivity(intent)
                                                    } catch (e: Exception) {
                                                        e.printStackTrace()
                                                    }

                                                }
                                                dialogServiceBinding.clouse.setOnClickListener {
                                                    create.dismiss()
                                                }
                                                dialogServiceBinding.share.setOnClickListener {
                                                    var shareIntent = Intent().apply {
                                                        this.action = Intent.ACTION_SEND
                                                        this.putExtra(
                                                            Intent.EXTRA_SUBJECT,
                                                            "Uzmobile-${sms.activeCode?.trim()}\n${sms.name?.trim()}"
                                                        )
                                                        this.putExtra(
                                                            Intent.EXTRA_TEXT,
                                                            "https://play.google.com/store/apps/details?id=uz.pdp.uzmobile"
                                                        )
                                                        this.type = "text/plain"
                                                    }
                                                    startActivity(shareIntent)
                                                }
                                                dialogServiceBinding.info.text =
                                                    "${sms.price?.trim()}\n${sms.count_sms?.trim()}\n${sms.day_sms}\n${onAttach.getText(R.string.active_ok)}: ${sms.activeCode?.trim()}\n".trim()
                                                dialogServiceBinding.name.text = sms.name?.trim()
                                                create.show()
                                            }
                                        })
                                                firebaseFirestore.collection("SMSConstructorCril").addSnapshotListener { value, error ->
                                                    value?.documentChanges?.forEach {
                                                        when(it.type){
                                                            DocumentChange.Type.ADDED->{
                                                                val smsCountry = it.document.toObject(SMSCountry::class.java)
                                                                listSMSCountry.add(smsCountry)
                                                            }
                                                        }
                                                    }
                                                    rvSMSCountry.submitList(listSMSCountry)
                                                    rvService.adapter = rvSMSCountry
                                                }
                                    }
                                }
                            }
                    }
                }
            }
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ViewPagerServiseFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String,param2: String) =
            ViewPagerServiseFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}