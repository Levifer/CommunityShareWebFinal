var map;
var locatie;
var marker;
var pos;
var geocoder;
var infoWindow = new google.maps.InfoWindow();
var evenement = [];
var gevaar = [];
var url = "http://localhost:8080/communityshare/resources";
var data;
var radius;


window.onload = function() {

    geocoder = new google.maps.Geocoder();
    var myLatlng = new google.maps.LatLng(-25.363882, 131.044922);
    var mapOptions = {
        zoom: 15,
        center: myLatlng,
        zoomControl: true,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };

    map = new google.maps.Map(document.getElementById("map-canvas"), mapOptions);

    geolocatie();




};


function geolocatie() {
    if (navigator.geolocation)
    {
        navigator.geolocation.getCurrentPosition(function(position) {
            pos = new google.maps.LatLng(position.coords.latitude,
                    position.coords.longitude);

            var latitudeM = position.coords.latitude;
            var longitudeM = position.coords.longitude;
            opzoekenevent(latitudeM, longitudeM);
            opzoekensituaties(latitudeM, longitudeM);

            locatie = new google.maps.Marker
                    (
                            {
                                position: pos,
                                map: map
                            }
                    );

            map.setCenter(pos);
            google.maps.event.addListener(locatie, 'click', function()
            {
                infoWindow.open(map, locatie);
            });

        });

    }
    else {

        handleNoGeolocation(false);
    }

    var contentInfowindow = "<p>u bent hier</p>";
    var infoWindow = new google.maps.InfoWindow
            (
                    {
                        content: contentInfowindow
                    }
            );



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
}



function opzoekenevent(latitudeM, longitudeM)
{

    standaardwaardeRadius();


    var long = longitudeM;
    var lat = latitudeM;

    var request = new XMLHttpRequest();
    request.open("GET", url + "/event/event/" + radius + "," + long + "," + lat);

    request.onload = function() {
        if (request.status === 200) {

            evenement = JSON.parse(request.responseText);
            markersMakenEvent();


        }
    };

    request.send(null);

}


function opzoekensituaties(latitudeM, longitudeM)
{


    standaardwaardeRadius();


    var long = longitudeM;
    var lat = latitudeM;


    var request = new XMLHttpRequest();
    request.open("GET", url + "/gevaarveld/gevaarveld/" + radius + "," + long + "," + lat);

    request.onload = function() {
        if (request.status === 200) {

            gevaar = JSON.parse(request.responseText);
            markersMakenSituatie();


        }
    };

    request.send(null);

}

function markersMakenEvent()
{

    for (var i = 0, length = evenement.length; i < length; i++) {
        data = evenement[i];


        var test = new google.maps.LatLng(data.latitude, data.longitude);


        var cat = data.categorie;
        if (cat === "Evenement")
        {
            var icon = "images/evenement.png";
        }
        ;

        var marker = new google.maps.Marker({
            position: test,
            map: map,
            title: data.categorie,
            icon: icon

        });

        (function(marker, data)
        {

            includeJS("//connect.facebook.net/en_US/all.js");
            includeJS('js/facebook.js');
            var inhoud = "<div id='Info'> <h5 id='facebookdata'>" + data.categorie + ": " + data.straatNaam + " " + data.gemeente + "</h5></br><p id='fbomschrijving'>" + data.omschrijving + "</p><p id='colorblack'>" + data.datum + "</p></br><a class='button' href='reacties.html'>Reageer</a></br><a class='button' onclick='postToWall()'>Share</a></div>";
            google.maps.event.addListener(marker, "click", function(e) {
                infoWindow.setContent(inhoud);
                createCookie('EventNr', data.eventNr, 1);
                createCookie('GevaarNr', null, 1);
                infoWindow.open(map, marker);
            });


        })

                (marker, data);

    }
}

function markersMakenSituatie()
{

    for (var i = 0, length = gevaar.length; i < length; i++)
    {
        data = gevaar[i];

        var test = new google.maps.LatLng(data.latitude, data.longitude);
        var icon;

        var cat = data.categorie;

        if (cat === "Gevaar")
        {
            icon = "images/gevaar.png";
        }
        else if (cat === "Zwerfvuil")
        {
            icon = "images/zwerfvuil.png";
        }
        else if (cat === "Wegdek Schade")
        {
            icon = "images/straat.png";
        }


        var marker = new google.maps.Marker({
            position: test,
            map: map,
            title: data.categorie,
            icon: icon

        });

        (function(marker, data)
        {


            includeJS("//connect.facebook.net/en_US/all.js");
            includeJS('js/facebook.js');
            var inhoud = "<div id='Info'> <h5 id='facebookdata'>" + data.categorie + ": " + data.straatNaam + " " + data.gemeente + "</h5></br><p id='fbomschrijving'>" + data.omschrijving + "</p><p id='colorblack'>" + data.datum + "</p><a class='button' href='reacties.html'>Reageer</a></br><a class='button' onclick='postToWall()'>Share</a></div>";
            google.maps.event.addListener(marker, "click", function(e) {
                infoWindow.setContent(inhoud);
                createCookie('EventNr', null, 1);
                createCookie('GevaarNr', data.gevaarNr, 1);
                infoWindow.open(map, marker);
            });


        })

                (marker, data);

    }
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

function createCookie(name, value, days) {
    if (days) {
        var date = new Date();
        date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
        var expires = "; expires=" + date.toGMTString();
    }
    else
        var expires = "";
    document.cookie = name + "=" + value + expires + "; path=/";
}
function includeJS(jsPath) {
    var js = document.createElement("script");
    js.setAttribute("type", "text/javascript");
    js.setAttribute("src", jsPath);
    document.getElementsByTagName("head")[0].appendChild(js);
}

function standaardwaardeRadius()
{
    if (readCookie('radius') === null)
    {
        radius = 5;
    }
    else
    {
        radius = readCookie('radius');
    }
}