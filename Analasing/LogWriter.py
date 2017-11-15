import datetime


class LogWriter:
    def __init__(self, file_name):
        self.file_name = file_name
        self.file = open(file_name, 'a')  # if file not exists create new file

    def write_log(self, message, with_date=True):
        self.file = open(self.file_name, 'a')

        if with_date:
            message = '[' + self.get_date_string() + '] ' + message + '\n'

        self.file.write(message)
        self.file.close()

    @staticmethod
    def get_time(message):
        # TODO: 메세지를 받아와서 그 메세지를 파싱해 시간 부분을 반환하기
        pass

    @staticmethod
    def get_messagetype(message):
        # TODO: 메세지 타입을 읽어와서 반환하기
        pass

    @staticmethod
    def get_message(message):
        # TODO: 시간을 제외한 메세지 부분만 반환하기
        pass

    def get_last_scrap_time(self):
        self.file = open(self.file_name, 'r')

        lines = self.file.readlines()

        for line in list(reversed(lines)):
            # TODO: reversed된 리스트를 분석해서 스크랩과 관련된 메세지가 나오게 되면 해당 부분의 시간을 반환하기
            pass
            
        self.file.close()

    @staticmethod
    def get_date_string():
        now = datetime.datetime.now()
        return now.strftime('%Y-%m-%d %H:%M:%S')


if __name__ == '__main__':
    lw = LogWriter('C:/Users/user/Desktop/test.txt')
    for i in range(0, 10):
        lw.write_log('test' + str(i))

    lw.get_last_scrap_time()
