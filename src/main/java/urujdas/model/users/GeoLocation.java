package urujdas.model.users;

import net.karneim.pojobuilder.GeneratePojoBuilder;

import java.math.BigDecimal;

public class GeoLocation {
    private final BigDecimal latitude;
    private final BigDecimal longitude;

    @GeneratePojoBuilder
    public GeoLocation(BigDecimal latitude, BigDecimal longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public static GeoLocationBuilder builder() {
        return new GeoLocationBuilder();
    }
}
