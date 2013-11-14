package uk.co.tankski.fiddle;

import android.app.Activity;
import android.os.Bundle;

import uk.co.tankski.fiddle.fragment.ServiceDemoFragment;

public class ServiceFiddleActivity extends Activity {

    /**
     * Overridden method called when the activity is starting. Any initialisation should be placed
     * within this method.
     * @param savedInstanceState - If the activity is being re-initialized after previously being
     *                           shut down then this Bundle contains the data it most recently
     *                           supplied in onSaveInstanceState(Bundle). Note: Otherwise it is
     *                           null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Call the onCreate method in the superclass and also set the activity content view up.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_fiddle);

        // If the savedInstanceState bundle is null...
        if(savedInstanceState == null) {
            /* Add a new ServiceDemoFragment to the UI using the container FrameLayout as an
            anchor. */
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new ServiceDemoFragment()).commit();
        }
    }
}