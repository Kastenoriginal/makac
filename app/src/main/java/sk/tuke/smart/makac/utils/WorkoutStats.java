package sk.tuke.smart.makac.utils;

public class WorkoutStats {

    private int duration;
    private int distance;
    private int pace;
    private double calories;

    private WorkoutStats(Builder builder) {
        this.duration = builder.duration;
        this.distance = builder.distance;
        this.pace = builder.pace;
        this.calories = builder.calories;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getPace() {
        return pace;
    }

    public void setPace(int pace) {
        this.pace = pace;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public static class Builder {
        private int duration;
        private int distance;
        private int pace;
        private double calories;

        public Builder duration(int duration) {
            this.duration = duration;
            return this;
        }

        public Builder distance(int distance) {
            this.distance = distance;
            return this;
        }

        public Builder pace(int pace) {
            this.pace = pace;
            return this;
        }

        public Builder calories(double calories) {
            this.calories = calories;
            return this;
        }

        public WorkoutStats build() {
            return new WorkoutStats(this);
        }
    }
}
