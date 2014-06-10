package com.qfc.yft.ui;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
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
	            .showImageOnLoading(R.drawable.load_default)
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
    public static void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you may tune some of them,
		// or you can create default configuration by
		//  ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
		.memoryCacheExtraOptions(200, 200)//taotao
				.threadPriority(Thread.NORM_PRIORITY - 1)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
//                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
//                .memoryCacheSize(2 * 1024 * 1024)
                .discCacheSize(50 * 1024 * 1024)
                .discCacheFileCount(100)
                .writeDebugLogs() // Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}
    
    public enum DisplayOptionType{
    	DEFAULT,TEST
    }
}
