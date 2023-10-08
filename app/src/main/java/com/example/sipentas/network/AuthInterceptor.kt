package com.example.sipentas.network



import com.example.sipentas.utils.SharePrefs
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor():Interceptor {

    @Inject
    lateinit var pref:SharePrefs
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = pref.getToken()
        val originalRequest = chain.request()
        val modifiedRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $token")
            .build()
        return chain.proceed(modifiedRequest)
    }

}