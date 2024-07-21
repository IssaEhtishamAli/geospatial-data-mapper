package Geospatial.Locator.Services;

import Geospatial.Locator.Models.Places;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.util.List;

public interface IPlacesService {
    public List<Places> getAllPlaces();
    public List<Places> findPlacesNear(double latitude, double longitude, double maxDistanceInMeters);
    public List<Places> getPlacesWithinBox(List<Point> polygonPoints);
}
