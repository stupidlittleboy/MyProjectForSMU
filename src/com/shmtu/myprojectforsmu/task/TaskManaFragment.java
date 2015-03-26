package com.shmtu.myprojectforsmu.task;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shmtu.myprojectforsmu.R;

public class TaskManaFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View newsLayout = inflater.inflate(R.layout.task_mana_layout, container,
				false);
		return newsLayout;
	}

}
