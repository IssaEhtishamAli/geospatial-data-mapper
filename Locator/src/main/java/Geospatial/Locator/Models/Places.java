package Geospatial.Locator.Models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

@Document(collection = "places")
@Data
@NoArgsConstructor
public class Places {
    @Id
    private String id;
    private String name;
    private String type;
    private GeoJsonPoint location;

    public Places(String id, String name, String type, GeoJsonPoint location) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.location = location;
    }
}
