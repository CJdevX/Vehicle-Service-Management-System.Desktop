package Class;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import javax.swing.JLabel;

public class RealTimeRemainingTime {

    private LocalTime targetTime; // Store the target time
    private Timer timer; // Timer for periodic updates
    private JLabel label; // JLabel to update with remaining time

    public RealTimeRemainingTime(String targetTimeString, JLabel lable) {
        this.label = lable;
        
        // Parse the 12-hour time format (e.g., "7:30 PM")
        DateTimeFormatter formatter12Hour = DateTimeFormatter.ofPattern("h:mm a");
        this.targetTime = LocalTime.parse(targetTimeString, formatter12Hour);
    }

    // Start displaying the remaining time
    public void startRealTimeRemainingDisplay() {
        // Create a Swing Timer that triggers every second (1000 ms)
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get remaining time
                String remainingTime = getRemainingTime();

                // Check if the target time has been reached
                if (remainingTime.equals("Time has already passed!")) {
                    label.setText("Time's Up"); // Output
                    stopTimer(); // Stop the timer
                } else {
                    label.setText(remainingTime);
                }
            }
        });

        // Start the timer
        timer.start();
    }

    // Stop the timer
    public void stopTimer() {
        if (timer != null) {
            timer.stop(); // Stop the timer
        }
    }

    // Get the remaining time as a string
    private String getRemainingTime() {
        // Get the current time
        LocalTime currentTime = LocalTime.now();

        // Calculate the duration between the two times
        Duration duration = Duration.between(currentTime, targetTime);

        // Check if the target time is in the past
        if (duration.isNegative()) {
            return "Time has already passed!";
        }

        // Extract hours, minutes, and seconds
        long hours = duration.toHours(); // For Hour
        long minutes = duration.toMinutes() % 60; // For Minutes
        long seconds = duration.getSeconds() % 60; // For Seconds

        // Return formatted remaining time
        //return String.format("%02d hours, %02d minutes, %02d seconds", hours, minutes, seconds);
        return String.format("%02d minutes, %02d seconds", minutes, seconds);   
    }
}
