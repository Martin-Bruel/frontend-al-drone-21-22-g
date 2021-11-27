class Invoker:
    
    def __init__(self):
        self._commands = {}

    def register(self, name, command):
        self._commands[name] = command

    def description(self):
        for name in self._commands.keys():
            print('[' + name + '] ' + self._commands[name].description())

    def execute(self, name):
        if name in self._commands.keys():
            self._commands[name].execute()
        else:
            print('Command "' + name + '" not recognised...')
