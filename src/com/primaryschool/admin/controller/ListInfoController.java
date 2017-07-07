package com.primaryschool.admin.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.primaryschool.admin.entity.CourseScore;
import com.primaryschool.admin.entity.CourseStudentInfo;
import com.primaryschool.admin.entity.CourseType;
import com.primaryschool.admin.entity.FileBean;
import com.primaryschool.admin.service.IAdminCourseScoreService;
import com.primaryschool.admin.service.IAdminCultureService;
import com.primaryschool.admin.service.IAdminDepartmentService;
import com.primaryschool.admin.service.IAdminEducationService;
import com.primaryschool.admin.service.IAdminHeaderMasterService;
import com.primaryschool.admin.service.IAdminIndexImagesService;
import com.primaryschool.admin.service.IAdminLabClassService;
import com.primaryschool.admin.service.IAdminManageService;
import com.primaryschool.admin.service.IAdminPartyService;
import com.primaryschool.admin.service.IAdminSclassService;
import com.primaryschool.admin.service.IAdminStuInfoService;
import com.primaryschool.admin.service.IAdminStudentService;
import com.primaryschool.admin.service.IAdminTeacherService;
import com.primaryschool.admin.service.IAdminTeachingResourceService;
import com.primaryschool.admin.service.IAdminTrendsService;
import com.primaryschool.admin.service.ICampusSceneryService;
import com.primaryschool.admin.service.IClassStyleService;
import com.primaryschool.global.config.PageSizeConfig;
import com.primaryschool.home.entity.CampusScenery;
import com.primaryschool.home.entity.ClassHomePage;
import com.primaryschool.home.entity.ClassStyle;
import com.primaryschool.home.entity.ClassSynopsis;
import com.primaryschool.home.entity.Culture;
import com.primaryschool.home.entity.DepartmentLinkContent;
import com.primaryschool.home.entity.DepartmentLinkNameList;
import com.primaryschool.home.entity.Education;
import com.primaryschool.home.entity.Grade;
import com.primaryschool.home.entity.HeadMaster;
import com.primaryschool.home.entity.IndexImages;
import com.primaryschool.home.entity.Manage;
import com.primaryschool.home.entity.Party;
import com.primaryschool.home.entity.Sclass;
import com.primaryschool.home.entity.Student;
import com.primaryschool.home.entity.StudentLab;
import com.primaryschool.home.entity.StudentLabMenuContent;
import com.primaryschool.home.entity.StudentLabMenuIntroduce;
import com.primaryschool.home.entity.Teacher;
import com.primaryschool.home.entity.TeachingResourcesClass;
import com.primaryschool.home.entity.TeachingResourcesContent;
import com.primaryschool.home.entity.TeachingResourcesMenu;
import com.primaryschool.home.entity.Trends;
import com.primaryschool.home.service.IBaseFileService;
import com.primaryschool.home.service.IGradeService;
import com.primaryschool.home.service.ILabClassService;
import com.primaryschool.home.service.IPageHelperService;
import com.primaryschool.home.service.ISclassService;
import com.primaryschool.home.service.ITeachingResourcesService;
import com.primaryschool.home.service.ITypeFlagToTypeNameService;

/**
 * 
* @ClassName: ListInfoController
* @Description: TODO 获取列表
* @author Mingshan
* @date 2017年4月14日 下午3:53:42
*
 */

@Controller
@RequestMapping("/admin/list")
public class ListInfoController<T> {


    @Autowired
    private IAdminIndexImagesService<T> indexImagesService;
    

    
    @Autowired
    private ICampusSceneryService<T> campusSceneryService;
    
	@Autowired
	private IAdminCultureService<T> cultureService;

    @Autowired
    private  IAdminTrendsService<T> trendsService;
	
    @Autowired
    private IAdminEducationService<T> educationService;
    
    @Autowired
    private IAdminStudentService<T> studentService;
    
    @Autowired
    private IGradeService<Grade> gradeService;
	//前台Service,用于classList.jsp页面
	@Autowired
	private ISclassService<Sclass> sclassService;
	//后台Service,用于后台班级主页管理
	@Autowired
	private IAdminSclassService<T> classService;
    
    @Autowired
    private IAdminTeacherService<T> teacherService;
    
    @Autowired
    private IAdminPartyService<T> partyService;
    
    @Autowired
    private IAdminDepartmentService<T> departmentService;
    
    @Autowired
    private IAdminManageService<T> manageService;
    
    @Autowired
	private IAdminTeachingResourceService<T> teachingResourcesService;
    
