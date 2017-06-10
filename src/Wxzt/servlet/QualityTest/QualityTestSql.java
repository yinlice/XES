package Wxzt.servlet.QualityTest;

import Wxzt.servlet.Common.Query;
import Wxzt.tool.JDBC;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yin on 2017/4/28.
 */
public class QualityTestSql extends Query{

    /**
     * 根据呼入方向和组别 来查询模板 进而查询模板下的项目
     * @param request
     * @return
     */
    public List<ProjectBean> getProjectByModelId(HttpServletRequest request){
        List<ProjectBean> list = new ArrayList<>();
        String direction = request.getParameter("direction");
        String groupid = request.getParameter("groupid");
        String sql = "select a.* from px_CRM_QualityTest_modelProject as a left join px_CRM_QualityTest_model as b  on a.modelid = b.modelid  where " +
                "  1=1  and b.direction = '"+direction+"' and b.groupid = "+groupid;
        try{
            JDBC jdbc = new JDBC();
            ResultSet rs = jdbc.executeQuery(sql);
            while(rs.next()){
                ProjectBean bean = new ProjectBean();
                String projectId = rs.getString("projectId");
                String modelid2 = rs.getString("modelId");
                String projectName = rs.getString("projectName");
                bean.setModelid(modelid2);
                bean.setProjectId(projectId);
                bean.setProjectName(projectName);
                list.add(bean);
            }
            jdbc.closeConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 根据项目的id 获取项目的类别
     * @param request
     * @return
     */
    public List<TypeBean> getTypeBeanByProjectId(HttpServletRequest request) {
        List<TypeBean> list = new ArrayList<>();
        String projectId = request.getParameter("projectId");
        String sql = "select a.* from px_CRM_QualityTest_modelType as a where a.projectid = "+projectId;
        try{
            JDBC jdbc = new JDBC();
            ResultSet rs = jdbc.executeQuery(sql);
            while(rs.next()){
                TypeBean bean = new TypeBean();
                String typeId = rs.getString("typeId");
                String projectId2 = rs.getString("projectId");
                String modelid = rs.getString("modelid");
                String typeLabel = rs.getString("typeLabel");
                String max = rs.getString("max");
                bean.setProjectId(projectId2);
                bean.setTypeid(typeId);
                bean.setModelid(modelid);
                bean.setTypeLabel(typeLabel);
                bean.setMax(max);
                list.add(bean);
            }
            jdbc.closeConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 向模板下添加项目
     * @param request
     * @return
     */
    public boolean addProject(HttpServletRequest request){
        String modelId = request.getParameter("modelId");
        String projectName = request.getParameter("projectName");
        String sql = "insert into px_CRM_QualityTest_modelProject (modelid,projectName) values ("+modelId+",'"+projectName+"')";
        return updataSql(sql);
    }
    /**
     * 更新项目的名称
     * @param request
     * @return
     */
    public boolean modifyProject(HttpServletRequest request){
        String projectName = request.getParameter("projectName");
        String projectId = request.getParameter("projectId");
        String sql = "update px_CRM_QualityTest_modelProject set projectName = '"+projectName+"' where projectId = "+projectId;
        return updataSql(sql);
    }

    /**
     * 删除项目
     * @param request
     * @return
     */
    public Boolean deleteProject(HttpServletRequest request){
        String projectId = request.getParameter("projectId");
        String sql = "delete from px_CRM_QualityTest_modelProject where projectId ="+projectId;
        return updataSql(sql);
    }

    /**
     * 添加项目下的类别
     *
     * @param request
     * @return
     */
    public boolean addType(HttpServletRequest request){
        String projectId = request.getParameter("projectId");
        String modelId = request.getParameter("modelId");
        String typeLabel = request.getParameter("typeLabel");
        String max = request.getParameter("max");
        String sql = "insert into px_CRM_QualityTest_modelType (projectId,modelId,typeLabel,max) values ("+projectId+","+modelId+"," +
                "'"+typeLabel+"',"+max+")";
        return  updataSql(sql);
    }

    /**
     * 编辑项目下的类别
     * @param request
     * @return
     */
    public boolean ModifyType(HttpServletRequest request){
        String typeId = request.getParameter("typeId");
        String typeLabel = request.getParameter("typeLabel");
        String max = request.getParameter("max");
        String sql = "update px_CRM_QualityTest_modelType set typeLabel = '"+typeLabel+"',max = "+max+" where typeId ="+typeId;
        return updataSql(sql);
    }

    /**
     * 删除项目下的类别
     * @param request
     * @return
     */
    public boolean deleteType(HttpServletRequest request){
        String typeId = request.getParameter("typeId");
        String sql = "delete from px_CRM_QualityTest_modelType where typeId = "+typeId;
        return updataSql(sql);
    }
}
