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


    public ProjectAlternative createProjectAlt(Map params, double[][][] footprint) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }//createProjectAlt()


    public void editProject(Long id, Map params, double[][][] footprint) throws Exception {
        // TODO Auto-generated method stub
    }//editProject()


    public void deleteProjectAlt(Long id) throws Exception {
        // TODO Auto-generated method stub
    }//deleteProjectAlt()


    /**
     * TODO
     */
    public ProjectAlternative getProjectAlternativeById(Long altId) throws Exception {
        return null;
    }//getProjectAlternativeById()


    /**
     * TODO
     */
    public Collection getProjectAlternativeGrades(Long cctId, Long altId) throws Exception {
        return null;
    }//getProjectAlternativeGrades()
    
    
    /**
     * Setup the association between projects and CCT.
     * 
     * @param cctId id of the CCT object to be associated
     * @param ids ids of projects to be assosicated
     * @throws Exception
     */
    public void setupProjectsForCCT(Long cctId, String[] ids) throws Exception {
        /*
         * TODO:
         *   Load CCT object by cctId, throw exception if failed
         *   Load each ProjectAlternative object by id, throw exception if any failed
         *   put each ProjectAlternative object to CCT.projects
         *   persist objects
         */
    }//setupProjectsForCCT()


    /**
     * Set grading
     * 
     * @param altId
     * @param critId
     * @param value
     * @throws Exception
     */
    public void setGrading(Long altId, Long critId, int value) throws Exception {
        /**
         * TODO:
         *   
         */
    }//setGrading()
    
    
}//class ProjectServiceImpl
