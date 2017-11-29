import datetime

class Stopwatch:
    def __init__(self):
        self.start_time = datetime.datetime.now()
        self.end_time = datetime.datetime.now()
        pass

    def start(self):
        self.start_time = datetime.datetime.now()

    def elapsed_time(self):
        pass
