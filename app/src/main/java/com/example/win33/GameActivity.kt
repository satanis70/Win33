package com.example.win33

import android.content.DialogInterface
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.win33.model.HangmanModel
import com.example.win33.model.Hangmanimg
import com.example.win33.model.Word
import com.example.win33.services.HangmanApi
import com.onesignal.OneSignal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.w3c.dom.Text
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class GameActivity : AppCompatActivity() {

    private lateinit var a: Button
    private lateinit var b: Button
    private lateinit var c: Button
    private lateinit var d: Button
    private lateinit var e: Button
    private lateinit var f: Button
    private lateinit var g: Button
    private lateinit var h: Button
    private lateinit var i: Button
    private lateinit var j: Button
    private lateinit var k: Button
    private lateinit var l: Button
    private lateinit var m: Button
    private lateinit var n: Button
    private lateinit var o: Button
    private lateinit var p: Button
    private lateinit var q: Button
    private lateinit var r: Button
    private lateinit var s: Button
    private lateinit var t: Button
    private lateinit var u: Button
    private lateinit var v: Button
    private lateinit var w: Button
    private lateinit var x: Button
    private lateinit var y: Button
    private lateinit var z: Button
    private var arrayListHangmanImages = ArrayList<Hangmanimg>()
    private var arrayListHangmanWords = ArrayList<Word>()
    private lateinit var imageViewHangman: ImageView
    private lateinit var tvHint: TextView
    private lateinit var tvWord: TextView
    private lateinit var currentWord : ArrayList<Char>
    var currentWordArrayList: ArrayList<String> = ArrayList()
    private var currentAttempt = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
        OneSignal.initWithContext(this)
        OneSignal.setAppId("714b9f14-381d-4fc4-a93c-28d480557381")
        val layout = findViewById<ConstraintLayout>(R.id.activity_game_layout)
        Glide.with(this).load("http://49.12.202.175/win33/background.jpg")
            .into(object : SimpleTarget<Drawable?>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable?>?
                ) {
                    layout.background = resource
                }
            })
        tvHint = findViewById(R.id.textView_hint)
        tvWord = findViewById(R.id.textView_word)
        imageViewHangman = findViewById(R.id.imageView_hangman)
        initButton()
        getImages()
    }

    private fun getImages(){
        CoroutineScope(Dispatchers.IO).launch {
            val api = HangmanApi::class.java
            val apiInterface = Retrofit.Builder()
                .baseUrl("http://49.12.202.175/win33/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(api)
            val call = apiInterface.getImages().awaitResponse()
            if (call.isSuccessful) {
                arrayListHangmanImages.addAll(call.body()!!.hangmanimg)
            }
            launch(Dispatchers.Main) {
                Glide.with(this@GameActivity)
                    .load(arrayListHangmanImages[0].url)
                    .into(imageViewHangman)
                getWords()
            }
        }
    }

    private fun loadCurrentImage(){
        Glide.with(this@GameActivity)
            .load(arrayListHangmanImages[currentAttempt].url)
            .into(imageViewHangman)
    }

    private fun getWords(){
        CoroutineScope(Dispatchers.IO).launch {
            val api = HangmanApi::class.java
            val apiInterface = Retrofit.Builder()
                .baseUrl("http://49.12.202.175/win33/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(api)
            val call = apiInterface.getWords().awaitResponse()
            if (call.isSuccessful) {
                arrayListHangmanWords.addAll(call.body()!!.words)
            }
            launch(Dispatchers.Main) {
                arrayListHangmanWords.addAll(arrayListHangmanWords)
                arrayListHangmanWords.shuffle()
                tvHint.text = arrayListHangmanWords[0].hint
                currentWord = arrayListHangmanWords[0].word.toList() as ArrayList<Char>
                for (i in currentWord.indices){
                    currentWordArrayList.add(currentWord[i].toString().lowercase())
                    currentWord[i]='*'
                }
                tvWord.text = currentWord.joinToString()
                onClickButton()
            }
        }
    }

    private fun initButton(){
        a = findViewById(R.id.a)
        b = findViewById(R.id.b)
        c = findViewById(R.id.c)
        d = findViewById(R.id.d)
        e = findViewById(R.id.e)
        f = findViewById(R.id.f)
        g = findViewById(R.id.g)
        h = findViewById(R.id.h)
        i = findViewById(R.id.i)
        j = findViewById(R.id.j)
        k = findViewById(R.id.k)
        l = findViewById(R.id.l)
        m = findViewById(R.id.m)
        n = findViewById(R.id.n)
        o = findViewById(R.id.o)
        p = findViewById(R.id.p)
        q = findViewById(R.id.q)
        r = findViewById(R.id.r)
        s = findViewById(R.id.s)
        t = findViewById(R.id.t)
        u = findViewById(R.id.u)
        v = findViewById(R.id.v)
        w = findViewById(R.id.w)
        x = findViewById(R.id.x)
        y = findViewById(R.id.y)
        z = findViewById(R.id.z)
    }

    private fun onClickButton(){
        a.setOnClickListener {
            result('a', a)
        }
        b.setOnClickListener {
            result('b', b)
        }
        c.setOnClickListener {
            result('c', c)
        }
        d.setOnClickListener {
            result('d', d)
        }
        e.setOnClickListener {
            result('e', e)
        }
        f.setOnClickListener {
            result('f', f)
        }
        g.setOnClickListener {
            result('g', g)
        }
        h.setOnClickListener {
            result('h', h)
        }
        i.setOnClickListener {
            result('i', i)
        }
        j.setOnClickListener {
            result('j', j)
        }
        k.setOnClickListener {
            result('k', k)
        }
        l.setOnClickListener {
            result('l', l)
        }
        m.setOnClickListener {
            result('m', m)
        }
        n.setOnClickListener {
            result('n', n)
        }
        o.setOnClickListener {
            result('o', o)
        }
        p.setOnClickListener {
            result('p', p)
        }
        q.setOnClickListener {
            result('q', q)
        }
        r.setOnClickListener {
            result('r', r)
        }
        s.setOnClickListener {
            result('s', s)
        }
        t.setOnClickListener {
            result('t', t)
        }
        u.setOnClickListener {
            result('u', u)
        }
        v.setOnClickListener {
            result('v', v)
        }
        w.setOnClickListener {
            result('w', w)
        }
        x.setOnClickListener {
            result('x', x)
        }
        y.setOnClickListener {
            result('y', y)
        }
        z.setOnClickListener {
            result('z', z)
        }
    }

    private fun result(value: Char, button: Button) {
        if (currentAttempt==7){
            showMyDialog(resources.getString(R.string.loss))
            disableButtons()
        } else if(!currentWord.contains('*')){
            showMyDialog(resources.getString(R.string.win))
            disableButtons()
        } else {
            if (currentWordArrayList.contains(value.toString())){
                for(i in currentWordArrayList.indices){
                    if (currentWordArrayList[i]==value.toString()){
                        currentWord[i]=value
                    }
                }
                if(!currentWord.contains('*')){
                    showMyDialog(resources.getString(R.string.win))
                    disableButtons()
                }
            } else {
                currentAttempt+=1
                if (currentAttempt==7){
                    showMyDialog(resources.getString(R.string.loss))
                    disableButtons()
                }
                loadCurrentImage()
            }
            button.visibility = View.INVISIBLE
            tvWord.text = currentWord.joinToString()
        }
    }

    private fun showMyDialog(result: String){
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle(resources.getString(R.string.result))
            setMessage(result)
            setPositiveButton(resources.getString(R.string.again)) { _: DialogInterface?, _: Int ->
               startActivity(Intent(this@GameActivity, GameActivity::class.java))
                finish()
            }
            setOnDismissListener {
                startActivity(Intent(this@GameActivity, GameActivity::class.java))
                finish()
            }

        }.create().show()
    }

    private fun disableButtons(){
        a.isEnabled = false
        b.isEnabled = false
        c.isEnabled = false
        d.isEnabled = false
        e.isEnabled = false
        f.isEnabled = false
        g.isEnabled = false
        h.isEnabled = false
        i.isEnabled = false
        j.isEnabled = false
        k.isEnabled = false
        l.isEnabled = false
        m.isEnabled = false
        n.isEnabled = false
        o.isEnabled = false
        p.isEnabled = false
        q.isEnabled = false
        r.isEnabled = false
        s.isEnabled = false
        t.isEnabled = false
        u.isEnabled = false
        v.isEnabled = false
        w.isEnabled = false
        x.isEnabled = false
        y.isEnabled = false
        z.isEnabled = false
    }
}