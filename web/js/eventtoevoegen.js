 var url = "http://localhost:8080/communityshareweb/resources"; // Send the new group to the back-end.
var event = {};

window.onload =function()
{
    button();
 
};

function toevoegenevent()
 {
    
     alert("test4");
     
     event.categorieEvent="Evenement";
     event.eventNr=9;
     event.persoonNr=10;//jQuery.trim($("#persoonNr2").val());
     event.fotoNr=12;//jQuery.trim($("#fotoNr").val());
     event.teller=0;
     event.straatNaam="zoutstraat";//jQuery.trim($("#straatNaam").val());
     event.gemeente="Aalst";//jQuery.trim($("#gemeente").val());
     event.omschrijving="omschrijving";//jQuery.trim($("#omschrijving").val());
     event.datum=jQuery.trim($("#datum").val());


    var request = new XMLHttpRequest();
    request.open("POST", url + "/event");

    request.onload = function() {
        if (request.status === 201) {
            alert("is toegevoegd");
        } 
    };
    request.setRequestHeader("Content-Type","APPLICATION/JSON");
    request.send(JSON.stringify(event));
};
function button()
{
    

$("#eventtoevoegen").click(function()
{
      alert("knop werkt");
    toevoegenevent();
  
    
    
});
}

