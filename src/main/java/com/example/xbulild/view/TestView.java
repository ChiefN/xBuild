package com.example.xbulild.view;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "/")
public class TestView extends VerticalLayout {

    public TestView() {
        add(new H1("Hello World!"));
    }
}
