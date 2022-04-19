package com.example.xbulild.vaadin.view;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;

public class AppView extends AppLayout {

    public AppView(){
        addToNavbar(new H1("xBuild"));

        RouterLink exerciseViewLink = new RouterLink("View exercises", ExerciseView.class);
        RouterLink testViewLink = new RouterLink("View testView", TestView.class);
        RouterLink adminViewLink = new RouterLink("View admin", AdminView.class);

        addToDrawer(new VerticalLayout(exerciseViewLink, testViewLink, adminViewLink));

    }
}
