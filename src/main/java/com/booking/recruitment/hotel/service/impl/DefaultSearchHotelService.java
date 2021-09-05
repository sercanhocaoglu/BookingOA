package com.booking.recruitment.hotel.service.impl;

import com.booking.recruitment.hotel.model.Hotel;
import com.booking.recruitment.hotel.service.HotelService;
import com.booking.recruitment.hotel.service.SearchHotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.booking.recruitment.hotel.constant.Constants.THREE_CLOSEST;
import static com.booking.recruitment.hotel.util.Utils.calculateDist;
import static org.springframework.util.CollectionUtils.isEmpty;

@Service
class DefaultSearchHotelService implements SearchHotelService {

    private final HotelService hotelService;

    @Autowired
    DefaultSearchHotelService(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @Override
    public List<Hotel> findThreeHotelsClosest(Long cityId) {
        List<Hotel> hotelList = hotelService.getHotelsByCity(cityId);
        if (!isEmpty(hotelList)) {
            HashMap<Hotel, Double> lookUpHotels = new HashMap<>();
            for (Hotel h : hotelList) {
                double dist = calculateDist(h.getLatitude(), h.getLongitude(),
                        h.getCity().getCityCentreLatitude(), h.getCity().getCityCentreLongitude());
                lookUpHotels.put(h, dist);
            }
            PriorityQueue<Map.Entry<Hotel, Double>> sortedHotels = sortByClosest(lookUpHotels);
            return sortedHotels.stream().map(Map.Entry::getKey).limit(THREE_CLOSEST).collect(Collectors.toList());
        }
        return null;
    }

    private PriorityQueue<Map.Entry<Hotel, Double>> sortByClosest(HashMap<Hotel, Double> hotelMap) {
        PriorityQueue<Map.Entry<Hotel, Double>> pq = new PriorityQueue(new Comparator<Map.Entry<Hotel, Double>>() {
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
