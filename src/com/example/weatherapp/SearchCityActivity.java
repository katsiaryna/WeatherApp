package com.example.weatherapp;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

@TargetApi(11)
public class SearchCityActivity extends Activity implements OnItemLongClickListener{

	private ActionBar mActionBar;
	private ArrayAdapter<String> mAdapter;
	private ListView mListView;
	private int mCurrentPosition;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_city);
		initializeWidgets();
		initializeActionBar();
		
	}
	
	private void initializeWidgets() {
		mListView = (ListView) findViewById(R.id.list_view_search);
		
		mAdapter= new ArrayAdapter<String>(SearchCityActivity.this,R.layout.item_list_search,MainActivity.Cities);
	    mListView.setAdapter(mAdapter);
	    mListView.setOnItemLongClickListener(this);
	}
	
	private void initializeActionBar() {
		mActionBar = SearchCityActivity.this.getActionBar();
		mActionBar.setCustomView(R.layout.action_bar_search_city);
		mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME|ActionBar.DISPLAY_SHOW_CUSTOM);
		
		findViewById(R.id.button_actoinbar_search_add).setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				String city = ((TextView)findViewById(R.id.edit_search_city)).getText().toString();
				MainActivity.mDateBase.addCity(city);
			    MainActivity.mActionBarListAdapter.add(city);
			    
			    
			    Intent intent = new Intent(SearchCityActivity.this,MainActivity.class);
			    startActivity(intent);
			    ((TextView)findViewById(R.id.edit_search_city)).setText("");
			   
			}
		});
	}

	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long position) {
		mCurrentPosition = (int)position;
		showDialog(1);
		return false;
	}
	protected Dialog onCreateDialog(int id) {
		if (id == 1) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		// заголовок
		alertDialogBuilder.setTitle("City");
		// сообщение
		alertDialogBuilder.setMessage("Deleate ?");
		// иконка
		alertDialogBuilder.setIcon(android.R.drawable.ic_delete);
		// кнопка положительного ответа
		
		alertDialogBuilder.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
			
				deleteCity();
				finish();
			}
		});
		alertDialogBuilder.setNeutralButton("Cancel",new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
		
				finish();
			}
		});
		// создаем диалог
		return alertDialogBuilder.create();
		}
		return super.onCreateDialog(id);
		}
     private void deleteCity() {
    	 MainActivity.mActionBarListAdapter.remove(MainActivity.Cities.get(Integer.valueOf(mCurrentPosition)));
         MainActivity.mActionBarListAdapter.notifyDataSetChanged();
         mAdapter.notifyDataSetChanged();
     }
	}

