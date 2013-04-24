

var url = "http://localhost:8080/communityshareweb/resources";
var liken = {};

window.onload =function()
{
    button();
 
}

function toevoegenliken()
 {
     
     liken.eventNr=0;//jQuery.trim($("#eventnr").val());
     liken.gevaarNr=1;//jQuery.trim($("#gevaarnr").val());
     liken.persoonNr=10;//jQuery.trim($("#persoonNrke").val());
     liken.likeNr=0;
     liken.liken=true;//jQuery.trim($("#liken").val());



    var request = new XMLHttpRequest();
    request.open("POST", url + "/like");

    request.onload = function() {
        if (request.status === 201) {
            
        } 
    };
    request.setRequestHeader("Content-Type","application/json");
    request.send(JSON.stringify(liken));
};
function button()
{
    

$("#liketoevoegen").click(function()
{
    toevoegenliken();
    
    
});
}