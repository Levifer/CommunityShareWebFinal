 var url = "http://localhost:8080/communityshareweb/resources";
window.onload = function() {

    var reactieNr = 1;
    var persoonNr =10;
    

    // Send a delete request to the back-end.
    var request = new XMLHttpRequest();
    request.open("DELETE", url + "/reactie/" + reactieNr + "," + persoonNr);
    request.onload = function() {
        if (request.status === 204) {
            alert("reactie has been deleted");
        } ; 
    };
    request.send(null);
};