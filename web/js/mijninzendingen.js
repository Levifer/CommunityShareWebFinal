var events = [];
var situaties = [];
var reacties = [];
var teller = 1;
var url = "http://localhost:8080/communityshare/resources";
var persoonNrInlog = readCookie('persoonNr');
window.onload = function()
{
    opzoekeneventpersoon();
    opzoekensituatiepersoon();
    opzoekenreactiespersoon();

};
function opzoekenreactiespersoon()
{
    var persoonnr = persoonNrInlog;

    var request = new XMLHttpRequest();
    request.open("GET", url + "/reactie/reactie/reactie/" + persoonnr);
    request.onload = function() {
        if (request.status === 200) {

            displayreacties(request.responseText);

        }
    };

    request.send(null);

}
function opzoekeneventpersoon()
{
    var persoonnr = persoonNrInlog;

    var request = new XMLHttpRequest();
    request.open("GET", url + "/event/event/" + persoonnr);
    request.onload = function() {
        if (request.status === 200) {

            displayevent(request.responseText);

        }
    };

    request.send(null);

}
function opzoekensituatiepersoon()
{
    var persoonnr = persoonNrInlog;

    var request = new XMLHttpRequest();
    request.open("GET", url + "/gevaarveld/gevaarveld/" + persoonnr);
    request.onload = function() {
        if (request.status === 200) {
            displaysituatie(request.responseText);

        }
    };

    request.send(null);

}
function displayevent(responseText)
{


    var eventDiv = document.getElementById("evenement");

    events = JSON.parse(responseText);

    for (var i = 0; i < events.length; i++)
    {


        var event = events[i];
        var div = document.createElement("div");
        var div2 = document.createElement("div");
        var p = document.createElement("p");
        var button = document.createElement("button");
        var h5 = document.createElement("h5");


        div.setAttribute("class", "two-thirds column");
        div2.setAttribute("class", "one-third column");
        button.setAttribute("class", "one-third column full-width button toevoegen");
        button.setAttribute("type", "button");
        button.setAttribute("value", "Click");




        h5.innerHTML = "</br>" + event.categorie + ": " + event.straatNaam + " " + event.gemeente;
        p.innerHTML = event.omschrijving + "</br>" + event.datum;
        button.innerHTML = "Verwijderen";

        eventDiv.appendChild(div);
        div.appendChild(h5);
        div.appendChild(p);
        eventDiv.appendChild(div2);
        div2.appendChild(button);




        with ({n: i})
        {
            button.onclick = function()
            {
                var event = events[n];
                var eventNr = event.eventNr;
                var persoonNr = persoonNrInlog;

                var request = new XMLHttpRequest();
                request.open("DELETE", url + "/event/" + eventNr + "," + persoonNr);
                request.onload = function()
                {
                    if (request.status === 204) {

                        window.location.href = "mijninzendingen.html";
                    }
                    ;
                };
                request.send(null);
            };
        }



    }
}

function displaysituatie(responseText)
{


    var situatieDiv = document.getElementById("situatie");

    situaties = JSON.parse(responseText);

    for (var s = 0; s < situaties.length; s++)
    {


        var situatie = situaties[s];
        var div = document.createElement("div");
        var div2 = document.createElement("div");
        var p = document.createElement("p");
        var button = document.createElement("button");
        var h5 = document.createElement("h5");



        div.setAttribute("class", "two-thirds column");
        div2.setAttribute("class", "one-third column");
        button.setAttribute("class", "one-third column full-width button toevoegen");
        button.setAttribute("type", "button");
        button.setAttribute("value", "Click");


        h5.innerHTML = "</br>" + situatie.categorie + ": " + situatie.straatNaam + " " + situatie.gemeente + "</br>";
        p.innerHTML = situatie.omschrijving + " </br>" + situatie.datum;
        button.innerHTML = "Verwijderen";

        situatieDiv.appendChild(div);
        div.appendChild(h5);
        div.appendChild(p);
        situatieDiv.appendChild(div2);
        div2.appendChild(button);





        with ({n: s})
        {
            button.onclick = function()
            {

                var gevaar = situaties[n];
                var gevaarNr = gevaar.gevaarNr;

                var persoonNr = persoonNrInlog;

                var request = new XMLHttpRequest();
                request.open("DELETE", url + "/gevaarveld/" + gevaarNr + "," + persoonNr);
                request.onload = function()
                {
                    if (request.status === 204) {

                        window.location.href = "mijninzendingen.html";
                    }
                    ;
                };
                request.send(null);
            };
        }



    }
}
function displayreacties(responseText)
{


    var reactieDIV = document.getElementById("reacties");

    reacties = JSON.parse(responseText);

    for (var t = 0; t < reacties.length; t++)
    {


        var reactie = reacties[t];
        var div = document.createElement("div");
        var div2 = document.createElement("div");
        var p = document.createElement("p");
        var button = document.createElement("button");



        div.setAttribute("class", "two-thirds column");
        div2.setAttribute("class", "one-third column");
        button.setAttribute("class", "one-third column full-width button toevoegen");
        button.setAttribute("type", "button");
        button.setAttribute("value", "Click");




        p.innerHTML = "</br>" + reactie.reactie + "</br>" + reactie.datum;
        button.innerHTML = "Verwijderen";

        reactieDIV.appendChild(div);
        div.appendChild(p);
        reactieDIV.appendChild(div2);
        div2.appendChild(button);




        with ({n: t})
        {
            button.onclick = function()
            {
                var react = reacties[n];
                var reactienr = react.reactieNr;
                var persoonNr = persoonNrInlog;

                var request = new XMLHttpRequest();
                request.open("DELETE", url + "/reactie/" + reactienr + "," + persoonNr);
                request.onload = function()
                {
                    if (request.status === 204) {

                        window.location.href = "mijninzendingen.html";
                    }
                    ;
                };
                request.send(null);
            };
        }



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