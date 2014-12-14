# Running script on Linux
rm edu/pku/retrieval/*.class

javac -encoding UTF-8 -classpath "lucene-core-4.10.2.jar:lucene-highlighter-4.10.2.jar:lucene-queryparser-4.10.2.jar:lucene-queries-4.10.2.jar:lucene-analyzers-common-4.10.2.jar:IKAnalyzer2012FF_u1.jar:." edu/pku/retrieval/IndexBuilder.java

# compile HighLighter.java
#javac -encoding UTF-8 -classpath "lucene-core-4.10.2.jar:lucene-highlighter-4.10.2.jar:lucene-queryparser-4.10.2.jar:lucene-queries-4.10.2.jar:lucene-analyzers-common-4.10.2.jar:IKAnalyzer2012FF_u1.jar:." edu/pku/retrieval/HighLighter.java

# Build index
java -classpath "lucene-core-4.10.2.jar:lucene-highlighter-4.10.2.jar:lucene-queryparser-4.10.2.jar:lucene-queries-4.10.2.jar:lucene-analyzers-common-4.10.2.jar:IKAnalyzer2012FF_u1.jar:." edu/pku/retrieval/IndexBuilder

# Run HighLighter
#java -classpath "lucene-core-4.10.2.jar:lucene-highlighter-4.10.2.jar:lucene-queryparser-4.10.2.jar:lucene-queries-4.10.2.jar:lucene-analyzers-common-4.10.2.jar:IKAnalyzer2012FF_u1.jar:." edu/pku/retrieval/HighLighter