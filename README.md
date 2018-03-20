# SpeedyHealth

This is an application that was developped during the Apps For Ghent Hackaton (17/03/'18).

The goal was to come up with an idea and convert that in to an application over the time period of 7 hours, the only condition being that we made use of the Open data provided by Ghent.

## Functionality

This app shows the runningroutes in Ghent retrieved from their [open data](https://datatank.stad.gent/4/cultuursportvrijetijd/routeyoulooproutes). This is done by converting Google Polylines and adding them on a Google Map via their API's. 

On top of that there is a TileOverlay that shows the pollution level per street. This way a runner can pick a healthier route!

Due to not being able to fix the pollution WMS in time, the working example makes use of the world [population](http://sedac.ciesin.columbia.edu/geoserver/wms).

## Used libraries

* Butterknife
* OkHttp
* Retrofit
* Google Services

## Acquired skills

Learned to work with WMS and a more in depth understanding of the Google Maps API's.

## Disclaimer

All rights are reserved by their respective owner, I did not develop this to gain anything from it other than knowledge and skills.
