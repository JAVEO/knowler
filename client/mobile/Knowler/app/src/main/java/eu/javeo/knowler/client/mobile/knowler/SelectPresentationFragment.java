package eu.javeo.knowler.client.mobile.knowler;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class SelectPresentationFragment extends Fragment {

	@InjectView(R.id.recyclerView)
	protected RecyclerView recyclerView;

	protected SelectPresentationAdapter adapter;

	public static SelectPresentationFragment newInstance() {
		SelectPresentationFragment fragment = new SelectPresentationFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	public SelectPresentationFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View inflate = inflater.inflate(R.layout.fragment_select_presentation, container, false);
		ButterKnife.inject(this, inflate);
		adapter = new SelectPresentationAdapter(getActivity());
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		recyclerView.setAdapter(adapter);
		return inflate;
	}
}
