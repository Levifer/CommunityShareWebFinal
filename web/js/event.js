var melding = [];
var teller = 1;
var url = "http://localhost:8080/communityshare/resources";
var gemeente;
window.onload = function() {
    if ((readCookie('evenement') === "true") || (readCookie('evenement') === "false"))
    {

    }
    else
    {
        createCookie('evenement', "null", 1);
    }
    checkGemeente();
    if (readCookie('evenement') === "true" || readCookie('evenement') === "null")
    {
        opzoekenevent();


    }
    else if (readCookie('situatie') === "true")
    {
        opzoekensituatie();

    }

    setInterval(handleRefresh, 15000);


};
function opzoekenevent()
{
    checkGemeente();

    var request = new XMLHttpRequest();
    request.open("GET", url + "/event/" + gemeente);
    request.onload = function() {// our request object
        if (request.status === 200) {//return code is "OK" 
            displayke(request.responseText);

        }
    };

    request.send(null);

}
function opzoekensituatie()
{
    checkGemeente();

    var request = new XMLHttpRequest();
    request.open("GET", url + "/gevaarveld/" + gemeente);
    request.onload = function() {
        if (request.status === 200) {
            displayke(request.responseText);

        }
    };

    request.send(null);

}
function displayke(responseText)

{

    var eventDiv = document.getElementById("eerstpositie");
    var eventDiv2 = document.getElementById("tweedepositie");
    var eventDiv3 = document.getElementById("derdepositie");

    ;
    melding = JSON.parse(responseText);


    var event = melding[0];
    var p = document.createElement("p");
    var h5 = document.createElement("h5");
    var p2 = document.createElement("p");

    p.setAttribute("class", "p");
    p2.setAttribute("class", "p");
    h5.setAttribute("class", "h5");
    p.setAttribute('id', "event1");
    p2.setAttribute('id', "event11");
    h5.setAttribute('id', "event111");
    h5.innerHTML = event.categorie + ": " + event.straatNaam + " " + event.gemeente;
    p.innerHTML = event.omschrijving;
    p2.innerHTML = event.datum;
    eventDiv.appendChild(h5);
    eventDiv.appendChild(p);
    eventDiv.appendChild(p2);

    var event = melding[1];
    var p = document.createElement("p");
    var h5 = document.createElement("h5");
    var p2 = document.createElement("p");
    p.setAttribute("class", "p");
    p2.setAttribute("class", "p");
    h5.setAttribute("class", "h5");
    p.setAttribute('id', "event2");
    p2.setAttribute('id', "event22");
    h5.setAttribute('id', "event222");
    h5.innerHTML = event.categorie + ": " + event.straatNaam + " " + event.gemeente;
    p.innerHTML = event.omschrijving;
    p2.innerHTML = event.datum;
    eventDiv2.appendChild(h5);
    eventDiv2.appendChild(p);
    eventDiv2.appendChild(p2);

    var event = melding[2];
    var p = document.createElement("p");
    var h5 = document.createElement("h5");
    var p2 = document.createElement("p");
    p.setAttribute("class", "p");
    p2.setAttribute("class", "p");
    h5.setAttribute("class", "h5");
    p.setAttribute('id', "event3");
    p2.setAttribute('id', "event33");
    h5.setAttribute('id', "event333");
    h5.innerHTML = event.categorie + ": " + event.straatNaam + " " + event.gemeente;
    p.innerHTML = event.omschrijving;
    p2.innerHTML = event.datum;
    eventDiv3.appendChild(h5);
    eventDiv3.appendChild(p);
    eventDiv3.appendChild(p2);

}
function clearbox()
{
    var eventDiv = document.getElementById("eerstpositie");
    var eventDiv2 = document.getElementById("tweedepositie");
    var eventDiv3 = document.getElementById("derdepositie");

    var olddiv = document.getElementById("event1");
    var olddiv2 = document.getElementById("event11");
    var olddiv3 = document.getElementById("event111");


    if (!(olddiv === null))
    {
        eventDiv.removeChild(olddiv);
        eventDiv.removeChild(olddiv2);
        eventDiv.removeChild(olddiv3);

        var olddiv = document.getElementById("event2");
        var olddiv2 = document.getElementById("event22");
        var olddiv3 = document.getElementById("event222");

        if (!(olddiv === null))
        {
            eventDiv2.removeChild(olddiv);
            eventDiv2.removeChild(olddiv2);
            eventDiv2.removeChild(olddiv3);


            var olddiv = document.getElementById("event3");
            var olddiv2 = document.getElementById("event33");
            var olddiv3 = document.getElementById("event333");

            if (!(olddiv === null))
            {
                eventDiv3.removeChild(olddiv);
                eventDiv3.removeChild(olddiv2);
                eventDiv3.removeChild(olddiv3);
            }

        }


    }



}
function handleRefresh()
{
    if (readCookie('evenement') === "true" && readCookie('situatie') === "true")
    {
        if (teller % 2 === 0)
        {


            clearbox();
            opzoekenevent();
            teller++;

        }
        else
        {

            clearbox();
            opzoekensituatie();
            teller++;
        }
    }
    else if (readCookie('evenement') === "true" || readCookie('evenement') === "null")
    {
        clearbox();
        opzoekenevent();
    }
    else if (readCookie('situatie') === 'true')
    {
        clearbox();
        opzoekensituatie();

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
function checkGemeente()
{
    if (readCookie('gemeente') === null)
    {
        gemeente = "9300 Aalst";
    }
    else
    {
        gemeente = readCookie('gemeente');
    }

}

