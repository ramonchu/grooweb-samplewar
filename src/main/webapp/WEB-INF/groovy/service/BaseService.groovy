package service

import javax.jdo.PersistenceManager
import javax.jdo.Transaction

class BaseService {

	protected PersistenceManager pm;

	public BaseService(PersistenceManager pm){
		this.pm = pm;
	}

	public execTrans(Closure<?> code){
		return execTrans(code,null)
	}

	public execTrans(Closure<?> code, Closure<?> onfail){
		Transaction tx=pm.currentTransaction();
		try {
			tx.begin();
			def res = code();
			tx.commit();
			return res;
		}catch(Exception e){
			if(onfail!=null){
				return onfail();
			}else{
				e.printStackTrace();
				return null;
			}
		}finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}
	}
}
