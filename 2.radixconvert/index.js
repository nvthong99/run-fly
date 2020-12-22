function convert(radixin, value){
    var result = ''
    var num;
    if(value == 0) result = '0'
    if(value == 1) result = '1'
    if(value == 2) result = '10'
    if(value == 3) result = '11'
    if(value == 4) result = '100'
    if(value == 5) result = '101'
    if(value == 6) result = '110'
    if(value == 7) result = '111'
    if(value == 8) result = '1000'
    if(value == 9) result = '1001'
    if(value == 'A'|| value == 'a') result = '1010'
    if(value == 'B'|| value == 'b') result = '1011'
    if(value == 'C'|| value == 'c') result = '1100'
    if(value == 'D' || value == 'd') result = '1101'
    if(value == 'E'|| value == 'e') result = '1110'
    if(value == 'F' || value == 'f') result = '1111'
     
    if(radixin == 8) num = 3
    if(radixin == 16 ) num = 4
    while(result.length < num){
        result = '0' + result
    }
    return result

}
console.log(convert(16, 'F'))

function changeradix(ojb){
    var radixin = ojb.value
    var text = document.getElementById("in")
    var x = ''
    x = document.getElementById("in").value;
    x = x.toUpperCase()
    var result = document.getElementById("result")
    var evl =''

    result.innerHTML = ''

    console.log(x)
    for ( i in x){
        var str = convert(radixin, x[i])
        
        if( i == 0){
            var start = str.indexOf('1');
            evl = str.slice(start, str.length)
        }else{
            evl = evl + ` ${str}`
        }
        
        console.log(str)
        result.innerHTML = result.innerHTML + `<h1> ${x[i]} <p>(${radixin}) </p> = ${str} <p>(2)</p> </h1>`
    }
    result.innerHTML =result.innerHTML + `<h1> Vậy ${x} <p>(${radixin}) </p> = ${evl} <p>(2)</p> </h1>`



}