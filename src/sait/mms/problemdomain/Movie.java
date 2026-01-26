package sait.mms.problemdomain;

public class Movie {
    private int duration;
    private String title;
    private int year;

    public Movie(int duration, String title, int year) { //Non default constructor per assignment instruction
        this.duration = duration;
        this.title = title;
        this.year = year;
    }

    public int getDuration() { //Getter methods
        return duration;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public String toString() { //toString method per assignment requirements
        return duration + "," + title + "," + year;
    }
}
