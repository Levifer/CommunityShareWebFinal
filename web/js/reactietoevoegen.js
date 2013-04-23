 var url = "http://localhost:8080/communityshareweb/resources"; // Send the new group to the back-end.
var reactie = {};

window.onload =function()
{
    button();
 
};

function toevoegenreactie()
 {
     reactie.eventNr=jQuery.trim($("#persoonNr").val());
     reactie.gevaarNr=
     reactie.persoonNr=
     reactie.reactieNr=0;
     reactie.reactieNr=
     reactie.datum=new Date();



    var request = new XMLHttpRequest();
    request.open("POST", url + "/reactie");

    request.onload = function() {
        if (request.status === 201) {
            
        } ; 
    };
    request.setRequestHeader("Content-Type","application/json");
    request.send(JSON.stringify(reactie));
};
function button()
{
    

$("#knopke").click(function()
{
    toevoegenreactie();
    
    
});
}
