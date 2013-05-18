function opslaan()
{
    createCookie('radius', document.getElementById("range").innerHTML, 100);
    createCookie('gemeente', document.getElementById("search").value, 100);
    createCookie('situatie', document.getElementById("regularCheckbox").checked, 100);
    createCookie('evenement', document.getElementById("tweedebox").checked, 100);
}

function readCookie(name)
{
    var nameEQ = name + "=";
    var ca = document.cookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) === ' ')
            c = c.substring(1, c.length);
        if (c.indexOf(nameEQ) === 0)
            return c.substring(nameEQ.length, c.length);
    }
    return null;
}
function createCookie(name, value, days) {
    if (days) {
        var date = new Date();
        date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
        var expires = "; expires=" + date.toGMTString();
    }
    else
        var expires = "";
    document.cookie = name + "=" + value + expires + "; path=/";
}
function RangefieldCheck()
{
    if (readCookie('radius') === null)
    {
        document.getElementById("rangefield").value = 5;
    }
    else
    {
        document.getElementById("rangefield").value = readCookie('radius');
    }
}

function RangeCheck()
{
    if (readCookie('radius') === null)
    {
        document.getElementById("range").innerHTML = 5;
    }
    else
    {
        document.getElementById("range").innerHTML = readCookie('radius');
    }
}

function GemeenteCheck()
{
    if (readCookie('gemeente') === null)
    {
        document.getElementById("search").value = '9300 Aalst';
    }
    else
    {
        document.getElementById("search").value = readCookie('gemeente');
    }
}

function checkboxCheck()
{
    if ((readCookie('situatie') === null))
    {
        document.getElementById("regularCheckbox").checked = false;
    }
    else if (readCookie('situatie') === 'true')
    {
        document.getElementById("regularCheckbox").checked = true;
    }
    else if (readCookie('situatie') === 'false')
    {
        document.getElementById("regularCheckbox").checked = false;
    }
}
function secondcheckboxCheck()
{
    if (readCookie('evenement') === "null")
    {
        document.getElementById("tweedebox").checked = true;
    }
    else if (readCookie('evenement') === 'true')
    {
        document.getElementById("tweedebox").checked = true;
    }
    else if (readCookie('evenement') === 'false')
    {
        document.getElementById("tweedebox").checked = false;
    }
}
