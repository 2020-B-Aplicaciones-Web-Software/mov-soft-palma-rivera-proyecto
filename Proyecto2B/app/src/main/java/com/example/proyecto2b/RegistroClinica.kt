package com.example.proyecto2b

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView

class RegistroClinica : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_clinica)
        val btn=findViewById<Button>(R.id.button3)
        btn.setOnClickListener {
            val intent=Intent(Intent.ACTION_PICK)
            intent.type="image/*"
            startActivityForResult(intent,0)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==0 && resultCode==Activity.RESULT_OK && data!=null){
            val uri=data.data
            val bitmap=MediaStore.Images.Media.getBitmap(contentResolver,uri)
            val drawable=BitmapDrawable(bitmap)
            findViewById<ImageView>(R.id.imageView).setImageDrawable(drawable)
        }
    }
}