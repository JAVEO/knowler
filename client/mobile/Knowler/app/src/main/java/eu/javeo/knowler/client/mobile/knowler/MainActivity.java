package eu.javeo.knowler.client.mobile.knowler;

import android.app.Fragment;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

	static final String ACTION_FRAGMENT_CHANGED = "ACTION_FRAGMENT_CHANGED";

	static final String EXTRA_FRAGMENT_TITLE = "EXTRA_FRAGMENT_TITLE";

	private static final long DRAWER_CLOSE_DELAY_MS = 350;

	@InjectView(R.id.drawer_layout)
	protected DrawerLayout drawerLayout;

	@InjectView(R.id.toolbar)
	protected Toolbar toolbar;

	@InjectView(R.id.navigation_view)
	protected NavigationView navigationView;

	private ActionBarDrawerToggle drawerToggle;

	private final Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.inject(this);
		prepareNavigationView();
		showFragment(SelectPresentationFragment.newInstance(), "Select Profile");
	}

	private void prepareNavigationView() {
		setSupportActionBar(toolbar);
		navigationView.setNavigationItemSelectedListener(this);
		drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.text_open_navigation, R.string.text_close_navigation);
		drawerLayout.setDrawerListener(drawerToggle);
		drawerToggle.syncState();
	}

	private void navigate(final MenuItem item) {
		switch (item.getItemId()) {
			default:
				break;
		}
	}

	@Override
	public boolean onNavigationItemSelected(final MenuItem menuItem) {
		menuItem.setChecked(true);
		drawerLayout.closeDrawer(GravityCompat.START);
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				navigate(menuItem);
			}
		}, DRAWER_CLOSE_DELAY_MS);
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	protected void showFragment(Fragment fragment, String title) {
		sendBroadcast(new Intent(ACTION_FRAGMENT_CHANGED).putExtra(EXTRA_FRAGMENT_TITLE, title));
		getFragmentManager().beginTransaction().replace(R.id.content, fragment).addToBackStack(null).commit();
	}
}
