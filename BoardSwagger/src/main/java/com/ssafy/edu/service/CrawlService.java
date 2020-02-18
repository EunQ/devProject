package com.ssafy.edu.service;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class CrawlService {
	
	public static String goCrawling(String repository) {
		String mdUrl = repository.replace("github.com", "raw.githubusercontent.com") + "/master/README.md";
		Document doc = null;
		try {
			doc = Jsoup.connect(mdUrl).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Elements element = doc.getElementsByTag("body").tagName("pre");
		String md = element.text();
//		System.out.println(str);
		return md;
	}
	
//	public static void main(String[] args) {
//		String repoString = "https://github.com/EunQ/devProject";
//		
//		goCrawling(repoString);
//	}
}
