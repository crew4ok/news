package urujdas.web.user.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class GeoLocation {
    @NotNull
    private final BigDecimal latitude;

    @NotNull
    private final BigDecimal longitude;

    @JsonCreator
    public GeoLocation(@JsonProperty("longitude") BigDecimal longitude,
                       @JsonProperty("latitude") BigDecimal latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public urujdas.model.users.GeoLocation toGeoLocation() {
        return urujdas.model.users.GeoLocation.builder()
                .withLatitude(latitude)
                .withLongitude(longitude)
                .build();
    }
}
