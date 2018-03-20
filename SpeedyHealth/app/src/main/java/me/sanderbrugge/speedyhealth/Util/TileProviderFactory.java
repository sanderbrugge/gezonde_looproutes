package me.sanderbrugge.speedyhealth.Util;

import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

public class TileProviderFactory {
    public static WMSTileProvider getOsgeoWmsTileProvider() {
        /*final String START_PART = "http://geo.irceline.be/wms?service=WMS&request=GetMap&layers=rioifdm%3Apm10_anmean_2016_ospm_vl&bbox=";
        final String WMS_FORMAT_STRING = "%f,%f,%f,%f&width=256&height=256&srs=EPSG:4326&format=image/png";*/

        final String START_PART = "http://sedac.ciesin.columbia.edu/geoserver/wms?service=WMS&version=1.1.1&request=GetMap&layers=gpw-v3-population-density_2000&bbox=";
        final String WMS_FORMAT_STRING = "%f,%f,%f,%f&width=256&height=256&srs=EPSG:900913&format=image/png&transparent=true";
        //http://geo.irceline.be/wms?service=WMS&request=GetMap&layers=rioifdm%3Apm10_anmean_2016_ospm_vl&bbox=3.484726,50.970319,3.979797,51.119472&width=800&height=600&srs=EPSG:4326&format=image/png

        WMSTileProvider tileProvider = new WMSTileProvider(256,256) {

            @Override
            public synchronized URL getTileUrl(int x, int y, int zoom) {
                double[] bbox = getBoundingBox(x, y, zoom);
                String s = String.format(Locale.US, WMS_FORMAT_STRING, bbox[MINX],
                        bbox[MINY], bbox[MAXX], bbox[MAXY]);
                Log.i("wmsdemo","coordinates: " + bbox[MINX] + " " + bbox[MINY] + " " + bbox[MAXX]+ " " + bbox[MAXY]);
                String completeURL = START_PART.concat(s);
                Log.d("WMSDEMO", completeURL);
                URL url = null;
                try {
                    url = new URL(completeURL );
                } catch (MalformedURLException e) {
                    throw new AssertionError(e);
                }
                return url;
            }
        };
        return tileProvider;
    }
}
