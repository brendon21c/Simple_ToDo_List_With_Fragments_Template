package com.clara.simple_todo_list_with_fragments;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
		AddToDoItemFragment.NewItemCreatedListener,
		ToDoItemDetailFragment.MarkItemAsDoneListener,
		ToDoListFragment.ListItemSelectedListener {


	private static final String TODO_ITEMS_KEY = "TODO ITEMS ARRAY LIST";
	private static final String ADD_NEW_FRAG_TAG = "ADD NEW FRAGMENT";
	private static final String LIST_FRAG_TAG = "LIST FRAGMENT";
	private static final String DETAIL_FRAG_TAG = "DETAIL FRAGMENT";

	private ArrayList<ToDoItem> mTodoItems;

	private static final String TAG = "MAIN ACTIVITY";



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {

			mTodoItems = new ArrayList<>();

			AddToDoItemFragment addNewFragment = AddToDoItemFragment.newInstance();
			ToDoListFragment listFragment = ToDoListFragment.newInstance(mTodoItems);
			ToDoItemDetailFragment detailFragment = ToDoItemDetailFragment.newInstance(new ToDoItem("", false));

			android.app.FragmentManager fm = getFragmentManager();
			android.app.FragmentTransaction ft = fm.beginTransaction();

			ft.add(R.id.add_todo_view_container, addNewFragment, ADD_NEW_FRAG_TAG);
			ft.add(R.id.todo_list_view_container, listFragment, LIST_FRAG_TAG);
			ft.add(R.id.todo_detail_view_container, detailFragment);

			ft.commit();

		} else {

			mTodoItems = savedInstanceState.getParcelableArrayList(TODO_ITEMS_KEY);

		}
	}

	@Override
	public void onSaveInstanceState(Bundle outBundle) {
		super.onSaveInstanceState(outBundle);
		outBundle.putParcelableArrayList(TODO_ITEMS_KEY, mTodoItems);

	}

	@Override
	public void newItemCreated(ToDoItem newItem) {

		mTodoItems.add(newItem);

		android.app.FragmentManager fm = getFragmentManager();
		ToDoListFragment listFragment = (ToDoListFragment) fm.findFragmentByTag(LIST_FRAG_TAG);
		listFragment.notifyItemsChanged();

	}

	@Override
	public void itemSelected(ToDoItem selected) {

		android.app.FragmentManager fm = getFragmentManager();
		android.app.FragmentTransaction ft = fm.beginTransaction();

		ft.replace(R.id.todo_detail_view_container, ToDoItemDetailFragment.newInstance(selected));
		ft.commit();



		/*
		ToDoItemDetailFragment detailFragment = ToDoItemDetailFragment.newInstance(selected);
		ft.add(android.R.id.content, detailFragment);
		ft.addToBackStack(DETAIL_FRAG_TAG);
		ft.commit(); */


	}

	@Override
	public void todoItemDone(ToDoItem doneItem) {

		mTodoItems.remove(doneItem);

		android.app.FragmentManager fm = getFragmentManager();

		ToDoListFragment listFragment = (ToDoListFragment) fm.findFragmentByTag(LIST_FRAG_TAG);
		listFragment.notifyItemsChanged();

		/*
		android.app.FragmentTransaction ft = fm.beginTransaction();
		fm.popBackStack();
		ft.commit();
		*/

	}
}


