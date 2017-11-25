class assssd:
    def __init__(self,a):
        self.a = 'a'


class SNSUser:
    def __init__(self, uid, name):
        self.uid = uid
        self.name = name
        self.notifications = []

    def test(self):

        self.notifications.append({'asdf':assssd('aa')})
        self.notifications.append({'asdfdsfsdfs':'fdsffsfsfsdf'})
        print(self.notifications)


if __name__ == '__main__':
    user = SNSUser('asdfasfd', 'JYT')
    user.test()
