package uk.co.tankski.fiddle.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import uk.co.tankski.fiddle.R;
import uk.co.tankski.fiddle.service.ServiceDemoService;

public class ServiceDemoFragment extends Fragment {

    // Blank constructor for instantiation purposes.
    public ServiceDemoFragment() {
    }

    /**
     * Overridden method called when the fragment is asked to instantiate is user interface.
     * @param inflater - The LayoutInflater object that can be used to inflate any views in the
     *                 fragment,
     * @param container - If non-null, this is the parent view that the fragment's UI should be
     *                  attached to. The fragment should not add the view itself, but this can be
     *                  used to generate the LayoutParams of the view,
     * @param savedInstanceState - If non-null, this fragment is being re-constructed from a
     *                           previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the view and get any UI objects we want to modify from it.
        View rootView = inflater.inflate(R.layout.fragment_service_demo, container, false);

        final TextView timeDisplay = (TextView) rootView.findViewById(R.id.demoTimeDisplay);
        final SeekBar timeSeekBar = (SeekBar) rootView.findViewById(R.id.demoTimeSeeker);

        Button startDemoButton = (Button) rootView.findViewById(R.id.demoStartButton);

        // Set the time seeker bar onSeekBarChangeListener to a new, custom one.
        timeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            /**
             * Notification that the progress level has changed. Clients can use the fromUser
             * parameter to distinguish user-initiated changes from those that occurred
             * programmatically.
             * @param seekBar - The SeekBar whose progress has changed,
             * @param progress - The current progress level. This will be in the range 0..max where
             *                 max was set by setMax(int). (The default value for max is 100.)
             * @param isUser - True if the progress change was initiated by the user.
             */
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean isUser) {
                // Set the time display text to the new progress of the seek bar.
                timeDisplay.setText(getResources().getString(R.string.demo_time_display, progress));
            }

            /**
             * Notification that the user has started a touch gesture. Clients may want to use this
             * to disable advancing the seekbar.
             * @param seekBar - The SeekBar in which the touch gesture began.
             */
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            /**
             * Notification that the user has finished a touch gesture. Clients may want to use
             * this to re-enable advancing the seekbar.
             * @param seekBar - The SeekBar in which the touch gesture began.
             */
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        // Set the demo start button's onClickListener.
        startDemoButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when a view has been clicked.
             * @param view - The view that was clicked.
             */
            @Override
            public void onClick(View view) {
                // Create a new intent for the service and pass the seeker bar progress to it.
                Intent serviceIntent = new Intent(getActivity(), ServiceDemoService.class);
                serviceIntent.putExtra(ServiceDemoService.PARAMETER_TIME_SET,
                        timeSeekBar.getProgress());

                // Then start the service using the new intent.
                getActivity().startService(serviceIntent);
            }
        });

        // Set up the time display for the first time.
        timeDisplay.setText(getResources()
                .getString(R.string.demo_time_display, timeSeekBar.getProgress()));

        // Finally return the view for the user interface.
        return rootView;
    }
}