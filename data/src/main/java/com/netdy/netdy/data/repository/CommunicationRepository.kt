package com.netdy.netdy.data.repository

import android.content.Context
import com.netdy.netdy.domain.repository.CommunicationRepository
import com.parse.Parse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class CommunicationRepository(private val context: Context) : CommunicationRepository {

    override fun initializeCommunication() {

        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG)

        val builder = OkHttpClient.Builder()
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.networkInterceptors().add(httpLoggingInterceptor)

        Parse.initialize(Parse.Configuration.Builder(context)
            .applicationId("netdy-parse-server-alpha-APP_ID")
            .clientKey(null)
            .clientBuilder(builder)
            .server("https://netdy-parse-server-alpha.herokuapp.com/parse/")
            .enableLocalDataStore()
            .build())
    }
}