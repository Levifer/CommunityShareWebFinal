 var url = "http://localhost:8080/communityshareweb/resources";
window.onload = function() {

    var gevaarNr = 1;
    var persoonNr =1;

    // Send a delete request to the back-end.
    var request = new XMLHttpRequest();
    alert("testing");
    request.open("DELETE", url + "/gevaarveld/" + gevaarNr + "," + persoonNr);
    request.onload = function() {
        if (request.status === 204) {
            alert("gevaarveld has been deleted");
        } ; 
    };
    request.send(null);
};
