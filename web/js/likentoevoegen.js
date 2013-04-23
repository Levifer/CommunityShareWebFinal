 var url = "http://localhost:8080/communityshareweb/resources"; // Send the new group to the back-end.
var liken = {};

window.onload =function()
{
    button();
 
};

function toevoegenliken()
 {
     liken.eventNr=jQuery.trim($("#persoonNr").val());
     liken.gevaarNr=
     liken.persoonNr=
     liken.likeNr=0;
     liken.liken="";



    var request = new XMLHttpRequest();
    request.open("POST", url + "/liken");

    request.onload = function() {
        if (request.status === 201) {
            
        } ; 
    };
    request.setRequestHeader("Content-Type","application/json");
    request.send(JSON.stringify(liken));
};
function button()
{
    

$("#knopke").click(function()
{
    toevoegenliken();
    
    
});
}