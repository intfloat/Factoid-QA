# -*- coding: utf-8 -*-
import sys
from xml.etree import ElementTree as ET

def merger(prefix, total = 300):
    """
    Take a list of files as input and merge them input a single big xml file
    """
    print '<?xml version="1.0" encoding="utf-8" standalone="no"?>'
    print '<QuestionSet>'
    for i in xrange(1, 300):
        reader = open(prefix + str(i) + '.xml', 'r')
        for line in reader:
            if line.startswith('<?xml version'):
                continue
            if line.startswith('<QuestionSet>'):
                continue
            if line.startswith('</QuestionSet>'):
                continue
            print line
        reader.close()
    print '</QuestionSet>'
    return

def bad_checker(file):
    """
    Check if any search result is empty due to http error
    """
    tree = ET.parse(file)
    root = tree.getroot()
    number = 0
    for question in root.getchildren():
        summary_cnt = len(question.findall('summary'))
        # print summary_cnt
        if summary_cnt == 0:
            print 'Question without summary: <ID,', question.get('id'), ',', question.findall('q')[0].text.strip(), '>'
            number += 1

    print '\nsummary for', number, 'questions missed.'
    return

def merge_structured_data(file):
    reader = open(file, 'r')
    cnt = 0
    cur = ''
    for line in reader:
        if cnt % 2 == 0:
            cur = line.strip()
            if not cur.endswith('?'):
                cur += '?'
        else:
            cur += line.strip()
            print cur
            cur = ''
        cnt += 1
    return

if __name__ == '__main__':
    # merge_structured_data('../OpenQA/tmp.txt')
    bad_checker('../data/stage3.xml')
    # merger('../data/out_baidu_')
    # for i in xrange(1, 301):
    #     name = '../data/out_baidu_' + str(i) + '.xml'
    #     print name
    #     try:
    #         bad_checker(name)
    #     except:
    #         continue
