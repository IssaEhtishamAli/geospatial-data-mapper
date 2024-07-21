package Geospatial.Locator.Repositriy;

import Geospatial.Locator.Models.Places;
import com.mongodb.client.model.geojson.Position;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GeoNearOperation;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.core.query.Query;
import org.slf4j.Logger;
import org.springframework.data.geo.Box;
import java.util.List;
import org.springframework.data.mongodb.core.query.NearQuery;


@Repository
public class PlacesRepositriyImpl implements IPlacesRepositriy{
    @Autowired
    private MongoTemplate mongoTemplate;
    private static final Logger logger = LoggerFactory.getLogger(PlacesRepositriyImpl.class);
    private static final double EARTH_RADIUS_IN_METERS = 6378100; // Earth's radius in meters

    @Override
    public List<Places> findPlacesNear(double latitude, double longitude, double maxDistanceInMeters) {
        try {
            // Create a GeoJsonPoint with the provided latitude and longitude
            GeoJsonPoint point = new GeoJsonPoint(longitude, latitude);

            // Convert max distance from meters to radians
            double maxDistanceInRadians = maxDistanceInMeters / EARTH_RADIUS_IN_METERS;
            Query query = new Query(Criteria.where("type").is("Building"));

            // Create the NearQuery object
            NearQuery nearQuery = NearQuery.near(point)
                    .maxDistance(maxDistanceInRadians)
                    .query(query)
                    .spherical(true);

            // Create the GeoNearOperation
            GeoNearOperation geoNearOperation = Aggregation.geoNear(nearQuery, "dist.calculated");

            // Create the aggregation
            Aggregation aggregation = Aggregation.newAggregation(geoNearOperation);

            // Execute the aggregation
            AggregationResults<Places> results = mongoTemplate.aggregate(aggregation, "places", Places.class);

            // Process the results
            for (Places document : results) {
                System.out.println(document.toString());
            }
            // Use maxDistance in radians
            return results.getMappedResults();


        }
        catch (Exception ex){
            throw ex;
        }
    }
    @Override
    public List<Places> getAllPlaces() {
        // Retrieve all documents from the "places" collection
        try {
            List<Places> response =mongoTemplate.findAll(Places.class);
            System.out.println("<----Response---->"+response);
            return response;
        }
        catch (Exception ex){
            throw  ex;
        }
    }
    @Override
    public List<Places> getPlacesWithinBox(List<Point> polygonPoints) {
        try {
            // Convert the list of GeoJsonPoints to a GeoJsonPolygon
            GeoJsonPolygon polygon = new GeoJsonPolygon(polygonPoints);

            // Create the aggregation pipeline
            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(Criteria.where("location").within(polygon))
            );

            // Execute the aggregation
            AggregationResults<Places> results = mongoTemplate.aggregate(aggregation, "places", Places.class);

            // Return the list of places
            return results.getMappedResults();
        } catch (Exception ex) {
            // Handle exceptions as needed
            throw new RuntimeException("Error fetching places within polygon", ex);
        }
    }
}
