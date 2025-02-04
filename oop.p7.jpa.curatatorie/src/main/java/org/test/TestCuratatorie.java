package org.test;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.entity.Curatatorie;
import org.entity.Curatatorie_chimica;
import org.entity.Curatatorie_ecologica;

public class TestCuratatorie {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EntityManagerFactory emf=Persistence.createEntityManagerFactory("CuratatorieJPA");
		EntityManager em=emf.createEntityManager();
		
		List <Curatatorie> lstCuratatoriePersistenta=em.createQuery("Select c From Curatatorie c",Curatatorie.class).getResultList();
		if(!lstCuratatoriePersistenta.isEmpty()) {
			em.getTransaction().begin();
			for(Curatatorie c:lstCuratatoriePersistenta) em.remove(c);
			em.getTransaction().commit();
		}
		List<Curatatorie> tipuriCuratatorie=new ArrayList<Curatatorie>();
		//Initializare explicita  a unor tipuri de curatatorie oferite

		Curatatorie_chimica c1=new Curatatorie_chimica(1,"Curatatorie chimica_1",150.0,10,"covoare");
		Curatatorie_chimica c2=new Curatatorie_chimica(2,"Curatatorie chimica_2",250.0, 11,"haine");
		
		
		tipuriCuratatorie.add(c1);
		tipuriCuratatorie.add(c2);
		
		
		Curatatorie_ecologica c3= new Curatatorie_ecologica(3,"Curatatorie ecologica_1",200.0,12,"covoare");
		Curatatorie_ecologica c4= new Curatatorie_ecologica(4,"Curatatorie_ecologica_2",275.0,13,"haine");
		
		tipuriCuratatorie.add(c3);
		tipuriCuratatorie.add(c4);
		
		
	
		
		em.getTransaction().begin();
		tipuriCuratatorie.stream().forEach(c->em.persist(c));
		em.getTransaction().commit();
		//Read after create
		lstCuratatoriePersistenta=em.createQuery("Select c From Curatatorie c",Curatatorie.class).getResultList();
		System.out.println("Lista curatatorie persistenta/salvat in baza de date");
		for (Curatatorie c:lstCuratatoriePersistenta)
			System.out.println("Cod: "+c.getCod()+", denumire: "+c.getDenumire()+", tarif: "+c.getTarif().toString());
		
	}
}

	


