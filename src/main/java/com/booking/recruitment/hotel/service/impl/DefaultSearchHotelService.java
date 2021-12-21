package com.booking.recruitment.hotel.service.impl;

import com.booking.recruitment.hotel.exception.ElementNotFoundException;
import com.booking.recruitment.hotel.model.Hotel;
import com.booking.recruitment.hotel.service.HotelService;
import com.booking.recruitment.hotel.service.SearchHotelService;
import com.booking.recruitment.hotel.util.HotelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.booking.recruitment.hotel.constant.Constants.THREE_CLOSEST;
import static com.booking.recruitment.hotel.util.HotelUtil.calculateDist;
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
        if (isEmpty(hotelList)) {
            throw new ElementNotFoundException("No convenient hotel has been found");
        }
        HashMap<Hotel, Double> hotelMap = new HashMap<>();
        for(Hotel hotel: hotelList){
            if(hotel.isDeleted())
                continue;
            hotelMap.put(hotel, calculateDist(hotel.getLatitude(), hotel.getLongitude(),
                    hotel.getCity().getCityCentreLatitude(), hotel.getCity().getCityCentreLongitude()));
        }
        PriorityQueue<Map.Entry<Hotel, Double>> sortedHotels = HotelUtil.sortByClosest(hotelMap);
        return sortedHotels.stream().map(Map.Entry::getKey).collect(Collectors.toList());
    }

}