    @Autowired
    private IAdminLabClassService<T> labClassService;
    
    @Autowired
    private IPageHelperService pageHelperService;
    
    @Autowired
    private ITypeFlagToTypeNameService typeFlagToTypeNameService;
    
	//获取所有的resource类型
    @Autowired
	private ITeachingResourcesService<T> teachingResources;
    //获取所有的lab类型
    @Autowired
	private ILabClassService<T> labService;
    
    @Autowired
    private IAdminHeaderMasterService<T> headerMasterService;
    

    @Autowired
    private IAdminStuInfoService<T> stuInfoService;
    
    @Autowired
    private IAdminCourseScoreService<T> courseScoreService;
    
    @Autowired
    private IBaseFileService<T> baseFileServcie;
    
    @Autowired
    private IClassStyleService<T> classStyleService;
    
    private int pageSize=PageSizeConfig.ADMIN_LIST_PAGESIZE;
    
    private int studentPageSize=PageSizeConfig.ADMIN_STUDENT_PAGESIZE;
    
	/**
	 * 
	* @Title: trends
	* @Description: TODO 校园动态列表
	* @param @param map
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	 */
   @SuppressWarnings("unchecked")
   @RequestMapping("/trends")	
   public  String trends(String flag,int p,ModelMap map) {
	   
	   String fileType = "ftrends";
	   
	   String sp = p + "";
	   if("".equals(sp)){
			p=1;
	   }
	   //查看详细信息url
	   String durl = "trends";
		
	   //当前的url
	   String url = "./trends?flag='"+flag+"'&p=";
		
	   //获取总记录量
	   int count = trendsService.findTrendsCount(flag);
	   //计算偏移量
	   int position = (p-1)*pageSize;
		
	   //根据偏移量获取数据
	   ArrayList<Trends> trends = (ArrayList<Trends>) trendsService.findTrendsInfo(flag, position, pageSize);
	   	
		//获取封装好的分页导航数据
       String toolBar = pageHelperService.createToolBar(count,pageSize, url, p);		
       
       //根据typeFlag获取typeName
       String typeName = typeFlagToTypeNameService.findTrendsTypeNameByTypeFlag(flag);
       
      
       map.put("durl",durl);
       map.put("toolBar", toolBar);
       map.put("typeName", typeName);
       map.put("typeFlag", flag);
       map.put("item", trends);  
       map.put("fileType", fileType);
	   return "admin/list/list";
   }
   
   /**
    * 
   * @Title: indexImages
   * @Description: TODO 首页 大轮播图
   * @param @return    设定文件
   * @return String    返回类型
   * @throws
    */
   @SuppressWarnings("unchecked")
   @RequestMapping("/indexImages")
   public  String indexImages(ModelMap map) {
	   
	   //获取图片列表
	   ArrayList<IndexImages> list = (ArrayList<IndexImages>) indexImagesService.findImagesList();
	   String  title = "首页大轮播图";
	   String  url = "indexImages";
		
	   map.put("title", title);
	   map.put("url", url);
	   map.put("list", list);
	   return "admin/list/indexImagesList";
   }
   
   
   /**
    * 
   * @Title: campusScenery
   * @Description: TODO 校园风光列表
   * @param @param map
   * @param @return    设定文件
   * @return String    返回类型
   * @throws
    */
   @SuppressWarnings("unchecked")
   @RequestMapping("/campusScenery")
   public String campusScenery(ModelMap map) {
	   ArrayList<CampusScenery> campusScenery = (ArrayList<CampusScenery>) campusSceneryService.findCampusSceneryList();
	   
	   String  title = "校园风光";
	   String  url = "campusScenery";
		
	   map.put("title", title);
	   map.put("url", url);
	   map.put("list", campusScenery);
	   return "admin/list/indexImagesList";
   }
   
