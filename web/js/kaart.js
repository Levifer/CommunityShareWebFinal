
      var map;
	 // Map maken. 
	function initialize() {
	var myLatlng = new google.maps.LatLng(50.85034,4.35171);
	var mapOptions = {
		zoom: 15,
		center: myLatlng,
		disableDefaultUI: true,
		mapTypeId: google.maps.MapTypeId.ROADMAP
	}
	map = new google.maps.Map(document.getElementById("map-canvas"), mapOptions);
	//event trigger voor de markers te plaatsen.
	google.maps.event.addListener(map, 'click', function(event) 
	{
    placeMarker(event.latLng);
	var NewMarker = new google.maps.latLng(position.coords.latitude,position.coords.longitude)
    window.alert(NewMarker)});
	//geolocatie
	if(navigator.geolocation) {
          navigator.geolocation.getCurrentPosition(function(position) {
            var pos = new google.maps.LatLng(position.coords.latitude,
                                             position.coords.longitude);
			// coordinaten van huidige locatie nodig 
			// voor melden van gevaar 
			//window.alert(pos);
             var locatie = new google.maps.Marker({
			position: pos,
			map: map,
         
			});
			// Cirkel voor de radius van events te late zien. 
            circle = new google.maps.Circle({
				map:map,
				clickable:false,
				radius: 500,
				fillcolor:'#00FFFF',
				fillOpacity: .0,
				strokeColor: '#00FFFF',
				strokeOpacity: .0,
				strokeWeight: .8
			});
			circle.bindTo('center',locatie,'position');
            map.setCenter(pos);
          }, function() {
            handleNoGeolocation(true);
          });
        } else {
          // Browser ondersteunt geen geolocatie
          handleNoGeolocation(false);
        }
      }

      function handleNoGeolocation(errorFlag) {
        if (errorFlag) {
          var content = 'Fout: De Geolocation service is mislukt.';
        } else {
          var content = 'Fout: Je browser ondersteunt geen geolocatie';
        }

        var options = {
          map: map,
          position: new google.maps.LatLng(60, 105),
          content: content
        };

        var infowindow = new google.maps.InfoWindow(options);
        map.setCenter(options.position);
      }
	  //functie voor markers op de map te zetten.
	function placeMarker(location) {
  var marker = new google.maps.Marker({
      position: location,
      map: map,
	  draggable:true
  });
  //map.setCenter(location);
}
google.maps.event.addDomListener(window, 'load', initialize);
