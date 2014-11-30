# -*- coding: utf-8 -*-
import jieba, logging
from xml.etree import ElementTree as et
import jieba.posseg as pseg
from numpy.random import shuffle

class FeatureExtractor:
    """
    Extract features from data
    """

    def __init__(self):
        """
        constructor
        """
        self.questions = []
        self.labels = []
        self.features = []
        self.mapping = {'Person':0, 'Number':1, 'Location':2, 'Other':3}

    def load(self, path = None):
        """
        load xml data from file specified by path parameter
        """
        try:
            tree = et.parse(path)
        except:
            logging.error('fail to parse xml from ' + path)
            return False
        root = tree.getroot()
        tp = []
        for question in root.getchildren():
            q = question.find('Question').text
            ans = self.mapping[question.find('AnswerType').text]
            tp.append((q, ans))
        shuffle(tp)
        for q, ans in tp:
            self.questions.append(q)
            self.labels.append(ans)
        return True

    def extract_features(self):
        """
        extract features for data
        """
        for question in self.questions:
            f = self.extract_features_aux(question)
            self.features.append(f)
        return self.features

    def extract_features_aux(self, question):
        """
        Auxiliary function for extracting features
        """
        f = []
        f.extend(self.__extract_word_feature(question))
        f.extend(self.__extract_pos_feature(question))
        return f

    def get_labels(self):
        """
        get labels for data
        """
        return self.labels

    def __extract_word_feature(self, question):
        """
        extract raw word features
        """
        words = jieba.cut(question)
        res = []
        for word in words:
            if len(word) == 0:
                continue
            if word == ' ' or word == u'？' or word == u'的' or word == u'是':
                continue
            # if word == u'得' or word == u'地' or word == '.':
                # continue
            res.append(word)
        return res

    def __extract_pos_feature(self, question):
        """
        extract POS features
        """
        ps = pseg.cut(question)
        return [w.flag for w in ps]

