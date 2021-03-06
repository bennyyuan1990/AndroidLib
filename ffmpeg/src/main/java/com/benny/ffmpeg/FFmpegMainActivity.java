package com.benny.ffmpeg;

import android.graphics.PixelFormat;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class FFmpegMainActivity extends AppCompatActivity implements OnClickListener {

    private TextView mContentTv;
    private SurfaceView mSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ffmpeg_main);

        findViewById(R.id.ffmpeg_Protocol).setOnClickListener(this);
        findViewById(R.id.ffmpeg_AVCodec).setOnClickListener(this);
        findViewById(R.id.ffmpeg_AVFilter).setOnClickListener(this);
        findViewById(R.id.ffmpeg_AVFormat).setOnClickListener(this);
        findViewById(R.id.ffmpeg_Configure).setOnClickListener(this);
        findViewById(R.id.ffmpeg_Decode2YUV).setOnClickListener(this);
        mSurfaceView = (SurfaceView) findViewById(R.id.ffmpeg_surfaceView);
        /*mSurfaceView.getHolder().addCallback(new Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                final String file =Environment.getExternalStorageDirectory() + "/Download/Wildlife.wmv";
                final Surface surface =  mSurfaceView.getHolder().getSurface();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        FFmpegPlayer.play(file,surface);
                    }
                });

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
*/
        mContentTv = (TextView) findViewById(R.id.ffmpeg_Content_tv);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ffmpeg_Protocol) {
            mContentTv.setText(FFmpegInfo.getProtocol());
        } else if (id == R.id.ffmpeg_AVCodec) {
            mContentTv.setText(FFmpegInfo.getAVCodec());
        } else if (id == R.id.ffmpeg_AVFilter) {
            mContentTv.setText(FFmpegInfo.getAVFilter());
        } else if (id == R.id.ffmpeg_AVFormat) {
            mContentTv.setText(FFmpegInfo.getAVFormat());
        } else if (id == R.id.ffmpeg_Configure) {
            mContentTv.setText(FFmpegInfo.getConfigure());
        } else if (id == R.id.ffmpeg_Decode2YUV) {
            try {
                final String file =Environment.getExternalStorageDirectory() + "/Download/Wildlife.wmv";

               // Environment.getExternalStorageDirectory() + "/Download/test.avi"
                mSurfaceView.getHolder().setFormat(PixelFormat.RGBA_8888);
                final Surface surface =  mSurfaceView.getHolder().getSurface();
               // FFmpegPlayer.play(file,surface);

                FFmpegMediaPlayer mediaPlayer = new FFmpegMediaPlayer();
                mediaPlayer.player(file,surface);

               // FFmpegPlayer.audio(Environment.getExternalStorageDirectory() + "/Download/Wildlife.wmv");

                //FFmpegCode.Decode2PCM(Environment.getExternalStorageDirectory() + "/Download/Maid with the Flaxen Hair.mp3", Environment.getExternalStorageDirectory() + "/Download/Wildlife.pcm");

                //FFmpegCode.Decode2YUV(Environment.getExternalStorageDirectory() + "/Download/Wildlife.wmv", Environment.getExternalStorageDirectory() + "/Download/Wildlife.yuv");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
