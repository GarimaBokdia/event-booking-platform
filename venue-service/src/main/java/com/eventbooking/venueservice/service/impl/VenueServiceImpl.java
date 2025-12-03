package com.eventbooking.venueservice.service.impl;

import com.eventbooking.venueservice.dto.*;
import com.eventbooking.venueservice.entity.Venue;
import com.eventbooking.venueservice.exception.VenueNotFoundException;
import com.eventbooking.venueservice.mapper.VenueMapper;
import com.eventbooking.venueservice.repository.VenueRepository;
import com.eventbooking.venueservice.service.VenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VenueServiceImpl implements VenueService {

    private final VenueRepository venueRepository;
    private final VenueMapper venueMapper;

    @Override
    public VenueResponse createVenue(VenueRequest request) {
        Venue venue = venueMapper.toEntity(request);
        Venue savedVenue = venueRepository.save(venue);
        return venueMapper.toResponse(savedVenue);
    }

    @Override
    public List<VenueResponse> getAllVenues() {
        return venueRepository.findAll().stream()
                .map(venueMapper::toResponse)
                .toList();
    }

    @Override
    public VenueResponse getVenueById(Long id) {
        Venue venue = venueRepository.findById(id)
                .orElseThrow(() -> new VenueNotFoundException(id));
        return venueMapper.toResponse(venue);
    }

    @Override
    public VenueResponse updateVenue(Long id, VenueRequest request) {
        Venue existing = venueRepository.findById(id)
                .orElseThrow(() -> new VenueNotFoundException(id));

        existing.setName(request.getName());
        existing.setAddress(request.getAddress());
        existing.setCity(request.getCity());
        existing.setState(request.getState());
        existing.setCountry(request.getCountry());
        existing.setCapacity(request.getCapacity());

        Venue updated = venueRepository.save(existing);
        return venueMapper.toResponse(updated);
    }

    @Override
    public void deleteVenue(Long id) {
        if (!venueRepository.existsById(id)) {
            throw new VenueNotFoundException( id);
        }
        venueRepository.deleteById(id);
    }
}
