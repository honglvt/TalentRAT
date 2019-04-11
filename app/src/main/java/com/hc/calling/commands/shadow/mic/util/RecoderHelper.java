package com.hc.calling.commands.shadow.mic.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import io.reactivex.functions.Consumer;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class RecoderHelper {
    private MediaRecorder mMediaRecorder;
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private Context mContext;
    private RecordCompletedListener recordCompletedListener;

    public RecoderHelper(Context context, RecordCompletedListener recordCompletedListener) {
        this.recordCompletedListener = recordCompletedListener;
        this.mContext = context;
    }

    @SuppressLint("CheckResult")
    public RecoderHelper record(String filePath) throws IOException {
        if (mMediaRecorder == null) {
            mMediaRecorder = new MediaRecorder();
        } else {
            mMediaRecorder.reset();
        }
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mMediaRecorder.setAudioSamplingRate(44100);
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mMediaRecorder.setAudioEncodingBitRate(96000);
        mMediaRecorder.setOutputFile(filePath);
        mMediaRecorder.prepare();
        mMediaRecorder.start();
        io.reactivex.Observable.timer(10, TimeUnit.SECONDS).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                release();
                recordCompletedListener.completed();
            }
        });
        return this;
    }

    public void play(String filePath) {
        try {
            mediaPlayer.setDataSource(filePath);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void release() {
        mMediaRecorder.stop();
        mMediaRecorder.release();
    }


    public interface RecordCompletedListener {
        void completed();
    }

}
