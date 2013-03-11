package controller;

import java.util.Date
import java.util.Locale

import javax.annotation.PostConstruct;

import com.arteco.grooweb.web.GrooController
import com.arteco.grooweb.web.GrooMap
import com.arteco.grooweb.web.GrooRequestMethod
import com.arteco.grooweb.web.GrooRole
import com.arteco.valoranet.model.Person;

import form.BasicForm
import service.PersonService


public class BasicController extends GrooController{

	private PersonService personService;
	

	public void init(){
		personService = new PersonService(this.persistenceManager);
	}
	
	@GrooMap("/listPeople.json")
	public Object listPeople(){
		return personService.getList();
	}
	
	@GrooMap("/addPerson.json")
	public Object addPerson(){
		Person p = new Person();
		p.setName("Ramón")
		p.setSurname("Arnau")
		personService.savePerson(p);
		return true;
	}

	class JsonObj{
		String name ="R";
		String surname ="A";
		String email ="A@b.com";
		String url ="http://agasfasdf.com";
	};

	@GrooMap("/index.html")
	public String index(){
		return "index.jsp";
	}

	@GrooMap("/prueba.html")
	public String prueba(){
		model.put("msg", "Hello world");
		return "prueba.jsp";
	}

	@GrooMap("/prueba.json")
	public Object pruebaJson(){
		return new JsonObj();
	}

	@GrooMap("/redirect.html")
	public String redirect(){
		return "redirect:/prueba.html";
	}
	
	@GrooMap("/parameters.html")
	public String parameters(){
		model.put("id", getParam("id"));
		model.put("from", getParam(Date.class,"from"));
		model.put("to", getParam(Date.class,"to"));
		return "parameters.jsp";
	}

	@GrooMap("/form.html")
	public String form(){
		return "form.jsp";
	}

	@GrooMap(value="/form.html",method=GrooRequestMethod.POST)
	public String formUpdate(){
		def popul = populidate(BasicForm.class);
		if(!popul.hasError()){
			//update changes in service layer, and do redirect after post
			return "formOk.jsp";
		}else{
			//show de  html form with errors
			model.put("urlToPost", "/form.html");
			model.put("form", popul.getValue());
			model.put("errors", popul.getErrors());
			return "form.jsp";
		}
	}
	
	
	@GrooMap("/groovyForm.html")
	public String groovyForm(){
		return "form.jsp";
	}

	@GrooMap(value="/groovyForm.html",method=GrooRequestMethod.POST)
	public String groovyFormUpdate(){
		def popul = populidate(BasicForm.class);
		if(!popul.hasError()){
			//update changes in service layer, and do redirect after post
			return "formOk.jsp";
		}else{
			//show de  html form with errors
			model.put("urlToPost", "/groovyForm.html");
			model.put("form", popul.getValue());
			model.put("errors", popul.getErrors());
			return "form.jsp";
		}
	}
	
	
	@GrooMap("/i18n.html")
	public String i18n(){
		model.put("messageEs", interpolate(new Locale("es"),"firstMessage"));
		model.put("messageEn", interpolate(new Locale("en"),"firstMessage"));
		model.put("messageDefault", interpolate(new Locale("en"),"firstMessage"));
		return "messages.jsp";
	}
	
	
	@GrooRole("ADMIN")
	@GrooMap("/notAllowed.html")
	public String notAllowed(){
		return "index.jsp";
	}
	
	@GrooMap("/xssAvoided.html")
	public String xssAvoided(){
		model.put("messageEs", "<script type='text/javascript'>alert('i catch you'); //if you can read this comment the escaped char works well</script>");
		model.put("messageEn", "<b>html injected</b>");
		model.put("messageDefault", "normal text");
		return "messages.jsp";
	}
	
	
	@GrooMap("/jadeTemplate.html")
	public String jadeTemplate(){
		model.put("messageEs", "<script type='text/javascript'>alert('i catch you'); //if you can read this comment the escaped char works well</script>");
		model.put("messageEn", "<b>html injected</b>");
		model.put("messageDefault", "normal text");
		return "jadetemplate.jade";
	}
	
	
}