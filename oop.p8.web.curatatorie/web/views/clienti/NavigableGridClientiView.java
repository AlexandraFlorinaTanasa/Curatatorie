package org.app.curatatorie.web.views.clienti;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.app.curatatorie.oop.p8.web.curatatorie.MainView;
import org.entity.Client;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;



@PageTitle("clienti")
@Route(value = "clienti", layout = MainView.class)

public class NavigableGridClientiView extends VerticalLayout implements HasUrlParameter<Integer>{
	
	
	// Definire model date
	private EntityManager em;
	private List<Client> clienti = new ArrayList<>();
	private Client client = null;
	private Binder<Client> binder = new BeanValidationBinder<>(Client.class);
	
	// Definire componente view
	private H1 titluForm = new H1("Lista Clienti");
	
	// Definire componente suport navigare
	private VerticalLayout gridLayoutToolbar;
	private TextField filterText = new TextField();
	private Button cmdEditClient = new Button("Editeaza client...");
	private Button cmdAdaugaClient = new Button("Adauga client...");
	private Button cmdStergeClient = new Button("Sterge client...");
	private Grid<Client> grid = new Grid<>(Client.class);
	
	// init Data Model
	private void initDataModel(){
	System.out.println("DEBUG START FORM >>> ");
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("CuratatorieJPA");
	em = emf.createEntityManager();
	List<Client> lst = em
	.createQuery("SELECT c FROM Client c ORDER BY c.id", Client.class)
	.getResultList();
	clienti.addAll(lst);
	if (lst != null && !lst.isEmpty()){
	Collections.sort(this.clienti, (c1, c2) ->  c1.getId().compareTo(c2.getId()));
	this.client = clienti.get(0);
	System.out.println("DEBUG: client init >>> " + client.getId());
	}
	//
	grid.setItems(this.clienti);
	binder.setBean(this.client);
	grid.asSingleSelect().setValue(this.client);
	}
	// init View Model
	private void initViewLayout() {
	// Layout navigare -------------------------------------//
	// Toolbar navigare
	filterText.setPlaceholder("Filter by nume...");
	filterText.setClearButtonVisible(true);
	filterText.setValueChangeMode(ValueChangeMode.LAZY);
	HorizontalLayout gridToolbar = new HorizontalLayout(filterText,
	cmdEditClient, cmdAdaugaClient, cmdStergeClient);
	// Grid navigare
	grid.setColumns("id", "nume");
	grid.addComponentColumn(item -> createGridActionsButtons(item)).setHeader("Actiuni");
	// Init Layout navigare
	gridLayoutToolbar = new VerticalLayout(gridToolbar, grid);
	// ---------------------------
	this.add(titluForm, gridLayoutToolbar);
	//
	}
	private Component createGridActionsButtons(Client item) {
		//
		Button cmdEditItem = new Button("Edit");
		cmdEditItem.addClickListener(e -> {
		grid.asSingleSelect().setValue(item);
		editClient();
		});
		Button cmdDeleteItem = new Button("Sterge");
		cmdDeleteItem.addClickListener(e -> {
		System.out.println("Sterge item: " + item);
		grid.asSingleSelect().setValue(item);
		stergeClient();
		refreshForm();
		} );
		//
		return new HorizontalLayout(cmdEditItem, cmdDeleteItem);
		}
	
	// init Controller components
	private void initControllerActions() {
	// Navigation Actions
	filterText.addValueChangeListener(e -> updateList());
	cmdEditClient.addClickListener(e -> {
	editClient();
	});
	cmdAdaugaClient.addClickListener(e -> {
	adaugaClient();
	});
	cmdStergeClient.addClickListener(e -> {
	stergeClient();
	refreshForm();
	});
	}
	// CRUD actions
	// Adaugare: delegare catre Formular detalii pacient
	private void adaugaClient() {
	this.getUI().ifPresent(ui -> ui.navigate(FormClientView.class, 999));
	}
	// Editare: delegare catre Formular detalii pacient
	private void editClient() {
	this.client = this.grid.asSingleSelect().getValue();
	System.out.println("Selected client:: " + client);
	if (this.client != null) {
	this.getUI().ifPresent(ui -> ui.navigate(
	FormClientView.class, this.client.getId())
	);
	}
	}
	// CRUD actions
	// Stergere: tranzactie locala cu EntityManager
	private void stergeClient() {
	this.client = this.grid.asSingleSelect().getValue();
	System.out.println("To remove: " + this.client);
	this.clienti.remove(this.client);
	if (this.em.contains(this.client)) {
	this.em.getTransaction().begin();
	this.em.remove(this.client);
	this.em.getTransaction().commit();
	}
	if (!this.clienti.isEmpty())
	this.client = this.clienti.get(0);
	else
	this.client = null;
	}
	// Start Form
	public NavigableGridClientiView() {
	//
	initDataModel();
	//
	initViewLayout();
	//
	initControllerActions();
	}
	// Populare grid cu set de date din model - filtrare
	private void updateList() {
	try {
	List<Client> lstClientiFiltered = this.clienti;
	if (filterText.getValue() != null) {
	lstClientiFiltered = this.clienti.stream()
	.filter(c -> c.getNume().contains(filterText.getValue()))
	.toList();
	grid.setItems(lstClientiFiltered);
	}
	} catch (Exception e) {
	e.printStackTrace();
	}
	}
	// Resincronizare componente-view cu modelul de date
	private void refreshForm() {
	System.out.println("Client curent: " + this.client);
	if (this.client != null) {
	grid.setItems(this.clienti);
	binder.setBean(this.client);
	grid.select(this.client);
	}
	}
	
	// … … //
	// Navigation Management:
	// URL-ul http://localhost:8080/clienti/3 asigură selecția clientului cu ID 3
	@Override
	public void setParameter(BeforeEvent event, @OptionalParameter Integer id) {
	if (id != null) {
	this.client = em.find(Client.class, id);
	System.out.println("Back client: " + client);
	if (this.client == null) {
	// DELETED Item
	if (!this.clienti.isEmpty())
	this.client = this.clienti.get(0);
	}
	// else: EDITED or NEW Item
	}
	this.refreshForm();
	}
	// … … //
	}
 


