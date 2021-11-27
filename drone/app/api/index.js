const { Router } = require('express')
const router = new Router()
const DroneController = require('../controllers/drone.controller')

router.post('/delivery/start',DroneController.startDelivery )
router.post('/connection/restart',DroneController.reStartDelivery )
router.post('/connection/stop',DroneController.stopDelivery )
router.get('/position', DroneController.getPosition)
module.exports = router