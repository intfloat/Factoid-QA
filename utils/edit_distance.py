# -*- coding: utf-8 -*-
import sys, logging
from xml.etree import ElementTree as ET
from nltk.metrics.distance import edit_distance
import xml.dom.minidom

# f = codecs.open(filename, mode='w', encoding='utf-8')

def update_summary(qa_pairs, baidu_data):
    assert(qa_pairs != None)
    assert(baidu_data != None)

    # load qa pairs
    reader = open(qa_pairs, 'r')
    pairs = []
    for line in reader:
        # print line
        words = line.split(' ')
        first = words[0]
        second = words[-1]
        pairs.append([first, second])

    print 'number of pairs loaded:', len(pairs)
    s1 = u'辛弃疾的名作《永遇乐.京口北固亭怀古》中”凭谁问，廉颇老矣“的下一句是什么？'
    s2 = u'辛弃疾的名作《永遇乐.京口北固亭怀古》中”凭谁问，廉颇老矣“的下一句是什么?'
    print edit_distance(s1, s2)
    # print pairs[0][0]

    # parse tree and update its summary
    tree = ET.parse(baidu_data)
    root = tree.getroot()
    for question in root.getchildren():
        q_text = question.findall('q')[0].text.strip()
        cnt = 0
        for p in pairs:
            print edit_distance(q_text, p[0])
            if edit_distance(q_text, p[0]) < 3:
                question.remove('summary')
                ans = ET.SubElement(question, 'summary')
                ans.text = p[1]
                break
        break

    xml_string = ET.tostring(root, encoding = 'utf-8')
    result = xml.dom.minidom.parseString(xml_string)
    # print result.toprettyxml()
    return

if __name__ == '__main__':
    reload(sys)
    sys.setdefaultencoding('utf-8')
    print sys.getdefaultencoding()
    # update_summary(qa_pairs = '../data/qa_pairs.txt', baidu_data = '../data/baidu_crawler_data.xml')
    update_summary(qa_pairs = '../data/qa_pairs.txt', baidu_data = '../data/small.xml')
