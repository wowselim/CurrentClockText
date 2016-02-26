package clock;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.scene.text.Text;

/**
 * A Subclass of Text that displays the current system time.
 * 
 * @author <a href="mailto:wowselim@gmail.com">Selim Dincer</a>
 * @version 1.0
 */
public class CurrentClockText extends Text {
    private final DateTimeFormatter formatter;

    /**
     * Show the current hour only for example: <code>18</code>.
     */
    public static final int HOUR = 1;

    /**
     * Show the current hour and minute for example: <code>18:47</code>.
     */
    public static final int HOUR_MINUTE = 2;

    /**
     * Show the current hour, minute and second for example: <code>18:47:16</code>.
     */
    public static final int HOUR_MINUTE_SECOND = 3;

    /**
     * Show the current hour, minute, second and millisecond for example:
     * <code>18:47:16.234</code>.
     */
    public static final int HOUR_MINUTE_SECOND_MILLISECOND = 4;

    private final TimerTask updateTask = new TimerTask() {
        @Override
        public void run() {
            LocalDateTime now = LocalDateTime.now();
            String nowTextual = formatter.format(now);
            Platform.runLater(() -> setText(nowTextual));
        }
    };

    /**
     * Constructs an instance of this class that displays the current hour and
     * minute.
     */
    public CurrentClockText() {
        this(HOUR_MINUTE);
    }

    /**
     * Constructs an instance of this class that display the current time at a
     * desired level of detail
     * 
     * @param detailLevel
     *            the level of detail desired in displaying the time.
     */
    public CurrentClockText(final int detailLevel) {
        String timeFormat = "";
        final long updateInterval;
        switch (detailLevel) {
        case HOUR:
            timeFormat = "HH";
            updateInterval = 60000;
            break;
        case HOUR_MINUTE:
            timeFormat = "HH:mm";
            updateInterval = 15000;
            break;
        case HOUR_MINUTE_SECOND:
            timeFormat = "HH:mm:ss";
            updateInterval = 500;
            break;
        case HOUR_MINUTE_SECOND_MILLISECOND:
            updateInterval = 1;
            timeFormat = "HH:mm:ss.SSS";
            break;
        default:
            throw new IllegalArgumentException(
                    "Unknown detail level for Clock: " + detailLevel);
        }

        formatter = DateTimeFormatter.ofPattern(timeFormat);
        new Timer(true).schedule(updateTask, 0, updateInterval);
    }
}
