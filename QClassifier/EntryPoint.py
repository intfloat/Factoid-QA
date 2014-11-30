
import logging, sys
from sklearn.feature_extraction.text import HashingVectorizer
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.feature_extraction import FeatureHasher
from FeatureExtractor import *
from QClassifier import *
from numpy import asarray

if __name__ == '__main__':

    logging.basicConfig(level = logging.DEBUG,
                format='%(asctime)s %(filename)s[line:%(lineno)d] %(levelname)s %(message)s',
                datefmt='%a, %d %b %Y %H:%M:%S',
                filename='qclassifier.log',
                filemode='w')
    reload(sys)
    sys.setdefaultencoding('utf8')

    logging.info('start to extract features')
    extractor = FeatureExtractor()
    extractor.load(path = '../data/pair.xml')
    features = extractor.extract_features()
    labels = extractor.get_labels()
    assert(len(labels) == len(features))

    logging.info('split data into training data & test data')
    train_percentage = 0.2
    mid = int(len(features) * 0.2)
    test_x, train_x = features[:mid], features[mid:]
    test_y, train_y = labels[:mid], labels[mid:]
    vectorizer = FeatureHasher(input_type = 'string', non_negative = True)
    train_x = vectorizer.transform(train_x)
    test_x = vectorizer.transform(test_x)
    train_y = asarray(train_y)
    test_y = asarray(test_y)

    logging.info('start to train a classifier')
    clf = QClassifier(questions = extractor.questions)
    clf.train(train_x, train_y)
    clf.evaluate(test_x, test_y)
    logging.info('done')

