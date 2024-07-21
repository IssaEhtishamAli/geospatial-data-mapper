package Geospatial.Locator.Controllers;

import Geospatial.Locator.Models.Generic.mGeneric;
import Geospatial.Locator.Models.Places;
import Geospatial.Locator.Models.PolygonRequest;
import Geospatial.Locator.Services.IPlacesService;
import org.springframework.data.geo.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/places/")
public class PlacesController {
    @Autowired
    private IPlacesService placesService;

    @RequestMapping(value = "findPlaces",method = RequestMethod.POST)
    public ResponseEntity<?> findPlacesNear(@RequestParam(value = "latitude") double latitude, @RequestParam(value = "longitude") double longitude, @RequestParam(value = "maxDistanceInMeters") double maxDistanceInMeters){
        try {
            List<Places> response = placesService.findPlacesNear(latitude,longitude,maxDistanceInMeters);
            System.out.println("<----Response---->"+response);
            if(!response.equals(null)){
                return ResponseEntity.ok(new mGeneric.mAPIResponse(1,"Location are nearest point",response));
            }
            else{
                return ResponseEntity.ok(new mGeneric.mAPIResponse(0,"Invalid are not nearest point",null));
            }
        }catch (Exception ex){
            throw ex;
        }
    }
    @RequestMapping(value = "findAllPlaces",method = RequestMethod.GET)
    public ResponseEntity<?> findAllPlacesNear(){
        try {
            List<Places> response =placesService.getAllPlaces();
            System.out.println("<----Response---->"+response);
            if(!response.equals(null)){
                return ResponseEntity.ok(new mGeneric.mAPIResponse(1,"Reterive All locations",response));
            }
            else{
                return ResponseEntity.ok(new mGeneric.mAPIResponse(0,"Invalid locations",null));
            }
        }
        catch (Exception ex){
        throw ex;
        }
    }
    @RequestMapping(value = "getGeowithin",method = RequestMethod.POST)
    public ResponseEntity<?> getGeoWithIn(@RequestBody PolygonRequest request){
        try {
            List<Point> polygonPoints = request.getPolygonpoints().stream()
                    .map(p -> new Point(p.getLongitude(), p.getLatitude()))
                    .collect(Collectors.toList());
            System.out.println("<----Polygonpoints------>"+polygonPoints);
            List<Places> response = placesService.getPlacesWithinBox(polygonPoints);
            System.out.println("<----Response---->" + response);
            HashMap<String, Object> res = new HashMap<>();
            res.put("response",response);
            res.put("size",response.size());
            if (response != null && !response.isEmpty()) {
                return ResponseEntity.ok(new mGeneric.mAPIResponse(1, "Retrieve All locations", res));
            } else {
                return ResponseEntity.ok(new mGeneric.mAPIResponse(0, "Invalid locations", null));
            }
        } catch (Exception ex) {
            throw new RuntimeException("Error fetching places within polygon", ex);
        }
    }
}
