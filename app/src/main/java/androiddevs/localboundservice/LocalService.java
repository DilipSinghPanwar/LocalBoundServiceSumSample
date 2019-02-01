package androiddevs.localboundservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Syacraft on 7/2/2016.
 */
public class LocalService extends Service {

    int mStartMode;       // indicates how to behave if the service is killed
    boolean mAllowRebind; // indicates whether onRebind should be used

    IBinder mBinder = new LocalBinder();// interface for clients that bind

    public class LocalBinder extends Binder{
        LocalService getService(){
            return LocalService.this;
        }
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        // The service is being created
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // The service is starting, due to a call to startService()
        return mStartMode;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        // All clients have unbound with unbindService()
        return mAllowRebind;
    }
    @Override
    public void onRebind(Intent intent) {
        // A client is binding to the service with bindService(),
        // after onUnbind() has already been called
    }
    @Override
    public void onDestroy() {
        // The service is no longer used and is being destroyed
    }

    // Input Output action using service
    public int getSum(int a,int b){
        return a+b;
    }
}
