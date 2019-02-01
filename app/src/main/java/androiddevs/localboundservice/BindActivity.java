package androiddevs.localboundservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BindActivity extends AppCompatActivity {

    LocalService mService;
    boolean mBound = false;
    @BindView(R.id.etNumOne)
    EditText etNumOne;
    @BindView(R.id.etNumTwo)
    EditText etNumTwo;
    @BindView(R.id.btnGetSum)
    Button btnGetSum;
    @BindView(R.id.tvResult)
    TextView tvResult;
    private String value1, value2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Bind to LocalService
        Intent intent = new Intent(this, LocalService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from the service
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }


    /**
     * Defines callbacks for service binding, passed to bindService()
     */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            LocalService.LocalBinder binder = (LocalService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    @OnClick(R.id.btnGetSum)
    public void onViewClicked() {
        value1 = etNumOne.getText().toString().trim();
        value2 = etNumTwo.getText().toString().trim();
        if (value1.length() > 0 && value2.length() > 0){
            if (mBound) {
                // Call a method from the LocalService.
                // However, if this call were something that might hang, then this request should
                // occur in a separate thread to avoid slowing down the activity performance.
                int num = mService.getSum(Integer.parseInt(value1), Integer.parseInt(value2));
                Toast.makeText(mService, num + "", Toast.LENGTH_SHORT).show();
                tvResult.setText(num + "");
            } else {
                Toast.makeText(BindActivity.this, "Service is not bind!", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(BindActivity.this, "Please enter values.", Toast.LENGTH_SHORT).show();
        }
    }
}