package com.banzhi.rxhttpsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.appcompat.widget.AppCompatButton
import com.banzhi.rxhttp.RxHttp
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SuspendActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suspend)
        findViewById<AppCompatButton>(R.id.btnSuspend)
            .setOnClickListener {
                GlobalScope.launch(Dispatchers.IO) {
                    val login = login()
                    Log.e("LOGIN==Suspend",login.toString())
                }
                loginCall().enqueue(object : Callback<BaseBean<Token>> {
                    override fun onResponse(
                        call: Call<BaseBean<Token>>,
                        response: Response<BaseBean<Token>>
                    ) {

                        Log.e("LOGIN==>CALL",response.body().toString())
                    }

                    override fun onFailure(call: Call<BaseBean<Token>>, t: Throwable) {

                    }

                })
                loginRx().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        Log.e("LOGIN==>RX", it.toString())
                    }
            }
    }

    suspend fun login(): BaseBean<Token> {
        return RxHttp.getInstance().getService(ApiServerKt::class.java)
            .login(LoginRequest("zs001", "123456"))
    }

    fun loginCall(): Call<BaseBean<Token>> {
        return RxHttp.getInstance().getService(ApiServerKt::class.java)
            .loginCall(LoginRequest("zs001", "123456"))
    }

    fun loginRx(): Observable<BaseBean<Token>> {
        return RxHttp.getInstance().getService(ApiServerKt::class.java)
            .loginRx(LoginRequest("zs001", "123456"))
    }
}

