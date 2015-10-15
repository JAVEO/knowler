package eu.javeo.knowler.client.mobile.knowler;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import de.greenrobot.event.EventBus;
import eu.javeo.knowler.client.mobile.knowler.event.ApplicationEvent;
import eu.javeo.knowler.client.mobile.knowler.event.ChangedMovieTimeEvent;
import eu.javeo.knowler.client.mobile.knowler.event.EventBusListener;
import eu.javeo.knowler.client.mobile.knowler.event.youtube.BufferingEvent;
import eu.javeo.knowler.client.mobile.knowler.event.youtube.PausedEvent;
import eu.javeo.knowler.client.mobile.knowler.event.youtube.PlayingEvent;
import eu.javeo.knowler.client.mobile.knowler.event.youtube.SeekToEvent;
import eu.javeo.knowler.client.mobile.knowler.event.youtube.StoppedEvent;
import eu.javeo.knowler.client.mobile.knowler.util.SystemUiHider;
import java.util.Random;

public class ShowPresentationActivity extends YouTubeFailureRecoveryActivity implements EventBusListener<ApplicationEvent> {

	public static final String EXTRA_VIDEO_ID = "EXTRA_VIDEO_ID";

	private static final boolean AUTO_HIDE = true;

	private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

	private static final boolean TOGGLE_ON_CLICK = true;

	private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

	@InjectView(R.id.fullscreen_content_controls)
	protected View controlsView;

	@InjectView(R.id.fullscreen_content)
	protected View contentView;

	Handler mHideHandler = new Handler();

	/**
	 * Touch listener to use for in-layout UI controls to delay hiding the
	 * system UI. This is to prevent the jarring behavior of controls going away
	 * while interacting with activity UI.
	 */
	View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View view, MotionEvent motionEvent) {
			if (AUTO_HIDE) {
				delayedHide(AUTO_HIDE_DELAY_MILLIS);
			}
			return false;
		}
	};

	private SystemUiHider mSystemUiHider;

	Runnable mHideRunnable = new Runnable() {
		@Override
		public void run() {
			mSystemUiHider.hide();
		}
	};

	private YouTubePlayer player;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fullscreen);
		ButterKnife.inject(this);
		EventBus.getDefault().register(this);

		mSystemUiHider = SystemUiHider.getInstance(this, contentView, HIDER_FLAGS);
		mSystemUiHider.setup();
		mSystemUiHider.setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
			int mControlsHeight;

			int mShortAnimTime;

			@Override
			@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
			public void onVisibilityChange(boolean visible) {
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
					if (mControlsHeight == 0) {
						mControlsHeight = controlsView.getHeight();
					}
					if (mShortAnimTime == 0) {
						mShortAnimTime = getResources().getInteger(
								android.R.integer.config_shortAnimTime);
					}
					controlsView.animate()
							.translationY(visible ? 0 : mControlsHeight)
							.setDuration(mShortAnimTime);
				} else {
					controlsView.setVisibility(visible ? View.VISIBLE : View.GONE);
				}

				if (visible) {
					delayedHide(AUTO_HIDE_DELAY_MILLIS);
				}
			}
		});

		contentView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (TOGGLE_ON_CLICK) {
					mSystemUiHider.toggle();
				} else {
					mSystemUiHider.show();
				}
			}
		});

		YouTubePlayerFragment youTubePlayerFragment = (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);
		youTubePlayerFragment.initialize(DeveloperKey.DEVELOPER_KEY, this);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		delayedHide(100);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

	private void delayedHide(int delayMillis) {
		mHideHandler.removeCallbacks(mHideRunnable);
		mHideHandler.postDelayed(mHideRunnable, delayMillis);
	}

	@Override
	public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
		if (!wasRestored) {
			player.cueVideo(getIntent().getStringExtra(EXTRA_VIDEO_ID));
			player.setPlaybackEventListener(new YouTubePlayer.PlaybackEventListener() {
				@Override
				public void onPlaying() {
					EventBus.getDefault().post(new PlayingEvent());
				}

				@Override
				public void onPaused() {
					EventBus.getDefault().post(new PausedEvent());
				}

				@Override
				public void onStopped() {
					EventBus.getDefault().post(new StoppedEvent());
				}

				@Override
				public void onBuffering(boolean b) {
					EventBus.getDefault().post(new BufferingEvent(b));
				}

				@Override
				public void onSeekTo(int i) {
					EventBus.getDefault().post(new SeekToEvent(i));
				}
			});
			this.player = player;
		}
	}

	@Override
	protected YouTubePlayer.Provider getYouTubePlayerProvider() {
		return (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);
	}

	@Override
	public void onEvent(ApplicationEvent event) {
		if (event instanceof ChangedMovieTimeEvent) {
			player.seekToMillis(new Random().nextInt(20000));
		}
	}
}
