import feedparser
import BaseCollector
import datetime
import json


class RSSCollector(BaseCollector.BaseCollector):
    def __init__(self):
        super(RSSCollector, self).__init__()
        self.last_time = {}

        # 내부적으로는 json으로 저장
        self.url_list = {}

    def update_item(self, url):
        feed = feedparser.parse(url)

        # TODO: 오류 수정
        url_dict = {'feeds', dict()}

        for key in feed['entries']:
            feed_data = {'title': key['title'],
                         'publish': key.published,
                         'description': key['description'],
                         'category': key['category'],
                         'link': key['link']}

            print(feed_data)

            url_dict['feeds'].append(feed_data)

        self.last_time[self.url_normalization(url)] = datetime.datetime.now()

    @staticmethod
    def url_normalization(url):
        return url.lower()

    def get_csv(self):
        pass

    def get_json(self):
        pass


if __name__ == '__main__':
    rc = RSSCollector()
    rc.update_item('http://blog.rss.naver.com/uutak2000.xml')