   @SuppressWarnings("unchecked")
   @RequestMapping("/culture")	
   public  String culture(String flag,int p,ModelMap map) {
	   
	   
	   String sp = p + "";
	   if("".equals(sp)){
			p=1;
	   }
	   //查看详细信息url
	   String durl = "culture";
	   
	   //文件上传类型
	   String fileType = "fculture";
		
	   //当前的url
	   String url = "./culture?flag='"+flag+"'&p=";
		
	   //获取总记录量
	   int count = cultureService.findCultureCount(flag);
	   //计算偏移量
	   int position = (p-1)*pageSize;
		
	   //根据偏移量获取数据
	   ArrayList<Culture> culture = (ArrayList<Culture>) cultureService.findCultureInfo(flag, position, pageSize); 	   	
		
	   //获取封装好的分页导航数据
       String toolBar = pageHelperService.createToolBar(count,pageSize, url, p);		
       
       //根据typeFlag获取typeName
       String typeName = typeFlagToTypeNameService.findCultureTypeNameByTypeFlag(flag);
       
      
       map.put("durl",durl);
       map.put("fileType", fileType);
       map.put("toolBar", toolBar);
       map.put("typeName", typeName);
       map.put("typeFlag", flag);
       map.put("item", culture);  
	   return "admin/list/list";
   }
   
   
   @SuppressWarnings("unchecked")
   @RequestMapping("/education")	
   public  String education(String flag,int p,ModelMap map) {
	   
	   
	   String sp = p + "";
	   if("".equals(sp)){
			p = 1;
	   }
	   //查看详细信息url
	   String durl = "education";
		
	   //当前的url
	   String url = "./education?flag='"+flag+"'&p=";
	   
	   //文件上传类型
 	   String fileType = "feducation";
		
	   //获取总记录量
	   int count = educationService.findEducationCount(flag);
	   //计算偏移量
	   int position = (p-1)*pageSize;
		
	   //根据偏移量获取数据
	   ArrayList<Education> education = (ArrayList<Education>) educationService.findEducationInfo(flag, position, pageSize);
	   	
		//获取封装好的分页导航数据
       String toolBar = pageHelperService.createToolBar(count,pageSize, url, p);		
       
       //根据typeFlag获取typeName
       String typeName = typeFlagToTypeNameService.findEducationTypeNameByTypeFlag(flag);
       
      
       map.put("durl",durl);
       map.put("fileType", fileType);
       map.put("toolBar", toolBar);
       map.put("typeName", typeName);
       map.put("typeFlag", flag);
       map.put("item", education);  
	   return "admin/list/list";
   }
   
   @SuppressWarnings("unchecked")
   @RequestMapping("/student")	
   public  String student(String flag,int p,ModelMap map) {
	   
	   
	   String sp = p + "";
	   if("".equals(sp)){
			p = 1;
	   }
	   //查看详细信息url
	   String durl = "student";
		
	   //当前的url
	   String url = "./student?flag='"+flag+"'&p=";
	   
	   //文件上传类型
 	   String fileType = "fstudent";
		
	   //获取总记录量
	   int count = studentService.findStudentCount(flag);
	   //计算偏移量
	   int position = (p-1)*pageSize;
		
	   //根据偏移量获取数据
	   ArrayList<Student> student = (ArrayList<Student>) studentService.findStudentInfo(flag, position, pageSize);
	   	
		//获取封装好的分页导航数据
       String toolBar = pageHelperService.createToolBar(count,pageSize, url, p);		
       
       //根据typeFlag获取typeName
       String typeName = typeFlagToTypeNameService.findStudentTypeNameByTypeFlag(flag);
       
      
       map.put("durl",durl);
       map.put("fileType", fileType);
       map.put("toolBar", toolBar);
       map.put("typeName", typeName);
       map.put("typeFlag", flag);
       map.put("item", student);  
	   return "admin/list/list";
   }
   
   /**获取班级列表**/
   @RequestMapping("/sclass")
	public String classList(ModelMap map) {
	   String durl = "sclass";
	   String murl = "manageSclass";
	   ArrayList<Grade> grade = (ArrayList<Grade>) gradeService.findGradeCode();
	   ArrayList<Sclass> sclass = (ArrayList<Sclass>) sclassService.findClassInfo();
	   SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
       Date date = new Date();
       String formatDate = sdf.format(date);
       int d = Integer.parseInt(formatDate);
       map.put("durl", durl);
	   map.put("murl", murl);
	   map.put("year", d);
	   map.put("sclass", sclass);
	   map.put("grade", grade);
	   return "/admin/beforelist/classList";
	}
   
