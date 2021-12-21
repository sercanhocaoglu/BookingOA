package com.booking.recruitment.hotel.controller;

import com.booking.recruitment.hotel.exception.BadRequestException;
import com.booking.recruitment.hotel.exception.ElementNotFoundException;
import com.booking.recruitment.hotel.model.Hotel;
import com.booking.recruitment.hotel.service.SearchHotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

import static com.booking.recruitment.hotel.constant.Constants.DIST;
import static org.springframework.util.CollectionUtils.isEmpty;

@RestController
@RequestMapping("/search")
public class SearchController {

    private final SearchHotelService searchHotelService;

    @Autowired
    public SearchController(SearchHotelService searchHotelService) {
        this.searchHotelService = searchHotelService;
    }

    @GetMapping(value = "/hotel/city/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Hotel> findThreeHotelsClosest(@PathVariable Long id,
                                              @RequestParam(required = false) @NotNull String sortBy) {
        if (!DIST.equals(sortBy)) {
            throw new BadRequestException("Given param sortBy's value must be 'distance'");
        }
       return searchHotelService.findThreeHotelsClosest(id);
    }

}
