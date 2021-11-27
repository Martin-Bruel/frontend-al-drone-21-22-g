exports.distance = function(p1, p2){

    var a = p1.latitude - p2.latitude;
    var b = p1.longitude - p2.longitude;
    return Math.sqrt( a*a + b*b );
}