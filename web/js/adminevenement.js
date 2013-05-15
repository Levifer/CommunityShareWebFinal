var events = [];
var url = "http://localhost:8080/communityshare/resources";
window.onload = function()
{
    opzoekenevent();


};
function opzoekenevent()
{
    var gemeente = readCookie('gemeenteAdmin');

    var request = new XMLHttpRequest();
    request.open("GET", url + "/event/" + gemeente);
    request.onload = function() {
        if (request.status === 200) {

            displayevent(request.responseText);

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


                var request = new XMLHttpRequest();
                request.open("DELETE", url + "/admin/" + eventNr);
                request.onload = function()
                {
                    if (request.status === 204) {

                        window.location.href = "adminevenement.html";
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