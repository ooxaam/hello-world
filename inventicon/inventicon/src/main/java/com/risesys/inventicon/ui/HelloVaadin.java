package com.risesys.inventicon.ui;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;

import com.risesys.inventicon.model.User;
import com.risesys.inventicon.model.dal.UserRepository;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Osamam
 *
 */
@Slf4j
@SpringUI(path = "/login")
@Title("Hello Vaadin")
@Theme("valo")
public class HelloVaadin extends UI {

    private static final long serialVersionUID = -4888237252252350664L;

    @Autowired private UserRepository userRepository;
    
    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout mainLayout = new VerticalLayout();
        setContent(mainLayout);

        // mainLayout.setSizeFull();

        FormLayout formLayout = new FormLayout();

        TextField tfName = new TextField("Name");
        tfName.setRequiredIndicatorVisible(true);
        tfName.setIcon(VaadinIcons.USER);
        tfName.setMaxLength(20);

        final TextField tfAge = new TextField("Age");
        tfAge.setRequiredIndicatorVisible(true);
        tfAge.setIcon(VaadinIcons.BELL);
        tfAge.setMaxLength(2);

        DateField tfDateOfBirth = new DateField();
        tfDateOfBirth.setDateFormat("yyyy-MM-dd");
        tfDateOfBirth.setPlaceholder("yyyy-mm-dd");
        tfDateOfBirth.setRequiredIndicatorVisible(true);
        tfDateOfBirth.setIcon(VaadinIcons.DATE_INPUT);
        tfDateOfBirth.setValue(LocalDate.now());

        Button btnSubmit = new Button("Save");

        formLayout.addComponent(tfName);
        formLayout.addComponent(tfAge);
        formLayout.addComponent(tfDateOfBirth);
        formLayout.addComponent(btnSubmit);

        formLayout.setWidth(null);
        mainLayout.addComponent(formLayout);
        // mainLayout.setComponentAlignment(formLayout, Alignment.MIDDLE_CENTER);

        Grid<User> grid = new Grid<>();
        grid.addColumn(User::getFirstName).setCaption("Name");
        grid.addColumn(User::getEmail).setCaption("Age");
        grid.addColumn(User::getRole).setCaption("DOB");

        btnSubmit.addClickListener(click -> {

            if (isValidInt(tfAge)) {
                User savedPerson = userRepository.save(User.builder().firstName(tfName.getValue()).build());
//                    new Person(tfName.getValue(), Integer.valueOf(tfAge.getValue()), tfDateOfBirth.getValue()));

                Notification.show("Person saved with ID : " + savedPerson.getId());
                log.info("Person saved with ID : {}", savedPerson.getId());
                grid.setItems(userRepository.findAll());
            } else {
                Notification.show("Invalid data.");
            }
        });

        mainLayout.addComponent(grid);
    }

    private boolean isValidInt(TextField tfAge) {

        boolean isValid = true;

        try {
            String ageValue = tfAge.getValue();
            int age = Integer.valueOf(ageValue);
        } catch (Exception ex) {
            log.error("Invalid Integer.");
            isValid = false;
        }
        return isValid;
    }
}
