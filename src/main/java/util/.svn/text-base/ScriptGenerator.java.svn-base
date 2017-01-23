package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author macho 한동훈
 *	
 *	DB에 저장된 Table 기반으로 Ibatis Script, Controller, Service를
 *	자동으로 생성함
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class ScriptGenerator {
	
	//DB
	static String url 				= "jdbc:oracle:thin:@122.99.198.175:1521:saga";
	static String user				= "sagaportal";
	static String password 			= "Saga2012";
	
	//Table, Namespace, URL Constant
	//
	//  Example) 
	//  
	//    Table Name	: TKS_INDVMEMODIRY
	//    Prefix		: IndvMemoDiry
	//
	static String cTable;
	static String cPrefixUpper;
	static String cPrefixLower;
	static String cPrefixURL;
	
	//Tab Constant
	static String t1 				= "\t";
	static String t2 				= "\t\t";
	static String t3 				= "\t\t\t";
	static String t4 				= "\t\t\t\t";
	static String t5 				= "\t\t\t\t\t";
	
	//Static String Buffer
	static String sBuffer			= "";
	
	//Command Type Constant
	final static int _CLEAR			= 0;
	final static int _IBATIS		= 1;
	final static int _CONTROLLER	= 2;
	final static int _SERVICE		= 3;
	
	public static void main(String[] args) throws Exception {
		ScriptGenerator script = new ScriptGenerator();
		
		//Generator Initialization
		script.dispInit();
		script.save(_CLEAR);
				
		//Ibatis Script
		disp("<sqlMap namespace=\""+cPrefixLower+"\">\n");
		script.dispList();
		script.dispDetail();
		script.dispInsert();
		script.dispUpdate();
		script.dispDelete();
		disp("</sqlMap>");
		script.save(_IBATIS);

		
		//Controller
		script.dispController();
		script.save(_CONTROLLER);
		
		//Service
		script.dispService();
		script.save(_SERVICE);
		
		System.out.println(sBuffer);
	}
	
	public void dispInit() throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		//Variable Initialization
		System.out.print("Table Name\t : ");
		cTable = br.readLine();
		System.out.print("Prefix\t\t : ");
		cPrefixUpper = br.readLine();
		cPrefixLower = (cPrefixUpper.substring(0, 1).toLowerCase()) + cPrefixUpper.substring(1);
		cPrefixURL = cPrefixUpper.toLowerCase();
		
		disp("\n");
		
		//Setup Information
		disp("[ Ibatis, Controller, Service Generator ]");
		disp("");
		disp("   Table Name\t : "+cTable);
		disp("   PrefixUpper\t : "+cPrefixUpper);
		disp("   PrefixLower\t : "+cPrefixLower);
		disp("   PrefixURL\t : "+cPrefixURL);
		
		disp("\n\n");
	}
	
	public void dispList() throws Exception{
		ScriptGenerator script = new ScriptGenerator();
		ArrayList<String> list = (script.getAllFields(cTable).get("field"));
		ArrayList<String> pk = (script.getAllFields(cTable).get("pk"));
		
		//SQL
		disp(t1+"<sql id=\"select"+cPrefixUpper+"ListSql\">");

			disp(t2+"SELECT");
		
				disp(t3+list.get(0));
				for(int i=1;i<list.size();i++){
					disp(t3+","+list.get(i));
				}
		
			disp(t2+"FROM");
		
				disp(t3+cTable);
		
			disp(t2+"WHERE");
				
				disp(t3+pk.get(0) + " = #"+pk.get(0)+"#");
				for(int i=1;i<pk.size();i++){
					disp(t3+"AND "+pk.get(i) + " = #"+pk.get(i)+"#");
				}
		
		disp(t1+"</sql>");
		
		
		//List
		disp(t1+"<select id=\"select"+cPrefixUpper+"List\" parameterClass=\"Map\" resultClass=\"HashMap\">");
			disp(t2+"<include refid=\"common.beforePaginatedList\"/>");
			disp(t2+"<include refid=\""+cPrefixLower+".select"+cPrefixUpper+"ListSql\"/>");
			disp(t2+"<include refid=\"common.afterPaginatedList\"/>");
		disp(t1+"</select>");
		
		//List Count
		disp(t1+"<select id=\"select"+cPrefixUpper+"ListCount\" parameterClass=\"Map\" resultClass=\"Integer\">");
			disp(t2+"<include refid=\"common.beforeCountPaginatedList\"/>");
			disp(t2+"<include refid=\""+cPrefixLower+".select"+cPrefixUpper+"ListSql\"/>");
			disp(t2+"<include refid=\"common.afterCountPaginatedList\"/>");
		disp(t1+"</select>");
		
		disp("\n");
	}
	
	public void dispDetail() throws Exception{
		ScriptGenerator script = new ScriptGenerator();
		ArrayList<String> list = (script.getAllFields(cTable).get("field"));
		ArrayList<String> pk = (script.getAllFields(cTable).get("pk"));
		
		//Detail
		disp(t1+"<select id=\"select"+cPrefixUpper+"Detail\" parameterClass=\"Map\" resultClass=\"HashMap\">");

			disp(t2+"SELECT");
		
				disp(t3+list.get(0));
				for(int i=1;i<list.size();i++){
					disp(t3+","+list.get(i));
				}
		
			disp(t2+"FROM");
		
				disp(t3+cTable);
		
			disp(t2+"WHERE");
				
				disp(t3+pk.get(0) + " = #"+pk.get(0)+"#");
				for(int i=1;i<pk.size();i++){
					disp(t3+"AND "+pk.get(i) + " = #"+pk.get(i)+"#");
				}
		
		disp(t1+"</select>");
		
		disp("\n");
	}
	
	public void dispInsert() throws Exception{
		ScriptGenerator script = new ScriptGenerator();
		ArrayList<String> list = (script.getAllFields(cTable).get("field"));
		
		//Insert
		disp(t1+"<insert id=\"insert"+cPrefixUpper+"Detail\" parameterClass=\"Map\">");

			disp(t2+"INSERT INTO "+cTable+" (");
		
				disp(t3+list.get(0));
				for(int i=1;i<list.size();i++){
					if(list.get(i).indexOf("FST_REG") != -1
					|| list.get(i).indexOf("FNL_UPD") != -1
					|| list.get(i).indexOf("FNL_AGT") != -1) continue;
					disp(t3+","+list.get(i));
				}
				disp(t3+",FST_REG_IP");
				disp(t3+",FST_REG_PN_ID");
				disp(t3+",FST_REG_DT");
				disp(t3+"<isNotEmpty property=\"AGENT_ID\">");
				disp(t3+",FNL_AGT_IP");
				disp(t3+",FNL_AGT_PN_ID");
				disp(t3+",FNL_AGT_DT");
				disp(t3+"</isNotEmpty>");
		
			disp(t2+") VALUES (");
		
				disp(t3+"#"+list.get(0)+"#");
				for(int i=1;i<list.size();i++){
					if(list.get(i).indexOf("FST_REG") != -1
					|| list.get(i).indexOf("FNL_UPD") != -1
					|| list.get(i).indexOf("FNL_AGT") != -1) continue;
					disp(t3+",#"+list.get(i)+"#");
				}
				disp(t3+",#USER_IP#");
				disp(t3+",#USER_ID#");
				disp(t3+",SYSDATE");
				disp(t3+"<isNotEmpty property=\"AGENT_ID\">");
				disp(t3+",#USER_IP#");
				disp(t3+",#AGENT_ID#");
				disp(t3+",SYSDATE");
				disp(t3+"</isNotEmpty>");
		
			disp(t2+")");
				
		disp(t1+"</insert>");
		
		disp("\n");
	}
	
	public void dispUpdate() throws Exception{
		ScriptGenerator script = new ScriptGenerator();
		ArrayList<String> list = (script.getAllFields(cTable).get("field"));
		ArrayList<String> pk = (script.getAllFields(cTable).get("pk"));
		
		//Update
		disp(t1+"<update id=\"update"+cPrefixUpper+"Detail\" parameterClass=\"Map\">");

			disp(t2+"UPDATE "+cTable);
			disp(t2+"SET");
		
				disp(t3+list.get(0)+" = #"+list.get(0)+"#");
				for(int i=1;i<list.size();i++){
					if(list.get(i).indexOf("FST_REG") != -1
					|| list.get(i).indexOf("FNL_UPD") != -1
					|| list.get(i).indexOf("FNL_AGT") != -1) continue;
					disp(t3+","+list.get(i)+" = #"+list.get(i)+"#");
				}
				disp(t3+",FNL_UPD_IP = #USER_IP#");
				disp(t3+",FNL_UPD_PN_ID = #USER_ID#");
				disp(t3+",FNL_UPD_DT = SYSDATE");
				disp(t3+"<isNotEmpty property=\"AGENT_ID\">");
				disp(t3+",FNL_AGT_IP = #USER_IP#");
				disp(t3+",FNL_AGT_PN_ID = #AGENT_ID#");
				disp(t3+",FNL_AGT_DT = SYSDATE");
				disp(t3+"</isNotEmpty>");
		
			disp(t2+"WHERE");
				
				disp(t3+pk.get(0) + " = #"+pk.get(0)+"#");
				for(int i=1;i<pk.size();i++){
					disp(t3+"AND "+pk.get(i) + " = #"+pk.get(i)+"#");
				}
				
		disp(t1+"</update>");
		
		disp("\n");
	}
	
	public void dispDelete() throws Exception{
		ScriptGenerator script = new ScriptGenerator();
		ArrayList<String> list = (script.getAllFields(cTable).get("field"));
		ArrayList<String> pk = (script.getAllFields(cTable).get("pk"));
		
		//Delete
		if(list.toString().indexOf("DEL_YN") == -1){
			
			disp(t1+"<delete id=\"delete"+cPrefixUpper+"Detail\" parameterClass=\"Map\">");

				disp(t2+"DELETE FROM "+cTable);
				disp(t2+"WHERE");
					
					disp(t3+pk.get(0) + " = #"+pk.get(0)+"#");
					for(int i=1;i<pk.size();i++){
						disp(t3+"AND "+pk.get(i) + " = #"+pk.get(i)+"#");
					}
				
			disp(t1+"</delete>");
			
		}else{
			
			disp(t1+"<update id=\"delete"+cPrefixUpper+"Detail\" parameterClass=\"Map\">");

				disp(t2+"UPDATE "+cTable);
				disp(t2+"SET");
				
					disp(t3+"DEL_YN = 'Y'");
					
				disp(t2+"WHERE");
				
					disp(t3+pk.get(0) + " = #"+pk.get(0)+"#");
					for(int i=1;i<pk.size();i++){
						disp(t3+"AND "+pk.get(i) + " = #"+pk.get(i)+"#");
					}
				
			disp(t1+"</update>");
			
		}
		
		disp("\n");
	}
	
	public void dispController(){
		disp("\n");
		
		disp("public class "+cPrefixUpper+"Controller {");
		
		disp("");
		
			disp(t1+"private static final Logger logger = LoggerFactory.getLogger("+cPrefixUpper+"Controller.class);\n");
		
			disp(t1+"@Autowired");
			disp(t1+""+cPrefixUpper+"Service "+cPrefixLower+"Service;");
			
		disp("");
		
			disp(t1+"@Autowired");
			disp(t1+"AsyncResponseMap responseMap;");
			
		disp("");
		
			disp(t1+"@RequestMapping(\"/"+cPrefixURL+"/"+cPrefixURL+"List.do\")");
			disp(t1+"public void "+cPrefixURL+"List(@RequestParam Map paramMap,ModelMap model){");
				disp(""+t2+"model.put(\"results\", "+cPrefixLower+"Service.select"+cPrefixUpper+"List(paramMap));");
			disp(t1+"}");
			
		disp("");
		
			disp(t1+"@RequestMapping(\"/"+cPrefixURL+"/"+cPrefixURL+"Detail.do\")");
			disp(t1+"public void "+cPrefixURL+"Detail(@RequestParam Map paramMap,ModelMap model){");
				disp(""+t2+"model.put(\"result\", "+cPrefixLower+"Service.select"+cPrefixUpper+"Detail(paramMap));");
			disp(t1+"}");
			
		disp("");
		
			disp(t1+"@RequestMapping(\"/"+cPrefixURL+"/"+cPrefixURL+"Form.do\")");
			disp(t1+"public void "+cPrefixURL+"Form(@RequestParam Map paramMap,ModelMap model){");
				disp(""+t2+"model.put(\"result\", "+cPrefixLower+"Service.select"+cPrefixUpper+"Detail(paramMap));");
			disp(t1+"}");
			
		disp("");
		
			disp(t1+"@RequestMapping(\"/"+cPrefixURL+"/"+cPrefixURL+"Insert.do\")");
			disp(t1+"public @ResponseBody Map "+cPrefixURL+"Insert(@RequestParam Map paramMap){");
				disp(""+t2+""+cPrefixLower+"Service.insert"+cPrefixUpper+"Detail(paramMap);");
				disp(""+t2+"return responseMap.setSaveOkMessage().setUrl(\"/"+cPrefixURL+"/"+cPrefixURL+"List.do\");");
			disp(t1+"}");
			
		disp("");
		
			disp(t1+"@RequestMapping(\"/"+cPrefixURL+"/"+cPrefixURL+"Modify.do\")");
			disp(t1+"public @ResponseBody Map "+cPrefixURL+"Modify(@RequestParam Map paramMap){");
				disp(""+t2+""+cPrefixLower+"Service.update"+cPrefixUpper+"Detail(paramMap);");
				disp(""+t2+"return responseMap.setModifyOkMessage().setUrl(\"/"+cPrefixURL+"/"+cPrefixURL+"List.do\");");
			disp(t1+"}");
			
		disp("");
		
			disp(t1+"@RequestMapping(\"/"+cPrefixURL+"/"+cPrefixURL+"Delete.do\")");
			disp(t1+"public @ResponseBody Map "+cPrefixURL+"Delete(@RequestParam Map paramMap){");
				disp(""+t2+""+cPrefixLower+"Service.delete"+cPrefixUpper+"Detail(paramMap);");
				disp(""+t2+"return responseMap.setDeleteOkMessage().setUrl(\"/"+cPrefixURL+"/"+cPrefixURL+"List.do\");");
			disp(t1+"}");
			
		disp("}");
	}
	
	public void dispService() throws Exception{
		ScriptGenerator script = new ScriptGenerator();
		ArrayList<String> list = (script.getAllFields(cTable).get("field"));
		String deleteType;
		
		if(list.toString().indexOf("DEL_YN") == -1){
			deleteType = "delete";
		}else{
			deleteType = "update";
		}
		
		disp("\n");
		
		disp("public class "+cPrefixUpper+"Service {");
		
		disp("");
		
			disp(t1+"private static final Logger logger = LoggerFactory.getLogger("+cPrefixUpper+"Service.class);\n");
		
			disp(t1+"@Autowired");
			disp(t1+"CommonDao dao;");
			
		disp("");
		
			disp(t1+"public PaginatedList select"+cPrefixUpper+"List(Map paramMap){");
				disp(""+t2+"return dao.queryForPaginatedList(\""+cPrefixLower+".select"+cPrefixUpper+"List\", paramMap);");
			disp(t1+"}");
			
		disp("");
		
			disp(t1+"public Map select"+cPrefixUpper+"Detail(Map paramMap){");
				disp(""+t2+"return dao.queryForMap(\""+cPrefixLower+".select"+cPrefixUpper+"Detail\", paramMap);");
			disp(t1+"}");
			
		disp("");
		
			disp(t1+"public void insert"+cPrefixUpper+"Detail(Map paramMap){");
				disp(""+t2+"dao.insert(\""+cPrefixLower+".insert"+cPrefixUpper+"Detail\", paramMap);");
			disp(t1+"}");
			
		disp("");
		
			disp(t1+"public void update"+cPrefixUpper+"Detail(Map paramMap){");
				disp(""+t2+"dao.update(\""+cPrefixLower+".update"+cPrefixUpper+"Detail\", paramMap);");
			disp(t1+"}"+t1+"");
			
		disp("");
		
			disp(t1+"public void delete"+cPrefixUpper+"Detail(Map paramMap){");
				disp(""+t2+"dao."+deleteType+"(\""+cPrefixLower+".delete"+cPrefixUpper+"Detail\", paramMap);");
			disp(t1+"}");
			
		disp("}");
		
		disp("\n");
	}
	
	public Map<String, ArrayList<String>> getAllFields(String table) throws Exception {
	    ArrayList<String> allFields = new ArrayList<String>();
	    ArrayList<String> allPKs = new ArrayList<String>();
	    Map result = new HashMap();
	    
	    Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection dbConnection = DriverManager.getConnection(url, user, password);
	    
	    try {
	        DatabaseMetaData myDatabaseMetaData = dbConnection.getMetaData();
	        ResultSet columnsResultSet = myDatabaseMetaData.getColumns(null, null, table, null);
	        ResultSet primaryKeysSet = myDatabaseMetaData.getPrimaryKeys(null, null, table);

	        while (columnsResultSet.next()) { allFields.add(columnsResultSet.getString("COLUMN_NAME")); }
	        while (primaryKeysSet.next()) { allPKs.add(primaryKeysSet.getString("COLUMN_NAME")); }
	        
	        result.put("field", allFields);
	        result.put("pk", allPKs);
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return result;
	}
	
	public static void disp(Object obj){
		System.out.println(obj);
		
		sBuffer += (String)obj + "\n";
	}
	
	public void save(int type) throws Exception{
		File file;
		FileOutputStream output;
		OutputStreamWriter writer;
		BufferedWriter fBuffer;
		String lBuffer = "";
		
		File dir = new File("c:/script");
		if(!dir.exists()) dir.mkdir();
		
		switch(type){
			case _IBATIS :
				file = new File("c:/script/"+cPrefixLower+".xml");
				
				if(file.exists()) file.delete();
				
				output = new FileOutputStream("c:/script/"+cPrefixLower+".xml",true);
				writer = new OutputStreamWriter(output);
				fBuffer = new BufferedWriter(writer);
				
				lBuffer += "<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n";
				lBuffer += "<!DOCTYPE sqlMap PUBLIC \"-//ibatis.apache.org//DTD SQL Map 2.0//EN\" \"http://ibatis.apache.org/dtd/sql-map-2.dtd\">\n\n";
				
				lBuffer += sBuffer;
				
				fBuffer.write(lBuffer, 0 , lBuffer.length());
				fBuffer.flush();
			
				//Static Buffer Clear
				sBuffer = "";
			break;
			
			case _CONTROLLER :
				file = new File("c:/script/"+cPrefixUpper+"Controller.java");
				
				if(file.exists()) file.delete();
				
				output = new FileOutputStream("c:/script/"+cPrefixUpper+"Controller.java",true);
				writer = new OutputStreamWriter(output);
				fBuffer = new BufferedWriter(writer);
				
				lBuffer += "package jp.go.saga.controller.portal;\n\n";

				lBuffer += "import java.util.Map;\n\n";

				lBuffer += "import jp.go.saga.common.AsyncResponseMap;\n";
				lBuffer += "import jp.go.saga.service.portal."+cPrefixUpper+"Service;\n\n";

				lBuffer += "import org.slf4j.Logger;\n";
				lBuffer += "import org.slf4j.LoggerFactory;\n";
				lBuffer += "import org.springframework.beans.factory.annotation.Autowired;\n";
				lBuffer += "import org.springframework.stereotype.Controller;\n";
				lBuffer += "import org.springframework.ui.ModelMap;\n";
				lBuffer += "import org.springframework.web.bind.annotation.RequestMapping;\n";
				lBuffer += "import org.springframework.web.bind.annotation.RequestParam;\n";
				lBuffer += "import org.springframework.web.bind.annotation.ResponseBody;\n\n";

				lBuffer += "@Controller\n";
				lBuffer += "@SuppressWarnings({\"rawtypes\"})";
				
				lBuffer += sBuffer;
				
				fBuffer.write(lBuffer, 0 , lBuffer.length());
				fBuffer.flush();
				
				//Static Buffer Clear
				sBuffer = "";
			break;
			
			case _SERVICE :
				file = new File("c:/script/"+cPrefixUpper+"Service.java");
				
				if(file.exists()) file.delete();
				
				output = new FileOutputStream("c:/script/"+cPrefixUpper+"Service.java",true);
				writer = new OutputStreamWriter(output);
				fBuffer = new BufferedWriter(writer);
				
				lBuffer += "package jp.go.saga.service.portal;\n\n";

				lBuffer += "import java.util.Map;\n\n";

				lBuffer += "import jp.go.saga.common.repository.CommonDao;\n\n";

				lBuffer += "import org.slf4j.Logger;\n";
				lBuffer += "import org.slf4j.LoggerFactory;\n";
				lBuffer += "import org.springframework.beans.factory.annotation.Autowired;\n";
				lBuffer += "import org.springframework.stereotype.Service;\n\n";

				lBuffer += "import bsmedia.system.ibatis.PaginatedList;\n\n";

				lBuffer += "@Service\n";
				lBuffer += "@SuppressWarnings({\"rawtypes\"})";
				
				lBuffer += sBuffer;
				
				fBuffer.write(lBuffer, 0 , lBuffer.length());
				fBuffer.flush();
				
				//Static Buffer Clear
				sBuffer = "";
			break;
			
			case _CLEAR :
				//Static Buffer Clear
				sBuffer = "";
			break;
		}
	}
	
}
