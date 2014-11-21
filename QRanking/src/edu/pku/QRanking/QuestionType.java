package edu.pku.QRanking;

public enum QuestionType {

    NULL, PERSON_NAME, CONTRY_NAME,LOCATION_NAME, ORGANIZATION_NAME, NUMBER, TIME, DEFINITIION, OBJECT;

    public String getNature() {
        String nature = "unknown";
		//nr 人名
        //nr1 汉语姓氏
        //nr2 汉语名字
        //nrj 日语人名
        //nrf 音译人名
        if (QuestionType.PERSON_NAME == this) {
            nature = "nr";
        }
    	//ns 地名
        //nsf 音译地名
        if (QuestionType.LOCATION_NAME == this) {
            nature = "ns";
        }
        //nt 机构团体名
        if (QuestionType.ORGANIZATION_NAME == this) {
            nature = "nt";
        }
		//m 数词
        //mq 数量词
        if (QuestionType.NUMBER == this) {
            nature = "m";
        }
		//t 时间词
        //tg 时间词性语素
        if (QuestionType.TIME == this) {
            nature = "t";
        }

        return nature;
    }
}
