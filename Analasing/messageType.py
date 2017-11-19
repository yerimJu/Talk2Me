from enum import Enum


class MessageType(Enum):
    Collect = 1
    UserRead = 2
    Unknown = 3


class ScrapSource(Enum):
    Facebook = 1
    Twitter = 2
    RSS = 3
    Gmail = 4
