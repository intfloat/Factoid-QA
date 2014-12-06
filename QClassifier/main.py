# -*- coding: utf-8 -*-
import sys, re, nltk
from QClassifier import QClassifierImpl
import jieba.analyse
from xml.etree import ElementTree
import xml.dom.minidom

reload(sys)
sys.setdefaultencoding('utf8')

testtree = ElementTree.parse("../data/testset.xml")
root = testtree.getroot()

buildroot = ElementTree.Element("QuestionSet")

clf = QClassifierImpl(train_data_path = '../data/pair.xml')
# clf = QClassifierImpl(train_data_path = 'train.xml')
clf.train()

for question in root.getchildren():
      q_text = question.getchildren()[0].text
      testtags = jieba.analyse.extract_tags(q_text, topK=5)
      testkeys = ' '.join(testtags)

      testquestion = ElementTree.SubElement(buildroot, 'question')
      testquestion.set('id', question.get('id'))
      # question text
      q = ElementTree.SubElement(testquestion, 'q')
      q.text = q_text

      # question category
      category = clf.get_type(q_text)
      cate_tree = ElementTree.SubElement(testquestion, 'category')
      cate_tree.text = category

      # query word
      query = ElementTree.SubElement(testquestion, 'query')
      query.text = testkeys
      # break

xml_string = ElementTree.tostring(buildroot, encoding = 'utf-8')
xml = xml.dom.minidom.parseString(xml_string)
print xml.toprettyxml()
