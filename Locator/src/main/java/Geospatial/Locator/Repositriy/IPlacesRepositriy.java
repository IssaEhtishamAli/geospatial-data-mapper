package Geospatial.Locator.Repositriy;

import Geospatial.Locator.Models.Places;
import org.springframework.data.geo.Point;

import java.awt.*;
import java.util.List;

public interface IPlacesRepositriy {
    public List<Places> getAllPlaces();
    public List<Places> findPlacesNear(double latitude, double longitude, double maxDistanceInMeters);
    public List<Places> getPlacesWithinBox(List<Point> polygonPoints);
}
