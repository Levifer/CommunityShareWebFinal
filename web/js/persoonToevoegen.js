var url = "http://localhost:8080/communityshareweb/resources";
var persoon = {};

window.onload =function()
{
    button();
};

function toevoegenpersoon()
 {
     
          
        persoon.persoonNr =jQuery.trim($("#persoonNr").val());
        persoon.score =jQuery.trim($("#score").val());
        persoon.facebookAccount=jQuery.trim($("#facebook").val());
        persoon.twitterAccount =jQuery.trim($("#twitter").val());

   
  
        
    var request = new XMLHttpRequest();
    request.open("POST", url + "/persoon");

    request.onload = function()
    {
        if (request.status === 201) 
        {
           
        }  
    };
        request.setRequestHeader("Content-Type","application/json");
        request.send(JSON.stringify(persoon));
    };

function button()
{
    

        $("#persoontoevoegen").click(function()
        {
            toevoegenpersoon();
          
        });
}