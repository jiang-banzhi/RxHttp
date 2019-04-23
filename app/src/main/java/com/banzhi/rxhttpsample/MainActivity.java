package com.banzhi.rxhttpsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.banzhi.rxhttp.RxHttp;
import com.banzhi.rxhttp.download.DownloadObserver;
import com.banzhi.rxhttp.exception.ApiException;
import com.banzhi.rxhttp.subscriber.BaseSubscriber;
import com.banzhi.rxhttp.utils.RxHelper;

import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {
    TextView tv;
    Button btnDown;
//    String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1555579341676&di=16205e5ca66348749a3d434b7036d249&imgtype=0&src=http%3A%2F%2Fimg4.duitang.com%2Fuploads%2Fitem%2F201202%2F03%2F20120203155201_BwB3B.jpg";
    String url = "http://192.168.1.210:6003/FilePath//UploadFile/ddf3cdf5-0afb-4e48-8db8-b8e503348622.apk";
//    String url = "http://192.168.1.210:6003/FilePath//UploadFile/1cd251f8-a127-41f7-8253-3b0d0ab27c08.gif";
//    String url = "https://github.com/Trinea/android-open-project/archive/master.zip";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnDown = findViewById(R.id.btn_down);
        tv = findViewById(R.id.tv);
        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxHttp.downloadFile(url).subscribe(new DownloadObserver("image.apk") {


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
        findViewById(R.id.btn_token).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxHttp.getRetrofit().create(ApiServer.class)
                        .loadType()
                        .compose(RxHelper.<BaseBean>switchSchedulers())
                        .subscribe(new BaseSubscriber<BaseBean>() {
                            @Override
                            public void onError(ApiException e) {

                            }

                            @Override
                            public void onNext(BaseBean baseBean) {
                                super.onNext(baseBean);
                            }
                        });
            }
        });
    }
}
