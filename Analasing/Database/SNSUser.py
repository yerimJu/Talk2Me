from Database.UserSetting import UserSetting


class assssd:
    def __init__(self,a):
        self.a = 'a'


class SNSUser:
    def __init__(self, uid, name, setting):
        self.uid = uid
        self.name = name
        self.setting = UserSetting()


if __name__ == '__main__':
    user = SNSUser('asdfasfd', 'JYT')
