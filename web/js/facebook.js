var email;
var groep = {};
var facebookaccount;
var naamvol;
window.fbAsyncInit = function() {
    FB.init({
        appId: '509855929050339',
        oauth: true,
        status: true,
        cookie: true,
        xfbml: true
    });
};

function loginUser()
{
    FB.login(function(response)
    {

        if (response.authResponse) {
            console.log('Welcome!  Fetching your information.... ');

            access_token = response.authResponse.accessToken;
            user_id = response.authResponse.userID;
            login();


        } else {

            console.log('User cancelled login or did not fully authorize.');

        }
    }, {
        scope: 'publish_stream,email'
    });
}
function postToWall()
{
    var naam = 'melding:';
    var soort = document.getElementById("facebookdata").innerText;
    var beschrijving = document.getElementById("fbomschrijving").innerText;
    var obj = {
        method: 'feed',
        redirect_uri: 'http://localhost:8080/communityshare/home.html',
        link: 'http://localhost:8080/communityshare/home.html',
        name: naam,
        caption: soort,
        description: beschrijving
    };

    FB.ui(obj);
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
function login()
{
    FB.api('/me', function(response) {
        facebookaccount = response.email;
        naamvol = response.name;



        var http = new XMLHttpRequest();
        http.open("GET", url + "/persoon/" + facebookaccount);
        http.onload = function() {
            if (http.status === 200)
            {
                var persoon = JSON.parse(http.responseText);

                createCookie('persoonNr', persoon, 1);

                window.location = "http://localhost:8080/communityshare/home.html";

            }
            else
            {

                var arry = naamvol.split(" ", 2);
                var voornaam = arry[0];
                var naam = arry[1];
                var voornaam = voornaam.trim();
                var naam = naam.trim();

                groep.persoonNr = 0;
                groep.facebookAccount = facebookaccount;
                groep.naam = naam;
                groep.voornaam = voornaam;

                var xml = new XMLHttpRequest();
                xml.open("POST", url + "/persoon/");
                xml.onload = function() {

                    if (xml.status === 204)
                    {

                        login();

                    }
                };

                xml.setRequestHeader("Content-Type", "application/json");
                xml.send(JSON.stringify(groep));


            }
        };

        http.send(null);
    });
}