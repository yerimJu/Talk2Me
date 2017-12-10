import datetime


class Stopwatch:
    def __init__(self):
        self.running = False
        self.start_time = datetime.datetime.now()
        self.end_time = self.start_time
        pass

    def start(self):
        self.start_time = datetime.datetime.now()
        self.running = True

    def stop(self):
        self.end_time = datetime.datetime.now()
        self.running = False

    def reset(self):
        self.start_time = datetime.datetime.now()
        self.end_time = self.start_time
        self.running = False

    def elapsed_time(self):
        if self.running:
            return datetime.datetime.now() - self.start_time
        else:
            return self.end_time - self.start_time
