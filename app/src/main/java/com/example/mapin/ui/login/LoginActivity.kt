package com.example.mapin.ui.login

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mapin.MainActivity
import com.example.mapin.databinding.ActivityLoginBinding
import com.example.mapin.network.model.LoginResponse
import com.example.mapin.network.service.LoginRequest
import com.example.mapin.network.service.LoginService
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding
    val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e(TAG, "카카오계정으로 로그인 실패", error)
        } else if (token != null) {
            Log.i(TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
            startMainActivity()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val kakaoLoginButton = binding.kakaoLoginButton
        loginViewModel =
            ViewModelProvider(this, LoginViewModelFactory())[LoginViewModel::class.java]
        KakaoSdk.init(this, "ffa56ed9ec1ad5bc1c60999cf73c6708")
        kakaoLoginButton.setOnClickListener {
            // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                loginWithKakaoApp()
            } else {
                UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
            }
        }
    }

    private fun loginWithKakaoApp() {
        UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
            if (error != null) {
                Log.e(TAG, "카카오톡으로 로그인 실패", error)
                // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
            } else if (token != null) {
                UserApiClient.instance.me { user, error ->
                    if (error != null) {
                        Log.e(TAG, "사용자 정보 요청 실패", error)
                    } else if (user != null) {
                        lginToServer(user)
                    }
                }
                Log.i(TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")
                startMainActivity()
            }
        }
    }

    private fun lginToServer(user: User) {
        LoginService.create()
            .login(LoginRequest("asdfsaf", "USER", user.properties!!["nickname"]!!))
            .enqueue(object :
                Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    Log.d("BBBBBBBBBBBBBBBBBBBBBBBB", response.body().toString())
                }

                override fun onFailure(
                    call: Call<LoginResponse>,
                    t: Throwable
                ) {

                }
            })
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }
}