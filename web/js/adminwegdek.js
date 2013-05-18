
var situaties = [];

var url = "http://localhost:8080/communityshare/resources";
window.onload = function()
{

    opzoekensituatie();

};

function opzoekensituatie()
{
    var gemeente = readCookie('gemeenteAdmin');

    var request = new XMLHttpRequest();
    request.open("GET", url + "/gevaarveld/" + gemeente);
    request.onload = function() {
        if (request.status === 200) {
            displaysituatie(request.responseText);

        }
    };

    request.send(null);

}

function displaysituatie(responseText)
{


    var situatieDiv = document.getElementById("wegdek");

    situaties = JSON.parse(responseText);

    for (var s = 0; s < situaties.length; s++)
    {


        var situatie = situaties[s];
        if (situatie.categorie === "Wegdek Schade")
        {
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



                    var request = new XMLHttpRequest();
                    request.open("DELETE", url + "/admin/admin/" + gevaarNr);
                    request.onload = function()
                    {
                        if (request.status === 204) {

                            window.location.href = "adminwegdek.html";
                        }
                        ;
                    };
                    request.send(null);
                };
            }

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

