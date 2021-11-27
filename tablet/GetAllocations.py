import argparse
import requests

from Command import Command


class GetAllocations(Command):

    def __init__(self):
        self._url = 'http://localhost:8085/truck/allocation'

    def description(self):
        return 'Get drone/delivery allocations'

    def execute(self):
        try:
            data = self.getAllocations()
            for allocation in data:
                print('> ',allocation['droneId'], ' will handle deliveries #', allocation['deliveryIds'])
        except requests.exceptions.RequestException:
            print('Can\'t create connection with : ' + self._url)

    def getAllocations(self):
        response = requests.get(self._url)
        return response.json()

if __name__ == "__main__":
    getAllocations = GetAllocations()
    parser = argparse.ArgumentParser(description=getAllocations.description())
    args = parser.parse_args()
    res = getAllocations.getAllocations()
    print(res)