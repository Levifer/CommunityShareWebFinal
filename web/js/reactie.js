var url = "http://localhost:8080/communityshare/resources";
var groep = {};
var goepsit = {};
var reac = [];
var pers = "";
var react;
var persoonNrInlog = readCookie('persoonNr');
window.onload = function()
{

    if (readCookie('EventNr') === "null")
    {

        toonreactiesituatie();
        button();
    }
    else
    {
        toonreactiesevenement();
        button();

    }
};
function toonreactiesevenement()
{




    var eventNr = readCookie('EventNr');
    var request = new XMLHttpRequest();
    request.open("GET", url + "/reactie/reactie/" + eventNr);

    request.onload = function() {
        if (request.status === 200) {


            toonreacties(request.responseText);


        }
    };

    request.send(null);


}
function toonreactiesituatie()
{


    var gevaarnr = readCookie('GevaarNr');
    var request = new XMLHttpRequest();
    request.open("GET", url + "/reactie/" + gevaarnr);

    request.onload = function() {
        if (request.status === 200)
        {


            toonreacties(request.responseText);


        }
    };

    request.send(null);




}
function toevoegenreactieevenement()
{
    var datumvandaag = new Date();
    var datum = datumvandaag.getFullYear() + "-" + (datumvandaag.getMonth() + 1) + "-" + datumvandaag.getDate();


    groep.eventNr = readCookie('EventNr');
    groep.gevaarNr = 0;
    groep.persoonNr = persoonNrInlog;
    groep.reactieNr = 0;
    groep.reactie = jQuery.trim($("#regularTextarea").val());
    groep.datum = datum;

    if (!(groep.reactie === ""))
    {

        var request = new XMLHttpRequest();
        request.open("POST", url + "/reactie");

        request.onload = function() {
            if (request.status === 204)
            {
                window.location.href = "reacties.html";

            }
            ;
        };
        request.setRequestHeader("Content-Type", "application/json");
        request.send(JSON.stringify(groep));
    }
    else
    {
        alert("Er moet een reactie worden ingevuld");

    }
}
function toevoegenreactiesituatie()
{
    var datumvandaag = new Date();
    var datum = datumvandaag.getFullYear() + "-" + (datumvandaag.getMonth() + 1) + "-" + datumvandaag.getDate();

    goepsit.eventNr = 0;
    goepsit.gevaarNr = readCookie('GevaarNr');
    goepsit.persoonNr = persoonNrInlog;
    goepsit.reactieNr = 0;
    goepsit.reactie = jQuery.trim($("#regularTextarea").val());
    goepsit.datum = datum;

    if (!(goepsit.reactie === ""))
    {

        var request = new XMLHttpRequest();
        request.open("POST", url + "/reactie");

        request.onload = function() {
            if (request.status === 204)
            {
                window.location.href = "reacties.html";

            }
            ;
        };
        request.setRequestHeader("Content-Type", "application/json");
        request.send(JSON.stringify(goepsit));
    }
    else
    {
        alert("Er moet een reactie worden ingevuld");

    }
}
function button()
{


    $("#eventtoevoegen").click(function()
    {


        if (readCookie('EventNr') === "null")
        {

            toevoegenreactiesituatie();

        }
        else
        {
            toevoegenreactieevenement();

        }


    });
}

function toonreacties(responseText)
{

    var reactieDiv = document.getElementById("reactielijst");

    reac = JSON.parse(responseText);

    var i = 0;
    if (!(reac.length === 0))
    {
        reactielus();
        function reactielus()
        {



            var react = reac[i];

            var p = document.createElement("p");
            var h5 = document.createElement("h5");
            var div = document.createElement("div");

            p.setAttribute("class", "p");
            h5.setAttribute("class", "h5");
            div.setAttribute("class", "sixteen columns");



            var persoon = react.persoonNr;

            var request = new XMLHttpRequest();
            request.open("GET", url + "/persoon/persoon/" + persoon);

            request.onload = function() {// our request object
                if (request.status === 200)
                {

                    var pers = request.responseText;

                    h5.innerHTML = pers + "</br>";
                    p.innerHTML = react.reactie + "</br></br>" + react.datum;

                    reactieDiv.appendChild(div);
                    div.appendChild(h5);
                    div.appendChild(p);
                    i++;
                    if (i < reac.length)
                    {
                        reactielus();
                    }

                }
                else
                {
                    alert("deze persoon bestaat ni");
                }
            };

            request.send(null);





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