package com.youandbbva.enteratv;

import com.youandbbva.enteratv.dao.WidgetDAO;
import com.youandbbva.enteratv.domain.BannerInfo;
import com.youandbbva.enteratv.domain.FileInfo;

/**
 * Generate HTML elements.
 *  
 * @author CJH
 * 
 */

public class HTML {
	
	/**
	 * Return quote string.
	 * 
	 * @param str
	 * @return string
	 */
	public static String quote(String str){
		return Constants.CSV_QUOTE + str + Constants.CSV_QUOTE; 
	}
	
	/**
	 * Like jquery attr.
	 * 
	 * @param property
	 * @param value
	 * @return string
	 */
	public static String attr(String property, String value){
		return property + "="+ quote(value); 
	}
	
	/**
	 * A href html.
	 * 
	 * @param link
	 * @return string
	 */
	public static String href(String link){
		if (link.length()==0)
			return attr("href", "#");
		
		return attr("href", link);
	}
	
	/**
	 * Return real media url.
	 * 
	 * @param kind Image, Video, etc.
	 * @param key
	 * @return path
	 */
	public static String media(String kind, String key){
		if (kind.equals("image") && key.length()==0)
			return Constants.BASEPATH.trim() + "resources/images/logo-bbva-tipo.svg";
		
		if (key.length()==0)
			return "";
		
		return Constants.BASEPATH.trim() + "serve?blob-key="+key; 
	}

	/**
	 * Generate banner html.
	 * 
	 * @param style
	 * @param banner
	 * @return html
	 */
	public static String generateBanner(String style, BannerInfo banner)
	{
		
		
		String result = "";
		String target="";
		String key="serve?blob-key=";
		String idbanner= banner.getImage();
		
		FileInfo file = new FileInfo();
		WidgetDAO widget = new WidgetDAO();
		
		String strmediaContent =""+widget.getMediaContent2(idbanner);
		
		
		if (banner.getTarget().equals("1"))
			target="_blank";
		
		if (style.length()>0)
			result += "<div " + HTML.attr("class", style) + ">";
		
		result += "<a " + href(banner.getUrl()) + ( target.length()==0 ? "" : attr("target", target) ) + ">";
		result += "<img " + attr("src", Constants.BASEPATH.trim()+ key +strmediaContent ) + " " + attr("alt", "") + ">";
		result += "</a>";
		
		if (style.length()>0)
			result += "</div>";
		
		return result;
	}
	
	/**
	 * Generate features image html.
	 * 
	 * @param image
	 * @return html
	 */
	public static String generateFeatures_Image(String image){
		String result = "";
		result += "<img " + attr("src", media("image", image)) + " " + attr("alt", "") + ">";
		return result;
	}
	
	/**
	 * Generate features title.
	 * 
	 * @param title
	 * @param link
	 * @return html
	 */
	public static String generateFeatures_Title(String title, String link){
		String result = "";
		result += "<h5>" + "<a " + href(link) + ">" + title + "</a>" + "</h5>";
		return result;
	}
	
	/**
	 * Generate features date html.
	 * 
	 * @param date
	 * @return html
	 */
	public static String generateFeatures_Date(String date){
		String result = "";
		result += "<small>" + date + "</small>";
		return result;
	}
	
	/**
	 * Generate features content html.
	 * 
	 * @param content
	 * @return html
	 */
	public static String generateFeatures_Content(String content){
		String result = "";
		result += "<div " + attr("id", "featured_news_content") + ">" + content + "</div>";
		return result;
	}
	
	/**
	 * Generate features read-more link html.
	 * 
	 * @param link
	 * @return html
	 */
	public static String generateFeatures_More(String link){
		String result = "";
		result += "<a " + attr("class", "read-more") + " " + href(link) +  ">Read more <i " + attr("class", "fa fa-angle-right fa-fw") + "></i></a>";
		return result;
	}
	
