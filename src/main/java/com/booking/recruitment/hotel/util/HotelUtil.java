package com.booking.recruitment.hotel.util;

import com.booking.recruitment.hotel.model.Hotel;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import static com.booking.recruitment.hotel.constant.Constants.THREE_CLOSEST;

public class HotelUtil {

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

    public static PriorityQueue<Map.Entry<Hotel, Double>> sortByClosest(HashMap<Hotel, Double> hotelMap) {
        PriorityQueue pq = new PriorityQueue(new Comparator<Map.Entry<Hotel, Double>>() {
            @Override
            public int compare(Map.Entry<Hotel, Double> a, Map.Entry<Hotel, Double> b) {
                return -a.getValue().compareTo(b.getValue());
            }
        });
        for (Map.Entry<Hotel, Double> entry : hotelMap.entrySet()) {
            pq.add(entry);
            if (pq.size() > THREE_CLOSEST)
                pq.poll();
        }
        return pq;
    }

}
