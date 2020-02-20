package com.ssafy.edu.service;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import lombok.extern.slf4j.Slf4j;

@Slf4j 
public class CrawlService {
	
	public String goCrawling(String repository) {
		String mdUrl = repository.replace("github.com", "raw.githubusercontent.com") + "/master/README.md";
		Document doc = null;
		String md = "";
		try {
			doc = Jsoup.connect(mdUrl).get();
			Elements element = doc.getElementsByTag("body").tagName("pre");
			md = element.text();
		} catch (IOException e) {
			md = "README.md 파일이 존재하지 않습니다.";
			log.error("goCrawling error {}",e);
		}
		
		return md;
	}
}