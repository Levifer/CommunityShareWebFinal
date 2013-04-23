 var url = "http://localhost:8080/communityshareweb/resources";
 
window.onload = function() {

    var eventNr = 1;
    var persoonNr =1;

    // Send a delete request to the back-end.
    var request = new XMLHttpRequest();
    alert("testing");
    request.open("DELETE", url + "/event/" + eventNr + "," + persoonNr);
    request.onload = function() {
        if (request.status === 204) {
            alert("event has been deleted");
        } ; 
    };
    request.send(null);
};