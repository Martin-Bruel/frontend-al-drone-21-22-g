async function receivedNotifications(notifications){
    console.log("Bunch of notifications recieved :")
    for(indexNotification in notifications){
        currentNotif = notifications[indexNotification]
        console.log("Notification "+currentNotif.id+" : "+currentNotif.description);
    }
}

module.exports = {
    receivedNotifications
}