   /**获取班级内详细信息**/
   @SuppressWarnings("unchecked")
   @RequestMapping("/sclassHomePage")	
   public  String sclassHomePage(int classId,int grade,String className,int p,ModelMap map) {
	//   String flag="resources";
	   String sp = p + "";
	   if("".equals(sp)){
			p = 1;
	   }
	   //查看详细信息url
	   String durl = "sclassHomePage";
	   
	   String introurl = "sclassintro";
		
	   //当前的url
	   String url = "./sclassHomePage?classId='"+classId+"'&grade='"+grade+"'&className='"+className+"'&p=";
	   
	   //文件上传类型
 	   String fileType = "fstudent";
		
	   //获取总记录量
	   int count = classService.findSclassCount(classId);
	   //计算偏移量
	   int position = (p-1)*pageSize;
	   
	   //根据classId获取简介信息
	   ClassSynopsis classSynopsis = (ClassSynopsis) classService.findSclassIntroById(classId);
	   //根据偏移量获取数据
	   ArrayList<ClassHomePage> classHomePage = (ArrayList<ClassHomePage>) classService.findClassInfoByClassId(classId, position, pageSize);
	   	
		//获取封装好的分页导航数据
       String toolBar = pageHelperService.createToolBar(count,pageSize, url, p);		
       
       //获取labName即社团名称
       String labName = grade+"年级"+className;
       
       map.put("introurl",introurl);
       map.put("introduce",classSynopsis);
       map.put("labId", classId);
       map.put("durl",durl);
       map.put("fileType", fileType);
       map.put("toolBar", toolBar);
       map.put("className", className);
       map.put("labName", labName);
       map.put("grade", grade);
      // map.put("typeFlag", flag);
       map.put("item", classHomePage);  
	   return "admin/list/sclassList";
   }
	
   
   @SuppressWarnings("unchecked")
   @RequestMapping("/teacher")	
   public  String teacher(String flag,int p,ModelMap map) {
	   
	   
	   String sp = p + "";
	   if("".equals(sp)){
			p = 1;
	   }
	   //查看详细信息url
	   String durl = "teacher";
		
	   //当前的url
	   String url = "./teacher?flag='"+flag+"'&p=";
	   
	   //文件上传类型
 	   String fileType = "fteacher";
		
	   //获取总记录量
	   int count = teacherService.findTeacherCount(flag);
	   //计算偏移量
	   int position = (p-1)*pageSize;
		
	   //根据偏移量获取数据
	   ArrayList<Teacher> teacher = (ArrayList<Teacher>) teacherService.findTeacherInfo(flag, position, pageSize);
	   	
		//获取封装好的分页导航数据
       String toolBar = pageHelperService.createToolBar(count,pageSize, url, p);		
       
       //根据typeFlag获取typeName
       String typeName = typeFlagToTypeNameService.findTeacherTypeNameByTypeFlag(flag);
       
      
       map.put("durl",durl);
       map.put("fileType", fileType);
       map.put("toolBar", toolBar);
       map.put("typeName", typeName);
       map.put("typeFlag", flag);
       map.put("item", teacher);  
	   return "admin/list/list";
   }
   
   @SuppressWarnings("unchecked")
   @RequestMapping("/party")	
   public  String party(String flag,int p,ModelMap map) {
	   
	   
	   String sp = p + "";
	   if("".equals(sp)){
			p = 1;
	   }
	   //查看详细信息url
	   String durl = "party";
		
	   //当前的url
	   String url = "./party?flag='"+flag+"'&p=";
	   
	   //文件上传类型
 	   String fileType = "fparty";
		
	   //获取总记录量
	   int count = partyService.findPartyCount(flag);
	   //计算偏移量
	   int position = (p-1)*pageSize;
		
	   //根据偏移量获取数据
	   ArrayList<Party> party = (ArrayList<Party>) partyService.findPartyInfo(flag, position, pageSize);
	   	
		//获取封装好的分页导航数据
       String toolBar = pageHelperService.createToolBar(count,pageSize, url, p);		
       
       //根据typeFlag获取typeName
       String typeName = typeFlagToTypeNameService.findPartyTypeNameByTypeFlag(flag);
       
      
       map.put("durl",durl);
       map.put("fileType", fileType);
       map.put("toolBar", toolBar);
       map.put("typeName", typeName);
       map.put("typeFlag", flag);
       map.put("item", party);  
	   return "admin/list/list";
   }
   
   @SuppressWarnings("unchecked")
   @RequestMapping("/manage")	
   public  String manage(String flag,int p,ModelMap map) {
	   
	   
	   String sp = p + "";
	   if("".equals(sp)){
			p=1;
	   }
	   //查看详细信息url
	   String durl = "manage";
		
	   //当前的url
	   String url = "./manage?flag='"+flag+"'&p=";
	   
	   //文件上传类型
 	   String fileType = "fmanage";
		
	   //获取总记录量
	   int count = manageService.findManageCount(flag);
	   //计算偏移量
	   int position = (p-1)*pageSize;
		
	   //根据偏移量获取数据
	   ArrayList<Manage> manage = (ArrayList<Manage>) manageService.findManageInfo(flag, position, pageSize);
	   	
		//获取封装好的分页导航数据
       String toolBar = pageHelperService.createToolBar(count,pageSize, url, p);		
       
       //根据typeFlag获取typeName
       String typeName = typeFlagToTypeNameService.findManageTypeNameByTypeFlag(flag);
       
      
       map.put("durl",durl);
       map.put("fileType", fileType);
       map.put("toolBar", toolBar);
       map.put("typeName", typeName);
       map.put("typeFlag", flag);
       map.put("item", manage);  
	   return "admin/list/list";
   }
   
