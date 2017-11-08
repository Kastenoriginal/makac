package sk.tuke.smart.makac.utils;

public final class Constants {

    public static final String COMMAND_START = "sk.tuke.smart.makac.COMMAND_START";
    public static final String COMMAND_STOP = "sk.tuke.smart.makac.COMMAND_STOP";
    public static final String COMMAND_PAUSE = "sk.tuke.smart.makac.COMMAND_PAUSE";
    public static final String COMMAND_CONTINUE = "sk.tuke.smart.makac.COMMAND_CONTINUE";
    public static final String TICK = "sk.tuke.smart.makac.TICK";

    public static final String TAG_ACTIVITY_TYPE = "type";
    public static final String TAG_DURATION = "duration";
    public static final String TAG_DISTANCE = "distance";
    public static final String TAG_PACE = "pace";
    public static final String TAG_CALORIES = "calories";
    public static final String TAG_POSITIONS = "positions";
    public static final String TAG_STATE = "state";

    public static final int RUNNING = 0;
    public static final int WALKING = 1;
    public static final int CYCLING = 2;

    public static final int STATE_STOPPED = 10;
    public static final int STATE_RUNNING = 11;
    public static final int STATE_CONTINUE = 12;

    public static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    public static final long MIN_TIME_BW_UPDATES = 1000 * 3;
}
