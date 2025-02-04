package org.app.curatatorie.oop.p8.web.curatatorie;



import org.app.curatatorie.web.views.clienti.FormClientView;
import org.app.curatatorie.web.views.clienti.NavigableGridClientiView;
import org.app.curatatorie.web.views.curatatorie.FormCuratatorieView;
import org.app.curatatorie.web.views.curatatorie.NavigableGridCuratatorieView;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;

@Route
public class MainView extends VerticalLayout implements RouterLayout {
public MainView() {
setMenuBar();
}
private void setMenuBar() {
MenuBar mainMenu = new MenuBar();
MenuItem homeMenu = mainMenu.addItem("Home");
homeMenu.addClickListener(event -> UI.getCurrent().navigate(MainView.class));
//


MenuItem gridFormsClientiMenu = mainMenu.addItem("Clienti");
SubMenu gridFormsClientiMenuBar = gridFormsClientiMenu.getSubMenu();
gridFormsClientiMenuBar.addItem("Lista Clienti...",
event -> UI.getCurrent().navigate(NavigableGridClientiView.class));
gridFormsClientiMenuBar.addItem("Form Editare Client...",
event -> UI.getCurrent().navigate(FormClientView.class));
//
//
MenuItem gridFormsCuratatoriiMenu = mainMenu.addItem("Curatatorie ");
SubMenu gridFormsCuratatoriiMenuBar = gridFormsCuratatoriiMenu.getSubMenu();
gridFormsCuratatoriiMenuBar.addItem("Lista Curatatorii...",
event -> UI.getCurrent().navigate(NavigableGridCuratatorieView.class));
gridFormsCuratatoriiMenuBar.addItem("Form Editare Curatatorie...",
event -> UI.getCurrent().navigate(FormCuratatorieView.class));

 
add(new HorizontalLayout(mainMenu));
}
}








 