   /**
    * 显示教学资源列表
    */
   @SuppressWarnings("unchecked")
   @RequestMapping("/teachingResource")
   public String teachingResource(ModelMap map) {
	 //获取班级列表
 		ArrayList<TeachingResourcesClass> classlist = (ArrayList<TeachingResourcesClass>)  teachingResources.findTeachingResourcesClassList();
 		
 		//获取教学资源项目列表
 		ArrayList<TeachingResourcesMenu>   menu = (ArrayList<TeachingResourcesMenu>) teachingResources.findTeachingResourcesMenu();
 		
 		map.put("classlist", classlist);
 		map.put("menu", menu);
	   return "admin/beforelist/teachingResource";
   }
   
   /*
    * 教学资源界面
    * murl 更新删除操作
    * durl 返回到当前页面
    */
   @SuppressWarnings("unchecked")
   @RequestMapping("/manageResource")
   public String manageResource(ModelMap map) {
	   String durl="manageResource";
	 //获取班级列表
 		ArrayList<TeachingResourcesClass> classlist = (ArrayList<TeachingResourcesClass>)  teachingResources.findTeachingResourcesClassList();
 		
 		//获取教学资源项目列表
 		ArrayList<TeachingResourcesMenu>   menu = (ArrayList<TeachingResourcesMenu>) teachingResources.findTeachingResourcesMenu();
 		
 		String name="教学资源";
 		map.put("name",name);
 		map.put("durl", durl);
 		map.put("classlist", classlist);
 		map.put("menu", menu);
	   return "admin/beforelist/manageResource";
   }
   
   @SuppressWarnings("unchecked")
   @RequestMapping("/manageDepartment")
   public String partyLink(ModelMap map) {
	   String durl = "manageDepartment";
	   //获取部门列表
 		ArrayList<DepartmentLinkNameList> menu = (ArrayList<DepartmentLinkNameList>)  departmentService.findDepartmentNameList();
 		String name = "部门链接";
 		
 		map.put("name",name);
 		map.put("durl", durl);
 		map.put("menu", menu);
	   return "admin/beforelist/manageResource";
   }
   
   //获取部门内所有信息BydeptId 
   @SuppressWarnings("unchecked")
   @RequestMapping("/department")	
   public  String department(int deptId,int p,ModelMap map) {
	//   String flag="resources";
	   
	   String sp = p + "";
	   if("".equals(sp)){
			p = 1;
	   }
	   //查看详细信息url
	   String durl = "department";
		
	   //当前的url
	   String url = "./department?deptId='"+deptId+"'&p=";
	   
	   //文件上传类型
 	   String fileType = "fmanage";
		
	   //获取总记录量
	   int count = departmentService.findDepartmentContentCount(deptId);
	   //计算偏移量
	   int position = (p-1)*pageSize;
		
	   //根据偏移量获取数据
	   ArrayList<DepartmentLinkContent> departmentLinkContent = (ArrayList<DepartmentLinkContent>) departmentService.findDepartmentContentInfo(deptId, position, pageSize);
	   	
		//获取封装好的分页导航数据
       String toolBar = pageHelperService.createToolBar(count,pageSize, url, p);		
       
       //获取menuName即该部门的名称
       String menuName = departmentService.findDepartmentNameById(deptId);
       
       map.put("deptId", deptId);
       map.put("durl",durl);
       map.put("fileType", fileType);
       map.put("toolBar", toolBar);
       map.put("menuName", menuName);
      // map.put("typeFlag", flag);
       map.put("item", departmentLinkContent);  
	   return "admin/list/departmentList";
   }
   
   
   @SuppressWarnings("unchecked")
   @RequestMapping("/resource")	
   public  String resource(int classId,int menuId,int p,ModelMap map) {
	//   String flag="resources";
	   
	   String sp = p + "";
	   if("".equals(sp)){
			p = 1;
	   }
	   //查看详细信息url
	   String durl = "resource";
		
	   //当前的url
	   String url = "./resource?classId='"+classId+"'&menuId='"+menuId+"'&p=";
	   
	   //文件上传类型
 	   String fileType = "fteacher";
		
	   //获取总记录量
	   int count = teachingResourcesService.findTeachingResourceCount(classId, menuId);
	   //计算偏移量
	   int position = (p-1)*pageSize;
		
	   //根据偏移量获取数据
	   ArrayList<TeachingResourcesContent> TeachingResourcesContent = (ArrayList<TeachingResourcesContent>) teachingResourcesService.findTeachingResourceInfo(classId,menuId,position, pageSize);
	   	
		//获取封装好的分页导航数据
       String toolBar = pageHelperService.createToolBar(count,pageSize, url, p);		
       
       //获取menuName
       String menuName = teachingResourcesService.findTeachingResurceNameById(menuId);
       
       map.put("classId", classId);
       map.put("menuId", menuId);
       map.put("durl",durl);
       map.put("fileType", fileType);
       map.put("toolBar", toolBar);
       map.put("menuName", menuName);
      // map.put("typeFlag", flag);
       map.put("item", TeachingResourcesContent);  
	   return "admin/list/resourceList";
   }
   
