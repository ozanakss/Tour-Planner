package sample;

public class Model {


    private String date;
    private String duration;
    private String distance;

    public Model(String date, String duration, String distance) {

        this.date = date;
        this.duration = duration;
        this.distance = distance;
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

    @Override
    public String toString() {
        return "Log{" +
                "date='" + date + '\'' +
                ", duration='" + duration + '\'' +
                ", distance='" + distance + '\'' +
                '}';
    }
}
