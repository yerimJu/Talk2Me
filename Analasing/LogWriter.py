import os.path


class LogWriter:
    def __init__(self, file_name):
        self.file_name = file_name
        if not os.path.isfile(file_name):
            raise FileNotFoundError()
        else:
            self.file = open(file_name, 'w')

    def write_log(self, message):
        self.file.write(message)
        self.file.close()


if __name__ == '__main__':
    lw = LogWriter('C:/Users/user/Desktop/test.txt')
    lw.write_log('test')
