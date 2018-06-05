package com.banzhi.rxhttpsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.banzhi.rxhttp.RxHttp;
import com.banzhi.rxhttp.download.DownloadObserver;

import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {
    TextView tv;
    Button btnDown;
    String url = "http://220.170.49.110/6/p/z/o/m/pzomnzzmarvqljydxmlpdwuznlksvl/hd.yinyuetai.com/C9330152A4DFC7FB2FBC3B6070E67899.flv?sc\\u003d79f895a4e8743128\\u0026br\\u003d1105\\u0026vid\\u003d2491056\\u0026aid\\u003d201\\u0026area\\u003dHT\\u0026vst\\u003d4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnDown = findViewById(R.id.btn_down);
        tv = findViewById(R.id.tv);
        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxHttp.downloadFile(url).subscribe(new DownloadObserver("image") {


                    @Override
                    protected void getDisposable(Disposable d) {

                    }

                    @Override
                    protected void onError(String errorMsg) {

                    }

                    @Override
                    protected void onSuccess(long bytesRead, long contentLength, float progress, boolean done, String filePath) {
                        Log.i("TAG", "bytesRead=" + bytesRead + "     contentLength=" + contentLength + "  progress=" + progress + "  done" + done + "   filePath" + filePath);
                        tv.setText("bytesRead=" + bytesRead + "     contentLength=" + contentLength + "  progress=" + progress + "  done" + done + "   filePath" + filePath);
                    }
                });
            }
        });
    }
}
