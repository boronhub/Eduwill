package com.boron.eduwill;

public class Booking {
    public String location, date, timeStart, timeEnd, age, subject;

    public Booking(){

    }

    public Booking(String location, String date, String timeStart,String timeEnd,String age, String subject) {
        this.location = location;
        this.date = date;
        this.timeEnd = timeStart;
        this.timeEnd = timeEnd;
        this.age = age;
        this.subject = subject;
    }
}
