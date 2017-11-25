import feedparser
import BaseCollector


class RSSCollector(BaseCollector.BaseCollector):
    def __init__(self):
        super(RSSCollector, self).__init__()

    @staticmethod
    def print_test(url):
        feed = feedparser.parse(url)

        for key in feed['entries']:
            title = key['title']
            publish_date = key.published
            description = key['description']
            category = key['category']
            link = key['link']
            print(title, publish_date, category)

    def get_csv(self):
        pass

    def get_json(self):
        pass


if __name__ == '__main__':
    rc = RSSCollector()
    rc.print_test('http://blog.rss.naver.com/uutak2000.xml')
