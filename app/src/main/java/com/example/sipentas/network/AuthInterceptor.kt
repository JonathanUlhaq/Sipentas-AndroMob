package com.example.sipentas.network



import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor():Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val modifiedRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MywibmFtZSI6Im4iLCJ1c2VybmFtZSI6ImFkbWluaWMiLCJuaWsiOiIxMjMxMjMxMjMxMjMxMjMyIiwic2VudHJhIjo3LCJlbWFpbCI6ImFkbWluaWNAZ21haWwuY29tIiwicGFzc3dvcmQiOiIkMnkkMTAkU3lDd0hoWnlvWmZtOXdhak9NOGguTzdHd3ZyT3REYm5PUkZtRVJOSXMzcFE5NDBYTWNVUUMiLCJjcmVhdGVkX2F0IjoiMjAyMy0wOS0wOFQxMDoyOTozNy4yMjdaIiwidXBkYXRlZF9hdCI6IjIwMjMtMDktMDhUMTA6Mjk6MzcuMjI3WiIsImlhdCI6MTY5NTQ3MTc3Nn0.tD2poIuV4jLzJMOVo4kVdDOmG_QZ1qq9jHCQGLWia34")
            .build()
        return chain.proceed(modifiedRequest)
    }

}