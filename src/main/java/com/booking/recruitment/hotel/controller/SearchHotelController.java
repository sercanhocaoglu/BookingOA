package com.booking.recruitment.hotel.controller;

import com.booking.recruitment.hotel.exception.BadRequestException;
import com.booking.recruitment.hotel.exception.ElementNotFoundException;
import com.booking.recruitment.hotel.model.Hotel;
import com.booking.recruitment.hotel.service.SearchHotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.booking.recruitment.hotel.constant.Constants.DIST;
import static org.springframework.util.CollectionUtils.isEmpty;

@RestController
@RequestMapping("/search")
public class SearchHotelController {

    private final SearchHotelService searchHotelService;

    @Autowired
    public SearchHotelController(SearchHotelService searchHotelService) {
        this.searchHotelService = searchHotelService;
    }

    @GetMapping(value = "/{cityId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Hotel> findThreeHotelsClosest(@PathVariable Long cityId,
                                              @RequestParam(required = false) String sortBy) {
        if (DIST.equals(sortBy)) {
            List<Hotel> hotels = searchHotelService.findThreeHotelsClosest(cityId);
            if (isEmpty(hotels)) {
                throw new ElementNotFoundException("There is no convenient hotels!");
            }
            return hotels;
        } else {
            throw new BadRequestException("The sortBy parameter must be " + DIST);
        }
    }

}
