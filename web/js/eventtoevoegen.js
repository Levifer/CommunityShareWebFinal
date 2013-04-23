 var url = "http://localhost:8080/communityshareweb/resources"; // Send the new group to the back-end.
var event = {};

window.onload =function()
{
    button();
 
};

function toevoegenevent()
 {
     event.persoonNr=jQuery.trim($("#persoonNr").val());
     event.fotoNr=
     event.teller=
     event.straatNaam=
     event.gemeente=
     event.omschrijving=
     event.datum=
     event.eventnr=0;
     event.categorie="Evenement";


    var request = new XMLHttpRequest();
    request.open("POST", url + "/event");

    request.onload = function() {
        if (request.status === 201) {
            
        } ; 
    };
    request.setRequestHeader("Content-Type","application/json");
    request.send(JSON.stringify(event));
};
function button()
{
    

$("#knopke").click(function()
{
    toeveogenevent();
    
    
});
}

