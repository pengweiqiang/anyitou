package cn.com.anyitou.commons;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.Handler.Callback;
import android.text.TextUtils;
import android.widget.ImageView;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class AppsIconLoader implements Callback
{

    private static final String LOADER_THREAD_NAME = "AppsIconLoader";

    private static final int MESSAGE_REQUEST_LOADING = 1;

    private static final int MESSAGE_PHOTOS_LOADED = 2;

    private final int mDefaultResourceId;

    private static class BitmapHolder
    {
        private static final int NEEDED = 0;
        private static final int LOADING = 1;
        private static final int LOADED = 2;

        int state;
        SoftReference<Bitmap> bitmapRef;
    }

    /**
     * A soft cache for photos.
     */
    private final ConcurrentHashMap<String, BitmapHolder> mBitmapCache = new ConcurrentHashMap<String, BitmapHolder>();

    /**
     * A map from ImageView to the corresponding photo ID. Please note that this photo ID may change before the photo
     * loading request is started.
     */
    private final ConcurrentHashMap<ImageView, String> mPendingRequests = new ConcurrentHashMap<ImageView, String>();

    /**
     * Handler for messages sent to the UI thread.
     */
    private final Handler mMainThreadHandler = new Handler(this);

    /**
     * Thread responsible for loading photos from the database. Created upon the first request.
     */
    private LoaderThread mLoaderThread;

    /**
     * A gate to make sure we only send one instance of MESSAGE_PHOTOS_NEEDED at a time.
     */
    private boolean mLoadingRequested;

    /**
     * Flag indicating if the image loading is paused.
     */
    private boolean mPaused;

    private final Context mContext;

    /**
     * Constructor.
     * 
     * @param context
     *            content context
     * @param defaultResourceId
     *            the image resource ID to be used when there is no photo for a contact
     */
    public AppsIconLoader(Context context, int defaultResourceId)
    {
        mDefaultResourceId = defaultResourceId;
        mContext = context;
    }

    /**
     * Load photo into the supplied image view. If the photo is already cached, it is displayed immediately. Otherwise a
     * request is sent to load the photo from the database.
     */
    public void loadPhoto(ImageView view, String photoPath)
    {
        if (TextUtils.isEmpty(photoPath))
        {
            // No photo is needed
            view.setImageResource(mDefaultResourceId);
            mPendingRequests.remove(view);
        }
        else
        {
            boolean loaded = loadCachedPhoto(view,photoPath);
            if (loaded)
            {
                mPendingRequests.remove(view);
            }
            else
            {
                mPendingRequests.put(view,photoPath);
                if (!mPaused)
                {
                    // Send a request to start loading photos
                    requestLoading();
                }
            }
        }
    }

    /**
     * Checks if the photo is present in cache. If so, sets the photo on the view, otherwise sets the state of the photo
     * to {@link BitmapHolder#NEEDED} and temporarily set the image to the default resource ID.
     */
    private boolean loadCachedPhoto(ImageView view, String photoPath)
    {
        BitmapHolder holder = mBitmapCache.get(photoPath);
        if (holder == null)
        {
            holder = new BitmapHolder();
            mBitmapCache.put(photoPath,holder);
        }
        else if (holder.state == BitmapHolder.LOADED)
        {
            // Null bitmap reference means that database contains no bytes for the photo
            if (holder.bitmapRef == null)
            {
                view.setImageResource(mDefaultResourceId);
                return true;
            }

            Bitmap bitmap = holder.bitmapRef.get();
            if (bitmap != null)
            {
                view.setImageBitmap(bitmap);
                return true;
            }

            // Null bitmap means that the soft reference was released by the GC
            // and we need to reload the photo.
            holder.bitmapRef = null;
        }
        // The bitmap has not been loaded - should display the placeholder image.
        view.setImageResource(mDefaultResourceId);
        holder.state = BitmapHolder.NEEDED;
        return false;
    }

    /**
     * Stops loading images, kills the image loader thread and clears all caches.
     */
    public void stop()
    {
        pause();

        if (mLoaderThread != null)
        {
            mLoaderThread.quit();
            mLoaderThread = null;
        }

        clear();
    }

    public void clear()
    {
        mPendingRequests.clear();
        mBitmapCache.clear();
        System.gc();
    }

    /**
     * Temporarily stops loading photos from the database.
     */
    public void pause()
    {
        mPaused = true;
    }

    /**
     * Resumes loading photos from the database.
     */
    public void resume()
    {
        mPaused = false;
        if (!mPendingRequests.isEmpty())
        {
            requestLoading();
        }
    }

    /**
     * Sends a message to this thread itself to start loading images. If the current view contains multiple image views,
     * all of those image views will get a chance to request their respective photos before any of those requests are
     * executed. This allows us to load images in bulk.
     */
    private void requestLoading()
    {
        if (!mLoadingRequested)
        {
            mLoadingRequested = true;
            mMainThreadHandler.sendEmptyMessage(MESSAGE_REQUEST_LOADING);
        }
    }

    /**
     * Processes requests on the main thread.
     */
    @Override
	public boolean handleMessage(Message msg)
    {
        switch (msg.what)
        {
            case MESSAGE_REQUEST_LOADING:
            {
                mLoadingRequested = false;
                if (!mPaused)
                {
                    if (mLoaderThread == null)
                    {
                        mLoaderThread = new LoaderThread(mContext);
                        mLoaderThread.start();
                    }

                    mLoaderThread.requestLoading();
                }
                return true;
            }

            case MESSAGE_PHOTOS_LOADED:
            {
                if (!mPaused)
                {
                    processLoadedImages();
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Goes over pending loading requests and displays loaded photos. If some of the photos still haven't been loaded,
     * sends another request for image loading.
     */
    private void processLoadedImages()
    {
        Iterator<ImageView> iterator = mPendingRequests.keySet().iterator();
        while (iterator.hasNext())
        {
            ImageView view = iterator.next();
            String photoPath = mPendingRequests.get(view);
            boolean loaded = loadCachedPhoto(view,photoPath);
            if (loaded)
            {
                iterator.remove();
            }
        }

        if (!mPendingRequests.isEmpty())
        {
            requestLoading();
        }
    }

    /**
     * Stores the supplied bitmap in cache.
     */
    private void cacheBitmap(String path, Drawable icon)
    {
        if (mPaused)
        {
            return;
        }

        BitmapHolder holder = new BitmapHolder();
        holder.state = BitmapHolder.LOADED;
        if (icon != null)
        {
            try
            {
                BitmapDrawable bd = (BitmapDrawable) icon;   
                Bitmap bitmap = bd.getBitmap(); 
                holder.bitmapRef = new SoftReference<Bitmap>(bitmap);
            }
            catch (OutOfMemoryError e)
            {
                // Do nothing - the photo will appear to be missing
            }
        }
        mBitmapCache.put(path,holder);
    }

    /**
     * Populates an array of photo IDs that need to be loaded.
     */
    private void obtainPhotoPathsToLoad(ArrayList<String> photoPaths)
    {
        photoPaths.clear();

        /*
         * Since the call is made from the loader thread, the map could be
         * changing during the iteration. That's not really a problem:
         * ConcurrentHashMap will allow those changes to happen without throwing
         * exceptions. Since we may miss some requests in the situation of
         * concurrent change, we will need to check the map again once loading
         * is complete.
         */
        Iterator<String> iterator = mPendingRequests.values().iterator();
        while (iterator.hasNext())
        {
            String path = iterator.next();
            BitmapHolder holder = mBitmapCache.get(path);
            if (holder != null && holder.state == BitmapHolder.NEEDED)
            {
                // Assuming atomic behavior
                holder.state = BitmapHolder.LOADING;
                photoPaths.add(path);
            }
        }
    }

    /**
     * The thread that performs loading of photos from the database.
     */
    private class LoaderThread extends HandlerThread implements Callback
    {
        private final Context mContext;
        private PackageManager pManager;
        private final ArrayList<String> mPhotoPaths = new ArrayList<String>();
        private Handler mLoaderThreadHandler;

        public LoaderThread(Context context)
        {
            super(LOADER_THREAD_NAME);
            mContext = context;
            pManager = mContext.getPackageManager();
        }

        /**
         * Sends a message to this thread to load requested photos.
         */
        public void requestLoading()
        {
            if (mLoaderThreadHandler == null)
            {
                mLoaderThreadHandler = new Handler(getLooper(),this);
            }
            mLoaderThreadHandler.sendEmptyMessage(0);
        }

        /**
         * Receives the above message, loads photos and then sends a message to the main thread to process them.
         */
        @Override
		public boolean handleMessage(Message msg)
        {
            loadPhotosOnSdcard();
            mMainThreadHandler.sendEmptyMessage(MESSAGE_PHOTOS_LOADED);
            return true;
        }

        private void loadPhotosOnSdcard()
        {
            obtainPhotoPathsToLoad(mPhotoPaths);

            int count = mPhotoPaths.size();
            if (count == 0)
            {
                return;
            }

            for (int i = 0; i < count; i++)
            {
                String path = mPhotoPaths.get(0);
                try
                {
                    Drawable icon = getIconFromPkgName(path);
                    cacheBitmap(path, icon);
                    mPhotoPaths.remove(path);
                }
                catch (Exception e)
                {
                    mPhotoPaths.remove(path);
                    e.printStackTrace();
                }
            }
            
            count = mPhotoPaths.size();
            for (int i = 0; i < count; i++) {
                cacheBitmap(mPhotoPaths.get(i), null);
            }

        }

        public Drawable getIconFromPkgName(String pkgName)
        {
            try
            {
                PackageInfo info = pManager.getPackageInfo(pkgName, 0);
                Drawable dicon = info.applicationInfo.loadIcon(pManager);
                
                return dicon;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            
            return null;
        }
    }
}