   /**
    * @param durl 用于返回到当前页面
    * @param murl 增加和删除方法中找到位置
    * @param map
    * @return
    */
   	@SuppressWarnings("unchecked")
	@RequestMapping("/labclass")
	public String labClassList(ModelMap map) {
		String durl = "labclass";
   		String murl = "managelablist";
		ArrayList<StudentLab> labClass = (ArrayList<StudentLab>) labService.findLabClassListInfo();
		map.put("durl", durl);
		map.put("murl", murl);
		map.put("labClass", labClass);
		
		return "admin/beforelist/labClass";
	}
   	
   	@SuppressWarnings("unchecked")
    @RequestMapping("/labclassinfo")	
    public  String labclassinfo(int labId,int p,ModelMap map) {
 	//   String flag="resources";
 	   
 	   String sp = p + "";
 	   if("".equals(sp)){
 			p = 1;
 	   }
 	   //查看详细信息url
 	   String durl = "labclassinfo";
 	   
 	   String introurl = "labintro";
 		
 	   //当前的url
 	   String url = "./labclassinfo?labId='"+labId+"'&p=";
 	   
 	   //文件上传类型
  	   String fileType = "fstudent";
 		
 	   //获取总记录量
 	   int count = labClassService.findLabClassCount(labId);
 	   //计算偏移量
 	   int position = (p-1)*pageSize;
 	   
 	   //根据labId获取简介信息
 	   StudentLabMenuIntroduce studentLabMenuIntroduce = (StudentLabMenuIntroduce) labClassService.findLabMenuIntroById(labId);
 	   //根据偏移量获取数据
 	   ArrayList<StudentLabMenuContent> studentLabMenuContent = (ArrayList<StudentLabMenuContent>) labClassService.findLabClassInfo(labId, position, pageSize);
 	   	
 		//获取封装好的分页导航数据
        String toolBar = pageHelperService.createToolBar(count,pageSize, url, p);		
        
        //获取labName即社团名称
        String labName = labClassService.findLabClassTypeById(labId);
        
        map.put("introurl", introurl);
        map.put("introduce", studentLabMenuIntroduce);
        map.put("labId", labId);
        map.put("durl",durl);
        map.put("fileType", fileType);
        map.put("toolBar", toolBar);
        map.put("labName", labName);
       // map.put("typeFlag", flag);
        map.put("item", studentLabMenuContent);  
 	   return "admin/list/labClassList";
    }
   	
