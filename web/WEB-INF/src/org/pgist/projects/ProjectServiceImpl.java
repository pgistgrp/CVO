/**
 *
 */
package org.pgist.projects;

import java.sql.Connection;
import java.util.*;
import java.util.Date;

import org.pgist.discussion.*;

/**
 * @author Guirong
 *
 */
public class ProjectServiceImpl implements ProjectService{
	private ProjectDAO projectDAO = null;
	private Connection connection = null;
    private DiscussionDAO discussionDAO;

    public ProjectDAO getProjectDAO(){
		return this.projectDAO;
	}

	public void setProjectDAO(ProjectDAO p){
		this.projectDAO = p;
	}

	public Connection getConnection(){
		return this.connection;
	}

    public DiscussionDAO getDiscussionDAO() {
        return discussionDAO;
    }

    public void setConnection(Connection c){
		this.connection = c;
	}

    public void setDiscussionDAO(DiscussionDAO discussionDAO) {
        this.discussionDAO = discussionDAO;
    }

    //-----------------------------------------------
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
	public void saveFootprint(Project project, double[][][] coords, String geoType) throws Exception{
		projectDAO.saveFootprint(project, coords, geoType);
	}
	public Map getFootprints(String fpids) throws Exception{
		return projectDAO.getFootprints(fpids);
	}
	public double[][][] getFootprint(Long fpid) throws Exception{
		return projectDAO.getFootprint(fpid);
	}

        public void makeProjectsDiscussable(String criteria) throws Exception {
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


}
