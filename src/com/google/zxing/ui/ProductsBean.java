package com.google.zxing.ui;
/**
 * @author - Vishal Joshi
 */
public class ProductsBean {
	private String data;
	private boolean matched;
	public boolean isMatched() {
		return matched;
	}
	public void setMatched(boolean matched) {
		this.matched = matched;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	private String content;

}
