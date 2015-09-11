package com.youandbbva.enteratv.gae;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.youandbbva.enteratv.Utils;

/**
 * Serve thumbnail image to client.
 * Returns URL.  
 * 		=s200 : 200*200 
 * 		=s150 : 150*150
 * it can select any pixel.
 *  
 * @author CJH
 *
 */

public class Image extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try {
			result.put("key", "");
		} catch (Exception e) {	}
		
		try{
			String blobKey = Utils.checkNull(req.getParameter("blob-key"));
			ImagesService imagesService = ImagesServiceFactory.getImagesService();
			String imageUrl = imagesService.getServingUrl(new BlobKey(blobKey));
			result.put("key", imageUrl);
		}catch (Exception ee){}
		
		res.setContentType("text/plain;charset=UTF-8");
		try {
			res.getWriter().print(result.toString());
		} catch (Exception e) {}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try {
			result.put("key", "");
		} catch (Exception e) {	}
		
		try{
			String blobKey = Utils.checkNull(req.getParameter("blob-key"));
			ImagesService imagesService = ImagesServiceFactory.getImagesService();
			String imageUrl = imagesService.getServingUrl(new BlobKey(blobKey));
			result.put("key", imageUrl);
		}catch (Exception ee){}
		
		res.setContentType("text/plain;charset=UTF-8");
		try {
			res.getWriter().print(result.toString());
		} catch (Exception e) {}
	}
}
