"""
Question classifier
"""
import logging, sys
from time import time
from sklearn.linear_model import RidgeClassifier
from sklearn.svm import LinearSVC
from sklearn.linear_model import SGDClassifier
from sklearn.linear_model import Perceptron
from sklearn.linear_model import PassiveAggressiveClassifier
from sklearn.naive_bayes import BernoulliNB, MultinomialNB
from sklearn.neighbors import KNeighborsClassifier
from sklearn.neighbors import NearestCentroid
from sklearn.utils.extmath import density
from sklearn import metrics
from FeatureExtractor import FeatureExtractor
from sklearn.feature_extraction.text import HashingVectorizer
from sklearn.feature_extraction import FeatureHasher
from numpy import asarray

class QClassifierImpl:
    """
    A wrapper for question classifier
    """

    def __init__(self, train_data_path, pred_qs = None):
        """
        Constructor
        """
        logging.basicConfig(level = logging.DEBUG,
                format='%(asctime)s %(filename)s[line:%(lineno)d] %(levelname)s %(message)s',
                datefmt='%a, %d %b %Y %H:%M:%S',
                filename='qclassifier.log',
                filemode='w')
        reload(sys)
        sys.setdefaultencoding('utf8')

        self.clf = None
        self.path = train_data_path
        self.pred_qs = pred_qs
        self.extractor = FeatureExtractor()
        self.features = None
        self.labels = None
        self.vectorizer = None
        self.cate = ['Person', 'Number', 'Location', 'Other']

    def train(self):
        """
        Train use all of the given data
        """
        self.extractor.load(path = self.path)
        self.features = self.extractor.extract_features()
        self.labels = self.extractor.get_labels()
        self.clf = QClassifier(questions = self.extractor.questions)
        assert(len(self.labels) == len(self.features))

        X = self.features
        Y = self.labels
        self.vectorizer = FeatureHasher(input_type = 'string', non_negative = True)
        X = self.vectorizer.transform(X)
        Y = asarray(Y)

        logging.info('start training')
        self.clf.train(X, Y)
        logging.info('done')

    def get_type(self, question):
        """
        Get type for a given question
        """
        if not self.features or not self.labels:
            logging.error('You need to train model first!')
            return None
        if not question:
            logging.error('Question should not be None')
            return None
        f = [self.extractor.extract_features_aux(question)]
        f = self.vectorizer.transform(f)
        # print self.clf.predict(f)
        return self.cate[self.clf.predict(f)[0]]

class QClassifier:
    """
    A question classifier implementation
    """

    def __init__(self, questions, model = 'Perceptron'):
        """
        Constructor
        """
        self.model = model
        self.questions = questions
        # self.clf = MultinomialNB(alpha = .01)
        # self.clf = Perceptron(n_iter = 50)
        # self.clf = KNeighborsClassifier(n_neighbors=20)
        self.clf = LinearSVC(penalty="l2", dual=False, tol=1e-3)

    def train(self, train_x, train_y):
        """
        Training algorithm implementation
        """
        begin = time()
        self.clf.fit(train_x, train_y)
        logging.info("train time: " + str(time() - begin) + " seconds.")

    def predict(self, test_x):
        """
        Predict label of a new sample given trained models
        """
        return self.clf.predict(test_x)

    def evaluate(self, test_x, test_y):
        """
        Evaluate performance of a trained model
        """
        pred = self.predict(test_x)
        arr = [1 for i, j in zip(pred, test_y) if i == j]
        mapping = ['Person', 'Number', 'Location', 'Other']
        # for i in xrange(len(pred)):
            # if pred[i] != test_y[i]:
            # print self.questions[i], ' correct:', mapping[test_y[i]], 'predict:', mapping[pred[i]]
        logging.info(metrics.classification_report(test_y, pred))

    def __benchmark(self, clf):
        pass

class L1LinearSVC(LinearSVC):

    def fit(self, X, y):
        # The smaller C, the stronger the regularization.
        # The more regularization, the more sparsity.
        self.transformer_ = LinearSVC(penalty="l1",
                                      dual=False, tol=1e-3)
        X = self.transformer_.fit_transform(X, y)
        return LinearSVC.fit(self, X, y)

    def predict(self, X):
        X = self.transformer_.transform(X)
        return LinearSVC.predict(self, X)
