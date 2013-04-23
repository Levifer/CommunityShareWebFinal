 var url = "http://localhost:8080/communityshareweb/resources"; // Send the new group to the back-end.
var group = {};

window.onload =function()
{
    button();
 
};

function toevoegenpersoon()
 {
     
          
        group.persoonNr =jQuery.trim($("#persoonNr").val());
        group.score =jQuery.trim($("#score").val());
        group.facebookAccount=jQuery.trim($("#facebook").val());
        group.twitterAccount =jQuery.trim($("#twitter").val());


  

    var request = new XMLHttpRequest();
    request.open("POST", url + "/persoon");

    request.onload = function() {
        if (request.status === 201) {
            
        } ; 
    };
    request.setRequestHeader("Content-Type","application/json");
    request.send(JSON.stringify(group));
};
function button()
{
    

$("#knopke").click(function()
{
    toevoegenpersoon();
    
    
});
}