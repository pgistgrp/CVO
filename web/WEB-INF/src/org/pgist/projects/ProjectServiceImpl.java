package org.pgist.projects;

import java.sql.Connection;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.pgist.discussion.DiscussionDAO;
import org.pgist.discussion.InfoObject;
import org.pgist.discussion.InfoStructure;


/**
 * @author  Guirong
 */
public class ProjectServiceImpl implements ProjectService{
    
    
	private ProjectDAO projectDAO = null;
    
    private DiscussionDAO discussionDAO;
    
	private Connection connection = null;
    
    
	/**
     * @param projectDAO  the projectDAO to set
     * @uml.property  name="projectDAO"
     */
	public void setProjectDAO(ProjectDAO p){
		this.projectDAO = p;
	}
	
    
    /**
     * @param discussionDAO  the discussionDAO to set
     * @uml.property  name="discussionDAO"
     */
    public void setDiscussionDAO(DiscussionDAO discussionDAO) {
        this.discussionDAO = discussionDAO;
    }
    
    
    /**
     * @param connection  the connection to set
     * @uml.property  name="connection"
     */
    public void setConnection(Connection c){
        this.connection = c;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
	
    
	public Project getProject(Long pId) throws Exception{
		return projectDAO.getProject(pId);
	}
    
    
	public List getProjects(String criteria) throws Exception{
		return projectDAO.getProjects(criteria);
	}
    
    
	public void saveProject(Project project) throws Exception{
		projectDAO.save(project);
	}
    
    
	public void saveProject(Project project, ProjectAlternative alternative) throws Exception{
		projectDAO.save(project, alternative);
	}
    
    
	public void saveFootprint(ProjectAlternative pa, double[][][] coords, String geoType) throws Exception{
		projectDAO.saveFootprint(pa, coords, geoType);
	}
    
    
	public Map getFootprints(String fpids) throws Exception{
		return projectDAO.getFootprints(fpids);
	}
    
    
	public double[][][] getFootprint(Long fpid) throws Exception{
		return projectDAO.getFootprint(fpid);
	}
	
    
    public void publishProjects(String criteria) throws Exception {
        Date date = new Date();

        InfoStructure info = new InfoStructure();
        info.setType("sdmap");
        info.setRespTime(date);

        for (Project project : (List<Project>) projectDAO.getProjects(criteria)) {
            System.out.println("-->>enable project " + project.getName());
            InfoObject obj = new InfoObject();
            obj.setObject(project);
            obj.setRespTime(date);

            info.getInfoObjects().add(obj);
        }//for

        discussionDAO.save(info);
    }//publish()


    public Project getProjectById(Long pid) throws Exception {
        return projectDAO.getProject(pid);
    }//getProjectById()


    public Collection getProjects() throws Exception {
        return projectDAO.getProjects();
    }//getProjects()


    public Project createProject(String name, String description) throws Exception {
        Project project = new Project();
        
        project.setName(name);
        project.setDescription(description);
        
        projectDAO.save(project);
        
        return project;
    }//createProject()


}//class ProjectServiceImpl
