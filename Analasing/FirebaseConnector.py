import firebase_admin
from firebase_admin import credentials
from firebase_admin import db


class FirebaseConnector:
    @staticmethod
    def load():
        cred = credentials.Certificate('Key.json')
        firebase_admin.initialize_app(cred, {
                                                'databaseURL': 'https://talk2me-58045.firebaseio.com/'
                                            })

        ref = db.reference('/users')
        users_dict = ref.get()

        ref = db.reference('/notifications')
        notification_dict = ref.get()

        for user in users_dict:
            print('user token : ', user)
            # print(users_dict[user])
            try:
                print('facebook Access Token : ', users_dict[user]['facebookAccesstoken'])
            except KeyError:
                print("this User dosen't have FaceBook Access Token")
            print('email : ', users_dict[user]['email'])
            print('username : ', users_dict[user]['username'], end='\n\n')
