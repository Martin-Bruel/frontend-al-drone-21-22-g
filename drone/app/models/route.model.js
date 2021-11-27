const Joi = require('joi')
const BaseModel = require('../utils/base-model.js')

module.exports = new BaseModel('Route', {
  step:Joi.number().required(),
  itinary: Joi.object().required(),
  start: Joi.number().required(),
  lastdate: Joi.number().required()
})
