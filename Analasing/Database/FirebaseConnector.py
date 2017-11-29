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

        users_dict = FirebaseConnector.get_user()

        for user in users_dict:
            print('user token : ', user)
            # print(users_dict[user])

            try:
                print('facebook Access Token : ', users_dict[user]['facebookAccesstoken'])
            except KeyError:
                print("this User doesn't have FaceBook Access Token")

            try:
                print('setting : ', FirebaseConnector.get_setting(user))
            except KeyError:
                # TODO: 이 경우에 디폴트 설정으로 설정해주기
                pass

            print('email : ', users_dict[user]['email'])
            print('username : ', users_dict[user]['username'], end='\n\n')

    @staticmethod
    def get_setting(token):
        return db.reference('/settings').get()[token]

    @staticmethod
    def get_user():
        return db.reference('/users').get()
