# 问题分类模块 #


** 主要开发者： ** 贾延延


** 任务： **

设计一个问题的分类器，关于分词、词性标注、实体识别等可参考[Stanford Parser](http://nlp.stanford.edu/software/index.shtml)，其他工具如nltk亦可。

生成候选查询词，问题文本可作为查询词，实体的集合也可以作为查询词

程序运行
-----------

依赖[jieba](https://github.com/fxsjy/jieba), [sklearn](http://scikit-learn.org/), [numpy](www.numpy.org/)等python模块

运行命令为：python EntryPoint.py

在python 2.6版本未发现问题，暂不支持python 3

目前的分类准确率为：87% 左右
