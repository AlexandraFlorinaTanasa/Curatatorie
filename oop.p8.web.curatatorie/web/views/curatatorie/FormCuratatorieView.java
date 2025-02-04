package org.app.curatatorie.web.views.curatatorie;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.app.curatatorie.oop.p8.web.curatatorie.MainView;
import org.entity.Curatatorie;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;



	@PageTitle("curatatorie")
		@Route(value = "curatatorie", layout = MainView.class)
	public class FormCuratatorieView extends VerticalLayout implements HasUrlParameter<Integer>{

		
		// Definire model date
		private EntityManager em;
		private Curatatorie curatatorie = null;
		private Binder<Curatatorie> binder = new BeanValidationBinder<>(Curatatorie.class);
		// Definire componente view
		// Definire Form
		private VerticalLayout formLayoutToolbar;
		private H1 titluForm = new H1("Form Curatatorie");
		private IntegerField cod = new IntegerField("Cod curatatorie:");
		private TextField denumire = new TextField("Denumire: ");
		// Definire componente actiuni Form-Controller
		private Button cmdAdaugare = new Button("Adauga");
		private Button cmdSterge = new Button("Sterge");
		private Button cmdAbandon = new Button("Abandon");
		private Button cmdSalveaza = new Button("Salveaza");
			// … … //
			// Navigation Management:
			// URL-ul http://localhost:8080/clienti/3 asigură afișare detaliilor clientului cu ID 3
			@Override
			public void setParameter(BeforeEvent event, @OptionalParameter Integer cod) {
			System.out.println("Curatatorie cod: " + cod);
			if (cod != null) {
			// EDIT Item
			this.curatatorie = em.find(Curatatorie.class, cod);
			System.out.println("Selected curatatorie to edit:: " + curatatorie);
			if (this.curatatorie == null) {
			System.out.println("ADD curatatorie:: " + curatatorie);
			// NEW Item
			this.adaugaCuratatorie();
			this.curatatorie.setCod(cod);
			this.curatatorie.setDenumire("Curatatorie NOUA " + cod);
			}
			}
			this.refreshForm();
			}
			
			// init Data Model
			private void initDataModel(){
			System.out.println("DEBUG START FORM >>> ");
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("CuratatorieJPA");
			this.em = emf.createEntityManager();
			this.curatatorie = em
			.createQuery("SELECT c FROM Curatatorie c ORDER BY c.cod", Curatatorie.class)
			.getResultStream().findFirst().get();
			//
			binder.forField(cod).bind("cod");
			binder.forField(denumire).bind("denumire");
			//
			refreshForm();
			}
			// init View Model
			private void initViewLayout() {
			// Form-Master-Details -----------------------------------//
			// Form-Master
			FormLayout formLayout = new FormLayout();
			formLayout.add(cod, denumire);
			formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));
			formLayout.setMaxWidth("400px");
			// Toolbar-Actions-Master
			HorizontalLayout actionToolbar =
			new HorizontalLayout(cmdAdaugare, cmdSterge, cmdAbandon, cmdSalveaza);
			actionToolbar.setPadding(false);
			//
			this.formLayoutToolbar = new VerticalLayout(formLayout, actionToolbar);
			// ---------------------------
			this.add(titluForm, formLayoutToolbar);
			//
			}
			// init Controller components
			private void initControllerActions() {
			// Transactional Master Actions
			cmdAdaugare.addClickListener(e -> {
			adaugaCuratatorie();
			refreshForm();
			});
			cmdSterge.addClickListener(e -> {
			stergeCuratatorie();
			// Navigate back to NavigableGridClienteForm
			this.getUI().ifPresent(ui -> ui.navigate(
			NavigableGridCuratatorieView.class)
			);
			});
			cmdAbandon.addClickListener(e -> {
			// Navigate back to NavigableGridClienteForm
			this.getUI().ifPresent(ui -> ui.navigate(
			NavigableGridCuratatorieView.class, this.curatatorie.getCod())
			);
			});
			cmdSalveaza.addClickListener(e -> {
			salveazaCuratatorie();
			// refreshForm();
			// Navigate back to NavigableGridClienteForm
			this.getUI().ifPresent(ui -> ui.navigate(
			NavigableGridCuratatorieView.class, this.curatatorie.getCod())
			);
			});
			}
			private void refreshForm() {
				System.out.println("Curatatorie curenta: " + this.curatatorie);
				if (this.curatatorie != null) {
				binder.setBean(this.curatatorie);
				}
				}
			// CRUD actions
			private void salveazaCuratatorie() {
			try {
			this.em.getTransaction().begin();
			this.curatatorie = this.em.merge(this.curatatorie);
			this.em.getTransaction().commit();
			System.out.println("Curatatorie Salvata");
			} catch (Exception ex) {
			if (this.em.getTransaction().isActive())
			this.em.getTransaction().rollback();
			System.out.println("*** EntityManager Validation ex: " + ex.getMessage());
			throw new RuntimeException(ex.getMessage());
			}
			}
			// CRUD actions
			private void adaugaCuratatorie() {
			this.curatatorie = new Curatatorie();
			this.curatatorie.setCod(999);  // ID arbitrar, inexistent în baza de date
			this.curatatorie.setDenumire("Curatatorie Noua");
			}
			// CRUD actions
			private void stergeCuratatorie() {
			System.out.println("To remove: " + this.curatatorie);
			if (this.em.contains(this.curatatorie)) {
			this.em.getTransaction().begin();
			this.em.remove(this.curatatorie);
			this.em.getTransaction().commit();
			}
			}
			// Start Form
			public FormCuratatorieView() {
			//
			initDataModel();
			//
			initViewLayout();
			//
			initControllerActions();
			}
		}
		