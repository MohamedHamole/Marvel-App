package com.example.imagesapp.data.remote

import com.example.imagesapp.BuildConfig
import com.example.imagesapp.utils.Constants.API_KEY
import com.example.imagesapp.utils.Constants.HASH
import com.example.imagesapp.utils.Constants.MD5
import com.example.imagesapp.utils.Constants.TS
import okhttp3.Interceptor
import okhttp3.Response
import java.nio.charset.StandardCharsets
import java.security.MessageDigest

class AppInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val requiresAuth = originalRequest.header("Requires-Auth")?.toBoolean() ?: true

        if (requiresAuth) {
            val timestamp = System.currentTimeMillis()
            val publicKey = BuildConfig.PUBLIC_KEY
            val privateKey = BuildConfig.PRIVATE_KEY
            val hash = generateHash(timestamp, privateKey, publicKey)

            val newUrl = originalRequest.url.newBuilder()
                .addQueryParameter(TS, timestamp.toString())
                .addQueryParameter(API_KEY, publicKey)
                .addQueryParameter(HASH, hash)
                .build()

            val newRequest = originalRequest.newBuilder()
                .url(newUrl)
                .build()

            return chain.proceed(newRequest)
        } else {
            return chain.proceed(originalRequest)
        }
    }

    private fun generateHash(timestamp: Long, privateKey: String, publicKey: String): String {
        val data = "$timestamp$privateKey$publicKey"

        // Using MD5 for hash generation
        val md5 = MessageDigest.getInstance(MD5)
        val hashBytes = md5.digest(data.toByteArray(StandardCharsets.UTF_8))

        // Convert the byte array to a hexadecimal string
        return hashBytes.joinToString("") { "%02x".format(it) }
    }
}
