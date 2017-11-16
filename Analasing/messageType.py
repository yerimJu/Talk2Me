from enum import Enum


class MessageType(Enum):
    Collect = 1
    UserRead = 2
    Unknown = 3
