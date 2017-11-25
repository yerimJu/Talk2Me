import FaceBookCollector
import TwitterCollector
import RSSCollector
from FirebaseConnector import FirebaseConnector

if __name__ == '__main__':
    f = FirebaseConnector()
    f.load()
