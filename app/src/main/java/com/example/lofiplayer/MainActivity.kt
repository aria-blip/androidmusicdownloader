package com.example.lofiplayer

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.nfc.Tag
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.OpenableColumns
import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.documentfile.provider.DocumentFile
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lofiplayer.databinding.MainActivityBinding
import com.example.lofiplayer.ui.theme.LofiPlayerTheme
import java.net.URI

class MainActivity : ComponentActivity() {
    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = MainActivityBinding.inflate(layoutInflater)
        val view =binding.root
        setContentView(view)


        var menus=menus("piano", arrayOf(Song("maries "),Song("heps method #2")))

        var resultLauncher = registerForActivityResult(ActivityResultContracts.OpenDocumentTree()) { result: Uri? ->
                // There are no request codes
                val data = result
            Log.e("warnnnnnnnnnnnnnnnnnnnnnnnnnnnn", "ggg")

            val docRoot: DocumentFile? = data?.let { DocumentFile.fromTreeUri(this, it) }
            var urist: Array<DocumentFile>? = docRoot?.listFiles()
            if (urist != null) {
                for(u in urist){
                    menus.songs=menus.songs.plus(Song(u.uri.toString()))
                    Log.e("warnnnnnnnnnnnnnnnnnnnnnnnnnnnn", u.uri.toString())
                    Log.e("warnnnnnnnnnnnnnnnnnnnnnnnnnnnn", u.uri.toString())

                }
            }
            binding.list.adapter=SongAdapter(menus.songs)

            binding.list.adapter?.notifyDataSetChanged();


        }

        binding.list.layoutManager=  LinearLayoutManager(this)
        binding.list.adapter=SongAdapter(menus.songs)

        binding.button2.setOnClickListener(){
            binding.button2.setText("loading")
            resultLauncher.launch(null)
        }


    }
}