	/**
	 * Replace image-thumbnail to real video.
	 * 
	 * @param html
	 * @return string
	 */
	public static String replaceVideo(String html, String thumb) {
		String result = "";
		boolean have = false;
		String style="";
		
		System.out.println("Original :"+html);
		
		try{
			int index = html.indexOf("<img");
			int lastIndex = 0;
			
			if (index==-1)
				return html;
			
			while (index!=-1){
				result += html.substring(0, index);
				html = html.substring(index);
				
				int endIndex = html.indexOf(">");
				String img = html.substring(0, endIndex);
				if (img.indexOf("video")>-1){
					int start = img.indexOf("video=\"");
					int end = img.indexOf("\"", start+7);
					
					String key = img.substring(start+7, end);

					
					start = img.indexOf("style=\"");
					if (start>-1){
						end = img.indexOf("\"", start+7);
						style = img.substring(start+7, end);
						have = true;
					}

					int start_src = img.indexOf("src=\"");
					int end_src = img.indexOf("\"", start+5);

					String thumbnail = img.substring(start_src+5, end_src);
					
					int width=473;
					int height=245;
					if (!have){
						style="width:473px; height:245px;";
					}else{
						try{
							int s = style.indexOf("width");
							s += 5;
							int e = style.indexOf("px", s);
							width = (int)Utils.getDouble(style.substring(s, e).replaceAll(":", "").trim());
						}catch (Exception fdf){}
						
						try{
							int s = style.indexOf("height");
							s += 6;
							int e = style.indexOf("px", s);
							height = (int)Utils.getDouble(style.substring(s, e).replaceAll(":", "").trim());
						}catch (Exception fdf){}
						
						if (style.indexOf("%")>-1){
							style="width:473px; height:245px;";
						}
					}
//					width=473;
//					height=245;
					result += "<object type=\"application/x-shockwave-flash\" " +  
							 "data=\"/resources/swf/playerLite.swf\" " + 
							 "width=\""+width+"\" height=\""+height+"\" style=\"visibility: visible; "+style +"\">" +
							 "<param name=\"menu\" value=\"true\">"+
							 "<param name=\"allowfullscreen\" value=\"true\">"+
							 "<param name=\"allowscriptaccess\" value=\"always\">"+
							 "<param name=\"flashvars\" value=\"vidWidth="+width+"&vidHeight="+height+"&vidPath="+key+"&thumbPath="+thumbnail+"&plShowAtStart=true&plHideDelay=2000&autoPlay=false&autoLoop=false&watermark=hide&vidAspectRatio=fit&seekbar=show\"></object>" ;
					
				}else{
					result += html.substring(0, endIndex+1);
				}
				
				html = html.substring(endIndex+1);
				index = html.indexOf("<img");
			}
		}catch (Exception e){
		}
		
		result += html;
		System.out.println("Result :"+result);	
		String strCadenaAnterior= "\" style=&plShowAtStart=true&plHideDelay=2000&autoPlay";
		String strCadenaLimpia ="&plShowAtStart=true&plHideDelay=2000&autoPlay";
		String resultado="";
		
		resultado = result.replaceAll(strCadenaAnterior, strCadenaLimpia);
		System.out.println("resultado " + resultado);
		
		
		return resultado;
	}
	
	public static String replaceVideoForMobile(String html) {
		String result = "";
		boolean have = false;
		String style="";
		
		System.out.println("Original :"+html);
		
		try{
			int index = html.indexOf("<img");
			int lastIndex = 0;
			
			if (index==-1)
				return html;
			
			while (index!=-1){
				result += html.substring(0, index);
				html = html.substring(index);
				
				int endIndex = html.indexOf(">");
				String img = html.substring(0, endIndex);
				if (img.indexOf("video")>-1){
					int start = img.indexOf("video=\"");
					int end = img.indexOf("\"", start+7);
					
					String key = img.substring(start+7, end);
					
					start = img.indexOf("style=\"");
					if (start>-1){
						end = img.indexOf("\"", start+7);
						style = img.substring(start+7, end);
						have = true;
					}
					
					int width=400;
					int height=300;
					if (!have){
						style="width:400px; height:300px;";
					}else{
						try{
							int s = style.indexOf("width");
							s += 5;
							int e = style.indexOf("px", s);
							width = (int)Utils.getDouble(style.substring(s, e).replaceAll(":", "").trim());
						}catch (Exception fdf){}
						
						try{
							int s = style.indexOf("height");
							s += 6;
							int e = style.indexOf("px", s);
							height = (int)Utils.getDouble(style.substring(s, e).replaceAll(":", "").trim());
						}catch (Exception fdf){}
						
						if (style.indexOf("%")>-1){
							style="width:400px; height:300px;";
						}
					}
					
					result += "<video " + HTML.attr("src", HTML.media("video", key));
					result += " " + HTML.attr("style", style);
					result += " controls></video>";
					
				}else{
					result += html.substring(0, endIndex+1);
				}
				
				html = html.substring(endIndex+1);
				index = html.indexOf("<img");
			}
		}catch (Exception e){

		}
		
		result += html;
		System.out.println("Result :"+result);		
		
		return result;
	}
}
