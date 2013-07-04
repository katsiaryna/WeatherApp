package data;

import java.io.IOException;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.example.weatherapp.R;

public class ImageContainer {
	//format[number of picture in assets :code of weather]
	private Hashtable<String, Drawable> keyImage = new Hashtable<String, Drawable>();
	private Context context;
	private  String [] keys ;
	
	public Set<String> set = new HashSet<String>();
	
	public ImageContainer(Context context) {
		this.context  = context;
		keys = this.context.getResources().getStringArray(R.array.keys);
		try {
			for(int i = 0; i < keys.length ; i++) {
				String[] tmp = keys[i].split("\\:");
				//put  [weather code] [Drawable]
				keyImage.put(tmp[1], returnDrawableFromFile("pict"+tmp[0]+".png"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private Drawable returnDrawableFromFile(String file) throws IOException {
		return Drawable.createFromStream(context.getAssets().open(file),null);
	}
	
	public Hashtable<String,Drawable> getContainer(){
		return keyImage;
	}
	
}
