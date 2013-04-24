 
 
 var events=[];
 var teller =1;
 var url = "http://localhost:8080/communityshareweb/resources";
window.onload = function(){
    opzoekenevent();
 setInterval(handleRefresh,15000);


 }
 function opzoekenevent()
 {
     var gemeente = "Aalst";

  var request = new XMLHttpRequest();
  request.open("GET", url + "/event/"+gemeente);
  request.onload = function(){// our request object
      if (request.status === 200){//return code is "OK" 
          displayke(request.responseText);

      }
  };
  
  request.send(null);
 
 }
 function opzoekensituatie()
 {
    var gemeente = "Aalst";

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
    
     var eventDiv = document.getElementById("eerstpositie");
     var eventDiv2 = document.getElementById("tweedepositie");
     var eventDiv3 = document.getElementById("derdepositie");
     // eventDiv.innerHTML = responseText;
     events =JSON.parse(responseText);
    
     
             var event = events[0];
             var p = document.createElement("p");
             var h5 =document.createElement("h5");
             var p2 = document.createElement("p");
             
             p.setAttribute("class","p");
             p2.setAttribute("class","p");
             h5.setAttribute("class","h5");
                p.setAttribute('id', "event1"); 
                p2.setAttribute('id',"event11");
                h5.setAttribute('id',"event111");
            
             
            
                     h5.innerHTML = event.categorie + ": " +event.straatNaam + " " +event.gemeente;
                     p.innerHTML = event.omschrijving;
                     p2.innerHTML= event.datum;
             eventDiv.appendChild(h5);
             eventDiv.appendChild(p);
             eventDiv.appendChild(p2);
             
             var event = events[1];
             var p = document.createElement("p");
             var h5 =document.createElement("h5");
             var p2 = document.createElement("p");
             p.setAttribute("class","p");
             p2.setAttribute("class","p");
             h5.setAttribute("class","h5");
                p.setAttribute('id', "event2"); 
                p2.setAttribute('id',"event22");
                h5.setAttribute('id',"event222");
                     h5.innerHTML = event.categorie + ": " +event.straatNaam + " " +event.gemeente;
                     p.innerHTML = event.omschrijving;
                     p2.innerHTML= event.datum;
             eventDiv2.appendChild(h5);
             eventDiv2.appendChild(p);
             eventDiv2.appendChild(p2);
             
             var event = events[2];
             var p = document.createElement("p");
             var h5 =document.createElement("h5");
             var p2 = document.createElement("p");
             p.setAttribute("class","p");
             p2.setAttribute("class","p");
             h5.setAttribute("class","h5");
                p.setAttribute('id', "event3"); 
                p2.setAttribute('id',"event33");
                h5.setAttribute('id',"event333");
                     h5.innerHTML = event.categorie + ": " +event.straatNaam + " " +event.gemeente;
                     p.innerHTML = event.omschrijving;
                     p2.innerHTML= event.datum;
             eventDiv3.appendChild(h5);
             eventDiv3.appendChild(p);
             eventDiv3.appendChild(p2);
         
 }
 function clearbox()
 {
                 var eventDiv = document.getElementById("eerstpositie");
                var eventDiv2 = document.getElementById("tweedepositie");
                var eventDiv3 = document.getElementById("derdepositie");
                 
                 var olddiv = document.getElementById("event1");
                 var olddiv2 = document.getElementById("event11");
                 var olddiv3 = document.getElementById("event111");
                 
                       
             

                 eventDiv.removeChild(olddiv);
                 eventDiv.removeChild(olddiv2);
                 eventDiv.removeChild(olddiv3);
                 
                 var olddiv = document.getElementById("event2");
                 var olddiv2 = document.getElementById("event22");
                 var olddiv3 = document.getElementById("event222");
                 
                 eventDiv2.removeChild(olddiv);
                 eventDiv2.removeChild(olddiv2);
                 eventDiv2.removeChild(olddiv3);
                         
                    var olddiv = document.getElementById("event3");
                 var olddiv2 = document.getElementById("event33");
                 var olddiv3 = document.getElementById("event333");
                 eventDiv3.removeChild(olddiv);
                 eventDiv3.removeChild(olddiv2);
                 eventDiv3.removeChild(olddiv3);
        

 }
function handleRefresh()
 {
     if(teller%2===0)
         {
             
             
             clearbox();
             opzoekenevent();
             teller++;
             
         }
         else
             {
                 
                 clearbox();
                 opzoekensituatie();
                 teller++;
             }
 }