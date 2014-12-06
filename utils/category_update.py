import sys
from xml.etree import ElementTree as ET
import xml.dom.minidom

def update_category(source = None, target = None):
    """
    In case question classifier has been updated,
    we should only change question category rather than
    crawl the whole web again.

    This script will do this job for us
    """
    s_tree = parse(source)
    s_root = s_tree.getroot()
    t_tree = parse(target)
    t_root = t_tree.getroot()

    for sq, tq in zip(s_root.getchildren(), t_root.getchildren()):
        s_cat = sq.findall('category')[0].text.strip()
        t_cat = tq.findall('category')[0].text.strip()
        if  s_cat != t_cat:
            tq.findall('category')[0].text = s_cat

    print ET.tostring(t_tree, encoding = 'utf-8')

if __name__ == '__main__':
    update_category(source = 'stage2.xml', target = 'stage3.xml')
