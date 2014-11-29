# -*- coding: utf-8 -*-
import sys, os

if __name__ == '__main__':
    reload(sys)
    sys.setdefaultencoding('utf-8')
    reader = open(sys.argv[1], 'r')
    cnt = 0
    for line in reader:
        if line.startswith('<doc '):
            begin = line.index('title=\"')
            end = line.index('\">', begin)
            title = line[(begin + 7):end]
            title = title.replace(u'/', '')
            title = title.replace(u'\\', '')
            print title
            dr = u'extracted_' + str(cnt / 10000)
            cnt += 1
            if not os.path.exists(dr):
                os.mkdir(dr)
            writer = open(dr + '/' + title, 'w')
        elif line.startswith('</doc>'):
            writer.flush()
            writer.close()
        else:
            line = line.replace(u'「', '')
            line = line.replace(u'」', '')
            line = line.replace(u'（）', '')
            line = line.replace(u'-{zh-hans:', '')
            while True:
                pos = line.find(u';zh-hant:')
                if pos > 0:
                    p2 = line.find(u'}-', pos)
                    if p2 < 0:
                        line = line.replace(u';zh-hant:', '')
                    else:
                        line = line[:pos] + line[p2 + len(u'}-'):]
                else:
                    break
            writer.write(line)
