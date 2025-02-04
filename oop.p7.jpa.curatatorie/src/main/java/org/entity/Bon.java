package org.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.AUTO;
import javax.persistence.Temporal;
import static javax.persistence.TemporalType.DATE;
@Entity
public class Bon {
	@Id
	@GeneratedValue(strategy = AUTO)
	private Integer nrBon;
	@Temporal(DATE)
	private Date data=new Date();
	@OneToMany(mappedBy = "bon")
	private List<LinieBon> linieBon=new ArrayList<LinieBon>();
	@ManyToOne
     private Client client;
	private Double totalBon;
	private Double totalTVA;
	public Integer getNrBon() {
		return nrBon;
	}
	public void setNrBon(Integer nrBon) {
		this.nrBon = nrBon;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public List<LinieBon> getLinieBon() {
		return linieBon;
	}
	public void setLinieBon(List<LinieBon> linieBon) {
		this.linieBon = linieBon;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	
	public void setTotalBon(Double totalBon) {
		this.totalBon = totalBon;
	}

	public void setTotalTVA(Double totalTVA) {
		this.totalTVA = totalTVA;
	}

	public Double getTotalBon() {
		if(linieBon.isEmpty()) return null;
		Double totalBon=0.0;
		for(LinieBon lb:linieBon)
			totalBon+=lb.getValoareLinie();
		return totalBon;
	}
	
	Double calculTotal() {
		Double totalBon=.0;
		for(LinieBon lb:linieBon ) totalBon+=lb.getValoareLinie();
		return totalBon;
	}
	public Double getTotalTVA() {
		if(linieBon.isEmpty())
			return null;
		Double totalBon=calculTotal();
		return 0.19/1.09*totalBon; // se aplica tva de 19%
	}
	
	
	
	
	public void adaugaLinie (LinieBon linieBon) {
		LinieBon.add(linieBon);
	}
	public void adauga(Curatatorie curatatorie) {
		LinieBon lb  =new LinieBon();
		lb.setBon(this);
		lb.setCuratatorie(curatatorie);
		this.linieBon.add(lb);
	}
	public Bon(Integer nrBon, Date data, List<LinieBon> linieBon, Client client, Double totalBon,
			Double totalTVA) {
		super();
		this.nrBon = nrBon;
		this.data = data;
		this.linieBon = linieBon;
		this.client = client;
		this.totalBon = totalBon;
		this.totalTVA = totalTVA;
	}
	public Bon() {
		super();
	}
	@Override
	public int hashCode() {
		return Objects.hash(client, data, linieBon, nrBon, totalBon, totalTVA);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bon other = (Bon) obj;
		return Objects.equals(client, other.client) && Objects.equals(data, other.data)
				&& Objects.equals(linieBon, other.linieBon) && Objects.equals(nrBon, other.nrBon)
				&& Objects.equals(totalBon, other.totalBon) && Objects.equals(totalTVA, other.totalTVA);
	}
	@Override
	public String toString() {
		return "Bon [nrBon=" + nrBon + ", data=" + data + ", linieBon=" + linieBon + ", client=" + client
				+ ", totalBon=" + totalBon + ", totalTVA=" + totalTVA + "]";
	}
	
}
