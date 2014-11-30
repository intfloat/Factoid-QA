# -*- coding: utf-8 -*-
from QClassifier import QClassifierImpl

# clf = QClassifierImpl(train_data_path = 'train.xml')
clf = QClassifierImpl(train_data_path = '../data/pair.xml')
clf.train()
print clf.get_type(u'西班牙的首都是哪座城市？')
print clf.get_type(u'飞轮海中的吴尊现在多大了？')
print clf.get_type(u'小美指的是哪位明星？')
print clf.get_type(u'冯小刚的现任妻子是谁？')
print clf.get_type(u'哪场战役代表美国正式卷入第二次世界大战?')
print clf.get_type(u'《三国演义》中“官渡之战”中交战双方的指挥官分别是?')
print clf.get_type(u'唱感动天感动地的人是那个歌手?')
