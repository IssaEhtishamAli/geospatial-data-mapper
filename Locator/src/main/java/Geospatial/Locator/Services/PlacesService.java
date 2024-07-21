package Geospatial.Locator.Services;

import Geospatial.Locator.Models.Places;
import Geospatial.Locator.Repositriy.IPlacesRepositriy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PlacesService implements IPlacesService{
    @Autowired
    private IPlacesRepositriy placesRepositriy;
    @Override
    public List<Places> findPlacesNear(double latitude, double longitude, double maxDistanceInMeters) {
       return placesRepositriy.findPlacesNear(latitude,longitude,maxDistanceInMeters);
    }
    @Override
    public List<Places> getAllPlaces(){
        return placesRepositriy.getAllPlaces();
    }
    @Override
    public List<Places> getPlacesWithinBox(List<Point> polygonPoints)
    {
        return placesRepositriy.getPlacesWithinBox(polygonPoints);
    }

}
