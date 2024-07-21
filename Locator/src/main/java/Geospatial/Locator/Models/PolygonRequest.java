package Geospatial.Locator.Models;

import java.util.List;

public class PolygonRequest {
    private List<PolygonPoint> polygonpoints;

    // Getters and setters

    public List<PolygonPoint> getPolygonpoints() {
        return polygonpoints;
    }

    public void setPolygonpoints(List<PolygonPoint> polygonpoints) {
        this.polygonpoints = polygonpoints;
    }

    public static class PolygonPoint {
        private double longitude;
        private double latitude;

        // Getters and setters

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }
    }
}
