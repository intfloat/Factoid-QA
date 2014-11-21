# 答案抽取模块 #


** 主要开发者： ** 李琦


** 任务： **

根据问题的答案类型，以及检索得到的句子，抽出去候选答案集合
	
对候选答案设计一个ranking算法，排在第一位的作为最终答案

** 需要文件 **
stanford-postagger-3.3.1.jar
stanford-segmenter-3.4.1.jar

斯坦福分词数据
data\

斯坦福中文词性标注模型
models\chinese-distsim.tagger
models\chinese-distsim.tagger.props


依赖库
-------

<i>Java JDK 1.6 or higher<i>

<i>Jsoup</i>

<i>Stanford segmenter</i>

<i>Stanford postagger</i>