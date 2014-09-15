package com.tl.kernel.config;

public class Locale {
	private String language;
	private String country;
	private String variant;
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getVariant() {
		return variant;
	}
	public void setVariant(String variant) {
		this.variant = variant;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	
	public String toString(){
		return (new StringBuffer()
		.append("[language:").append(language)
		.append(",country:").append(country)
		.append(",variant:").append(variant)
		.append("]")
		).toString();
	}
}
