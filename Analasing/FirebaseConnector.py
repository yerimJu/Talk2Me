import firebase_admin
from firebase_admin import credentials
from firebase_admin import db


def load():
    cred = credentials.Certificate('Key.json')
    firebase_admin.initialize_app(cred, {
                                            'databaseURL' : 'https://talk2me-58045.firebaseio.com/'
                                        })

    ref = db.reference('/notifications')

    print(ref.get())

if __name__ == '__main__':
    load()
