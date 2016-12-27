package com.example.zbarscan.scan;


import android.os.Handler;
import android.os.Message;

import com.example.zbarscan.R;


/**
 * 描述: 扫描消息转发
 */
public final class CapturedActivityHandler extends Handler {

	DecodeThread decodeThread = null;
	CapturedActivity activity = null;
	private State state;

	private enum State {
		PREVIEW, SUCCESS, DONE
	}

	public CapturedActivityHandler(CapturedActivity activity) {
		this.activity = activity;
		decodeThread = new DecodeThread(activity);
		decodeThread.start();
		state = State.SUCCESS;
		CameraManager.get().startPreview();
		restartPreviewAndDecode();
	}

	@Override
	public void handleMessage(Message message) {

		switch (message.what) {
		case R.id.zbar_auto_focus:
			if (state == State.PREVIEW) {
				CameraManager.get().requestAutoFocus(this, R.id.zbar_auto_focus);
			}
			break;
		case R.id.zbar_restart_preview:
			restartPreviewAndDecode();
			break;
		case R.id.zbar_decode_succeeded:
			state = State.SUCCESS;
			activity.handleDecode((String) message.obj);// 解析成功，回调
			break;
		case R.id.zbar_decode_failed:
			state = State.PREVIEW;
			CameraManager.get().requestPreviewFrame(decodeThread.getHandler(),R.id.zbar_decode);
			break;
		}

	}

	public void quitSynchronously() {
		state = State.DONE;
		CameraManager.get().stopPreview();
		removeMessages(R.id.zbar_decode_succeeded);
		removeMessages(R.id.zbar_decode_failed);
		removeMessages(R.id.zbar_decode);
		removeMessages(R.id.zbar_auto_focus);
	}

	private void restartPreviewAndDecode() {
		if (state == State.SUCCESS) {
			state = State.PREVIEW;
			CameraManager.get().requestPreviewFrame(decodeThread.getHandler(),R.id.zbar_decode);
			CameraManager.get().requestAutoFocus(this, R.id.zbar_auto_focus);
		}
	}

}
