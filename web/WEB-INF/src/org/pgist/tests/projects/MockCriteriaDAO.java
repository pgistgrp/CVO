package org.pgist.tests.projects;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.pgist.criteria.Criteria;
import org.pgist.criteria.CriteriaDAO;
import org.pgist.criteria.CriteriaSuite;
import org.pgist.criteria.Objective;
import org.pgist.cvo.CCT;
import org.pgist.users.User;

public class MockCriteriaDAO implements CriteriaDAO {
	
	
	

	/* (non-Javadoc)
	 * @see org.pgist.criteria.CriteriaDAO#assocCriterion(java.lang.Long, java.lang.Long, boolean)
	 */
	public void addAssocCriterion(Long critId, Long critSuiteId, boolean checked) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	
	public void editObjective(Long objectiveId, String description) throws Exception {
		
		
	}
	
	public Long checkPublished(Long cctId) {
		
		return null;
	}
	
	public boolean getContainsCriteria(Long critId, Long critSuiteId) throws Exception {
		// TODO Auto-generated method stub
		return true;
	}
	
	
	public Criteria addCriterion(Boolean bool_themes, Boolean bool_objectives,
			String name, CCT cct, Set themes, Set objectives, String na)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public Objective addObjective(Long critId, String description) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteCriterion(Long id) throws Exception {
		// TODO Auto-generated method stub

	}

	public void deleteObjective(Long id) throws Exception {
		// TODO Auto-generated method stub

	}

	public void editCriterion(boolean bool_name, boolean bool_themes, boolean bool_objectives,
			Criteria c, String name, CCT cct, Set themes, Set objectives,
			String na) throws Exception {
		// TODO Auto-generated method stub

	}

	public Collection getAllCriterion(CCT cct) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection getAllCriterion() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public Criteria getCriterionById(Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public Set getCriterions(String[] criteriaIdList) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public Set getObjectiveObjects(String[] objectivesIdList) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection getObjectives() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public Set getThemeObjects(String[] themeIdList) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public int getWeight(Long critSuiteId, Long critId) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setWeight(CCT cct, Criteria criteria, int weight)
			throws Exception {
		// TODO Auto-generated method stub

	}

	public User getUserById(Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public Object load(Class klass, Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public void save(Object object) throws Exception {
		// TODO Auto-generated method stub

	}

	public Criteria addCriterion(Boolean bool_themes, Boolean bool_objectives, String name, Set themes, Set objectives, String na) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public void editCriterion(boolean bool_name, boolean bool_themes, boolean bool_objectives, Criteria c, String name, Set themes, Set objectives, String na) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public Set getAllCriterion(Long critSuiteId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public CriteriaSuite getCriteriaSuiteById(Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public void setWeight(Long suiteId, Criteria criteria, int weight) throws Exception {
		// TODO Auto-generated method stub
		
	}

    public Collection getCriteriaSuites() throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

}
