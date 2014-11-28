"""
Question classifier
"""
import logging
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


class QClassifier:
    """
    A question classifier implementation
    """

    def __init__(self, model = 'Perceptron'):
        """
        Constructor
        """
        self.model = model
        # self.clf = MultinomialNB(alpha = .01)
        # self.clf = Perceptron(n_iter = 50)
        # self.clf = KNeighborsClassifier(n_neighbors=20)
        self.clf = LinearSVC(penalty="l1", dual=False, tol=1e-3)

    def train(self, train_x, train_y):
        """
        Training algorithm implementation
        """
        begin = time()
        # print train_y
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
        # print 1. * sum(arr) / len(pred)
        # print metrics.f1_score(test_y, pred)
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
