package service

import javax.jdo.PersistenceManager
import javax.jdo.Query
import javax.jdo.Transaction

import com.arteco.valoranet.model.Person


public class PersonService{

	private PersistenceManager pm;

	public PersonService(PersistenceManager pm){
		this.pm = pm;
	}

	public List<Person> getList(){
		Transaction tx=pm.currentTransaction();
		try {
			tx.begin();
			Query q = pm.newQuery("SELECT FROM " + Person.class.getName());
			List<Person> people = (List<Person>)q.execute();
			tx.commit();
			return people;
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}
	}


	public void savePerson(Person p){
		Transaction tx=pm.currentTransaction();
		try {
			tx.begin();
			pm.makePersistent(p);
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}
	}
}