package com.booking.recruitment.hotel.util;

public class Utils {

    public static double calculateDist(double latitudeX, double longitudeX, double latitudeY, double longitudeY) {
        double diffLatitude = Math.toRadians(latitudeY - latitudeX);
        double diffLongitude = Math.toRadians(longitudeY - longitudeX);

        latitudeX = Math.toRadians(latitudeX);
        latitudeY = Math.toRadians(latitudeY);

        double a = Math.pow(Math.sin(diffLatitude / 2), 2) * Math.cos(latitudeX) * Math.cos(latitudeY) + Math.pow(Math.sin(diffLongitude / 2), 2);
        double rad = 6371;
        double c = Math.asin(Math.sqrt(a)) * 2;
        return rad * c;
    }

}
