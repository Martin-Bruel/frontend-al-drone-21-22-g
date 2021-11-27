import argparse
import requests

from Command import Command


class LaunchDrone(Command):

    def __init__(self):
        self._url = 'http://localhost:8085/start/drone/{0}'

    def description(self):
        return 'Launch specified drone with given delivery'

    def execute(self):
        try:
            droneId = input('> '+'Enter droneId'+' : ')
            deliveryIds = input('> '+'Enter deliveryIds'+' : ')

            data = self.launchDrone(droneId, deliveryIds.split())
            print('> ','Starting ',droneId, ' with deliveries #', deliveryIds)
        except requests.exceptions.RequestException:
            print('Can\'t create connection with : ' + self._url)

    def launchDrone(self, droneId, deliveryIds):
        url = self._url.format(droneId)
        response = requests.post(url, json=deliveryIds)
        return response

if __name__ == "__main__":
    launchDrone = LaunchDrone()
    parser = argparse.ArgumentParser(description=launchDrone.description())
    parser.add_argument('-d', '--droneId', help='drone to launch', required=True)
    parser.add_argument('-D', '--deliveryIds', help='deliveries assigned to drone', required=True)
    args = parser.parse_args()
    res = launchDrone.launchDrone(args.droneId, args.deliveryIds)
    print(res)
