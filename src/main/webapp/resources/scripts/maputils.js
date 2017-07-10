var selectedMarker = null;

function refreshMapData(args){
    clearMarkers();
    var newMarkers = eval('(' + args.newMarkers + ')');
    var newMarkersLength = newMarkers.length;

    for(var i = 0;i < newMarkersLength;i++)
    {
        var newMarker = newMarkers[i];
        var marker = new google.maps.Marker({
            position: newMarker.latlng,
            title:newMarker.title,
            clickable:true,
            id:newMarker.id,
            icon:newMarker.icon,
            data:newMarker.data
        });
        marker.addListener('click', function() {
            eventMapMarkerSelected([{name:'selectedMarkerType', value:this.data.type},{name:'selectedMarkerID', value:this.data.id}]);
        });
        marker.setMap(PF('globalMap').getMap())
        PF('globalMap').getMap().markers[i] = marker
    }
}

function clearMarkers() {
    var gmap = PF('globalMap').getMap();
    for(var i in gmap.markers)
    {
        var oldMarker = gmap.markers[i];
        oldMarker.setMap(null);
    }
    PF('globalMap').getMap().markers=[]
}