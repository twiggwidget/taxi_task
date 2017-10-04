/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
//---------------
 var map;
 var myTimer;
 var today;
 var urlPort;
 moment().format();
//---------------
var app = {
    // Application Constructor
    initialize: function() 
    {
        document.addEventListener('deviceready', this.onDeviceReady.bind(this), false);
    },

    // deviceready Event Handler
    //
    // Bind any cordova events here. Common events are:
    // 'pause', 'resume', etc.
    onDeviceReady: function() 
    {
        this.receivedEvent('deviceready');
		onLoadFunc();//device is ready, so init...
		document.getElementById("getCurrPosition").addEventListener("click", getCurrPosition);
//		document.getElementById("watchPosition").addEventListener("click", watchPosition);
        document.addEventListener("backbutton", this.onBackKeyDown, false);
		var div = document.getElementById("map_canvas");

		// Initialize the map view
		map = plugin.google.maps.Map.getMap(div);

		// Wait until the map is ready status.
		map.addEventListener(plugin.google.maps.event.MAP_READY, app.onMapReady);
    },

    // Handle the back button
    onBackKeyDown: function() 
    {
 		map.clear();
  		map.off();

  		if (navigator.app) 
  		{
		    navigator.app.exitApp();
		} else if (navigator.device) 
		{
		    navigator.device.exitApp();
		} else 
		{
		    window.close();
        }
    },

	onMapReady: function ()
	{
		console.log("onMapReady:");
        app.receivedEvent('mapready');
 	 	var button1 = document.getElementById("map-button1");
 	 	var button2 = document.getElementById("map-button2");
 		button1.addEventListener("click", app.onButtonClick1);
 		button2.addEventListener("click", app.onButtonClick2);
    	map.getMyLocation(function(location) 
    	{
        // Move camera to the marker created
    		map.animateCamera(
    		{
        		'target': location.latLng,
        		'zoom': 15,
        		'bearing': 140
    		});
   		 });
	},

    // Update DOM on a Received Event
    receivedEvent: function(id) 
    {
        var parentElement = document.getElementById(id);
        var listeningElement = parentElement.querySelector('.listening');
        var receivedElement = parentElement.querySelector('.received');

        listeningElement.setAttribute('style', 'display:none;');
        receivedElement.setAttribute('style', 'display:block;');

        console.log('Received Event: ' + id);
    },
    
//john
	onButtonClick1: function()
	{
		// Move to the position with animation
		map.animateCamera
		({
			target: {lat: 51.5769603, lng: -0.0617729},
			zoom: 11,
			tilt: 60,
			bearing: 140,
			duration: 5000
		},	function()
			{
				// Add a maker
				map.addMarker
				({
					position: {lat: 51.5769603, lng: -0.0617729},
					title: "John\n" ,
					snippet: "Have a nice day !",
					animation: plugin.google.maps.Animation.BOUNCE
				},	function(marker) 
					{
						// Show the info window
						marker.showInfoWindow();

						// Catch the click event
						marker.on(plugin.google.maps.event.INFO_CLICK, function()
						{
							// To do something...
							alert("North London");
						});//marker.on
				});//addMarker
			});//animateCamera
	},//onButtonClick1

//Gabi 51.52041800413905%2C-0.07519049999996241
	onButtonClick2: function()
	{
		// Move to the position with animation
		map.animateCamera
		({
			target: {lat: 51.52041800413905, lng: -0.07519049999996241},
			zoom: 11,
			tilt: 60,
			bearing: 140,
			duration: 5000
		},	function()
			{
				// Add a maker
				map.addMarker
				({
					position: {lat: 51.52041800413905, lng: -0.07519049999996241},
					title: "Gabi\n" ,
					snippet: "Have a nice day !",
					animation: plugin.google.maps.Animation.BOUNCE
				},	function(marker) 
					{
						// Show the info window
						marker.showInfoWindow();

						// Catch the click event
						marker.on(plugin.google.maps.event.INFO_CLICK, function()
						{
							// To do something...
							alert("Central London");
						});//marker.on
				});//addMarker
			});//animateCamera
	},//onButtonClick2

//	getMap: function()
//	{
//		return map;//app.map
//	},

	animateCamera: function(target, zoom, tilt, bearing, duration)
	{
		
	},

	addMarker: function(position, title, snippet, animation)
	{
		
	}
//---------------
};//app
//---------------
app.initialize();
//---------------