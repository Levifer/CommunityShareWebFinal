//window.onload = function(){
 var gevaren=[];
 var url = "http://localhost:8080/communityshareweb/resources";
window.onload = function(){
    
 //setInterval(handleRefresh,1000);
 
 var gemeente = "haaltert";

  var request = new XMLHttpRequest();
  request.open("GET", url + "/gevaarveld/"+gemeente);
  request.onload = function(){// our request object
      if (request.status === 200){//return code is "OK" 
          displayke(request.responseText);

      }
  };
  
  request.send(null);
 
 }
 
 function displayke(responseText)
 //function displayke(persoons)
 {
     var gevaarDiv = document.getElementById("PersoonList")
     
      //eventDiv.innerHTML = responseText;
      gevaren =JSON.parse(responseText);
    
     for(var i = 0; i < gevaren.length; i++)
         {
             var gevaar = gevaren[i];
             var div = document.createElement("div");
             div.setAttribute("class","gevaar");
            
                     div.innerHTML = " persoonnr = " + gevaar.persoonNr + " fotoNr = "+gevaar.fotoNr+" straatNaam = "+gevaar.straatNaam+" gemeente = "+gevaar.gemeente+" omschrijving ="+gevaar.omschrijving;
           
             gevaarDiv.appendChild(div);
         }
 }

