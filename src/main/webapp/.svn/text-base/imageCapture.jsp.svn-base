<%@ page import="java.io.*"%>
<%@ page import="bsmedia.system.config.ApplicationProperty"%>
<%
	String image = request.getParameter("image");

	System.out.println("image : "+image);

	// size protection
	if(image==null || image.length()>100000) return;

	byte[] bytes = org.apache.commons.codec.binary.Base64.decodeBase64(image);
	if(bytes==null) return;

	String save = request.getParameter("save");
	String name = request.getParameter("name");
	String type = request.getParameter("type");
	if(save!=null && name!=null && ("JPG".equalsIgnoreCase(type) || "PNG".equalsIgnoreCase(type) )){
		//String webappRoot = pageContext.getServletContext().getRealPath("/");

		String webappRoot = pageContext.getServletContext().getRealPath("/");

		System.out.println("webappRoot : "+webappRoot);

		File folder = new File(webappRoot + "/klb_resources/capture/img/");
		File fileName = new File(folder, name + "." + type);
		FileOutputStream fos = new FileOutputStream(fileName);
		fos.write(bytes);
		fos.close();

		String preUri = ApplicationProperty.get("sso.url.root");

		out.println(preUri+"/klb_resources/capture/img/"+name+"."+type);
	}else{
		response.setContentType("image/jpeg");
		OutputStream os = response.getOutputStream();
		for(int i=0; i<bytes.length; i++){
			os.write(bytes[i]);
		}
		os.flush();
		os.close();
	}

%>
