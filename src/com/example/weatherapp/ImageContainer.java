package com.example.weatherapp;

import java.util.Dictionary;
import java.util.Hashtable;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class ImageContainer {

	private Dictionary<String, Drawable> keyImage = new Hashtable<String, Drawable>();
	private Context context;
	private  int [] keys ;
	private Drawable [] images;
	
	public ImageContainer(Context context) {
		this.context  = context;
		keys = this.context.getResources().getIntArray(R.array.keys);
		images = new Drawable[]{
				this.context.getResources().getDrawable(R.drawable.pict16),
				this.context.getResources().getDrawable(R.drawable.pict17), 
				this.context.getResources().getDrawable(R.drawable.pict0),
				this.context.getResources().getDrawable(R.drawable.pict17),
				this.context.getResources().getDrawable(R.drawable.pict7), 
				this.context.getResources().getDrawable(R.drawable.pict5),
				this.context.getResources().getDrawable(R.drawable.pict16),
				this.context.getResources().getDrawable(R.drawable.pict15), 
				this.context.getResources().getDrawable(R.drawable.pict5),
				this.context.getResources().getDrawable(R.drawable.pict5),
				this.context.getResources().getDrawable(R.drawable.pict12), 
				this.context.getResources().getDrawable(R.drawable.pict12),
				this.context.getResources().getDrawable(R.drawable.pict11),
				this.context.getResources().getDrawable(R.drawable.pict7), 
				this.context.getResources().getDrawable(R.drawable.pict14),
				this.context.getResources().getDrawable(R.drawable.pict16),
				this.context.getResources().getDrawable(R.drawable.pict14), 
				this.context.getResources().getDrawable(R.drawable.pict14),
				this.context.getResources().getDrawable(R.drawable.pict15),
				this.context.getResources().getDrawable(R.drawable.pict15), 
				this.context.getResources().getDrawable(R.drawable.pict13),
				this.context.getResources().getDrawable(R.drawable.pict7),
				this.context.getResources().getDrawable(R.drawable.pict7), 
				this.context.getResources().getDrawable(R.drawable.pict7),
				this.context.getResources().getDrawable(R.drawable.pict12),
				this.context.getResources().getDrawable(R.drawable.pict12), 
				this.context.getResources().getDrawable(R.drawable.pict12),
				this.context.getResources().getDrawable(R.drawable.pict12),
				this.context.getResources().getDrawable(R.drawable.pict11), 
				this.context.getResources().getDrawable(R.drawable.pict11),
				this.context.getResources().getDrawable(R.drawable.pict7),
				this.context.getResources().getDrawable(R.drawable.pict7), 
				this.context.getResources().getDrawable(R.drawable.pict11),
				this.context.getResources().getDrawable(R.drawable.pict11),
				this.context.getResources().getDrawable(R.drawable.pict19), 
				this.context.getResources().getDrawable(R.drawable.pict19),
				this.context.getResources().getDrawable(R.drawable.pict14),
				this.context.getResources().getDrawable(R.drawable.pict13), 
				this.context.getResources().getDrawable(R.drawable.pict17),
				this.context.getResources().getDrawable(R.drawable.pict7),
				this.context.getResources().getDrawable(R.drawable.pict7), 
				this.context.getResources().getDrawable(R.drawable.pict5),
				this.context.getResources().getDrawable(R.drawable.pict11),
				this.context.getResources().getDrawable(R.drawable.pict22), 
				this.context.getResources().getDrawable(R.drawable.pict26),
				this.context.getResources().getDrawable(R.drawable.pict30),
				this.context.getResources().getDrawable(R.drawable.pict28), 
				this.context.getResources().getDrawable(R.drawable.pict32)
		};
		for (int i = 0; i<keys.length; i++){
			keyImage.put(String.valueOf(keys[i]), images[i]);
		}
	}

	public Dictionary<String,Drawable> getContainer(){
		return keyImage;
	}
}
