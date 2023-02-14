package com.example.win33.services

import com.example.win33.model.HangmanModel
import com.example.win33.model.WordModel
import retrofit2.Call
import retrofit2.http.GET

interface HangmanApi {
    @GET("hangman.json")
    fun getImages(): Call<HangmanModel>
    @GET("words.json")
    fun getWords(): Call<WordModel>
}