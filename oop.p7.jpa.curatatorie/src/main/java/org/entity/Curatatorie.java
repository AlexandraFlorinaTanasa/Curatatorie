package org.entity;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.AUTO;
@Entity
public class Curatatorie {
@Id
@GeneratedValue(strategy = AUTO)
private Integer cod;
private String denumire;
private Double tarif;
public Integer getCod() {
	return cod;
}
public void setCod(Integer cod) {
	this.cod = cod;
}
public String getDenumire() {
	return denumire;
}
public void setDenumire(String denumire) {
	this.denumire = denumire;
}
public Double getTarif() {
	return tarif;
}
public void setTarif(Double tarif) {
	this.tarif = tarif;
}
public Curatatorie(Integer cod, String denumire, Double tarif) {
	super();
	this.cod = cod;
	this.denumire = denumire;
	this.tarif = tarif;
}
public Curatatorie() {
	super();
}
@Override
public int hashCode() {
	return Objects.hash(cod, denumire, tarif);
}
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	Curatatorie other = (Curatatorie) obj;
	return Objects.equals(cod, other.cod) && Objects.equals(denumire, other.denumire)
			&& Objects.equals(tarif, other.tarif);
}
@Override
public String toString() {
	return "Curatatorie [cod=" + cod + ", denumire=" + denumire + ", tarif=" + tarif + "]";
}




}