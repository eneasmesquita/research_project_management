/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//abaixo coloque o tempo limite no formato minutos : segundos
var limit = "40" + ":00";
if (document.images) {
    var parselimit = limit.split(":")
    parselimit = parselimit[0] * 60 + parselimit[1] * 1
}
function contagem() {
    if (!document.images)
        return
    if (parselimit == 1)
        top.location.href = "../login.jsf"
    else {
        parselimit -= 1
        curmin = Math.floor(parselimit / 60)
        cursec = parselimit % 60
        if (cursec < 10)
            cursec = "0" + cursec;
        if (curmin != 0)
            curtime = curmin + ":" + cursec;
        else
            curtime = cursec;//+" Segundos regredindo....."
        document.getElementById('countDownSessao').innerHTML = curtime;
        setTimeout("contagem()", 1000);
    }
}

