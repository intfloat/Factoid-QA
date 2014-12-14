
rm edu/pku/QRanking/*.class
rm edu/pku/QRanking/*/*.class
javac -encoding UTF-8 -classpath "jsoup-1.8.1.jar:seg.jar:stanford-postagger-3.2.0.jar:." edu/pku/QRanking/AnswerExtraction.java

echo 'run ranking algorithm on wikipedia data...'
java -classpath "jsoup-1.8.1.jar:seg.jar:stanford-postagger-3.2.0.jar:." -jar QRanking stage3.xml stage4_wiki.txt > out.txt 2>&1 &


echo 'run ranking algorithm on crawler data...'
java -classpath "jsoup-1.8.1.jar:seg.jar:stanford-postagger-3.2.0.jar:." -jar QRanking baidu_crawler_data_v5.xml stage4_open.txt > out.txt 2>&1 &

