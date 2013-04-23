 var url = "http://localhost:8080/communityshareweb/resources"; // Send the new group to the back-end.
var gevaar = {};

window.onload =function()
{
    button();
 
};

function toevoegengevaar()
 {
     gevaar.persoonNr=jQuery.trim($("#persoonNr").val());
     gevaar.fotoNr=
     gevaar.teller=
     gevaar.straatNaam=
     gevaar.gemeente=
     gevaar.omschrijving=
     gevaar.datum=
     gevaar.eventnr=0;
     gevaar.categorie="";


    var request = new XMLHttpRequest();
    request.open("POST", url + "/gevaarveld");

    request.onload = function() {
        if (request.status === 201) {
            
        } ; 
    };
    request.setRequestHeader("Content-Type","application/json");
    request.send(JSON.stringify(gevaar));
};
function button()
{
    

$("#knopke").click(function()
{
    toevoegengevaar();
    
    
});
}