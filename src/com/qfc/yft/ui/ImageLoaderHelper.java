package com.qfc.yft.ui;

import java.util.HashMap;
import java.util.Map;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.qfc.yft.R;

/**
 * 
 * @author taotao
 * @Date 2014-6-6
 */
public class ImageLoaderHelper {
	public static ImageLoader imageLoader = ImageLoader.getInstance();
//    public static DisplayImageOptions image_display_options = 
    
    		static Map<DisplayOptionType, DisplayImageOptions> map = new HashMap<ImageLoaderHelper.DisplayOptionType, DisplayImageOptions>();
    		
    public static DisplayImageOptions getDisplayOpts(DisplayOptionType type){
    	DisplayImageOptions dio ;
    	if(null==(dio = map.get(type))) {
    		
	    	switch (type) {
			case TEST:
				
				break;
	
			default:
				dio = new DisplayImageOptions.Builder()
	            .showImageOnLoading(R.drawable.ic_launcher)
	            .showImageForEmptyUri(R.drawable.ic_launcher)
	            .showImageOnFail(R.drawable.ic_launcher)
	            .displayer(new SimpleBitmapDisplayer())
	            .cacheInMemory(true)
	            .cacheOnDisc(false)
	            .build();
				break;
			}
	    	map.put(type, dio);
    	}
    	return dio;
    }
    
    
    public enum DisplayOptionType{
    	DEFAULT,TEST
    }
}
