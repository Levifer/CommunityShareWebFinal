 var url = "http://localhost:8080/communityshareweb/resources";
window.onload = function() {

    var likeNr = 3;
    var persoonNr =9;
    

    // Send a delete request to the back-end.
    var request = new XMLHttpRequest();
    request.open("DELETE", url + "/like/" + likeNr + "," + persoonNr);
    request.onload = function() {
        if (request.status === 204) {
            alert("like has been deleted");
        } ; 
    };
    request.send(null);
};