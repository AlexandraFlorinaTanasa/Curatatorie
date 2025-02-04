package org.entity;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.AUTO;
@Entity
public class LinieBon {
	@Id
	@GeneratedValue(strategy = AUTO)
	private Integer idLinie;
	@ManyToOne
	Curatatorie curatatorie;
	@ManyToOne
	Bon bon;
	private Double TVALinie;
	private Double valoareLinie;
	
	public Integer getIdLinie() {
		return idLinie;
	}
	public void setIdLinie(Integer idLinie) {
		this.idLinie = idLinie;
	}
	public Curatatorie getCuratatorie() {
		return curatatorie;
	}
	public void setCuratatorie(Curatatorie curatatorie) {
		this.curatatorie = curatatorie;
	}
	public Bon getBon() {
		return bon;
	}
	public void setBon(Bon bon) {
		this.bon = bon;
	}
	
	public Double getTVALinie() {
		if(TVALinie==null || TVALinie==0) TVALinie=calcTVALinie();
		return TVALinie;
	}
	public Double getValoareLinie() {
		if(valoareLinie==null || valoareLinie==0.0) valoareLinie=calcValLinie();
		return valoareLinie;
	}
	Double calcValLinie() {
		Double val=null;
		if(curatatorie!=null)
			val=curatatorie.getTarif();
		return val;
	}
	Double calcTVALinie() {
		Double valTVA=null;
		if(curatatorie!=null )
			valTVA=0.19/1.19*(curatatorie.getTarif());
		return valTVA;
	}
	public LinieBon(Integer idLinie, Curatatorie curatatorie, Bon bon, Double TVALinie,
			Double valoareLinie) {
		super();
		this.idLinie = idLinie;
		this.curatatorie = curatatorie;
		this.bon = bon;
		this.TVALinie = TVALinie;
		this.valoareLinie = valoareLinie;
	}
	public LinieBon() {
		super();
	}
	public static void add(LinieBon linieBon) {
		LinieBon.add(linieBon);
	}
	@Override
	public int hashCode() {
		return Objects.hash(TVALinie, bon, idLinie, curatatorie, valoareLinie);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LinieBon other = (LinieBon) obj;
		return Objects.equals(TVALinie, other.TVALinie) && Objects.equals(bon, other.bon)
				&& Objects.equals(idLinie, other.idLinie) && Objects.equals(curatatorie, other.curatatorie)
				&& Objects.equals(valoareLinie, other.valoareLinie);
	}
	@Override
	public String toString() {
		return "LinieBon [idLinie=" + idLinie + ", curatatorie=" + curatatorie + ", bon=" + bon + ", TVALinie="
				+ TVALinie + ", valoareLinie=" + valoareLinie + "]";
	}
	
	
}

