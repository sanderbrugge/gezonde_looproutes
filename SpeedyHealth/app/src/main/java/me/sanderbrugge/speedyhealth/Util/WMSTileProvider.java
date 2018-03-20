package me.sanderbrugge.speedyhealth.Util;

import android.util.Log;

import com.google.android.gms.maps.model.UrlTileProvider;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;

public abstract class WMSTileProvider extends UrlTileProvider {
    // Web Mercator n/w corner of the map.
    private static final double[] TILE_ORIGIN = {-20037508.34789244, 20037508.34789244};
    //array indexes for that data
    private static final int ORIG_X = 0;
    private static final int ORIG_Y = 1; // "
    private static final double ORIGIN_SHIFT = Math.PI * 6378137d;

    // Size of square world map in meters, using WebMerc projection.
    private static final double MAP_SIZE = 20037508.34789244 * 2;

    // array indexes for array to hold bounding boxes.
    protected static final int MINX = 0;
    protected static final int MAXX = 1;
    protected static final int MINY = 2;
    protected static final int MAXY = 3;

    /**
     * Transform the y map meter in y cordinate
     *
     * @param latitude the latitude of map
     * @return meters of y cordinate
     */
    private double inMetersYCoordinate(double latitude) {
        if (latitude < 0) {
            return -inMetersYCoordinate(-latitude);
        }
        return (Math.log(Math.tan((90d + latitude) * Math.PI / 360d)) / (Math.PI / 180d)) * ORIGIN_SHIFT / 180d;
    }

    /**
     * Transform the x map meter in x cordinate
     *
     * @param longitude the longitude of map
     * @return meters of x cordinate
     */
    private double inMetersXCoordinate(double longitude) {
        return longitude * ORIGIN_SHIFT / 180.0;
    }
    // cql filters
    private String cqlString = "";

    // Construct with tile size in pixels, normally 256, see parent class.
    public WMSTileProvider(int x, int y) {
        super(x, y);
    }

    protected String getCql() {
        return URLEncoder.encode(cqlString);
    }

    public void setCql(String c) {
        cqlString = c;
    }

    // Return a web Mercator bounding box given tile x/y indexes and a zoom
    // level.
    protected double[] getBoundingBox(int x, int y, int zoom) {
        double newX = inMetersXCoordinate(x);
        double newY = inMetersYCoordinate(y);

        Log.i("WMSTILEPROVIDER", "x = " + x + " y = " + y);

        double tileSize = MAP_SIZE / Math.pow(2, zoom);
        double minx = TILE_ORIGIN[ORIG_X] + x * tileSize;
        double maxx = TILE_ORIGIN[ORIG_X] + (x+1) * tileSize;
        double miny = TILE_ORIGIN[ORIG_Y] - (y+1) * tileSize;
        double maxy = TILE_ORIGIN[ORIG_Y] - y * tileSize;

        double[] bbox = new double[4];
        bbox[MINX] = minx;
        bbox[MINY] = miny;
        bbox[MAXX] = maxx;
        bbox[MAXY] = maxy;

        return bbox;
    }
}