from GetAllocations import GetAllocations
from Invoker import Invoker
from LaunchDrone import LaunchDrone


def register_commands():
    invoker = Invoker()
    invoker.register('1', GetAllocations())
    invoker.register('2', LaunchDrone())
    return invoker

def main():
    invoker = register_commands()
    while True:
        print('---------Menu--------')
        invoker.description()
        command = input('Enter the command ("0" to exit) : ')
        if command == '0':
            break
        invoker.execute(command)
    print('Bye.')


if __name__ == "__main__":
    main()