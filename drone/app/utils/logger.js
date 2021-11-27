/* eslint-disable no-console */
const originalConsoleLog = console.log
console.log = function consoleLog(...args) {
  const newArguments = [...args]
  return originalConsoleLog.apply(this, newArguments)
}
console.info = console.log

module.exports = console
