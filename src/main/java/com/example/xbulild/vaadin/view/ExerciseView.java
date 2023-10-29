package com.example.xbulild.vaadin.view;

import com.example.xbulild.data.equipment.Equipment;
import com.example.xbulild.data.exercise.Exercise;
import com.example.xbulild.data.equipment.EquipmentService;
import com.example.xbulild.data.exercise.ExerciseService;
import com.example.xbulild.data.property.PropertyRepository;
import com.example.xbulild.data.property.PropertyService;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

import java.util.List;
import java.util.Set;


@Route(value = "/exercise", layout = AppView.class)
public class ExerciseView extends VerticalLayout {
    ExerciseService exerciseService;
    EquipmentService equipmentService;
    PropertyService propertyService;
    Grid<Exercise> exerciseGrid;
    TextField filterText = new TextField();
    Paragraph customApi = new Paragraph();
    CheckboxGroup<Equipment> equipmentCheckboxGroup = new CheckboxGroup<>();

    public ExerciseView(ExerciseService exerciseService, EquipmentService equipmentService, PropertyService propertyService){
        this.exerciseService = exerciseService;
        this.equipmentService = equipmentService;
        this.propertyService = propertyService;
        this.exerciseGrid = new Grid<>(Exercise.class, false);

        HorizontalLayout hl = new HorizontalLayout(new Paragraph("Current API query: "), customApi);
        hl.setSizeFull();

        exerciseGrid.addColumn(exercise -> exercise.getName()).setHeader("Name");
        exerciseGrid.addColumn(exercise -> getEquipmentSetAsString(exercise)).setHeader("Equipment");

        propertyService.findDistinctCategory().forEach(categoryStr -> {
            exerciseGrid.addColumn(exercise -> getPropertyAsString(exercise, categoryStr)).setHeader(categoryStr);
        });

        exerciseGrid.addColumn(exercise -> exercise.getExerciseTag().getTag()).setHeader("Tags");

        exerciseGrid.setDetailsVisibleOnClick(true);
        exerciseGrid.setItemDetailsRenderer(getUniqueApi());

        add(hl, exerciseGrid);

        filterText.setPlaceholder("Filter");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateGrid());

        equipmentCheckboxGroup.setLabel("Export data");
        equipmentCheckboxGroup.setItems(equipmentListDataProvider());
        equipmentCheckboxGroup.setItemLabelGenerator(Equipment::getName);
        equipmentCheckboxGroup.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);
        equipmentCheckboxGroup.addValueChangeListener(e -> updateGrid());

        add(filterText, equipmentCheckboxGroup);

        updateGrid();
        
    }

    private Renderer<Exercise> getUniqueApi() {
        ComponentRenderer<VerticalLayout, Exercise> CR = new ComponentRenderer<>(exercise ->{
            VerticalLayout vl = new VerticalLayout();

            String uniqueApiString = "localhost:8080/api/exercise/" + exercise.getId();
            Paragraph uniqueApiParagraph = new Paragraph(uniqueApiString);
            vl.add(uniqueApiParagraph);

            return vl;
        });
        return CR;
    }

    private void updateGrid() {
        Set<Equipment> selectedItems = equipmentCheckboxGroup.getSelectedItems();
        List<String> equipmentIdList = selectedItems.stream().map(Equipment::getId).toList();
        exerciseGrid.setItems(exerciseService.findAllByCustomFilter(filterText.getValue(), equipmentIdList, null));

        String apiQuery = getFilterAsApiQuery(filterText.getValue(), equipmentIdList);
        customApi.setText(apiQuery);
    }

    public String getFilterAsApiQuery(String filterText, List<String> equipmentIdList){
        StringBuffer filterAsQuery = new StringBuffer("");

        filterAsQuery.append("localhost:8080/api/exercise?");

        if(filterText != null && !filterText.isEmpty()){
            filterAsQuery.append("filterText=");
            filterAsQuery.append(filterText);
        }
        if(filterText != null && !filterText.isEmpty() && equipmentIdList != null && !equipmentIdList.isEmpty()){
            filterAsQuery.append("&");
        }
        if(equipmentIdList != null && !equipmentIdList.isEmpty()){
            filterAsQuery.append("equipmentList=");

            equipmentIdList.forEach(equipmentId -> {
                filterAsQuery.append(equipmentId);
                filterAsQuery.append(",");
            });
            filterAsQuery.deleteCharAt(filterAsQuery.length()-1);
        }

        return filterAsQuery.toString();
    }

    public String getEquipmentSetAsString(Exercise exercise){
        StringBuffer equipmentAsString = new StringBuffer("");
            exercise.getEquipmentSet().forEach(equipment -> {
                equipmentAsString.append(equipment.getName());
                equipmentAsString.append(", ");
            });
            return equipmentAsString.toString();
    }

    public String getPropertyAsString(Exercise exercise, String categoryStr) {
        StringBuffer propertyAsString = new StringBuffer("");
        exercise.getPropertySet().stream().filter(property -> property.getCategory().equals(categoryStr)).forEach(property -> {
            propertyAsString.append(property.getName());
            propertyAsString.append(", ");
        });
        return propertyAsString.toString();
    }

    public ListDataProvider<Equipment> equipmentListDataProvider(){
        return new ListDataProvider<>(equipmentService.findAll().stream().toList());
    }

}
