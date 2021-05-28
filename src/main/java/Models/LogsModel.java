package Models;

public class LogsModel {


    private String date;
    private String duration;
    private String distance;
    private String name;
    private String description;
    private String total_time;
    private String rating;
    private String maxspeed, minspeed, steps;

    public LogsModel(String date, String duration, String distance, String name, String description, String total_time, String rating, String maxspeed, String minspeed, String steps) {
        this.date = date;
        this.duration = duration;
        this.distance = distance;
        this.name = name;
        this.description = description;
        this.total_time = total_time;
        this.rating = rating;
        this.maxspeed = maxspeed;
        this.minspeed = minspeed;
        this.steps = steps;
    }

    public String getDate() {
        return date;
    }

    public String getDuration() {
        return duration;
    }

    public String getDistance() {
        return distance;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTotal_time() {
        return total_time;
    }

    public void setTotal_time(String total_time) {
        this.total_time = total_time;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getMaxspeed() {
        return maxspeed;
    }

    public void setMaxspeed(String maxspeed) {
        this.maxspeed = maxspeed;
    }

    public String getMinspeed() {
        return minspeed;
    }

    public void setMinspeed(String minspeed) {
        this.minspeed = minspeed;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    //method for printing Log
    @Override
    public String toString() {
        return "Log{" +
                "date='" + date + '\'' +
                ", duration='" + duration + '\'' +
                ", distance='" + distance + '\'' +
                '}';
    }
}
