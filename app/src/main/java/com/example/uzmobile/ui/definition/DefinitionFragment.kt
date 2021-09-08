package com.example.uzmobile.ui.definition

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainer
import com.example.uzmobile.R
import com.example.uzmobile.databinding.DialogAllBinding
import com.example.uzmobile.databinding.FragmentDefinitionBinding
import com.example.uzmobile.language.LocaleHelper
import com.example.uzmobile.models.CardAnim
import com.example.uzmobile.models.Definitions
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import java.lang.Exception

class DefinitionFragment : Fragment(R.layout.fragment_definition) {
    private val binding by viewBinding(FragmentDefinitionBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            val onAttach = LocaleHelper.onAttach(requireContext())
            val position = arguments?.getInt("position",0)
            if (position==1){
                val cardAnim = arguments?.getSerializable("cardAnim") as CardAnim
                (activity as AppCompatActivity).supportActionBar?.title = cardAnim.nameMenu
                textMin.text = "${cardAnim.countTime} ${onAttach.getText(R.string.min)}"
                textSms.text ="${cardAnim.smsCount} ${onAttach.getText(R.string.sms_my)}"
                textMb.text = "${cardAnim.MB} MB"
                tv1.text = "${cardAnim.nameMenu}"
                summ.text = "${cardAnim.summ} ${onAttach.getText(R.string.month_summ)}"
                info.text = "${onAttach.getText(R.string.user_price)} ${cardAnim.summ}\n${onAttach.getText(R.string.uzb)} ${cardAnim.countTime} ${onAttach.getText(R.string.min1)}\n${onAttach.getText(R.string.my_internet)} ${cardAnim.MB}\n${onAttach.getText(R.string.uzb)} ${cardAnim.smsCount}\n\n\n${onAttach.getText(R.string.update_tarif)} ${cardAnim.code}"
                connect.visibility = View.INVISIBLE
                all.visibility = View.INVISIBLE
                connect1.visibility = View.VISIBLE
                connect1.setOnClickListener {
                    val substring = cardAnim.code.substring(cardAnim.code.length - 1)
                    var Num:String
                    if (substring=="#"){
                        Num = cardAnim.code.substring(0, cardAnim.code.length.minus(1)) + Uri.encode("#")
                    }else{
                        Num = cardAnim.code.substring(0, cardAnim.code.length.minus(2)) + Uri.encode("#")
                    }
                    var intent = Intent(Intent.ACTION_CALL)
                    intent.data = Uri.parse("tel:$Num")
                    try {
                        startActivity(intent)
                    }catch (e: Exception){
                        e.printStackTrace()
                    }
                }
            }else{
                val definitions = arguments?.getSerializable("definitions") as Definitions
                (activity as AppCompatActivity).supportActionBar?.title = definitions.name
                textMb.text = definitions.mont_mb
                textMin.text = definitions.month_min
                textSms.text = definitions.month_sms
                tv1.text = definitions.name
                summ.text=definitions.summ_mont
                summUser.text = "${onAttach.getText(R.string.abonent_to_lovi)}"
                name.text = "${onAttach.getText(R.string.tarif_uz)}:\"${definitions.name}\""
                all.text = onAttach.getText(R.string.batafsil)
                connect.text = onAttach.getText(R.string.update_tarif)
                summMoney.text =definitions.summ_mont
                info.text = "${onAttach.getText(R.string.user_price)} ${definitions.summ_mont}\n${onAttach.getText(R.string.uzb)} ${definitions.month_min} ${onAttach.getText(R.string.min1)}\n${onAttach.getText(R.string.my_internet)} ${definitions.mont_mb}\n${onAttach.getText(R.string.uzb)} ${definitions.month_sms}\n\n\n${onAttach.getText(R.string.update_tarif)} ${definitions.code}"

                connect.setOnClickListener {
                    val substring = definitions.code?.substring(definitions.code?.length!! - 1)
                    var Num:String
                    if (substring=="#"){
                        Num = definitions.code?.substring(0, definitions.code?.length?.minus(1)!!)+ Uri.encode("#")
                    }else{
                        Num = definitions.code?.substring(0, definitions.code?.length?.minus(2)!!)+ Uri.encode("#")
                    }
                    var intent = Intent(Intent.ACTION_CALL)
                    intent.data = Uri.parse("tel:$Num")
                    try {
                        startActivity(intent)
                    }catch (e: Exception){
                        e.printStackTrace()
                    }

                }
                all.setOnClickListener {
                    var alertDialog = AlertDialog.Builder(requireContext(),R.style.BottomSheetDialogThem)
                    val create = alertDialog.create()
                    var dialogAllBinding = DialogAllBinding.inflate(LayoutInflater.from(requireContext()),null,false)
                    create.setView(dialogAllBinding.root)
                    dialogAllBinding.dialogName.text = onAttach.getText(R.string.batafsil)
                    dialogAllBinding.okBtn.setOnClickListener {
                        create.dismiss()
                    }

                    dialogAllBinding.share.setOnClickListener {
                        var shareIntent = Intent().apply {
                            this.action = Intent.ACTION_SEND
                            this.putExtra(Intent.EXTRA_SUBJECT,"Uzmobile-${definitions.code}\n${definitions.name}")
                            this.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id=uz.pdp.uzmobile")
                            this.type="text/plain"
                        }
                        startActivity(shareIntent)
                    }
                    dialogAllBinding.info.text = definitions.info
                    create.show()
                }
            }
        }
    }
}