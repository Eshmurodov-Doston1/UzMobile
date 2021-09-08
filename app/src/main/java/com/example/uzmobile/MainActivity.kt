package com.example.uzmobile

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.example.uzmobile.databinding.ActivityMainBinding
import com.example.uzmobile.databinding.DialogEmailBinding
import com.example.uzmobile.databinding.InfoDialogBinding
import com.example.uzmobile.language.LocaleHelper
import com.example.uzmobile.ui.settings.SettingsActivity
import com.github.florent37.runtimepermission.kotlin.askPermission
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        val onAttach = LocaleHelper.onAttach(this)

        binding.appBarMain.include.balans.text = onAttach.getText(R.string.balans)
        binding.appBarMain.include.operator.text = onAttach.getText(R.string.operator)
        binding.appBarMain.include.news.text = onAttach.getText(R.string.yangiliklar)
        binding.appBarMain.include.settings.text = onAttach.getText(R.string.sozlamalar)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_home), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.itemIconTintList = null
        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_home->{
                    binding.navView.menu.findItem(R.id.nav_home).isChecked=true
                    binding.navView.menu.findItem(R.id.nav_home).setIcon(R.drawable.telegram1)
                    binding.navView.menu.findItem(R.id.nav_gallery).setIcon(R.drawable.ic_contacts)
                    binding.navView.menu.findItem(R.id.share).setIcon(R.drawable.ic_share)
                    binding.navView.menu.findItem(R.id.favorities).setIcon(R.drawable.ic_star)
                    binding.navView.menu.findItem(R.id.info).setIcon(R.drawable.ic_info_button)
                    val uri =
                        Uri.parse("https://t.me/ussdchat")
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    try {
                        startActivity(intent)
                    } catch (e: Exception) {
                        binding.drawerLayout.close()
                        Snackbar.make(binding.root, "Qayta urining!", Snackbar.LENGTH_LONG).show()
                    }
                    binding.drawerLayout.close()
                }
                R.id.nav_gallery->{
                    binding.navView.menu.findItem(R.id.nav_gallery).isChecked=true
                    binding.navView.menu.findItem(R.id.nav_home).setIcon(R.drawable.telegram)
                    binding.navView.menu.findItem(R.id.nav_gallery).setIcon(R.drawable.ic_contacts1)
                    binding.navView.menu.findItem(R.id.share).setIcon(R.drawable.ic_share)
                    binding.navView.menu.findItem(R.id.favorities).setIcon(R.drawable.ic_star)
                    binding.navView.menu.findItem(R.id.info).setIcon(R.drawable.ic_info_button)

                    var alertDialog = AlertDialog.Builder(this,R.style.BottomSheetDialogThem)
                    val create = alertDialog.create()
                    var dialogEmailBinding = DialogEmailBinding.inflate(LayoutInflater.from(this),null,false)
                    dialogEmailBinding.dialogText.text = onAttach.getText(R.string.biz_bilan_aloqa)
                    dialogEmailBinding.clouse.text = onAttach.getText(R.string.orqaga)
                    dialogEmailBinding.send.text = onAttach.getText(R.string.xat_jo_natish)
                    create.setView(dialogEmailBinding.root)
                    dialogEmailBinding.clouse.setOnClickListener {
                        create.dismiss()
                    }
                    dialogEmailBinding.send.setOnClickListener {
                        var email = dialogEmailBinding.email.text.toString()
                        val intent = Intent(Intent.ACTION_SENDTO,Uri.fromParts("mailto",email,null))
                        try {
                            startActivity(Intent.createChooser(intent,"Send email..."))
                        }catch (e:Exception){
                            e.printStackTrace()
                        }
                        create.dismiss()
                    }
                    create.show()
                    binding.drawerLayout.close()
                }
                R.id.share->{
                    binding.navView.menu.findItem(R.id.nav_gallery).isChecked=true
                    binding.navView.menu.findItem(R.id.nav_home).setIcon(R.drawable.telegram)
                    binding.navView.menu.findItem(R.id.nav_gallery).setIcon(R.drawable.ic_contacts)
                    binding.navView.menu.findItem(R.id.share).setIcon(R.drawable.ic_share1)
                    binding.navView.menu.findItem(R.id.favorities).setIcon(R.drawable.ic_star)
                    binding.navView.menu.findItem(R.id.info).setIcon(R.drawable.ic_info_button)
                    var shareIntent = Intent().apply {
                        this.action = Intent.ACTION_SEND
                        this.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id=uz.pdp.uzmobile")
                        this.type="text/plain"
                    }
                    startActivity(shareIntent)
                    binding.drawerLayout.close()
                }
                R.id.favorities->{

                    binding.navView.menu.findItem(R.id.nav_gallery).isChecked=true
                    binding.navView.menu.findItem(R.id.nav_home).setIcon(R.drawable.telegram)
                    binding.navView.menu.findItem(R.id.nav_gallery).setIcon(R.drawable.ic_contacts)
                    binding.navView.menu.findItem(R.id.share).setIcon(R.drawable.ic_share)
                    binding.navView.menu.findItem(R.id.favorities).setIcon(R.drawable.ic_star1)
                    binding.navView.menu.findItem(R.id.info).setIcon(R.drawable.ic_info_button)

                    val uri =
                        Uri.parse("https://play.google.com/store/apps/details?id=uz.pdp.uzmobile")
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    try {
                        startActivity(intent)
                    } catch (e: Exception) {
                        binding.drawerLayout.close()
                        Snackbar.make(binding.root, "Qayta urining !", Snackbar.LENGTH_LONG).show()
                    }
                }
                R.id.info->{
                    binding.navView.menu.findItem(R.id.nav_gallery).isChecked=true
                    binding.navView.menu.findItem(R.id.nav_home).setIcon(R.drawable.telegram)
                    binding.navView.menu.findItem(R.id.nav_gallery).setIcon(R.drawable.ic_contacts)
                    binding.navView.menu.findItem(R.id.share).setIcon(R.drawable.ic_share)
                    binding.navView.menu.findItem(R.id.favorities).setIcon(R.drawable.ic_star)
                    binding.navView.menu.findItem(R.id.info).setIcon(R.drawable.ic_info_button1)

                    binding.drawerLayout.close()
                    var alertDialog = AlertDialog.Builder(this,R.style.BottomSheetDialogThem)
                    val create = alertDialog.create()
                    var infoDialogBinding = InfoDialogBinding.inflate(LayoutInflater.from(this),null,false)
                    create.setView(infoDialogBinding.root)
                    infoDialogBinding.dialogText.text = onAttach.getText(R.string.biz_haqimizda)
                    infoDialogBinding.email.text = onAttach.getText(R.string.hello)
                    infoDialogBinding.clouse.text = onAttach.getText(R.string.orqaga)
                    infoDialogBinding.clouse.setOnClickListener {
                        create.dismiss()
                    }
                    create.show()
                }
            }
            true
        }
        navView.menu.findItem(R.id.nav_home).title = onAttach.getText(R.string.menu_home)
        navView.menu.findItem(R.id.nav_gallery).title = onAttach.getText(R.string.menu_gallery)
        navView.menu.findItem(R.id.share).title = onAttach.getText(R.string.menu_slideshow)
        navView.menu.findItem(R.id.favorities).title = onAttach.getText(R.string.menu_favorites)
        navView.menu.findItem(R.id.info).title = onAttach.getText(R.string.menu_info)


        askPermission(Manifest.permission.CALL_PHONE){

        }.onDeclined { e ->
            if (e.hasDenied()) {
                android.app.AlertDialog.Builder(this)
                    .setMessage(onAttach.getString(R.string.permission))
                    .setPositiveButton(onAttach.getText(R.string.ok)) { dialog, which ->
                        e.askAgain();
                    } //ask again
                    .setNegativeButton(onAttach.getText(R.string.no)) { dialog, which ->
                        dialog.dismiss();
                    }
                    .show();
            }

            if(e.hasForeverDenied()) {
                e.goToSettings();
            }
        }
        binding.appBarMain.include.card1.setOnClickListener {
                askPermission(Manifest.permission.CALL_PHONE){
                    val Num = "*107" + Uri.encode("#")
                    var intent = Intent(Intent.ACTION_CALL)
                    intent.data = Uri.parse("tel:$Num")
                    try {
                        startActivity(intent)
                    }catch (e:Exception){
                        e.printStackTrace()
                    }
                }.onDeclined { e ->
                    if (e.hasDenied()) {
                        android.app.AlertDialog.Builder(this)
                            .setMessage(onAttach.getText(R.string.permission))
                            .setPositiveButton(onAttach.getText(R.string.ok)) { dialog, which ->
                                e.askAgain();
                            } //ask again
                            .setNegativeButton(onAttach.getText(R.string.no)) { dialog, which ->
                                dialog.dismiss();
                            }
                            .show();
                    }

                    if(e.hasForeverDenied()) {
                        e.goToSettings();
                    }
                }
        }
        binding.appBarMain.include.card2.setOnClickListener {
            navController.navigate(R.id.operatorFragment)
        }
        binding.appBarMain.include.cardMenu.setOnClickListener {
            navController.popBackStack()
            navController.navigate(R.id.nav_home)
        }
        binding.appBarMain.include.card3.setOnClickListener {
            navController.navigate(R.id.newsFragment)
        }
        binding.appBarMain.include.card4.setOnClickListener {
           var intent = Intent(this,SettingsActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        val name = binding.navView.findViewById<TextView>(R.id.name_My)
        name.text = LocaleHelper.onAttach(this).getText(R.string.milliy_operator)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }




}