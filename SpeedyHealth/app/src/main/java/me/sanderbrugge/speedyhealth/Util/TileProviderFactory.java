package me.sanderbrugge.speedyhealth.Util;

import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

public class TileProviderFactory {
    public static WMSTileProvider getOsgeoWmsTileProvider() {


        //This is configured for:
        // http://beta.sedac.ciesin.columbia.edu/maps/services
        // (TODO check that this WMS service still exists at the time you try to run this demo,
        // if it doesn't, find another one that supports EPSG:900913
        final String WMS_FORMAT_STRING =
                "http://sedac.ciesin.columbia.edu/geoserver/wms?service=WMS&version=1.1.1&request=GetMap&layers=gpw-v3-population-density_2000&bbox=%f,%f,%f,%f&width=256&height=256&srs=EPSG:900913&format=image/png&transparent=true";

        //http://sedac.ciesin.columbia.edu/geoserver/wms?service=WMS&version=1.1.1&request=GetMap&layers=gpw-v3-population-density_2000&bbox=%f,%f,%f,%f&width=256&height=256&srs=EPSG:900913&format=image/png&transparent=true
        //http://geo.irceline.be/wms?service=WMS&request=GetMap&layers=rioifdm%3Apm10_anmean_2016_ospm_vl&bbox=3.484726,50.970319,3.979797,51.119472&width=800&height=600&srs=EPSG:4326&format=image/png

        WMSTileProvider tileProvider = new WMSTileProvider(256,256) {

            @Override
            public synchronized URL getTileUrl(int x, int y, int zoom) {
                double[] bbox = getBoundingBox(x, y, zoom);
                String s = String.format(Locale.US, WMS_FORMAT_STRING, bbox[MINX],
                        bbox[MINY], bbox[MAXX], bbox[MAXY]);
                Log.d("WMSDEMO", s);
                URL url = null;
                try {
                    url = new URL(s);
                } catch (MalformedURLException e) {
                    throw new AssertionError(e);
                }
                return url;
            }
        };
        return tileProvider;
    }
}