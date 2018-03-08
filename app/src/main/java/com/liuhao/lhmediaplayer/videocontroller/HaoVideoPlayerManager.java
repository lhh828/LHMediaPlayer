package com.liuhao.lhmediaplayer.videocontroller;

/**
 * 视频播放器管理器.
 */
public class HaoVideoPlayerManager {

    private HaoVideoPlayer mVideoPlayer;

    private HaoVideoPlayerManager() {
    }

    private static HaoVideoPlayerManager sInstance;

    public static synchronized HaoVideoPlayerManager instance() {
        if (sInstance == null) {
            sInstance = new HaoVideoPlayerManager();
        }
        return sInstance;
    }

    public HaoVideoPlayer getCurrentHaoVideoPlayer() {
        return mVideoPlayer;
    }

    public void setCurrentHaoVideoPlayer(HaoVideoPlayer videoPlayer) {
        if (mVideoPlayer != videoPlayer) {
            releaseHaoVideoPlayer();
            mVideoPlayer = videoPlayer;
        }
    }

    public void suspendHaoVideoPlayer() {
        if (mVideoPlayer != null && (mVideoPlayer.isPlaying() || mVideoPlayer.isBufferingPlaying())) {
            mVideoPlayer.pause();
        }
    }

    /**activity生命周期中调用*/
    public void resumeHaoVideoPlayer() {
        if (mVideoPlayer != null && (mVideoPlayer.isPaused() || mVideoPlayer.isBufferingPaused())) {
            mVideoPlayer.restart();
        }
    }

    /**activity生命周期中调用 onstop或ondestory*/
    public void releaseHaoVideoPlayer() {
        if (mVideoPlayer != null) {
            mVideoPlayer.release();
            mVideoPlayer = null;
        }
    }

    public boolean onBackPressd() {
        if (mVideoPlayer != null) {
            if (mVideoPlayer.isFullScreen()) {
                return mVideoPlayer.exitFullScreen();
            } else if (mVideoPlayer.isTinyWindow()) {
                return mVideoPlayer.exitTinyWindow();
            }
        }
        return false;
    }
}
