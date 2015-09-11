package com.youandbbva.enteratv.gae;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.youandbbva.enteratv.Utils;

/**
 * Serve media to client.
 * 
 * @author CJH
 *
 */
public class Serve extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		try{
		BlobKey blobKey = new BlobKey(req.getParameter("blob-key"));
		String name = Utils.encode(Utils.checkNull(req.getParameter("name")));
		if (name.length()>0)
			res.setHeader("Content-Disposition", "attachment; filename=" + name);
		blobstoreService.serve(blobKey, res);
		}catch (Exception e){}
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		try{
		BlobKey blobKey = new BlobKey(req.getParameter("blob-key"));
		String name = Utils.encode(Utils.checkNull(req.getParameter("name")));
		if (name.length()>0)
			res.setHeader("Content-Disposition", "attachment; filename=" + name);
		blobstoreService.serve(blobKey, res);
		}catch (Exception e){}
	}
}