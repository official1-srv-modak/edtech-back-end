package com.modakdev.modakflix_test;

public class Test {
    String title;
    Long duration;

    public Test(String title) {
        this.title = title;
        this.duration = 360L;
    }

    public Test() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Test{" +
                "title='" + title + '\'' +
                ", duration=" + duration +
                '}';
    }
}
