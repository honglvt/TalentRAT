package com.hc.calling.commands.mic.util;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.widget.Toast;

import java.io.IOException;

public class RecoderHelper {
    public MediaRecorder mMediaRecorder;
    public MediaPlayer mediaPlayer = new MediaPlayer();
    private Context mContext;

    public RecoderHelper(Context context) {
        this.mContext = context;
    }

    public void record(String filePath) throws IOException {
        if (mMediaRecorder == null) {
            mMediaRecorder = new MediaRecorder();
        } else {
            mMediaRecorder.reset();
        }
        Toast.makeText(mContext.getApplicationContext(), "开始录音", Toast.LENGTH_SHORT).show();
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mMediaRecorder.setAudioSamplingRate(44100);
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mMediaRecorder.setAudioEncodingBitRate(96000);
        mMediaRecorder.setOutputFile(filePath);
        mMediaRecorder.prepare();
        mMediaRecorder.start();

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

    public void release() {
        mMediaRecorder.release();
        mediaPlayer.release();
    }

}
