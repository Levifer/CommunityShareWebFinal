var geocoder;
var map;
var marker;
var lat;
var lng;
var pos;
var url = "http://localhost:8080/communityshare/resources";
var groep = {};
var persoonnr = readCookie('persoonNr');
function initialize() {
    //MAP
    var latlng = new google.maps.LatLng(50.86896, 4.04198);
    var options = {
        zoom: 15,
        center: latlng,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };

    map = new google.maps.Map(document.getElementById("map-canvas"), options);

    if (navigator.geolocation)
    {
        navigator.geolocation.getCurrentPosition(function(position)
        {
            var pos = new google.maps.LatLng(position.coords.latitude,
                    position.coords.longitude);
            lat = pos.lat();
            lng = pos.lng();

            marker = new google.maps.Marker({
                position: pos,
                map: map,
                draggable: true
            });
            codeLatLng(lat, lng);
            map.setCenter(pos);
            marker.setDraggable(true);
            google.maps.event.addListener(marker, 'drag', function() {
                geocoder.geocode({
                    'latLng': marker.getPosition()
                }, function(results, status) {
                    if (status === google.maps.GeocoderStatus.OK)
                    {
                        if (results[0]) {
                            document.getElementById("address").value = (results[0].formatted_address);
                            lat = marker.getPosition().lat();
                            lng = marker.getPosition().lng();

                        }
                    }
                });
            });

        }, function() {
            handleNoGeolocation(true);
        });
    } else {

        handleNoGeolocation(false);
    }

    geocoder = new google.maps.Geocoder();

    marker = new google.maps.Marker(
            {
                map: map,
                draggable: true
            });

}

$(document).ready(function() {

    initialize();
    button();

    $(function() {
        $("#address").autocomplete({
            source: function(request, response) {
                geocoder.geocode({
                    'address': request.term + ',BE'
                }, function(results, status) {
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
                lat = (ui.item.latitude);
                lng = (ui.item.longitude);

                var location = new google.maps.LatLng(ui.item.latitude, ui.item.longitude);
                marker.setPosition(location);
                map.setCenter(location);
            }
        });
    });

    google.maps.event.addListener(marker, 'drag', function() {
        geocoder.geocode({
            'latLng': marker.getPosition()
        }, function(results, status) {
            if (status === google.maps.GeocoderStatus.OK) {
                if (results[0]) {
                    $('#address').val(results[0].formatted_address);
                    $('#latitude').val(marker.getPosition().lat());
                    $('#longitude').val(marker.getPosition().lng());
                }
            }
        });
    });

});
function codeLatLng(lat, lng)
{
    geocoder = new google.maps.Geocoder;
    var latlng = new google.maps.LatLng(lat, lng);
    geocoder.geocode({
        'latLng': latlng
    }, function(results, status)

    {
        if (status === google.maps.GeocoderStatus.OK)
        {
            var adres = results[0].formatted_address;


            document.getElementById("address").value = adres;

        }
        else
        {
            alert('Geocoder failed due to: ' + status);
        }
    });
}


function toevoegensituatie()
{
    var adres = jQuery.trim($("#address").val());
    var arrykrap = adres.split(",", 2);
    var straatnaam = arrykrap[0];
    var gemeente = arrykrap[1];
    var gemeente = gemeente.trim();

    var datumvandaag = new Date();
    var datum = datumvandaag.getFullYear() + "-" + (datumvandaag.getMonth() + 1) + "-" + datumvandaag.getDate();



    groep.persoonNr = persoonnr;
    groep.fotoNr = 12;
    groep.teller = 0;
    groep.straatNaam = straatnaam;
    groep.gemeente = gemeente;
    groep.omschrijving = jQuery.trim($("#regularTextarea").val());
    groep.datum = datum;
    groep.gevaarNr = 0;
    groep.longitude = lng;
    groep.latitude = lat;
    groep.categorie = jQuery.trim($("#categorie").val());


    if (groep.categorie === "Gevaar" && groep.omschrijving === "")
    {
        groep.omschrijving = "Opgelet!! Er is een gevaarlijke situatie gesignaleerd op deze locatie.";

    }
    else if (groep.categorie === "Zwerfvuil" && groep.omschrijving === "")
    {
        groep.omschrijving = "Zwerfvuil: Er is zwerfvuil gevonden op deze locatie.";
    }
    else if (groep.categorie === "Wegdek Schade" && groep.omschrijving === "")
    {
        groep.omschrijving = "Opgepast!! Er is wegdek schade gesignaleerd op deze baan";
    }
    else
    {

    }

    var request = new XMLHttpRequest();
    request.open("POST", url + "/gevaarveld");

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


    $("#gevaartoevoegen").click(function()
    {

        toevoegensituatie();

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