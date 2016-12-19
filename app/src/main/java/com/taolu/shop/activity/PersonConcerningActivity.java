package com.taolu.shop.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.taolu.shop.R;
import com.taolu.shop.utils.LogUtil;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by thinkpad on 2016/11/26.
 */
public class PersonConcerningActivity extends AppCompatActivity {
    @InjectView(R.id.iv_person_concerning_cancel)
    ImageView mIvPersonConcerningCancel;
    @InjectView(R.id.tv_person_concerning)
    TextView mTvPersonConcerning;
    @InjectView(R.id.tv_person_concerning_company)
    TextView mTvPersonConcerningCompany;
    @InjectView(R.id.tv_update)
    TextView mTvUpdate;
    private int mCurrentVersion;
    private String path;
    private int mVersion;
    private String mV2Path;
    private ProgressDialog pd;
    private final int NEW_VERSION = 1;
    public final int NEWEST_VERSION = 2;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case NEW_VERSION:
                    mTvPersonConcerning.setText("版本号:sony  v:" + mCurrentVersion+".0");
                    LogUtil.e("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee", "==========" + msg.what);
                    //说明有新版本了,弹出提示框 提示更新
                    havaNewVersion();
                    break;
                case NEWEST_VERSION://最新版本
                    mTvPersonConcerning.setText("版本号:sony  v:" + mCurrentVersion+".0");
                    Toast.makeText(PersonConcerningActivity.this, "已是最新版本,无须更新", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_concerning);
        ButterKnife.inject(this);
        //版本号:sony v:2.0

        mIvPersonConcerningCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //更新
        mTvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"更新被点击了", Toast.LENGTH_SHORT).show();
                new Thread() {
                    private Message mMsg;

                    @Override
                    public void run() {
                        //获取当前版本的信息
                        PackageInfo packageInfo = null;
                        try {
                            packageInfo = PersonConcerningActivity.this
                                    .getPackageManager()
                                    .getPackageInfo(getApplicationContext()
                                            .getPackageName(), 0);

                            String versionName = packageInfo.versionName;
                            float currentVersionf = Float.parseFloat(versionName);
                            //当前版本的值
                            mCurrentVersion = (int) currentVersionf;
                            //  Toast.makeText(getApplicationContext(), "当前版本" + mCurrentVersion, Toast.LENGTH_SHORT);
                            LogUtil.e("=================================", "" + mCurrentVersion);
                            try {
                                path = "http://192.168.1.104:8080/serverVersion.json";
                                // URL url = new URL(path);
                                // HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                //  conn.setRequestMethod("GET");
                                //  conn.setConnectTimeout(5000);
                                //  if (conn.getResponseCode() != 200) {
                                // InputStream is = conn.getInputStream();
                                // String readStream = StreamTools.readStream(is);
                                // JSONObject object = new JSONObject(readStream);
                                //从json中获取版本信息和apk路径
                                //mVersion = object.getInt("version");
                                //mV2Path = object.getString("v2");
                                //模拟用
                                mVersion = 2;
                                mV2Path = "...";
                                if (mCurrentVersion < mVersion) {
                                    mMsg = Message.obtain();
                                    mMsg.what = NEW_VERSION;
                                    mHandler.sendMessage(mMsg);
                                    //弹出对话框
                                    LogUtil.e("===========================你大爷", "你大爷...");

                                } else {
                                    mMsg = Message.obtain();
                                    mMsg.what = NEWEST_VERSION;
                                    mHandler.sendMessage(mMsg);
                                    //弹出吐司
                                    LogUtil.e("=============================你二爷", "你二爷...");
                                }
                                // }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                        }

                    }
                }.start();
            }
        });
    }

    private void havaNewVersion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PersonConcerningActivity.this);
        builder.setTitle("检测到最新版本,是否更新下载");
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pd = new ProgressDialog(PersonConcerningActivity.this);
                //设置进度条水平
                pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pd.show();
                HttpUtils http = new HttpUtils();
                File sdDir = Environment.getExternalStorageDirectory();
                File file = new File(sdDir, SystemClock.currentThreadTimeMillis() + ".apk");
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    //如果sd卡是挂在状态就下载
                    http.download(mV2Path, file.getAbsolutePath(), new RequestCallBack<File>() {
                        @Override
                        public void onSuccess(ResponseInfo<File> responseInfo) {
                            pd.dismiss();
                            HttpUtils http = new HttpUtils();
                            File sdDir = Environment.getExternalStorageDirectory();
                            File file = new File(sdDir, SystemClock.currentThreadTimeMillis() + ".apk");

                            Toast.makeText(PersonConcerningActivity.this, "下载成功", Toast.LENGTH_SHORT).show();
                            //下载成功,替换安装
                            Intent intent = new Intent();
                            intent.setAction("android.intent.action.VIEW");
                            intent.addCategory("android.intent.category.DEFAULT");
                            intent.setDataAndType(Uri.fromFile(responseInfo.result), "application/vnd.android.package-archive");
                            PersonConcerningActivity.this.startActivity(intent);
                        }

                        @Override
                        public void onFailure(HttpException e, String s) {
                            pd.dismiss();
                            //下载失败
                            Toast.makeText(PersonConcerningActivity.this, "下载失败", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onLoading(long total, long current, boolean isUploading) {
                            super.onLoading(total, current, isUploading);
                            pd.setMax((int) total);
                            pd.setProgress((int) current);
                        }
                    });
                }

            }
        });
        builder.show();
    }
}
