import datetime
import messageType
import re


class LogWriter:
    pattern = re.compile(r"\[(.+?)\] .+")

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

        match = LogWriter.pattern.match(message)
        return match[2]

    @staticmethod
    def get_message_type(message):
        # Collect From Twitter: Target='asdfasdf'
        # Collect From Facebook: Target='asdfasdf'
        # FaceBook: Found Users Collect Targets

        collect_pattern = re.compile("Collect From (Twitter|Facebook): Target='(.+)'")
        target_pattern = re.compile("(Twitter|Facebook|): (.+)")
        if re.match(collect_pattern, LogWriter.get_message(message)):
            return messageType.MessageType.Collect
        if re.match(target_pattern, LogWriter.get_message(message)):
            return messageType.MessageType.UserRead
        # TODO: Debug this part
        return messageType.MessageType.Unknown

    @staticmethod
    def get_message(message):
        match = LogWriter.pattern.match(message)
        return match[1]

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
    lw = LogWriter('test.txt')
    for i in range(0, 10):
        lw.write_log('test' + str(i))

    lw.get_message_type('[asdfasdfa] 123456567778')
    lw.get_last_scrap_time()
