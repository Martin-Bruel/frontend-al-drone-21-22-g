const notificationService = require('../services/notification.service');

async function receivedNotifications(req, res) {
    try {
        notificationService.receivedNotifications(req.body);
        res.status(200).json('notification received')
    } catch (error) {
        console.log(error)
        res.status(500)
    }
}

module.exports = {
    receivedNotifications
}