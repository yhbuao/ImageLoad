package com.yhbuao.imageload

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yhbuao.image.ImageLoader
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ImageLoader.with(this)
            .load("https://www.baidu.com/img/bd_logo.png")
            .circle()
            .into(iv)
    }
}
