package com.booking.recruitment.hotel.service;

import com.booking.recruitment.hotel.model.Hotel;

import java.util.List;

public interface SearchHotelService {

    List<Hotel> findThreeHotelsClosest(Long cityId);

}