   	/**
   	 * 
   	* @Title: HeaderMasterInfo
   	* @Description: TODO 校长列表
   	* @param @param p
   	* @param @param map
   	* @param @return    设定文件
   	* @return String    返回类型
   	* @throws
   	 */
   	@SuppressWarnings("unchecked")
	@RequestMapping("/headMaster")
   	public String HeaderMasterInfo(int p,ModelMap map) {
   	   String sp = p + "";
	   if(sp.equals("")){
			p = 1;
	   }
	   
	   //当前的url
	   String url = "./headMaster?p=";
	   		
	   //获取总记录量
	   int count = headerMasterService.findHeadMasterCount();
	   //计算偏移量
	   int position = (p-1)*pageSize;
   	   ArrayList<HeadMaster> headMaster = (ArrayList<HeadMaster>) headerMasterService.findHeaderMasterInfo(position, pageSize);
       //获取封装好的分页导航数据
       String toolBar = pageHelperService.createToolBar(count,pageSize, url, p);	
   	   map.put("headMaster", headMaster);
   	   map.put("toolBar", toolBar);
   	   return "admin/list/headmasterList";
   	   
   	}
   	
   	
   	/**
   	 * 主要完成对班级学生信息的录入，修改和删除
   	 */
   	/**获取班级列表**/
    @RequestMapping("/studentInfomation")
 	public String studentInfomation(ModelMap map) {
 	   String durl="studentInfomation";
 		ArrayList<Grade> grade = (ArrayList<Grade>) gradeService.findGradeCode();
 		ArrayList<Sclass> sclass = (ArrayList<Sclass>) sclassService.findClassInfo();
 		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        String formatDate = sdf.format(date);
        int d = Integer.parseInt(formatDate);
        map.put("durl", durl);
 		map.put("year", d);
 		map.put("sclass", sclass);
 		map.put("grade", grade);
 		return "/admin/beforelist/studentInfomation";
 	}
    
    
    /**获取某个班级内学生详细信息**/
    @SuppressWarnings("unchecked")
    @RequestMapping("/stuInfo")	
    public  String stuInfo(int classId,String fullName,int p,ModelMap map) {
 	//   String flag="resources";
 	   String sp = p + "";
 	   if("".equals(sp)){
 			p = 1;
 	   }
 	   //查看详细信息url
 	   String durl = "stuInfo";
 	   
 		
 	   //当前的url
 	   String url = "./stuInfo?classId='"+classId+"'&fullName='"+fullName+"'&p=";
 	   
 		
 	   //获取总记录量
 	   int count = stuInfoService.findStuCount(classId);
 	   //计算偏移量
 	   int position = (p-1)*studentPageSize;
 	   System.out.println("count-"+count+"position--"+position);
 	   //根据偏移量获取数据
 	   ArrayList<CourseStudentInfo> courseStudentInfo = (ArrayList<CourseStudentInfo>) stuInfoService.findStuInfo(classId, position, studentPageSize);
      //处理年龄
	   String age;
	   int byear;
	   int stuAge;
	   SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
	   Date date = new Date();
	   String formatDate = sdf.format(date);
	   int year = Integer.parseInt(formatDate);
 	   for(CourseStudentInfo info:courseStudentInfo){
 		  age = info.getStuBirthday();
 		  byear = Integer.parseInt(age.substring(0, 4));
 		  stuAge = year-byear;
 		  info.setStuAge(stuAge);
 	   }
 	   
 	   //获取封装好的分页导航数据
        String toolBar = pageHelperService.createToolBar(count,studentPageSize, url, p);		
        
        
        map.put("classId", classId);
        map.put("durl",durl);
        map.put("toolBar", toolBar);
        map.put("fullName", fullName);
        map.put("item", courseStudentInfo);  
 	   return "admin/list/stuInfoList";
    }
    
    /**
     * TODO 各科成绩的管理
     * @param map
     * @return
     */
    @RequestMapping("/studentScore")
    public String studentScore(ModelMap map) {
		String durl = "studentScore";
		ArrayList<Grade> grade = (ArrayList<Grade>) gradeService.findGradeCode();
		ArrayList<Sclass> sclass = (ArrayList<Sclass>) sclassService.findClassInfo();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		Date date = new Date();
		String formatDate = sdf.format(date);
		int d = Integer.parseInt(formatDate);
		map.put("durl", durl);
		map.put("year", d);
		map.put("sclass", sclass);
		map.put("grade", grade);
 	   return "admin/beforelist/studentScore";
    }
    
    
    /**
     * TODO 各科成绩的管理
     * @param map
     * @return
     */
    @RequestMapping("/manageClass")
    public String manageClass(ModelMap map) {
		String durl = "manageClass";
		String fileType = "fstudent";
		ArrayList<Grade> grade = (ArrayList<Grade>) gradeService.findGradeCode();
		ArrayList<Sclass> sclass = (ArrayList<Sclass>) sclassService.findClassInfo();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		Date date = new Date();
		String formatDate = sdf.format(date);
		int d = Integer.parseInt(formatDate);
		map.put("fileType", fileType);
		map.put("durl", durl);
		map.put("year", d);
		map.put("sclass", sclass);
		map.put("grade", grade);
 	   return "admin/beforelist/manageClass";
    }
    
