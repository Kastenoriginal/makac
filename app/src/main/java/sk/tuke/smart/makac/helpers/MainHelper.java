package sk.tuke.smart.makac.helpers;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

public final class MainHelper {
    private static final float MpS_TO_MIpH = 2.23694f;
    private static final float KM_TO_MI = 0.62137119223734f;
    private static final float MINpKM_TO_MINpMI = 1.609344f;

    /**
     * return string of time in format HH:MM:SS
     * @param time - in seconds
     */
    public static String formatDuration(long time) {
        return String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(time),
                TimeUnit.MILLISECONDS.toMinutes(time) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(time)),
                TimeUnit.MILLISECONDS.toSeconds(time) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time)));

    }

    /**
     * convert m to km and round to 2 decimal places and return as string
     */
    public static String formatDistance(double n) {
        DecimalFormat formatter = new DecimalFormat("#.00");
        return formatter.format(n / 1000);
    }

    /**
     * round number to 2 decimal places and return as string
     */
    public static String formatPace(double n) {
        return String.valueOf(Math.round(n * 100.0) / 100.0);
    }

    /**
     * round number to integer
     */
    public static String formatCalories(double n) {
        return String.valueOf((int) Math.round(n));
    }

    /**
     *  convert km to mi (multiply with corresponding constant)
     */
    public static double kmToMi(double n) {
        return Math.round(n * KM_TO_MI * 100.0) / 100.0;
    }

    /**
     *  convert m/s to mi/h (multiply with corresponding constant)
     */
    public static double mpsToMiph(double n) {
        return Math.round(n * MpS_TO_MIpH * 100.0) / 100.0;
    }

    /**
     *  convert min/km to min/mi (multiply with corresponding constant)
     */
    public static double minpkmToMinpmi(double n) {
        return Math.round(n * MINpKM_TO_MINpMI * 100.0) / 100.0;
    }
}
