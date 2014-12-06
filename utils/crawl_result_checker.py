import sys
from xml.etree import ElementTree as ET

def merger(path_prefix, total = 300):
    """
    Take a list of files as input and merge them input a single big xml file
    """
    print '<?xml version="1.0" encoding="utf-8" standalone="no"?>'
    print '<QuestionSet>'
    for i in xrange(1, 301):
        reader = open(prefix + str(i), 'r')
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
    for question in root.getchildren():
        summary_cnt = len(question.findall('summary'))
        # print summary_cnt
        if summary_cnt == 0:
            print 'Question without summary: <ID,', question.get('id'), ',', question.findall('q')[0].text, '>'
    return

if __name__ == '__main__':
    bad_checker('../data/out_baidu_193.xml')
