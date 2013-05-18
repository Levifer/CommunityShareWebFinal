

var admin = {};
var url = "http://localhost:8080/communityshare/resources";
window.onload = function()
{
    button();


};
function login()
{
    var gebruikersnaam = jQuery.trim($("#gebruiker").val());
    var wachtwoord = jQuery.trim($("#wachtwoord").val());
    var request = new XMLHttpRequest();
    request.open("GET", url + "/admin/" + gebruikersnaam + "," + wachtwoord);
    request.onload = function()
    {
        if (request.status === 200)
        {
            admin = JSON.parse(request.responseText);
            var gemeente = admin.gemeente;
            createCookie('gemeenteAdmin', gemeente, 1);
            window.location.href = "admingevaar.html";
        }
        else
        {
            alert("Wachtwoord of Gebruikersnaam zijn incorrect");

        }
    };
    request.send(null);
}
;

function button()
{


    $("#login").click(function()
    {

        login();



    });
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