package sk.tuke.smart.makac.helpers;

import java.util.List;

public final class SportActivities {

    private static final int RUNNING = 0;
    private static final int WALKING = 1;
    private static final int CYCLING = 2;
    private static final float RUNNING_MULTIPLIER = 1.535353535f;
    private static final float WALKING_MULTIPLIER = 1.14f;
    private static final float CYCLING_MULTIPLIER = 0.744444444f;

    /**
     * Returns MET value for an activity.
     *
     * @param activityType - sport activity type (0 - running, 1 - walking, 2 - cycling)
     * @param speed        - speed in m/s
     * @return
     */
    public static double getMET(int activityType, Float speed) {
        int mph = (int) Math.round(MainHelper.mpsToMiph(speed));

        switch (activityType) {
            case RUNNING:
                return Math.ceil(getRunningMET(mph));
            case WALKING:
                return Math.ceil(getWalkingMET(mph));
            case CYCLING:
                return Math.ceil(getCyclingMET(mph));
            default:
                return -1;
        }
    }

    /**
     * Returns final calories computed from the data provided (returns value in kcal)
     *
     * @param sportActivity               - sport activity type (0 - running, 1 - walking, 2 - cycling)
     * @param weight                      - weight in kg
     * @param speedList                   - list of all speed values recorded (unit = m/s)
     * @param timeFillingSpeedListInHours - time of collecting speed list (duration of sport activity from first to last speedPoint in speedList)
     * @return
     */
    public static double countCalories(int sportActivity, float weight, List<Float> speedList, double timeFillingSpeedListInHours) {
        Double met = getMET(sportActivity, calculateAvgSpeed(speedList));
        return met > 0 ? met * weight * timeFillingSpeedListInHours : -1;
    }

    private static double getCalculatedMET(int activityType, int mph) {
        switch (activityType) {
            case RUNNING:
                return mph * RUNNING_MULTIPLIER;
            case WALKING:
                return mph * WALKING_MULTIPLIER;
            case CYCLING:
                return mph * CYCLING_MULTIPLIER;
            default:
                return -1;
        }
    }

    private static double getRunningMET(int mph) {
        switch (mph) {
            case 4:
                return 6.0;
            case 5:
                return 8.3;
//                    case 5:
//                    case 5:
//                    case 5:
//                    case 5:
//                    case 5:
//                    case 5:
//                    case 5:
//                    case 5:
//                    case 5:
            default:
                return getCalculatedMET(RUNNING, mph);
        }
    }

    private static double getWalkingMET(int mph) {
        switch (mph) {
            case 1:
                return 2.0;
            case 2:
                return 2.8;
            case 3:
                return 3.1;
            case 4:
                return 3.5;
            default:
                return getCalculatedMET(WALKING, mph);
        }
    }

    private static double getCyclingMET(int mph) {
        switch (mph) {
            case 10:
                return 6.8;
//            case 11:
//            case 12:
//            case 14:
//            case 16:
//            case 18:
//            case 20:
            default:
                return getCalculatedMET(CYCLING, mph);
        }
    }

    private static float calculateAvgSpeed(List<Float> speedList) {
        Float sum = 0f;
        if (!speedList.isEmpty()) {
            for (Float speed : speedList) {
                sum += speed;
            }
            return sum / speedList.size();
        } else {
            return -1f;
        }
    }
}
