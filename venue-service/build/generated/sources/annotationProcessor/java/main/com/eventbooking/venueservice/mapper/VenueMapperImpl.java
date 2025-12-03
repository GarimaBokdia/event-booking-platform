package com.eventbooking.venueservice.mapper;

import com.eventbooking.venueservice.dto.VenueRequest;
import com.eventbooking.venueservice.dto.VenueResponse;
import com.eventbooking.venueservice.entity.Venue;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-06T15:08:16+0530",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.14.3.jar, environment: Java 17.0.16 (Homebrew)"
)
@Component
public class VenueMapperImpl implements VenueMapper {

    @Override
    public Venue toEntity(VenueRequest dto) {
        if ( dto == null ) {
            return null;
        }

        Venue.VenueBuilder venue = Venue.builder();

        venue.name( dto.getName() );
        venue.address( dto.getAddress() );
        venue.city( dto.getCity() );
        venue.state( dto.getState() );
        venue.country( dto.getCountry() );
        venue.capacity( dto.getCapacity() );

        return venue.build();
    }

    @Override
    public VenueResponse toResponse(Venue venue) {
        if ( venue == null ) {
            return null;
        }

        VenueResponse venueResponse = new VenueResponse();

        venueResponse.setId( venue.getId() );
        venueResponse.setName( venue.getName() );
        venueResponse.setAddress( venue.getAddress() );
        venueResponse.setCity( venue.getCity() );
        venueResponse.setState( venue.getState() );
        venueResponse.setCountry( venue.getCountry() );
        venueResponse.setCapacity( venue.getCapacity() );

        venueResponse.setCreatedAt( venue.getCreatedAt() != null ? venue.getCreatedAt().toEpochMilli() : 0L );
        venueResponse.setUpdatedAt( venue.getUpdatedAt() != null ? venue.getUpdatedAt().toEpochMilli() : 0L );

        return venueResponse;
    }
}
