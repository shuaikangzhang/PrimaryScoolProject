package com.primaryschool.home.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.primaryschool.global.config.PageSizeConfig;
import com.primaryschool.home.entity.DepartmentLinkContent;
import com.primaryschool.home.entity.Education;
import com.primaryschool.home.entity.Manage;
import com.primaryschool.home.entity.Party;
import com.primaryschool.home.entity.Student;
import com.primaryschool.home.entity.StudentLab;
import com.primaryschool.home.entity.Teacher;
import com.primaryschool.home.service.IDepartmentLinkService;
import com.primaryschool.home.service.IEducationService;
import com.primaryschool.home.service.ILabClassService;
import com.primaryschool.home.service.IManageService;
import com.primaryschool.home.service.IPartyService;
import com.primaryschool.home.service.IStudentService;
import com.primaryschool.home.service.ITeacherService;

/**
 * 
* @ClassName: ItemToJsonController
* @Description: TODO Json解析控制器
* @author Mingshan
* @date 2017年3月29日 上午8:59:23
*
 */

@Controller
@RequestMapping("/tojson")
public class ItemToJsonController<T> {
    @Autowired
    private IEducationService<Education> educationService;
    
    @Autowired
    private IStudentService<Student>  studentService;
     
    @Autowired
    private ITeacherService<Teacher>  teacherService;
    
    @Autowired
    private IPartyService<Party>  partyService;
    
    @Autowired
    private IManageService<Manage> manageService;
    
	@Autowired
	private ILabClassService<T> labClassService;
    
	@Autowired
	private IDepartmentLinkService<DepartmentLinkContent> departmentLinkService;
	    
	
    int position=0;
	int item_per_page=PageSizeConfig.HOME_INDEX_PAGESIZE;
	
	@RequestMapping("/education")
    public void EducationToJSON(String  flag,HttpServletResponse response){
		response.setCharacterEncoding("UTF-8");  
		PrintWriter out=null;
		
		System.out.println(flag);
		//根据类型获取信息
		ArrayList<Education>  education=(ArrayList<Education>)educationService.findEducationInfo(flag, position, item_per_page);
        //调用fastjson生成json信息
		String json = JSON.toJSONString(education, true);
		System.out.println(json);
		response.setContentType("application/json");
		try {
			out=response.getWriter();
			out.write(json);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			out.close();
		}
	}
	
	@RequestMapping("/student")
	public void StudentToJson(String  flag,HttpServletResponse response){
		response.setCharacterEncoding("UTF-8");  
		
		PrintWriter out=null;
		
		System.out.println(flag);
		//根据类型获取信息
		ArrayList<Student>  student=(ArrayList<Student>)studentService.findStudentInfo(flag, position, item_per_page);
        //调用fastjson生成json信息
		String json = JSON.toJSONString(student, true);
		System.out.println(json);
		response.setContentType("application/json");
		try {
			out=response.getWriter();
			out.write(json);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			out.close();
		}
	}
	
	@RequestMapping("/teacher")
	public void TeacherToJson(String  flag,HttpServletResponse response){
	    response.setCharacterEncoding("UTF-8");  
		
		PrintWriter out=null;
		
		System.out.println(flag);
		//根据类型获取信息
		ArrayList<Teacher>  teacher=(ArrayList<Teacher>)teacherService.findTeacherInfo(flag, position, item_per_page);
        //调用fastjson生成json信息
		String json = JSON.toJSONString(teacher, true);
		System.out.println(json);
		response.setContentType("application/json");
		try {
			out=response.getWriter();
			out.write(json);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			out.close();
		}
	}
	
	
	@RequestMapping("/party")
	public void PartyToJson(String  flag,HttpServletResponse response){
		response.setCharacterEncoding("UTF-8");  
			
		PrintWriter out=null;
		
		System.out.println(flag);
		//根据类型获取信息
		ArrayList<Party>  party=(ArrayList<Party>)partyService.findPartyInfo(flag, position, item_per_page);
        //调用fastjson生成json信息
		String json = JSON.toJSONString(party, true);
		System.out.println(json);
		response.setContentType("application/json");
		try {
			out=response.getWriter();
			out.write(json);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			out.close();
		}
	}
	
	/**
	 * 
	* @Title: ManageToJson
	* @Description: TODO 学校管理
	* @param @param flag
	* @param @param response    设定文件
	* @return void    返回类型
	* @throws
	 */
	@RequestMapping("/manage")
	public void ManageToJson(String  flag,HttpServletResponse response){
		response.setCharacterEncoding("UTF-8");  
		
		PrintWriter out=null;
		
		System.out.println(flag);
		//根据类型获取信息
		ArrayList<Manage>  manage=(ArrayList<Manage>)manageService.findManageInfo(flag, position, item_per_page); //调用fastjson生成json信息
		String json = JSON.toJSONString(manage, true);
		System.out.println(json);
		response.setContentType("application/json");
		try {
			out=response.getWriter();
			out.write(json);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			out.close();
		}
	}
	
	
	/**
	 * 
	* @Title: labClassToJson
	* @Description: TODO 综合实验课
	* @param @param response    设定文件
	* @return void    返回类型
	* @throws
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/labclass")
	public void labClassToJson(HttpServletResponse response){
		
		response.setCharacterEncoding("UTF-8");  
		
		PrintWriter out=null;
		ArrayList<StudentLab> labClass=(ArrayList<StudentLab>) labClassService.findLabClassListInfo();
		String json = JSON.toJSONString(labClass, true);
		System.out.println(json);
		response.setContentType("application/json");
		try {
			out=response.getWriter();
			out.write(json);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			out.close();
		}
	
	}
	
	
	
	
	/**
	 * 
	* @Title: JobPlacementToJSON
	* @Description: TODO  部门链接各部门信息加载
	* @param @param departmentNameType
	* @param @param flag
	* @param @param response    设定文件
	* @return void    返回类型
	* @throws
	 */
	@RequestMapping("/departmentLink")
    public void JobPlacementToJSON(int departmentId,String  flag,HttpServletResponse response){
		response.setCharacterEncoding("UTF-8");  
		PrintWriter out=null;
		
		System.out.println(flag);
		//根据类型id获取信息
		ArrayList<DepartmentLinkContent>  departmentlinkStaff=(ArrayList<DepartmentLinkContent>)departmentLinkService.findDepartmentLinkInfo(departmentId,flag, position, item_per_page);
        //调用fastjson生成json信息
		String json = JSON.toJSONString(departmentlinkStaff, true);
		System.out.println("下面开始打印json了========"+json);
		response.setContentType("application/json");
		try {
			out=response.getWriter();
			out.write(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			out.close();
		}
	}
}
