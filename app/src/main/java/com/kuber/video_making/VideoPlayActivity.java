package com.kuber.video_making;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;

import java.io.File;

public class VideoPlayActivity extends AppCompatActivity {

    private static final String TAG = VideoPlayActivity.class.getSimpleName();
    private FFmpeg ffmpeg;
    private VideoView videoView;
    private Button createTV;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);

        videoView = findViewById(R.id.generatedVideo);
        createTV = findViewById(R.id.createTV);
        progressDialog = new ProgressDialog(VideoPlayActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        loadFFMpegBinary();
       makeVideo();

        createTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VideoPlayActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private void loadFFMpegBinary() {
        try {
            if (ffmpeg == null) {
                Log.d(TAG, "ffmpeg : era nulo");
                ffmpeg = FFmpeg.getInstance(this);
            }
            ffmpeg.loadBinary(new LoadBinaryResponseHandler() {
                @Override
                public void onFailure() {
                    showUnsupportedExceptionDialog();
                }

                @Override
                public void onSuccess() {
                    Log.d(TAG, "ffmpeg : correct Loaded");
                }
            });
        } catch (FFmpegNotSupportedException e) {
            showUnsupportedExceptionDialog();
        } catch (Exception e) {
            Log.d(TAG, "EXception no controlada : " + e);
        }
    }

    private void showUnsupportedExceptionDialog() {
        new AlertDialog.Builder(VideoPlayActivity.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Not Supported")
                .setMessage("Device Not Supported")
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        VideoPlayActivity.this.finish();
                    }
                })
                .create()
                .show();

    }

    private void makeVideo() {
        File picDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String fileOutputPrefix = "anim_video";
        String fileOutputExtn = ".mp4";
        File dest = new File(picDir, fileOutputPrefix + fileOutputExtn);

        String command[] = {"-y",
                "-loop", "1", "-i", picDir + "/video_photo001.PNG",
                "-loop", "1", "-i", picDir + "/video_photo002.PNG",
                "-loop", "1", "-i", picDir + "/video_photo003.PNG",
                "-filter_complex",
                "[0:v]trim=duration=3,overlay=shortest=1:enable='between(t,2.5,3)':y=800-(t)*266," +
                        "drawtext=fontfile=/system/fonts/DroidSans.ttf: text='WELCOME' :fontsize=60 :fontcolor=ffffff :alpha='if(lt(t,1),0,if(lt(t,2),(t-1)/1,if(lt(t,3),1,if(lt(t,4),(1-(t-3))/1,0))))':x=(w-text_w)/2:y=60," +
                        "drawtext=enable='between(t,0,2.5)':text='START BEFORE YOU ARE READY':fontfile=/system/fonts/DroidSans.ttf:fontsize=60:fontcolor=ffffff:x=w-275*t:y=700," +
                        "drawtext=enable='between(t,0,2.5)':fontfile=/system/fonts/DroidSans.ttf:text='RISE':fontsize=60:fontcolor=ffffff:alpha='if(lt(t,1),0,if(lt(t,2),(t-1)/1,if(lt(t,3),1,if(lt(t,4),(1-(t-3))/1,0))))':x=w/2:y=h/2-text_h," +
                        "drawtext=enable='between(t,0,2.5)':fontfile=/system/fonts/DroidSans.ttf:text='TOGETHER':fontsize=60:fontcolor=ffffff:alpha='if(lt(t,1),0,if(lt(t,2),(t-1)/1,if(lt(t,3),1,if(lt(t,4),(1-(t-3))/1,0))))':x=w/2:y=h/2,setsar=1/1[v0];" +
                        "[1:v]trim=duration=3,overlay=shortest=1:enable='between(t,2.5,3)':y=-(800)+(t)*266," +
                        "drawtext=enable='between(t,0,2.5)':text='T H E  W O R L D  I S':fontfile=/system/fonts/DroidSans.ttf:fontsize=40:fontcolor=2fa4b6:x=(w-text_w)/2:y=100+t*30-100," +
                        "drawtext=enable='between(t,0,2.5)':text='beautiful':fontfile=/system/fonts/DroidSans.ttf:fontsize=60:fontcolor=ffffff:alpha='if(lt(t,1),0,if(lt(t,2),(t-1)/1,if(lt(t,3),1,if(lt(t,4),(1-(t-3))/1,0))))':x=(w-text_w)/2:y=150," +
                        "drawtext=enable='between(t,0,2.5)':fontfile=/system/fonts/DroidSans.ttf:text='SAVE THE PLANET':fontsize=60:fontcolor=ffffff:alpha='if(lt(t,1),0,if(lt(t,2),(t-1)/1,if(lt(t,3),1,if(lt(t,4),(1-(t-3))/1,0))))':x=(w-text_w)/2:y=700,setsar=1/1[v1];" +
                        "[2:v]trim=duration=3," +
                        "drawtext=fontfile=/system/fonts/DroidSans.ttf:text='just':fontsize=80:fontcolor=ffffff:alpha='if(lt(t,1),0,if(lt(t,2),(t-1)/1,if(lt(t,3),1,if(lt(t,4),(1-(t-3))/1,0))))':x=200:y=50," +
                        "drawtext=fontfile=/system/fonts/DroidSans.ttf:text='Breathe':fontsize=80:fontcolor=ffffff:alpha='if(lt(t,1),0,if(lt(t,2),(t-1)/1,if(lt(t,3),1,if(lt(t,4),(1-(t-3))/1,0))))':x=150:y=110," +
                        "drawtext=text='Nature is pleased':fontfile=/system/fonts/DroidSans.ttf:fontsize=40:fontcolor=white:x=(w-text_w)/2:y=800-t*30," +
                        "drawtext=text='With Simplicity':fontfile=/system/fonts/DroidSans.ttf:fontsize=40:fontcolor=white:x=(w-text_w)/2:y=850-t*30," +
                        "drawtext=fontsize=40:fontcolor=white:fontfile=/system/fonts/DroidSans.ttf:text='STAY CLOSE TO NATURE':x=(-520)+(t)*200:y=400,setsar=1/1[v2];" +
                        "[v0][v1][v2]concat=n=3:v=1:a=0,setsar=1/1[v]", "-map", "[v]", "-aspect", "1:1", "-crf", "27", "-preset", "veryfast", "-pix_fmt", "yuv420p", dest.getAbsolutePath()};

        execFFmpegBinary(command);

    }


    private void execFFmpegBinary(final String[] command) {
        try {
            ffmpeg.execute(command, new ExecuteBinaryResponseHandler() {
                @Override
                public void onFailure(String s) {

                }

                @Override
                public void onSuccess(String s) {

                }

                @Override
                public void onProgress(String s) {

                    progressDialog.setMessage(s);
                }

                @Override
                public void onStart() {

                }

                @Override
                public void onFinish() {
                    progressDialog.dismiss();
                    playVideo();
                }
            });
        } catch (FFmpegCommandAlreadyRunningException e) {

        }
    }

    private void playVideo() {
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/anim_video.mp4";
        File file = new File(path);
        if (file.exists()) {
            Uri uri = Uri.parse(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/anim_video.mp4");
            videoView.stopPlayback();
            videoView.setVideoURI(uri);
            videoView.start();
        }
    }
}
