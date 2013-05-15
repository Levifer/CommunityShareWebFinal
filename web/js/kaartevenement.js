var geocoder;
var map;
var marker;
var infoWindow = new google.maps.InfoWindow();
var content = "<p>Uw gekozen locatie.</p>";
var url = "http://localhost:8080/communityshare/resources";
var groep = {};
var longitude;
var latitude;
var persoonNr = readCookie('persoonNr');

function initialize()
{

    var latlng = new google.maps.LatLng(50.9363646, 4.042521699999952);
    var options = {
        zoom: 15,
        center: latlng,
        disableDefaultUI: true,
        zoomControl: true,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };

    map = new google.maps.Map(document.getElementById("map-canvas"), options);

    geocoder = new google.maps.Geocoder();

    marker = new google.maps.Marker(
            {
                map: map,
                draggable: true,
                icon: "images/evenement.png"
            });

    marker.info = new google.maps.InfoWindow({content: content});
    google.maps.event.addListener(marker, "click", function(e) {
        marker.info.open(map, marker);
    });
}

$(document).ready(function() {

    initialize();
    button();

    $(function() {
        $("#address").autocomplete({
            source: function(request, response) {
                geocoder.geocode({'address': request.term + ',BE'}, function(results, status) {
                    response($.map(results, function(item) {
                        return {
                            label: item.formatted_address,
                            value: item.formatted_address,
                            latitude: item.geometry.location.lat(),
                            longitude: item.geometry.location.lng()
                        };
                    }));
                });
            },
            select: function(event, ui) {
                latitude = ui.item.latitude;
                longitude = ui.item.longitude;

                var location = new google.maps.LatLng(ui.item.latitude, ui.item.longitude);
                marker.setPosition(location);
                map.setCenter(location);
            }
        });
    });

    google.maps.event.addListener(marker, 'drag', function() {
        geocoder.geocode({'latLng': marker.getPosition()}, function(results, status) {
            if (status === google.maps.GeocoderStatus.OK) {
                if (results[0]) {
                    $('#address').val(results[0].formatted_address);
                    latitude = (marker.getPosition().lat());
                    longitude = (marker.getPosition().lng());
                }
            }
        });
    });

});


function toevoegenevent()
{
    var adres = jQuery.trim($("#address").val());
    var arrykrap = adres.split(",", 2);
    var straatnaam = arrykrap[0];
    var gemeente = arrykrap[1];
    var gemeente = gemeente.trim();


    groep.persoonNr = persoonNr;
    groep.fotoNr = 12;
    groep.teller = 0;
    groep.straatNaam = straatnaam;
    groep.gemeente = gemeente;
    groep.omschrijving = jQuery.trim($("#regularTextarea").val());
    groep.datum = jQuery.trim($("#datum1").val());
    groep.eventNr = 0;
    groep.longitude = longitude;
    groep.latitude = latitude;
    groep.categorie = "Evenement";

    if (groep.omschrijving === "")
    {
        groep.omschrijving = "Feest: Er is een feest in de straat.";
    }

    var request = new XMLHttpRequest();
    request.open("POST", url + "/event");

    request.onload = function() {
        if (request.status === 204)
        {
            console.log(request.responseText);
            window.location.href = "kaart.html";
        }
    };
    request.setRequestHeader("Content-Type", "application/json");
    request.send(JSON.stringify(groep));

}
;
function button()
{


    $("#eventtoevoegen").click(function()
    {

        toevoegenevent();



    });
}

function readCookie(name)
{
    var nameEQ = name + "=";
    var ca = document.cookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) === ' ')
            c = c.substring(1, c.length);
        if (c.indexOf(nameEQ) === 0)
            return c.substring(nameEQ.length, c.length);
    }
    return null;
}


