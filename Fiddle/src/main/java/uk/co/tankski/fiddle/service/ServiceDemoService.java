package uk.co.tankski.fiddle.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.util.Log;

import uk.co.tankski.fiddle.R;

public class ServiceDemoService extends IntentService {
    /**
     * Constant parameter name that represents the field the time set by the activity will be in.
     */
    public static final String PARAMETER_TIME_SET = "timeSet";

    // Blank constructor for instantiation reasons.
    public ServiceDemoService() {
        super("Service Demo Service");
    }

    /**
     * Constructor used to instantiate a new ServiceDemoService object with a given name.
     * @param name - The name used for this service.
     */
    public ServiceDemoService(String name) {
        super(name);
    }

    /**
     * Called by the system when the service is first created. Do not call this method directly.
     */
    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * Called by the system to notify a Service that it is no longer used and is being removed.
     * The service should clean up any resources it holds (threads, registered receivers, etc) at
     * this point. Upon return, there will be no more calls in to this Service object and it is
     * effectively dead. Do not call this method directly.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * Overridden method that is invoked on a worker thread with a request to process. Only one
     * intent is processed at a time, but it is processed independent of other application logic,
     * i.e. the UI thread.
     * @param intent - The value passed to startService(Intent).
     */
    @Override
    protected void onHandleIntent(final Intent intent) {
        // Determine a notification ID for any notifications we'll use.
        final int notificationId = (int) System.currentTimeMillis();

        // Create a new notification builder to create the in progress notification.
        final Notification.Builder serviceNotification =
                new Notification.Builder(getApplicationContext())
                        .setContentTitle("Service Started")
                        .setContentText("Duration: " +
                                intent.getIntExtra(PARAMETER_TIME_SET, 0) + " seconds")
                        .setSmallIcon(R.drawable.ic_service_notification)
                        .setProgress(intent.getIntExtra(PARAMETER_TIME_SET, 0), 0, false)
                        .setAutoCancel(true);

        // Get the notification manager for the system and notify it the new notification.
        final NotificationManager manager = (NotificationManager) getApplicationContext()
                .getSystemService(NOTIFICATION_SERVICE);
        manager.notify(notificationId, serviceNotification.build());

        // Then create a new Runnable object that will do any processing in the background.
        Runnable sleeper = new Runnable() {
            /**
             * Overridden method that performs some action on a separate thread.
             */
            @Override
            public void run() {
                // Try to update the notification progress each second...
                try {
                    for(int i = 0; i < intent.getIntExtra(PARAMETER_TIME_SET, 0); i++) {
                        serviceNotification
                                .setProgress(intent.getIntExtra(PARAMETER_TIME_SET, 0), i, false);
                        manager.notify(notificationId, serviceNotification.build());

                        // And then sleep for another second.
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    // If an exception occurred, log it to logcat, including the stack trace.
                    Log.e("ServiceDemo", "Service was interrupted.", e);
                }

                // Then build the finished notification to be displayed.
                Notification.Builder finishNotification =
                        new Notification.Builder(getApplicationContext())
                            .setContentTitle("Service finished")
                            .setContentText("Service ran for " +
                                    intent.getIntExtra(PARAMETER_TIME_SET, 0) + " seconds")
                            .setSmallIcon(R.drawable.ic_service_notification).setAutoCancel(true);

                /* Then cancel the in progress notification and display the finished one with the
                same notification ID. */
                manager.cancel(notificationId);
                manager.notify(notificationId, finishNotification.build());
            }
        };

        // Execute the thread and when that is over, stop the service.
        sleeper.run();
        stopSelf(notificationId);
    }
}