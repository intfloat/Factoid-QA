package edu.pku.openqa.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class ExtractQuestion {
	
	private static final String Number = "Number";
	private static final String Location = "Location";
	private static final String Person = "Person";
	private static final String Other = "Other";

	/**
	 * @param args
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Scanner cin = null;
		File file = new File("sample.txt");
		try {
			cin = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println(cin.hasNextLine());
		String question = null;
//		String questionStyle = null;
//		String questionType = null;
		boolean found = false;
//		System.out.println("start");
		int cnt = 0;
		FileWriter writer = new FileWriter("out.txt");
		ArrayList<String> numbers = new ArrayList<String>();
		numbers.add("数量");
		numbers.add("代码");
		numbers.add("时间长度");
		numbers.add("时间");
		numbers.add("金额");
		numbers.add("序号");		
		numbers.add("年龄");
		numbers.add("其他物理度量");
		numbers.add("百分数");
		numbers.add("书号");
		numbers.add("频率");
		ArrayList<String> locations = new ArrayList<String>();
		locations.add("国家行政单位");
		locations.add("国家");
		locations.add("地址");
		locations.add("位置类");		
		locations.add("赢利性机构");
		locations.add("教育机构");
		locations.add("建筑物");
		locations.add("其他机构");
		ArrayList<String> person = new ArrayList<String>();
		person.add("名称");
		person.add("人物类");
		person.add("别称");
		ArrayList<String> others = new ArrayList<String>();
		others.add("歌词");
		others.add("植物");
		others.add("网址");
		others.add("文艺作品名");
		others.add("连续问题的答案");
		others.add("艺术术语");
		others.add("电视节目");
		others.add("信息类");
		others.add("事件");
		others.add("日常生活用品");
		others.add("数码产品及家电");
		others.add("动物");
		others.add("不确定类型");
		others.add("体育术语");
		others.add("交通运输工具");
		others.add("电台频道");
		others.add("剧情");
		others.add("军用器材");
		others.add("其它");
		others.add("食材");
		others.add("星座");
		others.add("语言");
		others.add("职业");
		others.add("其他术语");
		others.add("奖项");
		others.add("种族");
		others.add("曲谱");
		others.add("电脑术语");
		others.add("电视台");
		others.add("服装");
		others.add("网名");
		others.add("乐器");
		others.add("歌名");
		others.add("专辑名");
		others.add("非实体");
		others.add("品牌");
		writer.write("<QuestionSet>\n");
		while (cin.hasNextLine()) {
			String s = cin.nextLine().trim();
//			System.out.println(s);
			if (s.contains("<Question>")) {
				question = s;
			}
			else if (s.contains("<QuestionStyle>") && s.contains("factoid")) {
				found = true;
			}
			else if (s.contains("<AnswerType2>") && found) {
				String prev = s;
				if (s.contains("歌名")) { 
					found = false; 
					continue; 
				} 
				for (String t : numbers) s = s.replace(t, Number);
				for (String t : locations) s = s.replace(t, Location);
				for (String t : person) s = s.replace(t, Person);
				for (String t : others) s = s.replace(t, Other);
				s = s.replace("AnswerType2", "AnswerType");
				writer.write("<Q>\n");
				for (int i = 0; i < 8; ++i) writer.write(" ");
				writer.write(question + "\n");
				for (int i = 0; i < 8; ++i) writer.write(" ");
				writer.write(s + "\n");
				writer.write("</Q>\n");
				if (prev.equals(s)) System.out.println(prev);
//				System.out.println(question + "\t" + s);
				++cnt;
				found = false;
			}
		}
		writer.write("</QuestionSet>\n");
		writer.flush();
		System.out.println(cnt);
		return;
	}

}

