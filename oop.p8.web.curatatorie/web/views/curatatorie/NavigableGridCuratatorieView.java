package org.app.curatatorie.web.views.curatatorie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;




import org.entity.Curatatorie;

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

import org.app.curatatorie.oop.p8.web.curatatorie.MainView;


	@PageTitle("curatatorii")
	@Route(value = "curatatorii", layout = MainView.class)

	public class NavigableGridCuratatorieView  extends VerticalLayout implements HasUrlParameter<Integer>{
		
		
		// Definire model date
		private EntityManager em;
		private List<Curatatorie> curatatorii = new ArrayList<>();
		private Curatatorie curatatorie = null;
		private Binder<Curatatorie> binder = new BeanValidationBinder<>(Curatatorie.class);
		
		// Definire componente view
		private H1 titluForm = new H1("Lista Curatatorie");
		
		// Definire componente suport navigare
		private VerticalLayout gridLayoutToolbar;
		private TextField filterText = new TextField();
		private Button cmdEditCuratatorie = new Button("Editeaza curatatorie...");
		private Button cmdAdaugaCuratatorie = new Button("Adauga curatatorie...");
		private Button cmdStergeCuratatorie = new Button("Sterge curatatorie...");
		private Grid<Curatatorie> grid = new Grid<>(Curatatorie.class);
		
		// init Data Model
		private void initDataModel(){
		System.out.println("DEBUG START FORM >>> ");
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("CuratatorieJPA");
		em = emf.createEntityManager();
		List<Curatatorie> lst = em
		.createQuery("SELECT c FROM Curatatorie c ORDER BY c.cod", Curatatorie.class)
		.getResultList();
		curatatorii.addAll(lst);
		if (lst != null && !lst.isEmpty()){
		Collections.sort(this.curatatorii, (c1, c2) ->  c1.getCod().compareTo(c2.getCod()));
		this.curatatorie = curatatorii.get(0);
		System.out.println("DEBUG: curatatorie init >>> " + curatatorie.getCod());
		}
		//
		grid.setItems(this.curatatorii);
		binder.setBean(this.curatatorie);
		grid.asSingleSelect().setValue(this.curatatorie);
		}
		// init View Model
		private void initViewLayout() {
		// Layout navigare -------------------------------------//
		// Toolbar navigare
		filterText.setPlaceholder("Filter by nume...");
		filterText.setClearButtonVisible(true);
		filterText.setValueChangeMode(ValueChangeMode.LAZY);
		HorizontalLayout gridToolbar = new HorizontalLayout(filterText,
		cmdEditCuratatorie, cmdAdaugaCuratatorie, cmdStergeCuratatorie);
		// Grid navigare
		grid.setColumns("cod", "denumire");
		grid.addComponentColumn(item -> createGridActionsButtons(item)).setHeader("Actiuni");
		// Init Layout navigare
		gridLayoutToolbar = new VerticalLayout(gridToolbar, grid);
		// ---------------------------
		this.add(titluForm, gridLayoutToolbar);
		//
		}
		private Component createGridActionsButtons(Curatatorie item) {
			//
			Button cmdEditItem = new Button("Edit");
			cmdEditItem.addClickListener(e -> {
			grid.asSingleSelect().setValue(item);
			editCuratatorie();
			});
			Button cmdDeleteItem = new Button("Sterge");
			cmdDeleteItem.addClickListener(e -> {
			System.out.println("Sterge item: " + item);
			grid.asSingleSelect().setValue(item);
			stergeCuratatorie();
			refreshForm();
			} );
			//
			return new HorizontalLayout(cmdEditItem, cmdDeleteItem);
			}
		
		// init Controller components
		private void initControllerActions() {
		// Navigation Actions
		filterText.addValueChangeListener(e -> updateList());
		cmdEditCuratatorie.addClickListener(e -> {
		editCuratatorie();
		});
		cmdAdaugaCuratatorie.addClickListener(e -> {
		adaugaCuratatorie();
		});
		cmdStergeCuratatorie.addClickListener(e -> {
		stergeCuratatorie();
		refreshForm();
		});
		}
		// CRUD actions
		// Adaugare: delegare catre Formular detalii curatatorie
		private void adaugaCuratatorie() {
		this.getUI().ifPresent(ui -> ui.navigate(FormCuratatorieView.class, 999));
		}
		// Editare: delegare catre Formular detalii curatatorie
		private void editCuratatorie() {
		this.curatatorie = this.grid.asSingleSelect().getValue();
		System.out.println("Selected curatatorie:: " + curatatorie);
		if (this.curatatorie != null) {
		this.getUI().ifPresent(ui -> ui.navigate(
		FormCuratatorieView.class, this.curatatorie.getCod())
		);
		}
		}
		// CRUD actions
		// Stergere: tranzactie locala cu EntityManager
		private void stergeCuratatorie() {
		this.curatatorie = this.grid.asSingleSelect().getValue();
		System.out.println("To remove: " + this.curatatorie);
		this.curatatorii.remove(this.curatatorie);
		if (this.em.contains(this.curatatorie)) {
		this.em.getTransaction().begin();
		this.em.remove(this.curatatorie);
		this.em.getTransaction().commit();
		}
		if (!this.curatatorii.isEmpty())
		this.curatatorie = this.curatatorii.get(0);
		else
		this.curatatorie = null;
		}
		// Start Form
		public NavigableGridCuratatorieView() {
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
		List<Curatatorie> lstCuratatorieFiltered = this.curatatorii;
		if (filterText.getValue() != null) {
		lstCuratatorieFiltered = this.curatatorii.stream()
		.filter(c -> c.getDenumire().contains(filterText.getValue()))
		.toList();
		grid.setItems(lstCuratatorieFiltered);
		}
		} catch (Exception e) {
		e.printStackTrace();
		}
		}
		// Resincronizare componente-view cu modelul de date
		private void refreshForm() {
		System.out.println("Curatatorie curenta: " + this.curatatorie);
		if (this.curatatorie != null) {
		grid.setItems(this.curatatorii);
		binder.setBean(this.curatatorie);
		grid.select(this.curatatorie);
		}
		}
		
		// … … //
		// Navigation Management:
		// URL-ul http://localhost:8080/clienti/3 asigură selecția clientului cu ID 3
		@Override
		public void setParameter(BeforeEvent event, @OptionalParameter Integer cod) {
		if (cod != null) {
		this.curatatorie = em.find(Curatatorie.class, cod);
		System.out.println("Back curatatorie: " + curatatorie);
		if (this.curatatorie == null) {
		// DELETED Item
		if (!this.curatatorii.isEmpty())
		this.curatatorie = this.curatatorii.get(0);
		}
		// else: EDITED or NEW Item
		}
		this.refreshForm();
		}
		// … … //
		}
	 