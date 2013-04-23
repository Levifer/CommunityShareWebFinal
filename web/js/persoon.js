 
/* //window.onload = function(){
 var persoons=[];
 var url = "http://localhost:8080/communityshareweb/resources";
swindow.onload = function(){
    
 //setInterval(handleRefresh,1000);
 
 

  var request = new XMLHttpRequest();
  request.open("GET", url + "/persoon");
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
     var persoonDiv = document.getElementById("PersoonList")
     
      //persoonDiv.innerHTML = responseText;
      persoons =JSON.parse(responseText);
    
     for(var i = 0; i < persoons.length; i++)
         {
             var persoon = persoons[i];
             var div = document.createElement("div");
             div.setAttribute("class","Persoon");
             if(persoon.facebookAccount!="geen")
                 {
                     div.innerHTML = "persoonnr = " + persoon.persoonNr + " Score = "+persoon.score+" FacebookAccount = "+persoon.facebookAccount;
                 }
                 else
                 {
                     div.innerHTML = "persoonnr = " + persoon.persoonNr + " Score = "+persoon.score+ " TwitterAccount = "+persoon.twitterAccount;
                 }
             persoonDiv.appendChild(div);
         }
 }
 /*function handleRefresh()
 {
    var url = "http://localhost:8080/communityshareweb/resources/persoon/?callback=displayke";
 var newScriptElement=document.createElement("script")
 newScriptElement.setAttribute("src",url);
 newScriptElement.setAttribute("id","jsonp");
 
 var oldScriptElement = document.getElementById("jsonp");
 var head = document.getElementsByTagName("head")[0];
 if(oldScriptElement == null)
     {
         head.appendChild(newScriptElement);
     }else
         {
             head.replaceChild(newScriptElement, oldScriptElement);
         }
 
 }*/