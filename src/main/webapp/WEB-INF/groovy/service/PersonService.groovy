package service

import javax.jdo.PersistenceManager
import javax.jdo.Query

import com.arteco.valoranet.model.Person



public class PersonService extends BaseService{

	public PersonService(PersistenceManager pm){
		super(pm);
	}

	public List<Person> getList(){
		return execTrans({
			Query q = pm.newQuery("SELECT FROM " + Person.class.getName());
			List<Person> people = (List<Person>)q.execute();
			return people;
		});
	}


	public void savePerson(Person p){
		execTrans({
			pm.makePersistent(p);
		});
	}
}