    //考试科目管理界面
    @SuppressWarnings("unchecked")
    @RequestMapping("/manageScore")
    public String manageScore(ModelMap map) {
 	   String durl = "manageScore";
  		//获取考试项目列表
  		ArrayList<CourseType>  menu = (ArrayList<CourseType>) courseScoreService.findCourseType();
  		
  		map.put("durl", durl);
  		map.put("menu", menu);
 	   return "admin/beforelist/manageScore";
    }
    
    
    
   //**获取某个班级内成绩信息**//
    @SuppressWarnings("unchecked")
    @RequestMapping("/scoreInfo")	
    public  String scoreInfo(int classId,String fullName,int p,ModelMap map) {
 	//   String flag="resources";
 	   String sp = p + "";
 	   if("".equals(sp)){
 			p = 1;
 	   }
 	   //查看详细信息url
 	   String durl = "scoreInfo";
 	   
 	  //当前的url
 	   String url = "./scoreInfo?classId='"+classId+"'&fullName='"+fullName+"'&p=";
 	   
 	  //获取总记录量
 	   int count = courseScoreService.findScoreCount(classId);
 	  //获取考试的科目数
 	   long sum = courseScoreService.findTypeCount();
 	   
 	   int sSum = (int)sum;
 	   //计算偏移量
 	   int position = (p-1)*sSum*pageSize;
 	  System.out.println("count-"+count+"position--"+position);
 	   //根据偏移量获取数据
 	   ArrayList<CourseScore> courseScore = (ArrayList<CourseScore>) courseScoreService.findScoreInfo(classId, position, pageSize*sSum);
 	   System.out.println("size--"+courseScore.size());
 	 
 	   //获取考试类别
 	   ArrayList<CourseType> courseType = (ArrayList<CourseType>) courseScoreService.findCourseType();
 	   
 	   //获取封装好的分页导航数据
        String toolBar = pageHelperService.createToolBar(count,pageSize, url, p);		
        
        map.put("courseType", courseType);
        map.put("sum", sum);
        map.put("classId", classId);
        map.put("durl",durl);
        map.put("toolBar", toolBar);
        map.put("fullName", fullName);
        map.put("item", courseScore);  
 	   return "admin/list/scoreInfoList";
    }
    
    
    //作息时间表 
    @SuppressWarnings("unchecked")
	@RequestMapping("/restTime")
    public String restTime(ModelMap map) {
 	   String durl = "restTime";
  		 		
  	   String belongType = "frestTime";
        //获取文件列表
        ArrayList<FileBean> Spring = (ArrayList<FileBean>) baseFileServcie.findFile(belongType,1);
        ArrayList<FileBean> Autumn = (ArrayList<FileBean>) baseFileServcie.findFile(belongType,2);
        map.put("Spring", Spring);  
        map.put("Autumn", Autumn); 
  		map.put("fileType", belongType);
  		map.put("durl", durl);
 	   return "admin/list/restTime";
    }
  
    /**
     * 
    * @Title: classStyle
    * @Description: TODO  班级风采动态
    * @param @param map
    * @param @return    设定文件
    * @return String    返回类型
    * @throws
     */
    @SuppressWarnings("unchecked")
	@RequestMapping("/classStyle")
    public String  classStyle(int classId,String className,ModelMap map) {
    	
    	 ArrayList<ClassStyle> style = (ArrayList<ClassStyle>) classStyleService.findClassStyleList(classId);
    	
    	 String url = "classStyle";
    	 map.put("style", style);
    	 map.put("url", url);
    	 map.put("className", className);
    	 map.put("classId", classId);
    	 return "admin/list/classStyle";
    }
    
    
    /**
     * 
    * @Title: helpdoc
    * @Description: TODO  帮助文档
    * @param @param map
    * @param @return    设定文件
    * @return String    返回类型
    * @throws
     */
    @SuppressWarnings("unchecked")
	@RequestMapping("/helpdoc")
    public String helpdoc(ModelMap map) {
 	   String durl = "helpdoc";
  		 		
  	   String belongType = "fhelpdoc";
        //操作流程
        ArrayList<FileBean> line = (ArrayList<FileBean>) baseFileServcie.findFile(belongType,1);
        //注册指导
        ArrayList<FileBean> document = (ArrayList<FileBean>) baseFileServcie.findFile(belongType,2);
        //统计文件
        ArrayList<FileBean> count = (ArrayList<FileBean>) baseFileServcie.findFile(belongType,3);
        map.put("line", line);  
        map.put("document", document); 
        map.put("count", count); 
  		map.put("fileType", belongType);
  		map.put("durl", durl);
 	   return "admin/list/helpdoc";
    }
  
}
